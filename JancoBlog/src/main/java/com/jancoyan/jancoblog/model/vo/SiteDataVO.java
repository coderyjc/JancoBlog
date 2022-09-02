/**
 * @Author: Yan Jingcun
 * @Date: 2022/9/2
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SiteDataVO {

    /**
     * 总浏览量
     */
    private Integer viewCountAll;

    /**
     * 总字数
     */
    private Integer wordCountAll;

    /**
     * 总评论
     */
    private Integer commentCountAll;

    /**
     * 总文章数
     */
    private Integer articleCountAll;
}
