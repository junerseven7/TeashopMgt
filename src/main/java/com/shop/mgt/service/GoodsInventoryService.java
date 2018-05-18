package com.shop.mgt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.mgt.model.GoodsInventoryDTO;
import com.shop.mgt.model.GoodsStockDTO;
import com.shop.mgt.model.UpdateType;
import com.shop.mgt.repository.GoodsInventoryRepository;
import com.shop.mgt.repository.GoodsStockRepository;

/**  
* @className:GoodsInventoryService.java
* @description:盘点模块Service
* @author hj  
* @date 2018年4月18日  
*/
@Service
public class GoodsInventoryService {
	
	private final static Logger logger = LoggerFactory.getLogger(GoodsInventoryService.class);
	@Autowired
	private GoodsInventoryRepository goodsInventoryRepository;	
	@Autowired
	private GoodsStockRepository goodsStockRepository;
	
	
	@Transactional(readOnly = true)
	public List<GoodsInventoryDTO> listGoodsInventory(GoodsInventoryDTO dto) throws Exception {
		return goodsInventoryRepository.listGoodsInventory(dto);
	}
	
	@Transactional(readOnly = true)
	public int listGoodsInventoryCount(GoodsInventoryDTO dto) throws Exception {
		return goodsInventoryRepository.listGoodsInventoryCount(dto);
	}
	
	public int deleteGoodsInventory(GoodsInventoryDTO[] dtos) throws Exception{
		int res = 0;
		for (GoodsInventoryDTO dto : dtos) {
			res += goodsInventoryRepository.deleteGoodsInventory(dto);
		}
		return res;
	}
	
	/**  
	 * Title: saveGoodsInventory
	 * Description:保存新的盘点记录
	 * @param dto
	 * @return
	 * @throws Exception  
	 */  
	@Transactional(rollbackFor = RuntimeException.class)
	public synchronized int saveGoodsInventory(GoodsInventoryDTO dto) throws Exception {
		GoodsStockDTO gsDTO = new GoodsStockDTO();
		gsDTO.setGoods_id(dto.getGoods_id());
		gsDTO = goodsStockRepository.getGoodsStock(gsDTO);

		if (null == gsDTO)
			return 0;

		dto.setExisting_inventory(gsDTO.getNow_quantity());
		dto.setInventory_difference(gsDTO.getNow_quantity() - dto.getCheck_inventory());
		return goodsInventoryRepository.saveGoodsInventory(dto);
	}
	
	
	/**  
	 * Title: getGoodsInventoryById
	 * Description: 当编辑单条盘点信息，实时去查询商品库存信息。
	 * @param inventoryId
	 * @param goodsId
	 * @return
	 * @throws Exception  
	 */  
	@Transactional(readOnly = true)
	public GoodsInventoryDTO getGoodsInventoryById(int inventoryId, int goodsId) throws Exception {
		GoodsStockDTO gsDTO = new GoodsStockDTO();
		gsDTO.setGoods_id(goodsId);
		gsDTO = goodsStockRepository.getGoodsStock(gsDTO);
		
		if (null == gsDTO){
			logger.error("[GoodsInventoryService.GoodsInventoryDTO]商品ID：" + inventoryId + "库存信息不存在！");
			return null;
		}

		GoodsInventoryDTO retrunDTO = goodsInventoryRepository.getGoodsInventoryById(inventoryId);
		retrunDTO.setExisting_inventory(gsDTO.getNow_quantity());
		return retrunDTO;
	}
	
	/**  
	 * Title: updateGoodsInventory
	 * Description:如果"手动调整库存"字段不等于0，需要更新库存。
	 * @param dto
	 * @return
	 * @throws Exception  
	 */  
	@Transactional(rollbackFor = RuntimeException.class)
	public int updateGoodsInventory(GoodsInventoryDTO dto) throws Exception {
		GoodsStockDTO gsDTO = new GoodsStockDTO();
		gsDTO.setGoods_id(dto.getGoods_id());

		// 根据id查询盘点记录是否存在
		GoodsInventoryDTO po = goodsInventoryRepository.getGoodsInventoryById(dto.getId());
		if (null == po) {
			logger.error("[GoodsInventoryService.updateGoodsInventory]不存在ID : {} 的盘点记录", dto.getId());
			return 0;
		}

		GoodsStockDTO gsPO = goodsStockRepository.getGoodsStock(gsDTO);
		if (null == gsPO) {
			logger.error("[GoodsInventoryService.updateGoodsInventory]商品ID：{} 库存信息不存在！", dto.getGoods_id());
			return 0;
		}

		// 判断本次提交的"手动调整库存"是否和现有记录的值不同
		if (dto.getAdjust_inventory() != null && dto.getAdjust_inventory() != po.getAdjust_inventory()) {
			// 更新对应商品的库存信息
			gsDTO.setNow_quantity(dto.getAdjust_inventory());
			goodsStockRepository.updateGoodsStock(gsDTO, UpdateType.NOT_CUMULATIVE);
		}

		dto.setExisting_inventory(gsPO.getNow_quantity());
		dto.setInventory_difference(gsPO.getNow_quantity() - dto.getCheck_inventory());
		return goodsInventoryRepository.updateGoodsInventory(dto);
	}
	
	/**  
	 * Title: listGoodsInventorySummary
	 * Description:每日盘点详情汇总 
	 * @param orderTime
	 * @return
	 * @throws Exception  
	 */  
	@Transactional(readOnly = true)
	public List<GoodsInventoryDTO> listGoodsInventorySummary(String InventoryTime) throws Exception {
		GoodsInventoryDTO dto = new GoodsInventoryDTO();
		dto.setInventory_date(InventoryTime);
		return goodsInventoryRepository.listGoodsInventorySummary(dto);
	}
	
	
}
