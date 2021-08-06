package com.jancoyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.pojo.Article;
import com.jancoyan.mapper.ArticleMapper;
import com.jancoyan.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public List<Article> selectArticleByTagId(String tagId) {
        return baseMapper.selectArticleByTagId(tagId);
    }

    @Override
    public Article selectByPrimaryKeyWithAuthorName(String articleId) {
        return baseMapper.selectByIdWithAuthorName(articleId);
    }

    @Override
    public IPage<Article> selectUserArticleByPage(Integer page, Integer limit, String id) {
        IPage<Article> iPage = new Page<>(page, limit);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("article_author_id", id);
        return baseMapper.selectPage(iPage, wrapper);
    }

    @Override
    public IPage<Article> selectAllWithAuthorNameByPage(Integer page, Integer limit) {
        IPage<Article> iPage = new Page<>(page, limit);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("article_post_time");
        return baseMapper.selectAllWithAuthorNameByPage(iPage, wrapper);
    }

    @Override
    public IPage<Article> selectAllByPage(Integer pn, Integer limit) {
        IPage<Article> page = new Page<>(pn, limit);
        return baseMapper.selectPage(page, null);
    }

    @Override
    public List<Article> getArticleRankByView() {
        return baseMapper.selectOrderByArticleViewCount();
    }

    @Override
    public List<Article> getArticleRankByLike() {
        return baseMapper.selectOrderByArticleLikeCount();
    }

    @Override
    public List<Article> getArticleRankByComment() {
        return baseMapper.selectOrderByArticleCommentCount();
    }

    @Override
    public List<Article> selectArticleByType(String typeId) {
        return baseMapper.selectArticleByType(typeId);
    }
}
