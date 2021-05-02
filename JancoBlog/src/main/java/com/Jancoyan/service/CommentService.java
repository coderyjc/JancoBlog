package com.Jancoyan.service;

import com.Jancoyan.dao.ArticleCommentMapper;
import com.Jancoyan.domain.ArticleComment;
import com.Jancoyan.domain.ArticleCommentExample;
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
     * 直接插入文章的评论
     * @param articleComment 文章评论
     */
    public void insertComment(ArticleComment articleComment) {
        articleCommentMapper.insert(articleComment);
    }


}
