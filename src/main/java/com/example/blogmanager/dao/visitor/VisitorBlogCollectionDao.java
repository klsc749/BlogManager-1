package com.example.blogmanager.dao.visitor;

import com.example.blogmanager.model.DO.BlogCollection;
import com.example.blogmanager.model.DTO.visitor.VisitorBlogCollectionDTO;
import com.example.blogmanager.util.PageQueryUtil;

import java.util.List;

public interface VisitorBlogCollectionDao {

    List<VisitorBlogCollectionDTO> getBlogCollectionByPage(PageQueryUtil pageQueryUtil);
    long countBlogCollection();

}
