package com.jancoyan.jancoblog.controller;


import com.jancoyan.jancoblog.service.UserService;
import com.jancoyan.jancoblog.utils.Msg;
import lombok.experimental.Accessors;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg login(){

        return Msg.success();
    }

    @RequestMapping(value = "/getuserinfo", method = RequestMethod.GET)
    public Msg getUserInfo(){

        return Msg.success();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAll(){

        return Msg.success();
    }
}

