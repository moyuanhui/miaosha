package com.moyhui.miaosha.controller;

import com.moyhui.miaosha.redis.RedisService;
import com.moyhui.miaosha.result.CodeMsg;
import com.moyhui.miaosha.result.Result;
import com.moyhui.miaosha.service.MiaoShaUserService;
import com.moyhui.miaosha.service.UserService;
import com.moyhui.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@RequestMapping("/login")
public class LoginController {


	private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	RedisService redisService;

	@Autowired
	MiaoShaUserService userService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

	@RequestMapping("/do_login")
	@ResponseBody
	public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
		log.info(loginVo.toString());
		//登录
		userService.login(response, loginVo);
		return Result.success(true);
	}

}
