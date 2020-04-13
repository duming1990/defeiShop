package com.ebiz.webapp.web.struts.manager.admin;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.util.PinyinTools;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BasePayAction;
import com.ebiz.webapp.web.util.HttpUtils;
import com.ebiz.webapp.web.util.ZxingUtils;

/**
 * @author Liu,Jia
 * @version 2016-01-20
 */
public class RepairDataAction extends BasePayAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("list");
	}

	/**
	 * 获取BasePro JSON格式数据 看控制台输出 /ninePorters/manager/admin/RepairData.do?method=getBaseProJson
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBaseProJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray jsonArrayPar = new JSONArray();

		BaseProvince tempPro = new BaseProvince();
		tempPro.setPar_index(0l);
		tempPro.setIs_del(0);
		List<BaseProvince> tempProList = super.getFacade().getBaseProvinceService().getBaseProvinceList(tempPro);

		for (BaseProvince temp : tempProList) {// 省
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", temp.getP_index());
			jsonObject.put("text", temp.getP_name());

			BaseProvince tempCity = new BaseProvince();
			tempCity.setPar_index(temp.getP_index());
			tempCity.setIs_del(0);
			List<BaseProvince> tempCityList = super.getFacade().getBaseProvinceService().getBaseProvinceList(tempCity);

			JSONArray jsonArrayCityChildren = new JSONArray();
			for (BaseProvince temp2 : tempCityList) {// 市
				JSONObject jsonObjectCity = new JSONObject();
				jsonObjectCity.put("value", temp2.getP_index());
				jsonObjectCity.put("text", temp2.getP_name());
				jsonArrayCityChildren.add(jsonObjectCity);

				BaseProvince tempCity2 = new BaseProvince();
				tempCity2.setPar_index(temp2.getP_index());
				tempCity2.setIs_del(0);
				List<BaseProvince> tempCity2List = super.getFacade().getBaseProvinceService()
						.getBaseProvinceList(tempCity2);

				JSONArray jsonArrayCity2Children = new JSONArray();

				for (BaseProvince temp3 : tempCity2List) {// 县
					JSONObject jsonObjectCity2 = new JSONObject();
					jsonObjectCity2.put("value", temp3.getP_index());
					jsonObjectCity2.put("text", temp3.getP_name());
					jsonArrayCity2Children.add(jsonObjectCity2);

					BaseProvince tempCity3 = new BaseProvince();
					tempCity3.setPar_index(temp3.getP_index());
					tempCity3.setIs_del(0);
					List<BaseProvince> tempCity3List = super.getFacade().getBaseProvinceService()
							.getBaseProvinceList(tempCity3);

					JSONArray jsonArrayCity3Children = new JSONArray();
					for (BaseProvince temp4 : tempCity3List) {// 镇
						JSONObject jsonObjectCity3 = new JSONObject();
						jsonObjectCity3.put("value", temp4.getP_index());
						jsonObjectCity3.put("text", temp4.getP_name());
						jsonArrayCity3Children.add(jsonObjectCity3);
					}
					jsonObjectCity2.put("children", jsonArrayCity3Children);
				}
				jsonObjectCity.put("children", jsonArrayCity2Children);
			}

			jsonObject.put("children", jsonArrayCityChildren);
			jsonArrayPar.add(jsonObject);
		}

		logger.info(jsonArrayPar.toString());

		super.renderText(response, "操作成功！");
		return null;
	}

	/**
	 * 修复关系表出现错误的地方 注意的地方 1、需要数据库先把ym_id调整正确 2、确保所有下级的ym_id都正确
	 *
	 * @param
	 * @return
	 * @throws Exception
	 * @desc 用完就注释该方法
	 */

	public ActionForward updateErrorUserRelation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo entityQuery = new UserInfo();
		entityQuery.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
		entityQuery.getMap().put("ym_id_is_null", true);
		List<UserInfo> entityQueryList = super.getFacade().getUserInfoService().getUserInfoList(entityQuery);
		if (null != entityQueryList && entityQueryList.size() > 0) {
			for (UserInfo temp : entityQueryList) {
				UserInfo userInfoUpdate = new UserInfo();
				userInfoUpdate.setId(temp.getId());
				userInfoUpdate.setYmid("admin");
				super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdate);
				super.getFacade().getUserInfoService().updateUserInfoYmIdAndInsertUserRelation(userInfoUpdate);
			}
		}
		super.renderText(response, "操作成功！");
		return null;
	}

	/**
	 * 修复杜明的错误
	 * 
	 * @param 先要查询出有问题的用户 userRelation 和 userRelationPar 表中未插入相关的信息
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateUserInfoYmIdAndInsertUserRelation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 先修复yz的数据
		UserInfo userInfo = new UserInfo();
		userInfo.setIs_village(1);
		userInfo.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
		List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(userInfo);
		if (null != userInfoList && userInfoList.size() > 0) {
			for (UserInfo temp : userInfoList) {
				UserInfo userInfoPar = super.getUserInfo(temp.getYmid());
				if (null != userInfoPar) {
					UserInfo userInfoUpdate = new UserInfo();
					userInfoUpdate.setId(temp.getId());
					userInfoUpdate.setYmid(userInfoPar.getUser_name());
					super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdate);
					// 先更新自己的关系
					super.getFacade().getUserInfoService().updateUserInfoYmIdAndInsertUserRelation(userInfoUpdate);

					// 查找下级
					UserInfo userInfoSon = new UserInfo();
					userInfoSon.setYmid(temp.getUser_name());
					List<UserInfo> userInfoSonList = super.getFacade().getUserInfoService()
							.getUserInfoList(userInfoSon);
					if (null != userInfoSonList && userInfoSonList.size() > 0) {
						for (UserInfo tempSon : userInfoSonList) {
							// UserInfo userInfoUpdateSon = new UserInfo();
							// userInfoUpdateSon.setId(tempSon.getId());
							// userInfoUpdateSon.setYmid(temp.getUser_name());
							// super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdateSon);
							super.getFacade().getUserInfoService().updateUserInfoYmIdAndInsertUserRelation(tempSon);
						}
					}

				}
			}
		}

		// 在修复pu的数据
		UserInfo userInfoPu = new UserInfo();
		userInfoPu.setIs_village(1);
		userInfoPu.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
		List<UserInfo> userInfoPuList = super.getFacade().getUserInfoService().getUserInfoList(userInfoPu);
		if (null != userInfoPuList && userInfoPuList.size() > 0) {
			for (UserInfo temp : userInfoPuList) {
				VillageInfo villageInfo = super.getVillageInfo(temp.getYmid());
				if (null != villageInfo) {

					UserInfo UserInfoVillage = new UserInfo();
					UserInfoVillage.setOwn_village_id(villageInfo.getId());
					UserInfoVillage.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
					UserInfoVillage.setIs_del(0);
					UserInfoVillage = super.getFacade().getUserInfoService().getUserInfo(UserInfoVillage);

					UserInfo userInfoUpdate = new UserInfo();
					userInfoUpdate.setId(temp.getId());
					userInfoUpdate.setYmid(UserInfoVillage.getUser_name());
					super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdate);
					// 先更新自己的关系
					super.getFacade().getUserInfoService().updateUserInfoYmIdAndInsertUserRelation(userInfoUpdate);

					// 查找下级
					UserInfo userInfoSon = new UserInfo();
					userInfoSon.setYmid(temp.getUser_name());
					List<UserInfo> userInfoSonList = super.getFacade().getUserInfoService()
							.getUserInfoList(userInfoSon);
					if (null != userInfoSonList && userInfoSonList.size() > 0) {
						for (UserInfo tempSon : userInfoSonList) {
							super.getFacade().getUserInfoService().updateUserInfoYmIdAndInsertUserRelation(tempSon);
						}
					}

				}
			}
		}

		// 在修复贫困户的数据
		UserInfo userInfoPoor = new UserInfo();
		userInfoPoor.setIs_poor(1);
		userInfoPoor.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
		List<UserInfo> userInfoPoorList = super.getFacade().getUserInfoService().getUserInfoList(userInfoPoor);
		if (null != userInfoPoorList && userInfoPoorList.size() > 0) {
			for (UserInfo temp : userInfoPoorList) {

				PoorInfo poorInfo = new PoorInfo();
				poorInfo.setId(temp.getPoor_id());
				poorInfo = super.getFacade().getPoorInfoService().getPoorInfo(poorInfo);

				if (null != poorInfo) {
					UserInfo UserInfoPoor = new UserInfo();
					UserInfoPoor.setPoor_id(poorInfo.getId());
					UserInfoPoor.setIs_poor(1);
					UserInfoPoor = super.getFacade().getUserInfoService().getUserInfo(UserInfoPoor);

					UserInfo userInfoUpdate = new UserInfo();
					userInfoUpdate.setId(temp.getId());
					userInfoUpdate.setYmid(UserInfoPoor.getUser_name());
					super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdate);
					// 先更新自己的关系
					super.getFacade().getUserInfoService().updateUserInfoYmIdAndInsertUserRelation(userInfoUpdate);

					// 查找下级
					UserInfo userInfoSon = new UserInfo();
					userInfoSon.setYmid(temp.getUser_name());
					List<UserInfo> userInfoSonList = super.getFacade().getUserInfoService()
							.getUserInfoList(userInfoSon);
					if (null != userInfoSonList && userInfoSonList.size() > 0) {
						for (UserInfo tempSon : userInfoSonList) {
							super.getFacade().getUserInfoService().updateUserInfoYmIdAndInsertUserRelation(tempSon);
						}
					}

				}
			}
		}

		super.renderText(response, "操作成功！");
		return null;
	}

	/**
	 * 查询三级下面没有四级的区域
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryThreeLevelNoFourLevel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setP_level(3);
		baseProvince.setIs_del(0);
		List<BaseProvince> tempList = super.getFacade().getBaseProvinceService().getBaseProvinceList(baseProvince);
		for (BaseProvince temp : tempList) {
			BaseProvince tempSon = new BaseProvince();
			tempSon.setPar_index(temp.getP_index());
			tempSon.setIs_del(0);
			int count = super.getFacade().getBaseProvinceService().getBaseProvinceCount(tempSon);
			if (count == 0) {

				// 这个地方需要去生成一个默认区域
				BaseProvince baseProvinceTemp = new BaseProvince();
				baseProvinceTemp.setP_index(Long.valueOf(temp.getP_index() + "001000"));
				baseProvinceTemp.setPar_index(temp.getP_index());
				baseProvinceTemp.setP_level(4);
				baseProvinceTemp.setAdd_date(new Date());
				baseProvinceTemp.setP_name("城区");
				baseProvinceTemp.setS_name("城区");
				baseProvinceTemp.setRoot_code(temp.getRoot_code());
				baseProvinceTemp.setFull_name(temp.getFull_name() + ",城区");
				super.getFacade().getBaseProvinceService().createBaseProvince(baseProvinceTemp);
			}
		}
		super.renderText(response, "操作成功");
		return null;
	}

	/**
	 * 修复村站的二维码信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActionForward createVillageQrCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		VillageInfo entity = new VillageInfo();
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.setIs_del(0);
		List<VillageInfo> entityList = super.getFacade().getVillageInfoService().getVillageInfoList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (VillageInfo temp : entityList) {
				String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
				if (!ctxDir.endsWith(File.separator)) {
					ctxDir = ctxDir + File.separator;
				}
				String ctx = super.getCtxPath(request, false);

				String uploadDir = "files" + File.separator + "village";

				if (!ctxDir.endsWith(File.separator)) {
					ctxDir = ctxDir + File.separator;
				}
				File savePath = new File(ctxDir + uploadDir);
				if (!savePath.exists()) {
					savePath.mkdirs();
				}

				UserInfo userInfo = new UserInfo();
				userInfo.setOwn_village_id(temp.getId());
				userInfo.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
				userInfo.setIs_village(1);
				userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);

				String imgPath = ctxDir + uploadDir + File.separator + temp.getId() + ".png";
				String share_url = ctx + "/m/MVillage.do?method=index&id=" + temp.getId();
				File imgFile = new File(imgPath);
				if (!imgFile.exists()) {
					String waterMarkPath = ctxDir + "styles/imagesPublic/qrCodeWater.png";
					ZxingUtils.encodeQrcode(share_url, imgPath, 400, 400, waterMarkPath);
					VillageInfo villageInfoUpdate = new VillageInfo();
					villageInfoUpdate.setId(temp.getId());
					villageInfoUpdate.setVillage_qrcode(uploadDir + File.separator + temp.getId() + ".png");
					super.getFacade().getVillageInfoService().modifyVillageInfo(villageInfoUpdate);
				}

			}
		}

		super.renderText(response, "操作成功");
		return null;
	}

	/**
	 * 修复商品的二维码信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActionForward createComminfoQrCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CommInfo entity = new CommInfo();
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.setIs_del(0);
		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoList(entity);
		HttpSession session = request.getSession(false);
		String Path = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));// 文件绝对路径
		if (null != entityList && entityList.size() > 0) {
			for (CommInfo temp : entityList) {
				String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
				if (!ctxDir.endsWith(File.separator)) {
					ctxDir = ctxDir + File.separator;
				}
				String ctx = super.getCtxPath(request, false);
				Integer comm_id = temp.getId();
				String LogoFile = Path + "/styles/imagesPublic/user_header.png";
				if (null != temp && StringUtils.isNotBlank(temp.getMain_pic())) {
					File tempFile = new File(Path + temp.getMain_pic());
					if (tempFile.exists()) {
						LogoFile = Path + temp.getMain_pic();// 商品的头像
					}
				}
				String Jump_path = ctx + "/m/MEntpInfo.do?id=" + comm_id;// 二维码跳转的路径
				String name = temp.getComm_name();// 底部添加的文字
				String uploadDir = "files" + File.separator + "commInfo";// 文件夹的名称
				if (!ctxDir.endsWith(File.separator)) {
					ctxDir = ctxDir + File.separator;
				}
				File savePath = new File(ctxDir + uploadDir);
				if (!savePath.exists()) {
					savePath.mkdirs();
				}

				String imgPath = ctxDir + uploadDir + File.separator + comm_id + ".png";
				File imgFile = new File(imgPath);
				if (!imgFile.exists()) {
					super.createQrcode(Path, Jump_path, LogoFile, name, uploadDir, comm_id);
					CommInfo commInfoUpdate = new CommInfo();
					commInfoUpdate.setId(temp.getId());
					commInfoUpdate.setComm_qrcode_path(uploadDir + File.separator + comm_id + ".png");// 数据库储存路径
					super.getFacade().getCommInfoService().modifyCommInfo(commInfoUpdate);
				}
			}
		}
		super.renderText(response, "操作成功");
		return null;
	}

	/**
	 * 修复村商品的二维码信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActionForward createVillageComminfoQrCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		VillageDynamic entity = new VillageDynamic();
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.setType(Keys.DynamicType.dynamic_type_2.getIndex());
		entity.setIs_del(0);
		List<VillageDynamic> entityList = super.getFacade().getVillageDynamicService().getVillageDynamicList(entity);
		HttpSession session = request.getSession(false);
		String Path = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));// 文件绝对路径
		if (null != entityList && entityList.size() > 0) {
			for (VillageDynamic temp : entityList) {
				String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
				if (!ctxDir.endsWith(File.separator)) {
					ctxDir = ctxDir + File.separator;
				}
				String ctx = super.getCtxPath(request, false);
				Integer comm_id = temp.getComm_id();

				CommInfo commInfoQuery = super.getCommInfo(comm_id);
				String LogoFile = Path + "/styles/imagesPublic/user_header.png";
				if (null != commInfoQuery && StringUtils.isNotBlank(commInfoQuery.getMain_pic())) {
					File tempFile = new File(Path + commInfoQuery.getMain_pic());
					if (tempFile.exists()) {
						LogoFile = Path + commInfoQuery.getMain_pic();
					}
				}
				String Jump_path = ctx + "/m/MUserCenter.do?method=MUserCommInfo&id=" + temp.getId();// 二维码跳转的路径
				String name = temp.getComm_name();// 底部添加的文字
				String uploadDir = "files" + File.separator + "commInfo";// 文件夹的名称
				if (!ctxDir.endsWith(File.separator)) {
					ctxDir = ctxDir + File.separator;
				}
				File savePath = new File(ctxDir + uploadDir);
				if (!savePath.exists()) {
					savePath.mkdirs();
				}

				String imgPath = ctxDir + uploadDir + File.separator + comm_id + ".png";
				File imgFile = new File(imgPath);
				if (!imgFile.exists()) {
					super.createQrcode(Path, Jump_path, LogoFile, name, uploadDir, comm_id);
					VillageDynamic villageDynamicUpdate = new VillageDynamic();
					villageDynamicUpdate.setId(temp.getId());
					villageDynamicUpdate.setQrcode_image_path(uploadDir + File.separator + comm_id + ".png");// 数据库储存路径
					super.getFacade().getVillageDynamicService().modifyVillageDynamic(villageDynamicUpdate);
				}
			}
		}

		super.renderText(response, "操作成功");
		return null;
	}

	/**
	 * 修复贫困户的二维码信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActionForward createpoorInfoQrCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PoorInfo entity = new PoorInfo();
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.setIs_del(0);
		List<PoorInfo> entityList = super.getFacade().getPoorInfoService().getPoorInfoList(entity);
		HttpSession session = request.getSession(false);
		String Path = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));// 文件绝对路径
		if (null != entityList && entityList.size() > 0) {
			for (PoorInfo temp : entityList) {
				String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
				if (!ctxDir.endsWith(File.separator)) {
					ctxDir = ctxDir + File.separator;
				}
				String ctx = super.getCtxPath(request, false);
				Integer poorInfo_id = temp.getId();

				PoorInfo poorInfoQuery = new PoorInfo();
				poorInfoQuery.setId(poorInfo_id);
				poorInfoQuery = super.getFacade().getPoorInfoService().getPoorInfo(poorInfoQuery);
				String LogoFile = Path + "/styles/imagesPublic/user_header.png";
				if (null != poorInfoQuery && StringUtils.isNotBlank(poorInfoQuery.getHead_logo())) {
					File tempFile = new File(Path + poorInfoQuery.getHead_logo());
					if (tempFile.exists()) {
						LogoFile = Path + poorInfoQuery.getHead_logo();
					}
				}
				String Jump_path = ctx + "/m/MUserCenter.do?method=index&user_id=" + temp.getUser_id();// 二维码跳转的路径
				String name = poorInfoQuery.getReal_name();// 底部添加的文字
				String uploadDir = "files" + File.separator + "poorInfo";// 文件夹的名称
				if (!ctxDir.endsWith(File.separator)) {
					ctxDir = ctxDir + File.separator;
				}
				File savePath = new File(ctxDir + uploadDir);
				if (!savePath.exists()) {
					savePath.mkdirs();
				}
				String imgPath = ctxDir + uploadDir + File.separator + poorInfo_id + ".png";
				File imgFile = new File(imgPath);
				if (!imgFile.exists()) {
					super.createQrcode(Path, Jump_path, LogoFile, name, uploadDir, poorInfo_id);
					PoorInfo poorInfoUpdate = new PoorInfo();
					poorInfoUpdate.setId(poorInfo_id);
					poorInfoUpdate.setPoor_qrcode(uploadDir + File.separator + poorInfo_id + ".png");// 数据库储存路径
					super.getFacade().getPoorInfoService().modifyPoorInfo(poorInfoUpdate);
				}
			}
		}
		super.renderText(response, "操作成功");
		return null;
	}

	/**
	 * 修复贫困户新建pk用户mobile
	 * 
	 * @param 先要查询出mobile为null的pk用户,判断poorinfo中mobile在user_info是否重复，若不重复更新的pk用户
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePkUserMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 先查询出mobile为null的pk用户
		UserInfo userInfo = new UserInfo();
		userInfo.setIs_poor(1);
		userInfo.setIs_del(0);
		userInfo.getMap().put("user_name_like", "pk");
		List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(userInfo);
		int count = 0;
		if (null != userInfoList && userInfoList.size() > 0) {
			for (UserInfo temp : userInfoList) {
				if (temp.getMobile() == null) {
					PoorInfo pInfo = new PoorInfo();
					pInfo.setId(temp.getPoor_id());
					pInfo = getFacade().getPoorInfoService().getPoorInfo(pInfo);
					if (pInfo != null) {
						UserInfo uInfo = new UserInfo();
						uInfo.setMobile(pInfo.getMobile());
						uInfo.setIs_del(0);
						int repeatCount = getFacade().getUserInfoService().getUserInfoCount(uInfo);
						if (Integer.valueOf(repeatCount) > 0) {
							count += 1;
						} else {
							temp.setMobile(pInfo.getMobile());
							super.getFacade().getUserInfoService().modifyUserInfo(temp);
						}
					}
				}
			}
		}
		super.renderText(response, "操作成功！号码重复的用户个数为：" + count);
		return null;
	}

	/**
	 * 修复用户实名认证图片存入baseImgs
	 * 
	 * @param 先要查询出Img_id_card_zm不为null的用户
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePkUserIdCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = new UserInfo();
		userInfo.setIs_del(0);
		userInfo.getMap().put("user_name_like", "p");
		List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(userInfo);
		if (null != userInfoList && userInfoList.size() > 0) {
			for (UserInfo temp : userInfoList) {

				if (temp.getImg_id_card_zm() != null) {

					List<BaseImgs> imgList = new ArrayList<BaseImgs>();
					BaseImgs img_zm = new BaseImgs();
					img_zm.setLink_id(temp.getId());
					img_zm.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
					img_zm.setFile_path(temp.getImg_id_card_zm());
					imgList.add(img_zm);

					BaseImgs img_fm = new BaseImgs();
					img_fm.setLink_id(temp.getId());
					img_fm.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
					img_fm.setFile_path(temp.getImg_id_card_fm());
					imgList.add(img_fm);

					BaseImgs baseImg = new BaseImgs();
					baseImg.setBaseImgsList(imgList);
					baseImg.getMap().put("remove_user_id", temp.getId());
					super.getFacade().getBaseImgsService().createIdCardBaseImgs(baseImg);
				}
			}
		}
		super.renderText(response, "操作成功！");
		return null;
	}

	/**
	 * 修复订单和用户的货款 商家货款多减了一次会员立减的修复-必须订单完成后修复
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateOrderInfoAndUserInfoWithEntpHuokuanBi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.getMap().put("order_type_in", "10,30");
		orderInfo.getMap().put("xiadan_user_sum_gt", "0");
		orderInfo.getMap().put("judge_entp_huokuan_bi", "true");
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());

		List<OrderInfo> orderInfoList = super.getFacade().getOrderInfoService().getOrderInfoList(orderInfo);
		if (null != orderInfoList && orderInfoList.size() > 0) {
			for (OrderInfo t : orderInfoList) {
				// 待返商家货款
				EntpInfo ei = new EntpInfo();
				ei.setId(t.getEntp_id());
				ei.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				ei = super.getFacade().getEntpInfoService().getEntpInfo(ei);
				if (null != ei) {// 查询商家
					BigDecimal new_entp_huokuan_bi = t.getNo_dis_money().subtract(t.getXiadan_user_sum());
					BigDecimal old_entp_huokuan_bi = t.getEntp_huokuan_bi();
					if (new_entp_huokuan_bi.compareTo(old_entp_huokuan_bi) == 1) {
						// 货款：入
						super.insertUserBiRecord(ei.getAdd_user_id(), null, 1, t.getId(),
								new_entp_huokuan_bi.subtract(old_entp_huokuan_bi), Keys.BiType.BI_TYPE_300.getIndex(),
								Keys.BiGetType.BI_GET_TYPE_X1.getIndex());

						// 更新商家货款和待返货款
						UserInfo update_user = new UserInfo();
						update_user.setId(ei.getAdd_user_id());
						update_user.getMap().put("add_bi_huokuan", new_entp_huokuan_bi.subtract(old_entp_huokuan_bi));
						super.getFacade().getUserInfoService().modifyUserInfo(update_user);

						OrderInfo oi = new OrderInfo();
						oi.setId(t.getId());
						oi.setEntp_huokuan_bi(new_entp_huokuan_bi);
						super.getFacade().getOrderInfoService().modifyOrderInfo(oi);
					}
				}

			}
		}
		super.renderText(response, "操作成功！");
		return null;
	}

	/**
	 * 贫困户自动加入村 修复历史数据
	 * 
	 * @param 先要查询出贫困户,判断是否加入该村，若未加入则添加
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePoorUserAddVillage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 先查询出平困户
		UserInfo userInfo = new UserInfo();
		userInfo.setIs_poor(1);
		userInfo.setIs_del(0);
		List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(userInfo);
		int count = 0;
		if (null != userInfoList && userInfoList.size() > 0) {
			for (UserInfo curUser : userInfoList) {
				PoorInfo pInfo = new PoorInfo();
				pInfo.setId(curUser.getPoor_id());
				pInfo.setIs_del(0);
				pInfo.setAudit_state(1);
				pInfo = getFacade().getPoorInfoService().getPoorInfo(pInfo);
				if (pInfo != null) {
					VillageMember vMember = new VillageMember();
					vMember.setUser_id(curUser.getId());
					vMember.setVillage_id(pInfo.getVillage_id());
					vMember.setIs_del(0);
					vMember = getFacade().getVillageMemberService().getVillageMember(vMember);
					if (vMember == null) {
						VillageMember villageMember = new VillageMember();
						villageMember.setUser_id(curUser.getId());
						villageMember.setVillage_id(pInfo.getVillage_id());
						villageMember.setIs_del(0);
						villageMember.setAdd_user_id(pInfo.getAdd_user_id());
						villageMember.setAdd_user_name(pInfo.getAdd_user_name());
						villageMember.setAdd_date(pInfo.getAudit_date());
						villageMember.setAudit_date(pInfo.getAudit_date());
						villageMember.setAudit_user_id(pInfo.getAudit_user_id());
						villageMember.setAudit_state(1);
						villageMember.setAudit_desc("贫困户审核通过，默认加入该村！");
						if (curUser.getMobile() != null) {
							villageMember.setMobile(curUser.getMobile());
						}
						getFacade().getVillageMemberService().createVillageMember(villageMember);
						count++;
					}
				}
			}
		}
		super.renderText(response, "操作成功！修复贫困户加入该村个数为：" + count);
		return null;
	}

	public ActionForward updateOrderActualMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONArray jsonArray = new JSONArray();
		JSONObject data = new JSONObject();
		int ret = 0;

		BaseData baseData = getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1001.getIndex());

		OrderInfoDetails ods = new OrderInfoDetails();
		ods.getMap().put("actual_money_not_null", true);
		List<OrderInfoDetails> odsList = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsList(ods);
		if (null != odsList && odsList.size() > 0) {
			for (OrderInfoDetails item : odsList) {
				data = new JSONObject();

				CommInfo comm = new CommInfo();
				comm.setId(item.getComm_id());
				comm.setIs_del(0);
				comm = getFacade().getCommInfoService().getCommInfo(comm);
				if (null == comm) {
					continue;
				}
				if (comm.getIs_rebate().intValue() == 0) {
					continue;

				}

				BigDecimal 商品返利 = item.getGood_sum_price().multiply(comm.getRebate_scale().divide(new BigDecimal(100)));
				BigDecimal 立减金额 = 商品返利.multiply(new BigDecimal(baseData.getPre_number2()).divide(new BigDecimal(
						baseData.getPre_number())));

				BigDecimal 实际支付金额 = item.getGood_sum_price().subtract(立减金额);

				item.setActivity_price(item.getActual_money());

				data.put("good_sum_price", item.getGood_sum_price());
				data.put("商品返利", 商品返利);
				data.put("立减金额", 立减金额);
				data.put("原实际支付金额", item.getActual_money());

				item.setActual_money(实际支付金额);

				data.put("新实际支付金额", 实际支付金额);
				jsonArray.add(data);

				getFacade().getOrderInfoDetailsService().modifyOrderInfoDetails(item);

			}
		}

		data.put("jsonArray", jsonArray);
		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	/**
	 * @desc 退款系统修改 order_info 增加 real_pay_money 实际支付金额 不会修改
	 */
	public ActionForward updateRetrunInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONArray jsonArray = new JSONArray();
		JSONObject data = new JSONObject();
		int ret = 0;
		String msg = "";

		OrderReturnInfo orderReturnInfo = new OrderReturnInfo();
		List<OrderReturnInfo> orderReturnInfoList = getFacade().getOrderReturnInfoService().getOrderReturnInfoList(
				orderReturnInfo);
		if (null != orderReturnInfoList && orderReturnInfoList.size() > 0) {
			for (OrderReturnInfo item : orderReturnInfoList) {
				if (null == item.getReturn_no()) {
					item.setReturn_no(super.getReturnNo());
				}
				if (item.getAudit_state().intValue() == Keys.INFO_STATE.INFO_STATE_1.getIndex()) {
					// 平台审核通过
					item.setReturn_money_type(Keys.return_money_type.return_money_type_0.getIndex());
					item.setReturn_bi_dianzi(item.getPrice());
					getFacade().getOrderReturnInfoService().modifyOrderReturnInfo(item);
				}
			}
		}

		data.put("jsonArray", jsonArray);
		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	/**
	 * 修复县域的小程序二维码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward updateServiceQrcodePath(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ServiceCenterInfo centerInfo = new ServiceCenterInfo();
		centerInfo.setIs_del(0);
		centerInfo.setAudit_state(1);
		List<ServiceCenterInfo> serviceCenterInfoList = super.getFacade().getServiceCenterInfoService()
				.getServiceCenterInfoList(centerInfo);
		for (ServiceCenterInfo temp : serviceCenterInfoList) {
			logger.info("=============" + temp.getService_qrcode_path());
			if (null != temp && StringUtils.isBlank(temp.getService_qrcode_path())) {// 已经有的就不在生成了
				Map<String, String> map = new HashMap<String, String>();// 小程序二维码图片
				map.put("path", "pages/country/country");
				map.put("scene", temp.getId().toString());
				String sendGet = HttpUtils.sendGet(Keys.api_url + "/v1/mobile/weixin/getWeixinQrCode", map);
				JSONObject jsonObject = JSONObject.parseObject(sendGet);
				JSONObject url = JSONObject.parseObject(jsonObject.getString("data"));
				String qrcode_path = url.getString("url");
				if (StringUtils.isNotBlank(qrcode_path)) {
					ServiceCenterInfo centerInfoUpdate = new ServiceCenterInfo();
					centerInfoUpdate.setId(temp.getId());
					centerInfoUpdate.setService_qrcode_path(qrcode_path);
					super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(centerInfoUpdate);
				}
			}
		}
		super.renderText(response, "操作成功！");
		return null;
	}

	/**
	 * 修复村站的小程序二维码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward updateVillageQrcodePath(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		VillageInfo villageInfo = new VillageInfo();
		villageInfo.setIs_del(0);
		villageInfo.setAudit_state(1);
		List<VillageInfo> villageInfoList = super.getFacade().getVillageInfoService().getVillageInfoList(villageInfo);
		for (VillageInfo temp : villageInfoList) {
			if (null != temp && StringUtils.isBlank(temp.getVillage_qrcode_path())) {// 已经有的就不在生成了
				Map<String, String> map = new HashMap<String, String>();// 小程序二维码图片
				map.put("path", "pages/village/village");
				map.put("scene", temp.getId().toString());
				String sendGet = HttpUtils.sendGet(Keys.api_url + "/v1/mobile/weixin/getWeixinQrCode", map);
				logger.info("=============jsonObject===============================" + sendGet);
				JSONObject jsonObject = JSONObject.parseObject(sendGet);
				JSONObject url = JSONObject.parseObject(jsonObject.getString("data"));
				String qrcode_path = url.getString("url");
				if (StringUtils.isNotBlank(qrcode_path)) {
					VillageInfo villageInfoUpdate = new VillageInfo();
					villageInfoUpdate.setId(temp.getId());
					villageInfoUpdate.setVillage_qrcode_path(qrcode_path);
					super.getFacade().getVillageInfoService().modifyVillageInfo(villageInfoUpdate);
				}
			}
		}
		super.renderText(response, "操作成功！");
		return null;
	}

	/**
	 * 修复李峰实名认证bug
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateBaseAuditRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo entity = new UserInfo();
		entity.setIs_del(0);
		entity.getMap().put("img_id_card_zm_not_empty", "true");
		List<UserInfo> entityList = super.getFacade().getUserInfoService().getUserInfoList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (UserInfo temp : entityList) {
				// 这个地方判断base_audit_record表是否出现问题
				BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
				baseAuditRecord.setLink_id(temp.getId());
				baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
				int count = super.getFacade().getBaseAuditRecordService().getBaseAuditRecordCount(baseAuditRecord);
				if (count == 0) {
					// 进行修复数据
					BaseAuditRecord entityInsert = new BaseAuditRecord();
					entityInsert.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
					entityInsert.setLink_id(temp.getId());
					entityInsert.setLink_table("USER_INFO");
					entityInsert.setOpt_note(temp.getReal_name());
					entityInsert.setAdd_date(new Date());
					entityInsert.setAdd_user_id(temp.getId());
					entityInsert.setAdd_user_name(temp.getUser_name());
					entityInsert.setAudit_state(0);
					if (temp.getIs_renzheng().intValue() == 1) {
						entityInsert.setAudit_state(1);
						entityInsert.setAudit_user_id(temp.getId());
						entityInsert.setAudit_date(new Date());
						entityInsert.setAudit_note("贫困户审核通过，自动实名认证");
					}
					super.getFacade().getBaseAuditRecordService().createBaseAuditRecord(entityInsert);

				}
			}
		}

		super.renderText(response, "操作成功！");
		return null;
	}

	/**
	 * 修复镇数据首字母大写
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateBaseProPalpha(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String p_level = (String) dynaBean.get("p_level");

		BaseProvince entity = new BaseProvince();
		entity.setIs_del(0);
		entity.setP_level(Integer.valueOf(p_level));
		List<BaseProvince> entityList = super.getFacade().getBaseProvinceService().getBaseProvinceList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (BaseProvince temp : entityList) {
				// 这个地方需要更新Palpha
				BaseProvince entityUpdate = new BaseProvince();
				entityUpdate.setP_index(temp.getP_index());
				entityUpdate.setP_alpha(PinyinTools.cn2FirstSpell(StringUtils.substring(temp.getP_name(), 0, 1))
						.toUpperCase());
				super.getFacade().getBaseProvinceService().modifyBaseProvince(entityUpdate);
			}
		}

		super.renderText(response, "操作成功！");
		return null;
	}

	/**
	 * 合肥市的商品（219）临时设置为扶贫商品，扶贫合肥市所有贫困户（5330）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateCommAndPoorsTemp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/*
		 * 首先，更新商品状态 update comm_info t set t.IS_TEMP = 1 ,t.IS_AID =1 ,t.AID_SCALE = 1 WHERE t.P_INDEX LIKE '3401%' AND
		 * t.OWN_ENTP_ID <> 0 AND t.IS_DEL = 0 AND t.AUDIT_STATE = 1 AND t.COMM_TYPE = 2 AND t.IS_SELL = 1 AND t.IS_AID
		 * = 0 查询商品临时商品数量 SELECT count(t.id) FROM comm_info t WHERE t.IS_TEMP = 1 select max(t.id) from poor_info t
		 * where t.P_INDEX like '3401%' and t.IS_DEL = 0 and t.AUDIT_STATE = 1;
		 */

		DynaBean dynaBean = (DynaBean) form;
		String max_id = (String) dynaBean.get("max_id");
		String count = (String) dynaBean.get("count");
		CommInfo entity = new CommInfo();
		entity.setIs_temp(1);
		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoList(entity);

		if (null != entityList && entityList.size() > 0) {
			for (CommInfo temp : entityList) {
				int i = 1;

				Random random = new Random();

				while (i <= Integer.valueOf(count)) {
					int poor_id = random.nextInt(Integer.valueOf(max_id) + 1 - 543) + 543;
					PoorInfo poorInfo = new PoorInfo();
					poorInfo.setId(poor_id);
					poorInfo.setIs_del(0);
					poorInfo.setAudit_state(1);
					poorInfo.getMap().put("p_index_like", "3401");
					poorInfo = super.getFacade().getPoorInfoService().getPoorInfo(poorInfo);
					if (null != poorInfo) {
						CommInfoPoors commInfoPoors = new CommInfoPoors();
						commInfoPoors.setComm_id(temp.getId());
						commInfoPoors.setPoor_id(poor_id);
						commInfoPoors.setAdd_date(new Date());
						commInfoPoors.setAdd_user_id(1);
						commInfoPoors.setIs_temp(1);
						super.getFacade().getCommInfoPoorsService().createCommInfoPoors(commInfoPoors);
						i++;
					}
				}
			}
		}

		super.renderText(response, "操作成功！");
		return null;
	}

	/**
	 * 修复村商品订单收货地址
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateOrderAddr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		JSONObject data = new JSONObject();

		int i = 0;
		OrderInfo a = new OrderInfo();
		a.setOrder_type(Keys.OrderType.ORDER_TYPE_7.getIndex());
		// a.setOrder_state(Keys.OrderState.ORDER_STATE_0.getIndex());
		List<OrderInfo> list = getFacade().getOrderInfoService().getOrderInfoList(a);
		if (null != list && list.size() > 0) {
			OrderInfo updateOrder = null;
			for (OrderInfo item : list) {
				if (null == item.getShipping_address_id()) {
					continue;
				}

				ShippingAddress b = new ShippingAddress();
				b.setId(item.getShipping_address_id());
				b.setIs_del(0);
				b = getFacade().getShippingAddressService().getShippingAddress(b);
				if (b == null) {
					continue;
				}

				updateOrder = new OrderInfo();
				updateOrder.setId(item.getId());
				updateOrder.setRel_province(b.getRel_province());
				updateOrder.setRel_city(b.getRel_city());
				updateOrder.setRel_region(b.getRel_region());
				updateOrder.setRel_addr(b.getRel_addr());
				updateOrder.setRel_tel(b.getRel_tel());
				i = getFacade().getOrderInfoService().modifyOrderInfo(updateOrder);
				if (i > 0) {
					i++;
				}

			}
		}
		data.put("修改数量：", i);

		return returnAjaxData(response, "1", "操作成功！", data);

	}

	/**
	 * 重新设置合伙人小程序二维码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reSetServiceQrcodePath(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();

		serviceCenterInfo.setIs_del(0);
		serviceCenterInfo.setAudit_state(1);
		serviceCenterInfo.setIs_virtual(0);
		List<ServiceCenterInfo> centerInfoList = super.getFacade().getServiceCenterInfoService()
				.getServiceCenterInfoList(serviceCenterInfo);

		JSONObject data = new JSONObject();
		Integer count = 0;
		for (ServiceCenterInfo sci : centerInfoList) {
			// 已经有的就不在生成了
			Map<String, String> map = new HashMap<String, String>();// 小程序二维码图片
			map.put("path", "pages/country/country");
			map.put("scene", sci.getId().toString());
			String sendGet = HttpUtils.sendGet(Keys.api_url + "/v1/mobile/weixin/getWeixinQrCode", map);
			JSONObject jsonObject = JSONObject.parseObject(sendGet);
			JSONObject url = JSONObject.parseObject(jsonObject.getString("data"));
			String qrcode_path = url.getString("url");
			if (StringUtils.isNotBlank(qrcode_path)) {
				ServiceCenterInfo centerInfoUpdate = new ServiceCenterInfo();
				centerInfoUpdate.setId(sci.getId());
				centerInfoUpdate.setService_qrcode_path(qrcode_path);
				int i = super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(centerInfoUpdate);
				count += i;
			}

		}
		data.put("修改数量：", count);
		return returnAjaxData(response, "1", "操作成功！", data);
	}
}