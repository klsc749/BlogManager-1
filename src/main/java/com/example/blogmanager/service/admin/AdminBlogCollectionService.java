package com.example.blogmanager.service.admin;

import com.example.blogmanager.model.DO.BlogCollection;

public interface AdminBlogCollectionService {

    BlogCollection modifyBlogCollection(BlogCollection blogCollection);

    BlogCollection createBlogCollection(BlogCollection blogCollection);

    boolean deleteBlogCollection(String id);
}
