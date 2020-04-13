package com.ebiz.webapp.web.struts.manager.admin;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.SensitiveWord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.util.FileTools;

/**
 * @author Liu,Zhixiang
 * @version 2011-04-22
 */
public class SensitiveWordAction extends BaseAdminAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String content_like = (String) dynaBean.get("content_like");

		SensitiveWord entity = new SensitiveWord();
		super.copyProperties(entity, dynaBean);

		entity.getMap().put("content_like", content_like);
		Integer recordCount = getFacade().getSensitiveWordService()
				.getSensitiveWordCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(),
				pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<SensitiveWord> list = getFacade().getSensitiveWordService()
				.getSensitiveWordPaginatedList(entity);
		request.setAttribute("entityList", list);
		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (!GenericValidator.isLong(id)) {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
		SensitiveWord entity = new SensitiveWord();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getSensitiveWordService().getSensitiveWord(entity);
		super.copyProperties(form, entity);
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setNaviStringToRequestScope(request);
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		UserInfo sessionUi = super.getUserInfoFromSession(request);

		SensitiveWord entity = new SensitiveWord();
		super.copyProperties(entity, dynaBean);

		if (null != entity.getId()) {// update
			getFacade().getSensitiveWordService().modifySensitiveWord(entity);
			saveMessage(request, "entity.updated");
		} else {// insert
			getFacade().getSensitiveWordService().createSensitiveWord(entity);
			// 敏感词库
			String file_path = request.getSession().getServletContext()
					.getRealPath(String.valueOf(File.separatorChar))
					+ "wd.txt";
			
			FileTools.string2FileLast(entity.getContent(),file_path);
			
//			FileWriter fw = null;
//			try {
//				// 如果文件存在，则追加内容；如果文件不存在，则创建文件
//				File f = new File(file_path);
//				fw = new FileWriter(f, true);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			PrintWriter pw = new PrintWriter(fw);
//			pw.println(entity.getContent());
//			pw.flush();
//			try {
//				fw.flush();
//				pw.close();
//				fw.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			saveMessage(request, "entity.inerted");
		}

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity
				.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "4")) {
			return super.checkPopedomInvalid(request, response);
		}

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		UserInfo sessionUi = super.getUserInfoFromSession(request);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			SensitiveWord sensitiveWord = new SensitiveWord();
			sensitiveWord.setId(new Integer(id));
			sensitiveWord = getFacade().getSensitiveWordService().getSensitiveWord(sensitiveWord);
			
			SensitiveWord entity = new SensitiveWord();
			entity.setId(new Integer(id));
			getFacade().getSensitiveWordService().removeSensitiveWord(entity);
			// 敏感词库
			String file_path = request.getSession().getServletContext()
					.getRealPath(String.valueOf(File.separatorChar))
					+ "wd.txt";
			
			String srcStr = sensitiveWord.getContent();
			String replaceStr = "";
			FileTools.replaceFileContent(srcStr,replaceStr,file_path);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			// 敏感词库
			String file_path = request.getSession().getServletContext()
					.getRealPath(String.valueOf(File.separatorChar))
					+ "wd.txt";
			for(String word_id : pks){
				SensitiveWord sensitiveWord = new SensitiveWord();
				sensitiveWord.setId(new Integer(word_id));
				sensitiveWord = getFacade().getSensitiveWordService().getSensitiveWord(sensitiveWord);
				
				String srcStr = sensitiveWord.getContent();
				String replaceStr = "";
				FileTools.replaceFileContent(srcStr,replaceStr,file_path);
			}
			
			SensitiveWord entity = new SensitiveWord();
			entity.getMap().put("pks", pks);
			getFacade().getSensitiveWordService().removeSensitiveWord(entity);
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(
				request, "id", "ids", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			SensitiveWord entity = new SensitiveWord();
			entity.setId(new Integer(id));
			entity = getFacade().getSensitiveWordService().getSensitiveWord(
					entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, entity);
			return mapping.findForward("view");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}
}
