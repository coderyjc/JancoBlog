package com.jancoyan.jancoblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.pojo.Article;
import com.jancoyan.jancoblog.mapper.ArticleMapper;
import com.jancoyan.jancoblog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public IPage<Article> getIndexList(Integer pn, Integer limit, String condition) {

        //        分页查询
        IPage<Article> iPage = new Page<>(pn, limit);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();

        String[] split = condition.split("--");
        for (String item : split) {
            String[] split2 = item.split("=");
            if(split2.length < 2){
                continue;
            }

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
                    wrapper.orderByAsc("article_like_count");
                } else {
                    wrapper.orderByDesc("article_like_count");
                }
            }else if(split2[0].equals("rank_comment")){
                if (split2[1].equals("1")) {
                    wrapper.orderByAsc("article_comment_count");
                } else {
                    wrapper.orderByDesc("article_comment_count");
                }
            }
        }

        return baseMapper.getIndexList(iPage, wrapper);
    }
}
