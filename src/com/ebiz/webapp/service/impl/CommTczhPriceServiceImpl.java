package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommTczhAttributeDao;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.service.CommTczhPriceService;

/**
 * @author Wu,Yang
 * @version 2014-05-21 10:41
 */
@Service
public class CommTczhPriceServiceImpl extends BaseImpl implements CommTczhPriceService {

	@Resource
	private CommTczhPriceDao commTczhPriceDao;

	@Resource
	private CommTczhAttributeDao commTczhAttributeDao;

	@Resource
	private CommInfoDao commInfoDao;

	public Integer createCommTczhPrice(CommTczhPrice t) {
		return this.commTczhPriceDao.insertEntity(t);
	}

	public CommTczhPrice getCommTczhPrice(CommTczhPrice t) {
		return this.commTczhPriceDao.selectEntity(t);
	}

	public Integer getCommTczhPriceCount(CommTczhPrice t) {
		return this.commTczhPriceDao.selectEntityCount(t);
	}

	public List<CommTczhPrice> getCommTczhPriceList(CommTczhPrice t) {
		return this.commTczhPriceDao.selectEntityList(t);
	}

	public int modifyCommTczhPrice(CommTczhPrice t) {

		return this.commTczhPriceDao.updateEntity(t);
	}

	public int removeCommTczhPrice(CommTczhPrice t) {
		return this.commTczhPriceDao.deleteEntity(t);
	}

	public List<CommTczhPrice> getCommTczhPricePaginatedList(CommTczhPrice t) {
		return this.commTczhPriceDao.selectEntityPaginatedList(t);
	}

	public Integer createCommTczhPriceAndAttr(CommTczhPrice t) {
		// 如果是新建套餐组合或者重建套餐组合
		// 1.先删除以往的price和attr数据
		String comm_id = t.getComm_id();
		CommTczhPrice del_CommTczhPrice = new CommTczhPrice();
		del_CommTczhPrice.setComm_id(comm_id);
		this.commTczhPriceDao.deleteEntity(del_CommTczhPrice);

		CommTczhAttribute del_CommTczhAttribute = new CommTczhAttribute();
		del_CommTczhAttribute.setComm_id(comm_id);
		this.commTczhAttributeDao.deleteEntity(del_CommTczhAttribute);

		CommInfo commInfo = new CommInfo();
		commInfo.setId(Integer.valueOf(t.getComm_id()));
		commInfo.setIs_del(0);
		commInfo = this.commInfoDao.selectEntity(commInfo);

		// 2.重新插入新的价格数据
		List<CommTczhPrice> list_CommTczhPrice = t.getCommTczhPriceList();
		BigDecimal min_price = new BigDecimal("0");
		BigDecimal min_org_price = new BigDecimal("0");
		if (null != list_CommTczhPrice && list_CommTczhPrice.size() > 0) {
			min_price = list_CommTczhPrice.get(0).getComm_price();
			if (null != list_CommTczhPrice.get(0).getOrig_price()) {
				min_org_price = list_CommTczhPrice.get(0).getOrig_price();
			}
			for (CommTczhPrice ctp : list_CommTczhPrice) {
				Integer comm_tczh_id = this.commTczhPriceDao.insertEntity(ctp);
				List<CommTczhAttribute> list_CommTczhAttribute = ctp.getCommTczhAttributeList();
				if (ctp.getComm_price().compareTo(min_price) < 0) {
					min_price = ctp.getComm_price();
				}
				if (null != ctp.getOrig_price() && ctp.getOrig_price().compareTo(min_org_price) < 0) {
					min_org_price = ctp.getOrig_price();// 获得套餐里面的最低价格
				}
				if(null!=list_CommTczhAttribute&&list_CommTczhAttribute.size()>0){
					for (CommTczhAttribute cta : list_CommTczhAttribute) {
						cta.setComm_tczh_id(comm_tczh_id);
						this.commTczhAttributeDao.insertEntity(cta);
					}
				}
			}
		}
		// 修改commInfo 中 min_price
		CommInfo commInfo2 = new CommInfo();
		commInfo2.setId(Integer.valueOf(t.getComm_id()));
		commInfo2.setSale_price(min_price);
		if (min_org_price.compareTo(new BigDecimal(0)) > 0) {
			commInfo2.setPrice_ref(min_org_price);
		}
		commInfo2.setIs_has_tc(1);
		this.commInfoDao.updateEntity(commInfo2);
		return null;
	}

	public int modifyCommTczhPriceInventory(CommTczhPrice t) {
		return this.commTczhPriceDao.updateCommTczhPriceInventory(t);
	}

	public List<CommTczhPrice> getCommTczhJoinCommInfoList(CommTczhPrice t) {
		return this.commTczhPriceDao.selectCommTczhJoinCommInfoList(t);
	}

}
