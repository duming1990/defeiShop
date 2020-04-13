package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.service.BaseImgsService;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:59
 */
@Service
public class BaseImgsServiceImpl implements BaseImgsService {

	@Resource
	private BaseImgsDao baseImgsDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private OrderInfoDetailsDao orderInfoDetailsDao;

	public Integer createBaseImgs(BaseImgs t) {
		int flag = baseImgsDao.insertEntity(t);

		// 发票寄送成功更新order_detail
		if (null != t.getMap().get("update_order_detail_fp_state")) {

			OrderInfo order = (OrderInfo) t.getMap().get("update_order_detail_fp_state");
			int row = this.orderInfoDao.updateEntity(order);
			if (Integer.valueOf(row) > 0) {
				OrderInfoDetails entity = new OrderInfoDetails();
				entity.setOrder_id(order.getId());
				entity.setFp_state(Keys.Fp_State.Fp_State_1.getIndex());
				List<OrderInfoDetails> entityList = this.orderInfoDetailsDao.selectEntityList(entity);
				if (entityList != null && entityList.size() > 0) {
					for (OrderInfoDetails temp : entityList) {
						temp.setFp_state(Keys.Fp_State.Fp_State_2.getIndex());
						this.orderInfoDetailsDao.updateEntity(temp);
					}
				}
			}
		}
		return flag;
	}

	public Integer createIdCardBaseImgs(BaseImgs t) {
		int flag = 0;
		if (null != t.getMap().get("remove_user_id")) {
			BaseImgs img = new BaseImgs();
			img.getMap().put("link_id", t.getMap().get("remove_user_id"));
			img.getMap().put("img_type", Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
			baseImgsDao.deleteEntity(img);
			for (BaseImgs temp : t.getBaseImgsList()) {
				flag = baseImgsDao.insertEntity(temp);
			}
		}
		return flag;
	}

	public BaseImgs getBaseImgs(BaseImgs t) {
		return this.baseImgsDao.selectEntity(t);
	}

	public Integer getBaseImgsCount(BaseImgs t) {
		return this.baseImgsDao.selectEntityCount(t);
	}

	public List<BaseImgs> getBaseImgsList(BaseImgs t) {
		return this.baseImgsDao.selectEntityList(t);
	}

	public int modifyBaseImgs(BaseImgs t) {
		return this.baseImgsDao.updateEntity(t);
	}

	public int removeBaseImgs(BaseImgs t) {
		return this.baseImgsDao.deleteEntity(t);
	}

	public List<BaseImgs> getBaseImgsPaginatedList(BaseImgs t) {
		return this.baseImgsDao.selectEntityPaginatedList(t);
	}

}
