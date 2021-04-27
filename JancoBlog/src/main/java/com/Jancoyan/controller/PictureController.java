package com.Jancoyan.controller;

import com.Jancoyan.utils.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class PictureController {


    @ResponseBody
    @RequestMapping(value = "/uploadarticlepicture", method = RequestMethod.POST)
    public Msg uploadArticlePicture(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value = "image-file", required = false)MultipartFile multipartFile){
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Msg.success();
    }


}
