package com.ebiz.webapp.web.struts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2010-12-16
 */
public class IndexServiceCenterInfoAction extends BaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String show_type = (String) dynaBean.get("show_type");

		if (StringUtils.isBlank(show_type) && StringUtils.equals(Keys.is_zhonghui, "false")) {
			return this.index(mapping, form, request, response);
		} else {
			return this.view(mapping, form, request, response);
		}
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		super.setPublicInfoOtherList(request);

		BaseProvince baseProvince = new BaseProvince();
		// baseProvince.setPar_index(0);
		baseProvince.setIs_del(0);
		baseProvince.getMap().put("orderByInfo", " CONVERT(p_name USING gbk), ");
		List<BaseProvince> baseProvinceList = getFacade().getBaseProvinceService().getBaseProvinceList(baseProvince);
		List<BaseProvince> bp1List = new ArrayList<BaseProvince>();
		List<BaseProvince> bp2List = new ArrayList<BaseProvince>();
		List<BaseProvince> bp3List = new ArrayList<BaseProvince>();

		for (BaseProvince bp : baseProvinceList) {
			if (bp.getP_level().intValue() == 1) {
				bp1List.add(bp);
			}
			if (bp.getP_level().intValue() == 2) {
				bp2List.add(bp);
			}
			if (bp.getP_level().intValue() == 3) {
				bp3List.add(bp);
			}
		}

		logger.info("==bp3List.size()" + bp3List.size());
		for (BaseProvince bp2 : bp2List) {
			List<BaseProvince> bp3realList = new ArrayList<BaseProvince>();
			for (BaseProvince bp3 : bp3List) {
				if (bp2.getP_index().intValue() == bp3.getPar_index().intValue()) {
					bp3realList.add(bp3);
				}
			}

			bp2.setBp3List(bp3realList);
		}

		List<BaseProvince> bpList = new ArrayList<BaseProvince>();
		for (BaseProvince bp1 : bp1List) {
			List<BaseProvince> bp2realList = new ArrayList<BaseProvince>();
			for (BaseProvince bp2 : bp2List) {
				if (bp1.getP_index().intValue() == bp2.getPar_index().intValue()) {
					bp2realList.add(bp2);
					logger.info("==bp3realList.size()" + bp2.getBp3List().size());
				}
			}
			bp1.setBp2List(bp2realList);
			bpList.add(bp1);
		}

		// ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		// for (BaseProvince p : baseProvinceList) {
		// serviceCenterInfo = new ServiceCenterInfo();
		// serviceCenterInfo.setAudit_state(1);
		// serviceCenterInfo.setEffect_state(1);
		// serviceCenterInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		// serviceCenterInfo.getMap().put("par_index", p.getP_index());
		// serviceCenterInfo.getMap().put("p_level", "2");
		// p.getMap().put(
		// "serviceCenterInfoList",
		// super.getFacade().getServiceCenterInfoService()
		// .getServiceCenterInfoCountByPIndex(serviceCenterInfo));
		// }
		request.setAttribute("bpList", bpList);
		return new ActionForward("/index/IndexServiceCenterInfo/index.jsp");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		super.setPublicInfoOtherList(request);

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");//
		return mapping.findForward("list");

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		super.setPublicInfoOtherList(request);

		DynaBean dynaBean = (DynaBean) form;

		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.setPay_success(1);// 已支付
		entity.setAudit_state(1);// 审核通过
		entity.setEffect_state(1);// 已生效
		List<ServiceCenterInfo> entityList = super.getFacade().getServiceCenterInfoService()
				.getServiceCenterInfoList(entity);
		request.setAttribute("entityList", entityList);

		return mapping.findForward("view");
	}

}