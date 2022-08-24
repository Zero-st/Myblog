package com.st.myblog.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Dell
 *
 *
 * Mybatis-Spring原理分析 -- @MapperScan注解
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.st.myblog.mapper")
public class MybatisPlusConfig {


    //PaginationInterceptor 对象

    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

}
