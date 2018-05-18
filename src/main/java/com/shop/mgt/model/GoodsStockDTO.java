/*
*
*/
package com.shop.mgt.model;

import java.io.Serializable;

/**
 * @className:GoodsStockDTO.java
 * @description:商品库存明细表
 * @author hj
 * @date 2018年4月10日
 */
public class GoodsStockDTO extends PaginationModel implements Serializable {
	private static final long serialVersionUID = 3148321347812444346L;

	private Integer id;
	private Integer goods_id;// 商品表ID FK
	private String goods_name;// 商品名字（关联获得）
	private Integer now_quantity;// 现有商品库存个数
	private String input_emploee;// 录入人
	private String crt_dttm;// 创建时间
	private String lastupt_dttm;// 最后修改时间
	private String enable_flg;// 数据是否有效(0:无效;1:有效)

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}

	public Integer getNow_quantity() {
		return now_quantity;
	}

	public void setNow_quantity(Integer now_quantity) {
		this.now_quantity = now_quantity;
	}

	public String getInput_emploee() {
		return input_emploee;
	}

	public void setInput_emploee(String input_emploee) {
		this.input_emploee = input_emploee;
	}

	public String getCrt_dttm() {
		return crt_dttm;
	}

	public void setCrt_dttm(String crt_dttm) {
		this.crt_dttm = crt_dttm;
	}

	public String getLastupt_dttm() {
		return lastupt_dttm;
	}

	public void setLastupt_dttm(String lastupt_dttm) {
		this.lastupt_dttm = lastupt_dttm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEnable_flg() {
		return enable_flg;
	}

	public void setEnable_flg(String enable_flg) {
		this.enable_flg = enable_flg;
	}
}
