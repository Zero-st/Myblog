package com.st.myblog.testopencode.teatreflect;

import com.st.myblog.testopencode.component.MyAutowired;
import com.st.myblog.testopencode.controller.UserController;
import com.st.myblog.testopencode.service.UserService;
import org.junit.Test;

import java.util.stream.Stream;

public class TestDemo2 {

    @Test
    public void test() throws Exception {

        //被注入的对象
        UserService userService = new UserService();

        //要使用注入bean的类对象
        UserController userController = new UserController();
        Class<? extends UserController> aClass = userController.getClass();

        System.out.println(userService);

        //获取所有属性

        Stream.of(aClass.getDeclaredFields()).forEach(field -> {
                    String name = field.getName();
                    MyAutowired annotation = field.getAnnotation(MyAutowired.class);
                    if (annotation != null) {
                        field.setAccessible(true);
                        //获取属性类型
                        Class<?> type = field.getType();

                        try {
                            Object o = type.newInstance();
                            field.set(userController, o);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }

                }
        );

        System.out.println(userController.getUserService());

    }
}
