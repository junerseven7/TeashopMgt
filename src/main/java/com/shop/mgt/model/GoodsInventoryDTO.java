/*
*
*/
package com.shop.mgt.model;

import java.io.Serializable;

/**
 * @className:GoodsInventory.java
 * @description:商品盘点明细表
 * @author hj
 * @date 2018年4月10日
 */
public class GoodsInventoryDTO extends PaginationModel implements Serializable {

	private static final long serialVersionUID = -7257812841584060663L;

	private Integer id;//
	private Integer goods_id;// 商品表ID FK
	private String goods_name;// 商品名字（关联获得）
	private Integer existing_inventory;// 现有库存(参照“库存明细表”的"NOW_QUANTITY"字段)
	private Integer check_inventory;// 盘点库存
	private Integer inventory_difference;// 库存差异(实时库存-盘点后的库存)
	private String inventory_date;// 盘点日期
	private Integer adjust_inventory;// 手动调整增减库存
	private String crt_dttm;// 创建时间
	private String lastupt_dttm;// 最后修改时间
	private String enable_flg;// 数据是否有效(0:无效;1:有效)

	private String inventory_date_start;// 查询条件
	private String inventory_date_end;// 查询条件

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getInventory_date_start() {
		return inventory_date_start;
	}

	public void setInventory_date_start(String inventory_date_start) {
		this.inventory_date_start = inventory_date_start;
	}

	public String getInventory_date_end() {
		return inventory_date_end;
	}

	public void setInventory_date_end(String inventory_date_end) {
		this.inventory_date_end = inventory_date_end;
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

	public Integer getExisting_inventory() {
		return existing_inventory;
	}

	public void setExisting_inventory(Integer existing_inventory) {
		this.existing_inventory = existing_inventory;
	}

	public Integer getCheck_inventory() {
		return check_inventory;
	}

	public void setCheck_inventory(Integer check_inventory) {
		this.check_inventory = check_inventory;
	}

	public Integer getInventory_difference() {
		return inventory_difference;
	}

	public void setInventory_difference(Integer inventory_difference) {
		this.inventory_difference = inventory_difference;
	}

	public String getInventory_date() {
		return inventory_date;
	}

	public void setInventory_date(String inventory_date) {
		this.inventory_date = inventory_date;
	}

	public Integer getAdjust_inventory() {
		return adjust_inventory;
	}

	public void setAdjust_inventory(Integer adjust_inventory) {
		this.adjust_inventory = adjust_inventory;
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

	public String getEnable_flg() {
		return enable_flg;
	}

	public void setEnable_flg(String enable_flg) {
		this.enable_flg = enable_flg;
	}

	@Override
	public String toString() {
		return "GoodsInventoryDTO [id=" + id + ", goods_id=" + goods_id + ", goods_name=" + goods_name + ", existing_inventory=" + existing_inventory + ", check_inventory=" + check_inventory
				+ ", inventory_difference=" + inventory_difference + ", inventory_date=" + inventory_date + ", adjust_inventory=" + adjust_inventory + ", crt_dttm=" + crt_dttm + ", lastupt_dttm="
				+ lastupt_dttm + ", enable_flg=" + enable_flg + ", inventory_date_start=" + inventory_date_start + ", inventory_date_end=" + inventory_date_end + "]";
	}
	
}
