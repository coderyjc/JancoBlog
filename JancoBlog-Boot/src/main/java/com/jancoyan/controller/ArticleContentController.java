package com.jancoyan.controller;


import com.jancoyan.pojo.Article;
import com.jancoyan.pojo.ArticleContent;
import com.jancoyan.service.ArticleContentService;
import com.jancoyan.service.ArticleService;
import com.jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.cs.ext.MS874;

/**
 * @author Jancoyan
 * @since 2021-07-01
 */
@RestController
@RequestMapping("/content")
public class ArticleContentController {

    @Autowired
    ArticleContentService contentService;

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Msg getArticle(
            @PathVariable(value = "id") String articleId
    ){
        // 获取内容
        ArticleContent content = new ArticleContent();
        content.setArticleId(articleId);
        content = content.selectById();
        Article article = articleService.selectByPrimaryKeyWithAuthorName(articleId);
        return Msg.success().add("content", content.getArticleHtml()).add("article", article);
    }

}

