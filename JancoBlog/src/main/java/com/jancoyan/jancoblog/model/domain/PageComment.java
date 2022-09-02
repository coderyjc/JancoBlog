/**
 * @Author: Yan Jingcun
 * @Date: 2021/10/8
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.model.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageComment extends Model<LikeRecord> {

    private String commentAuthorName;

    private String articleId;

    private Integer articleAuthor;

    private String articleTitle;

    private Date commentDate;

}

