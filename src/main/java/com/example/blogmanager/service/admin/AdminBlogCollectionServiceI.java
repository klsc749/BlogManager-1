package com.example.blogmanager.service.admin;

import com.example.blogmanager.dao.admin.AdminBlogCollectionDao;
import com.example.blogmanager.model.DO.BlogCollection;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AdminBlogCollectionServiceI implements AdminBlogCollectionService{
    private final AdminBlogCollectionDao adminBlogCollectionDao;
    @Override
    public BlogCollection modifyBlogCollection(BlogCollection blogCollection) {
        UpdateResult updateResult = adminBlogCollectionDao.updateBlogCollection(blogCollection);
        return updateResult.getModifiedCount() > 0 ? blogCollection : null;
    }

    @Override
    public BlogCollection createBlogCollection(BlogCollection blogCollection) {
        blogCollection.setCreateDate(new Date());
        return adminBlogCollectionDao.saveBlogCollection(blogCollection);
    }

    @Override
    public boolean deleteBlogCollection(String id) {
        return adminBlogCollectionDao.deleteBlogCollection(id);
    }
}
