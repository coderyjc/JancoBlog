package com.jancoyan.controller;


import com.jancoyan.pojo.ArticleContent;
import com.jancoyan.service.ArticleContentService;
import com.jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import sun.nio.cs.ext.MS874;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jancoyan
 * @since 2021-07-01
 */
@Controller
@RequestMapping("/content")
public class ArticleContentController {

    @Autowired
    ArticleContentService contentService;

    @RequestMapping(value = "/{id}")
    public Msg getArticle(
            @PathVariable(value = "id") String articleId
    ){
        ArticleContent content = new ArticleContent();
        content.setArticleId(articleId);
        content = content.selectById();
        return Msg.success().add("content", content.getArticleHtml());
    }

}

