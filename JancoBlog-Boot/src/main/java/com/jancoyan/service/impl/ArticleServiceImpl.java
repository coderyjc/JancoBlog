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
    public IPage<Article> selectAllWithAuthorNameByPage(
            Integer page, Integer limit, String search) {
//        分页查询
        IPage<Article> iPage = new Page<>(page, limit);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
//        筛选条件
        String[] split = search.split("&");
        for (String item : split) {
            String[] split2 = item.split("=");
            if(split2.length < 2){
                continue;
            }
            System.out.println(item);
            System.out.println(split2[1]);
            if(split2[0].equals("article_author_name")){
                wrapper.like("article_author_name", split2[1]);
            }else if(split2[0].equals("article_title")){
                wrapper.like("article_title", split2[1]);
            }else if(split2[0].equals("type")){
                wrapper.eq("article_type", split2[1]);
            }else if(split2[0].equals("start")){
                wrapper.gt("article_post_time", split2[1]);
            }else if(split2[0].equals("end")){
                wrapper.lt("article_post_time", split2[1]);
            }else if(split2[0].equals("rank_view")){
                if (split2[1].equals("1")) {
                    wrapper.orderByAsc("article_view_count");
                } else {
                    wrapper.orderByDesc("article_view_count");
                }
            }else if(split2[0].equals("rank_like")){
                if (split2[1].equals("1")) {
                    wrapper.orderByAsc("article_rank_count");
                } else {
                    wrapper.orderByDesc("article_rank_count");
                }
            }else if(split2[0].equals("rank_comment")){
                if (split2[1].equals("1")) {
                    wrapper.orderByAsc("article_comment_count");
                } else {
                    wrapper.orderByDesc("article_comment_count");
                }
            }
        }

        wrapper.orderByDesc("article_post_time");
        return baseMapper.selectAllWithAuthorNameByPage(iPage, wrapper);
    }

    @Override
    public IPage<Article> selectAllByPage(Integer pn, Integer limit) {
        IPage<Article> page = new Page<>(pn, limit);
        return baseMapper.selectPage(page, null);
    }

    @Override
    public List<Article> getArticleRankByComment() {
        return baseMapper.selectOrderByArticleCommentCount();
    }

}
