package com.example.hazelcast.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientConnectionStrategyConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.client.config.ConnectionRetryConfig;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.core.HazelcastInstance;
import org.apache.el.stream.Optional;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static java.util.Optional.*;

/**
 * Created by Vitaliy on 18.12.2019.
 */
@Configuration
@PropertySource("classpath:hazelcast.properties")
@EnableConfigurationProperties(HazelcastProperties.class)
public class HazelcastConfig {

    private HazelcastProperties externalHazelcastProperties;

    public HazelcastConfig(HazelcastProperties hazelcastProperties){
        this.externalHazelcastProperties = hazelcastProperties;
    }

    @Bean
    public ClientConfig clientExternalConfig(){
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getGroupConfig().setName(externalHazelcastProperties.getGroupName());
        clientConfig.setProperty("hazelcast.client.statistics.enabled", "true");
        clientConfig.getConnectionStrategyConfig()
                .setReconnectMode(ClientConnectionStrategyConfig.ReconnectMode.ON);

        String[] members = ofNullable(externalHazelcastProperties.getMembers())
                .map(v -> v.split(","))
                .orElse(new String[0]);

        ClientNetworkConfig network = clientConfig.getNetworkConfig();
        network.addAddress(members)
                .setSmartRouting(false)
                .addOutboundPortDefinition(externalHazelcastProperties.getOutboundPortDefinition())
                .setRedoOperation(true)
                .setConnectionTimeout(externalHazelcastProperties.getConnectionTimeout())
                .setConnectionAttemptLimit(externalHazelcastProperties.getConnectionAttemptLimit());

        EvictionConfig evictionConfig = new EvictionConfig()
                .setEvictionPolicy(EvictionPolicy.LFU)
                .setMaximumSizePolicy(EvictionConfig.MaxSizePolicy.ENTRY_COUNT)
                .setSize(50000);

        NearCacheConfig nearCacheConfig = new NearCacheConfig()
                .setName("user")
                .setInMemoryFormat(InMemoryFormat.OBJECT)
                .setInvalidateOnChange(true)
                .setEvictionConfig(evictionConfig);

        clientConfig.addNearCacheConfig(nearCacheConfig);
        return clientConfig;
    }

    @Bean
    public HazelcastInstance hazelcastInstance() {
        return HazelcastClient.newHazelcastClient(clientExternalConfig());
    }
}
