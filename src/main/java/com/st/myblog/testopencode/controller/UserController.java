package com.st.myblog.testopencode.controller;


import com.st.myblog.testopencode.component.MyAutowired;
import com.st.myblog.testopencode.service.UserService;

public class UserController {


	@MyAutowired
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	/*public void setUserService(UserService userService) {
		this.userService = userService;
	}*/
}
