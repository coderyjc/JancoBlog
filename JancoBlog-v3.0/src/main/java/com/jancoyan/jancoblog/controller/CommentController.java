package com.jancoyan.jancoblog.controller;


import com.jancoyan.jancoblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService service;


}

