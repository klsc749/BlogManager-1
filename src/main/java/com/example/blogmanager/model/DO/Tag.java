package com.example.blogmanager.model.DO;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tag")
public class Tag {
    @Id
    String id;
    String name;
    int count;
}
