package com.example.blogmanager.service.admin;

import com.example.blogmanager.model.DO.BlogStatus;
import com.example.blogmanager.model.DTO.BlogItemDTO;
import com.example.blogmanager.model.DTO.admin.AdminBlogDTO;
import com.example.blogmanager.model.DTO.admin.AdminBlogItemDTO;
import com.example.blogmanager.util.PageQueryUtil;
import com.example.blogmanager.util.PageResult;

import java.util.List;

public interface AdminBlogService {
    PageResult<AdminBlogItemDTO> getAdminBlogItemDTOByPage(PageQueryUtil pageQueryUtil);
    PageResult<AdminBlogItemDTO> getAdminBlogItemDTOByPageByStatus(PageQueryUtil pageQueryUtil, BlogStatus blogStatus);
    AdminBlogDTO getAdminBlogDTOById(String id);
    AdminBlogDTO saveBlog(AdminBlogDTO adminBlogDTO);
    AdminBlogDTO publishBlog(AdminBlogDTO adminBlogDTO);
    boolean updateContent(AdminBlogDTO adminBlogDTO);
    boolean softDeleteBlog(String id);
    boolean restoreBlog(String id);
    boolean deleteBlog(String id);
    boolean deleteAllSoftDeletedBlogs();
}
