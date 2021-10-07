package com.jancoyan.jancoblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.LikeRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-06
 */
public interface LikeRecordService extends IService<LikeRecord> {

    /**
     * 获取用户收到的点赞列表（时间顺序）
     * @param userId 用户id
     * @param pn 页码
     * @param limit 容量
     * @return
     */
    IPage<LikeRecord> getUserReceive(String userId, Integer pn, Integer limit);
}
