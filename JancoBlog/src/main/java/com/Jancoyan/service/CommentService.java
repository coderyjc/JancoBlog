package com.Jancoyan.service;

import com.Jancoyan.dao.ArticleCommentMapper;
import com.Jancoyan.domain.ArticleComment;
import com.Jancoyan.domain.ArticleCommentExample;
import com.Jancoyan.domain.ArticleCommentKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jancoyan
 */
@Service
public class CommentService {

    @Autowired
    ArticleCommentMapper articleCommentMapper;

    /**
     * 获取某文章的所有评论, 按照事件降序
     * @param id 文章id
     * @return 所有评论
     */
    public List<ArticleComment> getCommmentByArticleId(String id) {
        ArticleCommentExample example = new ArticleCommentExample();
        example.setOrderByClause("comment_date desc");
        ArticleCommentExample.Criteria criteria = example.createCriteria();
        criteria.andArticleIdEqualTo(id);
        List<ArticleComment> comments = articleCommentMapper.selectByExampleWithBLOBs(example);
        return comments;
    }

    /**
     * 直接插入文章的评论,然后让文章的评论数量+1
     * @param articleComment 文章评论
     */
    public void insertComment(ArticleComment articleComment) {
        articleCommentMapper.insert(articleComment);
    }

    /**
     * 获取所有评论，按照文章题目分类
     * @return 评论列表
     */
    public List<ArticleComment> getAll() {
        return articleCommentMapper.selectWithArticleTitle();
    }

    /**
     * 由主键删除
     * @param articleCommentKey 主键class
     */
    public void deleteByPrimaryKey(ArticleCommentKey articleCommentKey) {
        articleCommentMapper.deleteByPrimaryKey(articleCommentKey);
    }

    /**
     * 由主键获取文章所有信息
     * @param articleCommentKey 主键
     * @return 查询到的一个comment
     */
    public ArticleComment getCommentByPrimaryKey(ArticleCommentKey articleCommentKey) {
        ArticleCommentExample example = new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria = example.createCriteria();
        criteria.andArticleIdEqualTo(articleCommentKey.getArticleId());
        criteria.andCommentDateEqualTo(articleCommentKey.getCommentDate());
        criteria.andAuthorEmailEqualTo(articleCommentKey.getAuthorEmail());
        List<ArticleComment> comments = articleCommentMapper.selectByExampleWithBLOBs(example);
        return comments.get(0);
    }
}
