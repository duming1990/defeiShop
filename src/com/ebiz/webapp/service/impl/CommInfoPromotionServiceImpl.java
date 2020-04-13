package com.ebiz.webapp.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseAttributeSonDao;
import com.ebiz.webapp.dao.CommInfoPromotionDao;
import com.ebiz.webapp.dao.CommTczhAttributeDao;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.domain.BaseAttributeSon;
import com.ebiz.webapp.domain.CommInfoPromotion;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.service.CommInfoPromotionService;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2016-08-08 10:44
 */
@Service
public class CommInfoPromotionServiceImpl implements CommInfoPromotionService {

	@Resource
	private CommInfoPromotionDao commInfoPromotionDao;

	@Resource
	private CommTczhPriceDao commTczhPriceDao;

	@Resource
	private CommTczhAttributeDao commTczhAttributeDao;

	@Resource
	private BaseAttributeSonDao baseAttributeSonDao;

	public Integer createCommInfoPromotion(CommInfoPromotion t) {
		CommInfoPromotion commInfoPromotion = t;
		int id = this.commInfoPromotionDao.insertEntity(t);
		if (null != commInfoPromotion.getComm_tczh_ids()) {
			// 插入团购套餐
			this.insetTgTczh(commInfoPromotion, id);
		}
		return id;
	}

	/**
	 * @param t
	 * @param tg_id
	 */
	public void insetTgTczh(CommInfoPromotion t, int tg_id) {
		String tg_tczh_ids = "";

		String comm_tczh_ids = t.getComm_tczh_ids();
		String str[] = comm_tczh_ids.split(",");
		for (String cur_id : str) {
			CommTczhPrice commTczhPrice = new CommTczhPrice();
			commTczhPrice.setId(Integer.valueOf(cur_id));
			commTczhPrice = this.commTczhPriceDao.selectEntity(commTczhPrice);
			if (null != commTczhPrice) {
				CommTczhAttribute selectCommTczhAttribute = new CommTczhAttribute();
				selectCommTczhAttribute.setComm_tczh_id(commTczhPrice.getId());
				selectCommTczhAttribute.setComm_id(t.getComm_id().toString());
				// selectCommTczhAttribute.setOrder_value(1);
				List<CommTczhAttribute> selectCommTczhAttributeList = this.commTczhAttributeDao
						.selectEntityList(selectCommTczhAttribute);
				if (selectCommTczhAttributeList != null && selectCommTczhAttributeList.size() > 0) {
					CommTczhPrice insetTGCommTczhPrice = null;
					BaseAttributeSon selectBaseAttributeSon = null;
					BaseAttributeSon inserTGBaseAttributeSon = null;
					CommTczhAttribute insertTGCommTczhAttribute = null;
					for (CommTczhAttribute temp : selectCommTczhAttributeList) {

						// 插入套餐价格
						insetTGCommTczhPrice = new CommTczhPrice();
						insetTGCommTczhPrice = commTczhPrice;
						insetTGCommTczhPrice.setId(null);
						insetTGCommTczhPrice.setTczh_type(Keys.tczh_type.tczh_type_1.getIndex());
						insetTGCommTczhPrice.setComm_id(Integer.valueOf(tg_id).toString());
						insetTGCommTczhPrice.setPar_id(Integer.valueOf(cur_id));
						int comm_tczh_id = this.commTczhPriceDao.insertEntity(insetTGCommTczhPrice);

						// 查询商品套餐规格属性
						selectBaseAttributeSon = new BaseAttributeSon();
						selectBaseAttributeSon.setId(Integer.valueOf(temp.getAttr_id()));
						selectBaseAttributeSon.setLink_id(Integer.valueOf(temp.getComm_id()));
						selectBaseAttributeSon = this.baseAttributeSonDao.selectEntity(selectBaseAttributeSon);
						if (selectBaseAttributeSon != null) {
							// 插入商品套餐规格属性
							inserTGBaseAttributeSon = new BaseAttributeSon();
							inserTGBaseAttributeSon = selectBaseAttributeSon;
							inserTGBaseAttributeSon.setId(null);
							inserTGBaseAttributeSon.setType(Keys.tczh_type.tczh_type_1.getIndex());
							inserTGBaseAttributeSon.setLink_id(tg_id);
							int attr_id = this.baseAttributeSonDao.insertEntity(inserTGBaseAttributeSon);

							// 插入商品套餐属性名称
							insertTGCommTczhAttribute = new CommTczhAttribute();
							insertTGCommTczhAttribute = temp;
							insertTGCommTczhAttribute.setId(null);
							insertTGCommTczhAttribute.setType(Keys.tczh_type.tczh_type_1.getIndex());
							insertTGCommTczhAttribute.setComm_id(Integer.valueOf(tg_id).toString());
							insertTGCommTczhAttribute.setComm_tczh_id(comm_tczh_id);
							insertTGCommTczhAttribute.setAttr_id(Integer.valueOf(attr_id).toString());
							insertTGCommTczhAttribute.setPar_id(Integer.valueOf(cur_id));
							this.commTczhAttributeDao.insertEntity(insertTGCommTczhAttribute);

							if ("" == tg_tczh_ids) {
								tg_tczh_ids = tg_tczh_ids + comm_tczh_id;
							} else {
								tg_tczh_ids = tg_tczh_ids + "," + comm_tczh_id;
							}
						}
					}
					CommInfoPromotion updataCommTczhIds = new CommInfoPromotion();
					updataCommTczhIds.setId(tg_id);
					updataCommTczhIds.setTg_tczh_ids(tg_tczh_ids);
					commInfoPromotionDao.updateEntity(updataCommTczhIds);
				}
			}
		}
	}

