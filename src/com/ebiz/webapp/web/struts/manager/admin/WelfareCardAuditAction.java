package com.ebiz.webapp.web.struts.manager.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.CardApply;
import com.ebiz.webapp.domain.CardGenHis;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.domain.CardPIndex;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;

public class WelfareCardAuditAction extends BaseAdminAction {
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

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String title_like = (String) dynaBean.get("title_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");

		CardApply entity = new CardApply();
		super.copyProperties(entity, form);
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		// entity.setAdd_uid(ui.getId());
		entity.getMap().put("title_like", title_like);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		Integer recordCount = getFacade().getCardApplyService().getCardApplyCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CardApply> entityList = super.getFacade().getCardApplyService().getCardApplyPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (CardApply temp : entityList) {
				CardPIndex cPIndex = new CardPIndex();
				cPIndex.setCard_apply_id(temp.getId());
				List<CardPIndex> cPIndexList = getFacade().getCardPIndexService().getCardPIndexList(cPIndex);
				temp.getMap().put("cPIndexList", cPIndexList);
			}
		}
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");

	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		CardApply entity = new CardApply();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(0);
		entity = getFacade().getCardApplyService().getCardApply(entity);
		if (null != entity) {
			CardPIndex cPIndex = new CardPIndex();
			cPIndex.setCard_apply_id(entity.getId());
			List<CardPIndex> list = getFacade().getCardPIndexService().getCardPIndexList(cPIndex);
			request.setAttribute("list", list);
			super.copyProperties(form, entity);
		}
		return new ActionForward("/../manager/admin/WelfareCardAudit/audit.jsp");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		resetToken(request);
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		String audit_state = (String) dynaBean.get("audit_state");
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");

		if (StringUtils.isBlank(id)) {
			super.showMsgForManager(request, response, "参数错误！");
			return null;
		}

		CardApply cardApply = new CardApply();
		cardApply.setId(Integer.valueOf(id));
		cardApply.setIs_del(0);
		cardApply = getFacade().getCardApplyService().getCardApply(cardApply);
		if (null == cardApply) {
			super.showMsgForManager(request, response, "福利卡申请不存在或已取消！");
			return null;
		}

		ServiceCenterInfo service = new ServiceCenterInfo();
		service.setId(cardApply.getSevice_center_info_id());
		service.setIs_del(0);
		service.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		service = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(service);
		if (null == service) {
			String msg = "县域不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

		CardApply entity = new CardApply();
		super.copyProperties(entity, form);
		entity.setId(cardApply.getId());
		entity.setAudit_date(new Date());
		entity.setAudit_user_id(ui.getId());

		// 判断是否已生成福利卡
		CardGenHis cardGenHis = new CardGenHis();
		cardGenHis.setCard_apply_id(cardApply.getId());
		CardGenHis isHaveCard = getFacade().getCardGenHisService().getCardGenHis(cardGenHis);
		if (null != isHaveCard) {
			// 福利卡已生成
			if (isHaveCard.getInfo_state() > Keys.CARD_INFO_STATE.CARD_INFO_STATE_0.getIndex()) {
				super.showMsgForManager(request, response, "该福利卡已被使用，无法审核！");
				return null;
			}

			// 先删
			CardGenHis cGenHis = new CardGenHis();
			cGenHis.setId(isHaveCard.getId());
			cGenHis.getMap().put("remove_card_info", true);
			getFacade().getCardGenHisService().removeCardGenHis(cGenHis);
		}

		if (entity.getAudit_state() == Keys.audit_state.audit_state_1.getIndex()) {
			// 生成福利卡
			cardGenHis.setCard_apply_id(cardApply.getId());
			// 批次号=Gen + 年月日 + （字母和数字）四位随机数 + 卡数量
			String gen_no = "Gen" + fmt.format(new Date()) + this.getStringRandom(4);
			cardGenHis.setGen_no(gen_no);
			if (cardApply.getIs_entity().intValue() == 1) {
				cardGenHis.setCard_type(Keys.CardType.CARD_TYPE_10.getIndex());
			} else if (cardApply.getIs_entity().intValue() == 0) {
				cardGenHis.setCard_type(Keys.CardType.CARD_TYPE_20.getIndex());
			}
			cardGenHis.setCard_amount(cardApply.getCard_amount());
			cardGenHis.setGen_count(cardApply.getCard_count());
			cardGenHis.setInfo_state(Keys.CARD_INFO_STATE.CARD_INFO_STATE_0.getIndex());
			cardGenHis.setIs_system(1);
			cardGenHis.setOwn_service_id(service.getId());
			cardGenHis.setOwn_service_name(service.getServicecenter_name());
			cardGenHis.setAdd_uid(ui.getId());
			cardGenHis.setAdd_date(new Date());
			// 插入子表
			List<CardInfo> cardList = new ArrayList<CardInfo>();

			for (int i = 0; i < cardApply.getCard_count(); i++) {
				CardInfo card = new CardInfo();
				// 卡数量编号
				String str = (i + 1) + "";
				char[] ary1 = str.toCharArray();
				char[] ary2 = { '0', '0', '0', '0' };
				System.arraycopy(ary1, 0, ary2, ary2.length - ary1.length, ary1.length);
				String number = new String(ary2);

				// 卡号=E + 年月日 + （字母和数字）四位随机数 + 卡数量编号
				String card_no = "E" + fmt.format(new Date()) + this.getStringRandom(4) + number;
				card.setCard_no(card_no);

				// 六位随机密码含字母
				DESPlus DES = new DESPlus();
				String card_pwd = DES.encrypt(this.getStringRandom(6));
				card.setCard_pwd(card_pwd);

				card.setCard_apply_id(cardApply.getId());
				card.setApply_no(cardApply.getApply_no());
				card.setGen_no(gen_no);
				card.setSevice_center_info_id(service.getId());
				card.setCard_type(cardGenHis.getCard_type());
				card.setCard_amount(cardApply.getCard_amount());
				card.setCard_cash(cardApply.getCard_amount());
				card.setCard_state(Keys.CARD_STATE.CARD_STATE_0.getIndex());
				card.setStart_date(cardApply.getStart_date());
				card.setEnd_date(cardApply.getEnd_date());
				card.setAdd_date(new Date());
				card.setAdd_uid(ui.getId());
				card.setIs_del(0);
				card.setOrder_value(0);
				card.setIs_send(0);
				cardList.add(card);
			}
			cardGenHis.getMap().put("insert_card_info", cardList);
			entity.getMap().put("insert_card_gen_his", cardGenHis);
		}

		getFacade().getCardApplyService().modifyCardApply(entity);

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	// 生成随机数字和字母,
	public static String getStringRandom(int length) {

		String val = "";
		Random random = new Random();

		// 参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				char randomVal = (char) (random.nextInt(26) + temp);
				// 输出是大写字母还是小写字母
				while (String.valueOf(randomVal).equals("I") || String.valueOf(randomVal).equals("l")
						|| String.valueOf(randomVal).equals("O") || String.valueOf(randomVal).equals("o")) {
					randomVal = (char) (random.nextInt(26) + temp);
				}
				val += randomVal;
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

}
