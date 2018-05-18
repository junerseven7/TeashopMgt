package com.shop.mgt.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.shop.mgt.model.GoodsStockDTO;
import com.shop.mgt.model.UpdateType;

/**  
* @className:GoodsStockRepository.java
* @description:库存信息DAO
* @author hj  
* @date 2018年4月10日  
*/  
@Repository
public class GoodsStockRepository {
	private final static Logger logger = LoggerFactory.getLogger(GoodsStockRepository.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取商品信息列表
	 * @return
	 * @throws Exception
	 */
	public List<GoodsStockDTO> listGoodsStockInfo(GoodsStockDTO dto) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select a.id,b.goods_name,a.goods_id,now_quantity,input_emploee,a.crt_dttm,a.lastupt_dttm,a.enable_flg from goods_stock a,goods b where a.goods_id=b.id and a.enable_flg=1 ");
		if (null !=(dto.getGoods_id())&& 0 !=dto.getGoods_id() ) {
			sql.append(" and a.goods_id like ?   ");
			params.add("%" + dto.getGoods_id() + "%");
		}
		if (StringUtils.isNotBlank(dto.getGoods_name())) {
			sql.append(" and b.goods_name like ? ");
			params.add("%" + dto.getGoods_name() + "%");
		}
	
		params.add(dto.getLimitNum());
		params.add(dto.getRows());
		sql.append(" order by a.lastupt_dttm desc,a.id desc limit ?, ?");
		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<GoodsStockDTO>(GoodsStockDTO.class));
	}
	
	/**
	 * 获取商品信息总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public int listGoodsStockInfoCount(GoodsStockDTO dto) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" select count(1) from goods_stock a,goods b  where a.goods_id=b.id and a.enable_flg=1 ");
		if (null !=(dto.getGoods_id())&& 0 !=dto.getGoods_id() ) {
			sql.append(" and a.goods_id like ?   ");
			params.add("%" + dto.getGoods_id() + "%");
		}
		if (StringUtils.isNotBlank(dto.getGoods_name())) {
			sql.append(" and b.goods_name like ? ");
			params.add("%" + dto.getGoods_name() + "%");
		}
		return jdbcTemplate.queryForObject(sql.toString(),params.toArray(),Integer.class);
	}
	
	
	/**
	 * 获取单个库存商品详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GoodsStockDTO getGoodsStockById(int id) throws Exception{
		GoodsStockDTO dto =  jdbcTemplate.queryForObject("select a.id,b.goods_name,a.goods_id,now_quantity,input_emploee from goods_stock a,goods b where a.goods_id=b.id and a.enable_flg=1 and a.id=?", new BeanPropertyRowMapper<GoodsStockDTO>(GoodsStockDTO.class), new Object[] { id });
		return dto;
	}
	
	/**
	 * 获取单个库存商品详情
	 * @param dto
	 * @return
	 * @throws Exception
	 */  
	public  GoodsStockDTO getGoodsStock(GoodsStockDTO dto) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select id,goods_id,now_quantity,input_emploee from goods_stock a where  a.enable_flg=1");

		if (null != (dto.getGoods_id()) && 0 != dto.getGoods_id()) {
			sql.append(" and goods_id = ?   ");
			params.add(dto.getGoods_id());
		}

		List<GoodsStockDTO> list = jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<GoodsStockDTO>(GoodsStockDTO.class));
		if (null != list && list.size() == 1)
			return list.get(0);

		return null;
	}
	
	/**
	 * 插入商品数据
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int saveGoodsStock(final GoodsStockDTO po) throws Exception {
		int resRow = jdbcTemplate.update("insert into goods_stock(goods_id,now_quantity,input_emploee) values(?,?,?)", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, po.getGoods_id());
				ps.setObject(2, po.getNow_quantity());
				ps.setObject(3, po.getInput_emploee());
			}
		});
		logger.debug("[saveOrder]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 更新商品信息
	 * @param po
	 * @return
	 */
	public int updateGoodsStock(final GoodsStockDTO po,UpdateType ut) throws Exception {
		StringBuffer sql = new StringBuffer("update goods_stock set ");
	/*	if (null != po.getNow_quantity() && StringUtils.isNotBlank(po.getInput_emploee())) {
			sql.append("  now_quantity=?,input_emploee=?   ");
			sql.append(" where id=? ");
		} else {
			sql.append(" now_quantity=now_quantity+?  ");
			sql.append(" where goods_id=? ");
		}*/
		switch (ut) {
		case NORMAL:
			sql.append("  now_quantity=?,input_emploee=?   ");
			sql.append(" where id=? ");
			break;
		case ACCUMULATE_ADD:
			sql.append(" now_quantity=now_quantity+?  ");
			sql.append(" where goods_id=? ");
			break;
		case ACCUMULATE_SUB:
			sql.append(" now_quantity=now_quantity-?  ");
			sql.append(" where goods_id=? ");
			break;
		case NOT_CUMULATIVE:
			sql.append(" now_quantity=?  ");
			sql.append(" where goods_id=? ");
			break;
		}
		
		int resRow = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, po.getNow_quantity());
				if (null != po.getNow_quantity() && StringUtils.isNotBlank(po.getInput_emploee())) {
					ps.setObject(2, po.getInput_emploee());
					ps.setInt(3, po.getId());
				} else {
					ps.setInt(2, po.getGoods_id());
				}
			}
		});
		logger.debug("[updateGoods]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 删除商品
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int deleteGoodsStock(final GoodsStockDTO po) throws Exception {
		int resRow = jdbcTemplate.update("delete from goods_stock where id=? and enable_flg=1", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, po.getId());
			}
		});
		logger.debug("[deleteGoods]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	
	
	class ObjectRowMapper implements RowMapper<GoodsStockDTO> {
		@Override
		public GoodsStockDTO mapRow(ResultSet resultSet, int i) throws SQLException {
			GoodsStockDTO dto = new GoodsStockDTO();
			dto.setId(resultSet.getInt("id"));
			dto.setGoods_id(resultSet.getInt("goods_id"));
			dto.setNow_quantity(resultSet.getInt("now_quantity"));
			dto.setInput_emploee(resultSet.getString("input_emploee"));

			if (isExistColumn(resultSet, "crt_dttm"))
				dto.setCrt_dttm(resultSet.getString("crt_dttm"));
			if (isExistColumn(resultSet, "lastupt_dttm"))
				dto.setLastupt_dttm(resultSet.getString("lastupt_dttm"));
			if (isExistColumn(resultSet, "enable_flg"))
				dto.setEnable_flg(resultSet.getString("enable_flg"));
			return dto;
		}

		public boolean isExistColumn(ResultSet rs, String columnName) {
			try {
				if (rs.findColumn(columnName) > 0) {
					return true;
				}
			} catch (SQLException e) {
				return false;
			}
			return false;
		}
	}
}
