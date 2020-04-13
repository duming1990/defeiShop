package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseAttributeDao;
import com.ebiz.webapp.dao.BaseAttributeSonDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommInfoPoorsDao;
import com.ebiz.webapp.dao.CommInfoTagsDao;
import com.ebiz.webapp.dao.CommTczhAttributeDao;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.dao.EntpInfoDao;
import com.ebiz.webapp.dao.PdContentDao;
import com.ebiz.webapp.dao.PdImgsDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.VillageDynamicDao;
import com.ebiz.webapp.domain.BaseAttribute;
import com.ebiz.webapp.domain.BaseAttributeSon;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommInfoTags;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.PdContent;
import com.ebiz.webapp.domain.PdImgs;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.service.CommInfoService;
import com.ebiz.webapp.web.util.WeiXinSendMessageUtils;

/**
 * @author Wu,Yang
 * @version 2013-09-25 20:27
 */
@Service
public class CommInfoServiceImpl extends BaseImpl implements CommInfoService {
	@Resource
	private CommInfoDao commInfoDao;

	@Resource
	private BaseAttributeDao baseAttributeDao;

	@Resource
	private BaseAttributeSonDao baseAttributeSonDao;

	@Resource
	private CommTczhAttributeDao commTczhAttributeDao;

	@Resource
	private CommTczhPriceDao commTczhPriceDao;

	@Resource
	private PdImgsDao pdImgsDao;

	@Resource
	private PdContentDao pdContentDao;

	@Resource
	private EntpInfoDao entpInfoDao;

	@Resource
	private CommInfoTagsDao commInfoTagsDao;

	@Resource
	private CommInfoPoorsDao commInfoPoorsDao;

	@Resource
	private VillageDynamicDao villageDynamicDao;

	@Resource
	private UserInfoDao userInfoDao;

