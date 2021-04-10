package com.Jancoyan.controller;


import com.Jancoyan.domain.Article;
import com.Jancoyan.service.ArticleService;
import com.Jancoyan.utils.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @author Jancoyan
 */
@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     *
     * @param pn 要请求的页码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public Msg getIndexArticles(
            @RequestParam(value = "pn", defaultValue = "1") Integer pn    ){
        PageHelper.startPage(pn, 5);
        List<Article> articles = articleService.getAll();
        PageInfo pageInfo = new PageInfo(articles, 5);
        return Msg.success().add("PageInfo", pageInfo);
    }

}
