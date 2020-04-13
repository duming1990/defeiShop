package com.ebiz.webapp.web.struts.m;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.domain.VillageDynamicComment;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.sensitivewdfilter.WordFilter;
import com.ebiz.webapp.web.Keys;

/**
 * @author 王志雄
 * @date 2018年1月31日
 */
public class MVillageDynamicAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.from(mapping, form, request, response);
	}

	public ActionForward from(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String type = (String) dynaBean.get("type");
		if (StringUtils.isNotBlank(type) && type.equals("1")) {
			return new ActionForward("/../m/MVillageDynamic/MVillageDynamic_from.jsp");// 动态
		}

		return new ActionForward("/../m/MVillageDynamic/MVillageDynamic_from_comm.jsp");// 商品
	}

	public ActionForward getAjaxDataList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "1", msg = "";
		JSONObject data = new JSONObject();
		DynaBean dynaBean = (DynaBean) form;
		String village_id = (String) dynaBean.get("village_id");
		String type = (String) dynaBean.get("type");

		if (GenericValidator.isInt(type) && Integer.valueOf(type) == 2) {
			setVillageCommClass(data, village_id);
		}

		return returnAjaxData(response, code, msg, data);

	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String village_id = (String) dynaBean.get("village_id");
		String content = (String) dynaBean.get("content");
		String type = (String) dynaBean.get("type");
		String comm_name = (String) dynaBean.get("comm_name");
		String comm_price = (String) dynaBean.get("comm_price");
		String inventory = (String) dynaBean.get("inventory");
		String cls_id = (String) dynaBean.get("cls_id");
		String up_date = (String) dynaBean.get("up_date");
		String down_date = (String) dynaBean.get("down_date");
		String comm_no = (String) dynaBean.get("comm_no");
		log.info("====comm_no:" + comm_no);
		String[] upload_files = request.getParameterValues("upload_files");
		String comm_main_pic = "";// 商品主图

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			code = "0";
			msg = "您还未登录，请先登录系统！";
			return returnAjaxData(response, code, msg, data);
		}
		if (StringUtils.isBlank(content) && (null == upload_files || upload_files.length <= 0)) {
			msg = "请填写动态后发布！";
			return returnAjaxData(response, code, msg, data);
		}
		if (StringUtils.isBlank(type)) {
			msg = "参数不正确，请联系管理员！";
			return returnAjaxData(response, code, msg, data);
		}
		VillageMember villageMember = new VillageMember();
		villageMember.setVillage_id(Integer.valueOf(village_id));
		villageMember.setUser_id(ui.getId());
		villageMember.setAudit_state(1);
		villageMember.setIs_del(0);
		villageMember = getFacade().getVillageMemberService().getVillageMember(villageMember);
		if (villageMember == null) {
			msg = "请先申请加入该村！";
			return returnAjaxData(response, code, msg, data);
		}

		VillageInfo villageInfo = super.getVillageInfo(village_id, Keys.INFO_STATE.INFO_STATE_1.getIndex());
		if (null == villageInfo) {
			msg = "村子不存在或审核未通过";
			return returnAjaxData(response, code, msg, data);
		}

		VillageDynamic dynamic = new VillageDynamic();
		super.copyProperties(dynamic, form);
		dynamic.setContent(WordFilter.doFilter(dynamic.getContent()));
		dynamic.setVillage_name(villageInfo.getVillage_name());
		dynamic.setP_index(villageInfo.getP_index());
		dynamic.setType(Integer.valueOf(type));
		dynamic.setAdd_date(new Date());
		dynamic.setAdd_user_id(ui.getId());
		dynamic.setAdd_user_name(ui.getReal_name());
		dynamic.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		dynamic.setAudit_date(new Date());
		if (null != upload_files && upload_files.length > 0) {
			comm_main_pic = upload_files[0];// 获取一张图片当做商品主图
			dynamic.getMap().put("upload_files", upload_files);
		}

		// 发布商品
		if (Integer.valueOf(type) == Keys.DynamicType.dynamic_type_2.getIndex()) {
			// 创建商品及套餐
			CommInfo commInfo = new CommInfo();
			commInfo.setComm_name(comm_name);
			commInfo.setComm_type(Keys.CommType.COMM_TYPE_7.getIndex());
			if (comm_main_pic.length() > 0) {
				commInfo.setMain_pic(comm_main_pic);
			}
			commInfo.setP_index(Integer.valueOf(StringUtils.substring(villageInfo.getP_index().toString(), 0, 6)));
			commInfo.setOwn_entp_id(villageInfo.getId());
			commInfo.setPrice_ref(new BigDecimal(comm_price));
			commInfo.setSale_price(new BigDecimal(comm_price));
			commInfo.setComm_no(comm_no);
			commInfo.setIs_has_tc(1);
			commInfo.setIs_sell(1);
			commInfo.setCls_id(Integer.valueOf(cls_id));
			commInfo.setUp_date(sdFormat_ymd.parse(up_date));
			commInfo.setDown_date(sdFormat_ymd.parse(down_date));

			commInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
			commInfo.setAudit_date(new Date());

			commInfo.setAdd_date(new Date());
			commInfo.setAdd_user_id(ui.getId());
			commInfo.setAdd_user_name(ui.getReal_name());
			dynamic.getMap().put("dynamic_comm_info", commInfo);
			// 套餐
			CommTczhPrice commTczh = new CommTczhPrice();
			commTczh.setAdd_date(new Date());
			commTczh.setAdd_user_id(ui.getId());
			commTczh.setComm_price(new BigDecimal(comm_price));
			commTczh.setInventory(Integer.valueOf(inventory));
			commTczh.setTczh_name(commInfo.getComm_name());
			dynamic.getMap().put("dynamic_commTczh", commTczh);
			dynamic.getMap().put("village_id", village_id);

		}
		code = "1";
		msg = "发布成功！";
		int i = getFacade().getVillageDynamicService().createVillageDynamic(dynamic);
		if (i < 1) {
			code = "-1";
			msg = "发布失败！请联系管理员";
		} else {

			// 如果插入成功了，自动生成商品的二维码
			createCommInfoCode(request, i);

		}
		return returnAjaxData(response, code, msg, data);
	}

	/**
	 * @desc 评论、回复
	 */
	public ActionForward saveComment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "1", msg = "";
		JSONObject data = new JSONObject();
		Integer entity_id = null;
		Integer add_user_id = null;
		Integer village_id = null;
		String add_user_name = "";

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");// 关联user_id
		String content = (String) dynaBean.get("content");
		String type = (String) dynaBean.get("type");
		log.info("type:" + type);
		log.info("id:" + id);
		log.info("village_id:" + village_id);
		if (StringUtils.isBlank(type) || !GenericValidator.isInt(id)) {
			code = "-1";
			msg = "参数不正确，请联系管理员";
			return super.returnAjaxData(response, code, msg, data);
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			code = "0";
			msg = "您还未登录，请先登录系统！";
			return super.returnAjaxData(response, code, msg, data);
		}

		// 评论的回复
		if (Integer.valueOf(type) == Keys.CommentType.COMMENT_TYPE_2.getIndex()) {

			VillageDynamicComment reply = new VillageDynamicComment();
			reply.setIs_del(0);
			reply.setId(Integer.valueOf(id));
			reply = getFacade().getVillageDynamicCommentService().getVillageDynamicComment(reply);
			if (null == reply) {
				code = "-1";
				msg = "该信息已被删除";
				return super.returnAjaxData(response, code, msg, data);
			}

			entity_id = reply.getLink_id();
			add_user_id = reply.getAdd_user_id();
			add_user_name = reply.getAdd_user_name();
			VillageDynamic dynamic = new VillageDynamic();
			dynamic.setId(entity_id);
			dynamic = getFacade().getVillageDynamicService().getVillageDynamic(dynamic);

			village_id = dynamic.getVillage_id();
		}

		// 动态的评论或者点赞
		if (Integer.valueOf(type) == Keys.CommentType.COMMENT_TYPE_1.getIndex()
				|| Integer.valueOf(type) == Keys.CommentType.COMMENT_TYPE_3.getIndex()) {
			VillageDynamic dynamic = new VillageDynamic();
			dynamic.setId(Integer.valueOf(id));
			dynamic.setIs_del(0);
			dynamic = getFacade().getVillageDynamicService().getVillageDynamic(dynamic);
			if (null == dynamic) {
				code = "-1";
				msg = "该信息已被删除";
				return super.returnAjaxData(response, code, msg, data);
			}

			entity_id = dynamic.getId();
			add_user_id = dynamic.getAdd_user_id();
			add_user_name = dynamic.getAdd_user_name();
			village_id = dynamic.getVillage_id();
		}

		// 如果是点赞,判断是否赞过，赞过取消。
		if (Integer.valueOf(type) == Keys.CommentType.COMMENT_TYPE_3.getIndex()) {

			// 判断操作用户与被操作用户是否同一个村子
			VillageDynamic dynamic = new VillageDynamic();
			dynamic.setId(Integer.valueOf(id));
			dynamic.setIs_del(0);
			dynamic = getFacade().getVillageDynamicService().getVillageDynamic(dynamic);
			if (null == dynamic) {
				msg = "动态已被删除!";
				return returnAjaxData(response, "-1", msg, data);
			}

			VillageDynamicComment zan = new VillageDynamicComment();
			zan.setLink_id(entity_id);
			zan.setIs_del(0);
			zan.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
			zan.setComment_type(Keys.CommentType.COMMENT_TYPE_3.getIndex());
			zan.setLink_user_id(add_user_id);
			zan.setAdd_user_id(ui.getId());
			zan = getFacade().getVillageDynamicCommentService().getVillageDynamicComment(zan);
			if (null != zan) {
				JSONObject json = new JSONObject();
				List<VillageDynamicComment> zanList = getVillageDynamicCommentList(Integer.valueOf(id),
						Keys.CommentType.COMMENT_TYPE_3.getIndex(), 5, null);
				if (null != zanList && zanList.size() > 0) {
					int i = 0;
					for (VillageDynamicComment temp : zanList) {
						if (temp.getAdd_user_id().equals(ui.getId())) {
							json.put("cur_user_zanName_index", i);
						}
						i++;
					}
				}
				// 取消赞
				code = "-2";
				getFacade().getVillageDynamicCommentService().removeVillageDynamicComment(zan);

				json.put("code", code);
				json.put("msg", msg);
				json.put("datas", data);
				json.put("del_zan", zan);
				String jsonsring = json.toJSONString();
				logger.info("jsonsring:{}", jsonsring);
				super.renderJson(response, jsonsring);
				return null;

			}
		}

		VillageDynamicComment insertComment = new VillageDynamicComment();
		insertComment.setLink_id(entity_id);
		if (StringUtils.isNotBlank(content)) {
			insertComment.setContent(content);
		}
		insertComment.setLink_user_id(add_user_id);
		insertComment.setLink_user_name(add_user_name);
		insertComment.setComment_type(Integer.valueOf(type));
		insertComment.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		insertComment.setAudit_date(new Date());
		insertComment.setAdd_date(new Date());
		insertComment.setAdd_user_id(ui.getId());
		insertComment.setAdd_user_name(ui.getReal_name());
		insertComment.getMap().put("village_id", village_id);
		insertComment.getMap().put("real_name", ui.getReal_name());
		int i = getFacade().getVillageDynamicCommentService().createVillageDynamicComment(insertComment);

		if (i > 0) {
			msg = "评论成功";
		} else {
			msg = "评论失败";
		}

		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("datas", data);
		json.put("insertComment", insertComment);
		String jsonsring = json.toJSONString();
		super.renderJson(response, jsonsring);
		return null;
	}

	public ActionForward del(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String village_id = (String) dynaBean.get("village_id");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			code = "0";
			msg = "您还未登录，请先登录系统！";
			return returnAjaxData(response, code, msg, data);
		}
		if (!GenericValidator.isInt(village_id)) {
			msg = "参数不正确";
			return returnAjaxData(response, code, msg, data);
		}

		VillageDynamic dynamic = new VillageDynamic();
		dynamic.setId(Integer.valueOf(village_id));
		dynamic = getFacade().getVillageDynamicService().getVillageDynamic(dynamic);
		if (null != dynamic) {
			dynamic = new VillageDynamic();
			dynamic.setId(Integer.valueOf(village_id));
			dynamic.setVillage_id(dynamic.getVillage_id());
			dynamic.setIs_del(1);
			dynamic.setDel_date(new Date());
			dynamic.setDel_user_id(ui.getId());
			dynamic.setDel_user_name(ui.getUser_name());
			int i = getFacade().getVillageDynamicService().modifyVillageDynamic(dynamic);
			if (i < 1) {
				code = "-1";
				msg = "删除失败！请联系管理员";
			}
		}
		code = "1";
		msg = "删除成功！";

		return returnAjaxData(response, code, msg, data);
	}
}
