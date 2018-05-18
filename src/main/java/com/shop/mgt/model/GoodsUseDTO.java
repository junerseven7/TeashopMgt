package com.shop.mgt.model;

import java.io.Serializable;

/**
 * @className:GoodsUseDTO.java
 * @description:商品使用明细表
 * @author hj
 * @date 2018年4月10日
 */
public class GoodsUseDTO extends PaginationModel implements Serializable {
	private static final long serialVersionUID = 7822135374230904307L;

	private Integer id;
	private Integer goods_id;// 商品表ID FK
	private Integer quantity;// 使用量(记数单位参考商品表)
	private String use_emploee;// 使用人
	private String use_dttm;// 使用的时间
	private Integer is_effect;// 是否生效
	private String crt_dttm;// 创建时间
	private String lastupt_dttm;// 最后修改时间
	private String enable_flg;// 数据是否有效(0:无效;1:有效)

	private String goods_name;// 商品名字（关联获得）
	private String use_dttm_start;// 查询条件
	private String use_dttm_end;// 查询条件

	public Integer getIs_effect() {
		return is_effect;
	}

	public void setIs_effect(Integer is_effect) {
		this.is_effect = is_effect;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getUse_emploee() {
		return use_emploee;
	}

	public void setUse_emploee(String use_emploee) {
		this.use_emploee = use_emploee;
	}

	public String getUse_dttm() {
		return use_dttm;
	}

	public void setUse_dttm(String use_dttm) {
		this.use_dttm = use_dttm;
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

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getUse_dttm_start() {
		return use_dttm_start;
	}

	public void setUse_dttm_start(String use_dttm_start) {
		this.use_dttm_start = use_dttm_start;
	}

	public String getUse_dttm_end() {
		return use_dttm_end;
	}

	public void setUse_dttm_end(String use_dttm_end) {
		this.use_dttm_end = use_dttm_end;
	}

	public String getEnable_flg() {
		return enable_flg;
	}

	public void setEnable_flg(String enable_flg) {
		this.enable_flg = enable_flg;
	}

}
