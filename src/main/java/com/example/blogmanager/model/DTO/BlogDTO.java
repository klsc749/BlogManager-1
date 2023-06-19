package com.example.blogmanager.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {
    @Id
    String id;
    String title;
    Date date;
    Date lastModifiedTime;
    String content;
    List<String> tags;
    String category;
}
