package com.jancoyan.jancoblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jancoyan.jancoblog.pojo.Article;
import com.jancoyan.jancoblog.pojo.LikeRecord;
import com.jancoyan.jancoblog.pojo.User;
import com.jancoyan.jancoblog.service.LikeRecordService;
import com.jancoyan.jancoblog.utils.Msg;
import com.jancoyan.jancoblog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-06
 */
@Controller
@RequestMapping("/like_record")
public class LikeRecordController {

    @Autowired
    LikeRecordService service;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 判断用户是否点赞了这个文章
     * @param id 文章id
     * @param request
     * @return
     */
    @RequestMapping(value = "/like", method = RequestMethod.GET)
    public Msg isLiked(
        @RequestParam(value = "id") String id,
        HttpServletRequest request
    ){
//        登录认证
        String token = request.getHeader("token");
        if(null == token){
            return Msg.expire();
        }
        User user;
        user = (User) redisUtil.get(token);
        if(null != user){
            return Msg.fail();
        }

        QueryWrapper<LikeRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", id);
        wrapper.eq("author_id", user.getUserId());

        LikeRecord record = new LikeRecord();
        record = record.selectOne(wrapper);

        if(null == record){
//            没有点赞
            return Msg.success().add("suc", false);
        }else {
//            点赞了
            return Msg.success().add("suc", true);
        }
    }

    /**
     * 获取一个用户最近收到的点赞记录
     * @param id 用户id
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Msg getUserLike(
            @RequestParam(value = "id") String id
    ) {


        return Msg.success();
    }
}

