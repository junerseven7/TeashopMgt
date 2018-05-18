/*
*
*/  
package com.shop.mgt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.mgt.service.ReportsService;

/**  
* @className:ReportsController.java
* @description:报表
* @author hj  
* @date 2018年4月19日  
*/
@Controller
@RequestMapping("/reports")
public class ReportsController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(ReportsController.class);

	@Autowired
	private ReportsService reportsService;
	
	@RequestMapping("/goodsUse")
	public String welcome() {
		return "reports/goodsUseReport";
	}
	
	/**  
	 * Title: listGoodsUse
	 * Description: 商品使用报表数据
	 * @param request
	 * @return  
	 * @throws Exception 
	 */  
	@RequestMapping("/inventorData")
	@ResponseBody
	public Map<String, List<Object>> listGoodsUse(HttpServletRequest request) throws Exception {
		if(null == request.getParameter("good_id"))
			return new HashMap<String, List<Object>>();
		
		int goodId =Integer.valueOf(request.getParameter("good_id"));
		logger.debug("[ReportsController.listGoodsUse] goodId = {}",goodId);
		return reportsService.listGoodsUse(goodId);
	}
	
}
