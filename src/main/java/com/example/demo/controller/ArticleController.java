package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.Role;
import com.example.demo.service.ArticleService;
import com.example.demo.util.CommonResponseData;
import com.example.demo.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

//    @Secured(Role.readControl.getAccessName()) TODO 把ACCESS改成static的
    @Secured("ROLE_浏览文章")
    @RequestMapping(value = Constant.URL_ARTICLE, method = RequestMethod.GET)
    @ResponseBody
    public CommonResponseData getArticles() {
        CommonResponseData ret = new CommonResponseData();
        ret.setData(articleService.getArticles());
        ret.setStatusCode(200);
        ret.setMsg("文章获取成功");
        return ret;
    }

    @Secured("ROLE_浏览文章")
    @GetMapping(Constant.URL_ARTICLE+"/{articleID}")
    @ResponseBody
    public CommonResponseData getArticle(@PathVariable int articleID){
        CommonResponseData ret = new CommonResponseData();
        ret.setData(articleService.getArticle(articleID));
        ret.setStatusCode(200);
        ret.setMsg("文章读取成功");
        return ret;
    }

    @Secured("ROLE_添加文章")
    @PostMapping(Constant.URL_ARTICLE)
    @ResponseBody
    public CommonResponseData addArticles(@RequestBody Map<String,Object> jsonMap){
        Article newart = new Article();
        newart.setArticleTitle((String)jsonMap.get("title"));
        newart.setArticleEditor((String)jsonMap.get("editorName"));
        newart.setArticleContent((String)jsonMap.get("content"));
        newart.setPersonId((Integer)jsonMap.get("personId"));
        List<Integer> channelIds = (List<Integer>)jsonMap.get("channelIds");

        CommonResponseData ret = new CommonResponseData();
        ret.setData(articleService.addArticles(newart,channelIds));
        ret.setMsg("添加成功");
        ret.setStatusCode(200);
        return ret;
    }

    @Secured("ROLE_编辑文章")
    @PatchMapping(Constant.URL_ARTICLE)
    @ResponseBody
    public CommonResponseData changeArticles(@RequestBody Map<String,Object> jsonMap){

        Article newart = new Article();
        newart.setArticleTitle((String)jsonMap.get("title"));
        newart.setArticleEditor((String)jsonMap.get("editorName"));
        newart.setArticleContent((String)jsonMap.get("content"));
        newart.setPersonId((Integer)jsonMap.get("personId"));
        newart.setArticleId((Integer)jsonMap.get("changeArticleId"));
        List<Integer> channelIds = (List<Integer>)jsonMap.get("channelIds");

        CommonResponseData ret = new CommonResponseData();
        articleService.updateArticle(newart,channelIds);
//        ret.setData(articleService.addArticles(newart,channelIds));
        ret.setMsg("修改成功");
        ret.setStatusCode(200);
        return ret;
    }

    @Secured("ROLE_删除文章")
    @DeleteMapping(Constant.URL_ARTICLE+"/{articleID}")
    @ResponseBody
    public CommonResponseData delArticle(@PathVariable int articleID){
        CommonResponseData ret = new CommonResponseData();
        articleService.delArticle(articleID);
        ret.setMsg("删除成功");
        ret.setStatusCode(200);
        return ret;
    }
}
