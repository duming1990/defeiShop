package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ModPopedomDao;
import com.ebiz.webapp.domain.ModPopedom;
import com.ebiz.webapp.service.ModPopedomService;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
@Service
public class ModPopedomServiceImpl implements ModPopedomService {

	@Resource
	private ModPopedomDao modPopedomDao;

	public Integer createModPopedom(ModPopedom t) {
		this.modPopedomDao.deleteEntity(t);

		List<ModPopedom> modPopedomList = t.getModPopedomList();
		if (null != modPopedomList) {
			for (ModPopedom modPopedom : modPopedomList) {
				modPopedom.setUser_id(t.getUser_id());
				modPopedom.setRole_id(t.getRole_id());
				this.modPopedomDao.insertEntity(modPopedom);
			}
		}

		return 1;
	}

	public int modifyModPopedom(ModPopedom t) {
		return this.modPopedomDao.updateEntity(t);
	}

	public int removeModPopedom(ModPopedom t) {
		return this.modPopedomDao.deleteEntity(t);
	}

	public ModPopedom getModPopedom(ModPopedom t) {
		return this.modPopedomDao.selectEntity(t);
	}

	public Integer getModPopedomCount(ModPopedom t) {
		return this.modPopedomDao.selectEntityCount(t);
	}

	public List<ModPopedom> getModPopedomList(ModPopedom t) {
		return this.modPopedomDao.selectEntityList(t);
	}

	public List<ModPopedom> getModPopedomPaginatedList(ModPopedom t) {
		return this.modPopedomDao.selectEntityPaginatedList(t);
	}

}
