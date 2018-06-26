package com.moyhui.miaosha.controller;

import com.moyhui.miaosha.domain.User;
import com.moyhui.miaosha.redis.RedisService;
import com.moyhui.miaosha.redis.UserKey;
import com.moyhui.miaosha.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

	@Autowired
	RedisService redisService;


	@RequestMapping("/get")
	@ResponseBody
	public Result<User> get() {
		User  user  = redisService.get(UserKey.getById, ""+1, User.class);
		return Result.success(user);
	}

	@RequestMapping("/set")
	public Result<Boolean> set(){
		User user = new User();
		user.setId(1);
		user.setName("allen");
		redisService.set(UserKey.getById, ""+1, user);//UserKey:id1
		return Result.success(true);
	}
}
