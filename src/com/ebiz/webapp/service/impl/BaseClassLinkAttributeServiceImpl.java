package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseClassLinkAttributeDao;
import com.ebiz.webapp.domain.BaseClassLinkAttribute;
import com.ebiz.webapp.service.BaseClassLinkAttributeService;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
@Service
public class BaseClassLinkAttributeServiceImpl implements BaseClassLinkAttributeService {

	@Resource
	private BaseClassLinkAttributeDao baseClassLinkAttributeDao;

	public Integer createBaseClassLinkAttribute(BaseClassLinkAttribute t) {
		if (t.getMap().get("cks") != null) {
			return createBaseClassLinkAttributes(t);
		}
		return this.baseClassLinkAttributeDao.insertEntity(t);
	}

	public BaseClassLinkAttribute getBaseClassLinkAttribute(BaseClassLinkAttribute t) {
		return this.baseClassLinkAttributeDao.selectEntity(t);
	}

	public Integer getBaseClassLinkAttributeCount(BaseClassLinkAttribute t) {
		return this.baseClassLinkAttributeDao.selectEntityCount(t);
	}

	public List<BaseClassLinkAttribute> getBaseClassLinkAttributeList(BaseClassLinkAttribute t) {
		return this.baseClassLinkAttributeDao.selectEntityList(t);
	}

	public int modifyBaseClassLinkAttribute(BaseClassLinkAttribute t) {
		return this.baseClassLinkAttributeDao.updateEntity(t);
	}

	public int removeBaseClassLinkAttribute(BaseClassLinkAttribute t) {
		return this.baseClassLinkAttributeDao.deleteEntity(t);
	}

	public List<BaseClassLinkAttribute> getBaseClassLinkAttributePaginatedList(BaseClassLinkAttribute t) {
		return this.baseClassLinkAttributeDao.selectEntityPaginatedList(t);
	}

	private Integer createBaseClassLinkAttributes(BaseClassLinkAttribute t) {
		String[] cks = (String[]) t.getMap().get("cks");
		Integer result = 0;
		if (t.getMap().get("cks") == null) {
			return result;
		}

		for (int i = 0; i < cks.length; i++) {
			BaseClassLinkAttribute baseClassLinkAttribute = new BaseClassLinkAttribute();
			baseClassLinkAttribute.setCls_id(t.getCls_id());
			baseClassLinkAttribute.setAttr_id(Integer.valueOf(cks[i]));
			BaseClassLinkAttribute baseClassLinkAttributeAnother = this.baseClassLinkAttributeDao
					.selectEntity(baseClassLinkAttribute);
			if (baseClassLinkAttributeAnother == null) {
				this.baseClassLinkAttributeDao.insertEntity(baseClassLinkAttribute);
				result++;
			}
		}
		return result;
	}
}
