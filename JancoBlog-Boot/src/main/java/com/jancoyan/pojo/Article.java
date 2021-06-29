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

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tbl_article")
public class Article extends Model<Article> {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id，即作者id+时间戳
     */
    @TableId(value = "article_id")
    private String articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 作者id
     */
    private Integer articleAuthorId;

    /**
     * 文章摘要
     */
    private String articleSummary;

    /**
     * 是否允许评论
     */
    private Integer articleIsComment;

    /**
     * 文章排名，用来置顶
     */
    private Integer articleRank;

    /**
     * 上一次修改时间
     */
    private LocalDate articleEditTime;

    /**
     * 文章发表时间
     */
    private LocalDate articlePostTime;

    /**
     * 文章阅读量
     */
    private Integer articleViewCount;

    /**
     * 文章评论量
     */
    private Integer articleCommentCount;

    /**
     * 文章点赞量
     */
    private Integer articleLikeCount;

    /**
     * 文章作者的名字
     */
    @TableField(exist = false)
    private String articleAuthorName;
}
