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

        wrapper.orderByDesc("article_post_time");
        return baseMapper.getIndexList(iPage, wrapper);
    }
}
