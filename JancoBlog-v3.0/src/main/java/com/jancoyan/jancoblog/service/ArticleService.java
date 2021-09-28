package com.jancoyan.jancoblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
public interface ArticleService extends IService<Article> {

    IPage<Article> getIndexList(Integer pn, Integer limit, String condition);

    IPage<Article> getManageList(String userName, Integer pn, Integer limit, String condition);

}
