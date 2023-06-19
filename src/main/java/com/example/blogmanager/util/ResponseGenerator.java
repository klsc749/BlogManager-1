package com.example.blogmanager.util;
import com.example.blogmanager.common.Response;
import org.springframework.util.StringUtils;

public class ResponseGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    private static final int RESULT_CODE_SUCCESS = 200;
    private static final int RESULT_CODE_SERVER_ERROR = 500;

    public static Response<Object> genSuccessResult() {
        Response<Object> response = new Response<>();
        response.setCode(RESULT_CODE_SUCCESS);
        response.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return response;
    }

    public static Response<Object> genSuccessResult(String message) {
        Response<Object> response = new Response<>();
        response.setCode(RESULT_CODE_SUCCESS);
        response.setMessage(message);
        return response;
    }

    public static Response<Object> genSuccessResult(Object data) {
        Response<Object> response = new Response<>();
        response.setCode(RESULT_CODE_SUCCESS);
        response.setMessage(DEFAULT_SUCCESS_MESSAGE);
        response.setData(data);
        return response;
    }

    public static Response<Object> genFailResult(String message) {
        Response<Object> response = new Response<>();
        response.setCode(RESULT_CODE_SERVER_ERROR);
        if (!StringUtils.hasText(message)) {
            response.setMessage(DEFAULT_FAIL_MESSAGE);
        } else {
            response.setMessage(message);
        }
        return response;
    }

    public static Response<Object> genFailResult(Object data) {
        Response<Object> response = new Response<>();
        response.setMessage(DEFAULT_FAIL_MESSAGE);
        response.setData(data);
        return response;
    }

    public static Response<Object> genFailResult(int code, String message) {
        Response<Object> response = new Response<>();
        response.setMessage(message);
        response.setCode(code);
        return response;
    }

    public static Response<Object> genErrorResult(int code, String message) {
        Response<Object> response = new Response<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}
