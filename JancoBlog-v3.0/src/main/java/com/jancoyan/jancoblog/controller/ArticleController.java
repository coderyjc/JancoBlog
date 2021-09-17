package com.jancoyan.jancoblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.Article;
import com.jancoyan.jancoblog.service.ArticleService;
import com.jancoyan.jancoblog.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService service;

    /**
     * 获取首页的文章，带有搜索功能
     * @param pn 第几页
     * @param limit 每一页的大小
     * @param condition 条件
     * @return 标准的pageInfo
     */
    @RequestMapping(value = "/all")
    public Msg getAll(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition
    ){
        IPage<Article> iPage = service.getIndexList(Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    @RequestMapping(value = "/manage")
    public Msg getManageAll(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition
    ){
        IPage<Article> iPage = service.getManageList(Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Msg getUserAll(){



        return Msg.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Msg batchDeleteArticle(String ids){
        Article article = new Article();
        boolean suc = false;
        if(!ids.contains("&")){
            article.setArticleId(ids);
            suc = article.deleteById();
        } else {
            String[] id = ids.split("&");
            for (String item : id) {
                article.setArticleId(item);
                suc = article.deleteById();
            }
        }
        return Msg.success().add("suc", suc ? "success" : "fail");
    }





}

