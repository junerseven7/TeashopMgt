package com.shop.mgt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mgt.constants.CommonConstants;
import com.shop.mgt.model.GoodsInfoDTO;
import com.shop.mgt.model.GoodsOrderDTO;
import com.shop.mgt.model.GoodsStockDTO;
import com.shop.mgt.model.UpdateType;
import com.shop.mgt.repository.GoodsInfoRepository;
import com.shop.mgt.repository.GoodsStockRepository;
import com.shop.mgt.repository.OrderRepository;

/**  
* @className:OrderService.java</p>  
* @description:
* @author hj  
* @date 2018年4月10日  
*/
@Service
public class OrderService {
	private final static Logger logger = LoggerFactory.getLogger(OrderService.class);
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private GoodsInfoRepository goodsInfoRepository;
	@Autowired
	private GoodsStockRepository goodsStockRepository;
	
	@Transactional(readOnly = true)
	public List<GoodsOrderDTO> listOrder(GoodsOrderDTO dto) throws Exception {
		return orderRepository.listOrderInfo(dto);
	}
	
	@Transactional(readOnly = true)
	public int listOrderCount(GoodsOrderDTO info) throws Exception {
		return orderRepository.listOrderCount(info);
	}
	
	public int deleteOrderInfo(GoodsOrderDTO[] dtos) throws Exception{
		int res = 0;
		for (GoodsOrderDTO dto : dtos) {
			res += orderRepository.deleteOrderInfo(dto);
		}
		return res;
	}
	
	/**  
	 * Title: effectOrderInfo
	 * Description: 使得未生效的订单生效
	 * @param dtos
	 * @return
	 * @throws Exception  
	 */  
	@Transactional(rollbackFor = RuntimeException.class)
	public synchronized int effectOrderInfo(GoodsOrderDTO[] dtos) throws Exception {
		int res = 0;
		for (GoodsOrderDTO dto : dtos) {
			/** 根据orderId查询订单是否存在 ，或者是否已经为"已生效"状态 */
			GoodsOrderDTO goPO = orderRepository.getOrderById(dto.getId());
			if (null == goPO || goPO.getIs_effect() == CommonConstants.IS_EFFECT_TURE)
				continue;
			/** 更新商品库存信息 */
			GoodsStockDTO po = new GoodsStockDTO();
			po.setNow_quantity(dto.getGoods_count());
			po.setGoods_id(dto.getGoods_id());
			int resRow = goodsStockRepository.updateGoodsStock(po,UpdateType.ACCUMULATE_ADD);
			if(resRow == 0){
				logger.info("[OrderService.effectOrderInfo] 商品ID={}的库存记录不存在",dto.getGoods_id());
				continue;
			}
			/** 更新订单为"已生效"状态 */
			dto.setIs_effect(CommonConstants.IS_EFFECT_TURE);
			res += orderRepository.effectOrderInfo(dto);
		}
		return res;
	}
	
	/**  
	 * Title: saveOrder
	 * Description:保存新的订单并计算订单的总价 
	 * @param dto
	 * @return
	 * @throws Exception  
	 */  
	@Transactional(rollbackFor = RuntimeException.class)
	public synchronized int saveOrder(GoodsOrderDTO dto) throws Exception {
		GoodsInfoDTO goodsPO = goodsInfoRepository.getGoodById(dto.getGoods_id());
		if (null == goodsPO || goodsPO.getPrice() == null)
			return 0;
		dto.setTotal_price(goodsPO.getPrice() * dto.getGoods_count());
		dto.setGoods_price(goodsPO.getPrice());
		dto.setIs_effect(CommonConstants.IS_EFFECT_FALSE);// 默认未生效
		return orderRepository.saveOrder(dto);
	}
	
	@Transactional(readOnly = true)
	public GoodsOrderDTO getOrderById(int id) throws Exception{
		return orderRepository.getOrderById(id);
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public int updateOrder(GoodsOrderDTO dto) throws Exception{
		return orderRepository.updateOrder(dto);
	}
	
	/**  
	 * Title: listOrderSummary
	 * Description:每日订单详情汇总 
	 * @param orderTime
	 * @return
	 * @throws Exception  
	 */  
	@Transactional(readOnly = true)
	public List<GoodsOrderDTO> listOrderSummary(String orderTime) throws Exception {
		GoodsOrderDTO dto = new GoodsOrderDTO();
		dto.setOrder_date(orderTime);
		return orderRepository.listOrderSummary(dto);
	}
	
}
