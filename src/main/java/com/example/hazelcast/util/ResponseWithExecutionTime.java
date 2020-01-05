package com.example.hazelcast.util;

import lombok.Data;
import org.omg.CORBA.Object;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * Created by Vitaliy on 17.12.2019.
 */
@Data
public class ResponseWithExecutionTime<T> implements Serializable{
    private Long processingTime;
    private T object;

    public ResponseWithExecutionTime(T object, Long processingTime){
        this.processingTime = processingTime;
        this.object = object;
    }
}
