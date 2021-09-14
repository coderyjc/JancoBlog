package com.Jancoyan.dao;

import com.Jancoyan.domain.ArticleTagExample;
import com.Jancoyan.domain.ArticleTagKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleTagMapper {
    long countByExample(ArticleTagExample example);

    int deleteByExample(ArticleTagExample example);

    int deleteByPrimaryKey(ArticleTagKey key);

    int insert(ArticleTagKey record);

    int insertSelective(ArticleTagKey record);

    List<ArticleTagKey> selectByExample(ArticleTagExample example);

    int updateByExampleSelective(@Param("record") ArticleTagKey record, @Param("example") ArticleTagExample example);

    int updateByExample(@Param("record") ArticleTagKey record, @Param("example") ArticleTagExample example);
}