package com.jancoyan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.pojo.Article;
import com.jancoyan.pojo.User;
import com.jancoyan.service.ArticleService;
import com.jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/submit", method = RequestMethod.PUT)
    public Msg postBlog(
        @RequestParam("innerHTML") String innerHTML,
        @RequestParam("innerMD") String innerMD,
        @RequestParam("title") String title,
        @RequestParam("types") String[] types,
        HttpSession session
    ){
        System.out.println(innerHTML);
        System.out.println(innerMD);
        System.out.println(title);
        System.out.println(types[0]);
        return Msg.success();
    }


    /**
     * 获取所有首页文章
     * @param pn 第几页
     * @return 消息
     */
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public Msg selectArticleById(
            @RequestParam(value = "pn")Integer pn
    ){
        IPage<Article> page = articleService.selectAllByPage(pn, 10);
        return Msg.success().add("pageInfo", page);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAll(Integer page, Integer limit){
        IPage<Article> iPage = articleService.selectAllByPage(page, limit);
        return Msg.success().add("pageInfo", iPage);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Msg getUserAll(
            Integer page, Integer limit,
            HttpSession session
    ){
        User currUser = (User)session.getAttribute("user");
        IPage<Article> iPage = articleService.selectUserArticleByPage(page, limit, String.valueOf(currUser.getUserId()));
        return Msg.success().add("pageInfo", iPage);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Msg searchArticles(
            @RequestParam("keyword") String keyword
    ){
        Article article = new Article();
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("article_title", keyword);
        List<Article> list = article.selectList(queryWrapper);
        return Msg.success().add("pageInfo", list);
    }

    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public Msg getArticleRankByView(){
        List<Article> list = articleService.getArticleRankByView();
        return Msg.success().add("viewRank", list);
    }

    @RequestMapping(value = "/rank", method = RequestMethod.POST)
    public Msg getArticleRankByLike(){
        List<Article> list = articleService.getArticleRankByLike();
        return Msg.success().add("likeRank", list);
    }

    @RequestMapping(value = "/rank", method = RequestMethod.PUT)
    public Msg getArticleRankByComment(){
        List<Article> list = articleService.getArticleRankByComment();
        return Msg.success().add("commentRank", list);
    }



}