	public Integer createCommInfo(CommInfo t) {
		Integer id = this.commInfoDao.insertEntity(t);

		if (null != t.getMap().get("update_comm_tczh_price")) {
			// 不是团购商品添加商品属性
			String tczh_name[] = t.getMap().get("tczh_names").toString().split(",");

			if (tczh_name.length > 0) {
				t.setIs_has_tc(1);
			} else {
				t.setIs_has_tc(0);
			}
			this.insertCommTczhPriceCount3(t, id);
			this.setCalculationMinPrice(t);
			this.commInfoDao.updateEntity(t);
		}

		// 插入商品轮播图
		List<PdImgs> list = t.getCommImgsList();
		if (list != null && list.size() > 0) {
			for (PdImgs pdImges : list) {
				pdImges.setPd_id(id);
				pdImges.setId(null);
				this.pdImgsDao.insertEntity(pdImges);
			}
		}

		// 插入商品详细信息
		String comm_content = t.getComm_content();
		PdContent pdContent = new PdContent();
		pdContent.setType(1);
		pdContent.setPd_id(id);
		pdContent.setContent(comm_content);
		this.pdContentDao.insertEntity(pdContent);

		// 需要插入套餐属性从别的表中
		if (null != t.getMap().get("needInsertTczh")) {

			String from_commid = (String) t.getMap().get("old_comm_id");
			Integer to_commid = id;

			// 复制BaseAttribute,BaseAttributeSon表,并将新旧attribute记录到map中,供CommTczhAttribute表替换用
			Map<String, String> attrMap = new HashMap<String, String>();
			BaseAttribute baseAttibute = new BaseAttribute();
			baseAttibute.setLink_id(Integer.valueOf(from_commid));
			List<BaseAttribute> baseAttibuteList = this.baseAttributeDao.selectEntityList(baseAttibute);
			for (BaseAttribute baseAttribute_ : baseAttibuteList) {
				Integer from_baseAttrId = baseAttribute_.getId();
				baseAttribute_.setId(null);
				baseAttribute_.setLink_id(to_commid);
				Integer to_baseAttrId = this.baseAttributeDao.insertEntity(baseAttribute_);
				BaseAttributeSon baseAttributeSon = new BaseAttributeSon();
				baseAttributeSon.setLink_id(Integer.valueOf(from_commid));
				baseAttributeSon.setAttr_id(from_baseAttrId);
				List<BaseAttributeSon> baseAttributeSonList = this.baseAttributeSonDao
						.selectEntityList(baseAttributeSon);
				for (BaseAttributeSon baseAttributeSon_ : baseAttributeSonList) {
					Integer frombaseAttributeSonId = baseAttributeSon_.getId();
					baseAttributeSon_.setId(null);
					baseAttributeSon_.setLink_id(to_commid);
					baseAttributeSon_.setAttr_id(to_baseAttrId);
					Integer tobaseAttributeSonId = this.baseAttributeSonDao.insertEntity(baseAttributeSon_);
					attrMap.put(String.valueOf(frombaseAttributeSonId), String.valueOf(tobaseAttributeSonId));
				}
			}

			// 复制CommTczhPrice,CommTczhAttribute表
			CommTczhPrice commTczhPrice = new CommTczhPrice();
			commTczhPrice.setComm_id(from_commid);
			List<CommTczhPrice> commTczhPriceList = this.commTczhPriceDao.selectEntityList(commTczhPrice);
			for (CommTczhPrice commTczhPrice_ : commTczhPriceList) {
				Integer fromCommTczhPriceId = commTczhPrice_.getId();
				commTczhPrice_.setId(null);
				commTczhPrice_.setComm_id(String.valueOf(to_commid));
				Integer toCommTczhPriceId = this.commTczhPriceDao.insertEntity(commTczhPrice_);

				CommTczhAttribute commTczhAttribute = new CommTczhAttribute();
				commTczhAttribute.setComm_id(from_commid);
				commTczhAttribute.setComm_tczh_id(fromCommTczhPriceId);
				List<CommTczhAttribute> commTczhAttributeList = this.commTczhAttributeDao
						.selectEntityList(commTczhAttribute);
				for (CommTczhAttribute commTczhAttribute_ : commTczhAttributeList) {
					commTczhAttribute_.setId(null);
					commTczhAttribute_.setComm_tczh_id(toCommTczhPriceId);
					commTczhAttribute_.setComm_id(String.valueOf(to_commid));
					commTczhAttribute_.setAttr_id(attrMap.get(commTczhAttribute_.getAttr_id()));
					this.commTczhAttributeDao.insertEntity(commTczhAttribute_);
				}
			}
		}

		if (null != t.getMap().get("input_tczh_price_update_linkTabel")) {
			CommTczhPrice commTczhPrice = (CommTczhPrice) t.getMap().get("input_tczh_price_update_linkTabel");
			commTczhPrice.setComm_id(String.valueOf(id));
			commTczhPrice.setCost_price(t.getPrice_ref());
			commTczhPrice.setComm_price(t.getSale_price());
			commTczhPrice.setUpdate_date(new Date());
			commTczhPrice.setComm_barcode(createCommTczhPriceNo(String.valueOf(id), t.getComm_barcode()));
			int tczh_id = commTczhPriceDao.insertEntity(commTczhPrice);

		}

		// 选择频道
		if (null != t.getMap().get("tag_ids") && StringUtils.isNotBlank(t.getMap().get("tag_ids").toString())) {
			String[] tag_ids = t.getMap().get("tag_ids").toString().split(",");
			if (null != tag_ids && tag_ids.length > 0) {
				// 先删除，再新增
				CommInfoTags commInfoTags = new CommInfoTags();
				commInfoTags.getMap().put("comm_id", id);
				this.commInfoTagsDao.deleteEntity(commInfoTags);

				for (String tag_id : tag_ids) {
					CommInfoTags temp = new CommInfoTags();
					temp.setComm_id(id);
					temp.setAdd_date(t.getAdd_date());
					temp.setAdd_user_id(t.getAdd_user_id().longValue());
					temp.setTag_id(Integer.valueOf(tag_id));
					this.commInfoTagsDao.insertEntity(temp);
				}
			}

		}

		// 选择扶贫对象
		if (null != t.getPoorsList() && t.getPoorsList().size() > 0) {
			// 先删除，再新增
			CommInfoPoors commInfoPoors = new CommInfoPoors();
			commInfoPoors.getMap().put("comm_id", id);
			this.commInfoPoorsDao.deleteEntity(commInfoPoors);
			for (CommInfoPoors temp : t.getPoorsList()) {
				temp.setComm_id(id);
				temp.setAdd_date(t.getAdd_date());
				temp.setAdd_user_id(t.getAdd_user_id());
				this.commInfoPoorsDao.insertEntity(temp);
			}
		}

		return id;
	}

