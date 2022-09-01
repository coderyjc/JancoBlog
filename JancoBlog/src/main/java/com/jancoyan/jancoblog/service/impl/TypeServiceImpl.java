package com.jancoyan.jancoblog.service.impl;

import com.jancoyan.jancoblog.pojo.Type;
import com.jancoyan.jancoblog.mapper.TypeMapper;
import com.jancoyan.jancoblog.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-09-14
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

}
