package com.ebiz.webapp.web.struts.manager.customer;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;
import com.ebiz.webapp.web.util.ZipUtils;

public class VillageCommInfoAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		int village_id = super.getUserInfo(ui.getId()).getOwn_village_id();
		VillageDynamic entity = new VillageDynamic();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		entity.setVillage_id(village_id);
		entity.setType(Keys.DynamicType.dynamic_type_2.getIndex());
		entity.setIs_del(0);
		Integer recordCount = getFacade().getVillageDynamicService().getVillageDynamicCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<VillageDynamic> entityList = super.getFacade().getVillageDynamicService()
				.getVillageDynamicPaginatedList(entity);
		for (VillageDynamic temp : entityList) {
			CommInfo commInfo = new CommInfo();
			commInfo.setComm_type(Keys.CommType.COMM_TYPE_7.getIndex());
			commInfo.setOwn_entp_id(village_id);
			commInfo.setIs_del(0);
			commInfo.setIs_sell(1);
			commInfo.setId(temp.getComm_id());
			commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
			if (commInfo != null) {
				temp.getMap().put("commInfo", commInfo);
			}
			// 套餐管理
			CommTczhPrice param_ctp = new CommTczhPrice();
			param_ctp.setComm_id(temp.getComm_id().toString());
			param_ctp.getMap().put("order_by_inventory_asc", "true");
			List<CommTczhPrice> CommTczhPriceList = super.getFacade().getCommTczhPriceService()
					.getCommTczhPriceList(param_ctp);
			if ((null != CommTczhPriceList) && (CommTczhPriceList.size() > 0)) {
				temp.getMap().put("count_tczh_price_inventory", CommTczhPriceList.get(0).getInventory());
			}
		}
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (GenericValidator.isLong(id)) {
			CommInfo entity = new CommInfo();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getCommInfoService().getCommInfo(entity);

			UserInfo userInfo = super.getUserInfo(entity.getAdd_user_id());
			if (null != userInfo) {
				request.setAttribute("userInfo", userInfo);
			}

			super.copyProperties(form, entity);
			dynaBean.set("is_freeship", 0);
			// 动态信息
			VillageDynamic vDynamic = new VillageDynamic();
			vDynamic.setType(Keys.DynamicType.dynamic_type_2.getIndex());
			vDynamic.setComm_id(entity.getId());
			vDynamic = super.getFacade().getVillageDynamicService().getVillageDynamic(vDynamic);
			if (vDynamic != null) {
				request.setAttribute("village_dynamic", vDynamic);
			}

			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward downloadQrcode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String comm_name_like = (String) dynaBean.get("comm_name_like");

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		int village_id = super.getUserInfo(ui.getId()).getOwn_village_id();
		VillageDynamic entity = new VillageDynamic();
		super.copyProperties(entity, form);

		entity.getMap().put("comm_name_like", comm_name_like);
		entity.setVillage_id(village_id);
		entity.setType(Keys.DynamicType.dynamic_type_2.getIndex());
		entity.setIs_del(0);

		List<VillageDynamic> entityList = super.getFacade().getVillageDynamicService()
				.getVillageDynamicPaginatedList(entity);
		String realPath = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
		String fname = EncryptUtilsV2.encodingFileName("二维码导出_日期" + sdFormat_ymd.format(new Date()) + ".zip");
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=" + fname);

		if (null != entityList && entityList.size() > 0) {
			ZipOutputStream zipout = new ZipOutputStream(response.getOutputStream());
			File[] files = new File[entityList.size()];
			int i = 0;
			for (VillageDynamic temp : entityList) {
				if (null != temp.getQrcode_image_path()) {
					File savePath = new File(realPath + temp.getQrcode_image_path());
					if (savePath.exists())
						files[i] = savePath;
				}
				i++;
			}
			ZipUtils.zipFile(files, "", zipout);
			zipout.flush();
			zipout.close();
			return null;
		} else {
			return null;
		}

	}
}
