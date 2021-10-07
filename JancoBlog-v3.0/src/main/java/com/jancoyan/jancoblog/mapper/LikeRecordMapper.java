package com.jancoyan.jancoblog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jancoyan.jancoblog.pojo.LikeRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-06
 */
public interface LikeRecordMapper extends BaseMapper<LikeRecord> {

    IPage<LikeRecord> getUserReceive(IPage<LikeRecord> iPage,
                                     Wrapper<LikeRecord> ew, String userId);
}