	public void insertCommTczhPriceCount3(CommInfo t, Integer id) {
		if (null != t.getMap().get("tczh_names") && null != t.getMap().get("tczh_prices")
				&& null != t.getMap().get("inventorys")) {

			CommTczhPrice del_CommTczhPrice = new CommTczhPrice();
			del_CommTczhPrice.setComm_id(t.getId().toString());
			del_CommTczhPrice.setTczh_type(0);
			this.commTczhPriceDao.deleteEntity(del_CommTczhPrice);

			String tczh_name[] = t.getMap().get("tczh_names").toString().split(",");
			String tczh_price[] = t.getMap().get("tczh_prices").toString().split(",");
			String inventory[] = t.getMap().get("inventorys").toString().split(",");
			String comm_weight[] = t.getMap().get("comm_weights").toString().split(",");
			String group_price[] = null;
			if (null != t.getMap().get("group_price")) {
				group_price = t.getMap().get("group_price").toString().split(",");
			}
			CommTczhPrice commTczh = null;
			for (int i = 0; i < tczh_name.length; i++) {
				commTczh = new CommTczhPrice();
				commTczh.setComm_id(id.toString());
				commTczh.setTczh_name(tczh_name[i]);
				commTczh.setComm_price(new BigDecimal(tczh_price[i]));
				commTczh.setComm_weight(new BigDecimal(comm_weight[i]));
				commTczh.setInventory(Integer.valueOf(inventory[i]));
				if (group_price != null) {
					commTczh.setGroup_price(new BigDecimal(group_price[i]));
				}
				commTczh.setOrder_value(0);
				commTczh.setAdd_date(new Date());
				commTczh.setAdd_user_id(t.getAdd_user_id());
				int a = this.commTczhPriceDao.insertEntity(commTczh);
			}
		}
	}

	public CommInfo getCommInfo(CommInfo t) {
		return this.commInfoDao.selectEntity(t);
	}

	public Integer getCommInfoCount(CommInfo t) {
		return this.commInfoDao.selectEntityCount(t);
	}

	public List<CommInfo> getCommInfoList(CommInfo t) {
		return this.commInfoDao.selectEntityList(t);
	}

