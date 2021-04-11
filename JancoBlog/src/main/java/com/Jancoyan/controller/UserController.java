package com.Jancoyan.controller;

import com.Jancoyan.domain.User;
import com.Jancoyan.service.UserService;
import com.Jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author Jancoyan
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 登录功能实现
     * @param user 用户
     * @param session Session
     * @return 登录状态信息
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg login(User user, HttpSession session) {
        if(userService.login(user)){
            session.setAttribute("userName", user.getUserName());
            return Msg.success().add("userName", user.getUserName());
        }else{
            return Msg.fail().add("userName", null);
        }
    }

}
