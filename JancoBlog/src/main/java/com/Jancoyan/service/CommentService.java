package com.Jancoyan.service;

import com.Jancoyan.dao.ArticleCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    ArticleCommentMapper articleCommentMapper;



}
