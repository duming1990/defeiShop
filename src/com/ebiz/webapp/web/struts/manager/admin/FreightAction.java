package com.ebiz.webapp.web.struts.manager.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.FreightDetail;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author chen,zhen
 * @version 2014-05-23 物流模版管理
 */
public class FreightAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 设定标题
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String fre_title_like = (String) dynaBean.get("fre_title_like");
		String st_update_time = (String) dynaBean.get("st_update_time");
		String en_update_time = (String) dynaBean.get("en_update_time");
		String own_entp_id = (String) dynaBean.get("own_entp_id");

		Freight entity = new Freight();
		super.copyProperties(entity, form);
		entity.getMap().put("fre_title_like", fre_title_like);

		entity.getMap().put("st_update_time", st_update_time);

		entity.getMap().put("en_update_time", en_update_time);
		if (StringUtils.isNotBlank(own_entp_id))
			entity.setEntp_id(Integer.valueOf(own_entp_id));

		// 查询没有被删除的数据
		entity.setIs_del(0);

		Integer recordCount = getFacade().getFreightService().getFreightCount(entity);

		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<Freight> entityList = super.getFacade().getFreightService().getFreightPaginatedList(entity);

		for (Freight f : entityList) {
			FreightDetail fd = new FreightDetail();
			fd.setFre_id(f.getId());
			List<FreightDetail> fdlist = super.getFacade().getFreightDetailService().getFreightDetailList(fd);
			f.setFreightDetailList(fdlist);

			if (null != f.getEntp_id())
				f.getMap().put("entpInfo", super.getEntpInfo(f.getEntp_id()));

		}

		request.setAttribute("entityList", entityList);

		return mapping.findForward("list");

	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		DynaBean dynaBean = (DynaBean) form;
		setNaviStringToRequestScope(request);
		// 得到用户信息
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		dynaBean.set("own_entp_id", userInfo.getOwn_entp_id());
		dynaBean.set("province", Keys.P_INDEX_INIT);

		String par_index = (String) dynaBean.get("par_index");
		// 全部取出来
		par_index = "0";

		request.setAttribute("areaList", getAreaList(par_index));

		return mapping.findForward("input");

	}

	/**
	 * 得到区域框数据
	 * 
	 * @return
	 */
	public List getAreaList(String par_index) {

		BaseProvince bplf = new BaseProvince();
		bplf.setPar_index(new Long(par_index));
		List<BaseProvince> bplfList = super.getFacade().getBaseProvinceService().getBaseProvinceList(bplf);
		if (null == bplfList || 0 == bplfList.size()) {

			return new ArrayList<BaseProvince>();
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (BaseProvince t : bplfList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("p_index", t.getP_index().toString());
			map.put("s_name", t.getS_name());
			String p_index = t.getP_index().toString();
			// 将省份下面的城市查询出来
			BaseProvince prov = new BaseProvince();
			prov.setPar_index(t.getP_index());
			List<BaseProvince> cityList = super.getFacade().getBaseProvinceService().getBaseProvinceList(prov);
			map.put(p_index + "", cityList);

			list.add(map);
		}

		return list;

	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 获得用户信息
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);

		List<FreightDetail> fdlist = new ArrayList();

		DynaBean dynaBean = (DynaBean) form;

		// 模版id
		String freight_id = (String) dynaBean.get("freight_id");
		// 不为空,则删除该id的所有的数据,表明是 修改
		if (!StringUtils.isEmpty(freight_id)) {

			FreightDetail df = new FreightDetail();
			df.setFre_id(Integer.valueOf(freight_id));
			// 删除详细信息
			// super.getFacade().getFreightDetailService().removeFreightDetail(df);

		}

		// 模版名称
		String pd_name = (String) dynaBean.get("pd_name");
		// 商品地址

		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String p_index = "";
		if (!StringUtils.isBlank(country)) {

			p_index = country;
		} else {
			if (!StringUtils.isBlank(city)) {

				p_index = city;
			} else {
				if (!StringUtils.isBlank(province)) {

					p_index = province;
				} else {
					p_index = "";
				}
			}
		}

		// p_index = (String) dynaBean.get("p_index");
		// 发货时间
		String delivery_time = (String) dynaBean.get("delivery_time");

		// 是否包邮
		String is_freeShipping = (String) dynaBean.get("is_freeShipping");
		// 是否开启满包邮
		String is_open_freeShipping_money = (String) dynaBean.get("is_open_freeShipping_money");
		// 开启满包邮的价格
		String open_money_freeShipping = (String) dynaBean.get("open_money_freeShipping");
		// 计价方式
		String valuation = (String) dynaBean.get("valuation");
		// 区域限售
		String area_limit = (String) dynaBean.get("area_limit");
		// 快递方式
		String delivery_way_1 = (String) dynaBean.get("delivery_way_1");
		// 快递默认费用
		String dw1_first_weight = (String) dynaBean.get("dw1_first_weight");
		dw1_first_weight = filterNull(dw1_first_weight, "0");

		// 快递首费
		String dw1_first_price = (String) dynaBean.get("dw1_first_price");
		dw1_first_price = filterNull(dw1_first_price, "0");
		// 快递续件
		String dw1_sed_weight = (String) dynaBean.get("dw1_sed_weight");
		dw1_sed_weight = filterNull(dw1_sed_weight, "0");
		// 快递续费
		String dw1_sed_price = (String) dynaBean.get("dw1_sed_price");
		dw1_sed_price = filterNull(dw1_sed_price, "0");
		// 快递方式
		String delivery_way_2 = (String) dynaBean.get("delivery_way_2");
		// 快递运送地址index
		String[] area_pindex_1 = request.getParameterValues("area_pindex_1");
		// 快递运送地址 名称
		String[] area_name_1 = request.getParameterValues("area_name_1");
		// 快递运送地址首件
		String[] first_weight_1 = request.getParameterValues("first_weight_1");
		// 快递运送地址首费
		String[] first_price_1 = request.getParameterValues("first_price_1");
		// 快递运送地址续件
		String[] sed_weight_1 = request.getParameterValues("sed_weight_1");
		// 快递运送地址续费
		String[] sed_price_1 = request.getParameterValues("sed_price_1");

		// 快递方式
		String delivery_way_3 = (String) dynaBean.get("delivery_way_3");
		// EMS默认费用
		String dw2_first_weight = (String) dynaBean.get("dw2_first_weight");
		dw2_first_weight = filterNull(dw2_first_weight, "0");
		// EMS首费
		String dw2_first_price = (String) dynaBean.get("dw2_first_price");
		dw2_first_price = filterNull(dw2_first_price, "0");
		// EMS续件
		String dw2_sed_weight = (String) dynaBean.get("dw2_sed_weight");
		dw2_sed_weight = filterNull(dw2_sed_weight, "0");
		// EMS续费
		String dw2_sed_price = (String) dynaBean.get("dw2_sed_price");
		dw2_sed_price = filterNull(dw2_sed_price, "0");

		// EMS运送地址index
		String[] area_pindex_2 = request.getParameterValues("area_pindex_2");
		// EMS运送地址 名称
		String[] area_name_2 = request.getParameterValues("area_name_2");
		// EMS运送地址首件
		String[] first_weight_2 = request.getParameterValues("first_weight_2");
		// EMS运送地址首费
		String[] first_price_2 = request.getParameterValues("first_price_2");
		// EMS运送地址续件
		String[] sed_weight_2 = request.getParameterValues("sed_weight_2");
		// EMS运送地址续费
		String[] sed_price_2 = request.getParameterValues("sed_price_2");

		// 平邮默认费用
		String dw3_first_weight = (String) dynaBean.get("dw3_first_weight");
		dw3_first_weight = filterNull(dw3_first_weight, "0");
		// 平邮首费
		String dw3_first_price = (String) dynaBean.get("dw3_first_price");
		dw3_first_price = filterNull(dw3_first_price, "0");
		// 平邮续件
		String dw3_sed_weight = (String) dynaBean.get("dw3_sed_weight");
		dw3_sed_weight = filterNull(dw3_sed_weight, "0");
		// 平邮续费
		String dw3_sed_price = (String) dynaBean.get("dw3_sed_price");
		dw3_sed_price = filterNull(dw3_sed_price, "0");

		// 平邮运送地址index
		String[] area_pindex_3 = request.getParameterValues("area_pindex_3");
		// 平邮运送地址 名称
		String[] area_name_3 = request.getParameterValues("area_name_3");
		// 平邮运送地址首件
		String[] first_weight_3 = request.getParameterValues("first_weight_3");
		// 平邮运送地址首费
		String[] first_price_3 = request.getParameterValues("first_price_3");
		// 平邮运送地址续件
		String[] sed_weight_3 = request.getParameterValues("sed_weight_3");
		// 平邮运送地址续费
		String[] sed_price_3 = request.getParameterValues("sed_price_3");

		// 先存储Freight
		Freight f = new Freight();
		// 添加模版名称

		f.setFre_title(pd_name);
		f.setP_index(Integer.valueOf(p_index));
		f.setDelivery_time(Integer.valueOf(delivery_time));
		f.setIs_freeshipping(Integer.parseInt(is_freeShipping));

		if (StringUtils.isNotBlank(is_open_freeShipping_money)) {
			f.setIs_open_freeShipping_money(Integer.parseInt(is_open_freeShipping_money));
		}
		if (StringUtils.isNotBlank(open_money_freeShipping)) {
			f.setOpen_money_freeShipping(Integer.parseInt(open_money_freeShipping));
		}

		f.setValuation(Integer.parseInt(valuation));
		f.setArea_limit(Integer.parseInt(area_limit));

		if (StringUtils.isNotBlank(own_entp_id))
			f.setEntp_id(Integer.valueOf(own_entp_id));

		// 如果不为空,则是更新操作.否则是新增操作
		if (!StringUtils.isEmpty(freight_id)) {

			f.setId(Integer.valueOf(freight_id));
			// super.getFacade().getFreightService().modifyFreight(f);
		} else {
			// super.getFacade().getFreightService().createFreight(f);
		}

		// 快递
		if (!StringUtils.isBlank(delivery_way_1)) {
			// 先保存默认的
			FreightDetail defaultfd = new FreightDetail();
			defaultfd.setFre_id(f.getId());
			defaultfd.setFirst_weight((new BigDecimal(dw1_first_weight)));
			defaultfd.setFirst_price(new BigDecimal(dw1_first_price));
			defaultfd.setSed_weight(new BigDecimal(dw1_sed_weight));
			defaultfd.setSed_price(new BigDecimal(dw1_sed_price));
			defaultfd.setDelivery_way(new Integer(1));
			fdlist.add(defaultfd);

			// super.getFacade().getFreightDetailService().createFreightDetail(defaultfd);
			// 在保存选定区域的数据
			for (int i = 0; i < area_pindex_1.length; i++) {
				// 存储默认的时候,第一个默认是"",因此需要去除
				if (!area_pindex_1[i].equals("")) {
					String pindex = area_pindex_1[i];
					String pname = area_name_1[i];
					String first_weight = first_weight_1[i];
					String first_price = first_price_1[i];
					String sed_weight = sed_weight_1[i];
					String sed_price = sed_price_1[i];
					FreightDetail areafd = new FreightDetail();
					areafd.setFre_id(f.getId());
					areafd.setArea_pindex(pindex);
					areafd.setArea_name(pname);
					areafd.setFirst_weight((new BigDecimal(first_weight)));
					areafd.setFirst_price(new BigDecimal(first_price));
					areafd.setSed_weight(new BigDecimal(sed_weight));
					areafd.setSed_price(new BigDecimal(sed_price));
					areafd.setDelivery_way(new Integer(1));

					fdlist.add(areafd);
					// super.getFacade().getFreightDetailService().createFreightDetail(areafd);

				}

			}

		}

		// EMS
		if (!StringUtils.isBlank(delivery_way_2)) {
			// 先保存默认的
			FreightDetail defaultfd = new FreightDetail();
			defaultfd.setFre_id(f.getId());
			defaultfd.setFirst_weight((new BigDecimal(dw2_first_weight)));
			defaultfd.setFirst_price(new BigDecimal(dw2_first_price));
			defaultfd.setSed_weight(new BigDecimal(dw2_sed_weight));
			defaultfd.setSed_price(new BigDecimal(dw2_sed_price));
			defaultfd.setDelivery_way(new Integer(2));

			fdlist.add(defaultfd);
			// super.getFacade().getFreightDetailService().createFreightDetail(defaultfd);
			// 在保存选定区域的数据
			for (int i = 0; i < area_pindex_2.length; i++) {
				// 存储默认的时候,第一个默认是"",因此需要去除
				if (!area_pindex_2[i].equals("")) {
					String pindex = area_pindex_2[i];
					String pname = area_name_2[i];
					String first_weight = first_weight_2[i];
					String first_price = first_price_2[i];
					String sed_weight = sed_weight_2[i];
					String sed_price = sed_price_2[i];
					FreightDetail areafd = new FreightDetail();
					areafd.setFre_id(f.getId());
					areafd.setArea_pindex(pindex);
					areafd.setArea_name(pname);
					areafd.setFirst_weight((new BigDecimal(first_weight)));
					areafd.setFirst_price(new BigDecimal(first_price));
					areafd.setSed_weight(new BigDecimal(sed_weight));
					areafd.setSed_price(new BigDecimal(sed_price));
					areafd.setDelivery_way(new Integer(2));
					fdlist.add(areafd);

					// super.getFacade().getFreightDetailService().createFreightDetail(areafd);

				}

			}

		}

		// 平邮
		if (!StringUtils.isBlank(delivery_way_3)) {
			// 先保存默认的
			FreightDetail defaultfd = new FreightDetail();
			defaultfd.setFre_id(f.getId());
			defaultfd.setFirst_weight((new BigDecimal(dw3_first_weight)));
			defaultfd.setFirst_price(new BigDecimal(dw3_first_price));
			defaultfd.setSed_weight(new BigDecimal(dw3_sed_weight));
			defaultfd.setSed_price(new BigDecimal(dw3_sed_price));
			defaultfd.setDelivery_way(new Integer(3));
			fdlist.add(defaultfd);
			// super.getFacade().getFreightDetailService().createFreightDetail(defaultfd);
			// 在保存选定区域的数据
			for (int i = 0; i < area_pindex_3.length; i++) {
				// 存储默认的时候,第一个默认是"",因此需要去除
				if (!area_pindex_3[i].equals("")) {
					String pindex = area_pindex_3[i];
					String pname = area_name_3[i];
					String first_weight = first_weight_3[i];
					String first_price = first_price_3[i];
					String sed_weight = sed_weight_3[i];
					String sed_price = sed_price_3[i];
					FreightDetail areafd = new FreightDetail();
					areafd.setFre_id(f.getId());
					areafd.setArea_pindex(pindex);
					areafd.setArea_name(pname);
					areafd.setFirst_weight((new BigDecimal(first_weight)));
					areafd.setFirst_price(new BigDecimal(first_price));
					areafd.setSed_weight(new BigDecimal(sed_weight));
					areafd.setSed_price(new BigDecimal(sed_price));
					areafd.setDelivery_way(new Integer(3));
					fdlist.add(areafd);
					// super.getFacade().getFreightDetailService().createFreightDetail(areafd);

				}

			}

		}

		f.setFreightDetailList(fdlist);
		super.getFacade().getFreightService().modifyFreight(f);

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;

	}

	public String filterNull(String str, String defaultvalue) {
		String v = "";
		if (str == null) {
			v = defaultvalue;
		} else {
			if (str.trim().equals("") || str.trim().equalsIgnoreCase("null")) {
				v = defaultvalue;
			} else {
				v = str;

			}

		}
		return v;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		/**
		 * 删除操作,分为2部分, 1:单行删除,直接删除 2:批量删除,由于前台控制,pks中肯定有数据
		 */
		if (StringUtils.isBlank(id) && null == pks) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		// 得到user信息
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		// 封装参数
		Freight entity = new Freight();
		Date date = new Date();
		entity.setIs_del(1);
		entity.setDel_date(date);
		entity.setDel_user_id(ui.getId());

		// 如果是单个删除
		if (StringUtils.isNotBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(Integer.valueOf(id));
			super.getFacade().getFreightService().modifyFreight(entity);
			// 发送信息
			saveMessage(request, "entity.deleted");
		} else if (ArrayUtils.isNotEmpty(pks)) {
			// 批量删除
			String[] ids = new String[pks.length];
			ids = pks;
			for (String pk : ids) {

				entity.getMap().put("pks", ids);
				entity.setId(Integer.valueOf(pk));
				super.getFacade().getFreightService().modifyFreight(entity);
			}
			saveMessage(request, "entity.deleted");
		}

		saveMessage(request, "entity.deleted");
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);

		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		request.setAttribute("freight_id", id);
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		if (GenericValidator.isLong(id)) {
			Freight entity = new Freight();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getFreightService().getFreight(entity);

			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			if (null != entity.getEntp_id()) {
				EntpInfo entpInfo = super.getEntpInfo(entity.getEntp_id());
				if (null != entpInfo)
					dynaBean.set("entp_name", entpInfo.getEntp_name());
				dynaBean.set("entp_id", entity.getEntp_id());
			}

			// 添加商品标题
			dynaBean.set("pd_name", entity.getFre_title());
			// dynaBean.set("p_index", entity.getP_index());
			dynaBean.set("delivery_time", entity.getDelivery_time());
			dynaBean.set("is_freeShipping", entity.getIs_freeshipping());
			dynaBean.set("is_open_freeShipping_money", entity.getIs_open_freeShipping_money());
			dynaBean.set("open_money_freeShipping", entity.getOpen_money_freeShipping());
			dynaBean.set("valuation", entity.getValuation());
			request.setAttribute("valuation_", entity.getValuation());
			dynaBean.set("area_limit", entity.getArea_limit());
			// 设置下拉框的值
			super.setprovinceAndcityAndcountryToFrom(dynaBean, entity.getP_index());

			// 取得模版明细信息
			// 取得快递的信息
			// area_pindex_isnotnull
			FreightDetail expressfd_default = new FreightDetail();
			expressfd_default.setFre_id(Integer.valueOf(id));
			expressfd_default.setDelivery_way(1);
			expressfd_default.getMap().put("area_pindex_isnull", true);
			expressfd_default.getMap().put("area_name_isnull", true);
			expressfd_default = super.getFacade().getFreightDetailService().getFreightDetail(expressfd_default);
			// 添加 邮递方式,以及默认的值

			if (expressfd_default != null) {

				dynaBean.set("delivery_way_1", "on");
				dynaBean.set("dw1_first_weight", expressfd_default.getFirst_weight());
				dynaBean.set("dw1_first_price", expressfd_default.getFirst_price());
				dynaBean.set("dw1_sed_weight", expressfd_default.getSed_weight());
				dynaBean.set("dw1_sed_price", expressfd_default.getSed_price());

			}

			// 获得排除 默认方式的邮递明细
			FreightDetail expressfd = new FreightDetail();
			expressfd.setFre_id(Integer.valueOf(id));
			expressfd.setDelivery_way(1);
			expressfd.getMap().put("area_pindex_isnotnull", true);
			expressfd.getMap().put("area_name_isnotnull", true);

			List<FreightDetail> expressfdList = super.getFacade().getFreightDetailService()
					.getFreightDetailList(expressfd);

			request.setAttribute("delivery_way_1_list", expressfdList);

			// 取得模版明细信息
			// 取得快递的信息
			// area_pindex_isnotnull
			FreightDetail EMSfd_default = new FreightDetail();
			EMSfd_default.setFre_id(Integer.valueOf(id));
			EMSfd_default.setDelivery_way(2);
			EMSfd_default.getMap().put("area_pindex_isnull", true);
			EMSfd_default.getMap().put("area_name_isnull", true);
			EMSfd_default = super.getFacade().getFreightDetailService().getFreightDetail(EMSfd_default);
			// 添加 邮递方式,以及默认的值
			if (EMSfd_default != null) {
				dynaBean.set("delivery_way_2", "on");
				dynaBean.set("dw2_first_weight", EMSfd_default.getFirst_weight());
				dynaBean.set("dw2_first_price", EMSfd_default.getFirst_price());
				dynaBean.set("dw2_sed_weight", EMSfd_default.getSed_weight());
				dynaBean.set("dw2_sed_price", EMSfd_default.getSed_price());
			}

			// 获得排除 默认方式的邮递明细
			FreightDetail EMSfd = new FreightDetail();
			EMSfd.setFre_id(Integer.valueOf(id));
			EMSfd.setDelivery_way(2);
			EMSfd.getMap().put("area_pindex_isnotnull", true);
			EMSfd.getMap().put("area_name_isnotnull", true);

			List<FreightDetail> EMSfdList = super.getFacade().getFreightDetailService().getFreightDetailList(EMSfd);

			request.setAttribute("delivery_way_2_list", EMSfdList);

			// 取得模版明细信息
			// 取得平邮的信息
			// area_pindex_isnotnull
			FreightDetail surfacemailfd_default = new FreightDetail();
			surfacemailfd_default.setFre_id(Integer.valueOf(id));
			surfacemailfd_default.setDelivery_way(3);
			surfacemailfd_default.getMap().put("area_pindex_isnull", true);
			surfacemailfd_default.getMap().put("area_name_isnull", true);
			surfacemailfd_default = super.getFacade().getFreightDetailService().getFreightDetail(surfacemailfd_default);
			// 添加 邮递方式,以及默认的值
			if (surfacemailfd_default != null) {

				dynaBean.set("delivery_way_3", "on");
				dynaBean.set("dw3_first_weight", surfacemailfd_default.getFirst_weight());
				dynaBean.set("dw3_first_price", surfacemailfd_default.getFirst_price());
				dynaBean.set("dw3_sed_weight", surfacemailfd_default.getSed_weight());
				dynaBean.set("dw3_sed_price", surfacemailfd_default.getSed_price());
			}

			// 获得排除 默认方式的邮递明细
			FreightDetail surfacemailfd = new FreightDetail();
			surfacemailfd.setFre_id(Integer.valueOf(id));
			surfacemailfd.setDelivery_way(3);
			surfacemailfd.getMap().put("area_pindex_isnotnull", true);
			surfacemailfd.getMap().put("area_name_isnotnull", true);

			List<FreightDetail> surfacemailfdList = super.getFacade().getFreightDetailService()
					.getFreightDetailList(surfacemailfd);

			request.setAttribute("delivery_way_3_list", surfacemailfdList);

		}

		// 初始化区域框
		request.setAttribute("areaList", getAreaList("0"));

		return mapping.findForward("input");

	}
}