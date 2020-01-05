package com.example.hazelcast.hazelcast;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Created by Vitaliy on 18.12.2019.
 */
@Data
@ConfigurationProperties("external.hazelcast")
public class HazelcastProperties {
    private String members;

    private String groupName;

    private String outboundPortDefinition;

    private Integer connectionTimeout;

    private Integer connectionAttemptLimit;

    private boolean smartRouting;
}
