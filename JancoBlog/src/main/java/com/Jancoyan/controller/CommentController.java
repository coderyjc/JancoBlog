package com.Jancoyan.controller;

import com.Jancoyan.domain.ArticleComment;
import com.Jancoyan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    public List<ArticleComment> getCommentByArticleId(){
        
    }

}
