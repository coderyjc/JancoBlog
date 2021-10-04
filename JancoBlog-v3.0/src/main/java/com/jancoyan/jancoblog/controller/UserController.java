package com.jancoyan.jancoblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.User;
import com.jancoyan.jancoblog.pojo.UserInfo;
import com.jancoyan.jancoblog.pojo.VUserTotalData;
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
import java.util.Date;
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

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ){
        User user = new User();
        String token = null;

        // 登录数据校验
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        wrapper.eq("user_password", MD5Util.getMD5(password));
        user = user.selectOne(wrapper);

        if(null != user){
            // 登录成功, 生成token
            token = JsonWebTokenUtils.createToken(user.getUserId().longValue());
            // 存到redis数据库, 设置过期时间为 60 分钟
            redisUtil.set(token, user, 1800);
            // 更新上次登录时间
            user.setUserLastLoginDate(new Date());
            user.updateById();
        }

        if(null != token){
            return Msg.success().add("token", token);
        } else {
            return Msg.fail().add("msg", "登录失败");
        }
    }


    /**
     * 用户主页的内容
     * @param userId 用户id
     * @param request request
     * @return
     */
    @RequestMapping(value = "/data/total", method = RequestMethod.GET)
    public Msg getUserTotalData(
            @RequestParam(value = "id", defaultValue = "-1")String userId,
            HttpServletRequest request
    ){
        if("-1".equals(userId)){
            // 没有提供id, 直接获取当前登录的用户
            String token = request.getHeader("token");
            if(null == token) {
                return Msg.expire();
            }
            User user = (User)redisUtil.get(token);
            userId = String.valueOf(user.getUserId());
            System.out.println(userId);
        }
        // 获取数据
        VUserTotalData data = service.getUserTotalData(userId);
        if (null != data){
            return Msg.success().add("data", data);
        } else {
            return Msg.fail().add("msg", "找不到用户");
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
        // 获取用户的信息


        return Msg.illegalToken();
    }

    /**
     * 在进入文章的时候，简要显示用户展示给别人看的信息
     * @param id 用户id
     * @return 消息
     */
    @RequestMapping(value = "/authorinfo", method = RequestMethod.GET)
    public Msg getAuthorInfo(
            @RequestParam(value = "id")String id
    ){
        return Msg.success();
    }


    /**
     * 注册
     * @param userName 姓名
     * @param password 密码
     * @param request 用来获取ip
     * @return 消息
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Msg register(
            @RequestParam("username") String userName,
            @RequestParam("password") String password,
            HttpServletRequest request
    ){
        User user = new User();
        // 设置信息
        user.setUserName(userName)
                .setUserPassword(MD5Util.getMD5(password))
                .setUserRole("user")
                .setUserCreateDate(new Date())
                .setUserIp(request.getRemoteAddr());
        // 插入
        boolean insert = user.insert();
        return Msg.success().add("success", insert);
    }

    /**
     * 用户注册的时候用来检查用户名的唯一性
     * @param userName 用户名
     * @return 消息
     */
    @RequestMapping(value = "/checkusername", method = RequestMethod.POST)
    public Msg checkUserNameUnique(
            @RequestParam(value = "username") String userName
    ){
        User user = new User();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        int count = user.selectCount(queryWrapper);
        return Msg.success().add("unique", count == 0);
    }

    /**
     * 用户退出登录
     * @return 成功
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Msg logout(HttpServletRequest request){
        String token = request.getHeader("token");
        if(null == token || "".equals(token)){
            // 登录过期
            return Msg.expire();
        }
        // 登录没过期，移除token
        redisUtil.del(token);
        return Msg.success();
    }

    /**
     * 管理员进行用户管理的时候分页获取所有用户
     * @param pn 页码
     * @param limit 容量
     * @param condition 条件
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAllUser(
            @RequestParam(value = "pn")String pn,
            @RequestParam(value = "limit", defaultValue = "10")String limit,
            @RequestParam(value = "condition", defaultValue = "")String condition
    ){
        IPage<User> iPage = service.getAll(Integer.parseInt(pn),
                Integer.parseInt(limit),
                condition);
        return Msg.success().add("pageInfo", iPage);
    }

    /**
     * 批量删除用户
     * @param ids 用户id 用 & 拼接
     * @return
     */
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

