package com.example.hazelcast.services;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vitaliy on 17.12.2019.
 */
public interface Crud<T>{

    Iterable<T> getAll();

    Optional<T> getById(Long id);

    T save(T value);

    Iterable<T> saveAll(List<T> values);

    void remove(Long id);
}
