package com.example.blogmanager.model.DTO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogItemDTO {
    private String id;
    private String title;
    private String contentAbstract;
    private List<String> tags;
    private String category;
    private Date date;
}
