package com.jancoyan.jancoblog.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tbl_article")
public class Article extends Model<Article> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "article_id")
    private String articleId;

    private String articleTitle;

    private Integer articleAuthor;

    private Integer articleType;

    private String articleSummary;

    private String articleHtml;

    private String articleMd;

    private Integer articleIsComment;

    private Integer articleRank;

    private Date articlePostTime;

    private Date articleEditTime;

    private Integer articleViewCount;

    private Integer articleCommentCount;

    private Integer articleLikeCount;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String typeName;

}