	public int modifyCommInfo(CommInfo t) {
		if (null != t.getIs_del() && t.getIs_del() == 1 && null != t.getId()) {
			// 删除商品，同时删除套餐
			CommTczhPrice del_CommTczhPrice = new CommTczhPrice();
			del_CommTczhPrice.setComm_id(t.getId().toString());
			// this.commTczhPriceDao.deleteEntity(del_CommTczhPrice);
		}

		if (null != t.getMap().get("update_comm_tczh_price")) {
			// 不是团购商品添加商品属性
			String tczh_name[] = t.getMap().get("tczh_names").toString().split(",");

			if (tczh_name.length > 0) {
				t.setIs_has_tc(1);
				this.insertCommTczhPriceCount3(t, t.getId());
			} else {
				t.setIs_has_tc(0);
				this.insertCommTczhPriceCount3(t, t.getId());
			}
			// 团购商品添加商品属性
			// this.insertCommTczhPriceCount5(t, t.getId());

			// 计算出最小的价格放到comm_info中ref_price
			this.setCalculationMinPrice(t);
		}

		if (null != t.getMap().get("update_comm_tczh_price_by_comm_id") && null != t.getCommTczhPrice()
				&& null != t.getCommTczhPrice().getComm_id()) {
			this.commTczhPriceDao.updateEntity(t.getCommTczhPrice());
		}

		// 插入产品轮播图
		List<PdImgs> list = t.getCommImgsList();
		if (list != null && list.size() > 0) {
			PdImgs entity = new PdImgs();
			entity.setPd_id(t.getId());
			this.pdImgsDao.deleteEntity(entity);

			for (PdImgs pdImges : list) {
				pdImges.setPd_id(t.getId());
				this.pdImgsDao.insertEntity(pdImges);
			}
		}

		// 更新商品详细信息
		if (null != t.getComm_content()) {
			String comm_content = t.getComm_content();
			PdContent pdContent = new PdContent();
			pdContent.setType(1);
			pdContent.setPd_id(t.getId());
			pdContent = pdContentDao.selectEntity(pdContent);
			if (null != pdContent) {
				pdContent.setContent(comm_content);
				this.pdContentDao.updateEntity(pdContent);
			} else { // 解决以前数据没有问题
				PdContent pdContentInsert = new PdContent();
				pdContentInsert.setType(1);
				pdContentInsert.setPd_id(t.getId());
				pdContentInsert.setContent(comm_content);
				pdContentDao.insertEntity(pdContentInsert);
			}
		}

		if (null != t.getMap().get("add_sale_count_update_link_table")) {// 增加或者减少销量 然后修改对应信息

			CommInfo commInfo = new CommInfo();
			commInfo.setId(t.getId());
			commInfo = commInfoDao.selectEntity(commInfo);
			if (commInfo != null) {
				CommInfo temp_commInfo = new CommInfo();
				temp_commInfo.setId(commInfo.getId());
				temp_commInfo.getMap().put("add_sale_count_update",
						Integer.valueOf(t.getMap().get("sale_count_update_add").toString()));
				commInfoDao.updateEntity(temp_commInfo);

			}
		}

		// 选择频道
		if (null != t.getMap().get("tag_ids") && StringUtils.isNotBlank(t.getMap().get("tag_ids").toString())) {
			String[] tag_ids = t.getMap().get("tag_ids").toString().split(",");
			if (null != tag_ids && tag_ids.length > 0) {
				// 先删除，再新增
				CommInfoTags commInfoTags = new CommInfoTags();
				commInfoTags.getMap().put("comm_id", t.getId());
				this.commInfoTagsDao.deleteEntity(commInfoTags);

				for (String tag_id : tag_ids) {
					CommInfoTags temp = new CommInfoTags();
					temp.setComm_id(t.getId());
					temp.setAdd_date(t.getUpdate_date());
					temp.setAdd_user_id(t.getUpdate_user_id().longValue());
					temp.setTag_id(Integer.valueOf(tag_id));
					this.commInfoTagsDao.insertEntity(temp);
				}
			}

		}

		// 选择扶贫对象
		if (null != t.getPoorsList() && t.getPoorsList().size() > 0) {
			// 先删除，再新增
			CommInfoPoors commInfoPoors = new CommInfoPoors();
			commInfoPoors.getMap().put("comm_id", t.getId());
			this.commInfoPoorsDao.deleteEntity(commInfoPoors);
			for (CommInfoPoors temp : t.getPoorsList()) {
				temp.setComm_id(t.getId());
				temp.setAdd_date(t.getUpdate_date());
				temp.setAdd_user_id(t.getUpdate_user_id());
				this.commInfoPoorsDao.insertEntity(temp);
			}
		}
		// 更新圈子动态
		if (t.getMap().get("update_dynamic") != null) {
			VillageDynamic vDynamic = (VillageDynamic) t.getMap().get("update_dynamic");
			this.villageDynamicDao.updateEntity(vDynamic);
		}
		// 村商品单独删除
		if (t.getMap().get("commType7DeleteLinkVillageDynamic") != null) {
			VillageDynamic vDynamic = new VillageDynamic();
			vDynamic.getMap().put("comm_id", t.getId());
			vDynamic.setIs_del(1);
			this.villageDynamicDao.updateEntity(vDynamic);
		}
		// 村商品多删除
		if (t.getMap().get("forEach_pks_and_deleteVillageDynamic") != null) {
			String[] pks = (String[]) t.getMap().get("forEach_pks_and_deleteVillageDynamic");
			if (null != pks && pks.length > 0) {
				for (String temp : pks) {
					CommInfo entity = new CommInfo();
					entity.setIs_del(1);
					entity.setDel_date(new Date());
					entity.setDel_user_id(t.getDel_user_id());
					entity.setId(Integer.valueOf(temp));
					this.commInfoDao.updateEntity(entity);
					// 这个地方在删除动态
					VillageDynamic vDynamic = new VillageDynamic();
					vDynamic.getMap().put("comm_id", temp);
					vDynamic.setIs_del(1);
					this.villageDynamicDao.updateEntity(vDynamic);
				}
			}
		}
		// 发送审核消息
		if (t.getMap().get("send_audit_msg") != null) {
			Integer code = (Integer) t.getMap().get("send_audit_msg");
			CommInfo commInfo = new CommInfo();
			commInfo.setId(t.getId());
			commInfo = commInfoDao.selectEntity(commInfo);
			if (null != commInfo) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(commInfo.getAdd_user_id());
				userInfo.setIs_del(0);
				userInfo = this.userInfoDao.selectEntity(userInfo);
				if (Integer.valueOf(code) == 1) {// 审核通过
					WeiXinSendMessageUtils.goodsAuditSuccess(commInfo, userInfo);
				}
				if (Integer.valueOf(code) == -1) {// 审核不通过
					WeiXinSendMessageUtils.goodsAuditFailure(commInfo, userInfo);
				}
			}
		}

