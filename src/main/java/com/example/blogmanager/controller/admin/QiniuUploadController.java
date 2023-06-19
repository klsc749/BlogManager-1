package com.example.blogmanager.controller.admin;

import com.example.blogmanager.common.Response;
import com.example.blogmanager.service.QiniuService;
import com.example.blogmanager.util.ResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QiniuUploadController {
    private final QiniuService qiniuService;
    @GetMapping("/uploadToken")
    public Response<Object> getUploadToken() {
        return ResponseGenerator.genSuccessResult((Object) qiniuService.getUploadToken());
    }
}
