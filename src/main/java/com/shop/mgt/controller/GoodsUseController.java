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

import com.shop.mgt.constants.CommonConstants;
import com.shop.mgt.model.GoodsUseDTO;
import com.shop.mgt.service.GoodsUseService;
import com.shop.mgt.utils.CommonUtils;

/**  
* @className:GoodsUseController.java
* @description:商品使用
* @author hj  
* @date 2018年4月24日  
*/
@Controller
@RequestMapping("/use")
public class GoodsUseController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(GoodsUseController.class);
	@Autowired
	private GoodsUseService goodsUseService;
	
	@RequestMapping("/index")
	public String welcome() {
		return "goodsUse";
	}
	
	/**
	 * 订单信息列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> listGoodsUse(HttpServletRequest request) {
		GoodsUseDTO dto = CommonUtils.request2Bean(request, GoodsUseDTO.class);
		Map<String, Object> resp = new HashMap<>();
		List<GoodsUseDTO> rows = null;
		int total = 0;
		try {
			rows = goodsUseService.listGoodsUse(dto);
			total = goodsUseService.listGoodsUseCount(dto);
			resp.put("rows", rows);
			resp.put("total", total);
			return resp;
		} catch (Exception e) {
			logger.error("[GoodsUseController.goodsUseService()] 异常信息 : " + e);
		}
		return null;
	}
	
	/**
	 * 删除商品使用记录
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteGoodsUse(@RequestBody GoodsUseDTO[] dtos) {
		int res = 0;
		try {
			if (dtos != null && dtos.length > 0) {
				res = goodsUseService.deleteGoodsUse(dtos);
			}
		} catch (Exception e) {
			logger.error("[GoodsUseController.deleteGoodsUse()] 异常信息 : {}", e);
		}
		return res > 0 ? "true" : "false";
	}
	
	/**
	 * 新建商品使用记录
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Boolean saveGoodsUse(HttpServletRequest request) {
		int res = 0;
		try {
			String id = request.getParameter("id_add");
			String quantity = request.getParameter("quantity_add");
			String use_emploee = request.getParameter("use_emploee_add");
			String use_dttm = request.getParameter("use_dttm_add");

			if (StringUtils.isBlank(id) || StringUtils.isBlank(quantity) || StringUtils.isBlank(use_dttm))
				return false;

			GoodsUseDTO dto = new GoodsUseDTO();
			dto.setGoods_id(Integer.valueOf(id));
			dto.setQuantity(Integer.valueOf(quantity));
			dto.setUse_dttm(use_dttm);
			if (StringUtils.isNotBlank(use_emploee))
				dto.setUse_emploee(use_emploee);

			res = goodsUseService.saveGoodsUse(dto);
		} catch (Exception e) {
			logger.error("[GoodsUseController.saveGoodsUse()] 异常信息 : {}", e);
		}
		return res > 0 ? true : false;
	}
	
	/**
	 * 查询单个商品使用记录
	 */
	@RequestMapping("/info")
	@ResponseBody
	public GoodsUseDTO getGoodsUseById(@RequestBody GoodsUseDTO dto) throws Exception{
		GoodsUseDTO goods = new GoodsUseDTO();
		try {
			if (dto.getIs_effect() == CommonConstants.IS_EFFECT_TURE)
				throw new RuntimeException("无法修改已经生效的记录");

			if (null != (dto.getId()))
				goods = goodsUseService.getGoodsUseById(dto.getId());
		} catch (Exception e) {
			logger.error("[GoodsUseController.getGoodsUseById()] 异常信息 : {}", e);
			throw e;
		}	
		return goods;
	}
	
	/**
	 * 更新商品使用记录
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateGoodsUse(HttpServletRequest request) {
		GoodsUseDTO dto = CommonUtils.request2Bean(request, GoodsUseDTO.class);
		int res = 0;
		try {
			int id = dto.getId();
			if (id > 0) {
				res = goodsUseService.updateGoodsUse(dto);
			}
		} catch (Exception e) {
			logger.error("[GoodsUseController.updateGoodsUse()] 异常信息 : " + e.getMessage());
		}
		return res > 0 ? "true" : "false";
	}
	
	/**
	 * 商品使用记录生效
	 */
	@RequestMapping(value = "/isEffect", method = RequestMethod.POST)
	@ResponseBody
	public String effectGoodsUse(@RequestBody GoodsUseDTO[] dtos) {
		int res = 0;
		try {
			if (dtos != null && dtos.length > 0) {
				res = goodsUseService.effectGoodsUse(dtos);
			}
		} catch (Exception e) {
			logger.error("[GoodsUseController.effectGoodsUse()] 异常信息 : {}", e);
		}
		return res > 0 ? "true" : "false";
	}
}
