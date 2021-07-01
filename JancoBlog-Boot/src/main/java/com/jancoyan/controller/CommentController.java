package com.jancoyan.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.pojo.Article;
import com.jancoyan.pojo.Comment;
import com.jancoyan.service.CommentService;
import com.jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping("/all")
    public Msg getAll(Integer page, Integer limit){
        IPage<Comment> iPage = commentService.selectAllByPageWithArticleName(page, limit);
        return Msg.success().add("pageInfo", iPage);
    }



}

