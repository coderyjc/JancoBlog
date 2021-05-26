package com.Jancoyan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommentKey {
    private String articleId;

    private String authorEmail;

    private Date commentDate;
}