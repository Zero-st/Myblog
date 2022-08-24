package com.st.myblog.service.impl;

import com.st.myblog.entity.User;
import com.st.myblog.mapper.UserMapper;
import com.st.myblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2022-08-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
