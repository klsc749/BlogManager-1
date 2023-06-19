package com.example.blogmanager.convert;

import com.example.blogmanager.model.DO.Blog;
import com.example.blogmanager.model.DTO.BlogItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BlogItemConvertTest {
    @Test
    public void testBlogToBlogItemDTO() {
        Blog blog = new Blog();
        blog.setTitle("test");
        blog.setContent("test");
        blog.setCategory("test");
        List<String> tags = new ArrayList<>();
        tags.add("test");
        blog.setTags(tags);
        blog.setDate(null);
        blog.setLastModifiedTime(null);
        blog.setId(null);

        BlogItemDTO blogItemDTO = BlogItemConvert.INSTANCE.blogToBlogItemDTO(blog);
        System.out.println(blogItemDTO);
    }

    @Test
    public void testBlogListToBlogItemDTOList() {
        Blog blog = new Blog();
        blog.setTitle("test");
        blog.setContent("test");
        blog.setCategory("test");
        List<String> tags = new ArrayList<>();
        tags.add("test");
        blog.setTags(tags);
        blog.setDate(null);
        blog.setLastModifiedTime(null);
        blog.setId(null);

        List<Blog> blogs = new ArrayList<>();
        blogs.add(blog);
        List<BlogItemDTO> blogItemDTOS = BlogItemConvert.INSTANCE.blogListToBlogItemDTOList(blogs);
        System.out.println(blogItemDTOS);
    }
}
