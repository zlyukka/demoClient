package com.example.hazelcast.controller;

import com.example.hazelcast.dto.User;
import com.example.hazelcast.services.Crud;
import com.example.hazelcast.services.UserCacheService;
import com.example.hazelcast.services.UserCacheServiceImpl;
import com.example.hazelcast.services.UserServices;
import com.example.hazelcast.util.ResponseWithExecutionTime;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vitaliy on 17.12.2019.
 */
@RestController
public class ElasticSearchController {

    UserServices userService;

    public ElasticSearchController(UserServices userService){
        this.userService = userService;
    }

    @GetMapping(value = "/get/user/by/id/{id}")
    public ResponseEntity<ResponseWithExecutionTime> getUserById(@PathVariable(value = "id") Long id){
        return processRequest(() -> userService.getById(id).orElse(new User()));
    }

    @GetMapping(value = "/get/user/all")
    public ResponseEntity<ResponseWithExecutionTime> getAllUser(){
        return processRequest(() -> Lists.newArrayList(userService.getAll()));
    }

    @DeleteMapping(value = "/delete/user/{id}")
    public ResponseEntity<ResponseWithExecutionTime> deleteUserById(@PathVariable(value = "id") Long id){
        return processRequest(() -> {
            userService.remove(id);
            return new User();
        });
    }

    @PostMapping(value = "/save/user")
    public ResponseEntity<ResponseWithExecutionTime> saveUser(@RequestBody User user){
        return processRequest(() -> userService.save(user));
    }

    private <T> ResponseEntity<ResponseWithExecutionTime> processRequest(Supplier<T> supplier){
        long startTime = System.currentTimeMillis();
        T object = supplier.get();
        return new ResponseEntity<>(new ResponseWithExecutionTime<T>(object, System.currentTimeMillis()-startTime), HttpStatus.OK);
    }
}
