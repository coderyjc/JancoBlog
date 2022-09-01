package com.jancoyan.jancoblog.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-10-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tbl_like_record")
public class LikeRecord extends Model<LikeRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "like_id", type = IdType.AUTO)
    private Integer likeId;

    /**
     * 被点赞文章id
     */
    private String articleId;

    /**
     * 点赞的人
     */
    private Integer authorId;

    /**
     * 点赞时间
     */
    private Date likeDate;

    /**
     * 点赞人用户名
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 被点赞的文章标题
     */
    @TableField(exist = false)
    private String articleTitle;

    /**
     * 被点赞的文章的作者
     */
    @TableField(exist = false)
    private String articleAuthor;

}
