package com.jancoyan.jancoblog.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tbl_deleted_comment")
public class DeletedComment extends Model<DeletedComment> {

    private static final long serialVersionUID = 1L;

    private Integer commentId;

    private Integer preCommentId;

    private String commentArticleId;

    private Integer commentAuthorId;

    private String commentAuthorName;

    private String commentAuthorEmail;

    private String commentContent;

    private Date commentDate;

    private String commentAuthorIp;

    private Integer commentLikeCount;

    @TableField(exist = false)
    private String articleTitle;

}
