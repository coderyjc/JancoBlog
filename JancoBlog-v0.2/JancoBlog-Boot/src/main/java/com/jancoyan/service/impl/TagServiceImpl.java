package com.jancoyan.service.impl;

import com.jancoyan.pojo.Tag;
import com.jancoyan.mapper.TagMapper;
import com.jancoyan.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-06-28
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
