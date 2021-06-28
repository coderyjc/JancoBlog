package com.jancoyan.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.io.Serializable;

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
    private LocalDate commentDate;

    /**
     * 评论人的ip地址
     */
    private String commentAuthorIp;

    /**
     * 评论的点赞数
     */
    private Integer commentLikeCount;


    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getPreCommentId() {
        return preCommentId;
    }

    public void setPreCommentId(Integer preCommentId) {
        this.preCommentId = preCommentId;
    }

    public String getCommentArticleId() {
        return commentArticleId;
    }

    public void setCommentArticleId(String commentArticleId) {
        this.commentArticleId = commentArticleId;
    }

    public Integer getCommentAuthorId() {
        return commentAuthorId;
    }

    public void setCommentAuthorId(Integer commentAuthorId) {
        this.commentAuthorId = commentAuthorId;
    }

    public String getCommentAuthorNickname() {
        return commentAuthorNickname;
    }

    public void setCommentAuthorNickname(String commentAuthorNickname) {
        this.commentAuthorNickname = commentAuthorNickname;
    }

    public String getCommentAuthorEmail() {
        return commentAuthorEmail;
    }

    public void setCommentAuthorEmail(String commentAuthorEmail) {
        this.commentAuthorEmail = commentAuthorEmail;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public LocalDate getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDate commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentAuthorIp() {
        return commentAuthorIp;
    }

    public void setCommentAuthorIp(String commentAuthorIp) {
        this.commentAuthorIp = commentAuthorIp;
    }

    public Integer getCommentLikeCount() {
        return commentLikeCount;
    }

    public void setCommentLikeCount(Integer commentLikeCount) {
        this.commentLikeCount = commentLikeCount;
    }

    @Override
    protected Serializable pkVal() {
        return this.commentId;
    }

    @Override
    public String toString() {
        return "Comment{" +
        "commentId=" + commentId +
        ", preCommentId=" + preCommentId +
        ", commentArticleId=" + commentArticleId +
        ", commentAuthorId=" + commentAuthorId +
        ", commentAuthorNickname=" + commentAuthorNickname +
        ", commentAuthorEmail=" + commentAuthorEmail +
        ", commentContent=" + commentContent +
        ", commentDate=" + commentDate +
        ", commentAuthorIp=" + commentAuthorIp +
        ", commentLikeCount=" + commentLikeCount +
        "}";
    }
}
