package com.jancoyan.jancoblog.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-16
 */
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_article_image")
@Data
public class ArticleImage extends Model<ArticleImage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "filename")
    private String filename;

    private String articleId;

    private Date insertDate;

}
