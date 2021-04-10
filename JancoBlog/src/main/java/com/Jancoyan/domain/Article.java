package com.Jancoyan.domain;

import java.util.Date;

public class Article {
    private String articleId;

    private String articleTitle;

    private Integer articleAuthorId;

    private String articleAbstract;

    private Integer articleType;

    private Date articleEditTime;

    private Date articlePostDate;

    private Integer articleViewTime;

    private Integer articleCommentCount;

    private Integer articleLikeCount;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId == null ? null : articleId.trim();
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle == null ? null : articleTitle.trim();
    }

    public Integer getArticleAuthorId() {
        return articleAuthorId;
    }

    public void setArticleAuthorId(Integer articleAuthorId) {
        this.articleAuthorId = articleAuthorId;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract == null ? null : articleAbstract.trim();
    }

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public Date getArticleEditTime() {
        return articleEditTime;
    }

    public void setArticleEditTime(Date articleEditTime) {
        this.articleEditTime = articleEditTime;
    }

    public Date getArticlePostDate() {
        return articlePostDate;
    }

    public void setArticlePostDate(Date articlePostDate) {
        this.articlePostDate = articlePostDate;
    }

    public Integer getArticleViewTime() {
        return articleViewTime;
    }

    public void setArticleViewTime(Integer articleViewTime) {
        this.articleViewTime = articleViewTime;
    }

    public Integer getArticleCommentCount() {
        return articleCommentCount;
    }

    public void setArticleCommentCount(Integer articleCommentCount) {
        this.articleCommentCount = articleCommentCount;
    }

    public Integer getArticleLikeCount() {
        return articleLikeCount;
    }

    public void setArticleLikeCount(Integer articleLikeCount) {
        this.articleLikeCount = articleLikeCount;
    }
}