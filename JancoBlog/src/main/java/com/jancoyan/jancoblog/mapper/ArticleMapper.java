package com.jancoyan.jancoblog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.model.domain.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jancoyan.jancoblog.model.domain.PageArticle;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<Article> getIndexList(IPage<Article> iPage, Wrapper ew);

    IPage<Article> getManageList(IPage<Article> iPage, Wrapper ew);

    Article getSingleArticle(String id);

    IPage<Article> getDeletedList(IPage<Article> iPage, Wrapper ew);

    void deleteCompletely(String articleId);

    void batchRecover(String articleId);

    IPage<PageArticle> getArticleByUserRecently(IPage<PageArticle> iPage, Wrapper ew);

    Article getSingleArticleDeleted(String id);

    Article getArticleEdit(String id);
}
