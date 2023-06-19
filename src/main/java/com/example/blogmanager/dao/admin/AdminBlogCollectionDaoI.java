package com.example.blogmanager.dao.admin;

import com.example.blogmanager.model.DO.BlogCollection;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminBlogCollectionDaoI implements AdminBlogCollectionDao{
    private final MongoTemplate mongoTemplate;
    @Override
    public boolean deleteBlogCollection(String id) {
        return mongoTemplate.remove(new Query(Criteria.where("id").is(id)), BlogCollection.class).getDeletedCount() > 0;
    }

    @Override
    public boolean checkBlogCollectionExist(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        if (mongoTemplate.findOne(query, BlogCollection.class) != null) {
            return true;
        }
        return false;
    }

    @Override
    public BlogCollection saveBlogCollection(BlogCollection blogCollection) {
        return mongoTemplate.save(blogCollection);
    }

    @Override
    public UpdateResult updateBlogCollection(BlogCollection blogCollection) {
        Update update = new Update();
        update.set("title", blogCollection.getTitle());
        update.set("cover", blogCollection.getCover());
        return mongoTemplate.updateFirst(new Query(Criteria.where("id").is(blogCollection.getId())), update, BlogCollection.class);
    }
}
