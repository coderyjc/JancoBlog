package com.jancoyan.jancoblog.service.impl;

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

}
