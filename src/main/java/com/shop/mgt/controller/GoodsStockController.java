package com.shop.mgt.controller;

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

import com.shop.mgt.model.GoodsStockDTO;
import com.shop.mgt.service.GoodsStockService;
import com.shop.mgt.utils.CommonUtils;

/**
 * @className:GoodsStockController
 * @description:商品库存明细
 * @author hj
 * @date 2018年4月10日
 */
@Controller
@RequestMapping("/stock")
public class GoodsStockController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(GoodsStockController.class);
	@Autowired
	private GoodsStockService goodsStockService;
	
	@RequestMapping("/index")
	public String welcome() {
		return "goodsStock";
	}

	/**
	 * 库存信息列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> listGodsStock(HttpServletRequest request) {
		GoodsStockDTO dto = CommonUtils.request2Bean(request, GoodsStockDTO.class);
		Map<String, Object> resp = new HashMap<>();
		List<GoodsStockDTO> rows = null;
		int total = 0;
		try {
			rows = goodsStockService.listGoodsStock(dto);
			total = goodsStockService.listGoodsStockCount(dto);
			resp.put("rows", rows);
			resp.put("total", total);
			return resp;
		} catch (Exception e) {
			logger.error("[GoodsStockController.listGoodsStock()] 异常信息 : " + e);
		}
		return null;
	}

	/**
	 * 单个库存信息
	 */
	@RequestMapping("/info")
	@ResponseBody
	public GoodsStockDTO getGoodsStockById(@RequestBody GoodsStockDTO  dto ) {
		GoodsStockDTO goods = new GoodsStockDTO();
		try {
			if (null !=(dto.getId()))
				goods = goodsStockService.getGoodsStockById(dto.getId());
		} catch (Exception e) {
			logger.error("[GoodsStockController.getGoodsStockById()] 异常信息 : " + e.getMessage());
		}
		return goods;
	}

	/**
	 * 新建商品库存信息
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String saveGoodsStock(HttpServletRequest request) {
		int res = 0;
		try {
			String id = request.getParameter("id_add");
			String nowQuantity = request.getParameter("now_quantity_add");
			String emploee = request.getParameter("input_emploee_add");

			if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(nowQuantity) && StringUtils.isNotBlank(emploee)) {
				GoodsStockDTO dto = new GoodsStockDTO();
				dto.setGoods_id(Integer.valueOf(id));
				dto.setNow_quantity(Integer.valueOf(nowQuantity));
				dto.setInput_emploee(emploee);
				res = goodsStockService.saveGoodsStock(dto);
			}
		} catch (Exception e) {
			logger.error("[GoodsStockController.saveGoodsStock()] 异常信息 : " + e.getMessage());
		}
		return res > 0 ? "true" : "false";
	}
	
	/**
	 * 删除商品信息
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteGoodsStock(@RequestBody GoodsStockDTO[] dtos) {
		int res = 0;
		try {
			if (dtos != null && dtos.length > 0) {
				res = goodsStockService.deleteGoodsStock(dtos);
			}
		} catch (Exception e) {
			logger.error("[GoodsStockController.deleteGoodsStock()] 异常信息 : {}", e);
		}
		return res > 0 ? "true" : "false";
	}
	
	/**
	 * 更新商品库存信息
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateGoodsStock(HttpServletRequest request) {
		GoodsStockDTO dto = CommonUtils.request2Bean(request, GoodsStockDTO.class);
		int res = 0;
		try {
			int id = dto.getId();
			if (id > 0) {
				res = goodsStockService.updateGoodsStock(dto);
			}
		} catch (Exception e) {
			logger.error("[GoodsStockController.updateGoodsStock()] 异常信息 : " + e.getMessage());
		}
		return res > 0 ? "true" : "false";
	}
}
