package com.ebiz.webapp.web.struts.m;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserRelationPar;
import com.ebiz.webapp.domain.UserScoreRecord;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.ScoreType;
import com.ebiz.webapp.web.util.DateTools;

//import com.ebiz.tjgis.web.util.DESPlus;

/**
 * @author Wu,yang
 * @version 2015-12-6
 */
public class MMyScoreAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.view(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		super.getModNameForMobile(request);
		request.setAttribute("titleSideName", Keys.TopBtns.VIEW.getName());
		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		entity = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == entity) {
			String msg = "用户名不存在或者已经被删除！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		super.copyProperties(form, entity);
		return mapping.findForward("view");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);
		super.getModNameForMobile(request);
		request.setAttribute("canSearch", true);
		String score_type = (String) dynaBean.get("score_type");
		Pager pager = (Pager) dynaBean.get("pager");

		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		ui = super.getUserInfo(ui.getId());
		request.setAttribute("user_info", ui);
		request.setAttribute("sum_score", ui.getCur_score().add(ui.getUser_score_union()));

		String tip_msg = "";
		String msg = "";
		Integer is_upgrade = 0;// 0:不能升级，1可以升级，2等级已最高
		BigDecimal curScore = new BigDecimal(0);// 用户积分
		if (null != ui.getCur_score()) {
			curScore = ui.getCur_score();
		}

		BigDecimal unionScore = new BigDecimal(0);// 联盟积分
		if (null != ui.getUser_score_union()) {
			unionScore = ui.getUser_score_union();
		}
		// 个人总积分
		BigDecimal total_my_score = curScore.add(unionScore);
		// 用户当前等级
		BaseData uiBaseData = new BaseData();
		uiBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		uiBaseData.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		uiBaseData.setId(ui.getUser_level());
		uiBaseData = getFacade().getBaseDataService().getBaseData(uiBaseData);

		if (uiBaseData == null) {
			tip_msg = "基础积分等级数据错误!";
			super.showTipMsg(mapping, form, request, response, tip_msg);
			return null;
		}
		// 当前等级
		int cur_user_level = uiBaseData.getPre_number3();
		String cur_level_name = uiBaseData.getType_name();
		request.setAttribute("cur_level_name", cur_level_name);
		request.setAttribute("cur_user_level", cur_user_level);

		DecimalFormat df2 = new DecimalFormat("###.00");// 这样为保持2位

		// 用户下一等级
		BaseData bData = new BaseData();
		bData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		bData.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		bData.setPre_number3(cur_user_level + 1);
		bData = getFacade().getBaseDataService().getBaseData(bData);
		if (bData == null) {
			msg = "基础积分等级数据错误!";
			super.showTipMsg(mapping, form, request, response, msg);
			return null;
		}

		if (Integer.valueOf(cur_user_level) == 7) {
			// 最高等级
			is_upgrade = 2;
			request.setAttribute("next_level_name", "尊敬的用户");
		} else {
			request.setAttribute("next_level_name", bData.getType_name());
			request.setAttribute("next_level_score", bData.getPre_number());
			// 用户升级所需个人积分
			BigDecimal next_level_score = new BigDecimal(bData.getPre_number());

			if (next_level_score.compareTo(ui.getCur_score()) <= 0) {
				// 个人积分满足升级条件
				is_upgrade = 1;
			} else {
				// 个人积分不满足升级条件
				request.setAttribute("person_score_need", next_level_score.subtract(ui.getCur_score()));

				if (cur_user_level < 3) {
					// 三星及其以下不需要查团队联盟积分
					if (total_my_score.compareTo(new BigDecimal(bData.getPre_number())) < 0) {
						// 总积分没有达到最低升级标准
						request.setAttribute("union_score_need", next_level_score.subtract(total_my_score));
					} else {
						// 总积分已经达到最低升级标准
						is_upgrade = 1;
					}
				} else {
					// 四星及其以上还需要查团队联盟积分
					if (total_my_score.compareTo(new BigDecimal(bData.getPre_number())) < 0) {
						// 总积分没有达到最低升级标准
						request.setAttribute("upgrade_defeated", true);
					} else {// 总积分已经达到最低升级标准

						UserRelationPar userRelationPar = new UserRelationPar();
						userRelationPar.setUser_par_id(ui.getId());
						userRelationPar.setUser_par_levle(1);
						List<UserRelationPar> userRelationParList = super.getFacade().getUserRelationParService()
								.getUserRelationParListWithScore(userRelationPar);
						if (null != userRelationParList && userRelationParList.size() > 0) {
							BigDecimal total_score = new BigDecimal(0);// 联盟积分（所有下级的总积分+个人积分，去除最高积分）
							for (int i = 0; i < userRelationParList.size(); i++) {
								// if (i == 0){
								// 查询最高分用户信息
								// UserInfo uInfo=new UserInfo();
								// uInfo.setId(userRelationParList.get(i).getUser_id());
								// uInfo=super.getFacade().getUserInfoService().getUserInfo(uInfo);
								// if(uInfo!=null){
								// request.setAttribute("top_user_name", uInfo.getUser_name());
								// }
								// }
								String score_temp = userRelationParList.get(i).getMap().get("total_score") == null ? ""
										: userRelationParList.get(i).getMap().get("total_score").toString();
								if (StringUtils.isNotBlank(score_temp) && GenericValidator.isDouble(score_temp)) {
									if (i == 0 && curScore.compareTo(new BigDecimal(score_temp)) == -1) {
										total_score = total_score.add(curScore);
									} else {
										total_score = total_score.add(new BigDecimal(score_temp));
									}
								}
							}
						}
					}
				}
			}
		}
		request.setAttribute("is_upgrade", is_upgrade);

		UserScoreRecord entity = new UserScoreRecord();
		entity.setAdd_user_id(ui.getId());
		if (StringUtils.isNotBlank(score_type) && GenericValidator.isInt(score_type)) {
			entity.setScore_type(Integer.valueOf(score_type));
		}
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());

		entity.getMap().put("order_by_info", "add_date desc,");

		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);
		Integer pageSize = 10;
		Integer recordCount = getFacade().getUserScoreRecordService().getUserScoreRecordCount(entity);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserScoreRecord> userScoreRecordList = super.getFacade().getUserScoreRecordService()
				.getUserScoreRecordPaginatedList(entity);
		if (null != userScoreRecordList && userScoreRecordList.size() > 0) {
			request.setAttribute("userScoreRecordList", userScoreRecordList);
			if (userScoreRecordList.size() == Integer.valueOf(pageSize)) {
				request.setAttribute("appendMore", 1);
			}
			dynaBean.set("pageSize", pageSize);
		}
		request.setAttribute("scoreTypes", Keys.ScoreType.values());

		return mapping.findForward("list");
	}

	public ActionForward getListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		// getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		String title_name_like = (String) dynaBean.get("title_name_like");
		String score_type = (String) dynaBean.get("score_type");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		UserScoreRecord entity = new UserScoreRecord();
		entity.setAdd_user_id(ui.getId());
		if (StringUtils.isNotBlank(score_type) && GenericValidator.isInt(score_type)) {
			entity.setScore_type(Integer.valueOf(score_type));
		}
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());

		entity.getMap().put("order_by_info", "add_date desc,");

		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);

		Integer recordCount = getFacade().getUserScoreRecordService().getUserScoreRecordCount(entity);
		pager.init(recordCount.longValue(), new Integer(pageSize), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<UserScoreRecord> list = getFacade().getUserScoreRecordService().getUserScoreRecordPaginatedList(entity);

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		// String ctx = super.getCtxPath(request);
		if ((null != list) && (list.size() > 0)) {
			for (UserScoreRecord usr : list) {
				JSONObject map = new JSONObject();

				String score_type_name = "";
				for (ScoreType temp : Keys.ScoreType.values()) {
					if (usr.getScore_type() == temp.getIndex()) {
						score_type_name = temp.getName();
					}
				}
				map.put("score_type_name", score_type_name);
				map.put("hd_score_before", usr.getHd_score_before());
				map.put("hd_score", usr.getHd_score());
				map.put("hd_score_after", usr.getHd_score_after());
				map.put("add_date", DateTools.getStringDate(usr.getAdd_date(), "yyyy-MM-dd"));

				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (list.size() == Integer.valueOf(pageSize)) {
				datas.put("appendMore", "1");
			}
			datas.put("dataList", dataLoadList.toString());
		} else {
			datas.put("ret", "0");
			datas.put("msg", "已全部加载");
		}

		logger.info("===datas:{}", datas.toString());
		super.renderJson(response, datas.toString());

		return null;

	}

}
