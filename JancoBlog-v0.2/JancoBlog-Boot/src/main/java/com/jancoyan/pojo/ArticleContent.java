package com.jancoyan.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jancoyan
 * @since 2021-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tbl_article_content")
public class ArticleContent extends Model<ArticleContent> {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    @TableId(value = "article_id")
    private String articleId;

    /**
     * 文章的html内容
     */
    private String articleHtml;

    /**
     * 文章的md内容
     */
    private String articleMd;

}
