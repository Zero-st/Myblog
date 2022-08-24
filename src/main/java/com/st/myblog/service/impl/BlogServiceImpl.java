package com.st.myblog.service.impl;

import com.st.myblog.entity.Blog;
import com.st.myblog.mapper.BlogMapper;
import com.st.myblog.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
