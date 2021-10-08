package com.jancoyan.jancoblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.pojo.LikeRecord;
import com.jancoyan.jancoblog.mapper.LikeRecordMapper;
import com.jancoyan.jancoblog.service.LikeRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-06
 */
@Service
public class LikeRecordServiceImpl extends ServiceImpl<LikeRecordMapper, LikeRecord> implements LikeRecordService {

    @Override
    public IPage<LikeRecord> getUserReceive(String userId, Integer pn, Integer limit) {
        IPage<LikeRecord> iPage = new Page<>(pn, limit);
        QueryWrapper<LikeRecord> wrapper = new QueryWrapper<>();

        wrapper.eq("article_author", userId);

        wrapper.orderByDesc("like_date");
        return baseMapper.getUserReceive(iPage, wrapper, userId);
    }
}
