package com.example.hazelcast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullFields;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Vitaliy on 17.12.2019.
 */
@Data
//@Document(indexName = "contact_book", type = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Long id;
    private String name;
    private String phone;
    private String addr;
}
