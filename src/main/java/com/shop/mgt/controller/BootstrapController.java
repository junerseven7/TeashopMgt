package com.shop.mgt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.mgt.utils.JsonUtils;

@SuppressWarnings("unused")
@Controller
public class BootstrapController {
	private final static String Menu_Icon_Sys = "icon-sys";//一级目录
	private final static String Menu_Icon_Nav = "icon-nav";//二级目录
	 
	/**  
	 * Title: indexModule
	 * Description: admin首页
	 * @return  
	 */  
	@RequestMapping("/index")
	public String indexModule() {
		return "index";
	}
	
	/**  
	 * Title: welcomeModule
	 * Description: 欢迎页面
	 * @return  
	 */  
	@RequestMapping("/welcome")
	public String welcomeModule() {
		return "welcome";
	}
	
	/**  
	 * Title: menu
	 * Description:加载admin菜单 
	 * @return  
	 */  
	@RequestMapping("/menu")
	@ResponseBody
	public String menu(HttpServletRequest request) {
		System.out.println(request.getLocalAddr()+"||"+request.getLocalPort());
		request.getLocalAddr();
		request.getLocalPort();
		
		Map<String, Object> menus = new HashMap<String, Object>();
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		// 菜单1
		Map<String, Object> firstMap = new HashMap<String, Object>();
		firstMap.put("menuid", "1000");
		firstMap.put("menuname", "店铺管理");
		firstMap.put("icon", Menu_Icon_Sys);

		List<Map<String, Object>> menuSecondList = new ArrayList<Map<String, Object>>();
		// 菜单1.1
		Map<String, Object> secondMap = new HashMap<String, Object>();
		secondMap.put("menuid", "1010");
		secondMap.put("menuname", "商品管理");
		secondMap.put("icon", "icon-creditcards");
		secondMap.put("url", "/mgt/goods/index");
		menuSecondList.add(secondMap);
		// 菜单1.2
		Map<String, Object> secondMapTwo = new HashMap<String, Object>();
		secondMapTwo.put("menuid", "1011");
		secondMapTwo.put("menuname", "订单管理");
		secondMapTwo.put("icon", "icon-cart");
		secondMapTwo.put("url", "/mgt/order/index");
		menuSecondList.add(secondMapTwo);
		// 菜单1.3
		Map<String, Object> secondMapThree = new HashMap<String, Object>();
		secondMapThree.put("menuid", "1012");
		secondMapThree.put("menuname", "库存管理");
		secondMapThree.put("icon", "icon-book");
		secondMapThree.put("url", "/mgt/stock/index");
		menuSecondList.add(secondMapThree);
		// 菜单1.4
		Map<String, Object> secondMapFour = new HashMap<String, Object>();
		secondMapFour.put("menuid", "1013");
		secondMapFour.put("menuname", "商品使用管理");
		secondMapFour.put("icon", "icon-users");
		secondMapFour.put("url", "/mgt/use/index");
		menuSecondList.add(secondMapFour);
		// 菜单1.5
		Map<String, Object> secondMapFive = new HashMap<String, Object>();
		secondMapFive.put("menuid", "1014");
		secondMapFive.put("menuname", "商品盘点管理");
		secondMapFive.put("icon", "icon-chart-organisation");
		secondMapFive.put("url", "/mgt/inventory/index");
		menuSecondList.add(secondMapFive);

		firstMap.put("menus", menuSecondList);
		menuList.add(firstMap);
		
	
		// 菜单2
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("menuid", "1100");
		map2.put("menuname", "报表管理");
		map2.put("icon", "icon-chart-bar");
		
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		//菜单2.1
		Map<String, Object> secondMap22 = new HashMap<String, Object>();
		secondMap22.put("menuid", "1110");
		secondMap22.put("menuname",  "商品使用报表");
		secondMap22.put("icon", "icon-chart-curve");
		secondMap22.put("url", "/mgt/reports/goodsUse");
		list2.add(secondMap22);
		/*
		//菜单2.2
		Map<String, Object> secondMap33 = new HashMap<String, Object>();
		secondMap33.put("menuid", "1111");
		secondMap33.put("menuname", "配置信息");
		secondMap33.put("icon", "-ok");
		secondMap33.put("url", "https://bbs.touker.com");
		list2.add(secondMap33);
		*/	
		map2.put("menus", list2);
		menuList.add(map2);
		
		menus.put("menus", menuList);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", menus);
		return JsonUtils.getObjectToJson(resultMap);
	}
}
