package com.example.blogmanager.service.visitor;

import com.example.blogmanager.model.DTO.visitor.VisitorBlogCollectionDTO;
import com.example.blogmanager.util.PageQueryUtil;
import com.example.blogmanager.util.PageResult;

import java.util.List;

public interface VisitorBlogCollectionService {
    PageResult<VisitorBlogCollectionDTO> getBlogCollectionByPage(PageQueryUtil pageQueryUtil);
}
