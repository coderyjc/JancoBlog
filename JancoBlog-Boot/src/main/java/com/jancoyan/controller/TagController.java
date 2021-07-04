package com.jancoyan.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.pojo.Tag;
import com.jancoyan.service.TagService;
import com.jancoyan.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @RequestMapping(value = "/tag", method = RequestMethod.PUT)
    public Msg addTag(
            @RequestParam("tagName") String tagName,
            @RequestParam("tagDescription") String tagDescription
    ){
        Tag tag = new Tag();
        tag.setTagName(tagName);
        tag.setTagDescription(tagDescription);
        tag.insert();
        return Msg.success();
    }

    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    public Msg updateTag(
            @RequestParam("tagId") String tagId,
            @RequestParam("tagName") String tagName,
            @RequestParam("description") String tagDescription
    ){
        Tag tag = new Tag();
        tag.setTagId(Integer.parseInt(tagId));
        tag.setTagName(tagName);
        tag.setTagDescription(tagDescription);
        tag.updateById();
        return Msg.success();
    }

    @RequestMapping(value = "/tag", method = RequestMethod.DELETE)
    public Msg deleteTag(
            @RequestParam("id") String tagId
    ){
        // 多个tag的id会用 & 连起来，用 & 把不同的tagid分割
        String[] ids = {tagId};
        if (tagId.contains("&")){
            ids = tagId.split("&");
        }
        Tag tag = new Tag();
        for (String id: ids) {
            tag.setTagId(Integer.parseInt(id));
            tag.deleteById();
        }
        return Msg.success();
    }

}

