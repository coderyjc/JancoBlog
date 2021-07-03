package com.jancoyan.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jancoyan.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jancoyan
 * @since 2021-06-28
 */
public interface CommentMapper extends BaseMapper<Comment> {

    IPage<Comment> selectAllByPageWithArticleName(IPage<Comment> commentIPage,
                                                  @Param(Constants.WRAPPER) Wrapper<Comment> wrapper);

    IPage<Comment> selectUserArticleCommentByUserId(IPage<Comment> iPage,
                                                    QueryWrapper<Comment> wrapper,
                                                    Integer userId);
}
