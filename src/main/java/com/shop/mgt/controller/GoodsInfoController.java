package com.shop.mgt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.mgt.model.DropDownVO;
import com.shop.mgt.model.GoodsInfoDTO;
import com.shop.mgt.service.GoodsInfoService;
import com.shop.mgt.utils.CommonUtils;

/**
 * @author hj
 * @className:GoodsInfoController.java
 * @description:商品信息
 * @date 2018年4月13日
 */
@Controller
@RequestMapping("/goods")
public class GoodsInfoController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(GoodsInfoController.class);

    @Autowired
    private GoodsInfoService goodsInfoService;

    @RequestMapping("/index")
    public String welcome() {
        return "goodsInfo";
    }

    /**
     * 商品信息列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> listGoodsInfo(HttpServletRequest request) {
        GoodsInfoDTO info = CommonUtils.request2Bean(request, GoodsInfoDTO.class);
        Map<String, Object> resp = new HashMap<>();
        List<GoodsInfoDTO> rows = null;
        int total = 0;
        try {
            rows = goodsInfoService.listGoods(info);
            total = goodsInfoService.listGoodsCount(info);
            resp.put("rows", rows);
            resp.put("total", total);
            return resp;
        } catch (Exception e) {
            logger.error("[GoodsInfoController.listGoods()] 异常信息 :{}", e);
        }
        return null;
    }

    /**
     * 单个商品信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public GoodsInfoDTO getGoodsInfo(HttpServletRequest request) {
        String id = request.getParameter("id");
        GoodsInfoDTO goods = null;
        try {
            if (StringUtils.isNotBlank(id))
                goods = goodsInfoService.getGoodById(id);
        } catch (Exception e) {
            logger.error("[GoodsInfoController.getGoodsInfo()] 异常信息 : {}", e);
        }
        return goods;
    }

    /**
     * 新建商品信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveGoodsInfo(HttpServletRequest request) {
        GoodsInfoDTO dto = CommonUtils.request2Bean(request, GoodsInfoDTO.class);
        int res = 0;
        try {
            if (null != dto)
                res = goodsInfoService.saveGoods(dto);
        } catch (Exception e) {
            logger.error("[GoodsInfoController.saveGoodsInfo()] 异常信息 : {}", e);
        }
        return res > 0 ? "true" : "false";
    }

    /**
     * 删除商品信息
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteGoodsInfo(@RequestBody GoodsInfoDTO[] dtos) {
        int res = 0;
        try {
            if (dtos != null && dtos.length > 0) {
                res = goodsInfoService.deleteGoodsInfo(dtos);
            }
        } catch (Exception e) {
            logger.error("[GoodsInfoController.deleteGoodsInfo()] 异常信息 : {}", e);
        }
        return res > 0 ? "true" : "false";
    }

    /**
     * 更新商品信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateGoodsInfo(HttpServletRequest request) {
        GoodsInfoDTO dto = CommonUtils.request2Bean(request, GoodsInfoDTO.class);
        int res = 0;
        try {
            int id = dto.getId();
            if (id > 0) {
                res = goodsInfoService.updateGoods(dto);
            }
        } catch (Exception e) {
            logger.error("[GoodsInfoController.deleteGoodsInfo()] 异常信息 : {}", e);
        }
        return res > 0 ? "true" : "false";
    }


    /**
     * 商品信息列表
     *
     * @return
     */
    @RequestMapping("/dropDownList")
    @ResponseBody
    public List<DropDownVO> dropDownList(HttpServletRequest request) {
        List<DropDownVO> returnList = new ArrayList<>();
        try {
            returnList = goodsInfoService.listDropDownList();
        } catch (Exception e) {
            logger.error("[GoodsInfoController.dropDownList()] 异常信息 : {}", e);
        }
        return returnList;
    }

}
