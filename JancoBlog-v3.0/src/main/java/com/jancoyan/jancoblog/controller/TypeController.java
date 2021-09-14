package com.jancoyan.jancoblog.controller;


import com.jancoyan.jancoblog.service.ArticleService;
import com.jancoyan.jancoblog.service.TypeService;
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
@RequestMapping("/type")
public class TypeController {


    @Autowired
    TypeService service;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAll(){

        return Msg.success();
    }



}

