package com.ebiz.webapp.web.struts.m;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseComminfoTags;
import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author 王志雄
 * @date 2018年1月22日
 */
public class IndexAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("footerFlag", 0);
		request.setAttribute("ShareIndexDesc", super.getSysSetting(Keys.ShareIndexDesc));
		request.setAttribute("index_show_pt", super.getSysSetting(Keys.INDEX_SHOW_PT));
		request.setAttribute("index_show_ys", super.getSysSetting(Keys.INDEX_SHOW_YS));
		if (super.isWeixin(request)) {
			super.setJsApiTicketRetrunParamToSession(request);
		}
		return mapping.findForward("success");
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "1", msg = "";
		JSONObject data = new JSONObject();

		// 轮播图
		List<MBaseLink> mBaseLinkList10 = this.getMBaseLinkList(10, 4, "true");
		data.put("mBaseLinkList10", mBaseLinkList10);
		// 导航
		List<MBaseLink> mBaseLinkList20 = this.getMBaseLinkList(20, 10, "true");
		data.put("mBaseLinkList20", mBaseLinkList20);
		// 楼层
		List<MBaseLink> mBaseLinkList30 = this.getMBaseLinkList(30, 20, "true");// 取后台编辑的楼层
		if ((null != mBaseLinkList30) && (mBaseLinkList30.size() > 0)) {
			for (MBaseLink temp : mBaseLinkList30) {
				if (temp.getPre_number() == 1) {
					temp.getMap().put("mBaseLinkList101",
							this.getMBaseLinkListWithParId(temp.getId(), 1, null, 5, "no_null_image_path"));
				}
				if (temp.getPre_number() == 2) {
					temp.getMap().put("mBaseLinkList201",
							this.getMBaseLinkListWithParId(temp.getId(), 1, null, 1, "no_null_image_path"));
					temp.getMap().put("mBaseLinkList202",
							this.getMBaseLinkListWithParId(temp.getId(), 2, null, 2, "no_null_image_path"));
				}

				if (temp.getPre_number() == 3) {
					temp.getMap().put("mBaseLinkList301",
							this.getMBaseLinkListWithParId(temp.getId(), 1, 0, 3, "no_null_image_path"));
					temp.getMap().put("mBaseLinkList302",
							this.getMBaseLinkListWithParId(temp.getId(), 1, 3, 3, "no_null_image_path"));
					temp.getMap().put("mBaseLinkList303",
							this.getMBaseLinkListWithParId(temp.getId(), 1, 6, 3, "no_null_image_path"));
					temp.getMap().put("mBaseLinkList304",
							this.getMBaseLinkListWithParId(temp.getId(), 4, null, 1, "no_null_image_path"));
				}
				if (temp.getPre_number() == 4) {
					temp.getMap().put("mBaseLinkList401",
							this.getMBaseLinkListWithParId(temp.getId(), 1, null, 1, "no_null_image_path"));
					temp.getMap().put("mBaseLinkList402",
							this.getMBaseLinkListWithParId(temp.getId(), 2, null, 1, "no_null_image_path"));
					temp.getMap().put("mBaseLinkList403",
							this.getMBaseLinkListWithParId(temp.getId(), 3, null, 1, "no_null_image_path"));
				}
				if (temp.getPre_number() == 5) {
					temp.getMap().put("mBaseLinkList501",
							this.getMBaseLinkListWithParId(temp.getId(), 1, null, 1, "no_null_image_path"));
					temp.getMap().put("mBaseLinkList502",
							this.getMBaseLinkListWithParId(temp.getId(), 2, null, 1, "no_null_image_path"));
					temp.getMap().put("mBaseLinkList503",
							this.getMBaseLinkListWithParId(temp.getId(), 3, null, 1, "no_null_image_path"));
				}
				if (temp.getPre_number() == 6) {
					temp.getMap().put("mBaseLinkList601",
							this.getMBaseLinkListWithParId(temp.getId(), 1, null, 1, "no_null_image_path"));
					temp.getMap().put("mBaseLinkList602",
							this.getMBaseLinkListWithParId(temp.getId(), 2, 0, 3, "no_null_image_path"));
					temp.getMap().put("mBaseLinkList603",
							this.getMBaseLinkListWithParId(temp.getId(), 2, 3, 3, "no_null_image_path"));
					temp.getMap().put("mBaseLinkList604",
							this.getMBaseLinkListWithParId(temp.getId(), 2, 6, 3, "no_null_image_path"));
				}
			}
		}
		data.put("mBaseLinkList30", mBaseLinkList30);

		// 楼层
		List<MBaseLink> mBaseLinkList60 = this.getMBaseLinkList(60, 1, "true");// 取后台编辑的楼层
		data.put("mBaseLinkList60", mBaseLinkList60);

		// 这个地方获取频道
		BaseComminfoTags baseComminfoTags = new BaseComminfoTags();
		baseComminfoTags.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		baseComminfoTags.setP_index(0);
		List<BaseComminfoTags> baseComminfoTagsList = super.getFacade().getBaseComminfoTagsService()
				.getBaseComminfoTagsList(baseComminfoTags);
		data.put("baseComminfoTagsList", baseComminfoTagsList);

		BaseComminfoTags baseComminfoTagsPro = new BaseComminfoTags();
		baseComminfoTagsPro.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		baseComminfoTagsPro.getMap().put("p_index_ne", 0);
		List<BaseComminfoTags> baseComminfoTagsProList = super.getFacade().getBaseComminfoTagsService()
				.getBaseComminfoTagsList(baseComminfoTagsPro);
		data.put("baseComminfoTagsProList", baseComminfoTagsProList);

		data.put("isWeixin", super.isWeixin(request));
		data.put("upLevelNeedPayMoney", super.getSysSetting(Keys.upLevelNeedPayMoney));

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != ui) {
			data.put("userInfo", super.getUserInfo(ui.getId()));
		} else {
			data.put("userInfo", null);
		}

		super.returnInfo(response, code, msg, data);
		return null;
	}
}
