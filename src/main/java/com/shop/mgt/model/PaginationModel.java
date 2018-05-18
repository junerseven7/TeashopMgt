package com.shop.mgt.model;

import java.io.Serializable;

/**
 * @className:PaginationModel
 * @description:分页组建
 * @author hj
 * @date 2018年4月12日
 */
public class PaginationModel implements Serializable {

	private static final long serialVersionUID = -533969725771021868L;
	private int rows = 0;
	private int page = 0;
	private int limitNum = 0;

	@Override
	public String toString() {
		return "BaseModule [rows=" + rows + ", page=" + page + ", limitNum=" + limitNum + "]";
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimitNum() {
		return rows * (page - 1);
	}

	public void setLimitNum(int limitNum) {
		this.limitNum = rows * (page - 1);
	}
}
