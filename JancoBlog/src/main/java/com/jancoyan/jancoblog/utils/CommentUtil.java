/**
 * @Author: Yan Jingcun
 * @Date: 2022/9/8
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jancoyan.jancoblog.model.domain.Comment;

public class CommentUtil {

    public static QueryWrapper<Comment> generateManageCommentWrapperByCondition(
            QueryWrapper<Comment> wrapper, String articleTitle,
            String commentAuthorName, String rankLike, String start,
            String end){

        if(CommentUtil.isConditionValid(commentAuthorName)){
            wrapper.like("comment_author_name", commentAuthorName);
        }
        if(CommentUtil.isConditionValid(articleTitle)){
            wrapper.like("article_title", articleTitle);
        }
        if(CommentUtil.isConditionValid(rankLike)){
            if ("1".equals(rankLike)) {
                wrapper.orderByAsc(rankLike);
            } else {
                wrapper.orderByDesc(rankLike);
            }
        }
        if(CommentUtil.isConditionValid(start)){
            wrapper.gt("comment_date", start);
        }
        if(CommentUtil.isConditionValid(end)){
            wrapper.lt("comment_date", end);
        }
        return wrapper;
    }


    /**
     * 判断条件是否有效
     * @param attr
     * @return
     */
    public static boolean isConditionValid(String attr){
        if(null == attr || "".equals(attr) || "null".equals(attr)){
            return false;
        }
        return true;
    }


}
