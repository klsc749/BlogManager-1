package com.example.blogmanager.dao.visitor;

import com.example.blogmanager.model.DTO.visitor.VisitorBlogDTO;
import com.example.blogmanager.model.DTO.visitor.VisitorBlogItemDTO;
import com.example.blogmanager.util.PageQueryUtil;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

public interface VisitorBlogDao {
    List<VisitorBlogItemDTO> getBlogItemsByPage(PageQueryUtil pageUtil);

    List<VisitorBlogItemDTO> getBlogItemsByPageByCollection(PageQueryUtil pageUtil, String collection);

    long getTotalBlogs();

    long getTotalBlogsByCollection(String collection);


    VisitorBlogDTO getBlogById(String blogId);

    UpdateResult increaseBlogViewCount(String blogId);
}
