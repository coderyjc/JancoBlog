package com.Jancoyan.controller;

import com.Jancoyan.domain.User;
import com.Jancoyan.utils.TimeUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
public class PictureController {


    /**
     *
     * @param request
     * @param session
     * @param multipartFile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadarticlepicture", method = RequestMethod.POST)
    public JSONObject uploadArticlePicture(@RequestParam(value = "editormd-image-file",
                                                   required = true)MultipartFile multipartFile,
                                           HttpServletRequest request,
                                           HttpSession session){
        System.out.println(multipartFile);
        // 获取用户id
        User user = (User) session.getAttribute("user");
        String userId = String.valueOf(user.getUserId());
        String trueFileName = multipartFile.getOriginalFilename();
        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));
        // 图片文件命名规范：
        // 时间-用户名.后缀
        String now = TimeUtils.getCurrentTimeString();
        String fileName = now + "-" + userId + suffix;
        String directory = now.substring(0, 7);
        String path =
                request.getSession().getServletContext().getRealPath(
                        "/static/image/" + directory + "/"
                );
        System.out.println(path);

        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        JSONObject rst = new JSONObject();
        rst.put("url", "../static/image/" + directory + "/" + fileName);
        rst.put("success", 1);
        rst.put("message", "upload success");

        System.out.println(rst);

        return rst;
    }


}
