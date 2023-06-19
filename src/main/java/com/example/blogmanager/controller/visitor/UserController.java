package com.example.blogmanager.controller.visitor;

import com.example.blogmanager.common.Response;
import com.example.blogmanager.service.JWTService;
import com.example.blogmanager.util.ResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitor")
@RequiredArgsConstructor
public class UserController {
    private final JWTService jwtService;

    @GetMapping("/checkToken")
    public Response<Object> checkToken(@RequestParam("token") String token, @RequestParam("username") String username) {
        return ResponseGenerator.genSuccessResult(jwtService.checkJWT(username, token));
    }
}
