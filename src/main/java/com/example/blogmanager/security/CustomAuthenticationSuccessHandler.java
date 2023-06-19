package com.example.blogmanager.security;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.blogmanager.common.Response;
import com.example.blogmanager.model.DO.User;
import com.example.blogmanager.service.JWTService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bson.json.JsonObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JWTService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Response<Object> res = new Response<>(200, "Login Success", null);
        String username = authentication.getName();
        String token = jwtService.saveJWT(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        res.setData(jsonObject);
        response.getWriter().write(JSON.toJSONString(res));
    }
}
