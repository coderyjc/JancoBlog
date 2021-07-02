package com.jancoyan.service;

import com.jancoyan.pojo.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-07-01
 */
public interface ArticleTagService extends IService<ArticleTag> {

    void deleteByArticleId(String articleId);

}
