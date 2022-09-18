package com.st.myblog.testopencode.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dell
 * @interface不是接口是注解类，在jdk1.5之后加入的功能，
 * 使用@interface自定义注解时，
 * 自动继承了java.lang.annotation.Annotation接口
 */
//作用什么时候
@Retention(RetentionPolicy.RUNTIME)
//作用在什么地方
@Target(ElementType.FIELD)
public @interface MyAutowired {


}
