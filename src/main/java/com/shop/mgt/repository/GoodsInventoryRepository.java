package com.shop.mgt.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.shop.mgt.model.GoodsInventoryDTO;

/**
 * @className:GoodsInventoryRepository.java
 * @description:
 * @author hj
 * @date 2018年4月18日
 */
@Repository
public class GoodsInventoryRepository {

	private final static Logger logger = LoggerFactory.getLogger(GoodsInventoryRepository.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**  
	 * Title: listGoodsInventory
	 * Description: 获取盘点列表
	 * @param dto
	 * @return
	 * @throws Exception  
	 */  
	public List<GoodsInventoryDTO> listGoodsInventory(GoodsInventoryDTO dto) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select a.id,a.goods_id,b.goods_name,a.existing_inventory,a.check_inventory,a.inventory_difference,a.inventory_date,a.adjust_inventory,a.crt_dttm,a.lastupt_dttm,a.enable_flg from goods_inventory a,goods b where a.goods_id=b.id and a.enable_flg=1 and b.enable_flg=1 ");

		if (null != (dto.getId()) && 0 != dto.getId()) {
			sql.append(" and a.id like ?   ");
			params.add("%" + dto.getId() + "%");
		}
		if (StringUtils.isNotBlank(dto.getGoods_name())) {
			sql.append(" and b.goods_name like ? ");
			params.add("%" + dto.getGoods_name() + "%");
		}
		if (StringUtils.isNotBlank(dto.getInventory_date_start())) {
			sql.append(" and inventory_date >= ? ");
			params.add(dto.getInventory_date_start());
		}
		if (StringUtils.isNotBlank(dto.getInventory_date_end())) {
			sql.append(" and inventory_date <=  ? ");
			params.add(dto.getInventory_date_end());
		}
		if (StringUtils.isNotBlank(dto.getInventory_date())) {
			sql.append(" and inventory_date =  ? ");
			params.add(dto.getInventory_date());
		}

		params.add(dto.getLimitNum());
		params.add(dto.getRows());
		sql.append(" order by a.lastupt_dttm desc,a.id desc limit ?, ?");
		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<GoodsInventoryDTO>(GoodsInventoryDTO.class));
	}

	/**  
	 * Title: listGoodsInventoryCount
	 * Description: 获取盘点记录总数
	 * @param dto
	 * @return
	 * @throws Exception  
	 */  
	public int listGoodsInventoryCount(GoodsInventoryDTO dto) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select count(a.id)  from goods_inventory a,goods b where a.goods_id=b.id and a.enable_flg=1 and b.enable_flg=1 ");

		if (null != (dto.getId()) && 0 != dto.getId()) {
			sql.append(" and a.id like ?   ");
			params.add("%" + dto.getId() + "%");
		}
		if (StringUtils.isNotBlank(dto.getGoods_name())) {
			sql.append(" and b.goods_name like ? ");
			params.add("%" + dto.getGoods_name() + "%");
		}
		if (StringUtils.isNotBlank(dto.getInventory_date_start())) {
			sql.append(" and inventory_date >= ? ");
			params.add(dto.getInventory_date_start());
		}
		if (StringUtils.isNotBlank(dto.getInventory_date_end())) {
			sql.append(" and inventory_date <=  ? ");
			params.add(dto.getInventory_date_end());
		}
		if (StringUtils.isNotBlank(dto.getInventory_date())) {
			sql.append(" and inventory_date =  ? ");
			params.add(dto.getInventory_date());
		}

		return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
	}
	
	/**
	 * 删除盘点单
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int deleteGoodsInventory(final GoodsInventoryDTO po) throws Exception {
		int resRow = jdbcTemplate.update("delete from goods_inventory where id=? and enable_flg=1", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, po.getId());
			}
		});
		logger.debug("[deleteGoodsInventory]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 新增盘点记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int saveGoodsInventory(final GoodsInventoryDTO po) throws Exception {
		int resRow = jdbcTemplate.update("insert into goods_inventory(goods_id,existing_inventory,check_inventory,inventory_difference,inventory_date) values(?,?,?,?,?)",
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setObject(1, po.getGoods_id());
						ps.setObject(2, po.getExisting_inventory());
						ps.setObject(3, po.getCheck_inventory());
						ps.setObject(4, po.getInventory_difference());
						ps.setObject(5, po.getInventory_date());
					}
				});
		logger.debug("[saveOrder]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 获取单个盘点详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GoodsInventoryDTO getGoodsInventoryById(int id) throws Exception{
		List<GoodsInventoryDTO> dtos = jdbcTemplate.query("select a.id,a.goods_id,b.goods_name,a.existing_inventory,a.check_inventory,a.inventory_difference,a.inventory_date,a.adjust_inventory from goods_inventory a,goods b where a.goods_id=b.id and a.enable_flg=1 and a.id=? ", new BeanPropertyRowMapper<GoodsInventoryDTO>(GoodsInventoryDTO.class), new Object[] { id });
		if(null != dtos && dtos.size()==1 )
			return dtos.get(0);
		return null;
	}
	
	/**
	 * 更新盘点记录
	 * @param po
	 * @return
	 */
	public int updateGoodsInventory(final GoodsInventoryDTO po) throws Exception {
		StringBuffer sql = new StringBuffer("update goods_inventory set check_inventory=?,inventory_date=?,existing_inventory=?, inventory_difference=?");
		if (po.getAdjust_inventory() != null)
			sql.append(",adjust_inventory=? ");
		sql.append(" where id=? ");

		int resRow = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, po.getCheck_inventory());
				ps.setObject(2, po.getInventory_date());
				ps.setObject(3, po.getExisting_inventory());
				ps.setObject(4, po.getInventory_difference());
				if (po.getAdjust_inventory() != null) {
					ps.setObject(5, po.getAdjust_inventory());
					ps.setInt(6, po.getId());
				} else {
					ps.setInt(5, po.getId());
				}
			}
		});
		logger.debug("[updateGoodsInventory]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**  
	 * Title: listGoodsInventorySummary
	 * Description: 查询指定日期盘点汇总集合
	 * @param dto
	 * @return
	 * @throws Exception  
	 */  
	public List<GoodsInventoryDTO> listGoodsInventorySummary(GoodsInventoryDTO dto) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select a.id,a.goods_id,b.goods_name,a.existing_inventory,a.check_inventory,a.inventory_difference,a.inventory_date,a.adjust_inventory from goods_inventory a,goods b where a.goods_id=b.id and a.enable_flg=1 and b.enable_flg=1");
															 
		if (StringUtils.isNotBlank(dto.getInventory_date())) {
			sql.append(" and a.inventory_date =  ? ");
			params.add(dto.getInventory_date());
		}
		sql.append(" order by a.lastupt_dttm desc,a.id desc");
		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<GoodsInventoryDTO>(GoodsInventoryDTO.class));
	}
}
