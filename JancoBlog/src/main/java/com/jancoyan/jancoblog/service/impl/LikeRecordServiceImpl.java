package com.jancoyan.jancoblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jancoyan.jancoblog.model.domain.LikeRecord;
import com.jancoyan.jancoblog.mapper.LikeRecordMapper;
import com.jancoyan.jancoblog.service.LikeRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

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


    @Override
    public void insertRecord(Integer userId, String articleId) {
        LikeRecord record = new LikeRecord();
        record.setAuthorId(userId);
        record.setLikeDate(new Date());
        record.setArticleId(articleId);
        record.insert();
    }

    @Override
    public void deleteRecord(Integer userId, String articleId) {
        LikeRecord record = new LikeRecord();
        QueryWrapper<LikeRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        wrapper.eq("author_id", userId);
        record.delete(wrapper);
    }
}
