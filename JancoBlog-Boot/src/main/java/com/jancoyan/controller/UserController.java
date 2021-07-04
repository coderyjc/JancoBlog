package com.jancoyan.controller;


import ch.qos.logback.core.db.dialect.MsSQLDialect;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.pojo.User;
import com.jancoyan.service.UserService;
import com.jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
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


    // 获取所有用户
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAll(Integer page, Integer limit){
        IPage<User> iPage = userService.selectAllUserWithoutPassword(page, limit);
        return Msg.success().add("pageInfo", iPage);
    }


    // 修改信息
    @RequestMapping(value = "/user", method = RequestMethod.HEAD)
    public Msg updateUser(
            @RequestParam("userId")String userId,
            @RequestParam("userName") String userName,
            @RequestParam("userNickname") String userNickname,
            @RequestParam("userSex") String userSex,
            @RequestParam("userEmail") String userEmail,
            HttpServletRequest request
    ){
        User user = new User();
        user.setUserId(Integer.parseInt(userId));
        user = user.selectById();
        System.out.println(user);
        user.setUserIp(request.getRemoteAddr());
        user.setUserSex("0".equals(userSex) ? 0 : 1);
        user.setUserNickname(userNickname);
        user.setUserEmail(userEmail);
        user.updateById();
        return Msg.success();
    }


    // 修改密码
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Msg updateUserPassword(
            @RequestParam("id") String userId,
            @RequestParam("oldPwd") String oldPassword,
            @RequestParam("newPwd") String newPassword
    ){
        // 业务逻辑还没写好
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("user_password", oldPassword);
        User user = new User();
        User user1 = user.selectOne(wrapper);
        if(user1 == null){
            return Msg.success().add("success", 0);
        }
        user.setUserId(Integer.parseInt(userId));
        user.setUserPassword(newPassword);
        user.updateById();
        return Msg.success().add("success", 1);
    }


    // 添加会员
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public Msg addUser(
        @RequestParam("userName") String userName,
        @RequestParam("userNickname") String userNickname,
        @RequestParam("userSex") String userSex,
        @RequestParam("password") String userPassword,
        @RequestParam("userEmail") String userEmail,
        HttpServletRequest request
    ){
        // id 自动获取
        User user = new User();
        user.setUserId(userService.getMaxUserId() + 1);
        user.setUserName(userName);
        user.setUserNickname(userNickname);
        user.setUserPassword(userPassword);
        user.setUserSex(userSex.equals("0") ? 0 : 1);
        user.setUserRole(1);
        user.setUserCreateDate(new Date());
        user.setUserIp(request.getRemoteAddr());
        user.insert();
        return Msg.success();
    }

    // batch删除会员
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public Msg deleteUser(
            @RequestParam("id") String userId
    ){
        String[] ids = {userId};
        if(userId.contains("&")){
            ids = userId.split("&");
        }
        User user = new User();
        for (String id : ids) {
            user.setUserId(Integer.parseInt(id));
            user.deleteById();
        }
        return  Msg.success();
    }
}

