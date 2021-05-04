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
     * /login
     *  - POST 登录
     *
     * /user
     *  - POST 更新个人信息
     *
     * /user/password
     *  - POST 更新密码
     */


    /**
     * 修改密码
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param repeat 重复新密码
     * @param session session对象
     * @return 修改的结果
     */
    @ResponseBody
    @RequestMapping(value = "/user/password", method = RequestMethod.POST)
    public Msg changePassword(String oldPwd, String newPwd, String repeat,
                              HttpSession session){
        User user = (User) session.getAttribute("user");
        if (!user.getUserPwd().equals(MD5Util.getMD5(oldPwd))){
            return Msg.success().add("msg", "旧密码错误");
        } else if(!newPwd.equals(repeat)){
            return Msg.success().add("msg", "两次密码不一致");
        }
        user.setUserPwd(MD5Util.getMD5(newPwd));
        // 数据库字段更新
        userService.updateUserSelective(user);
        // 更新session中的值
        session.setAttribute("user", user);
        return Msg.success().add("msg", "修改成功");
    }


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
        user.setUserBirthday(TimeUtils.castDateStringToDateTypeYMD(userBirthdayDay));
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
