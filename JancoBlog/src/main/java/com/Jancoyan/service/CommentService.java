package com.Jancoyan.service;

import com.Jancoyan.dao.ArticleCommentMapper;
import com.Jancoyan.domain.ArticleComment;
import com.Jancoyan.domain.ArticleCommentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService{

    @Autowired
    ArticleCommentMapper commentMapper;

    /**
     * 获取所有评论
     * @return 所有评论
     */
    public List<ArticleComment> getAll() {
        return commentMapper.selectByExample(null);
    }

    /**
     * 从主键删除
     * @param commentId 主键
     */
    public void deleteArticleById(Integer commentId) {
        commentMapper.selectByPrimaryKey(commentId);
    }

    /**
     * 从主键获取
     * @param commentId 主键
     * @return comment 评论本体
     */
    public ArticleComment getCommentById(Integer commentId) {
        return commentMapper.selectByPrimaryKey(commentId);
    }


    /**
     * 筛选某篇文章下面的所有评论
     * @param articleId 文章id
     */
    public List<ArticleComment> getCommentByArticleId(String articleId) {
        ArticleCommentExample example = new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria = example.createCriteria();
        criteria.andArticleIdEqualTo(articleId);
        return commentMapper.selectByExampleWithBLOBs(example);
    }

    /**
     * 直接插入
     * @param articleComment 记录
     */
    public void insert(ArticleComment articleComment) {
        commentMapper.insert(articleComment);
    }
}