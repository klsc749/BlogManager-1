package com.example.blogmanager.dao.visitor;

import com.example.blogmanager.model.DO.Blog;
import com.example.blogmanager.model.DO.BlogStatus;
import com.example.blogmanager.model.DO.Visibility;
import com.example.blogmanager.model.DTO.visitor.VisitorBlogDTO;
import com.example.blogmanager.model.DTO.visitor.VisitorBlogItemDTO;
import com.example.blogmanager.util.PageQueryUtil;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
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
@RequiredArgsConstructor
public class VisitorBlogDaoI implements VisitorBlogDao {
    private final MongoTemplate mongoTemplate;


    @Override
    public List<VisitorBlogItemDTO> getBlogItemsByPage(PageQueryUtil pageUtil) {
        //匹配已发布且公开的博客
        MatchOperation matchOperation = Aggregation.match(Criteria.where("status").is(BlogStatus.PUBLISHED)
                .and("visibility").is(Visibility.PUBLIC));
        return retrieveBlogItemDTOsByPage(pageUtil, matchOperation);
    }

    @Override
    public List<VisitorBlogItemDTO> getBlogItemsByPageByCollection(PageQueryUtil pageUtil, String collection) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("status").is(BlogStatus.PUBLISHED)
                .and("visibility").is(Visibility.PUBLIC)
                .and("category").is(collection));
        return retrieveBlogItemDTOsByPage(pageUtil, matchOperation);
    }

    @Override
    public long getTotalBlogs() {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(BlogStatus.PUBLISHED)
                .and("visibility").is(Visibility.PUBLIC));
        return mongoTemplate.count(query, Blog.class);
    }

    @Override
    public long getTotalBlogsByCollection(String collection) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(BlogStatus.PUBLISHED)
                .and("visibility").is(Visibility.PUBLIC)
                .and("category").is(collection));
        return mongoTemplate.count(query, Blog.class);
    }

    @Override
    public VisitorBlogDTO getBlogById(String blogId) {
        ProjectionOperation projectionOperation =
                Aggregation.project("id", "title", "tags", "content",
                        "category", "date", "lastModifiedTime", "viewCount");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("id").is(blogId)),
                projectionOperation
        );
        return mongoTemplate.aggregate(aggregation, Blog.class, VisitorBlogDTO.class).getUniqueMappedResult();
    }

    @Override
    public UpdateResult increaseBlogViewCount(String blogId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(blogId));

        Update update = new Update();
        update.inc("viewCount", 1);

        // The FindAndModifyOptions.returnNew(true) will return the updated document
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

        Blog updatedBlog = mongoTemplate.findAndModify(query, update, options, Blog.class);

        if (updatedBlog != null) {
            // The view count was successfully updated
            return UpdateResult.acknowledged(1, 1L, null);
        } else {
            // The blog was not found or the update failed
            return UpdateResult.unacknowledged();
        }

    }

    private List<VisitorBlogItemDTO> retrieveBlogItemDTOsByPage(PageQueryUtil pageQueryUtil, MatchOperation matchOperation) {
        ProjectionOperation projectionOperation =
                Aggregation.project("id", "title", "tags", "content",
                                "category", "date", "lastModifiedTime", "viewCount")
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

        return mongoTemplate.aggregate(aggregation, Blog.class,VisitorBlogItemDTO.class).getMappedResults();
    }
}
