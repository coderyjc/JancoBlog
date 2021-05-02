package com.Jancoyan.controller;

import com.Jancoyan.domain.User;
import com.Jancoyan.service.UserService;
import com.Jancoyan.utils.MD5Util;
import com.Jancoyan.utils.Msg;
import com.Jancoyan.utils.TimeUtils;
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
     * 更新用户信息，更新之后将更新的字段重新赋值给user，【造成参数缺失！！！】
     * @param userNickName 昵称
     * @param userEmail 邮箱
     * @param userSex 性别
     * @param userBirthdayDay 生日
     * @return 成功
     */
    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Msg updateUser(String userNickName,
                          String userEmail,
                          String userSex,
                          String userBirthdayDay,
                          HttpSession session){
        User user = (User) session.getAttribute("user");
        // 设置相关信息
        user.setUserNickname(userNickName);
        user.setUserEmail(userEmail);
        user.setUserSex(Integer.valueOf(userSex));
        user.setUserBirthday(TimeUtils.castDateStringToDateType(userBirthdayDay));
        // 调用函数进行更新
        userService.updateUserSelective(user);
        // 更新完毕之后将user重新赋值
        session.setAttribute("user", user);
        // 更新成功的消息
        return Msg.success();
    }



    /**
     * 登录功能实现
     * @param user 用户
     * @param session Session
     * @return 登录状态信息
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg login(User user, HttpSession session) {
        // 对密码进行加密
        user.setUserPwd(MD5Util.getMD5(user.getUserPwd()));
        user = userService.login(user);
        if(user != null){
            session.setAttribute("user", user);
            return Msg.success().add("user", user);
        }else{
            return Msg.fail().add("user", null);
        }
    }

}
