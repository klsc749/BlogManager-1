package com.example.blogmanager.controller.admin;

import com.example.blogmanager.common.Response;
import com.example.blogmanager.model.DO.BlogCollection;
import com.example.blogmanager.service.admin.AdminBlogCollectionService;
import com.example.blogmanager.util.ResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBlogCollectionController {
    private final AdminBlogCollectionService adminBlogCollectionService;

    @PostMapping("/updateBlogCollection")
    public Response<Object> updateBlogCollection(@RequestBody BlogCollection blogCollection) {
        return ResponseGenerator.genSuccessResult(adminBlogCollectionService.modifyBlogCollection(blogCollection));
    }

    @PostMapping("/createBlogCollection")
    public Response<Object> createBlogCollection(@RequestBody BlogCollection blogCollection) {
        return ResponseGenerator.genSuccessResult(adminBlogCollectionService.createBlogCollection(blogCollection));
    }

    @DeleteMapping("/deleteBlogCollection/{id}")
    public Response<Object> deleteBlogCollection(@PathVariable("id") String id) {
        return adminBlogCollectionService.deleteBlogCollection(id)
                ? ResponseGenerator.genSuccessResult() : ResponseGenerator.genFailResult("删除失败");
    }
}
