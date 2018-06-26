package com.moyhui.miaosha.controller;

import com.moyhui.miaosha.domain.User;
import com.moyhui.miaosha.result.Result;
import com.moyhui.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class SampleController {

	@Autowired
	UserService userService;

	@RequestMapping("getuser")
	public Result<User> doGet(){
		User user = userService.getById(1);
		return Result.success(user);
	}

	@RequestMapping("insert")
	public Result<Boolean> insertUser(){
		boolean bl = userService.tx();
		return Result.success(bl);
	}
}
