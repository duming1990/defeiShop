package com.ebiz.webapp.web.struts.manager.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.RwYhqRule;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseAction;

/**
 * @author Wu,Yang
 * @version 2011-04-20
 */
public abstract class BaseAdminAction extends BaseAction {

	public String getFullNameByPindex(Integer p_index) {
		String full_name = "";
		BaseProvince entity = new BaseProvince();
		entity.setIs_del(0);
		entity.setP_index(p_index.longValue());
		entity = super.getFacade().getBaseProvinceService().getBaseProvince(entity);
		if (null != entity) {
			full_name = entity.getFull_name();
		}
		return full_name;
	}

	public Integer getPublicRwRuleCount(String amount) {
		RwYhqRule rwYhqRule = new RwYhqRule();
		rwYhqRule.setAmount(Integer.valueOf(amount));
		rwYhqRule.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		Integer count = this.getFacade().getRwYhqRuleService().getRwYhqRuleCount(rwYhqRule);
		return count;
	}

	/**
	 * @author libaiqiang
	 * @version 2019-3-18
	 * @desc 获取导航
	 */
	public Map<String, List<BaseLink>> common(Integer type) throws Exception {
		BaseLink b = new BaseLink();
		Map<String, List<BaseLink>> map = new HashMap<String, List<BaseLink>>();
		// 导航栏
		b.setLink_type(type);
		List<BaseLink> baseLinkList = this.getFacade().getBaseLinkService().getBaseLinkList(b);
		// 底部
		if (null == baseLinkList) {
			return null;
		}
		map.put("baseLinkList", baseLinkList);
		return map;
	}

}