	public CommInfoPromotion getCommInfoPromotion(CommInfoPromotion t) {
		return this.commInfoPromotionDao.selectEntity(t);
	}

	public Integer getCommInfoPromotionCount(CommInfoPromotion t) {
		return this.commInfoPromotionDao.selectEntityCount(t);
	}

	public List<CommInfoPromotion> getCommInfoPromotionList(CommInfoPromotion t) {
		return this.commInfoPromotionDao.selectEntityList(t);
	}

	public int modifyCommInfoPromotion(CommInfoPromotion t) {
		// updateTgTczhIds(t);
		if (null != t.getMap().get("update_tg_commInfo") && null != t.getMap().get("comm_tczh_id")) {
			int i = 0;

			// 团购套餐先删后增
			CommInfoPromotion tgCommInfo = new CommInfoPromotion();
			tgCommInfo.setId(t.getId());
			tgCommInfo = this.commInfoPromotionDao.selectEntity(tgCommInfo);
			if (null != tgCommInfo) {
				// 之前已选择的商品套餐IDs
				// String ids[] = tgCommInfo.getTg_tczh_ids().split(",");
				// for (String id : ids) {
				// if (null != id && !id.equals("")) {
				//
				// }
				//
				// }
				CommTczhPrice a = new CommTczhPrice();
				// a.setId(Integer.valueOf(id));
				a.setComm_id(tgCommInfo.getId().toString());
				a.getMap().put("tczh_type", Keys.tczh_type.tczh_type_1.getIndex());
				this.commTczhPriceDao.deleteEntity(a);

				CommTczhAttribute b = new CommTczhAttribute();
				// b.setComm_tczh_id(Integer.valueOf(id));
				b.setType(Keys.tczh_type.tczh_type_1.getIndex());
				b.setComm_id(tgCommInfo.getId().toString());
				this.commTczhAttributeDao.deleteEntity(b);

				BaseAttributeSon baseAttributeSon = new BaseAttributeSon();
				baseAttributeSon.getMap().put("link_id", tgCommInfo.getId());
				baseAttributeSon.getMap().put("type", Keys.tczh_type.tczh_type_1.getIndex());
				this.baseAttributeSonDao.deleteEntity(baseAttributeSon);

				// 后增
				String tg_tczh_ids = "";

				// 当前已选择的商品套餐IDs
				String comm_tczh_ids = t.getMap().get("comm_tczh_id").toString();
				System.out.println("===当前已选择的商品套餐IDs：" + comm_tczh_ids);
				if (tgCommInfo.getComm_tczh_ids() != null && !"".equals(tgCommInfo.getComm_tczh_ids())) {

					String str[] = comm_tczh_ids.split(",");
					if (null != str && str.length > 0) {
						for (String tczh_id : str) {
							System.out.println("当前循环中的ID：" + tczh_id);
							CommTczhPrice commTczhPrice = new CommTczhPrice();
							commTczhPrice.setId(Integer.valueOf(tczh_id));
							commTczhPrice = this.commTczhPriceDao.selectEntity(commTczhPrice);
							if (null != commTczhPrice) {
								CommTczhAttribute selectCommTczhAttribute = new CommTczhAttribute();
								selectCommTczhAttribute.setComm_tczh_id(commTczhPrice.getId());
								// 这里应该是当前页面传来的商品ID
								selectCommTczhAttribute.setComm_id(t.getComm_id().toString());
								List<CommTczhAttribute> selectCommTczhAttributeList = this.commTczhAttributeDao
										.selectEntityList(selectCommTczhAttribute);
								if (selectCommTczhAttributeList != null && selectCommTczhAttributeList.size() > 0) {
									for (CommTczhAttribute temp : selectCommTczhAttributeList) {
										BaseAttributeSon selectBaseAttributeSon = new BaseAttributeSon();
										selectBaseAttributeSon.setId(Integer.valueOf(temp.getAttr_id()));
										selectBaseAttributeSon.setLink_id(Integer.valueOf(temp.getComm_id()));
										selectBaseAttributeSon = this.baseAttributeSonDao
												.selectEntity(selectBaseAttributeSon);
										if (selectBaseAttributeSon != null) {
											BaseAttributeSon inserTGBaseAttributeSon = new BaseAttributeSon();
											inserTGBaseAttributeSon = selectBaseAttributeSon;
											inserTGBaseAttributeSon.setId(null);
											inserTGBaseAttributeSon.setType(Keys.tczh_type.tczh_type_1.getIndex());
											inserTGBaseAttributeSon.setLink_id(tgCommInfo.getId());
											int attr_id = this.baseAttributeSonDao
													.insertEntity(inserTGBaseAttributeSon);

											CommTczhPrice insetTGCommTczhPrice = new CommTczhPrice();
											insetTGCommTczhPrice = commTczhPrice;
											insetTGCommTczhPrice.setId(null);
											insetTGCommTczhPrice.setTczh_type(Keys.tczh_type.tczh_type_1.getIndex());
											insetTGCommTczhPrice.setComm_id(Integer.valueOf(tgCommInfo.getId())
													.toString());
											insetTGCommTczhPrice.setPar_id(Integer.valueOf(tczh_id));
											int comm_tczh_id = this.commTczhPriceDao.insertEntity(insetTGCommTczhPrice);

											CommTczhAttribute insertTGCommTczhAttribute = new CommTczhAttribute();
											insertTGCommTczhAttribute = temp;
											insertTGCommTczhAttribute.setId(null);
											insertTGCommTczhAttribute.setType(Keys.tczh_type.tczh_type_1.getIndex());
											insertTGCommTczhAttribute.setComm_id(Integer.valueOf(tgCommInfo.getId())
													.toString());
											insertTGCommTczhAttribute.setComm_tczh_id(comm_tczh_id);
											insertTGCommTczhAttribute.setAttr_id(Integer.valueOf(attr_id).toString());
											insertTGCommTczhAttribute.setPar_id(Integer.valueOf(tczh_id));
											this.commTczhAttributeDao.insertEntity(insertTGCommTczhAttribute);

											if ("" == tg_tczh_ids) {
												tg_tczh_ids = tg_tczh_ids + comm_tczh_id;
											} else {
												tg_tczh_ids = tg_tczh_ids + "," + comm_tczh_id;

											}
											if ("" != tg_tczh_ids) {
												t.setTg_tczh_ids(tg_tczh_ids);
											}
										}
									}
									// CommInfoPromotion updataCommTczhIds = new CommInfoPromotion();
									// updataCommTczhIds.setId(tgCommInfo.getId());
									// updataCommTczhIds.setTg_tczh_ids(tg_tczh_ids);
									// System.out.println("==最后tg_tczh_ids:" + tg_tczh_ids);
									// i = commInfoPromotionDao.updateEntity(updataCommTczhIds);
								}
							}
						}
					}
				}

			}
			// return i;
		}
		return this.commInfoPromotionDao.updateEntity(t);
	}

