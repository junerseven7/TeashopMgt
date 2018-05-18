/*
*
*/
package com.shop.mgt.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.shop.mgt.model.GoodsUseDTO;

/**
 * @className:ReportsRepository.java
 * @description:报表dao
 * @author hj
 * @date 2018年4月20日
 */
@Repository
public class ReportsRepository {
	private final static Logger logger = LoggerFactory.getLogger(ReportsRepository.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 获取商品使用列表
	 * @return
	 * @throws Exception
	 */
	public List<GoodsUseDTO> listGoodsUse(final int goodId) throws Exception {
		StringBuffer sql = new StringBuffer("select quantity,use_dttm  from goods_use where goods_id=? order by use_dttm desc limit 1000");
		logger.debug("[ReportsRepository.listGoodsUse] sql={}", sql);
		return jdbcTemplate.query(sql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, goodId);
			}
		}, new ObjectRowMapper());
	}

	class ObjectRowMapper implements RowMapper<GoodsUseDTO> {
		@Override
		public GoodsUseDTO mapRow(ResultSet resultSet, int i) throws SQLException {
			GoodsUseDTO dto = new GoodsUseDTO();
			dto.setQuantity(resultSet.getInt("quantity"));
			dto.setUse_dttm(resultSet.getString("use_dttm").substring(0, 10));
			return dto;
		}
	}
}
