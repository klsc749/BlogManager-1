package com.example.blogmanager.model.DO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "blog")
@ToString
public class Blog {
    @Id
    String id;
    String title;
    Date date;
    Date lastModifiedTime;
    String content;
    List<String> tags;
    String category;
    Visibility visibility;
    BlogStatus status;
    int viewCount;

}
