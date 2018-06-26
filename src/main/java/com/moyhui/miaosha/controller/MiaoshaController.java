package com.moyhui.miaosha.controller;

import com.moyhui.miaosha.domain.MiaoshaOrder;
import com.moyhui.miaosha.domain.MiaoshaUser;
import com.moyhui.miaosha.domain.OrderInfo;
import com.moyhui.miaosha.result.CodeMsg;
import com.moyhui.miaosha.service.GoodsService;
import com.moyhui.miaosha.service.MiaoshaService;
import com.moyhui.miaosha.service.OrderService;
import com.moyhui.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	@Autowired
	MiaoshaService miaoshaService;

	@RequestMapping("/do_miaosha")
	public String list(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){
		model.addAttribute("user",user);
		if(user == null){
			return "login";
		}

		//判断库存
		GoodsVo goodsVo = goodsService.getGoodsByGoodsId(goodsId);
		int stock = goodsVo.getStockCount();
		if(stock <= 0){
			model.addAttribute("errmsg",CodeMsg.MIAO_SHA_OVER.getMsg());
			return "miaosha_fail";
		}

		//判断是否已经秒杀到
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
		if(order != null){
			model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
			return "miaosha_fail";
		}

		//减库存
		OrderInfo orderInfo = miaoshaService.miaosha(user,goodsVo);
		model.addAttribute("orderInfo",orderInfo);
		model.addAttribute("goods",goodsVo);
		return "order_detail";
	}
}






































































































