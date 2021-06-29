/**
 * @Author: Yan Jingcun
 * @Date: 2021/6/29
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MvcController {

    @GetMapping("/{id}")
    public String rootResource(@PathVariable(value = "id") String name){
        if("workbench".equals(name)){
            return "/workbench/index";
        }
        return name;
    }

    @GetMapping("/workbench/{id}")
    public String backEndManage(
            @PathVariable(value = "id") String path
    ){
            return "/workbench/" + path;
    }

    @GetMapping("/workbench/{id}/{id2}")
    public String subManage(
            @PathVariable(value = "id") String path,
            @PathVariable(value = "id2") String path2
    ){
        return "/workbench/" + path + "/" + path2;
    }
}
