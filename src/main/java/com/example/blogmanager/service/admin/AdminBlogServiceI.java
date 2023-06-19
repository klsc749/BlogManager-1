package com.example.blogmanager.service.admin;

import com.example.blogmanager.convert.AdminBlogDTOConvert;
import com.example.blogmanager.convert.BlogConvert;
import com.example.blogmanager.dao.admin.AdminBlogCollectionDao;
import com.example.blogmanager.dao.admin.AdminBlogDao;
import com.example.blogmanager.model.DO.Blog;
import com.example.blogmanager.model.DO.BlogCollection;
import com.example.blogmanager.model.DO.BlogStatus;
import com.example.blogmanager.model.DTO.admin.AdminBlogDTO;
import com.example.blogmanager.model.DTO.admin.AdminBlogItemDTO;
import com.example.blogmanager.util.PageQueryUtil;
import com.example.blogmanager.util.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBlogServiceI implements AdminBlogService{
    private final AdminBlogDao adminBlogDao;
    private final AdminBlogCollectionDao adminBlogCollectionDao;

    @Override
    public PageResult<AdminBlogItemDTO> getAdminBlogItemDTOByPage(PageQueryUtil pageQueryUtil) {
        List<AdminBlogItemDTO> blogItemDTOList = adminBlogDao.getAdminBlogItemDTO(pageQueryUtil);
        PageResult<AdminBlogItemDTO> pageResult = new PageResult<>();
        //填充数据
        pageResult.setData(blogItemDTOList);
        //设置当前页
        pageResult.setCurrentPage(pageQueryUtil.getPage());
        //设置元素总数
        pageResult.setTotalElements(adminBlogDao.countAllBlogs());
        //设置每页元素数
        pageResult.setPageSize(pageQueryUtil.getLimit());
        //设置总页数
        pageResult.setTotalPages((int) Math.ceil((double) pageResult.getTotalElements() / pageResult.getPageSize()));
        return pageResult;
    }

    @Override
    public PageResult<AdminBlogItemDTO> getAdminBlogItemDTOByPageByStatus(PageQueryUtil pageQueryUtil, BlogStatus blogStatus) {
        List<AdminBlogItemDTO> blogItemDTOList = adminBlogDao.getAdminBlogItemDTOByStatus(pageQueryUtil, blogStatus);
        PageResult<AdminBlogItemDTO> pageResult = new PageResult<>();
        //填充数据
        pageResult.setData(blogItemDTOList);
        //设置当前页
        pageResult.setCurrentPage(pageQueryUtil.getPage());
        //设置元素总数
        pageResult.setTotalElements(adminBlogDao.countAllBlogsByStatus(blogStatus));
        //设置每页元素数
        pageResult.setPageSize(pageQueryUtil.getLimit());
        //设置总页数
        pageResult.setTotalPages((int) Math.ceil((double) pageResult.getTotalElements() / pageResult.getPageSize()));
        return pageResult;
    }


    @Override
    public AdminBlogDTO getAdminBlogDTOById(String id) {
        return adminBlogDao.getAdminBlogDTOById(id);
    }

    @Override
    public AdminBlogDTO saveBlog(AdminBlogDTO adminBlogDTO) {
        Blog blog = AdminBlogDTOConvert.INSTANCE.adminBlogDTOToBlog(adminBlogDTO);
        if(!adminBlogCollectionDao.checkBlogCollectionExist(adminBlogDTO.getCategory())){
            BlogCollection blogCollection = new BlogCollection();
            blogCollection.setTitle(adminBlogDTO.getCategory());
            blogCollection.setCreateDate(new Date());
            adminBlogCollectionDao.saveBlogCollection(blogCollection);
        }
        blog.setStatus(BlogStatus.DRAFT);
        return BlogConvert.INSTANCE.blogToAdminBlogDTO(adminBlogDao.saveBlog(blog));
    }

    @Override
    public AdminBlogDTO publishBlog(AdminBlogDTO adminBlogDTO) {
        if(!adminBlogCollectionDao.checkBlogCollectionExist(adminBlogDTO.getCategory())){
            BlogCollection blogCollection = new BlogCollection();
            blogCollection.setTitle(adminBlogDTO.getCategory());
            blogCollection.setCreateDate(new Date());
            adminBlogCollectionDao.saveBlogCollection(blogCollection);
        }
        adminBlogDTO.setDate(new Date());
        adminBlogDTO.setStatus(BlogStatus.PUBLISHED);
        Blog blog = AdminBlogDTOConvert.INSTANCE.adminBlogDTOToBlog(adminBlogDTO);
        return BlogConvert.INSTANCE.blogToAdminBlogDTO(adminBlogDao.saveBlog(blog));
    }

    @Override
    public boolean updateContent(AdminBlogDTO adminBlogDTO) {
        adminBlogDTO.setLastModifiedTime(new Date());
        return adminBlogDao.updateContent(adminBlogDTO).getModifiedCount() > 0;
    }

    @Override
    public boolean softDeleteBlog(String id) {
        return adminBlogDao.changeBlogStatus(id, BlogStatus.DELETED).getModifiedCount() > 0;
    }

    @Override
    public boolean restoreBlog(String id) {
        return adminBlogDao.changeBlogStatus(id, BlogStatus.DRAFT).getModifiedCount() > 0;
    }

    @Override
    public boolean deleteBlog(String id) {
        return adminBlogDao.deleteBlog(id).getDeletedCount() > 0;
    }

    @Override
    public boolean deleteAllSoftDeletedBlogs() {
        return adminBlogDao.deleteAllSoftDeletedBlogs().getDeletedCount() > 0;
    }
}
