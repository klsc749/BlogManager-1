package com.example.blogmanager.controller.visitor;

import com.example.blogmanager.common.Response;
import com.example.blogmanager.service.visitor.VisitorBlogService;
import com.example.blogmanager.util.PageQueryUtil;
import com.example.blogmanager.util.ResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitor")
@RequiredArgsConstructor
public class VisitorBlogController {
    private final VisitorBlogService blogService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, visitor!";
    }

    @GetMapping("/getBlogItemsByPage")
    public Response<Object> getBlogItemsByPage(@RequestParam int page, @RequestParam int limit) {
        PageQueryUtil pageUtil = new PageQueryUtil(page, limit);
        return ResponseGenerator.genSuccessResult(blogService.getBlogItemsByPage(pageUtil));
    }

    @GetMapping("/getBlogItemsByCategory")
    public Response<Object> getBlogItemsByCategory(@RequestParam int page, @RequestParam int limit, @RequestParam String category) {
        PageQueryUtil pageUtil = new PageQueryUtil(page, limit);
        return ResponseGenerator.genSuccessResult(blogService.getBlogItemsByPageByCategory(pageUtil, category));
    }

    @GetMapping("/getBlog/{blogId}")
    public Response<Object> getBlogById(@PathVariable("blogId") String blogId) {
        return ResponseGenerator.genSuccessResult(blogService.getBlogById(blogId));
    }

}
