package com.example.blogmanager.dao.visitor;

import com.example.blogmanager.model.DO.Blog;
import com.example.blogmanager.model.DO.BlogCollection;
import com.example.blogmanager.model.DTO.visitor.VisitorBlogCollectionDTO;
import com.example.blogmanager.model.DTO.visitor.VisitorBlogDTO;
import com.example.blogmanager.util.PageQueryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VisitorBlogCollectionDaoI implements VisitorBlogCollectionDao {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<VisitorBlogCollectionDTO> getBlogCollectionByPage(PageQueryUtil pageQueryUtil) {
        ProjectionOperation projectionOperation =
                Aggregation.project("id", "title", "cover", "createDate");
        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "createDate"));
        Aggregation aggregation = Aggregation.newAggregation(
                projectionOperation
        );
        return mongoTemplate.aggregate(aggregation, BlogCollection.class, VisitorBlogCollectionDTO.class).getMappedResults();
    }



    @Override
    public long countBlogCollection() {
        return mongoTemplate.count(new Query(), BlogCollection.class);
    }


}
