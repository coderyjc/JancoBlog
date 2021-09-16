package com.jancoyan.jancoblog.controller;


import com.jancoyan.jancoblog.service.CommentService;
import com.jancoyan.jancoblog.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService service;

    @RequestMapping(value = "/all")
    public Msg getAll(){


        return Msg.success();
    }

    @RequestMapping(value = "/article")
    public Msg getCommentByArticle(){

        return Msg.success();
    }


    @RequestMapping(value = "/user")
    public Msg getCommentByUser(){

        return Msg.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Msg deleteComment(){


        return Msg.success();
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Msg postComment(){



        return Msg.success();
    }


}

