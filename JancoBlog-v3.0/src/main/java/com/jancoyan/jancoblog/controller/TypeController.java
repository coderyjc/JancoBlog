package com.jancoyan.jancoblog.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.pojo.Type;
import com.jancoyan.jancoblog.service.ArticleService;
import com.jancoyan.jancoblog.service.TypeService;
import com.jancoyan.jancoblog.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.IllegalPathStateException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
@RestController
@RequestMapping("/type")
public class TypeController {


    @Autowired
    TypeService service;

    /**
     * 获取所有类别，不分页
     * @return pageInfo
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAll(){
        IPage<Type> iPage = new Page<>(1, 99);
        Type type = new Type();
        iPage = type.selectPage(iPage, null);
        return Msg.success().add("pageInfo", iPage);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Msg addType(
            @RequestParam(value = "name") String typeName,
            @RequestParam(value = "description", defaultValue = "") String description
    ){



        return Msg.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Msg deleteType(
            @RequestParam(value = "ids")String ids
    ){



        return Msg.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Msg updateType(
            @RequestParam(value = "name") String typeName,
            @RequestParam(value = "description", defaultValue = "") String description
    ){


        return Msg.success();
    }


}

