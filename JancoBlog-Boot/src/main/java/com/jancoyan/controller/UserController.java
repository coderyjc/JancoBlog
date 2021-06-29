package com.jancoyan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jancoyan.pojo.User;
import com.jancoyan.service.UserService;
import com.jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Msg login(
            @RequestParam("username") String userName,
            @RequestParam("password") String userPassword,
            HttpSession session){

        // 使用ActiveRecord进行查找
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        wrapper.eq("user_password", userPassword);
        User user = new User();
        user = user.selectOne(wrapper);
        // 登录成功之后应该有用户的session
        if (null != user){
            session.setAttribute("user", user);
        }
        return Msg.success().add("user", user);
    }


}

