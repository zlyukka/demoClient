package com.example.hazelcast.services;

import com.example.hazelcast.dto.User;

import java.util.Optional;

/**
 * Created by Vitaliy on 25.12.2019.
 */
public interface UserCacheService extends Crud<User> {

    Optional<User> getFromReplicated(Long id);
    
}
