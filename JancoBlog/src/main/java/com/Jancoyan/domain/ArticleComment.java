package com.Jancoyan.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleComment extends ArticleCommentKey {
    private String authorNickname;

    private String authorIp;

    private Integer likeCount;

    private String commentContent;

    private String articleTitle;

    @Override
    public String toString() {
        return "ArticleComment{" +
                "authorNickname='" + authorNickname + '\'' +
                ", authorIp='" + authorIp + '\'' +
                ", likeCount=" + likeCount +
                ", commentContent='" + commentContent + '\'' +
                '}';
    }
}