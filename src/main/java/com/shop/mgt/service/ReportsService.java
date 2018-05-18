/*
*
*/
package com.shop.mgt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mgt.model.GoodsUseDTO;
import com.shop.mgt.repository.ReportsRepository;

/**
 * @className:ReportsService.java
 * @description:报表服务
 * @author hj
 * @date 2018年4月20日
 */
@Service
public class ReportsService {
	private final static Logger logger = LoggerFactory.getLogger(ReportsService.class);

	@Autowired
	private ReportsRepository reportsRepository;

	/**  
	 * Title: listGoodsInventory
	 * Description: 获取商品使用个数列表
	 * @param goodId
	 * @return
	 * @throws Exception  
	 */  
	@Transactional(readOnly = true)
	public Map<String, List<Object>> listGoodsUse(int goodId) throws Exception {
		List<GoodsUseDTO> list = reportsRepository.listGoodsUse(goodId);

		if (null == list || list.size() < 1) {
			logger.info("[ReportsService.listGoodsUse] 查询商品ID={}的盘点记录不存在", goodId);
			return null;
		}

		Map<String, List<Object>> returnDataList = new HashMap<>();
		List<Object> useTime = new ArrayList<>();
		List<Object> inventoryDifference = new ArrayList<>();

		for (GoodsUseDTO po : list) {
			useTime.add(po.getUse_dttm());
			inventoryDifference.add(po.getQuantity());
		}

		returnDataList.put("date", useTime);
		returnDataList.put("data", inventoryDifference);
		return returnDataList;
	}

}
