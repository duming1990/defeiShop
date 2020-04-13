package com.ebiz.webapp.web.struts;

import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class IndexAction extends BaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String pc = (String) dynaBean.get("pc");

		if (StringUtils.isBlank(pc)) {
			Boolean judgeIsMoblie = super.JudgeIsMoblie(request);
			if (judgeIsMoblie) {
				return new ActionForward("/m/index.do");
			} else {
				return this.index(mapping, form, request, response);
			}
		} else {
			return this.index(mapping, form, request, response);
		}

	}

	public ActionForward toStatic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index_static.shtml");
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		logger.info("1231313213");

		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
		String city = Keys.QUANGUO_P_INDEX;

		if (null != current_p_index) {
			city = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		} else {
			String p_name = Keys.QUANGUO_P_NAME;
			super.setPindexCookies(response, city, p_name);
		}

		if (city.equals(Keys.QUANGUO_P_INDEX.toString())) {
			city = null;
		}

		List<BaseLink> base10LinkList = this.getBaseLinkListWithPindex(10, 4, "no_null_image_path", city);
		request.setAttribute("base10LinkList", base10LinkList);

		List<BaseLink> base0LinkList = this.getBaseLinkListWithPindex(0, 1, "no_null_image_path", city);
		if (null != base0LinkList && base0LinkList.size() > 0) {
			request.setAttribute("base0Link", base0LinkList.get(base0LinkList.size() - 1));
		}

		// 热搜
		List<BaseLink> base88LinkList = this.getBaseLinkList(88, 5, null);
		request.setAttribute("base88LinkList", base88LinkList);
		// 最上面导航
		List<BaseLink> base3000LinkList = this.getBaseLinkList(3000, null, null);
		request.setAttribute("base3000LinkList", base3000LinkList);

		List<BaseLink> base90LinkList = this.getBaseLinkList(90, 6, null);
		request.setAttribute("base90LinkList", base90LinkList);

		List<BaseLink> base91LinkList = this.getBaseLinkList(91, 6, null);
		request.setAttribute("base91LinkList", base91LinkList);
		List<BaseLink> base40LinkList = this.getBaseLinkListWithPindex(40, 5, "no_null_image_path", city);
		request.setAttribute("base40LinkList", base40LinkList);

		List<BaseLink> base80LinkList = this.getBaseLinkList(80, 9, null);
		request.setAttribute("base80LinkList", base80LinkList);

		CommInfo commInfo = new CommInfo();
		commInfo.setComm_type(Keys.CommType.COMM_TYPE_4.getIndex());
		commInfo.setAudit_state(1);
		commInfo.setIs_sell(1);
		commInfo.setIs_has_tc(1);
		commInfo.getMap().put("not_out_sell_time", "true");
		commInfo.getRow().setCount(20);
		List<CommInfo> commmInfoList = super.getFacade().getCommInfoService().getCommInfoList(commInfo);
		request.setAttribute("commmInfoHdList", commmInfoList);

		List<BaseData> baseData2100List = super.getBaseDataList(Keys.BASE_DATA_ID.BASE_DATA_ID_2100.getIndex());

		BaseData nowBaseData = new BaseData();
		nowBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_2100.getIndex());
		nowBaseData.setIs_del(0);
		nowBaseData.getMap().put("now_date", ymdhms.format(new Date()));
		List<BaseData> baseDataNowList = getFacade().getBaseDataService().getBaseDataList(nowBaseData);
		if (null != baseDataNowList && baseDataNowList.size() > 0) {
			nowBaseData = baseDataNowList.get(0);
		}
		if (null != baseData2100List && baseData2100List.size() > 0 && null != nowBaseData
				&& null != nowBaseData.getId()) {
			for (BaseData temp : baseData2100List) {

				if (temp.getId().intValue() == nowBaseData.getId().intValue()) {
					Calendar now = Calendar.getInstance();
					String endTime = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
							+ now.get(Calendar.DAY_OF_MONTH) + " " + temp.getPre_varchar_2();
					request.setAttribute("endTime", sdFormat_ymdhms.parse(endTime));
				}
			}
		}

		List<BaseLink> base200LinkList = this.getBaseLinkList(200, 1, null);
		request.setAttribute("base200LinkList", base200LinkList);

		List<BaseLink> base210LinkList = this.getBaseLinkList(210, 5, null);
		request.setAttribute("base210LinkList", base210LinkList);

		List<BaseLink> base220LinkList = this.getBaseLinkList(220, 6, "no_null_image_path");
		request.setAttribute("base220LinkList", base220LinkList);

		List<BaseLink> base300LinkList = this.getBaseLinkList(300, 1, null);
		request.setAttribute("base300LinkList", base300LinkList);

		List<BaseLink> base310LinkList = this.getBaseLinkList(310, 1, "no_null_image_path");
		request.setAttribute("base310LinkList", base310LinkList);

		List<BaseLink> base320LinkList = this.getBaseLinkList(320, 6, "no_null_image_path");
		request.setAttribute("base320LinkList", base320LinkList);

		List<BaseLink> base330LinkList = this.getBaseLinkList(330, 4, null);
		request.setAttribute("base330LinkList", base330LinkList);

		List<BaseLink> base400LinkList = this.getBaseLinkList(400, 1, null);
		request.setAttribute("base400LinkList", base400LinkList);

		List<BaseLink> base410LinkList = this.getBaseLinkList(410, 4, "no_null_image_path");
		request.setAttribute("base410LinkList", base410LinkList);

		List<BaseLink> base70LinkList = this.getBaseLinkList(70, 20, null);// 取后台编辑的楼层
		if ((null != base70LinkList) && (base70LinkList.size() > 0)) {
			for (BaseLink temp : base70LinkList) {

				if (temp.getPre_number().intValue() == 1) {
					List<BaseLink> baseLink102List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 2, 8, "no_null_image_path", city, true);
					temp.getMap().put("baseLink102List", baseLink102List);
				} else if (temp.getPre_number().intValue() == 2) {
					List<BaseLink> baseLink102List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 2, 1, "no_null_image_path", city, true);
					temp.getMap().put("baseLink102List", baseLink102List);

					List<BaseLink> baseLink104List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 4, 6, "no_null_image_path", city, true);
					temp.getMap().put("baseLink104List", baseLink104List);
				} else if (temp.getPre_number().intValue() == 3) {
					List<BaseLink> baseLink102List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 2, 2, "no_null_image_path", city, true);
					temp.getMap().put("baseLink102List", baseLink102List);

					List<BaseLink> baseLink104List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 4, 4, "no_null_image_path", city, true);
					temp.getMap().put("baseLink104List", baseLink104List);
				} else if (temp.getPre_number().intValue() == 4) {
					List<BaseLink> baseLink102List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 2, 1, "no_null_image_path", city, true);
					temp.getMap().put("baseLink102List", baseLink102List);

					List<BaseLink> baseLink104List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 4, 4, "no_null_image_path", city, true);
					temp.getMap().put("baseLink104List", baseLink104List);

					List<BaseLink> baseLink105List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 5, 1, "no_null_image_path", city, true);
					temp.getMap().put("baseLink105List", baseLink105List);
				} else if (temp.getPre_number().intValue() == 5) {
					List<BaseLink> baseLink102List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 2, 2, "no_null_image_path", city, true);
					temp.getMap().put("baseLink102List", baseLink102List);

					List<BaseLink> baseLink104List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 4, 1, "no_null_image_path", city, true);
					temp.getMap().put("baseLink104List", baseLink104List);

					List<BaseLink> baseLink105List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 5, 2, "no_null_image_path", city, true);
					temp.getMap().put("baseLink105List", baseLink105List);
				} else if (temp.getPre_number().intValue() == 6) {

					List<BaseLink> baseLink602List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
							temp.getId(), 2, 10, "no_null_image_path", city, true);

					temp.getMap().put("baseLink602List", baseLink602List);

				}

				temp.getMap().put("baseLink101List",
						this.getBaseLinkListWithParIdAndParSonType(temp.getId(), 1, 6, null));
				temp.getMap().put("baseLink103List",
						this.getBaseLinkListWithParIdAndParSonType(temp.getId(), 3, 1, "no_null_image_path"));
				List<BaseLink> baseLink108List = this.getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(
						temp.getId(), 8, 1, "no_null_image_path", city, false);
				temp.getMap().put("baseLink108List", baseLink108List);

			}
		}
		request.setAttribute("base70LinkList", base70LinkList);

		return mapping.findForward("success");
	}

}