	/**
	 * @param t
	 */
	public void updateTgTczhIds(CommInfoPromotion t) {
		if (null != t.getMap().get("update_comm_tczh_ids")) {
			CommInfoPromotion tgCommInfo = new CommInfoPromotion();
			tgCommInfo.setId(t.getId());
			tgCommInfo = this.commInfoPromotionDao.selectEntity(tgCommInfo);
			if (null != tgCommInfo) {
				if (null != tgCommInfo.getComm_tczh_ids()) {
					// 新的ids
					String[] s_new_ids = t.getComm_tczh_ids().split(",");
					Integer int_new_ids[] = new Integer[s_new_ids.length];
					for (int i = 0; i < s_new_ids.length; i++) {
						int_new_ids[i] = Integer.parseInt(s_new_ids[i]);
					}
					// 旧的
					String[] s_old_ids = tgCommInfo.getComm_tczh_ids().split(",");
					Integer int_old_ids[] = new Integer[s_old_ids.length];
					for (int i = 0; i < s_old_ids.length; i++) {
						int_old_ids[i] = Integer.parseInt(s_old_ids[i]);
					}
					// 新旧数组对比是否全部相等
					Arrays.sort(int_new_ids);
					Arrays.sort(int_old_ids);
					if (!Arrays.equals(int_new_ids, int_old_ids)) {
						// 两个数组中的元素值不相同

						// Integer[] inset_ids = ;
						List<Integer> inset_ids = new ArrayList<Integer>();
						List<Integer> del_ids = new ArrayList<Integer>();

						for (int i = 0; i < int_new_ids.length; i++) {
							for (int j = 0; j < int_old_ids.length; i++) {
								if (int_new_ids[i] == int_old_ids[j]) {
									inset_ids.add(int_new_ids[i]);
								} else {
									del_ids.add(int_old_ids[j]);
								}
							}
						}

						// 去重
						for (Integer i : del_ids) {
							if (del_ids.contains(i))// 如果list2中存在index1中的元素就去掉这个元素
								del_ids.remove(i);
						}
						// 删除去除的团购套餐属性
						for (Integer i : del_ids) {
							CommTczhAttribute a = new CommTczhAttribute();
							a.setType(Keys.tczh_type.tczh_type_1.getIndex());
							a.setComm_tczh_id(i);
							a.setComm_id(tgCommInfo.getComm_id().toString());
							this.commTczhAttributeDao.deleteEntity(a);

							CommTczhPrice b = new CommTczhPrice();
							b.setId(i);
							b.setTczh_type(Keys.tczh_type.tczh_type_1.getIndex());
							b.setComm_id(tgCommInfo.getComm_id().toString());
							this.commTczhPriceDao.deleteEntity(b);

						}

					}

				}
			}
		}
	}

	public int removeCommInfoPromotion(CommInfoPromotion t) {
		return this.commInfoPromotionDao.deleteEntity(t);
	}

	public List<CommInfoPromotion> getCommInfoPromotionPaginatedList(CommInfoPromotion t) {
		return this.commInfoPromotionDao.selectEntityPaginatedList(t);
	}

}