		return this.commInfoDao.updateEntity(t);
	}

	/**
	 * 计算出最小的价格放到comm_info中ref_price
	 **/
	public void setCalculationMinPrice(CommInfo commInfo) {
		BigDecimal min_price = new BigDecimal("0");
		BigDecimal min_org_price = new BigDecimal("0");
		Integer sumInventory = 0;// 库存总量
		CommTczhPrice priceList = new CommTczhPrice();
		priceList.setComm_id(commInfo.getId().toString());
		List<CommTczhPrice> tczhPriceList = commTczhPriceDao.selectEntityList(priceList);
		min_price = tczhPriceList.get(0).getComm_price();
		if (null != tczhPriceList.get(0).getOrig_price()) {
			min_org_price = tczhPriceList.get(0).getOrig_price();// 原价格
		}
		// 1.修改套餐表价格
		for (CommTczhPrice cur : tczhPriceList) {
			if (cur.getComm_price().compareTo(min_price) < 0) {
				min_price = cur.getComm_price();// 获得套餐里面的最低价格
			}
			commInfo.setSale_price(min_price);
			sumInventory = sumInventory + cur.getInventory();
			if (null != cur.getOrig_price() && cur.getOrig_price().compareTo(min_org_price) < 0) {
				min_org_price = cur.getOrig_price();// 获得套餐里面的最低价格

				if (min_org_price.compareTo(new BigDecimal(0)) > 0) {
					commInfo.setPrice_ref(min_org_price);
				}
			}
		}
		commInfo.setInventory(sumInventory);// 把总库存存入commInfo

	}

	public int removeCommInfo(CommInfo t) {
		return this.commInfoDao.deleteEntity(t);
	}

	public List<CommInfo> getCommInfoPaginatedList(CommInfo t) {
		return this.commInfoDao.selectEntityPaginatedList(t);
	}

	@Override
	public List<CommInfo> getCommInfoForDistinct(CommInfo t) {
		return this.commInfoDao.selectCommInfoForDistinct(t);
	}

	/**
	 * @author Li,Ka
	 * @version 2012-04-28
	 * @desc 根据属性组合查询
	 */
	public Integer getCommInfoForSearchAttrCount(CommInfo t) {
		return this.commInfoDao.selectCommInfoForSearchAttrCount(t);
	}

	/**
	 * @author renqiang
	 * @version 2017-11-22
	 * @desc 商品是否已上架
	 */
	public Integer getCommInfoNameCount(CommInfo t) {
		return this.commInfoDao.selectCommInfoNameCount(t);
	}

	/**
	 * @author Li,Ka
	 * @version 2012-04-28
	 * @desc 根据属性组合查询分页
	 */
	public List<CommInfo> getCommInfoForSearchAttrPaginatedList(CommInfo t) {
		return this.commInfoDao.selectCommInfoForSearchAttrPaginatedList(t);
	}

	/**
	 * @author Myg
	 * @version 2013-11-05
	 * @desc 将最小套餐价格存入商品表的min_price表中
	 */
	public int modifyCommInfoMinPrice(CommInfo t) {
		return this.commInfoDao.updateCommInfoMinPrice(t);
	}

	public int modifyCommInfoInventory(CommInfo t) {
		return this.commInfoDao.updateCommInfoInventory(t);
	}

	// 套餐价格表comm_barcode唯一识别码
	public String createCommTczhPriceNo(String comm_id, String comm_barcode) {
		CommTczhPrice commTczhPriceCount = new CommTczhPrice();
		commTczhPriceCount.setComm_id(comm_id);
		int count = this.commTczhPriceDao.selectEntityCount(commTczhPriceCount);
		String pre_varchar = StringUtils.leftPad(comm_id, 11, "0")
				+ StringUtils.leftPad(String.valueOf(count + 1), 3, "0");
		return pre_varchar;
	}

	public List<CommInfo> getCommInfoListTwo(CommInfo t) {
		return this.commInfoDao.selectCommInfoListTwo(t);
	}

	public List<CommInfo> getCommInfoPageList(CommInfo t) {
		return this.commInfoDao.selectCommInfoPageList(t);
	}

	public Integer getClassRankingListCount(CommInfo t) {
		return commInfoDao.selectClassRankingListCount(t);
	}

	public List<CommInfo> getClassRankingList(CommInfo t) {
		return commInfoDao.selectClassRankingList(t);
	}

	@Override
	public List<CommInfo> getCommInfoRootClsId(CommInfo t) {
		return commInfoDao.selectCommInfoRootClsId(t);
	}

	public Integer getCommInfoCountForFuPin(CommInfo t) {
		return this.commInfoDao.selectCommInfoCountForFuPin(t);
	}

	public List<CommInfo> getCommInfoPaginatedListForFuPin(CommInfo t) {
		return this.commInfoDao.selectCommInfoPaginatedListForFuPin(t);
	}

	@Override
	public List<CommInfo> getWelfareCommInfoList(CommInfo t) {
		return this.commInfoDao.selectWelfareCommInfoList(t);
	}

	@Override
	public CommInfo getCommInfoByOrderId(Integer orderId) {
		return this.commInfoDao.selectCommInfoByOrderId(orderId);
	}

}
