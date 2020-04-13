package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;
import java.util.List;

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

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.StringHelper;

/**
 * @author Wang,Wei
 * @version 2011-12-01
 */
public class BasePdClassAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String cls_scope = (String) dynaBean.get("cls_scope");
		if (StringUtils.isBlank(cls_scope)) {
			cls_scope = "1";
		}

		String clazzTree = StringHelper.getTreeNodesFromBaseClassList(getFacade(), Integer.valueOf(cls_scope), false);
		request.setAttribute("clazzTree", clazzTree);

		return mapping.findForward("list");

	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String pd_class_type = (String) dynaBean.get("pd_class_type");
		dynaBean.set("pd_class_type", pd_class_type);
		dynaBean.set("order_value", "0");

		return mapping.findForward("input");
	}

	public ActionForward updateClsCodeMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		this.updateClsCode();

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String cls_id = (String) dynaBean.get("cls_id");
		String cls_scope = (String) dynaBean.get("cls_scope");
		if (StringUtils.isBlank(cls_scope)) {
			String msg = "参数cls_scope为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}
		Date date = new Date();
		BaseClass entity = new BaseClass();

		super.copyProperties(entity, form);

		Integer root_id = Integer.valueOf(cls_scope) * -1;
		int cls_scope_int = Integer.valueOf(cls_scope);

		entity.setRoot_id(root_id);

		if (null != entity.getPar_id()) {// 根据par_id获取root_id
			BaseClass baseClass = new BaseClass();
			baseClass.setCls_scope(cls_scope_int);
			baseClass.setCls_id(entity.getPar_id());
			List<BaseClass> baseClassList = super.getFacade().getBaseClassService()
					.proGetBaseClassParentList(baseClass);
			if ((null != baseClassList) && (baseClassList.size() > 0)) {
				for (BaseClass bc : baseClassList) {
					if (bc.getCls_level() == 1) {
						entity.setRoot_id(bc.getCls_id());
					}
				}
			}
		}

		if (StringUtils.isNotBlank(cls_id)) {
			entity.setUpdate_date(date);
			entity.setUpdate_user_id(ui.getId());
			super.getFacade().getBaseClassService().modifyBaseClass(entity);
			saveMessage(request, "entity.updated");
		} else {
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(ui.getId());
			entity.setAdd_user_name(ui.getUser_name());
			entity.setIs_del(0);// 0：未删除
			entity.setCls_scope(cls_scope_int);
			super.getFacade().getBaseClassService().createBaseClass(entity);
			saveMessage(request, "entity.inerted");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&cls_scope=" + cls_scope);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward saveAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String cls_scope = (String) dynaBean.get("cls_scope");
		if (StringUtils.isBlank(cls_scope)) {
			String msg = "参数cls_scope为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}
		BaseClass entity = new BaseClass();

		super.copyProperties(entity, form);

		Integer root_id = Integer.valueOf(cls_scope) * -1;
		int cls_scope_int = Integer.valueOf(cls_scope);

		entity.setRoot_id(root_id);

		if (null != entity.getPar_id()) {// 根据par_id获取root_id
			BaseClass baseClass = new BaseClass();
			baseClass.setCls_scope(cls_scope_int);
			baseClass.setCls_id(entity.getPar_id());
			List<BaseClass> baseClassList = super.getFacade().getBaseClassService()
					.proGetBaseClassParentList(baseClass);
			if ((null != baseClassList) && (baseClassList.size() > 0)) {
				for (BaseClass bc : baseClassList) {
					if (bc.getCls_level() == 1) {
						entity.setRoot_id(bc.getCls_id());
					}
				}
			}
		}

		entity.setAdd_date(new Date());
		entity.setAdd_user_id(ui.getId());
		entity.setAdd_user_name(ui.getUser_name());
		entity.setIs_del(0);// 0：未删除
		entity.setCls_scope(cls_scope_int);
		super.getFacade().getBaseClassService().createBaseClass(entity);

		super.renderText(response, "成功");
		// end
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String cls_id = (String) dynaBean.get("cls_id");
		String pksString = (String) dynaBean.get("pksString");
		String mod_id = (String) dynaBean.get("mod_id");
		String pd_class_type = (String) dynaBean.get("pd_class_type");

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		BaseClass entity = new BaseClass();
		Date date = new Date();

		String[] pks = null;
		if (StringUtils.isNotBlank(pksString)) {
			pks = StringUtils.split(pksString, ",");
		}

		if (StringUtils.isNotBlank(cls_id) && GenericValidator.isLong(cls_id)) {
			entity.setCls_id(Integer.valueOf(cls_id));
			entity.setIs_del(1);
			entity.setDel_date(date);
			entity.setDel_user_id(ui.getId());
			super.getFacade().getBaseClassService().modifyBaseClass(entity);
			saveMessage(request, "entity.deleted");
		} else if (ArrayUtils.isNotEmpty(pks)) {
			entity.setIs_del(1);
			entity.setDel_date(date);
			entity.setDel_user_id(ui.getId());
			entity.getMap().put("pks", pks);
			super.getFacade().getBaseClassService().modifyBaseClass(entity);

			saveMessage(request, "entity.deleted");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&pd_class_type=" + pd_class_type);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String cls_id = (String) dynaBean.get("cls_id");

		if (GenericValidator.isLong(cls_id)) {
			BaseClass entity = new BaseClass();
			entity.setCls_id(Integer.valueOf(cls_id));
			entity = getFacade().getBaseClassService().getBaseClass(entity);

			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			entity.setQueryString(super.serialize(request, "id", "method"));
			super.copyProperties(form, entity);

			setParNameAndRootNameToForm(dynaBean, entity);

			return mapping.findForward("input");
		} else {
			saveError(request, "errors.long", cls_id);
			return mapping.findForward("list");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String cls_id = (String) dynaBean.get("cls_id");
		if (GenericValidator.isLong(cls_id)) {
			BaseClass entity = new BaseClass();
			entity.setCls_id(Integer.valueOf(cls_id));
			entity = getFacade().getBaseClassService().getBaseClass(entity);
			if (entity != null) {
				if (entity.getDel_user_id() != null) {
					UserInfo ui = new UserInfo();
					ui.setId(entity.getDel_user_id());
					ui = super.getFacade().getUserInfoService().getUserInfo(ui);
					if (ui != null) {
						entity.getMap().put("del_name", ui.getUser_name());
					}
				}
			}
			super.copyProperties(form, entity);

			setParNameAndRootNameToForm(dynaBean, entity);

			return mapping.findForward("view");
		} else {
			saveError(request, "errors.long", cls_id);
			return mapping.findForward("list");
		}
	}

	private void setParNameAndRootNameToForm(DynaBean dynaBean, BaseClass entity) {
		Integer par_id = entity.getPar_id();
		Integer root_id = entity.getRoot_id();
		BaseClass baseClass = new BaseClass();
		baseClass.setCls_id(par_id);
		baseClass = getFacade().getBaseClassService().getBaseClass(baseClass);
		if (null != baseClass) {
			dynaBean.set("par_name", baseClass.getCls_name());
		}
		baseClass = new BaseClass();
		baseClass.setIs_del(0);
		baseClass.setCls_id(root_id);
		baseClass = getFacade().getBaseClassService().getBaseClass(baseClass);
		if (null != baseClass) {
			dynaBean.set("root_name", baseClass.getCls_name());
		}
	}

	public ActionForward getParClsInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String cls_scope = (String) dynaBean.get("cls_scope");
		String noSelectFar = (String) dynaBean.get("noSelectFar");

		if (StringUtils.isBlank(cls_scope)) {// 根目录par_id为-1
			cls_scope = "1";
		}
		String clazzTree;
		if (StringUtils.isNotBlank(noSelectFar)) {
			clazzTree = StringHelper
					.getTreeNodesFromBaseClassList(getFacade(), Integer.valueOf(cls_scope), false, true);
		} else {
			clazzTree = StringHelper.getTreeNodesFromBaseClassList(getFacade(), Integer.valueOf(cls_scope), true);
		}
		request.setAttribute("clazzTree", clazzTree);
		return new ActionForward("/../manager/admin/BasePdClass/list_pd.jsp");
	}

	public ActionForward getRootClsInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String cls_id = (String) dynaBean.get("cls_id");
		String cls_scope = (String) dynaBean.get("cls_scope");
		if (StringUtils.isNotBlank(cls_id)) {
			BaseClass entity = new BaseClass();
			entity.setIs_del(0);
			entity.setCls_id(Integer.valueOf(cls_id));
			entity.setCls_scope(1);
			if (StringUtils.isNotBlank(cls_scope))
				entity.setCls_scope(Integer.valueOf(cls_scope));

			List<BaseClass> list = super.getFacade().getBaseClassService().proGetBaseClassParentList(entity);// 子查父降序排列的列表
			JSONObject result = new JSONObject();
			String pd_no = "";
			BaseClass basePdClazz = new BaseClass();
			basePdClazz.setCls_id(Integer.valueOf(cls_id));
			logger.info("-------------------------->" + cls_id);
			basePdClazz.setIs_del(0);
			basePdClazz = super.getFacade().getBaseClassService().getBaseClass(basePdClazz);
			if (null != basePdClazz) {
				CommInfo commInfo = new CommInfo();
				Integer count = super.getFacade().getCommInfoService().getCommInfoCount(commInfo);
				if (null == basePdClazz.getCls_code()) {

					Integer step_2 = 1;
					BaseClass basePdClazz2 = new BaseClass();
					basePdClazz2.setCls_level(2);
					List<BaseClass> basePdClazz2List = getFacade().getBaseClassService().getBaseClassList(basePdClazz2);
					for (BaseClass bp2 : basePdClazz2List) {

						String level_1 = StringUtils.leftPad(String.valueOf(step_2++), 2, "0");
						String level_2 = "00";
						String level_3 = "000";
						String clscode2 = level_1.concat(level_2).concat(level_3);

						BaseClass tmp_update_2 = new BaseClass();
						tmp_update_2.setCls_id(bp2.getCls_id());
						tmp_update_2.setCls_code(clscode2);
						getFacade().getBaseClassService().modifyBaseClass(tmp_update_2);

						BaseClass basePdClazz3 = new BaseClass();
						basePdClazz3.setCls_level(3);
						basePdClazz3.setPar_id(tmp_update_2.getCls_id());
						List<BaseClass> basePdClazz3List = getFacade().getBaseClassService().getBaseClassList(
								basePdClazz3);
						Integer step_3 = 1;
						for (BaseClass bp3 : basePdClazz3List) {
							level_1 = StringUtils.substring(clscode2, 0, 2);
							level_2 = StringUtils.leftPad(String.valueOf(step_3++), 2, "0");
							level_3 = "000";

							String clscode3 = level_1.concat(level_2).concat(level_3);
							BaseClass tmp_update_3 = new BaseClass();
							tmp_update_3.setCls_id(bp3.getCls_id());
							tmp_update_3.setCls_code(clscode3);
							getFacade().getBaseClassService().modifyBaseClass(tmp_update_3);

							BaseClass basePdClazz4 = new BaseClass();
							basePdClazz4.setCls_level(4);
							basePdClazz4.setPar_id(tmp_update_3.getCls_id());
							List<BaseClass> basePdClazz4List = getFacade().getBaseClassService().getBaseClassList(
									basePdClazz4);
							Integer step_4 = 1;
							for (BaseClass bp4 : basePdClazz4List) {
								level_1 = StringUtils.substring(clscode3, 0, 2);
								level_2 = StringUtils.substring(clscode3, 2, 4);
								level_3 = StringUtils.leftPad(String.valueOf(step_4++), 3, "0");

								String clscode4 = level_1.concat(level_2).concat(level_3);
								BaseClass tmp_update_4 = new BaseClass();
								tmp_update_4.setCls_id(bp4.getCls_id());
								tmp_update_4.setCls_code(clscode4);
								getFacade().getBaseClassService().modifyBaseClass(tmp_update_4);
							}

						}
					}
					BaseClass basePdClazzHasClsClod = new BaseClass();
					basePdClazzHasClsClod.setCls_id(Integer.valueOf(cls_id));
					basePdClazzHasClsClod.setIs_del(0);
					basePdClazzHasClsClod = super.getFacade().getBaseClassService().getBaseClass(basePdClazzHasClsClod);
					if (null != basePdClazzHasClsClod) {
						pd_no = "YS" + basePdClazzHasClsClod.getCls_code()
								+ StringUtils.leftPad(String.valueOf(count), 4, "0");
						if (StringUtils.isNotBlank(cls_scope))
							pd_no = "FL" + basePdClazzHasClsClod.getCls_code()
									+ StringUtils.leftPad(String.valueOf(count), 4, "0");
					}
				} else {
					pd_no = "YS" + basePdClazz.getCls_code() + StringUtils.leftPad(String.valueOf(count), 4, "0");
					if (StringUtils.isNotBlank(cls_scope))
						pd_no = "FL" + basePdClazz.getCls_code() + StringUtils.leftPad(String.valueOf(count), 4, "0");
				}
				result.put("pd_no", pd_no);
			}

			if ((list != null) && (list.size() > 1)) {
				BaseClass temp = list.get(1);// 去根类别，即是约定的第二级
				result.put("root_id", temp.getCls_id());
				result.put("root_name", temp.getCls_name());
				super.render(response, result.toString(), "text/x-json;charset=UTF-8");
			} else if ((list != null) && (list.size() == 1)) {
				BaseClass BaseClass = new BaseClass();// 如果是有一个数据则取自己
				BaseClass.setIs_del(0);
				BaseClass.setCls_id(Integer.valueOf(cls_id));
				BaseClass = getFacade().getBaseClassService().getBaseClass(BaseClass);
				if (null != BaseClass) {
					result.put("root_id", BaseClass.getCls_id());
					result.put("root_name", BaseClass.getCls_name());
				}
				super.render(response, result.toString(), "text/x-json;charset=UTF-8");
			}
			return null;
		}
		return null;
	}

	public void updateClsCode() throws Exception {

		Integer step_2 = 1;

		BaseClass baseClass2 = new BaseClass();
		baseClass2.setCls_level(2);
		List<BaseClass> baseClass2List = getFacade().getBaseClassService().getBaseClassList(baseClass2);
		for (BaseClass bp2 : baseClass2List) {

			String level_1 = StringUtils.leftPad(String.valueOf(step_2++), 2, "0");
			String level_2 = "00";
			String level_3 = "000";
			String clscode2 = level_1.concat(level_2).concat(level_3);

			BaseClass tmp_update_2 = new BaseClass();
			tmp_update_2.setCls_id(bp2.getCls_id());
			tmp_update_2.setCls_code(clscode2);
			getFacade().getBaseClassService().modifyBaseClass(tmp_update_2);

			BaseClass baseClass3 = new BaseClass();
			baseClass3.setCls_level(3);
			baseClass3.setPar_id(tmp_update_2.getCls_id());
			List<BaseClass> baseClass3List = getFacade().getBaseClassService().getBaseClassList(baseClass3);
			Integer step_3 = 1;
			for (BaseClass bp3 : baseClass3List) {
				level_1 = StringUtils.substring(clscode2, 0, 2);
				level_2 = StringUtils.leftPad(String.valueOf(step_3++), 2, "0");
				level_3 = "000";

				String clscode3 = level_1.concat(level_2).concat(level_3);
				BaseClass tmp_update_3 = new BaseClass();
				tmp_update_3.setCls_id(bp3.getCls_id());
				tmp_update_3.setCls_code(clscode3);
				getFacade().getBaseClassService().modifyBaseClass(tmp_update_3);

				BaseClass baseClass4 = new BaseClass();
				baseClass4.setCls_level(4);
				baseClass4.setPar_id(tmp_update_3.getCls_id());
				List<BaseClass> baseClass4List = getFacade().getBaseClassService().getBaseClassList(baseClass4);
				Integer step_4 = 1;
				for (BaseClass bp4 : baseClass4List) {
					level_1 = StringUtils.substring(clscode3, 0, 2);
					level_2 = StringUtils.substring(clscode3, 2, 4);
					level_3 = StringUtils.leftPad(String.valueOf(step_4++), 3, "0");

					String clscode4 = level_1.concat(level_2).concat(level_3);
					BaseClass tmp_update_4 = new BaseClass();
					tmp_update_4.setCls_id(bp4.getCls_id());
					tmp_update_4.setCls_code(clscode4);
					getFacade().getBaseClassService().modifyBaseClass(tmp_update_4);
				}

			}
		}
	}
}