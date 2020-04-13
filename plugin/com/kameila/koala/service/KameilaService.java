package com.kameila.koala.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.kameila.koala.model.OrderLogistic;
import com.kameila.koala.model.OrderReponse;
import com.kameila.koala.model.ProductSku;
import com.kameila.koala.sdk.JSONUtils;
import com.kameila.koala.sdk.KoalaApi;
import com.kameila.koala.sdk.KoalaClient;
import com.kameila.koala.sdk.KoalaResult;

public class KameilaService {
	private KoalaClient koalaClient;
	
	public KameilaService(String appKey, String appSec) {
		this(appKey, appSec, false);
	}
	
	/**
	 * 初始化
	 * @param appKey
	 * @param appSec
	 * @param testEnv 是否开启测试环境
	 */
	public KameilaService(String appKey, String appSec, boolean testEnv) {
		koalaClient = new KoalaClient(appKey, appSec);
		if (testEnv) {
			koalaClient.enableTestEnv();
		}
	}

	/**
	 * 获取商品详情
	 * @param skuId
	 * @return
	 */
	public KoalaResult<ProductSku> getProductBySkuId(int skuId) {
		KoalaApi api = new KoalaApi();
		api.setApi("kml.d.product");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("skuId", skuId);
		api.setRequestBody(param);
		koalaClient.init();
		KoalaResult<ProductSku> result = koalaClient.sendRequest(api);
		if (null != result.getContent()) {
			result.setData(JSONUtils.fromJSON(result.getContent(), ProductSku.class));	
		}
		koalaClient.close();		
		return result;
	}
	
	/**
	 * 获取全部商品列表
	 * @return
	 */
	public KoalaResult<List<ProductSku>> getProductList() {
		KoalaApi api = new KoalaApi();
		api.setApi("kml.d.productList");
		koalaClient.init();
		KoalaResult<List<ProductSku>> result = koalaClient.sendRequest(api);
		if (null != result.getContent()) {
			result.setData(JSONUtils.fromJSON(result.getContent(), new TypeReference<List<ProductSku>>(){}));	
		}
		koalaClient.close();		
		return result;
	}
	
	/**
	 * 同步订单 这里参数需要调整
	 * @return
	 */
	public KoalaResult<List<OrderReponse>> syncOrder() { 
		KoalaApi api = new KoalaApi();
		api.setApi("kml.d.syncOrder");
		koalaClient.init();
		
		//TODO 请根据自己业务调整
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("order_no", "aaaa20170823002");
		param.put("name", "承太郎");
		param.put("mobile", "17715636523");
		param.put("id_num", "432522198607080515");
		param.put("province", "浙江省");
		param.put("city", "杭州市");
		param.put("district", "西湖区");
		param.put("detail_address", "西溪路560号照相机械研究所");
		param.put("total_price", 16.32);
		
		List<JSONObject> goods = new ArrayList<JSONObject>();
		JSONObject good = new JSONObject();
		good.put("productSkuId", 230);
		good.put("quantity", 1);
		good.put("supplyPrice", 16.32);
		goods.add(good);
		// 业务数据准备结束 后面可以不动

		param.put("goods", goods.toString());

		api.setRequestBody(param);		
		KoalaResult<List<OrderReponse>> result = koalaClient.sendRequest(api);
		if (null != result.getContent()) {
			result.setData(JSONUtils.fromJSON(result.getContent(), new TypeReference<List<OrderReponse>>(){}));	
		}
		koalaClient.close();		
		return result;
	}
	
	/**
	 * 同步物流单号
	 * @param order_no 分销商订单号
	 */
	public KoalaResult<List<OrderLogistic>> logistics(String order_no) {
		KoalaApi api = new KoalaApi();
		api.setApi("kml.d.logistics");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("order_no", order_no);
		api.setRequestBody(param);
		koalaClient.init();
		KoalaResult<List<OrderLogistic>> result = koalaClient.sendRequest(api);
		if (null != result.getContent()) {
			result.setData(JSONUtils.fromJSON(result.getContent(), new TypeReference<List<OrderLogistic>>(){}));	
		}
		koalaClient.close();		
		return result;
	}
	
}
