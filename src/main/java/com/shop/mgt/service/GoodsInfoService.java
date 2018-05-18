package com.shop.mgt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mgt.model.DropDownVO;
import com.shop.mgt.model.GoodsInfoDTO;
import com.shop.mgt.repository.GoodsInfoRepository;

@Service
public class GoodsInfoService {
	@Autowired
	private GoodsInfoRepository goodsInfoRepository;
	
	@Transactional(readOnly = true)
	public List<GoodsInfoDTO> listGoods(GoodsInfoDTO info) throws Exception {
		return goodsInfoRepository.listGoodsInfo(info);
	}
	@Transactional(readOnly = true)
	public int listGoodsCount(GoodsInfoDTO info) throws Exception {
		return goodsInfoRepository.listGoodsInfoCount(info);
	}
	
	@Transactional(readOnly = true)
	public GoodsInfoDTO getGoodById(String id) throws Exception{
		return goodsInfoRepository.getGoodById(Integer.valueOf(id));
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public int saveGoods(GoodsInfoDTO dto) throws Exception{
		return goodsInfoRepository.saveGoods(dto);
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public int deleteGoodsInfo(GoodsInfoDTO[] dtos) throws Exception {
		int res = 0;
		for (GoodsInfoDTO dto : dtos) 
			res += goodsInfoRepository.deleteGoods(dto);
		
		return res;
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public int updateGoods(GoodsInfoDTO dto) throws Exception{
		return goodsInfoRepository.updateGoods(dto);
	}
	
	@Transactional(readOnly = true)
	public List<DropDownVO> listDropDownList() throws Exception{
		return goodsInfoRepository.listDropDownList();
	}
	
}
