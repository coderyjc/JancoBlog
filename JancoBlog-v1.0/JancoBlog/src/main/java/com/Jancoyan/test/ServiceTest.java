package com.Jancoyan.test;


import com.Jancoyan.domain.Article;
import com.Jancoyan.service.ArticleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    public void searchTest(){
        List<Article> articles = articleService.getArticlesLikeName("a");
        for(Article article : articles){
            System.out.println(article);
        }
    }

}
