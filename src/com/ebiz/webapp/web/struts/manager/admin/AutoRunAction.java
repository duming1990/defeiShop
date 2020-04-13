package com.ebiz.webapp.web.struts.manager.admin;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserBiRecord;

public class AutoRunAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		return mapping.findForward("list");

	}

	public ActionForward autoConfirmReceipt(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			super.getFacade().getAutoRunService().autoConfirmReceipt();
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	public ActionForward autoOrderCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			super.getFacade().getAutoRunService().autoOrderCancel();
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	public ActionForward autoDataClear(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			super.getFacade().getOrderInfoService().removeOrderInfo(new OrderInfo());
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	public ActionForward autoServiceCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			super.getFacade().getAutoRunService().autoServiceCancel();
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	/**
	 * 自动同步京东商品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward autoSyncJdProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String save_path = request.getSession().getServletContext().getRealPath(String.valueOf(File.separatorChar))
					+ File.separatorChar;
			super.getFacade().getAutoRunService().autoSyncJdProduct();
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	/**
	 * 自动同步创建京东订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward autoSyncUploadJdOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			super.getFacade().getAutoRunService().autoSyncUploadJdOrder();
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	/**
	 * 自动同步确认京东订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward autoSyncConfirmJdOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			super.getFacade().getAutoRunService().autoSyncConfirmJdOrder();
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	/**
	 * 自动同步下载更新京东订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward autoSyncDownloadJdOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			super.getFacade().getAutoRunService().autoSyncDownloadJdOrder();
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	/**
	 * 同步下载京东地区
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward autoSyncDownloadJdArea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			logger.warn("==autoSyncDownloadJdArea start==");
			super.getFacade().getAutoRunService().autoSyncDownloadJdArea("0");
			logger.warn("==autoSyncDownloadJdArea end==");
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	/**
	 * 手动计算奖励
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward autoReckonRebateAndAid(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");

		if (StringUtils.isNotBlank(order_id) && GenericValidator.isLong(order_id)) {
			UserBiRecord userBiRecord = new UserBiRecord();
			userBiRecord.setOrder_id(Integer.valueOf(order_id));
			userBiRecord.setIs_del(0);
			int count = super.getFacade().getUserBiRecordService().getUserBiRecordCount(userBiRecord);
			if (count > 0) {
				super.renderText(response, "订单奖励已计算，请勿重复计算！");
				return null;
			}
		} else {
			super.renderText(response, "order_id错误，请联系管理员！");
			return null;
		}
		try {
			logger.warn("==autoReckonRebateAndAid start==");
			super.getFacade().getAutoRunService().autoReckonRebateAndAid(Integer.valueOf(order_id));
			logger.warn("==autoReckonRebateAndAid end==");
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("==autoReckonRebateAndAid ERRO==" + e.getMessage());
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	/**
	 * 9.9付费会员到期关闭 2018-3-13
	 */
	public ActionForward autoUserCartClose(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			logger.warn("==autoUserCartClose start==");
			super.getFacade().getAutoRunService().autoUserCartClose();
			logger.warn("==autoUserCartClose end==");
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	public ActionForward modifyAutoCloseOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			super.getFacade().getAutoRunService().modifyAutoCloseOrder();
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	/**
	 * 本年已获取扶贫金超出限制金额的贫困户自动从商品扶贫对象中移除，并给企业发站内消息 2018-4-11 liuzhixiang
	 */
	public ActionForward autoRemovePoorFromComm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			logger.warn("==autoRemovePoorFromComm start==");
			super.getFacade().getAutoRunService().autoRemovePoorFromComm();
			logger.warn("==autoRemovePoorFromComm end==");
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	/**
	 * 99元会员卡有效期的自动续费微信消息
	 */
	public ActionForward autoUserCardEndDateRenewTip(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			logger.warn("==autoUserCardEndDateRenewTip start==");
			super.getFacade().getAutoRunService().autoUserCardEndDateRenewTip();
			logger.warn("==autoUserCardEndDateRenewTip end==");
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

	/**
	 * 订单交易完成后，用户未评价，则系统默认生成好评
	 */
	public ActionForward autoCommentByOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			logger.warn("==autoCommentByOrder start==");
			super.getFacade().getAutoRunService().autoCommentByOrder();
			logger.warn("==autoCommentByOrder end==");
			super.renderText(response, "操作成功！");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("==autoCommentByOrder Exception==" + e.getMessage());
			super.renderText(response, "操作失败，请联系管理员！");
			return null;
		}
	}

}