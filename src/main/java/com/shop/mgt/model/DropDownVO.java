package com.shop.mgt.model;

import java.io.Serializable;

/**
 * @className:DropDown.java
 * @description:下拉框控件obj
 * @author hj
 * @date 2018年4月17日
 */
public class DropDownVO implements Serializable {

	private static final long serialVersionUID = 253024624528644645L;
	private String dKey;
	private String dValue;

	public String getdKey() {
		return dKey;
	}

	public void setdKey(String dKey) {
		this.dKey = dKey;
	}

	public String getdValue() {
		return dValue;
	}

	public void setdValue(String dValue) {
		this.dValue = dValue;
	}

	@Override
	public String toString() {
		return "DropDown [dKey=" + dKey + ", dValue=" + dValue + "]";
	}

	
}
