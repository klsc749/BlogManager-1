package com.example.blogmanager.controller.admin;

import com.example.blogmanager.common.Response;
import com.example.blogmanager.model.DO.Blog;
import com.example.blogmanager.model.DO.BlogStatus;
import com.example.blogmanager.model.DTO.admin.AdminBlogDTO;
import com.example.blogmanager.service.BlogService;
import com.example.blogmanager.service.TagService;
import com.example.blogmanager.service.admin.AdminBlogServiceI;
import com.example.blogmanager.util.PageQueryUtil;
import com.example.blogmanager.util.ResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class BlogAdminController {
    private final AdminBlogServiceI adminBlogService;

    @GetMapping("/getBlogItemDTOByPage")
    public Response<Object> getBlogItemDTOByPage(@RequestParam int page, @RequestParam int limit) {
        PageQueryUtil pageQueryUtil = new PageQueryUtil(page, limit);
        return ResponseGenerator.genSuccessResult(adminBlogService.getAdminBlogItemDTOByPage(pageQueryUtil));
    }

    @GetMapping("/getBlogItemDTOByPageAndStatus")
    public Response<Object> getBlogItemDTOByPageAndStatus(@RequestParam int page, @RequestParam int limit, @RequestParam BlogStatus status) {
        PageQueryUtil pageQueryUtil = new PageQueryUtil(page, limit);
        return ResponseGenerator.genSuccessResult(adminBlogService.getAdminBlogItemDTOByPageByStatus(pageQueryUtil, status));
    }

    @GetMapping("/getBlogDTOById/{id}")
    public Response<Object> getBlogDTOById(@PathVariable String id) {
        return ResponseGenerator.genSuccessResult(adminBlogService.getAdminBlogDTOById(id));
    }

    @PostMapping("/saveBlog")
    public Response<Object> saveBlog(@RequestBody AdminBlogDTO adminBlogDTO) {
        return ResponseGenerator.genSuccessResult(adminBlogService.saveBlog(adminBlogDTO));
    }

    @PostMapping("/publishBlog")
    public Response<Object> publishBlog(@RequestBody AdminBlogDTO adminBlogDTO) {
        return ResponseGenerator.genSuccessResult(adminBlogService.publishBlog(adminBlogDTO));
    }

    @DeleteMapping ("/softDeleteBlog/{id}")
    public Response<Object> softDeleteBlog(@PathVariable String id) {
        return adminBlogService.softDeleteBlog(id) ?
                ResponseGenerator.genSuccessResult() :
                ResponseGenerator.genFailResult("Delete failed");
    }

    @PostMapping("/restoreBlog/{id}")
    public Response<Object> restoreBlog(@PathVariable String id) {
        return adminBlogService.restoreBlog(id) ?
                ResponseGenerator.genSuccessResult() :
                ResponseGenerator.genFailResult("Restore failed");
    }

    @DeleteMapping("/hardDeleteBlog/{id}")
    public Response<Object> hardDeleteBlog(@PathVariable String id) {
        return adminBlogService.deleteBlog(id) ?
                ResponseGenerator.genSuccessResult() :
                ResponseGenerator.genFailResult("Delete failed");
    }
    @PostMapping("/updateBlogContent")
    public Response<Object> updateBlogContent(@RequestBody AdminBlogDTO adminBlogDTO) {
        return adminBlogService.updateContent(adminBlogDTO) ?
                ResponseGenerator.genSuccessResult() :
                ResponseGenerator.genFailResult("Update failed");
    }
}
