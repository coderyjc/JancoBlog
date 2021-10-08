/**
 * @Author: Yan Jingcun
 * @Date: 2021/10/8
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.pojo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PageArticle extends Model<PageArticle>{

    private String articleId;

    private Integer articleAuthor;

    private String articleTitle;

    private Date articlePostTime;

}
