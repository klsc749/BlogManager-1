package com.example.blogmanager.controller.visitor;

import com.example.blogmanager.common.Response;
import com.example.blogmanager.service.visitor.VisitorBlogCollectionService;
import com.example.blogmanager.util.PageQueryUtil;
import com.example.blogmanager.util.ResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitor")
@RequiredArgsConstructor
public class VisitorBlogCollectionController {
    private final VisitorBlogCollectionService visitorBlogCollectionService;

    @GetMapping("/getBlogCollectionByPage")
    public Response<Object> getBlogCollectionByPage(@RequestParam("page") Integer page,@RequestParam("limit") Integer limit){
        return ResponseGenerator.genSuccessResult(visitorBlogCollectionService.getBlogCollectionByPage(new PageQueryUtil(page, limit)));
    }
}
