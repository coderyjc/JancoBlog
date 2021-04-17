package com.Jancoyan.controller;

import com.Jancoyan.domain.Type;
import com.Jancoyan.service.TypeService;
import com.Jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Jancoyan
 */
@Controller
public class TypeController {

    @Autowired
    TypeService typeService;

    /**
     * 获取所有的一级文章类型，类型中包含了子类型
     * @return 成功信息，和类别列表
     */
    @ResponseBody
    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public Msg getAll(){
        List<Type> typeList = typeService.getAll();
        return Msg.success().add("types", typeList);
    }

}
