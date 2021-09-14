package com.jancoyan.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tbl_comment")
public class Comment extends Model<Comment> {

    private static final long serialVersionUID = 1L;

    /**
     * 评论的id
     */
    @TableId(value = "comment_id")
    private Integer commentId;

    /**
     * 上一级评论的id
     */
    private Integer preCommentId;

    /**
     * 评论在哪个文章下面
     */
    private String commentArticleId;

    /**
     * 评论在哪个文章下面，这个文章的标题
     */
    @TableField(exist = false)
    private String ArticleTitle;

    /**
     * 评论人的id（匿名没有）
     */
    private Integer commentAuthorId;

    /**
     * 评论人昵称
     */
    private String commentAuthorNickname;

    /**
     * 评论人邮箱
     */
    private String commentAuthorEmail;

    /**
     * 内容
     */
    private String commentContent;

    /**
     * 评论日期
     */
    private Date commentDate;

    /**
     * 评论人的ip地址
     */
    private String commentAuthorIp;

    /**
     * 评论的点赞数
     */
    private Integer commentLikeCount;

}
