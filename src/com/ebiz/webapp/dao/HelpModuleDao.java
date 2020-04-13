package com.ebiz.webapp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.HelpModule;

/**
 * @author Wu,Yang
 */
public interface HelpModuleDao extends EntityDao<HelpModule> {
	/**
	 * @author Wu,Yang
	 */
	Integer selectHelpModuleWithLevelNumber(HelpModule t) throws DataAccessException;

	/**
	 * @author Wu,Yang
	 */
	List<HelpModule> selectHelpModuleParentList(HelpModule t) throws DataAccessException;

}
