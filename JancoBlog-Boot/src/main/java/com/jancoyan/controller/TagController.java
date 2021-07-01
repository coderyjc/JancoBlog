package com.jancoyan.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.pojo.Tag;
import com.jancoyan.service.TagService;
import com.jancoyan.utils.Msg;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.IllegalPathStateException;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAll(Integer page, Integer limit){
        IPage<Tag> iPage = new Page<>(page, limit);
        Tag tag = new Tag();
        IPage<Tag> rst = tag.selectPage(iPage, null);
        return Msg.success().add("pageInfo", rst);
    }

}

