package com.jancoyan.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.pojo.Tag;
import com.jancoyan.pojo.Type;
import com.jancoyan.service.TypeService;
import com.jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    TypeService typeService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Msg getAll(Integer page, Integer limit){
        IPage<Type> iPage = new Page<>(page, limit);
        Type type = new Type();
        IPage<Type> rst = type.selectPage(iPage, null);
        return Msg.success().add("pageInfo", rst);
    }


    @RequestMapping(value = "/type", method = RequestMethod.PUT)
    public Msg addType(
            @RequestParam("typeName") String typeName,
            @RequestParam(value = "typeDescription", defaultValue = "") String typeDescription,
            @RequestParam(value = "preType", defaultValue = "null")String preType
    ){
        Type type = new Type();
        type.setTypeId(typeService.getMaxTypeId() + 1);
        if(!"null".equals(preType)){
            type.setPreTypeId(Integer.parseInt(preType));
        } else {
            type.setPreTypeId(null);
        }
        type.setTypeDescription(typeDescription);
        type.setTypeName(typeName);
        type.insert();
        return Msg.success();
    }


    @RequestMapping(value = "/type", method = RequestMethod.POST)
    public Msg updateType(
            @RequestParam("typeId") String typeId,
            @RequestParam("typeName") String typeName,
            @RequestParam("description") String typeDescription,
            @RequestParam(value = "preType", defaultValue = "null")String preType
    ){
        Type type = new Type();
        type.setTypeId(Integer.parseInt(typeId));
        type.setTypeName(typeName);
        type.setTypeDescription(typeDescription);
        type.setPreTypeId(Integer.parseInt(preType));
        type.updateById();
        return Msg.success();
    }


    @RequestMapping(value = "/type", method = RequestMethod.DELETE)
    public Msg deleteType(
            @RequestParam("id") String typeId
    ){
        String[] ids = {typeId};
        if (typeId.contains("&")){
            ids = typeId.split("&");
        }
        Type type = new Type();
        for (String id: ids) {
            type.setTypeId(Integer.parseInt(id));
            type.deleteById();
        }
        return Msg.success();
    }
}

