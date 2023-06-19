package com.example.blogmanager.service;

import com.example.blogmanager.dao.TagDao;
import com.example.blogmanager.model.DO.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagDao tagDao;

    @Autowired
    public TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public void addTag(Tag tag) {
        tag.setCount(1);
        tagDao.save(tag);
    }

    public void addTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        addTag(tag);
    }

    public void addOrUpdateTagCount(String name) {
        if(tagDao.checkTagExist(name))
            tagDao.increaseCount(name);
        else
            addTag(name);
    }
}
