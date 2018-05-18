package com.shop.mgt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mgt.constants.CommonConstants;
import com.shop.mgt.model.GoodsInfoDTO;
import com.shop.mgt.model.GoodsStockDTO;
import com.shop.mgt.model.GoodsUseDTO;
import com.shop.mgt.model.UpdateType;
import com.shop.mgt.repository.GoodsInfoRepository;
import com.shop.mgt.repository.GoodsStockRepository;
import com.shop.mgt.repository.GoodsUseRepository;


/**
 * @className:GoodsUseService.java
 * @description:商品使用
 * @author hj
 * @date 2018年4月24日
 */
@Service
public class GoodsUseService {
	private final static Logger logger = LoggerFactory.getLogger(GoodsUseService.class);
	@Autowired
	private GoodsUseRepository goodsUseRepository;
	@Autowired
	private GoodsInfoRepository goodsInfoRepository;
	@Autowired
	private GoodsStockRepository goodsStockRepository;
	
	@Transactional(readOnly = true)
	public List<GoodsUseDTO> listGoodsUse(GoodsUseDTO dto) throws Exception {
		return goodsUseRepository.listGoodsUse(dto);
	}

	@Transactional(readOnly = true)
	public int listGoodsUseCount(GoodsUseDTO info) throws Exception {
		return goodsUseRepository.listGoodsUseCount(info);
	}
	
	public int deleteGoodsUse(GoodsUseDTO[] dtos) throws Exception{
		int res = 0;
		for (GoodsUseDTO dto : dtos) {
			res += goodsUseRepository.deleteGoodsUse(dto);
		}
		return res;
	}
	
	/**  
	 * Title: saveGoodsUse
	 * Description:保存新的商品使用订单
	 * @param dto
	 * @return
	 * @throws Exception  
	 */  
	@Transactional(rollbackFor = RuntimeException.class)
	public int saveGoodsUse(GoodsUseDTO dto) throws Exception {
		GoodsInfoDTO goodsPO = goodsInfoRepository.getGoodById(dto.getGoods_id());
		if (null == goodsPO )
			return 0;
		dto.setIs_effect(CommonConstants.IS_EFFECT_FALSE);// 默认未生效
		return goodsUseRepository.saveGoodsUse(dto);
	}
	
	@Transactional(readOnly = true)
	public GoodsUseDTO getGoodsUseById(int id) throws Exception{
		return goodsUseRepository.getGoodsUseById(id);
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public int updateGoodsUse(GoodsUseDTO dto) throws Exception{
		return goodsUseRepository.updateGoodsUse(dto);
	}
	
	
	/**  
	 * Title: effectOrderInfo
	 * Description: 使得商品使用记录生效直接扣减库存
	 * @param dtos
	 * @return
	 * @throws Exception  
	 */  
	@Transactional(rollbackFor = RuntimeException.class)
	public synchronized int effectGoodsUse(GoodsUseDTO[] dtos) throws Exception {
		int res = 0;
		for (GoodsUseDTO dto : dtos) {
			/** 根据Id查询订单是否存在 ，或者是否已经为"已生效"状态 */
			GoodsUseDTO goPO = goodsUseRepository.getGoodsUseById(dto.getId());
			if (null == goPO || goPO.getIs_effect() == CommonConstants.IS_EFFECT_TURE)
				continue;

			GoodsStockDTO po = new GoodsStockDTO();
			po.setNow_quantity(dto.getQuantity());
			po.setGoods_id(dto.getGoods_id());
			GoodsStockDTO gsDTO = goodsStockRepository.getGoodsStock(po);
			if (gsDTO == null) {
				logger.info("[GoodsUseService.effectGoodsUse] 库存ID={}信息不存在，使用记录无法生效", dto.getGoods_id());
				continue;
			}
			if (gsDTO.getNow_quantity() - dto.getQuantity() < 0) {
				logger.info("[GoodsUseService.effectGoodsUse] 库存ID={},现有库存{},需要使用的库存{},扣减为负数。",dto.getGoods_id(),gsDTO.getNow_quantity(), dto.getQuantity());
				continue;
			}
			/** 更新商品库存信息 */
			goodsStockRepository.updateGoodsStock(po, UpdateType.ACCUMULATE_SUB);
			/** 更新订单为"已生效"状态 */
			dto.setIs_effect(CommonConstants.IS_EFFECT_TURE);
			res += goodsUseRepository.effectGoodsUse(dto);
		}
		return res;
	}
	
}
