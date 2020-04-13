package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.dao.VillageDynamicDao;
import com.ebiz.webapp.dao.VillageDynamicRecordDao;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.domain.VillageDynamicRecord;
import com.ebiz.webapp.service.VillageDynamicService;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
@Service
public class VillageDynamicServiceImpl extends BaseImpl implements VillageDynamicService {

	@Resource
	private VillageDynamicDao villageDynamicDao;

	@Resource
	private BaseImgsDao baseImgsDao;

	@Resource
	private CommInfoDao commInfoDao;

	@Resource
	private CommTczhPriceDao commTczhPriceDao;

	@Resource
	private VillageDynamicRecordDao villageDynamicRecordDao;

	public Integer createVillageDynamic(VillageDynamic t) {

		// 插入商品及套餐
		if (null != t.getMap().get("dynamic_comm_info") && null != t.getMap().get("dynamic_commTczh")) {
			CommInfo commInfo = (CommInfo) t.getMap().get("dynamic_comm_info");
			CommTczhPrice commTczhPrice = (CommTczhPrice) t.getMap().get("dynamic_commTczh");
			int comm_id = commInfoDao.insertEntity(commInfo);
			if (comm_id < 1) {
				return -1;
			}
			t.setComm_id(comm_id);
			commTczhPrice.setComm_id("" + comm_id);
			int tczh_id = commTczhPriceDao.insertEntity(commTczhPrice);
		}

		int id = this.villageDynamicDao.insertEntity(t);

		// 插入动态图片
		this.insertDynamicImgs(t, id);

		// 插入记录
		VillageDynamicRecord insertDynamicReord = new VillageDynamicRecord();
		// 商品动态
		if (t.getType().intValue() == Keys.DynamicType.dynamic_type_2.getIndex()) {
			insertDynamicReord.setComm_id(t.getComm_id());
			insertDynamicReord.setComm_name(t.getComm_name());
			insertRecord(insertDynamicReord, id, Keys.DynamicRecordType.DynamicRecordType_7.getIndex(), null, null,
					t.getAdd_user_id(), t.getAdd_user_name(), t.getVillage_id(), villageDynamicRecordDao);
		} else {
			insertRecord(null, id, Keys.DynamicRecordType.DynamicRecordType_6.getIndex(), null, null,
					t.getAdd_user_id(), t.getAdd_user_name(), t.getVillage_id(), villageDynamicRecordDao);
		}

		return id;
	}

	public void insertDynamicImgs(VillageDynamic t, int id) {
		if (null != t.getMap().get("upload_files")) {
			String[] bfs = (String[]) t.getMap().get("upload_files");
			for (int i = 0; i < bfs.length; i++) {
				if (StringUtils.isNotBlank(bfs[i])) {
					BaseImgs baseImgs = new BaseImgs();
					baseImgs.setLink_id(id);

					if (t.getType().intValue() == Keys.DynamicType.dynamic_type_1.getIndex()) {
						baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_40.getIndex());// 动态类型
					} else {
						baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_50.getIndex());// 商品类型
					}

					baseImgs.setFile_path(bfs[i]);
					baseImgsDao.insertEntity(baseImgs);

				} else {
					continue;
				}
			}
		}
	}

	public VillageDynamic getVillageDynamic(VillageDynamic t) {
		return this.villageDynamicDao.selectEntity(t);
	}

	public Integer getVillageDynamicCount(VillageDynamic t) {
		return this.villageDynamicDao.selectEntityCount(t);
	}

	public List<VillageDynamic> getVillageDynamicList(VillageDynamic t) {
		return this.villageDynamicDao.selectEntityList(t);
	}

	public int modifyVillageDynamic(VillageDynamic t) {
		return this.villageDynamicDao.updateEntity(t);
	}

	public int removeVillageDynamic(VillageDynamic t) {
		return this.villageDynamicDao.deleteEntity(t);
	}

	public List<VillageDynamic> getVillageDynamicPaginatedList(VillageDynamic t) {
		return this.villageDynamicDao.selectEntityPaginatedList(t);
	}

}
