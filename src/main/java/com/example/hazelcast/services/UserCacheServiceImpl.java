package com.example.hazelcast.services;

import com.example.hazelcast.dto.User;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ReplicatedMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Vitaliy on 25.12.2019.
 */
@Service
public class UserCacheServiceImpl implements UserCacheService{
    private final ConcurrentHashMap<Long, User> userConcurrentHashMap;
    private ReplicatedMap<Long, User> userReplicatedMap;

    public UserCacheServiceImpl(HazelcastInstance hazelcastInstance){
        userConcurrentHashMap = new ConcurrentHashMap<>();
        userReplicatedMap = hazelcastInstance.getReplicatedMap("userReplicated");
        userReplicatedMap.addEntryListener(new HazelcastEntryListener(userConcurrentHashMap));
        userConcurrentHashMap.putAll(userReplicatedMap);
    }


    @Override
    public Iterable<User> getAll() {
        return userConcurrentHashMap.values();
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(userConcurrentHashMap.get(id));
    }

    @Override
    public User save(User value) {
        userReplicatedMap.put(value.getId(), value);
        return value;
    }

    @Override
    public Iterable<User> saveAll(List<User> values) {
        Map<Long, User> userMap = values.stream()
                .collect(Collectors.toMap(k -> k.getId(), v -> v));
        userReplicatedMap.putAll(userMap);
        return values;
    }

    @Override
    public void remove(Long id) {
        userReplicatedMap.remove(id);
    }

    @Override
    public Optional<User> getFromReplicated(Long id) {
        return Optional.ofNullable(userReplicatedMap.get(id));
    }
}
