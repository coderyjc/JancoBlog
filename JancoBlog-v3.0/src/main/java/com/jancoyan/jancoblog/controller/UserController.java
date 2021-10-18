package com.jancoyan.jancoblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.User;
import com.jancoyan.jancoblog.pojo.UserInfo;
import com.jancoyan.jancoblog.pojo.UserLogin;
import com.jancoyan.jancoblog.pojo.VUserTotalData;
import com.jancoyan.jancoblog.service.UserService;
import com.jancoyan.jancoblog.utils.*;
import io.lettuce.core.output.ScanOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;

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
            @RequestParam(value = "password") String password,
            HttpServletRequest request
    ){
        String token = null;

        // 从视图选择登录
        User user = service.login(username, password);

        if(null != user){
            // 登录成功, 生成token
            token = JsonWebTokenUtils.createToken(user.getUserId().longValue());
            // 存到redis数据库, 设置过期时间为 60 分钟
            redisUtil.set(token, user, 30 * 60);
            // 更新上次登录时间
            user.setUserLastLoginDate(new Date());
            user.updateById();
        }

        //登录记录
        UserLogin log = new UserLogin();
        String userAgent = request.getHeader("User-Agent");
        log.setLoginUser(user.getUserId());
        log.setLoginDate(new Date());
        log.setLoginAddress(NetworkUtils.queryAddressByIp(request.getRemoteAddr()));
        log.setLoginIp(request.getRemoteAddr());
        log.setUserAgent(userAgent);
        log.setBrowserName(UserAgentUtils.getBorderName(userAgent));
        log.setBrowserVersion(UserAgentUtils.getBrowserVersion(userAgent));
        log.setOsName(UserAgentUtils.getOsName(userAgent));
        log.setOsVersion(UserAgentUtils.getOsVersion(userAgent));

        if(null != token){
            //插入用户登录信息
            log.insert();
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
                return Msg.fail();
            }
            User user = (User)redisUtil.get(token);
            userId = String.valueOf(user.getUserId());
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
        User user = (User)redisUtil.get(token);
        if(null != user){
            // 获取用户成功
            return Msg.success().add("user", user);
        }
        return Msg.fail().add("msg", "找不到用户");
    }


    /**
     * 获取当前已经登录的用户的详细信息
     * @param request req
     * @return
     */
    @RequestMapping(value = "/userdetail", method = RequestMethod.GET)
    public Msg getUserDetailInfo(
            HttpServletRequest request
    ){
        // 把token放在请求头中
        String token = request.getHeader("token");
        User user = (User)redisUtil.get(token);
        if(null == user){
            // 获取用户失败
            return Msg.expire().add("msg", "用户已过期");
        }
        // 获取用户的信息
        UserInfo info = service.getUserInfo(user.getUserId());
        if(null != info){
            return Msg.success().add("info", info);
        } else {
            return Msg.fail();
        }
    }



    /**
     * 在进入文章的时候，简要显示用户展示给别人看的信息
     * @param userId 用户id
     * @return 消息
     */
    @RequestMapping(value = "/authorinfo", method = RequestMethod.GET)
    public Msg getAuthorInfo(
            @RequestParam(value = "id")String userId
    ){
        VUserTotalData data = service.getUserTotalData(userId);
        if(null != data) {
            return Msg.success().add("data", data);
        } else {
            return Msg.fail().add("msg", "获取数据失败");
        }
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
            @RequestParam(value = "username") String userName,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "code") String code,
            HttpServletRequest request
    ){
        String verify = (String) request.getSession().getAttribute(VerifyCodeUtil.RANDOMCODEKEY);

        if(!verify.toLowerCase(Locale.ROOT).equals(code.toLowerCase(Locale.ROOT))){
            return Msg.fail().add("msg", "验证码输入错误");
        }

        User user = new User();
        // 设置信息
        user.setUserName(userName)
                .setUserLastLoginDate(null)
                .setUserId(null)
                .setUserSignature("Hello World")
                .setUserPassword(MD5Util.getMD5(password))
                .setUserRole(2)
                .setUserCreateDate(new Date())
                .setUserIp(request.getRemoteAddr());
        // 插入
        boolean insert = user.insert();
        if(!insert){
            return Msg.fail().add("msg", "用户注册失败");
        }
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
            return Msg.fail();
        }
        // 登录没过期，移除token
        redisUtil.del(token);
        return Msg.success().add("suc", true);
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

    /**
     * 更换头像
     * @return
     */
    @RequestMapping(value = "/upload/avatar", method = RequestMethod.POST)
    public Msg uploadAvatar(
            @RequestParam(value = "file") MultipartFile file,
            HttpServletRequest request
    ){
//        if (file == null) {
//            return Msg.fail().add("msg", "请选择要上传的图片");
//        }
//        if (file.getSize() > 1024 * 1024 * 10) {
//            return Msg.fail().add("msg", "文件大小不能大于10M");
//        }
//        //获取文件后缀
//        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
//            return Msg.fail().add("msg", "请选择jpg,jpeg,gif,png格式的图片");
//        }
//
//        String savePath = new File(".").getCanonicalPath() + "\\target\\classes\\static\\p\\";
//
//        System.out.println(savePath);
//
//        File savePathFile = new File(savePath);
//        if (!savePathFile.exists()) {
//            //若不存在该目录，则创建目录
//            savePathFile.mkdir();
//        }
//
//        //通过UUID生成唯一文件名
//        String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
//
//        System.out.println(filename);
//
//        try {
//            //将文件保存指定目录
//            file.transferTo(new File(savePath + filename));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Msg.fail().add("msg", "保存文件异常");
//        }
//
//        //返回文件名称
        return Msg.success().add("suc", true);
    }


    /**
     * 修改密码
     * @param userId 用户id，如果没有就是修改自己的密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/changepwd", method = RequestMethod.POST)
    public Msg changePassword(
            @RequestParam(value = "id", defaultValue = "-1") String userId,
            @RequestParam(value = "old") String oldPassword,
            @RequestParam(value = "new") String newPassword,
            HttpServletRequest request
    ){
        boolean suc = false;
        // 先判断有没有id
        if("-1".equals(userId)){
            // 没有id，修改目前登录的用户的id
            String token = request.getHeader("token");
            if(null == token) {
                return Msg.fail();
            }
            User user = (User) redisUtil.get(token);
            if(user.getUserPassword().equals(MD5Util.getMD5(oldPassword))){
                // 密码正确
                user.setUserPassword(MD5Util.getMD5(newPassword));
                suc = user.updateById();
                return Msg.success().add("suc", suc);
            } else {
                return Msg.success().add("suc", suc);
            }
        } //修改当前登录的用户的密码
        // 管理员修改用户的密码
        User user = new User();
        user.setUserId(Integer.parseInt(userId));
        user.setUserPassword(MD5Util.getMD5(newPassword));
        // 不需要验证旧密码
        suc = user.updateById();
        // 直接修改
        return Msg.success().add("suc", suc);
    }


    /**
     * 修改用户信息
     * @param userInfo 用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/info/update", method = RequestMethod.POST)
    public Msg updateUserInfo(
            @RequestParam(value = "user")String userInfo,
            HttpServletRequest request
    ){
        String token = request.getHeader("token");
        if(null == token){
            return Msg.fail().add("msg", "用户未登录");
        }
        User user = new User();
        user = (User) redisUtil.get(token);
        if(null == user){
            return Msg.expire();
        }

        UserInfo info = new UserInfo();

        // 收到一个userInfo - Json字符串，直接从这里拆开，然后update
        userInfo = userInfo.substring(1, userInfo.length() - 1);

        // 取出 “userId”：00000 这种
        String[] kvs = userInfo.split(",");

        for (String kv: kvs) {
            String[] nameAndValue = kv.split(":");
            // 下标0 为 “userID”  下标1为 “asdasd” “1” 10929 null
            // userId
            String name = nameAndValue[0].substring(1, nameAndValue[0].length() - 1);
            String value = nameAndValue[1];
            // 查找与选择逐个排查
            if((!"null".equals(value)) && (!"".equals(value))){
                // 此字段不是null
                // 逐个判断 userInfo 中的属性
                if("userId".equals(name)){
                    info.setUserId(Integer.parseInt(value));
                    continue;
                }
                if("userEmail".equals(name)){
                    info.setUserEmail(value.substring(1, value.length() - 1));
                    continue;
                }
                if("userSex".equals(name)){
                    if("\"1\"".equals(value)){
                        // 男
                        info.setUserSex(1);
                    } else if("\"0\"".equals(value)){
                        // 女
                        info.setUserSex(0);
                    }
                    continue;
                }
                if("userRegion".equals(name)){
                    info.setUserRegion(value.substring(1, value.length() - 1));
                    continue;
                }
                if("userBirthdate".equals(name)){
                    info.setUserBirthdate(TimeUtils.castDateStringToDateTypeYMD(
                            value.substring(1, 11)));
                    continue;
                }
                if("userTelephone".equals(name)){
                    info.setUserTelephone(value.substring(1, value.length() - 1));
                    continue;
                }
                if("userRealName".equals(name)){
                    info.setUserRealName(value.substring(1, value.length() - 1));
                    continue;
                }
                if("userSchool".equals(name)){
                    info.setUserSchool(value.substring(1, value.length() - 1));
                    continue;
                }
                if("userMajor".equals(name)){
                    info.setUserMajor(value.substring(1, value.length() - 1));
                    continue;
                }
                if("userEnterSchoolDate".equals(name)) {
                    info.setUserEnterSchoolDate(TimeUtils.castDateStringToDateTypeYMD(
                            value.substring(1, 11)));
                    continue;
                }
                if("userAcademicDegree".equals(name)){
                    info.setUserAcademicDegree(value.substring(1, value.length() - 1));
                    continue;
                }
                if("userCompany".equals(name)){
                    info.setUserCompany(value.substring(1, value.length() - 1));
                    continue;
                }
                if("userPosition".equals(name)){
                    info.setUserPosition(value.substring(1, value.length() - 1));
                    continue;
                }
                if("userField".equals(name)){
                    info.setUserField(value.substring(1, value.length() - 1));
                }

            }

        }

        boolean suc = info.updateById();
        return Msg.success().add("suc", suc);
    }

    @RequestMapping(value = "/info/user", method = RequestMethod.POST)
    public Msg updateUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "signature") String signature,
            HttpServletRequest request
    ){
        // 判断用户登录状态
        String token = request.getHeader("token");
        if(null == token){
            return Msg.fail().add("msg", "用户未登录");
        }
        User user = new User();
        user = (User) redisUtil.get(token);
        if(null == user){
            return Msg.expire();
        }
        // 修改用户
        user.setUserSignature(signature);
        user.setUserName(username);
        boolean suc = user.updateById();
        if(suc){
            redisUtil.set(token, user, 20 * 60);
        }
        return Msg.success().add("suc", suc);
    }


    /**
     * 获取验证码
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getverifycode", method = RequestMethod.GET)
    public void generateValidationCode(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ){
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");

            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");

            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            VerifyCodeUtil verifyCodeUtil = new VerifyCodeUtil();
            //输出验证码图片方法
            verifyCodeUtil.getRandcode(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Msg addUser(
            @RequestParam("username") String userName,
            @RequestParam("password") String password,
            HttpServletRequest request
    ){

        User user = new User();
        // 设置信息
        user.setUserName(userName)
                .setUserLastLoginDate(null)
                .setUserId(null)
                .setUserSignature("Hello World")
                .setUserPassword(MD5Util.getMD5(password))
                .setUserRole(2)
                .setUserCreateDate(new Date())
                .setUserIp(request.getRemoteAddr());
        // 插入
        boolean insert = user.insert();
        if(!insert){
            return Msg.fail().add("msg", "用户注册失败");
        }
        return Msg.success().add("success", insert);
    }

}

