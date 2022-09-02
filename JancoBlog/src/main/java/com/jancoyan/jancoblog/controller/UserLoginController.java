package com.jancoyan.jancoblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.model.domain.User;
import com.jancoyan.jancoblog.model.domain.UserLogin;
import com.jancoyan.jancoblog.service.UserLoginService;
import com.jancoyan.jancoblog.utils.Msg;
import com.jancoyan.jancoblog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-15
 */
@RestController
@RequestMapping("/login")
public class UserLoginController {

    @Autowired
    UserLoginService service;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 查询特定用户的登录记录，未指定id就是查找当前用户的登录记录
     * 只能查找最近10次的
     * @param userId 用户id
     * @param request
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAll(
            @RequestParam(value = "id", defaultValue = "") String userId,
            HttpServletRequest request
    ){
        String token = request.getHeader("token");
        if(null == token){
            return Msg.fail().add("msg", "用户验证失败");
        }
        User user = (User) redisUtil.get(token);
        if(null == user){
            return Msg.expire();
        }

        // 获取10条用户登录信息
        UserLogin userLogin = new UserLogin();
        // 构造Wrapper
        QueryWrapper<UserLogin> wrapper = new QueryWrapper<>();
        wrapper.eq("login_user", user.getUserId());
        wrapper.orderByDesc("login_date");
        // 构造Page
        IPage<UserLogin> iPage = new Page<>(1, 10);
        // 查询
        iPage =  userLogin.selectPage(iPage, wrapper);
        return Msg.success().add("pageInfo", iPage);
    }



}

