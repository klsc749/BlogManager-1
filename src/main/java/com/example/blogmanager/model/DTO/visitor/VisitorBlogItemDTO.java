package com.example.blogmanager.model.DTO.visitor;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class VisitorBlogItemDTO {
    String id;
    String title;
    String contentAbstract;
    Date date;
    Date lastModifiedTime;
    String category;
    List<String> tags;
    int viewCount;
}
