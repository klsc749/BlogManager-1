package com.example.blogmanager.model.DTO.admin;

import com.example.blogmanager.model.DO.BlogStatus;
import com.example.blogmanager.model.DO.Visibility;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AdminBlogDTO {
    String id;
    String title;
    String content;
    Date date;
    Date lastModifiedTime;
    String category;
    List<String> tags;
    Visibility visibility;
    BlogStatus status;
    int viewCount;
}
