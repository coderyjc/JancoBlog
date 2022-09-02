package com.jancoyan.jancoblog.service.impl;

import com.jancoyan.jancoblog.mapper.UserInfoMapper;
import com.jancoyan.jancoblog.model.domain.UserInfo;
import com.jancoyan.jancoblog.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-14
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
