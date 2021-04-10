package com.Jancoyan.service;


import com.Jancoyan.dao.ArticleMapper;
import com.Jancoyan.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jancoyan
 */
@Service
public class ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    public List<Article> getAll() {
        return articleMapper.selectByExample(null);
    }
}
