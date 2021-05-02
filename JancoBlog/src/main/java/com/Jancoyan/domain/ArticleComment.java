package com.Jancoyan.domain;

public class ArticleComment extends ArticleCommentKey {
    private String authorNickname;

    private String authorIp;

    private Integer likeCount;

    private String commentContent;

    @Override
    public String toString() {
        return "ArticleComment{" +
                "authorNickname='" + authorNickname + '\'' +
                ", authorIp='" + authorIp + '\'' +
                ", likeCount=" + likeCount +
                ", commentContent='" + commentContent + '\'' +
                '}';
    }

    public String getAuthorNickname() {
        return authorNickname;
    }

    public void setAuthorNickname(String authorNickname) {
        this.authorNickname = authorNickname == null ? null : authorNickname.trim();
    }

    public String getAuthorIp() {
        return authorIp;
    }

    public void setAuthorIp(String authorIp) {
        this.authorIp = authorIp == null ? null : authorIp.trim();
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }
}