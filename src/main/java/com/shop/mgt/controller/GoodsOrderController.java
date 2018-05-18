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

import com.shop.mgt.constants.CommonConstants;
import com.shop.mgt.model.GoodsOrderDTO;
import com.shop.mgt.service.OrderService;
import com.shop.mgt.utils.CommonUtils;

/**  
* @className:OrderController
* @description:订单信息
* @author hj  
* @date 2018年4月10日  
*/
@Controller
@RequestMapping("/order")
public class GoodsOrderController extends BaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(GoodsOrderController.class);
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/index")
	public String welcome() {
		return "goodsOrder";
	}
	
	/**
	 * 订单信息列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> listOrder(HttpServletRequest request) {
		GoodsOrderDTO dto = CommonUtils.request2Bean(request, GoodsOrderDTO.class);
		Map<String, Object> resp = new HashMap<>();
		List<GoodsOrderDTO> rows = null;
		int total = 0;
		try {
			rows = orderService.listOrder(dto);
			total = orderService.listOrderCount(dto);
			resp.put("rows", rows);
			resp.put("total", total);
			return resp;
		} catch (Exception e) {
			logger.error("[GoodsOrderController.listOrder()] 异常信息 : " + e);
		}
		return null;
	}

	/**
	 * 删除订单信息
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteOrderInfo(@RequestBody GoodsOrderDTO[] dtos) {
		int res = 0;
		try {
			if (dtos != null && dtos.length > 0) {
				res = orderService.deleteOrderInfo(dtos);
			}
		} catch (Exception e) {
			logger.error("[GoodsOrderController.deleteOrderInfo()] 异常信息 : {}", e);
		}
		return res > 0 ? "true" : "false";
	}
	
	/**
	 * 新建订单
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Boolean saveOrder(HttpServletRequest request) {
		int res = 0;
		try {
			String id = request.getParameter("id_add");
			String goods_count = request.getParameter("goods_count_add");
			String order_date = request.getParameter("order_date_add");

			if (StringUtils.isBlank(id) || StringUtils.isBlank(goods_count) || StringUtils.isBlank(order_date))
				return false;

			GoodsOrderDTO dto = new GoodsOrderDTO();
			dto.setGoods_id(Integer.valueOf(id));
			dto.setGoods_count(Integer.valueOf(goods_count));
			dto.setOrder_date(order_date);
			
			res = orderService.saveOrder(dto);
		} catch (Exception e) {
			logger.error("[GoodsOrderController.saveOrder()] 异常信息 : {}", e);
		}
		return res > 0 ? true : false;
	}
	
	/**
	 * 单个订单信息
	 */
	@RequestMapping("/info")
	@ResponseBody
	public GoodsOrderDTO getGoodsStockById(@RequestBody GoodsOrderDTO dto) throws Exception{
		GoodsOrderDTO goods = new GoodsOrderDTO();
		try {
			if (dto.getIs_effect() == CommonConstants.IS_EFFECT_TURE)
				throw new RuntimeException("无法修改已经生效的订单");

			if (null != (dto.getId()))
				goods = orderService.getOrderById(dto.getId());
		} catch (Exception e) {
			logger.error("[GoodsOrderController.getGoodsStockById()] 异常信息 : {}", e);
			throw e;
		}
		return goods;
	}

	/**
	 * 更新订单
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateOrder(HttpServletRequest request) {
		GoodsOrderDTO dto = CommonUtils.request2Bean(request, GoodsOrderDTO.class);
		int res = 0;
		try {
			int id = dto.getId();
			if (id > 0) {
				res = orderService.updateOrder(dto);
			}
		} catch (Exception e) {
			logger.error("[GoodsOrderController.updateOrder()] 异常信息 : " + e.getMessage());
		}
		return res > 0 ? "true" : "false";
	}
	
	/**
	 * 订单生效
	 */
	@RequestMapping(value = "/isEffect", method = RequestMethod.POST)
	@ResponseBody
	public String effectOrderInfo(@RequestBody GoodsOrderDTO[] dtos) {
		int res = 0;
		try {
			if (dtos != null && dtos.length > 0) {
				res = orderService.effectOrderInfo(dtos);
			}
		} catch (Exception e) {
			logger.error("[GoodsOrderController.effectOrderInfo()] 异常信息 : {}", e);
		}
		return res > 0 ? "true" : "false";
	}
	
	/**
	 * 每日订单汇总
	 */
	@RequestMapping(value = "/summary", method = RequestMethod.POST)
	@ResponseBody
	public List<GoodsOrderDTO> listOrderSummary(HttpServletRequest request) {
		List<GoodsOrderDTO> dto = new ArrayList<>();
		try {
			String order_summary_date = request.getParameter("order_summary_date");
			if (StringUtils.isNotBlank(order_summary_date)) {
				dto = orderService.listOrderSummary(order_summary_date);
			}
		} catch (Exception e) {
			logger.error("[GoodsOrderController.listOrderSummary()] 异常信息 : " + e.getMessage());
		}
		return dto;
	}
}
