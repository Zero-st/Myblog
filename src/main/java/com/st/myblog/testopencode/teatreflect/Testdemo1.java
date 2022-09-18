package com.st.myblog.testopencode.teatreflect;


import com.st.myblog.testopencode.controller.UserController;
import com.st.myblog.testopencode.service.UserService;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Testdemo1 {

	@Test
	public  void test() throws Exception {

		//被注入的对象
		UserService userService = new UserService();

		System.out.println(userService);


		//类似构造器注入方式
		//1.获得要注入bean的类对象

		Class<?> aClass = Class.forName("com.st.myblog.testopencode.controller.UserController");

		UserController newInstance = (UserController) aClass.newInstance();
		//2.获得属性

		Field field = aClass.getDeclaredField("userService");

		//获取属性名字
		String fieldName = field.getName();


		//加强访问
		field.setAccessible(true);


		//3.拼出get,set方法来属性注入  前缀：prefix        后缀：suffix
		String method_sux =fieldName.substring(0,1).toUpperCase() +  fieldName.substring(1,fieldName.length()).trim();

		System.out.println(method_sux);

		String setMethod= "set"+method_sux;

		System.out.println(setMethod);

		Method declaredMethod = aClass.getDeclaredMethod(setMethod,UserService.class);

		declaredMethod.setAccessible(true);



		//4.执行构造方法，属性注入
		declaredMethod.invoke(newInstance,userService);

		System.out.println(newInstance.getUserService());


	}


}
