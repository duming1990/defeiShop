package com.ebiz.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseClassDao;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.service.BaseClassService;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
@Service
public class BaseClassServiceImpl implements BaseClassService {

	@Resource
	private BaseClassDao baseClassDao;

	public Integer createBaseClass(BaseClass t) {
		return this.baseClassDao.insertEntity(t);
	}

	public BaseClass getBaseClass(BaseClass t) {
		return this.baseClassDao.selectEntity(t);
	}

	public Integer getBaseClassCount(BaseClass t) {
		return this.baseClassDao.selectEntityCount(t);
	}

	public List<BaseClass> getBaseClassList(BaseClass t) {
		return this.baseClassDao.selectEntityList(t);
	}

	public int modifyBaseClass(BaseClass t) {
		return this.baseClassDao.updateEntity(t);
	}

	public int removeBaseClass(BaseClass t) {
		return this.baseClassDao.deleteEntity(t);
	}

	public List<BaseClass> getBaseClassPaginatedList(BaseClass t) {
		return this.baseClassDao.selectEntityPaginatedList(t);
	}

	public BaseClass procedureUpdateClass(BaseClass t) {
		return this.baseClassDao.procedureUpdateClass(t);
	}

	@Override
	public List<BaseClass> proGetBaseClassParentList(BaseClass t) {
		// 子查父
		List<BaseClass> parentList = new ArrayList<BaseClass>();
		List<BaseClass> proGetBaseClassParentList = this.getParentList(t.getCls_id(), t.getCls_scope(), parentList);
		return proGetBaseClassParentList;
		// return this.baseClassDao.procedureGetBaseClassParentList(t);
	}

	@Override
	public List<BaseClass> proGetBaseClassSonList(BaseClass t) {
		// 父查子
		List<BaseClass> sonList = new ArrayList<BaseClass>();
		List<BaseClass> proGetBaseClassSonList = this.getSonList(true, t.getCls_id(), t.getCls_scope(), sonList);
		return proGetBaseClassSonList;

		// return this.baseClassDao.procedureGetBaseClassSonList(t);
	}

	public List<BaseClass> getParentList(Integer par_id, Integer cls_scope, List<BaseClass> parentList) {

		BaseClass baseClass = new BaseClass();
		baseClass.setCls_id(par_id);
		baseClass.setIs_del(0);
		baseClass.setCls_scope(cls_scope);
		baseClass = this.baseClassDao.selectEntity(baseClass);
		if (null != baseClass) {
			parentList.add(baseClass);
			this.getParentList(baseClass.getPar_id(), baseClass.getCls_scope(), parentList);
		}
		return parentList;
	}

	public List<BaseClass> getSonList(Boolean is_first, Integer par_id, Integer cls_scope, List<BaseClass> sonList) {

		if (is_first) {
			BaseClass temp = new BaseClass();
			temp.setCls_id(par_id);
			temp.setIs_del(0);
			temp.setCls_scope(cls_scope);
			temp = this.baseClassDao.selectEntity(temp);
			if (null != temp) {
				sonList.add(temp);
			}
		}

		BaseClass baseClass = new BaseClass();
		baseClass.setPar_id(par_id);
		baseClass.setIs_del(0);
		baseClass.setCls_scope(cls_scope);
		List<BaseClass> baseClassList = this.baseClassDao.selectEntityList(baseClass);
		if (null != baseClassList && baseClassList.size() > 0) {
			for (BaseClass temp : baseClassList) {
				sonList.add(temp);
				this.getSonList(false, temp.getCls_id(), temp.getCls_scope(), sonList);
			}
		}
		return sonList;
	}
}
