package com.Jancoyan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleComment {
    private Integer commentId;

    private String articleId;

    private Integer authorId;

    private String authorNickname;

    private String authorEmail;

    private Date commentDate;

    private String authorIp;

    private Integer likeCount;

    private String commentContent;

}