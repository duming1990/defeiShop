package com.ebiz.webapp.web.struts.manager.customer;

import javax.servlet.http.HttpServletRequest;

import com.ebiz.webapp.web.struts.service.BaseWebServiceAction;

public class BaseCustomerAction extends BaseWebServiceAction {
	@Override
	protected void setNaviStringToRequestScope(HttpServletRequest request) {
		// null 此方法在 customer中不需要了。。。
	}

	public void setPublicInfoListWithEntpAndCustomer(HttpServletRequest request) {

	}

}
