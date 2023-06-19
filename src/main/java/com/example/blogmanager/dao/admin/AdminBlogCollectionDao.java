package com.example.blogmanager.dao.admin;

import com.example.blogmanager.model.DO.BlogCollection;
import com.mongodb.client.result.UpdateResult;

public interface AdminBlogCollectionDao {
    boolean checkBlogCollectionExist(String title);
    BlogCollection saveBlogCollection(BlogCollection blogCollection);
    boolean deleteBlogCollection(String id);

    UpdateResult updateBlogCollection(BlogCollection blogCollection);
}
