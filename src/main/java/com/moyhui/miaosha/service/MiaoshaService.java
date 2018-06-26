package com.moyhui.miaosha.service;

import com.moyhui.miaosha.domain.MiaoshaUser;
import com.moyhui.miaosha.domain.OrderInfo;
import com.moyhui.miaosha.domain.User;
import com.moyhui.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiaoshaService {

	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo){

		//减库存
		goodsService.reduceStock(goodsVo);

		return  orderService.createOrder(user, goodsVo);
	}
}
