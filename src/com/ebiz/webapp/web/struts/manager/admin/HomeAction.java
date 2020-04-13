package com.ebiz.webapp.web.struts.manager.admin;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.HelpModule;
import com.ebiz.webapp.domain.SysSetting;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseWebAction;

/**
 * @author Wu,Yang
 * @version 2011-04-22
 */
public class HomeAction extends BaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<BaseLink> base20LinkList = this.getBaseLinkList(20, 9, null);
		request.setAttribute("base20LinkList", base20LinkList);

		List<BaseLink> base70LinkList = this.getBaseLinkList(70, 20, null);// 取后台编辑的楼层
		request.setAttribute("base70LinkList", base70LinkList);

		// 取最上面的导航条
		List<BaseLink> base3000LinkList = this.getBaseLinkList(3000, 6, null);
		request.setAttribute("base3000LinkList", base3000LinkList);

		SysSetting sysSetting = new SysSetting();
		sysSetting.setTitle("isEnabledIndexStatic");
		sysSetting = super.getFacade().getSysSettingService().getSysSetting(sysSetting);
		request.setAttribute("sysSetting", sysSetting);

		request.setAttribute("staticIndex", "true");

		return new ActionForward("/admin/Home/index.jsp");
	}

	public ActionForward genStaticIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
			if (!ctxDir.endsWith(File.separator)) {
				ctxDir = ctxDir + File.separator;
			}
			StringBuffer server = new StringBuffer();
			server.append(request.getHeader("host")).append(request.getContextPath());
			String server_domain = "http://".concat(server.toString());

			Map<String, Object> model_diannao = new HashMap<String, Object>();
			model_diannao.put("ctx", server_domain);

			this.setPublicInfoList(request, model_diannao);

			super.setPublicTitle(model_diannao);

			this.setBaseLinkListToModel(10, 4, "no_null_image_path", model_diannao);

			// 热门搜索
			BaseData baseData = new BaseData();
			baseData.setType(30);
			baseData.setIs_del(0);
			List<BaseData> base30DataList = getFacade().getBaseDataService().getBaseDataList(baseData);
			model_diannao.put("base30DataList", base30DataList);

			super.getCookie(request, model_diannao);// 获取cookie

			// 获取类别
			List<BaseLink> baseLink20List = this.getBaseLinkList(20, 9, null);
			if ((null != baseLink20List) && (baseLink20List.size() > 0)) {
				for (BaseLink bi : baseLink20List) {
					// 查询下面带文字的
					BaseLink blk = new BaseLink();
					blk.setLink_type(Integer.valueOf(30));
					blk.setPre_number(bi.getId());
					blk.setIs_del(0);
					blk.getRow().setCount(2);
					List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);
					bi.getMap().put("baseSmallLinkList", baseLinkList);

					// 查询添加大类所选的二级分类

					List<BaseClass> baseClassLevel3List = new ArrayList<BaseClass>();
					if (null != bi.getContent()) {
						String[] cls_id = StringUtils.splitByWholeSeparator(bi.getContent(), ",");
						if (null != cls_id && cls_id.length > 0) {
							for (int i = 0; i < cls_id.length; i++) {

								BaseClass baseClass = new BaseClass();
								baseClass.setCls_id(Integer.valueOf(cls_id[i]));
								baseClass.setIs_del(0);
								baseClass = super.getFacade().getBaseClassService().getBaseClass(baseClass);
								if (null != baseClass) {
									// 先查询名称
									BaseClass baseClassSon = new BaseClass();
									baseClassSon.setPar_id(Integer.valueOf(cls_id[i]));
									baseClassSon.setIs_del(0);
									List<BaseClass> baseClassSonList = super.getFacade().getBaseClassService()
											.getBaseClassList(baseClassSon);
									if (null != baseClassSonList && baseClassSonList.size() > 0)
										baseClass.getMap().put("baseClassSonList", baseClassSonList);

								}
								baseClassLevel3List.add(baseClass);
							}
						}
					}
					bi.getMap().put("baseClassLevel3List", baseClassLevel3List);
				}

			}
			model_diannao.put("base20LinkList", baseLink20List);

			// 最上面导航
			this.setBaseLinkListToModel(3000, 6, null, model_diannao);

			List<BaseLink> baseLink40List = this.getBaseLinkList(40, 12, "no_null_image_path");

			if (null != baseLink40List && baseLink40List.size() > 0) {
				List<BaseLink> baseLink40ArrayList = new ArrayList<BaseLink>();
				int size = (int) Math.round((Double.valueOf(baseLink40List.size()) / 2));
				for (int i = 0; i < size; i++) {
					BaseLink temp = new BaseLink();
					List<BaseLink> baseLinkList = this.getBaseLinkListWithRowAndFirst(40, 2, i * 2,
							"no_null_image_path");
					temp.getMap().put("base40LinkList", baseLinkList);
					baseLink40ArrayList.add(temp);
				}
				model_diannao.put("baseLink40ArrayList", baseLink40ArrayList);
			}

			this.setBaseLinkListToModel(50, 1, "no_null_image_path", model_diannao);

			this.setBaseLinkListToModel(60, 1, "no_null_image_path", model_diannao);

			List<BaseLink> base70LinkList = this.getBaseLinkList(70, 8, null);
			if ((null != base70LinkList) && (base70LinkList.size() > 0)) {
				int i = 1;
				for (BaseLink temp : base70LinkList) {

					temp.getMap().put("baseLink101List", this.getBaseLinkList(Integer.valueOf(i + "01"), 6, null));

					List<BaseLink> baseLink102List = this.getBaseLinkList(Integer.valueOf(i + "02"), 8,
							"no_null_image_path");
					if ((null != baseLink102List) && (baseLink102List.size() > 0)) {
						for (BaseLink temp2 : baseLink102List) {
							if (null != temp2.getComm_id()) {
								CommentInfo commentInfo = new CommentInfo();
								commentInfo.setLink_id(temp2.getComm_id());
								Integer commentCount = getFacade().getCommentInfoService().getCommentInfoCount(
										commentInfo);
								temp2.getMap().put("commentCount", commentCount);
							}
						}
					}
					temp.getMap().put("baseLink102List", baseLink102List);
					temp.getMap().put("baseLink103List", this.getBaseLinkList(Integer.valueOf(i + "03"), 1, null));
					i++;
				}
			}
			model_diannao.put("base70LinkList", base70LinkList);

			this.setBaseLinkListToModel(80, 9, null, model_diannao);
			this.setBaseLinkListToModel(90, 8, null, model_diannao);
			this.setBaseLinkListToModel(110, 8, null, model_diannao);

			String indexData = getFacade().getTemplateService().getContent("index/index.ftl", model_diannao);
			FileUtils.writeStringToFile(new File(ctxDir.concat("index.shtml")), indexData, "utf-8");
			FileUtils.writeStringToFile(new File(ctxDir.concat("index_static.shtml")), indexData, "utf-8");

			super.renderText(response, "静态化成功");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "静态化失败");
			return null;
		}

	}

	public ActionForward refreshCls(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			BaseLink blk = new BaseLink();
			blk.setLink_type(20);
			blk.setIs_del(0);
			blk.getRow().setCount(16);
			List<BaseLink> baseLink20List = super.getFacade().getBaseLinkService().getBaseLinkList(blk);
			for (BaseLink bi : baseLink20List) {
				if (null != bi.getContent() && GenericValidator.isInt(bi.getContent())) {
					Integer c_id = Integer.valueOf(bi.getContent());
					BaseClass baseClassSon = new BaseClass();
					baseClassSon.setPar_id(c_id);
					baseClassSon.setIs_del(0);
					List<BaseClass> baseClassSonList = super.getFacade().getBaseClassService()
							.getBaseClassList(baseClassSon);
					if (null != baseClassSonList && baseClassSonList.size() > 0) {
						for (BaseClass temp : baseClassSonList) {
							BaseClass baseClassSonSon = new BaseClass();
							baseClassSonSon.setPar_id(temp.getCls_id());
							baseClassSonSon.setIs_del(0);
							List<BaseClass> baseClassSonSonList = super.getFacade().getBaseClassService()
									.getBaseClassList(baseClassSonSon);
							temp.getMap().put("baseClassSonSonList", baseClassSonSonList);
						}
					}
					bi.setBaseClassList(baseClassSonList);
				}
			}

			request.getSession().getServletContext().setAttribute(Keys.slideNavList, baseLink20List);
			super.renderText(response, "更新成功");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			super.renderText(response, "更新失败");
			return null;
		}
	}

	public void setBaseLinkListToModel(Integer link_type, Integer count, String no_null_image_path,
			Map<String, Object> model) {
		List<BaseLink> baseLinkList = new ArrayList<BaseLink>();
		BaseLink baseLink = new BaseLink();
		baseLink.setLink_type(link_type);
		baseLink.setIs_del(0);
		if (null != count) {
			baseLink.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			baseLink.getMap().put("no_null_image_path", "true");
		}

		baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(baseLink);

		model.put("base" + link_type + "LinkList", baseLinkList);
	}

	public List<BaseLink> getBaseLinkList(Integer linkType, Integer count, Integer pre_number, String no_null_image_path) {
		BaseLink blk = new BaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		blk.setPre_number(pre_number);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);
		return baseLinkList;
	}

	public List<BaseLink> getBaseLinkListWithRowAndFirst(Integer linkType, Integer count, Integer first,
			String no_null_image_path) {
		BaseLink blk = new BaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != first) {
			blk.getRow().setFirst(first);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkPaginatedList(blk);
		return baseLinkList;
	}

	@Override
	public List<BaseLink> getBaseLinkList(Integer linkType, Integer count, String no_null_image_path) {
		BaseLink blk = new BaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);
		return baseLinkList;
	}

	public void setPublicInfoList(HttpServletRequest request, Map<String, Object> model)
			throws UnsupportedEncodingException {
		// 帮助中心
		// 1、新手上路
		this.setHelpModuleListByParId(request, 10010000, model);
		// 2、关于支付
		this.setHelpModuleListByParId(request, 10020000, model);
		// 3、关于配送
		this.setHelpModuleListByParId(request, 10030000, model);
		// 4、售后服务
		this.setHelpModuleListByParId(request, 10040000, model);
		// 5、申请合作
		this.setHelpModuleListByParId(request, 10050000, model);
		// 6、关于我们
		this.setHelpModuleListByParId(request, 10060000, model);

	}

	public void setHelpModuleListByParId(HttpServletRequest request, Integer par_id, Map<String, Object> model) {
		if (null != par_id) {
			List<HelpModule> helpModuleList = new ArrayList<HelpModule>();
			HelpModule helpModule = new HelpModule();
			helpModule.setPar_id(par_id);
			helpModuleList = getFacade().getHelpModuleService().getHelpModuleList(helpModule);
			model.put("helpModule" + par_id + "List", helpModuleList);
		}
	}
}
