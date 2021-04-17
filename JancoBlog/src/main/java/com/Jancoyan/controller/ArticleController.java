package com.Jancoyan.controller;


import com.Jancoyan.domain.Article;
import com.Jancoyan.service.ArticleService;
import com.Jancoyan.utils.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Jancoyan
 */
@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * 获取索引页的初始文章
     * @param pn 要请求的页码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public Msg getIndexArticles(
            @RequestParam(value = "pn", defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5);
        List<Article> articles = articleService.getAll();
        PageInfo pageInfo = new PageInfo(articles, 5);
        return Msg.success().add("PageInfo", pageInfo);
    }

    /**
     * 根据用户id获取用户的所有文章
     * @param pn 第几页
     * @param id 用户的id是多少
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/articles/{id}", method = RequestMethod.GET)
    public Msg getArticlesById(
            @RequestParam(value = "pn", defaultValue = "1") Integer pn,
            @PathVariable("id") Integer id
    ){
        PageHelper.startPage(pn, 10);
        List<Article> articles = articleService.getArticlesByUserId(id);
        PageInfo pageInfo = new PageInfo(articles, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 根据文章的类型和页码获取文章
     * @param id
     * @param pn
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/articles/type/{id}", method = RequestMethod.GET)
    public Msg getArticlesByType(
        @PathVariable("id") Integer id,
        @RequestParam(value = "pn", defaultValue = "1") Integer pn
    ){
        PageHelper.startPage(pn, 10);
        List<Article> articles = articleService.getArticlesByTypeId(id);
        PageInfo pageInfo = new PageInfo(articles, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }


}
