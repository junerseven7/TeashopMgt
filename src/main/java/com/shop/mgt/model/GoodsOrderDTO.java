package com.shop.mgt.model;

import java.io.Serializable;

/**
 * @className:OrderInfo.java
 * @description:
 * @author hj
 * @date 2018年4月10日
 */
public class GoodsOrderDTO extends PaginationModel implements Serializable {

	private static final long serialVersionUID = -8687409139555834096L;

	private Integer id;
	private Integer goods_id;// 商品表ID FK
	private String goods_name;// 商品名字（关联获得）
	private Double goods_price;// 商品单价
	private Integer goods_count;// 购买数量
	private Double total_price;// 总价
	private String order_date;// 订单日期
	private Integer is_effect;// 订单是否生效(1:未生效 2：已生效)
	private String crt_dttm;// 创建时间
	private String lastupt_dttm;// 最后修改时间
	private String enable_flg;// 数据是否有效(0:无效;1:有效)

	private String order_date_start;// 查询条件
	private String order_date_end;// 查询条件

	public String getOrder_date_start() {
		return order_date_start;
	}

	public void setOrder_date_start(String order_date_start) {
		this.order_date_start = order_date_start;
	}

	public String getOrder_date_end() {
		return order_date_end;
	}

	public void setOrder_date_end(String order_date_end) {
		this.order_date_end = order_date_end;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
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

	public Double getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(Double goods_price) {
		this.goods_price = goods_price;
	}

	public Integer getGoods_count() {
		return goods_count;
	}

	public void setGoods_count(Integer goods_count) {
		this.goods_count = goods_count;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public Integer getIs_effect() {
		return is_effect;
	}

	public void setIs_effect(Integer is_effect) {
		this.is_effect = is_effect;
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

}
