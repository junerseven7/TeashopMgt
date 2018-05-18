package com.shop.mgt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.mgt.model.GoodsInventoryDTO;
import com.shop.mgt.service.GoodsInventoryService;
import com.shop.mgt.utils.CommonUtils;

/**  
* @className:GoodsInventoryController.java
* @description:盘点明细
* @author hj  
* @date 2018年4月18日  
*/
@Controller
@RequestMapping("/inventory")
public class GoodsInventoryController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(GoodsInventoryController.class);

	@Autowired
	private GoodsInventoryService goodsInventoryService;

	@RequestMapping("/index")
	public String welcome() {
		return "goodsInventory";
	}
	
	/**
	 * 盘点信息记录列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> listGoodsInventory(HttpServletRequest request) {
		GoodsInventoryDTO dto = CommonUtils.request2Bean(request, GoodsInventoryDTO.class);
		Map<String, Object> resp = new HashMap<>();
		List<GoodsInventoryDTO> rows = null;
		int total = 0;
		try {
			rows = goodsInventoryService.listGoodsInventory(dto);
			total = goodsInventoryService.listGoodsInventoryCount(dto);
			resp.put("rows", rows);
			resp.put("total", total);
			return resp;
		} catch (Exception e) {
			logger.error("[GoodsInventoryController.listGoodsInventory()] 异常信息 : " + e);
		}
		return null;
	}
	
	/**
	 * 删除盘点记录
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteGoodsInventory(@RequestBody GoodsInventoryDTO[] dtos) {
		int res = 0;
		try {
			if (dtos != null && dtos.length > 0) {
				res = goodsInventoryService.deleteGoodsInventory(dtos);
			}
		} catch (Exception e) {
			logger.error("[GoodsInventoryController.deleteGoodsInventory()] 异常信息 : {}", e);
		}
		return res > 0 ? "true" : "false";
	}
	
	/**
	 * 新建盘点记录
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Boolean saveGoodsInventory(HttpServletRequest request) {
		int res = 0;
		try {
			String goodsId = request.getParameter("goods_id_add");
			String checkInventory = request.getParameter("check_inventory_add");
			String inventoryDate = request.getParameter("inventory_date_add");

			if (StringUtils.isBlank(goodsId) || StringUtils.isBlank(checkInventory) || StringUtils.isBlank(inventoryDate))
				return false;

			GoodsInventoryDTO dto = new GoodsInventoryDTO();
			dto.setGoods_id(Integer.valueOf(goodsId));
			dto.setCheck_inventory(Integer.valueOf(checkInventory));
			dto.setInventory_date(inventoryDate);
			res = goodsInventoryService.saveGoodsInventory(dto);
		} catch (Exception e) {
			logger.error("[GoodsInventoryController.saveGoodsInventory()] 异常信息 : {}", e);
		}
		return res > 0 ? true : false;
	}
	
	/**
	 * 查询单个盘点记录
	 */
	@RequestMapping("/info")
	@ResponseBody
	public GoodsInventoryDTO getGoodsInventoryById(@RequestBody GoodsInventoryDTO dto) throws Exception{
		GoodsInventoryDTO goods = new GoodsInventoryDTO();
		try {
			if (null != dto.getId() && dto.getGoods_id() != null)
				goods = goodsInventoryService.getGoodsInventoryById(dto.getId(),dto.getGoods_id());
		} catch (Exception e) {
			logger.error("[GoodsInventoryController.getGoodsInventoryById()] 异常信息 : {}", e);
			throw e;
		}
		return goods;
	}
	
	/**
	 * 更新盘点记录
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateGoodsInventory(HttpServletRequest request) {
		GoodsInventoryDTO dto = CommonUtils.request2Bean(request, GoodsInventoryDTO.class);
		int res = 0;
		try {
			if("".equals(request.getParameter("adjust_inventory")))
				dto.setAdjust_inventory(null);
			
			if (dto.getId() > 0 && dto.getGoods_id() > 0 && null != dto.getCheck_inventory() && StringUtils.isNotBlank(dto.getInventory_date()))
				res = goodsInventoryService.updateGoodsInventory(dto);
		} catch (Exception e) {
			logger.error("[GoodsInventoryController.updateGoodsInventory()] 异常信息 : {}",e);
		}
		return res > 0 ? "true" : "false";
	}
	
	/**
	 * 每日盘点汇总
	 */
	@RequestMapping(value = "/summary", method = RequestMethod.POST)
	@ResponseBody
	public List<GoodsInventoryDTO> listGoodsInventorySummary(HttpServletRequest request) {
		List<GoodsInventoryDTO> dto = new ArrayList<>();
		try {
			String inventory_summary_date = request.getParameter("inventory_summary_date");
			if (StringUtils.isNotBlank(inventory_summary_date)) {
				dto = goodsInventoryService.listGoodsInventorySummary(inventory_summary_date);
			}
		} catch (Exception e) {
			logger.error("[GoodsInventoryController.listGoodsInventorySummary()] 异常信息 : {}",e);
		}
		return dto;
	}
}
