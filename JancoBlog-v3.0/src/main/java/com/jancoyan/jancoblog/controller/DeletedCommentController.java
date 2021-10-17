package com.jancoyan.jancoblog.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.Comment;
import com.jancoyan.jancoblog.pojo.DeletedComment;
import com.jancoyan.jancoblog.service.DeletedCommentService;
import com.jancoyan.jancoblog.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-17
 */
@RestController
@RequestMapping("/deleted/comment")
public class DeletedCommentController {

    @Autowired
    DeletedCommentService service;

    /**
     * 获取某个文章的分页评论
     * @param id 文章id
     * @param pn 页码
     * @param limit 容量，默认为 7
     * @return 消息
     */
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public Msg getCommentByArticle(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "pn", defaultValue = "1")Integer pn,
            @RequestParam(value = "limit", defaultValue = "7") Integer limit
    ){
        IPage<DeletedComment> iPage = service.getCommentByArticle(id, pn, limit);
        return Msg.success().add("pageInfo", iPage);
    }



}

