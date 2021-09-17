package com.jancoyan.jancoblog.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.Article;
import com.jancoyan.jancoblog.pojo.Comment;
import com.jancoyan.jancoblog.pojo.User;
import com.jancoyan.jancoblog.service.UserService;
import com.jancoyan.jancoblog.utils.Msg;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Msg register(){



        return Msg.success();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAll(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition
    ){
        IPage<User> iPage = service.getAll(Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Msg batchDelte(
            @RequestParam(value = "ids") String ids
    ){
        User user = new User();
        boolean suc = false;
        if(!ids.contains("&")){
            user.setUserId(Integer.parseInt(ids));
            suc = user.deleteById();
        } else {
            String[] id = ids.split("&");
            for (String item : id) {
                user.setUserId(Integer.parseInt(item));
                suc = user.deleteById();
            }
        }
        return Msg.success().add("suc", suc ? "success" : "fail");
    }


}

