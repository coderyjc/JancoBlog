/**
 * @Author: Yan Jingcun
 * @Date: 2021/9/15
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MvcController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
