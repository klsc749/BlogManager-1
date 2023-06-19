package com.example.blogmanager.dao;

import com.example.blogmanager.model.DO.Tag;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDao {
    @Resource
    private MongoTemplate mongoTemplate;

    public void save(Tag tag) {
        mongoTemplate.save(tag);
    }

    public List<Tag> findAll() {
        return mongoTemplate.findAll(Tag.class);
    }

    public Tag findById(String id) {
        return mongoTemplate.findById(id, Tag.class);
    }

    public Tag findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Tag.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(findById(id));
    }

    public void deleteByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        mongoTemplate.remove(query, Tag.class);
    }

    public void increaseCount(String name) {
        Tag tag = findByName(name);
        if(tag == null)
            return;
        tag.setCount(tag.getCount() + 1);
        mongoTemplate.save(tag);
    }

    public void decreaseCount(String name) {
        Tag tag = findByName(name);
        if(tag == null)
            return;
        tag.setCount(tag.getCount() - 1);
        mongoTemplate.save(tag);
    }

    public boolean checkTagExist(String name) {
        Tag tag = findByName(name);
        return tag != null;
    }
}
