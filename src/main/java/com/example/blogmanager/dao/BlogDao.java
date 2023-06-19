package com.example.blogmanager.dao;

import com.example.blogmanager.model.DO.Blog;
import com.example.blogmanager.model.DO.BlogStatus;
import com.example.blogmanager.model.DTO.BlogItemDTO;
import com.example.blogmanager.util.PageQueryUtil;
import com.mongodb.client.result.UpdateResult;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class BlogDao {
    @Resource
    private MongoTemplate mongoTemplate;

    public Blog save(Blog blog) {
        return mongoTemplate.save(blog);
    }

    public List<Blog> findAllBlogDO() {
        return mongoTemplate.findAll(Blog.class);
    }

    public Blog findById(String id) {
        return mongoTemplate.findById(id, Blog.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(findById(id));
    }

    public List<BlogItemDTO> getAllBlogItemDTO() {

        ProjectionOperation projectionOperation = Aggregation.project("title", "tags", "category", "date")
                .andExpression("substr(content, 0, 100)").as("contentAbstract");

        Aggregation aggregation = Aggregation.newAggregation(projectionOperation);

        return mongoTemplate.aggregate(aggregation, Blog.class, BlogItemDTO.class).getMappedResults();
    }

    public long countVisibleBlogs() {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").nin("DELETED", "PRIVATE"));
        return mongoTemplate.count(query, Blog.class);
    }

    public boolean increaseViewCount(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("viewCount", 1);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Blog.class);
        return updateResult.getModifiedCount() > 0;
    }

    public boolean updateBlogStatus(String id, BlogStatus status) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("status", status);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Blog.class);
        return updateResult.getModifiedCount() > 0;
    }

    public boolean updateBlogContent(String id, String content) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("content", content).set("lastModifiedTime", new Date());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Blog.class);
        return updateResult.getModifiedCount() > 0;
    }

    public List<BlogItemDTO> findAllVisibleBlogItemDTO() {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("status").nin("DELETED", "PRIVATE"));

        ProjectionOperation projectionOperation = Aggregation.project("title", "tags", "category", "date")
                .andExpression("substr(content, 0, 100)").as("contentAbstract");

        Aggregation aggregation = Aggregation.newAggregation(matchOperation, projectionOperation);

        return mongoTemplate.aggregate(aggregation, Blog.class, BlogItemDTO.class).getMappedResults();
    }

    public List<BlogItemDTO> findAllVisibleBlogItemDTOByCategory(String category) {
        MatchOperation matchOperation = Aggregation.match(
                Criteria.where("status").nin("DELETED", "PRIVATE")
                .and("category").is(category)
        );

        ProjectionOperation projectionOperation = Aggregation.project("title", "tags", "category", "date")
                .andExpression("substr(content, 0, 100)").as("contentAbstract");

        Aggregation aggregation = Aggregation.newAggregation(matchOperation, projectionOperation);

        return mongoTemplate.aggregate(aggregation, Blog.class, BlogItemDTO.class).getMappedResults();
    }

    public List<BlogItemDTO> findAllVisibleBlogItemDTOByTag(String tag) {
        MatchOperation matchOperation = Aggregation.match(
                Criteria.where("status").nin("DELETED", "PRIVATE")
                        .and("tags").is(tag)
        );

        ProjectionOperation projectionOperation = Aggregation.project("title", "tags", "category", "date")
                .andExpression("substr(content, 0, 100)").as("contentAbstract");

        Aggregation aggregation = Aggregation.newAggregation(matchOperation, projectionOperation);

        return mongoTemplate.aggregate(aggregation, Blog.class, BlogItemDTO.class).getMappedResults();
    }

    public List<BlogItemDTO> findAllVisibleBlogItemDTOPage(PageQueryUtil pageQueryUtil){
        MatchOperation matchOperation = Aggregation.match(Criteria.where("status").nin("DELETED", "PRIVATE"));

        return retrieveBlogItemDTOsByPage(pageQueryUtil, matchOperation);
    }


    public List<BlogItemDTO> findVisibleBlogItemDTOPageByCategory(PageQueryUtil pageQueryUtil, String category) {
        MatchOperation matchOperation = Aggregation.match(
                Criteria.where("status").nin("DELETED", "PRIVATE")
                        .and("category").is(category)
        );

        return retrieveBlogItemDTOsByPage(pageQueryUtil, matchOperation);
    }

    public List<BlogItemDTO> findVisibleBlogItemDTOPageByTag(PageQueryUtil pageQueryUtil, String tag) {
        MatchOperation matchOperation = Aggregation.match(
                Criteria.where("status").nin("DELETED", "PRIVATE")
                        .and("tags").is(tag)
        );

        return retrieveBlogItemDTOsByPage(pageQueryUtil, matchOperation);
    }

    private List<BlogItemDTO> retrieveBlogItemDTOsByPage(PageQueryUtil pageQueryUtil, MatchOperation matchOperation) {
        ProjectionOperation projectionOperation = Aggregation.project("id", "title", "tags", "category", "date")
                .andExpression("substr(content, 0, 50)").as("contentAbstract");

        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "date"));

        Aggregation aggregation = Aggregation.newAggregation(
                matchOperation,
                projectionOperation,
                sortOperation,
                Aggregation.skip(pageQueryUtil.getStart()),
                Aggregation.limit(pageQueryUtil.getLimit())
        );

        return mongoTemplate.aggregate(aggregation, Blog.class, BlogItemDTO.class).getMappedResults();
    }

    public long countVisibleBlogsByCategory(String category) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").nin("DELETED", "PRIVATE")
                .and("category").is(category)
        );
        return mongoTemplate.count(query, Blog.class);
    }

    public long countVisibleBlogsByTag(String tag) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").nin("DELETED", "PRIVATE")
                .and("tags").is(tag)
        );
        return mongoTemplate.count(query, Blog.class);
    }
}
