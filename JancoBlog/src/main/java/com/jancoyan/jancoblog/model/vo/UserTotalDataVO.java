/**
 * @Author: Yan Jingcun
 * @Date: 2021/10/3
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Jancoyan
 * @since 2021-10-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserTotalDataVO {

    private Integer articleAuthor;

    private String userName;

    private String userSignature;

    private Integer  totalArticle;

    private Integer  totalViewCount;

    private Integer  totalLikeCount;

    private Integer  totalCommentCount;

}
