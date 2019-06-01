package com.example.demo.util;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class HTTPExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResponseData defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception{
        CommonResponseData retData = new CommonResponseData();
        retData.setMsg(e.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException ){
            retData.setStatusCode(404);
            response.setStatus(404);
        }else{
            retData.setStatusCode(500);
            response.setStatus(500);
        }
        retData.setData(null);
        return retData;
    }


}
