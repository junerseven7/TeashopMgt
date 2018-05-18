package com.shop.mgt.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.shop.mgt.model.DropDownVO;
import com.shop.mgt.model.GoodsInfoDTO;

@Repository
public class GoodsInfoRepository {
	private final static Logger logger = LoggerFactory.getLogger(GoodsInfoRepository.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取商品信息列表
	 * @return
	 * @throws Exception
	 */
	public List<GoodsInfoDTO> listGoodsInfo(GoodsInfoDTO info) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select id,goods_num,goods_type,goods_name,specification,unit,price,crt_dttm,lastupt_dttm,enable_flg from goods where enable_flg=1 ");
		if (StringUtils.isNotBlank(info.getGoods_num())) {
			sql.append(" and GOODS_NUM like ?   ");
			params.add("%" + info.getGoods_num() + "%");
		}
		if (StringUtils.isNotBlank(info.getGoods_name())) {
			sql.append(" and GOODS_NAME like ? ");
			params.add("%" + info.getGoods_name() + "%");
		}
		if (StringUtils.isNotBlank(info.getGoods_type())) {
			sql.append(" and GOODS_TYPE like ? ");
			params.add("%" + info.getGoods_type() + "%");
		}
		params.add(info.getLimitNum());
		params.add(info.getRows());
		sql.append(" order by lastupt_dttm desc,id desc limit ?, ?");
		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<GoodsInfoDTO>(GoodsInfoDTO.class));
	}
	
	/**
	 * 获取商品信息总数
	 * @return
	 * @throws Exception
	 */
	public int listGoodsInfoCount(GoodsInfoDTO info) throws Exception {
		List<Object> params = new ArrayList<Object>(); 
		StringBuffer sql=new StringBuffer("select count(id) from goods where enable_flg=1 ");
		if(!StringUtils.isEmpty(info.getGoods_num())){
			sql.append(" and GOODS_NUM like ?   ");
			params.add("%"+info.getGoods_num()+"%");
		}
		if(!StringUtils.isEmpty(info.getGoods_name())){
			sql.append(" and GOODS_NAME like ? ");
			params.add("%"+info.getGoods_name()+"%");
		}
		if (StringUtils.isNotBlank(info.getGoods_name())) {
			sql.append(" and GOODS_TYPE like ? ");
			params.add("%" + info.getGoods_type() + "%");
		}
		return jdbcTemplate.queryForObject(sql.toString(),params.toArray(),Integer.class);
	}
	
	
	/**
	 * 获取单个商品详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GoodsInfoDTO getGoodById(int id) throws Exception{
		GoodsInfoDTO dto = jdbcTemplate.queryForObject("select id,goods_num,goods_type,goods_name,specification,unit,price from goods where id=? and enable_flg=1", new ObjectRowMapper(), id);
		return dto;
	}
	
	/**
	 * 插入商品数据
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int saveGoods(final GoodsInfoDTO po) throws Exception {
		int resRow = jdbcTemplate.update("INSERT INTO goods(goods_num,goods_type,goods_name,specification,unit,price) VALUES(?,?,?,?,?,?)", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, po.getGoods_num());
				ps.setString(2, po.getGoods_type());
				ps.setString(3, po.getGoods_name());
				ps.setString(4, po.getSpecification());
				ps.setString(5, po.getUnit());
				ps.setObject(6, po.getPrice());
			}
		});
		logger.debug("操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 插入用户数据-防止sql注入-可以返回该条记录的主键（注意需要指定主键）
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int saveGoodsWithKey(final GoodsInfoDTO po) throws Exception {
		final String sql = "INSERT INTO goods(goods_num,goods_type,goods_name,specification,unit,price) VALUES(?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int resRow = jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, po.getGoods_num());
				ps.setString(2, po.getGoods_type());
				ps.setString(3, po.getGoods_name());
				ps.setString(4, po.getSpecification());
				ps.setString(5, po.getUnit());
				ps.setObject(6, po.getPrice());
				return ps;
			}
		}, keyHolder);
		logger.debug("操作结果记录数：  " + resRow + " 主键: " + keyHolder.getKey());
		return keyHolder.getKey().intValue();
	}
	
	/**
	 * 更新商品信息
	 * @param po
	 * @return
	 */
	public int updateGoods(final GoodsInfoDTO po) throws Exception {
		String sql = "update goods set goods_num=?,goods_type=? ,goods_name=?,specification=?,unit=?,price=?  where id=?";
		int resRow = jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, po.getGoods_num());
				ps.setString(2, po.getGoods_type());
				ps.setString(3, po.getGoods_name());
				ps.setString(4, po.getSpecification());
				ps.setString(5, po.getUnit());
				ps.setObject(6, po.getPrice());
				ps.setInt(7, po.getId());
			}
		});
		logger.debug("操作结果记录数：  " + resRow);
		return resRow;
	}
	
	/**
	 * 删除商品
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int deleteGoods(final GoodsInfoDTO po) throws Exception {
		int resRow = jdbcTemplate.update("delete from goods where id=? and enable_flg=1", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, po.getId());
			}
		});
		logger.debug("操作结果记录数：  " + resRow);
		return resRow;
	}
	
	
	/**
	 * 根据商品名查找-用于判断商品是否存在
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public GoodsInfoDTO getUserByUserName(final GoodsInfoDTO po) throws Exception {
		String sql = "select id,goods_num,goods_type,goods_name,specification,unit,price from goods where goods_name=? and enable_flg=1";
		List<GoodsInfoDTO> queryList = jdbcTemplate.query(sql, new ObjectRowMapper(), new Object[] { po.getGoods_name() });
		if (queryList != null && queryList.size() > 0) {
			return queryList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取商品总的记录数
	 * @return
	 * @throws Exception
	 */
	public Integer countGoodsNumber() throws Exception {
		String sql = "select count(id) from goods";
		Integer total = jdbcTemplate.queryForObject(sql, Integer.class);
		logger.debug("操作结果记录数：  " + total);
		return total;
	}
	
	/**  
	 * Title: listDropDownList
	 * Description: 商品信息下拉框
	 * @return  
	 */  
	public List<DropDownVO> listDropDownList() throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select id as dKey,goods_name as dValue from goods where enable_flg=1 order by id");
		return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<DropDownVO>(DropDownVO.class));
	}
	
	class ObjectRowMapper implements RowMapper<GoodsInfoDTO> {
		@Override
		public GoodsInfoDTO mapRow(ResultSet resultSet, int i) throws SQLException {
			GoodsInfoDTO dto = new GoodsInfoDTO();
			dto.setId(resultSet.getInt("id"));
			dto.setGoods_num(resultSet.getString("goods_num"));
			dto.setGoods_type(resultSet.getString("goods_type"));
			dto.setGoods_name(resultSet.getString("goods_name"));
			dto.setSpecification(resultSet.getString("specification"));
			dto.setUnit(resultSet.getString("unit"));
			dto.setPrice(resultSet.getDouble("price"));

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
