package com.Jancoyan.dao;

import com.Jancoyan.domain.ArticleComment;
import com.Jancoyan.domain.ArticleCommentExample;
import com.Jancoyan.domain.ArticleCommentKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleCommentMapper {
    long countByExample(ArticleCommentExample example);

    int deleteByExample(ArticleCommentExample example);

    int deleteByPrimaryKey(ArticleCommentKey key);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    List<ArticleComment> selectByExampleWithBLOBs(ArticleCommentExample example);

    List<ArticleComment> selectByExample(ArticleCommentExample example);

    ArticleComment selectByPrimaryKey(ArticleCommentKey key);

    int updateByExampleSelective(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByExampleWithBLOBs(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByExample(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKeyWithBLOBs(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);
}