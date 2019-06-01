package com.example.demo.controller;

import com.example.demo.util.CommonResponseData;
import com.example.demo.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class PersonController {
    @GetMapping("/login")
    @ResponseBody
    public CommonResponseData personLogin(){
        CommonResponseData ret = new CommonResponseData();
        ret.setStatusCode(404);
        return ret;
    }

}
