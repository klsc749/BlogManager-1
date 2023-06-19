package com.example.blogmanager.service;

import com.example.blogmanager.convert.BlogConvert;
import com.example.blogmanager.dao.BlogDao;
import com.example.blogmanager.model.DO.Blog;
import com.example.blogmanager.model.DO.BlogStatus;
import com.example.blogmanager.model.DTO.BlogDTO;
import com.example.blogmanager.model.DTO.BlogItemDTO;
import com.example.blogmanager.util.PageQueryUtil;
import com.example.blogmanager.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogService {
    private final BlogDao blogDao;

    @Autowired
    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public Blog addBlog(Blog blog) {
        blog.setDate(new Date());
        blog.setLastModifiedTime(new Date());
        return blogDao.save(blog);
    }
    public Blog saveBlog(Blog blog) {
        Blog oldBlog = blogDao.findById(blog.getId());
        blog.setDate(oldBlog.getDate());
        blog.setViewCount(oldBlog.getViewCount());
        blog.setLastModifiedTime(new Date());
        return blogDao.save(blog);
    }

    public Blog publishBlog(Blog blog) {
        blog.setDate(new Date());
        return blogDao.save(blog);
    }

    public List<BlogItemDTO> getAllBlogItemDTO() {
        return blogDao.getAllBlogItemDTO();
    }

    public BlogDTO getBlogById(String id){
        return BlogConvert.INSTANCE.blogToBlogDTO(blogDao.findById(id));
    }

    public boolean softDeleteBlogById(String id){
        return blogDao.updateBlogStatus(id, BlogStatus.DELETED);
    }

    public boolean restoreBlogById(String id){
        return blogDao.updateBlogStatus(id, BlogStatus.DELETED);
    }

    public void hardDeleteBlogById(String id){
        blogDao.deleteById(id);
    }

    public Blog updateBlog(Blog blog){
        blog.setLastModifiedTime(new Date());
        return blogDao.save(blog);
    }

    public boolean increaseViewCount(String id){
        return blogDao.increaseViewCount(id);
    }

    public Boolean updateBlogContent(String id, String content){

        return blogDao.updateBlogContent(id, content);
    }

    public List<BlogItemDTO> getBlogItemDTOByTag(String tag){
        return blogDao.findAllVisibleBlogItemDTOByTag(tag);
    }

    public List<BlogItemDTO> getVisibleBlogItemDTOByCategory(String category){
        return blogDao.findAllVisibleBlogItemDTOByCategory(category);
    }

    public PageResult<BlogItemDTO> getVisibleBlogItemDTOPage(PageQueryUtil pageQueryUtil) {
        List<BlogItemDTO> blogItemDTOS = blogDao.findAllVisibleBlogItemDTOPage(pageQueryUtil);
        long total = blogDao.countVisibleBlogs();
        int totalPage = (int) (total / pageQueryUtil.getLimit());
        return new PageResult<>(blogItemDTOS, pageQueryUtil.getPage(), pageQueryUtil.getLimit(), total, totalPage);
    }

    public PageResult<BlogItemDTO> getVisibleBlogItemDTOPageByCategory(PageQueryUtil pageQueryUtil, String category) {
        List<BlogItemDTO> blogItemDTOS = blogDao.findVisibleBlogItemDTOPageByCategory(pageQueryUtil, category);
        long total = blogDao.countVisibleBlogsByCategory(category);
        int totalPage = (int) (total / pageQueryUtil.getLimit());
        return new PageResult<>(blogItemDTOS, pageQueryUtil.getPage(), pageQueryUtil.getLimit(), total, totalPage);
    }

    public PageResult<BlogItemDTO> getVisibleBlogItemDTOPageByTag(PageQueryUtil pageQueryUtil, String tag) {
        List<BlogItemDTO> blogItemDTOS = blogDao.findVisibleBlogItemDTOPageByTag(pageQueryUtil, tag);
        long total = blogDao.countVisibleBlogsByTag(tag);
        int totalPage = (int) (total / pageQueryUtil.getLimit());
        return new PageResult<>(blogItemDTOS, pageQueryUtil.getPage(), pageQueryUtil.getLimit(), total, totalPage);
    }
}
