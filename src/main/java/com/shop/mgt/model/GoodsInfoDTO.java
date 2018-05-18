package com.shop.mgt.model;

import java.io.Serializable;

/**  
* @className:GoodsInfoDTO.java
* @description:产品信息
* @author hj  
* @date 2018年4月4日  
*/  
public class GoodsInfoDTO extends PaginationModel implements Serializable {

	private static final long serialVersionUID = -3833401238702654730L;

	private Integer id;
	private String goods_num;// 商品编码
	private String goods_type;// 商品类别
	private String goods_name;// 商品名称
	private String specification;// 规格
	private String unit;// 单位
	private Double price;// 单价
	private String crt_dttm;// 创建时间
	private String lastupt_dttm;// 最后修改时间
	private String enable_flg;// 数据是否有效(0:无效;1:有效)

	@Override
	public String toString() {
		return "GoodsInfoDTO [id=" + id + ", goods_num=" + goods_num + ", goods_type=" + goods_type + ", goods_name=" + goods_name + ", specification=" + specification + ", unit=" + unit + ", price="
				+ price + ", crt_dttm=" + crt_dttm + ", lastupt_dttm=" + lastupt_dttm + ", enable_flg=" + enable_flg + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
//	@JsonSerialize(using = CustomDateSerializer.class)  
//	@JsonSerialize(using = CustomDateSerializer.class) 

	public String getCrt_dttm() {
		return crt_dttm;
	}

	public void setCrt_dttm(String crt_dttm) {
		this.crt_dttm = crt_dttm;
	}

	public void setLastupt_dttm(String lastupt_dttm) {
		this.lastupt_dttm = lastupt_dttm;
	}

	public String getLastupt_dttm() {
		return lastupt_dttm;
	}

	public String getEnable_flg() {
		return enable_flg;
	}

	public void setEnable_flg(String enable_flg) {
		this.enable_flg = enable_flg;
	}

}
