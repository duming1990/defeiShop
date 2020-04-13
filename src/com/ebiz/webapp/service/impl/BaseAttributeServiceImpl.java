package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseAttributeDao;
import com.ebiz.webapp.dao.BaseAttributeSonDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommTczhAttributeDao;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.domain.BaseAttribute;
import com.ebiz.webapp.domain.BaseAttributeSon;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.service.BaseAttributeService;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
@Service
public class BaseAttributeServiceImpl extends BaseImpl implements BaseAttributeService {

	@Resource
	private BaseAttributeDao baseAttributeDao;

	@Resource
	private BaseAttributeSonDao baseAttributeSonDao;

	@Resource
	private CommTczhAttributeDao commTczhAttributeDao;

	@Resource
	private CommTczhPriceDao commTczhPriceDao;

	@Resource
	private CommInfoDao commInfoDao;

	public Integer createBaseAttribute(BaseAttribute t) {

		Integer id = this.baseAttributeDao.insertEntity(t);

		// 子属性添加
		List<BaseAttributeSon> baseAttributeSonList = t.getBaseAttributeSonList();
		if (null != baseAttributeSonList && baseAttributeSonList.size() > 0) {
			for (BaseAttributeSon son : baseAttributeSonList) {
				son.setId(null);
				son.setAttr_id(id);
				son.setLink_id(t.getLink_id());
				this.baseAttributeSonDao.insertEntity(son);
			}
		}

		if (null != t.getMap().get("need_insert_for_base")) {
			// 需要添加一份到基础库里面
			if (null != t.getLink_id()) {
				CommInfo commInfo = new CommInfo();
				commInfo.setId(t.getLink_id());
				commInfo = this.commInfoDao.selectEntity(commInfo);
				if (null != commInfo && null != commInfo.getOwn_entp_id()) {
					t.setId(null);
					t.setLink_id(null);
					t.setOwn_entp_id(commInfo.getOwn_entp_id());
					t.setAttr_scope(0);
					t.setCls_id(commInfo.getCls_id());
					Integer baseId = this.baseAttributeDao.insertEntity(t);

					// 更新原来的Link_has_attr_id
					BaseAttribute updateBaseAttr = new BaseAttribute();
					updateBaseAttr.setId(id);
					updateBaseAttr.setLink_has_attr_id(baseId);
					this.baseAttributeDao.updateEntity(updateBaseAttr);

					// 子属性添加
					if (null != baseAttributeSonList && baseAttributeSonList.size() > 0) {
						for (BaseAttributeSon son : baseAttributeSonList) {
							son.setId(null);
							son.setAttr_id(baseId);
							this.baseAttributeSonDao.insertEntity(son);
						}
					}

				}
			}
		}
		return id;

	}

	public BaseAttribute getBaseAttribute(BaseAttribute t) {
		return this.baseAttributeDao.selectEntity(t);
	}

	public Integer getBaseAttributeCount(BaseAttribute t) {
		return this.baseAttributeDao.selectEntityCount(t);
	}

	public List<BaseAttribute> getBaseAttributeList(BaseAttribute t) {
		return this.baseAttributeDao.selectEntityList(t);
	}

	public int modifyBaseAttribute(BaseAttribute t) {
		int id = this.baseAttributeDao.updateEntity(t);
		String del_attr_id = (String) t.getMap().get("del_attr_id");
		if (StringUtils.isNotBlank(del_attr_id)) {
			String del_id[] = del_attr_id.split(",");
			if (null != del_id && del_id.length > 0) {
				for (int i = 0; i < del_id.length; i++) {
					if (StringUtils.isNotBlank(del_id[i])) {
						BaseAttributeSon del_son = new BaseAttributeSon();
						del_son.setId(Integer.valueOf(del_id[i]));
						this.baseAttributeSonDao.deleteEntity(del_son);
					}
				}
			}
		}
		// 子属性添加
		List<BaseAttributeSon> baseAttributeSonList = t.getBaseAttributeSonList();
		if (null != baseAttributeSonList && baseAttributeSonList.size() > 0) {
			for (BaseAttributeSon son : baseAttributeSonList) {
				son.setAttr_id(t.getId());
				son.setLink_id(t.getLink_id());
				this.baseAttributeSonDao.insertEntity(son);
			}
		}
		String update_link_table = (String) t.getMap().get("update_link_table");
		if (StringUtils.isNotBlank(update_link_table)) {
			// 删除完成之后 判断是否还有套餐，如果没有 删除相关表 comm_tczh_attribute
			BaseAttribute baseAttribute2 = new BaseAttribute();
			baseAttribute2.setLink_id(t.getLink_id());
			baseAttribute2.setIs_del(0);
			List<BaseAttribute> baseAttributeList2 = this.baseAttributeDao.selectEntityList(baseAttribute2);
			// 证明没有 就删除 comm_tczh_attribute
			if (null == baseAttributeList2 || baseAttributeList2.size() == 0) {
				// 删除CommTczhAttribute
				CommTczhAttribute commTczhAttribute = new CommTczhAttribute();
				commTczhAttribute.setComm_id(t.getLink_id().toString());
				this.commTczhAttributeDao.deleteEntity(commTczhAttribute);

				// 删除comm_tczh_price
				CommTczhPrice commTczhPrice = new CommTczhPrice();
				commTczhPrice.setComm_id(t.getLink_id().toString());
				this.commTczhPriceDao.deleteEntity(commTczhPrice);
			}
		}

		return id;
	}

	public int removeBaseAttribute(BaseAttribute t) {
		return this.baseAttributeDao.deleteEntity(t);
	}

	public List<BaseAttribute> getBaseAttributePaginatedList(BaseAttribute t) {
		return this.baseAttributeDao.selectEntityPaginatedList(t);
	}

}
