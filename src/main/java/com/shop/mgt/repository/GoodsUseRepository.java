/*
*
*/  
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

import com.shop.mgt.model.GoodsUseDTO;

@Repository
public class GoodsUseRepository {
	private final static Logger logger = LoggerFactory.getLogger(GoodsUseRepository.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取商品使用列表
	 * @return
	 * @throws Exception
	 */
	public List<GoodsUseDTO> listGoodsUse(GoodsUseDTO dto) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select a.id,a.goods_id,b.goods_name,a.quantity,a.use_emploee,a.use_dttm,a.is_effect,a.crt_dttm,a.lastupt_dttm,a.enable_flg from goods_use a,goods b where a.goods_id=b.id and a.enable_flg=1 and b.enable_flg=1 ");

		if (StringUtils.isNotBlank(dto.getGoods_name())) {
			sql.append(" and b.goods_name like ? ");
			params.add("%" + dto.getGoods_name() + "%");
		}
		if (null != dto.getQuantity() && 0 != dto.getQuantity()) {
			sql.append(" and a.quantity like ? ");
			params.add("%" + dto.getQuantity() + "%");
		}
		if (StringUtils.isNotBlank(dto.getUse_dttm_start())) {
			sql.append(" and use_dttm >= ? ");
			params.add(dto.getUse_dttm_start());
		}
		if (StringUtils.isNotBlank(dto.getUse_dttm_end())) {
			sql.append(" and use_dttm <=  ? ");
			params.add(dto.getUse_dttm_end());
		}
		if (StringUtils.isNotBlank(dto.getUse_dttm())) {
			sql.append(" and use_dttm =  ? ");
			params.add(dto.getUse_dttm());
		}

		params.add(dto.getLimitNum());
		params.add(dto.getRows());
		sql.append(" order by a.lastupt_dttm desc,a.id desc limit ?, ?");
		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<GoodsUseDTO>(GoodsUseDTO.class));
	}
	
	/**
	 * 获取商品使用记录总数
	 * @return
	 * @throws Exception
	 */
	public int listGoodsUseCount(GoodsUseDTO dto) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" select count(1) from goods_use a,goods b where a.goods_id=b.id and a.enable_flg=1 and b.enable_flg=1 ");

		if (StringUtils.isNotBlank(dto.getGoods_name())) {
			sql.append(" and b.goods_name like ? ");
			params.add("%" + dto.getGoods_name() + "%");
		}
		if (null != dto.getQuantity() && 0 != dto.getQuantity()) {
			sql.append(" and a.quantity like ? ");
			params.add("%" + dto.getQuantity() + "%");
		}
		if (StringUtils.isNotBlank(dto.getUse_dttm_start())) {
			sql.append(" and use_dttm >= ? ");
			params.add(dto.getUse_dttm_start());
		}
		if (StringUtils.isNotBlank(dto.getUse_dttm_end())) {
			sql.append(" and use_dttm <=  ? ");
			params.add(dto.getUse_dttm_end());
		}
		if (StringUtils.isNotBlank(dto.getUse_dttm())) {
			sql.append(" and use_dttm =  ? ");
			params.add(dto.getUse_dttm());
		}
		return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
	}
	
	/**
	 * 删除产品使用记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int deleteGoodsUse(final GoodsUseDTO po) throws Exception {
		int resRow = jdbcTemplate.update("delete from goods_use where id=? and enable_flg=1", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, po.getId());
			}
		});
		logger.debug("[deleteGoodsUse]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 新增商品使用数据
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int saveGoodsUse(final GoodsUseDTO po) throws Exception {
		StringBuffer sql = new StringBuffer("insert into goods_use(goods_id,quantity,use_dttm,is_effect");
		if (StringUtils.isNotBlank(po.getUse_emploee()))
			sql.append(",use_emploee");

		sql.append(") VALUES(?,?,?,?");

		if (StringUtils.isNotBlank(po.getUse_emploee()))
			sql.append(",?");

		sql.append(")");

		int resRow = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, po.getGoods_id());
				ps.setObject(2, po.getQuantity());
				ps.setObject(3, po.getUse_dttm());
				ps.setObject(4, po.getIs_effect());
				if (StringUtils.isNotBlank(po.getUse_emploee()))
					ps.setObject(5, po.getUse_emploee());
			}
		});
		logger.debug("[saveGoodsUse]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 获取单个使用记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GoodsUseDTO getGoodsUseById(int id) throws Exception{
		List<GoodsUseDTO> dtos = jdbcTemplate.query("select a.id,a.goods_id,b.goods_name,a.quantity,a.use_emploee,a.use_dttm,a.is_effect,a.crt_dttm,a.lastupt_dttm,a.enable_flg from goods_use a,goods b where a.goods_id=b.id and a.enable_flg=1 and b.enable_flg=1 and a.id=? ", new BeanPropertyRowMapper<GoodsUseDTO>(GoodsUseDTO.class), new Object[] { id });
		if(null != dtos && dtos.size()==1 )
			return dtos.get(0);
		return null;
	}
	
	/**
	 * 更新使用记录信息
	 * @param po
	 * @return
	 */
	public int updateGoodsUse(final GoodsUseDTO po) throws Exception {
		StringBuffer sql = new StringBuffer("update goods_use set quantity=? ,use_dttm=? ");

		if (StringUtils.isNotEmpty(po.getUse_emploee()))
			sql.append(",use_emploee=? ");

		sql.append(" where id=?");

		int resRow = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, po.getQuantity());
				ps.setObject(2, po.getUse_dttm());
				if (StringUtils.isNotEmpty(po.getUse_emploee())) {
					ps.setObject(3, po.getUse_emploee());
					ps.setInt(4, po.getId());
				} else {
					ps.setInt(3, po.getId());
				}
			}
		});
		logger.debug("[updateGoodsUse]操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 更新商品使用记录（是否生效）
	 * @param po
	 * @return
	 */
	public int effectGoodsUse(final GoodsUseDTO po) throws Exception {
		String sql = "update goods_use set is_effect=? where id=?";
		int resRow = jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, po.getIs_effect());
				ps.setInt(2, po.getId());
			}
		});
		logger.debug("[effectGoodsUse]操作结果记录数：  " + resRow);
		return resRow;
	}
}
