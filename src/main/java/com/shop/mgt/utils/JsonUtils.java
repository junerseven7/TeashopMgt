/*
*
*/  
package com.shop.mgt.utils;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**  
* @className:JsonUtils.java
* @description:
* @author hj  
* @date 2018年4月12日  
*/
public class JsonUtils {
	
	/**  
	 * Title: getJSON
	 * Description:对象转json串 
	 * @param obj
	 * @return  
	 */  
	public static String getObjectToJson(Object obj) {
		if (null == obj) {
			return "";
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		String jsonStr = "";
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}
	
}
