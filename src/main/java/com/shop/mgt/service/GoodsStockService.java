package com.shop.mgt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mgt.model.GoodsStockDTO;
import com.shop.mgt.model.UpdateType;
import com.shop.mgt.repository.GoodsStockRepository;

/**  
* @className:GoodsStockService.java
* @description:
* @author hj  
* @date 2018年4月10日  
*/  
@Service
public class GoodsStockService {
	@Autowired
	private GoodsStockRepository goodsStockRepository;
	
	
	@Transactional(readOnly = true)
	public List<GoodsStockDTO> listGoodsStock(GoodsStockDTO dto) throws Exception {
		return goodsStockRepository.listGoodsStockInfo(dto);
	}
	
	@Transactional(readOnly = true)
	public int listGoodsStockCount(GoodsStockDTO info) throws Exception {
		return goodsStockRepository.listGoodsStockInfoCount(info);
	}
	
	
	@Transactional(readOnly = true)
	public GoodsStockDTO getGoodsStockById(int id) throws Exception{
		return goodsStockRepository.getGoodsStockById(id);
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public int saveGoodsStock(GoodsStockDTO dto) throws Exception{
		return goodsStockRepository.saveGoodsStock(dto);
	}
	
	public int deleteGoodsStock(GoodsStockDTO[] dtos) throws Exception{
		int res = 0;
		for (GoodsStockDTO dto : dtos) {
			res += goodsStockRepository.deleteGoodsStock(dto);
		}
		return res;
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public int updateGoodsStock(GoodsStockDTO dto) throws Exception{
		return goodsStockRepository.updateGoodsStock(dto,UpdateType.NORMAL);
	}
	
}
