package com.Jancoyan.controller;

import com.Jancoyan.domain.User;
import com.Jancoyan.utils.FileIo;
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

@Controller
public class PictureController {


    /**
     * 在md编辑器中进行图片的插入
     * @param multipartFile 前端传来的图像文件
     * @param request request对象
     * @param session session 对象
     * @return editor.md接受的json字符串
     */
    @ResponseBody
    @RequestMapping(value = "/uploadarticlepicture", method = RequestMethod.POST)
    public JSONObject uploadArticlePicture(@RequestParam(value = "editormd-image-file",
                                                   required = true)MultipartFile multipartFile,
                                           HttpServletRequest request,
                                           HttpSession session){
        // 获取用户id
        User user = (User) session.getAttribute("user");
        String userId = String.valueOf(user.getUserId());
        //
        String trueFileName = multipartFile.getOriginalFilename();
        // 文件后缀名
        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));
        // 图片文件命名规范：
        // 时间-用户名.后缀
        String now = TimeUtils.getCurrentTimeString();
        // 图片的全名
        String fileName = now.replaceAll("[:-]","").replace(' ', '-') + userId + suffix;
        // 目录，每月创建一个目录
        // 获取当前月份
        String directory = now.substring(0, 7);
        // 获取图片地址
        String path =
                request.getSession().getServletContext().getRealPath(
                        "/static/image/" + directory
                );

        // 调用工具类进行图片的写入
        FileIo.uploadPicture(multipartFile, path, fileName);

        String backUrl = request.getScheme() + "://" +
                request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath() +
                "/static/image/" + directory + "/" + fileName;

        JSONObject rst = new JSONObject();
        rst.put("url", backUrl);
        rst.put("success", 1);
        rst.put("message", "success");

        return rst;
    }


}
