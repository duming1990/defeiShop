package com.ebiz.webapp.web.struts.manager.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class ServiceCenterTopAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		setNaviStringToRequestScope(request);

		String sereach_st_date = (String) dynaBean.get("sereach_st_date");
		String sereach_en_date = (String) dynaBean.get("sereach_en_date");

		String right_join = (String) dynaBean.get("right_join");
		request.setAttribute("sum_count_xianshi", "0");
		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();

		if (StringUtils.isNotBlank(right_join)) {
			serviceCenterInfo.getMap().put("right_join", true);
			serviceCenterInfo.getMap().put("is_del", 0);
			serviceCenterInfo.getMap().put("effect_state", 1);
			request.setAttribute("sum_count_xianshi", "1");
		} else {
			serviceCenterInfo.getMap().put("left_join", true);
		}
		// if (StringUtils.isNotBlank(sereach_servecenter_date)) {
		// // 选择时间年-月
		// sereach_servecenter_date = sereach_servecenter_date + "-2";
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-hh");
		// Date date = sdf.parse(sereach_servecenter_date);
		//
		// serviceCenterInfo.getMap().put("sereach_servecenter_st_date", DateTools.getFirstDayThisMonth(date));
		// serviceCenterInfo.getMap().put("sereach_servecenter_en_date", DateTools.getLastDayThisMonth(date));
		// }

		serviceCenterInfo.getMap().put("sereach_servecenter_st_date", sereach_st_date);
		serviceCenterInfo.getMap().put("sereach_servecenter_en_date", sereach_en_date);
		logger.info("====合伙人排名====");
		List<ServiceCenterInfo> list = getFacade().getServiceCenterInfoService().getServiceCenterInfoPaiMingList(
				serviceCenterInfo);
		if (list != null && list.size() > 0) {
			for (ServiceCenterInfo temp : list) {
				// 1、循环出所查排名的合伙人
				ServiceCenterInfo serviceCenter1 = new ServiceCenterInfo();

				if (StringUtils.isNotBlank(right_join)) {
					// right join以合伙人为主
					serviceCenter1.setServicecenter_name(temp.getMap().get("servicecenter_name").toString());

				} else {
					// left join 以商家为主
					serviceCenter1.setP_index(Integer.valueOf(temp.getMap().get("p_index").toString()));
				}
				serviceCenter1.setIs_del(0);
				serviceCenter1.setEffect_state(1);
				List<ServiceCenterInfo> serviceCenterInfos = getFacade().getServiceCenterInfoService()
						.getServiceCenterInfoList(serviceCenter1);

				if (serviceCenterInfos != null && serviceCenterInfos.size() > 0) {
					// 2、查询合伙人总商家数
					EntpInfo entpInfo = new EntpInfo();
					entpInfo.setIs_del(0);
					String p_index_ = serviceCenterInfos.get(0).getP_index().toString();
					if (serviceCenterInfos.get(0).getServicecenter_level() != null
							&& serviceCenterInfos.get(0).getServicecenter_level().intValue() == 3
							&& serviceCenterInfos.get(0).getP_index() != null) {
						entpInfo.getMap().put("p_index_like", p_index_);
					} else if (serviceCenterInfos.get(0).getServicecenter_level() != null
							&& serviceCenterInfos.get(0).getServicecenter_level().intValue() == 2
							&& serviceCenterInfos.get(0).getP_index() != null) {
						entpInfo.getMap().put("p_index_like", p_index_.substring(0, 4));
					} else {
						entpInfo.getMap().put("p_index_like", p_index_.substring(0, 2));
					}

					int count = getFacade().getEntpInfoService().getEntpInfoCount(entpInfo);
					temp.getMap().put("entp_sum_count", count);
				}
			}
		}

		request.setAttribute("PaiMing_list", list);

		return mapping.findForward("list");
	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String service_name_like = (String) dynaBean.get("service_name_like");
		String sereach_servecenter_st_date = (String) dynaBean.get("sereach_servecenter_st_date");
		String sereach_servecenter_en_date = (String) dynaBean.get("sereach_servecenter_en_date");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (StringUtils.isBlank(service_name_like)) {
			String msg = "请填写合伙人名称!";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		serviceCenterInfo.getMap().put("servicecenter_name_like", service_name_like);
		serviceCenterInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		serviceCenterInfo.setEffect_state(new Integer(1));
		List<ServiceCenterInfo> serviceCenterInfoList = super.getFacade().getServiceCenterInfoService()
				.getServiceCenterInfoList(serviceCenterInfo);

		if (serviceCenterInfoList != null && serviceCenterInfoList.size() > 0) {
			for (ServiceCenterInfo temp : serviceCenterInfoList) {
				// 所属区域
				// BaseProvince baseProvince = new BaseProvince();
				// baseProvince.setP_index(temp.getP_index());
				// List<BaseProvince> baseProvincelist = getFacade().getBaseProvinceService().getBaseProvinceList(
				// baseProvince);
				// temp.getMap().put("server_name", baseProvincelist.get(0).getFull_name());

				// 合伙人销售额
				ServiceCenterInfo serviceSaleMoney = new ServiceCenterInfo();
				serviceSaleMoney.getMap().put("left_join", true);
				serviceSaleMoney.getMap().put("id", temp.getId());
				if (StringUtils.isNotBlank(sereach_servecenter_st_date)
						&& StringUtils.isNotBlank(sereach_servecenter_en_date)) {
					// 选择时间年-月
					Date st_date = sdf.parse(sereach_servecenter_st_date);
					Date en_date = sdf.parse(sereach_servecenter_en_date);

					request.setAttribute("sereach_servecenter_st_date", st_date);
					request.setAttribute("sereach_servecenter_en_date", en_date);

					serviceSaleMoney.getMap().put("sereach_servecenter_st_date", st_date);
					serviceSaleMoney.getMap().put("sereach_servecenter_en_date", en_date);
				}

				logger.info("====合伙人排名====");
				List<ServiceCenterInfo> list = getFacade().getServiceCenterInfoService()
						.getServiceCenterInfoPaiMingList(serviceSaleMoney);
				if (null != list && list.size() > 0) {
					temp.getMap().put("serviceSaleMoney", list.get(0).getMap().get("entp_sale_money"));
				} else {
					temp.getMap().put("serviceSaleMoney", 0);
				}

				String p_index_like = "";
				if (temp.getServicecenter_level() != null && temp.getServicecenter_level().intValue() == 1) {
					p_index_like = temp.getP_index().toString();
					p_index_like = p_index_like.substring(0, 2);
				}
				if (temp.getServicecenter_level() != null && temp.getServicecenter_level().intValue() == 2) {
					p_index_like = temp.getP_index().toString();
					p_index_like = p_index_like.substring(0, 4);
				}
				if (temp.getServicecenter_level() != null && temp.getServicecenter_level().intValue() == 3) {
					p_index_like = temp.getP_index().toString();
				}
				temp.getMap().put("p_index_like", p_index_like);

				// 认证商家 is_entp
				UserInfo userInfo = new UserInfo();
				userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				userInfo.setIs_entp(new Integer(1));
				userInfo.getMap().put("right_join_entp_info", "true");
				userInfo.getMap().put("entp_p_index_like", p_index_like);

				userInfo.getMap().put("audit_date_st", sereach_servecenter_st_date);
				userInfo.getMap().put("audit_date_en", sereach_servecenter_en_date);
				Integer userInfoCountEntp = getFacade().getUserInfoService().getUserInfoCount(userInfo);
				temp.getMap().put("userInfoCountEntp", userInfoCountEntp);

				UserInfo ui = super.getUserInfo(Integer.valueOf(temp.getAdd_user_id()));
				if (ui != null) {

					// UserInfo entity = new UserInfo();
					// entity.getMap().put("user_par_id", ui.getId());
					// entity.getMap().put("user_par_levle_le", 3);
					// entity.setIs_del(0);
					// entity.setIs_entp(new Integer(1));
					// entity.getMap().put("left_join_user_relation_par", "true");
					// entity.getMap().put("st_add_date", sereach_servecenter_st_date);
					// entity.getMap().put("en_add_date", sereach_servecenter_en_date);
					//
					// List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(entity);
					// if (userInfoList != null && userInfoList.size() > 0) {
					// temp.getMap().put("userInfoCountEntp", userInfoList.size());
					// } else {
					// temp.getMap().put("userInfoCountEntp", 0);
					// }

					UserInfo uInfo = new UserInfo();
					uInfo.getMap().put("user_par_id", ui.getId());
					uInfo.getMap().put("user_par_levle_le", 3);
					uInfo.setIs_del(0);
					uInfo.setIs_fuwu(new Integer(1));
					uInfo.getMap().put("left_join_user_relation_par", "true");
					uInfo.getMap().put("st_add_date", sereach_servecenter_st_date);
					uInfo.getMap().put("en_add_date", sereach_servecenter_en_date);
					List<UserInfo> uInfoList = getFacade().getUserInfoService().getUserInfoList(uInfo);
					if (uInfoList != null && uInfoList.size() > 0) {
						temp.getMap().put("userInfoCountFuwu", uInfoList.size());
					} else {
						temp.getMap().put("userInfoCountFuwu", 0);
					}

					UserInfo userInfo1 = new UserInfo();
					userInfo1.getMap().put("user_par_id", ui.getId());
					userInfo1.getMap().put("user_par_levle_le", 3);
					userInfo1.setIs_del(0);
					userInfo1.getMap().put("left_join_user_relation_par", "true");
					userInfo1.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
					userInfo1.getMap().put("st_add_date", sereach_servecenter_st_date);
					userInfo1.getMap().put("en_add_date", sereach_servecenter_en_date);
					logger.info("=======会员数量=========");
					List<UserInfo> uList = getFacade().getUserInfoService().getUserInfoList(userInfo1);
					if (uList != null && uList.size() > 0) {
						temp.getMap().put("userInfoCountVip", uList.size());
					} else {
						temp.getMap().put("userInfoCountVip", 0);
					}

				}

				// 合伙人
				// UserInfo uInfo = new UserInfo();
				// uInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				// uInfo.setIs_fuwu(new Integer(1));
				// uInfo.getMap().put("p_index_like", p_index_like);
				//
				// uInfo.getMap().put("st_add_date", sereach_servecenter_st_date);
				// uInfo.getMap().put("en_add_date", sereach_servecenter_en_date);
				// Integer userInfoCountFuwu = getFacade().getUserInfoService().getUserInfoCount(uInfo);
				// temp.getMap().put("userInfoCountFuwu", userInfoCountFuwu);
				// uInfo.getMap().put("today_date", new Date());
				// Integer userInfoCountFuwuToday = getFacade().getUserInfoService().getUserInfoCount(uInfo);
				// temp.getMap().put("userInfoCountFuwuToday", userInfoCountFuwuToday);
				// 会员

				// UserInfo userInfo2 = new UserInfo();
				// userInfo2.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				// userInfo2.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
				// userInfo2.getMap().put("p_index_like", p_index_like);
				// userInfo2.getMap().put("st_add_date", sereach_servecenter_st_date);
				// userInfo2.getMap().put("en_add_date", sereach_servecenter_en_date);
				// logger.info("========会员=======");
				// Integer userInfoCountVip = getFacade().getUserInfoService().getUserInfoCount(userInfo2);
				// temp.getMap().put("userInfoCountVip", userInfoCountVip);
				// userInfo.getMap().put("today_date", new Date());
				// Integer userInfoCountVipToday = getFacade().getUserInfoService().getUserInfoCount(userInfo);
				// request.setAttribute("userInfoCountVipToday", userInfoCountVipToday);
			}
			request.setAttribute("serviceCenterInfoList", serviceCenterInfoList);
		}
		return new ActionForward("/../manager/admin/ServiceCenterTop/search_list.jsp");
	}

	public ActionForward vipList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String user_par_id = (String) dynaBean.get("user_par_id");
		String is_vip = (String) dynaBean.get("is_vip");
		String p_index_like = (String) dynaBean.get("p_index_like");

		String user_name_like = (String) dynaBean.get("user_name_like");
		String sereach_servecenter_st_date = (String) dynaBean.get("sereach_servecenter_st_date");
		String sereach_servecenter_en_date = (String) dynaBean.get("sereach_servecenter_en_date");
		if (StringUtils.isBlank(user_par_id) || StringUtils.isBlank(user_par_id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		// 认证商家 is_entp
		UserInfo userInfo = new UserInfo();

		if (Integer.valueOf(is_vip) == 0) {
			// 商家
			userInfo.setIs_entp(new Integer(1));
			request.setAttribute("is_entp", true);
			userInfo.getMap().put("audit_date_st", sereach_servecenter_st_date);
			userInfo.getMap().put("audit_date_en", sereach_servecenter_en_date);
			userInfo.getMap().put("right_join_entp_info", "true");
			userInfo.getMap().put("entp_p_index_like", p_index_like);
		}
		if (Integer.valueOf(is_vip) == 1) {
			// 会员
			userInfo.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
			request.setAttribute("is_vip", true);
			userInfo.getMap().put("st_add_date", sereach_servecenter_st_date);
			userInfo.getMap().put("en_add_date", sereach_servecenter_en_date);
			userInfo.getMap().put("user_par_id", user_par_id);
			userInfo.getMap().put("user_par_levle_le", 3);
			userInfo.getMap().put("left_join_user_relation_par", "true");
		}
		if (Integer.valueOf(is_vip) == 3) {
			// 合伙人
			userInfo.setIs_fuwu(new Integer(1));
			request.setAttribute("is_fuwu", true);
			userInfo.getMap().put("st_add_date", sereach_servecenter_st_date);
			userInfo.getMap().put("en_add_date", sereach_servecenter_en_date);
			userInfo.getMap().put("user_par_id", user_par_id);
			userInfo.getMap().put("user_par_levle_le", 3);
			userInfo.getMap().put("left_join_user_relation_par", "true");
		}
		userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());

		logger.info("=======会员数量=========");
		List<UserInfo> uList = getFacade().getUserInfoService().getUserInfoList(userInfo);

		if (null != uList && uList.size() > 0) {
			for (UserInfo temp : uList) {

				if (temp.getIs_entp() != null && temp.getIs_entp().intValue() == 1) {
					EntpInfo entpInfo = new EntpInfo();
					entpInfo.setId(temp.getOwn_entp_id());
					entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
					if (entpInfo != null) {
						temp.getMap().put("audit_date", entpInfo.getAudit_date());
						temp.getMap().put("entp_name", entpInfo.getEntp_name());
					}
				}

				if (temp.getIs_fuwu() != null && temp.getIs_fuwu().intValue() == 1) {
					ServiceCenterInfo seInfo = new ServiceCenterInfo();
					seInfo.setAdd_user_id(temp.getId());
					seInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(seInfo);
					if (seInfo != null) {
						temp.getMap().put("service_name", seInfo.getServicecenter_name());
					}
				}
			}
			request.setAttribute("entityList", uList);
		}
		return new ActionForward("/../manager/admin/ServiceCenterTop/entp_vip_list.jsp");
	}
}