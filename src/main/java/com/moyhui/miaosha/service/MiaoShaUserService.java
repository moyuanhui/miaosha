package com.moyhui.miaosha.service;

import com.moyhui.miaosha.dao.MiaoShaUserDao;
import com.moyhui.miaosha.domain.MiaoshaUser;
import com.moyhui.miaosha.exception.GlobalException;
import com.moyhui.miaosha.redis.MiaoshaUserKey;
import com.moyhui.miaosha.redis.RedisService;
import com.moyhui.miaosha.result.CodeMsg;
import com.moyhui.miaosha.utils.MD5Util;
import com.moyhui.miaosha.utils.UUIDUtil;
import com.moyhui.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoShaUserService {


	public static final String COOKI_NAME_TOKEN = "token";
	@Autowired
	MiaoShaUserDao miaoShaUserDao;

	@Autowired
	RedisService redisService;

	public MiaoshaUser getById(long id){
		return miaoShaUserDao.getById(id);
	}
	public MiaoshaUser getByToken(HttpServletResponse response, String token){
		if(StringUtils.isEmpty(token)){
			return  null;
		}
		MiaoshaUser user =  redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);

		//延长有效期
		if(user != null){
			addCookie(response,token,user);
		}
		return  user;
	}

	public boolean login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		//判断手机号是否存在
		MiaoshaUser user = getById(Long.parseLong(mobile));
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);

		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		//生成cookie
		String token	 = UUIDUtil.uuid();
		addCookie(response, token, user);
		return true;
	}

	private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
		redisService.set(MiaoshaUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}















}
