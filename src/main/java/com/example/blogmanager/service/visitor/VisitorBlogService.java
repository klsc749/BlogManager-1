package com.example.blogmanager.service.visitor;

import com.example.blogmanager.model.DTO.visitor.VisitorBlogDTO;
import com.example.blogmanager.model.DTO.visitor.VisitorBlogItemDTO;
import com.example.blogmanager.util.PageQueryUtil;
import com.example.blogmanager.util.PageResult;

public interface VisitorBlogService {
    PageResult<VisitorBlogItemDTO> getBlogItemsByPage(PageQueryUtil pageUtil);
    PageResult<VisitorBlogItemDTO> getBlogItemsByPageByCategory(PageQueryUtil pageUtil, String collection);
    VisitorBlogDTO getBlogById(String blogId);
}
