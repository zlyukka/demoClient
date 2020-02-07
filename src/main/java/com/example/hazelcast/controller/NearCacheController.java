package com.example.hazelcast.controller;

import com.example.hazelcast.dto.User;
import com.example.hazelcast.services.UserCacheService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Vitaliy on 07.02.2020.
 */
@RestController
@RequestMapping(value = "/near")
public class NearCacheController {

    private IMap<Long, User> userIMap;

    public NearCacheController(HazelcastInstance hazelcastInstance){
        this.userIMap = hazelcastInstance.getMap("user");
    }

    @PostMapping(value = "/add/user")
    public User saveUserInNearCache(@RequestBody User user){
        userIMap.put(user.getId(), user);
        return user;
    }

    @GetMapping(value = "/get/user/{id}")
    public User readUserInNearCache(@PathVariable("id") Long id){
        User user = userIMap.get(id);
        return user;
    }
}
