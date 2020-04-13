package com.ebiz.webapp.web.struts;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class IndexHdAction extends BaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Boolean judgeIsMoblie = super.JudgeIsMoblie(request);
		if (judgeIsMoblie) {
			return new ActionForward("/m/MIndexHd.do", true);
		}

		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");

		List<BaseLink> base2100LinkList = this.getBaseLinkList(2100, 1, "no_null_image_path");
		request.setAttribute("base2100LinkList", base2100LinkList);

		CommInfo commInfo = new CommInfo();
		commInfo.setComm_type(Keys.CommType.COMM_TYPE_4.getIndex());
		commInfo.setAudit_state(1);
		commInfo.setIs_sell(1);
		commInfo.setIs_has_tc(1);
		commInfo.getMap().put("not_out_sell_time", "true");
		commInfo.getRow().setCount(20);

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(commInfo);
		pager.init(recordCount.longValue(), 20, pager.getRequestPage());
		commInfo.getRow().setFirst(pager.getFirstRow());
		commInfo.getRow().setCount(pager.getRowCount());

		List<CommInfo> commmInfoList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(commInfo);
		request.setAttribute("commmInfoList", commmInfoList);

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
					temp.getMap().put("is_current", "true");
				}
			}
		}

		request.setAttribute("baseData2100List", baseData2100List);
		request.setAttribute("canSearchHd", "true");

		return new ActionForward("/index/IndexHd/index.jsp");
	}
}
