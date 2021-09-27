package com.jancoyan.jancoblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.User;
import com.jancoyan.jancoblog.service.UserService;
import com.jancoyan.jancoblog.utils.JsonWebTokenUtils;
import com.jancoyan.jancoblog.utils.MD5Util;
import com.jancoyan.jancoblog.utils.Msg;
import com.jancoyan.jancoblog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ){
        User user = new User();
        String token = null;

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        wrapper.eq("user_password", MD5Util.getMD5(password));
        user = user.selectOne(wrapper);

        if(null != user){
            // 登录成功, 生成token
            token = JsonWebTokenUtils.createToken(user.getUserId().longValue());
            // 存到redis数据库, 设置过期时间为 60 分钟
            redisUtil.set(token, user, 1800);
        }

        if(null != token){
            return Msg.success().add("token", token);
        } else {
            return Msg.fail().add("msg", "登录失败");
        }
    }

    /**
     * 每一次要获取信息的时候都会发送一次这个
     * @return 带有用户消息的信息
     */
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public Msg getUserInfo(HttpServletRequest request){
        // 把token放在请求头中
        String token = request.getHeader("token");
        Object user = redisUtil.get(token);
        if(null != user){
            // 获取用户成功
            return Msg.success().add("user", user);
        }
        return Msg.illegalToken();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Msg register(
            @RequestParam("username") String userName,
            @RequestParam("password") String password,
            HttpServletRequest request
    ){
        User user = new User();


        return Msg.success();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Msg logout(){


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

