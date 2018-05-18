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

import com.shop.mgt.model.GoodsOrderDTO;

/**  
* @className:OrderRepository.java</p>  
* @description:
* @author hj  
* @date 2018年4月10日  
*/
@Repository
public class OrderRepository {
	
	private final static Logger logger = LoggerFactory.getLogger(OrderRepository.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取订单列表
	 * @return
	 * @throws Exception
	 */
	public List<GoodsOrderDTO> listOrderInfo(GoodsOrderDTO dto) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select a.id,a.goods_id,b.goods_name,a.goods_price,a.goods_count,a.total_price,a.order_date,is_effect,a.crt_dttm,a.lastupt_dttm,a.enable_flg from goods_order a,goods b where a.goods_id=b.id and a.enable_flg=1 ");

		if (null != (dto.getId()) && 0 != dto.getId()) {
			sql.append(" and a.id like ?   ");
			params.add("%" + dto.getId() + "%");
		}
		if (StringUtils.isNotBlank(dto.getGoods_name())) {
			sql.append(" and b.goods_name like ? ");
			params.add("%" + dto.getGoods_name() + "%");
		}
		if (null != dto.getTotal_price() && 0 != dto.getTotal_price()) {
			sql.append(" and a.total_price like ? ");
			params.add("%" + dto.getTotal_price() + "%");
		}
		if (StringUtils.isNotBlank(dto.getOrder_date_start())) {
			sql.append(" and order_date >= ? ");
			params.add(dto.getOrder_date_start());
		}
		if (StringUtils.isNotBlank(dto.getOrder_date_end())) {
			sql.append(" and order_date <=  ? ");
			params.add(dto.getOrder_date_end());
		}
		if (StringUtils.isNotBlank(dto.getOrder_date())) {
			sql.append(" and order_date =  ? ");
			params.add(dto.getOrder_date());
		}

		params.add(dto.getLimitNum());
		params.add(dto.getRows());
		sql.append(" order by a.lastupt_dttm desc,a.id desc limit ?, ?");
		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<GoodsOrderDTO>(GoodsOrderDTO.class));
	}
	
	/**
	 * 获取订单记录总数
	 * @return
	 * @throws Exception
	 */
	public int listOrderCount(GoodsOrderDTO dto) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" select count(1) from goods_order a,goods b where a.goods_id=b.id and a.enable_flg=1 ");

		if (null != (dto.getId()) && 0 != dto.getId()) {
			sql.append(" and a.id like ?   ");
			params.add("%" + dto.getId() + "%");
		}
		if (StringUtils.isNotBlank(dto.getGoods_name())) {
			sql.append(" and b.goods_name like ? ");
			params.add("%" + dto.getGoods_name() + "%");
		}
		if (null != dto.getTotal_price() && 0 != dto.getTotal_price()) {
			sql.append(" and a.total_price like ? ");
			params.add("%" + dto.getTotal_price() + "%");
		}
		if (StringUtils.isNotBlank(dto.getOrder_date_start())) {
			sql.append(" and order_date >= ? ");
			params.add(dto.getOrder_date_start());
		}
		if (StringUtils.isNotBlank(dto.getOrder_date_end())) {
			sql.append(" and order_date <=  ? ");
			params.add(dto.getOrder_date_end());
		}
		if (StringUtils.isNotBlank(dto.getOrder_date())) {
			sql.append(" and order_date =  ? ");
			params.add(dto.getOrder_date());
		}
		return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
	}
	
	/**
	 * 删除订单
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int deleteOrderInfo(final GoodsOrderDTO po) throws Exception {
		int resRow = jdbcTemplate.update("delete from goods_order where id=? and enable_flg=1", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, po.getId());
			}
		});
		logger.debug("[deleteOrderInfo]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 获取单个商品详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GoodsOrderDTO getOrderById(int id) throws Exception{
		List<GoodsOrderDTO> dtos = jdbcTemplate.query("select a.id,a.goods_id,b.goods_name,a.goods_price,a.goods_count,a.total_price,a.order_date,is_effect,a.crt_dttm,a.lastupt_dttm,a.enable_flg from goods_order a,goods b where a.goods_id=b.id and a.enable_flg=1 and a.id=? ", new BeanPropertyRowMapper<GoodsOrderDTO>(GoodsOrderDTO.class), new Object[] { id });
		if(null != dtos && dtos.size()==1 )
			return dtos.get(0);
		return null;
	}
	
	/**
	 * 插入商品数据
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int saveOrder(final GoodsOrderDTO po) throws Exception {
		int resRow = jdbcTemplate.update("INSERT INTO goods_order(goods_id,goods_price,goods_count,total_price,order_date,is_effect) VALUES(?,?,?,?,?,?)", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, po.getGoods_id());
				ps.setObject(2, po.getGoods_price());
				ps.setObject(3, po.getGoods_count());
				ps.setObject(4, po.getTotal_price());
				ps.setObject(5, po.getOrder_date());
				ps.setObject(6, po.getIs_effect());
			}
		});
		logger.debug("[saveOrder]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 更新订单信息
	 * @param po
	 * @return
	 */
	public int updateOrder(final GoodsOrderDTO po) throws Exception {
		String sql = "update goods_order set goods_price=? ,goods_count=?,total_price=? where id=?";
		int resRow = jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, po.getGoods_price());
				ps.setObject(2, po.getGoods_count());
				ps.setObject(3, po.getTotal_price());
				ps.setInt(4, po.getId());
			}
		});
		logger.debug("[updateGoods]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 更新订单状态（是否生效）
	 * @param po
	 * @return
	 */
	public int effectOrderInfo(final GoodsOrderDTO po) throws Exception {
		String sql = "update goods_order set is_effect=? where id=?";
		int resRow = jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, po.getIs_effect());
				ps.setInt(2, po.getId());
			}
		});
		logger.debug("[effectOrderInfo]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**  
	 * Title: listOrderSummary
	 * Description: 查询指定日期订单汇总集合
	 * @param dto
	 * @return
	 * @throws Exception  
	 */  
	public List<GoodsOrderDTO> listOrderSummary(GoodsOrderDTO dto) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select a.id,a.goods_id,b.goods_name,a.goods_price,a.goods_count,a.total_price,a.order_date,is_effect,a.crt_dttm,a.lastupt_dttm,a.enable_flg from goods_order a,goods b where a.goods_id=b.id and a.enable_flg=1 ");
		if (StringUtils.isNotBlank(dto.getOrder_date())) {
			sql.append(" and order_date =  ? ");
			params.add(dto.getOrder_date());
		}
		sql.append(" order by a.lastupt_dttm desc,a.id desc");
		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<GoodsOrderDTO>(GoodsOrderDTO.class));
	}
	
}
