package com.example.hazelcast.services;

//import com.example.hazelcast.dao.UserDao;
import com.example.hazelcast.dto.User;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ReplicatedMap;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Vitaliy on 17.12.2019.
 */
@Service
public class UserServicesImpl implements UserServices {

//    private UserDao userDao;

    private IMap<Long, User> resourcesMap;

    public UserServicesImpl(HazelcastInstance hazelcastInstance){
//        this.userDao = userDao;
        this.resourcesMap = hazelcastInstance.getMap("user");
    }


    @Override
    public Iterable<User> getAll() {
        return resourcesMap.values();
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(resourcesMap.get(id));
    }

    @Override
    public User save(User value) {
        return resourcesMap.put(value.getId(),value);
    }

    @Override
    public Iterable<User> saveAll(List<User> values) {
        Map<Long, User> userMap = values.stream()
                .collect(Collectors.toMap(k -> k.getId(), v -> v));
        resourcesMap.putAll(userMap);
        return userMap.values();
    }

    @Override
    public void remove(Long id) {
        resourcesMap.remove(id);
    }
}
