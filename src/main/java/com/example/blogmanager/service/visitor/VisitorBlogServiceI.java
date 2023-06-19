package com.example.blogmanager.service.visitor;

import com.example.blogmanager.dao.visitor.VisitorBlogDao;
import com.example.blogmanager.model.DTO.visitor.VisitorBlogDTO;
import com.example.blogmanager.model.DTO.visitor.VisitorBlogItemDTO;
import com.example.blogmanager.util.PageQueryUtil;
import com.example.blogmanager.util.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorBlogServiceI implements VisitorBlogService{
    private final VisitorBlogDao visitorBlogDao;
    @Override
    public PageResult<VisitorBlogItemDTO> getBlogItemsByPage(PageQueryUtil pageUtil) {
        List<VisitorBlogItemDTO> blogItemDTOS = visitorBlogDao.getBlogItemsByPage(pageUtil);
        PageResult<VisitorBlogItemDTO> pageResult = new PageResult<>();
        pageResult.setData(blogItemDTOS);
        pageResult.setCurrentPage(pageUtil.getPage());
        pageResult.setTotalElements(visitorBlogDao.getTotalBlogs());
        pageResult.setPageSize(pageUtil.getLimit());
        pageResult.setTotalPages((int) Math.ceil((double) pageResult.getTotalElements() / pageResult.getPageSize()));
        return pageResult;
    }

    @Override
    public PageResult<VisitorBlogItemDTO> getBlogItemsByPageByCategory(PageQueryUtil pageUtil, String collection) {
        List<VisitorBlogItemDTO> blogItemDTOS = visitorBlogDao.getBlogItemsByPageByCollection(pageUtil, collection);
        PageResult<VisitorBlogItemDTO> pageResult = new PageResult<>();
        pageResult.setData(blogItemDTOS);
        pageResult.setCurrentPage(pageUtil.getPage());
        pageResult.setTotalElements(visitorBlogDao.getTotalBlogsByCollection(collection));
        pageResult.setPageSize(pageUtil.getLimit());
        pageResult.setTotalPages((int) Math.ceil((double) pageResult.getTotalElements() / pageResult.getPageSize()));
        return pageResult;
    }

    @Override
    public VisitorBlogDTO getBlogById(String blogId) {
        visitorBlogDao.increaseBlogViewCount(blogId);
        return visitorBlogDao.getBlogById(blogId);
    }
}
