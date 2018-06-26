package com.moyhui.miaosha.dao;

import com.moyhui.miaosha.domain.MiaoshaGoods;
import com.moyhui.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

	@Select("SELECT g.*,mg.end_date,mg.start_date,mg.stock_count,mg.miaosha_price FROM `miaosha_goods` mg LEFT JOIN goods g on mg.goods_id = g.id")
	public List<GoodsVo> listGoodsVo();

	@Select("SELECT g.*,mg.end_date,mg.start_date,mg.stock_count,mg.miaosha_price FROM `miaosha_goods` mg LEFT JOIN goods g on mg.goods_id = g.id where g.id=#{goodsId}")
	public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

	@Update("update miaosha_goods set stock_count = stock_count -1 where goods_id = #{goodsId}")
	public int reduceStock(MiaoshaGoods miaoshaGoods);
}
