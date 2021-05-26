package com.Jancoyan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "Article{" +
                "articleId='" + articleId + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleAuthorId=" + articleAuthorId +
                ", articleType=" + articleType +
                '}';
    }

}