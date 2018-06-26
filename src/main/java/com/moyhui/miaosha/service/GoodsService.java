package com.moyhui.miaosha.service;

import com.moyhui.miaosha.dao.GoodsDao;
import com.moyhui.miaosha.domain.MiaoshaGoods;
import com.moyhui.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

	@Autowired
	GoodsDao goodsDao;

	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsByGoodsId(long goodsId){
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	public void reduceStock(GoodsVo goodsVo){
		MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
		miaoshaGoods.setGoodsId(goodsVo.getId());
		goodsDao.reduceStock(miaoshaGoods);
	}
}
