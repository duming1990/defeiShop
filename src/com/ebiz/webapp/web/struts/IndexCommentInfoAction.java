package com.ebiz.webapp.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 */
public class IndexCommentInfoAction extends BaseWebAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String link_id = (String) dynaBean.get("link_id");
		Pager pager = (Pager) dynaBean.get("pager");

		dynaBean.set("link_id", link_id);
		CommentInfo entity = new CommentInfo();
		super.copyProperties(entity, dynaBean);

		entity.setComm_state(1);
		Integer recordCount = super.getFacade().getCommentInfoService().getCommentInfoCount(entity);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setCount(pager.getRowCount());
		entity.getRow().setFirst(pager.getFirstRow());

		List<CommentInfo> commentInfoList = getFacade().getCommentInfoService().getCommentInfoPaginatedList(entity);
		if (null != commentInfoList && commentInfoList.size() > 0) {
			for (CommentInfo ci : commentInfoList) {
				String secretString = super.setSecretUserName(ci.getComm_uname());
				ci.getMap().put("secretString", secretString);

				Integer user_id = ci.getComm_uid();
				if (null != user_id)
					ci.getMap().put("userInfo", super.getUserInfo(user_id));

				if (1 == ci.getHas_pic()) {
					// 获取评论图片
					BaseFiles baseFiles = new BaseFiles();
					baseFiles.setLink_id(ci.getId());
					baseFiles.setLink_tab("CommentInfo");
					baseFiles.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
					List<BaseFiles> baseFilesList = super.getFacade().getBaseFilesService().getBaseFilesList(baseFiles);
					if (null != baseFilesList && baseFilesList.size() > 0) {
						ci.getMap().put("baseFilesList", baseFilesList);
					}
					super.setMapCommentSonList(ci);
				}
			}
		}
		request.setAttribute("commentInfoList", commentInfoList);
		return mapping.findForward("list");
	}

	public ActionForward otherList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String link_f_id = (String) dynaBean.get("link_f_id");
		Pager pager = (Pager) dynaBean.get("pager");

		if (StringUtils.isBlank(link_f_id)) {
			String msg = "传入的产品参数link_f_id为空";
			super.renderJavaScript(response, "alert('" + msg + "');");
			return null;
		}

		CommentInfo entity = new CommentInfo();
		entity.setComm_state(1);
		super.copyProperties(entity, dynaBean);
		// entity.setComm_state(0);
		Integer recordCount = super.getFacade().getCommentInfoService().getCommentInfoCount(entity);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setCount(pager.getRowCount());
		entity.getRow().setFirst(pager.getFirstRow());
		List<CommentInfo> commentInfoList = getFacade().getCommentInfoService().getCommentInfoPaginatedList(entity);
		if (null != commentInfoList && commentInfoList.size() > 0) {
			for (CommentInfo ci : commentInfoList) {
				String secretString = super.setSecretUserName(ci.getComm_uname());
				ci.getMap().put("secretString", secretString);
			}
		}
		request.setAttribute("commentInfoList", commentInfoList);

		// 商品评价
		CommentInfo ci = new CommentInfo();
		ci.setComm_state(1);
		ci.setLink_f_id(Integer.valueOf(link_f_id));
		Integer commentCount = getFacade().getCommentInfoService().getCommentInfoCount(ci);
		request.setAttribute("commentCount", commentCount);
		List<CommentInfo> list = getFacade().getCommentInfoService().getCommentInfoList(ci);
		request.setAttribute("commentInfoList6", list);
		// Integer commentScore = getFacade().getCommentInfoService().getCommentInfoAvgCommSore(ci);
		// request.setAttribute("commentScore", commentScore);

		ci.setComm_level(1);// 1、好评（评分：4、5分）
		Integer commentLevel1Count = getFacade().getCommentInfoService().getCommentInfoCount(ci);
		request.setAttribute("commentLevel1Count", commentLevel1Count);
		request.setAttribute("commentLevel1Rate",
				dfFormat0.format((commentLevel1Count.doubleValue() / commentCount.doubleValue()) * 100));
		ci.setComm_level(2);// 2、中评（评分2、3分）
		Integer commentLevel2Count = getFacade().getCommentInfoService().getCommentInfoCount(ci);
		request.setAttribute("commentLevel2Count", commentLevel2Count);
		request.setAttribute("commentLevel2Rate",
				dfFormat0.format((commentLevel2Count.doubleValue() / commentCount.doubleValue()) * 100));
		ci.setComm_level(3);// 3 、差评（评分：1分）
		Integer commentLevel3Count = getFacade().getCommentInfoService().getCommentInfoCount(ci);
		request.setAttribute("commentLevel3Count", commentLevel3Count);
		request.setAttribute("commentLevel3Rate",
				dfFormat0.format((commentLevel3Count.doubleValue() / commentCount.doubleValue()) * 100));
		if (commentCount.intValue() == 0) {
			request.setAttribute("commentLevel1Rate", "0");
			request.setAttribute("commentLevel2Rate", "0");
			request.setAttribute("commentLevel3Rate", "0");
		}
		return new ActionForward("/index/IndexCommentInfo/otherList.jsp");
	}
}
