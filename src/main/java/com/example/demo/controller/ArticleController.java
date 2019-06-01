package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.Role;
import com.example.demo.service.ArticleService;
import com.example.demo.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

//    @Secured(Role.readControl.getAccessName()) TODO 把ACCESS改成static的
    @Secured("ROLE_浏览文章")
    @RequestMapping(value = Constant.URL_ARTICLE, method = RequestMethod.GET)
    @ResponseBody
    public List<Article> getArticles() {
        return articleService.getArticles();
    }


}
