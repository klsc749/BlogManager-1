package com.example.blogmanager.dao.admin;

import com.example.blogmanager.model.DO.Blog;
import com.example.blogmanager.model.DO.BlogStatus;
import com.example.blogmanager.model.DO.Visibility;
import com.example.blogmanager.model.DTO.admin.AdminBlogDTO;
import com.example.blogmanager.model.DTO.admin.AdminBlogItemDTO;
import com.example.blogmanager.util.PageQueryUtil;
import com.mongodb.client.result.DeleteResult;
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

import java.util.List;

@Repository
public class AdminBlogDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<AdminBlogItemDTO> getAdminBlogItemDTO(PageQueryUtil pageQueryUtil) {
        return retrieveBlogItemDTOsByPage(pageQueryUtil, null);
    }

    public List<AdminBlogItemDTO> getAdminBlogItemDTOByStatus(PageQueryUtil pageQueryUtil, BlogStatus blogStatus) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("status").is(blogStatus));
        return retrieveBlogItemDTOsByPage(pageQueryUtil, matchOperation);
    }

    private List<AdminBlogItemDTO> retrieveBlogItemDTOsByPage(PageQueryUtil pageQueryUtil, MatchOperation matchOperation) {
        ProjectionOperation projectionOperation =
                Aggregation.project("id", "title", "tags", "content",
                                "category", "date", "status",
                                "lastModifiedTime", "visibility", "viewCount")
                .andExpression("substr(content, 0, 75)").as("contentAbstract");

        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "date"));

        Aggregation aggregation = matchOperation == null ? Aggregation.newAggregation(
                projectionOperation,
                sortOperation,
                Aggregation.skip(pageQueryUtil.getStart()),
                Aggregation.limit(pageQueryUtil.getLimit())
        ) : Aggregation.newAggregation(
                matchOperation,
                projectionOperation,
                sortOperation,
                Aggregation.skip(pageQueryUtil.getStart()),
                Aggregation.limit(pageQueryUtil.getLimit())
        );

        return mongoTemplate.aggregate(aggregation, Blog.class, AdminBlogItemDTO.class).getMappedResults();
    }

    public long countAllBlogs() {
        return mongoTemplate.count(new Query(), Blog.class);
    }

    public long countAllBlogsByStatus(BlogStatus blogStatus) {
        return mongoTemplate.count(new Query(Criteria.where("status").is(blogStatus)), Blog.class);
    }

    public AdminBlogDTO getAdminBlogDTOById(String id) {
        ProjectionOperation projectionOperation =
                Aggregation.project("id", "title", "tags", "content",
                                "category", "date", "status",
                        "lastModifiedTime", "visibility", "viewCount");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("id").is(id)),
                projectionOperation
        );
        return mongoTemplate.aggregate(aggregation, Blog.class, AdminBlogDTO.class).getUniqueMappedResult();
    }

    public Blog saveBlog(Blog blog) {
        blog.setId(blog.getId() == null || blog.getId().isEmpty() ? null : blog.getId());
        return mongoTemplate.save(blog);
    }

    public UpdateResult changeBlogStatus(String id, BlogStatus blogStatus){
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("status", blogStatus);
        return mongoTemplate.updateFirst(query, update, Blog.class);
    }

    public UpdateResult updateContent(AdminBlogDTO adminBlogDTO) {
        Query query = new Query(Criteria.where("id").is(adminBlogDTO.getId()));
        Update update = new Update();
        update.set("content", adminBlogDTO.getContent());
        update.set("lastModifiedTime", adminBlogDTO.getLastModifiedTime());
        return mongoTemplate.updateFirst(query, update, Blog.class);
    }

    public DeleteResult deleteBlog(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Blog.class);
        return mongoTemplate.remove(query, Blog.class);
    }

    public DeleteResult deleteAllSoftDeletedBlogs() {
        Query query = new Query(Criteria.where("status").is(BlogStatus.PUBLISHED));
        return mongoTemplate.remove(query, Blog.class);
    }
}
