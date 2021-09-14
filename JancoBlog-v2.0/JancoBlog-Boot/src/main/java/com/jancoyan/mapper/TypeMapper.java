package com.jancoyan.mapper;

import com.jancoyan.pojo.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jancoyan
 * @since 2021-06-28
 */
public interface TypeMapper extends BaseMapper<Type> {

    Integer getMaxTypeId();
}
