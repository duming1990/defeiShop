package com.ebiz.webapp.web.struts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.FormFile;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.BaseSsiAction;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.ssi.web.struts.bean.UploadFile;
import com.ebiz.webapp.domain.Activity;
import com.ebiz.webapp.domain.ActivityApply;
import com.ebiz.webapp.domain.BaseBrandInfo;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.BasePopedom;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPromotion;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.CommentInfoSon;
import com.ebiz.webapp.domain.EntpDuiZhang;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.FreightDetail;
import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.domain.ModPopedom;
import com.ebiz.webapp.domain.Msg;
import com.ebiz.webapp.domain.MsgReceiver;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnAudit;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.OrderReturnMsg;
import com.ebiz.webapp.domain.PdImgs;
import com.ebiz.webapp.domain.Point;
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.RoleUser;
import com.ebiz.webapp.domain.RwHbRule;
import com.ebiz.webapp.domain.RwYhqInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.SysModule;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.domain.SysSetting;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserScoreRecord;
import com.ebiz.webapp.domain.VillageContactList;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.domain.VillageDynamicComment;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.domain.WlCompMatflow;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.domain.YhqInfo;
import com.ebiz.webapp.domain.YhqInfoSon;
import com.ebiz.webapp.domain.YhqLink;
import com.ebiz.webapp.service.Facade;
import com.ebiz.webapp.util.CodeCreator;
import com.ebiz.webapp.util.CodeModel;
import com.ebiz.webapp.util.CoordinateConversion;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.BiGetType;
import com.ebiz.webapp.web.Keys.CommType;
import com.ebiz.webapp.web.Keys.SysOperType;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.EncryptUtilsV2;
import com.ebiz.webapp.web.util.FtpImageUtils;
import com.ebiz.webapp.web.util.FtpUtils;
import com.ebiz.webapp.web.util.HttpUtils;
import com.ebiz.webapp.web.util.StringHelper;

/**
 * @author Wu, Yang
 * @version 2011-04-20
 */
public abstract class BaseAction extends BaseSsiAction {

	public static DecimalFormat dfFormat = new DecimalFormat("0.00");

	public static DecimalFormat dfFormat0 = new DecimalFormat("0");

	public static SimpleDateFormat sdFormat_ymd = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat sdFormatymd = new SimpleDateFormat("yyyyMMdd");

	public static SimpleDateFormat ymdhms = new SimpleDateFormat("HH:mm:ss");

	public static SimpleDateFormat sdFormat_ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat sdFormat_ymdhms_china = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

	public static SimpleDateFormat sdFormatymdhms = new SimpleDateFormat("yyyyMMddHHmmss");

	public SimpleDateFormat sdFormatymdhmsS = new SimpleDateFormat("yyyyMMddhhmmssSSS");

	public SimpleDateFormat sdFormatymdForNo = new SimpleDateFormat("yyMMdd");

	private Facade facade;

	/**
	 * the powerful method to return facade with all services(method)
	 *
	 * @return facade
	 */
	protected Facade getFacade() {
		return this.facade;
	}

	@Override
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		Assert.notNull(actionServlet, "actionServlet is can not be null");
		ServletContext servletContext = actionServlet.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		this.facade = (Facade) wac.getBean("facade");
	}

	/**
	 * upload files
	 *
	 * @param form ActionForm
	 * @param uploadDir default is file/upload/
	 * @param isResizeImage default is false
	 * @param iswaterMark 是否添加水印
	 * @param resizeVersions
	 * @return
	 * @throws Exception
	 */
	protected List<UploadFile> uploadFile(ActionForm form, boolean isResizeImage, boolean isWaterMark,
			int... resizeVersions) throws Exception {
		return uploadFile(form, null, isResizeImage, isWaterMark, resizeVersions);
	}

	protected List<UploadFile> uploadFile(ActionForm form, String uploadDir, boolean isResizeImage,
			boolean isWaterMark, int... resizeVersions) throws Exception {
		if (StringUtils.isBlank(uploadDir)) {
			uploadDir = StringUtils.join(new String[] { "files", "upload", "" }, File.separator);
		}

		String[] folderPatterns = new String[] { "yyyy", "MM", "dd", "" };
		String autoCreatedDateDir = DateFormatUtils
				.format(new Date(), StringUtils.join(folderPatterns, File.separator));
		String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
		if (!ctxDir.endsWith(File.separator)) {
			ctxDir = ctxDir + File.separator;
		}
		File savePath = new File(ctxDir + uploadDir + autoCreatedDateDir);
		logger.debug("===> save path is: {}", savePath);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}

		List<UploadFile> uploadFileList = new ArrayList<UploadFile>();
		UploadFile uploadFile = null;

		int i = 0;
		Hashtable fileh = form.getMultipartRequestHandler().getFileElements();

		for (Enumeration e = fileh.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();

			FormFile formFile = (FormFile) fileh.get(key);
			String fileName = formFile.getFileName().trim();
			if (!"".equals(fileName)) {
				uploadFile = new UploadFile();
				uploadFile.setContentType(formFile.getContentType());
				uploadFile.setFileSize(formFile.getFileSize());
				uploadFile.setFileName(formFile.getFileName().trim());
				uploadFile.setFormName(key);
				String fileSaveName = StringUtils.join(new String[] { UUID.randomUUID().toString(), ".",
						uploadFile.getExtension().toLowerCase() });
				String fileSavePath = uploadDir + autoCreatedDateDir + fileSaveName;
				uploadFile.setFileSaveName(fileSaveName);
				uploadFile.setFileSavePath(StringUtils.replace(fileSavePath, File.separator, "/"));
				logger.debug(uploadFile.toString());
				uploadFileList.add(uploadFile);

				InputStream ins = formFile.getInputStream();
				OutputStream os = new FileOutputStream(ctxDir + fileSavePath);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}

				// ftp
				// ftpClientTemplate.storeFile(fileSavePath, ins);
				logger.info("===ctxDir:{}", ctxDir);
				logger.info("===fileSavePath:{}", fileSavePath);

				os.close();
				ins.close();

				// resize image
				if (isResizeImage && (formFile.getContentType().indexOf("image/") != -1)) {
					// resizeByMaxSize
					for (int maxSize : resizeVersions) {
						String source = ctxDir + fileSavePath;
						FtpImageUtils.resize(source, fileSavePath, maxSize);
					}
				}
				// TODO wzx
				isWaterMark = false;
				if (isWaterMark && (formFile.getContentType().indexOf("image/") != -1)) {// 水印
					String watermarkPath = "watermark".concat(File.separator).concat("watermark.png");
					log.info("====watermarkPath:" + watermarkPath);
					FtpImageUtils.waterMark(ctxDir + fileSavePath, ctxDir + watermarkPath, null);
					FtpUtils.uploadFile(ctxDir + fileSavePath, new File(ctxDir + fileSavePath));
				} else {
					FtpUtils.uploadFile(ctxDir + fileSavePath, new File(ctxDir + fileSavePath));
				}
			}
			i++;
		}
		return uploadFileList;
	}

	/**
	 * 检测用户名是否存在
	 */
	public void checkUserNameIsExist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_name = (String) dynaBean.get("user_name");
		UserInfo userInfo = new UserInfo();
		String isExist = "null";

		if (StringUtils.isNotBlank(user_name)) {
			userInfo.setUser_name(user_name);
			if (null == getFacade().getUserInfoService().getUserInfo(userInfo)) {
				isExist = String.valueOf("0");
			} else {
				isExist = String.valueOf("1");
			}
		}

		super.render(response, isExist, "text/x-json;charset=UTF-8");
	}

	public ActionForward checkPopedomInvalid(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		return isInvalid(request, response, "popedom.check.invalid");
	}

	public ActionForward isInvalid(HttpServletRequest request, HttpServletResponse response, String message)
			throws IOException {

		// String msg = super.getMessage(request, message);
		// // String cPath = request.getContextPath().concat("/");
		// super.renderJavaScript(response,
		// "alert('".concat(msg).concat("');history.back();"));
		// return null;

		return new ActionForward("/../manager/popedomInvalidTip.jsp");
	}

	/**
	 * @author Wu, Yang
	 * @version Build 2010-8-26
	 * @desc 显示导航文字
	 */
	protected void setNaviStringToRequestScope(HttpServletRequest request, String oper_name) {
		String mod_id = request.getParameter("mod_id");
		if (GenericValidator.isLong(mod_id)) {
			String naviString = " ";
			if (StringUtils.isNotBlank(mod_id)) {
				SysModule sysModule = new SysModule();
				sysModule.setMod_id(new Long(mod_id));
				List<SysModule> sysModuleList = getFacade().getSysModuleService().proGetSysModelParentList(sysModule);
				naviString = StringHelper.getNaviStringFromSysModuleList(sysModuleList);
			}
			request.setAttribute("naviString", naviString.concat(oper_name));
		}
	}

	protected void setNaviStringToRequestScope(HttpServletRequest request) {
		String method = request.getParameter("method");
		String oper_name = " ";
		if ("add".equalsIgnoreCase(method)) {
			oper_name = " -&gt; 添加";
		} else if ("edit".equalsIgnoreCase(method)) {
			oper_name = " -&gt; 编辑";
		} else if ("view".equalsIgnoreCase(method)) {
			oper_name = " -&gt; 查看";
		} else if ("audit".equalsIgnoreCase(method)) {
			oper_name = " -&gt; 审核";
		} else {
			oper_name = " -&gt; 列表";
		}

		setNaviStringToRequestScope(request, oper_name);
	}

	protected void getsonSysModuleList(HttpServletRequest request, DynaBean dynaBean) {
		String par_id = request.getParameter("par_id");
		String mod_id = request.getParameter("mod_id");
		if (!GenericValidator.isLong(par_id)) {
			return;
		}

		SysModule sysModule = new SysModule();
		sysModule.setPar_id(Long.valueOf(par_id));
		sysModule.setIs_del(0);

		UserInfo ui = this.getUserInfoFromSession(request);
		if (null != ui) {
			if (!Keys.pay_type_is_audit_success && (ui.getIs_entp().intValue() == 0)
					&& (ui.getIs_fuwu().intValue() == 0)) {// 没通过审核 并且是普通用户
				sysModule.setIs_lock(0);
			}
		}

		List<SysModule> sonSysModuleList = this.facade.getSysModuleService().getSysModuleList(sysModule);
		if ((null != sonSysModuleList) && (sonSysModuleList.size() > 0)) {
			int i = 0;
			for (SysModule bp : sonSysModuleList) {

				String linkUrl = "?";
				if (StringUtils.contains(bp.getMod_url(), linkUrl)) {
					linkUrl = "&";
				}
				bp.setMod_url(bp.getMod_url() + linkUrl + "par_id=" + par_id + "&mod_id=" + bp.getMod_id());
				if (StringUtils.isBlank(mod_id) && (i == 0)) {
					dynaBean.set("mod_id", bp.getMod_id());
				}
				i++;
			}
		}

		request.setAttribute("sonSysModuleList", sonSysModuleList);
	}

	protected void getsonSysModuleListForMobile(HttpServletRequest request, DynaBean dynaBean) {
		String par_id = request.getParameter("par_id");
		if (!GenericValidator.isLong(par_id)) {
			return;
		}

		SysModule sysModule = new SysModule();
		sysModule.setPar_id(Long.valueOf(par_id));
		sysModule.setIs_del(0);
		UserInfo ui = this.getUserInfoFromSession(request);
		if (null != ui) {
			if (!Keys.pay_type_is_audit_success && (ui.getIs_entp().intValue() == 0)
					&& (ui.getIs_fuwu().intValue() == 0)) {// 没通过审核 并且是普通用户
				sysModule.setIs_lock(0);
			}
		}

		List<SysModule> sonSysModuleList = this.facade.getSysModuleService().getSysModuleList(sysModule);
		if ((null != sonSysModuleList) && (sonSysModuleList.size() > 0)) {
			for (SysModule bp : sonSysModuleList) {

				String linkUrl = "?";
				if (StringUtils.contains(bp.getMod_url(), linkUrl)) {
					linkUrl = "&";
				}
				bp.setMod_url(bp.getMod_url() + linkUrl + "mod_id=" + bp.getMod_id());
				bp.setMod_url(StringUtils.replace(bp.getMod_url(), "/manager/customer/", "/m/M"));
			}
		}

		request.setAttribute("sonSysModuleList", sonSysModuleList);
	}

	protected void getModNameForMobile(HttpServletRequest request) {
		String mod_id = request.getParameter("mod_id");
		if (!GenericValidator.isLong(mod_id)) {
			return;
		}
		SysModule sysModule = new SysModule();
		sysModule.setMod_id(Long.valueOf(mod_id));
		sysModule = this.facade.getSysModuleService().getSysModule(sysModule);
		if (null == sysModule) {
			return;
		}
		request.setAttribute("header_title", sysModule.getMod_name());
	}

	protected void getMod_name(Long mod_id, StringBuffer sb) {
		SysModule sysModule = new SysModule();
		sysModule.setMod_id(mod_id);
		// sysModule.setIs_del(0);
		sysModule = this.facade.getSysModuleService().getSysModule(sysModule);
		if (null == sysModule) {
			return;
		}

		if (!new Integer(0).equals(sysModule.getPar_id())) {// 不是顶级结点
			getMod_name(sysModule.getPar_id(), sb);
			sb.append(" &gt; ").append(sysModule.getMod_name());
		}
	}

	protected void setNaviStringToRequestScope(HttpServletRequest request, String... mod_names) {
		StringBuffer naviStringSB = new StringBuffer(Keys.app_name);

		for (String mod_name : mod_names) {
			naviStringSB.append(" &gt; ").append(mod_name);
		}

		request.setAttribute("naviString", naviStringSB.toString());
	}

	/**
	 * @author Wu, Yang
	 * @version 2010-08-26
	 */
	public String getSysSetting(String title) throws Exception {
		SysSetting sysSetting = new SysSetting();
		sysSetting.setTitle(title);
		return getFacade().getSysSettingService().getSysSetting(sysSetting).getContent();
	}

	/**
	 * @param encoding 编码，用GBK图形才能正常显示
	 * @author Wu, Yang
	 * @version 2010-08-26
	 */
	protected void renderXmlWithEncoding(HttpServletResponse response, String text, String encoding) {
		render(response, text, "text/xml;charset=".concat(encoding));
	}

	/**
	 * @author Wu, Yang
	 * @version 2010-08-29
	 */
	protected void renderExcelWithEncoding(HttpServletRequest request, HttpServletResponse response, String encoding)
			throws IOException {
		String hiddenHtml = StringUtils.lowerCase(request.getParameter("hiddenHtml"));

		hiddenHtml = StringUtils.replace(hiddenHtml, "border=0", "border=1");
		hiddenHtml = StringUtils.replace(hiddenHtml, "border=\"0\"", "border=\"1\"");

		String fname = EncryptUtilsV2.encodingFileName(request.getParameter("hiddenName"));

		response.setCharacterEncoding(encoding);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(hiddenHtml);

		out.flush();
		out.close();
	}

	public UserInfo getUserInfoFromSession(HttpServletRequest request) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		// 防止出现审核通过之后没有进行重新登录出现的一系列问题
		if (null != userInfo) {
			userInfo = this.getUserInfo(userInfo.getId());
		}
		return userInfo;
	}

	public String getCtxPathForServer(HttpServletRequest request) {
		String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
		if (!ctxDir.endsWith(File.separator)) {
			ctxDir = ctxDir + File.separator;
		}
		return ctxDir;
	}

	public String getCtxPath(HttpServletRequest request) {
		return this.getCtxPath(request, true);
	}

	// is_add_port true:增加端口号
	public String getCtxPath(HttpServletRequest request, boolean is_add_port) {
		StringBuffer ctx = new StringBuffer();
		ctx.append(request.getScheme()).append("://").append(request.getServerName());
		if (is_add_port) {
			ctx.append(":").append(request.getServerPort());
		}
		ctx.append(request.getContextPath());

		return ctx.toString();
	}

	@Override
	protected void renderExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String hiddenHtml = StringUtils.lowerCase(request.getParameter("hiddenHtml"));

		hiddenHtml = StringUtils.replace(hiddenHtml, "border=0", "border=1");
		hiddenHtml = StringUtils.replace(hiddenHtml, "border=\"0\"", "border=\"1\"");

		String fname = EncryptUtilsV2.encodingFileName(request.getParameter("hiddenName"));

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(hiddenHtml);

		out.flush();
		out.close();
	}

	/**
	 * @param type : 10,用户类型（系统管理员，注册用户）
	 * @param 30、热门搜索
	 * @param 50、广告位
	 * @param 60、首页默认取的类别ID
	 * @param 70、住宿（住）房型
	 * @param 80、旅游（游）景点门票类型
	 * @author Wu, Yang
	 * @version 2010-08-26
	 * @desc 取得分类信息基础数据
	 */
	public List<BaseData> getBaseDataList(Integer type) {
		List<BaseData> baseDataList = new ArrayList<BaseData>();
		BaseData baseData = new BaseData();
		baseData.setType(type);
		baseData.setIs_del(0);
		baseDataList = getFacade().getBaseDataService().getBaseDataList(baseData);
		return baseDataList;
	}

	public List<BaseData> getBaseDataListByTypeAndPreNumber(Integer type, Integer pre_number) {
		List<BaseData> baseDataList = new ArrayList<BaseData>();
		BaseData baseData = new BaseData();
		baseData.setType(type);
		baseData.setPre_number(pre_number);
		baseData.setIs_del(0);
		baseDataList = getFacade().getBaseDataService().getBaseDataList(baseData);
		return baseDataList;
	}

	public List<BaseData> getBaseDataList(Integer type, int count) {
		List<BaseData> baseDataList = new ArrayList<BaseData>();
		BaseData baseData = new BaseData();
		baseData.setType(type);
		baseData.setIs_del(0);
		baseData.getRow().setCount(count);
		baseDataList = getFacade().getBaseDataService().getBaseDataList(baseData);
		return baseDataList;
	}

	/**
	 * @param type : 17000
	 * @param pre_number : 0 进库 ; 1 出库
	 * @author Qi, NengFei
	 * @version 2017-11-21
	 * @desc 取得商品进出库类型
	 */
	public List<BaseData> getInputOutTypeList(Integer type, Integer pre_number) {
		List<BaseData> baseDataList = new ArrayList<BaseData>();
		BaseData baseData = new BaseData();
		baseData.setType(type);
		baseData.setPre_number(pre_number);
		baseData.setIs_del(0);
		baseDataList = getFacade().getBaseDataService().getBaseDataList(baseData);
		return baseDataList;
	}

	/**
	 * @param type : 10,用户类型（系统管理员，注册用户）
	 * @author Wu, Yang
	 * @version 2010-08-26
	 * @desc 将分类信息基础数据List放到session中
	 */
	public void setBaseDataListToSession(Integer type, HttpServletRequest request) {
		List<BaseData> baseDataList = new ArrayList<BaseData>();
		BaseData baseData = new BaseData();
		baseData.setType(type);
		baseData.setIs_del(0);
		baseDataList = getFacade().getBaseDataService().getBaseDataList(baseData);
		request.setAttribute("baseData" + type + "List", baseDataList);
	}

	/**
	 * @author Wu, Yang
	 * @version 2011-04-26
	 * @desc 回显省市县单个的信息
	 */
	public void setprovinceAndcityAndcountryToFrom(DynaBean dynaBean, Integer p_index) {
		if (null != p_index) {
			BaseProvince baseProvince = new BaseProvince();
			baseProvince.setP_index(p_index.longValue());
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			if (null != baseProvince) {
				dynaBean.set("full_name", baseProvince.getFull_name());
				if (baseProvince.getPar_index() == 0) {
					dynaBean.set("province", baseProvince.getP_index().toString());
				} else {
					BaseProvince baseP = new BaseProvince();
					baseP.setP_index(baseProvince.getPar_index());
					baseP = getFacade().getBaseProvinceService().getBaseProvince(baseP);
					if (baseP.getPar_index() == 0) {
						dynaBean.set("province", baseP.getP_index().toString());
						dynaBean.set("city", baseProvince.getP_index().toString());
					} else {
						BaseProvince bp = new BaseProvince();
						bp.setP_index(baseP.getPar_index());
						bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
						dynaBean.set("province", bp.getP_index().toString());
						dynaBean.set("city", baseP.getP_index().toString());
						dynaBean.set("country", baseProvince.getP_index().toString());
					}
				}

			}
		}
	}

	public void setMprovinceAndcityAndcountryToFrom(DynaBean dynaBean, Integer p_index) {
		if (null != p_index) {
			BaseProvince baseProvince = new BaseProvince();
			baseProvince.setP_index(p_index.longValue());
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			if (null != baseProvince) {

				dynaBean.set("full_name", baseProvince.getFull_name());

				if (baseProvince.getPar_index() == 0) {
					dynaBean.set("rel_province", baseProvince.getP_index().toString());
					dynaBean.set("province_text", baseProvince.getP_name().toString());
				} else {
					BaseProvince baseP = new BaseProvince();
					baseP.setP_index(baseProvince.getPar_index());
					baseP = getFacade().getBaseProvinceService().getBaseProvince(baseP);
					if (baseP.getPar_index() == 0) {
						dynaBean.set("rel_province", baseP.getP_index().toString());
						dynaBean.set("province_text", baseP.getP_name().toString());

						dynaBean.set("rel_city", baseProvince.getP_index().toString());
						dynaBean.set("city_text", baseProvince.getP_name().toString());
					} else {
						BaseProvince bp = new BaseProvince();
						bp.setP_index(baseP.getPar_index());
						bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
						dynaBean.set("rel_province", bp.getP_index().toString());
						dynaBean.set("province_text", bp.getP_name().toString());

						dynaBean.set("rel_city", baseP.getP_index().toString());
						dynaBean.set("city_text", baseP.getP_name().toString());

						dynaBean.set("rel_region", baseProvince.getP_index().toString());
						dynaBean.set("rel_region_text", baseProvince.getP_name().toString());
					}
				}

			}
		}
	}

	/**
	 * @author Wu, Yang
	 * @version 2010-4-27
	 */
	// protected void setBaseProvinceStringToFrom(ActionForm form,
	// HttpServletRequest request, String p_index) {
	//
	// String areaString = "";
	// if (StringUtils.isNotBlank(p_index)) {
	// BaseProvince baseProvince = new BaseProvince();
	// baseProvince.setP_index(new Integer(p_index));
	// List<BaseProvince> baseProvinceList =
	// getFacade().getBaseProvinceService().getBaseProvinceParentList(
	// baseProvince);
	// areaString =
	// StringHelper.getAreaStringFromBaseProvince(baseProvinceList);
	// }
	// request.setAttribute("areaString", areaString);
	// }

	/**
	 * @author duzhiming
	 * @desc用户信息列表
	 * @version 2011-5-11
	 */
	public List<UserInfo> getUserInfoList() {

		UserInfo entity = new UserInfo();
		entity.setIs_del(0);
		return getFacade().getUserInfoService().getUserInfoList(entity);// entity
	}

	/**
	 * @author Zhang, Xufeng
	 * @version 2011-05-11
	 * @desc 回显始发地省市县单个的信息
	 */
	public void setSrcProvinceAndcityAndcountryToForm(DynaBean dynaBean, Integer p_index) {
		if (null != p_index) {
			BaseProvince baseProvince = new BaseProvince();
			baseProvince.setP_index(p_index.longValue());
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			if (null != baseProvince) {
				if (baseProvince.getPar_index() == 0) {
					dynaBean.set("src_province", baseProvince.getP_index().toString());
				} else {
					BaseProvince baseP = new BaseProvince();
					baseP.setP_index(baseProvince.getPar_index());
					baseP = getFacade().getBaseProvinceService().getBaseProvince(baseP);
					if (baseP.getPar_index() == 0) {
						dynaBean.set("src_province", baseP.getP_index().toString());
						dynaBean.set("src_city", baseProvince.getP_index().toString());
					} else {
						BaseProvince bp = new BaseProvince();
						bp.setP_index(baseP.getPar_index());
						bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
						dynaBean.set("src_province", bp.getP_index().toString());
						dynaBean.set("src_city", baseP.getP_index().toString());
						dynaBean.set("src_country", baseProvince.getP_index().toString());
					}
				}
			}
		}
	}

	/**
	 * @author Zhang, Xufeng
	 * @version 2011-05-11
	 * @desc 回显目的地省市县单个的信息
	 */
	public void setDestProvinceAndcityAndcountryToForm(DynaBean dynaBean, Integer p_index) {
		if (null != p_index) {
			BaseProvince baseProvince = new BaseProvince();
			baseProvince.setP_index(p_index.longValue());
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			if (null != baseProvince) {
				if (baseProvince.getPar_index() == 0) {
					dynaBean.set("dest_province", baseProvince.getP_index().toString());
				} else {
					BaseProvince baseP = new BaseProvince();
					baseP.setP_index(baseProvince.getPar_index());
					baseP = getFacade().getBaseProvinceService().getBaseProvince(baseP);
					if (baseP.getPar_index() == 0) {
						dynaBean.set("dest_province", baseP.getP_index().toString());
						dynaBean.set("dest_city", baseProvince.getP_index().toString());
					} else {
						BaseProvince bp = new BaseProvince();
						bp.setP_index(baseP.getPar_index());
						bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
						dynaBean.set("dest_province", bp.getP_index().toString());
						dynaBean.set("dest_city", baseP.getP_index().toString());
						dynaBean.set("dest_country", baseProvince.getP_index().toString());
					}
				}
			}
		}
	}

	/**
	 * 验证是否具有操作权限
	 */
	public Object checkUserModPopeDom(ActionForm form, HttpServletRequest request, String... popedoms) {
		boolean legitimate = false;
		String modPopedom = this.getModPopeDom(form, request);
		if ("+".equals(modPopedom)) {
			return null;
		}

		for (String popedom : popedoms) {
			popedom = popedom.concat("+");
			if (StringUtils.indexOf(modPopedom, popedom) == -1) {
				legitimate = false;
				break;
			}
			legitimate = true;
		}

		if (legitimate) {
			return legitimate;
		}
		return null;
	}

	public String getModPopeDom(ActionForm form, HttpServletRequest request) {
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		if (!GenericValidator.isLong(mod_id)) {
			request.setAttribute("popedom", "");
			return "";
		}
		StringBuffer popedom = new StringBuffer();

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		RoleUser roleUser = new RoleUser();
		roleUser.setUser_id(userInfo.getId());
		List<RoleUser> roleUserList = this.facade.getRoleUserService().getRoleUserList(roleUser);
		boolean legal = false;
		for (RoleUser temp : roleUserList) {
			if (null == temp) {
				continue;
			}
			legal = true;
			ModPopedom webModPopedom = new ModPopedom();
			webModPopedom.setMod_id(Integer.valueOf(mod_id));
			webModPopedom.setRole_id(temp.getRole_id());
			webModPopedom = this.getFacade().getModPopedomService().getModPopedom(webModPopedom);
			if (null != webModPopedom) {
				BasePopedom bp = new BasePopedom();
				bp.setPpdm_code(new Integer(webModPopedom.getPpdm_code()));
				bp = getFacade().getBasePopedomService().getBasePopedom(bp);
				if (null != bp) {
					popedom.append(bp.getPpdm_detail());
				}
			}
		}
		if (legal) {
			popedom.append("+");
		}

		ModPopedom webModPopedom = new ModPopedom();
		webModPopedom.setMod_id(Integer.valueOf(mod_id));
		webModPopedom.setUser_id(userInfo.getId());
		webModPopedom = this.getFacade().getModPopedomService().getModPopedom(webModPopedom);
		if (null != webModPopedom) {
			BasePopedom bp = new BasePopedom();
			bp.setPpdm_code(new Integer(webModPopedom.getPpdm_code()));
			bp = getFacade().getBasePopedomService().getBasePopedom(bp);
			if (null != bp) {
				popedom.append(bp.getPpdm_detail());
			}
		}
		popedom.append("+");

		request.setAttribute("popedom", popedom.toString());

		return popedom.toString();
	}

	/**
	 * 查询企业列表(未删除、审核通过)
	 */
	public List<EntpInfo> getEntpInfoList() {
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.setAudit_state(1);
		List<EntpInfo> list = getFacade().getEntpInfoService().getEntpInfoList(entpInfo);
		return list;
	}

	/**
	 * @return ip
	 * @author Li, Yuan
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip) && StringUtils.contains(ip, ",")) {
			ip = StringUtils.split(ip, ",")[0];
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		logger.info("===ip:" + ip);
		return ip;
	}

	public boolean isLocal(HttpServletRequest request) {
		String ip = getIpAddr(request);
		logger.info("===ip:" + ip);
		if (("192.168.2.160,192.168.2.201,0:0:0:0:0:0:0:1").contains(ip)) {
			return true;
		}
		return false;

	}

	public void createSysOperLog(HttpServletRequest request, String oper_type, String oper_memo, String logString,
			Integer link_id) throws Exception {
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		SysOperLog sysOperLog = new SysOperLog();
		sysOperLog.setOper_type(Integer.valueOf(oper_type));
		sysOperLog.setOper_time(new Date());
		String ip = this.getIpAddr(request);

		sysOperLog.setOper_uip(ip);
		sysOperLog.setOper_memo(oper_memo);
		if (null != link_id) {
			sysOperLog.setLink_id(link_id);
		}
		BaseClass baseClass = new BaseClass();
		baseClass.setCls_scope(10);
		baseClass.setCls_id(Integer.valueOf(oper_type));
		baseClass.setIs_del(0);
		baseClass = getFacade().getBaseClassService().getBaseClass(baseClass);
		if (null != baseClass) {
			sysOperLog.setOper_name(baseClass.getCls_name());
		}

		if (userInfo != null) {
			sysOperLog.setOper_uid(userInfo.getId());
			sysOperLog.setOper_uname(userInfo.getUser_name());
		}
		getFacade().getSysOperLogService().createSysOperLog(sysOperLog);
	}

	/**
	 * @param oper_type 10：用户登录
	 * @param 20 供应商/企业审核
	 * @param 30 产品审核
	 * @param 40 品牌审核
	 * @param 50 商品审核
	 * @param 60 订单审核 61订单调货 62订单发货 65订单冻结 66 订单删除
	 * @author Wu, Yang
	 * @version Build 2010-8-24
	 * @desc 记录操作日志
	 */
	public void createSysOperLog(HttpServletRequest request, int oper_type, String oper_memo, String logString)
			throws Exception {
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (userInfo != null) {
			SysOperLog sysOperLog = new SysOperLog();
			sysOperLog.setOper_type(oper_type);
			String oper_name = "";
			for (SysOperType sot : Keys.SysOperType.values()) {
				if (sot.getIndex() == oper_type) {
					oper_name = sot.getName();
					break;
				}
			}
			sysOperLog.setOper_name(oper_name);

			sysOperLog.setOper_time(new Date());
			String ip = this.getIpAddr(request);

			// String ip_addr = "";
			// String get_ip_url =
			// "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
			// String result = GetApiUtils.getApiWithUrl(get_ip_url);
			// logger.info(result);
			//
			// StringBuffer server = new StringBuffer();
			// server.append(request.getHeader("host")).append(request.getContextPath());
			// String server_domain = server.toString();
			// if (!(StringUtils.contains(server_domain, "localhost") ||
			// StringUtils.contains(server_domain, "0.0.1")))
			// {
			// String data = new
			// JSONObject(result.toString()).get("data").toString();
			// JSONObject json_data = new JSONObject(data);
			// // String area = (String) json_data.get("area");
			// String area = "<br />";
			// String region = (String) json_data.get("region");
			// String city = (String) json_data.get("city");
			// ip_addr = area.concat(region).concat(city);
			// }

			sysOperLog.setOper_uip(ip);
			sysOperLog.setOper_memo(oper_memo);

			sysOperLog.setOper_uid(userInfo.getId());
			sysOperLog.setOper_uname(userInfo.getUser_name());

			getFacade().getSysOperLogService().createSysOperLog(sysOperLog);
		}
	}

	public HashMap<String, String> calcMatflowPrice(HashMap<String, String> map) {
		String entp_id = map.get("entp_id");
		String user_id = map.get("user_id");
		String gn_type = map.get("gn_type");
		String user_type = map.get("user_type");
		String p_index = map.get("p_index");
		String charge_type = map.get("charge_type");

		/*
		 * EntpInfo entpInfo = new EntpInfo(); entpInfo.setId(Integer.valueOf(entp_id)); entpInfo =
		 * super.getFacade().getEntpInfoService().getEntpInfo(entpInfo); Integer src_p_index = entpInfo.getP_index();
		 */
		// 20131030改为起始地为安徽合肥
		Integer src_p_index = 340100;

		CartInfo cartInfo = new CartInfo();
		cartInfo.setEntp_id(Integer.valueOf(entp_id));
		cartInfo.setUser_id(Integer.valueOf(user_id));
		cartInfo.setGn_type(Integer.valueOf(gn_type));
		List<CartInfo> cartInfoList = this.getFacade().getCartInfoService().getCartInfoList(cartInfo);
		BigDecimal entpWeight = new BigDecimal("0"); // 不包邮重量
		BigDecimal entpAllWeight = new BigDecimal("0"); // 总重量,即包邮重量+不包邮重量
		BigDecimal entpMatflowPrice = new BigDecimal("0");
		StringBuffer matflow_memo = new StringBuffer("");
		for (CartInfo cart_info_ : cartInfoList) {
			CommInfo commInfo = new CommInfo();
			commInfo.setId(cart_info_.getComm_id());
			commInfo = this.getFacade().getCommInfoService().getCommInfo(commInfo);
			BigDecimal comm_weight = commInfo.getComm_weight();// cart_info_.getComm_weight()
																// //
																// 20131202改为从商品中取,不从购物车中取
			entpAllWeight = entpAllWeight.add(comm_weight.multiply(new BigDecimal(cart_info_.getPd_count())));
			if (commInfo.getIs_freeship().intValue() == 0) { // 得出不包邮的商品总重量
				entpWeight = entpWeight.add(comm_weight.multiply(new BigDecimal(cart_info_.getPd_count())));
			}
		}
		if ((entpWeight.compareTo(new BigDecimal("0")) != 0) && StringUtils.isNotBlank(p_index)) { // 有重量,需要计算运费
			WlCompMatflow wlCompMatflow_result = null;
			WlCompMatflow wlCompMatflow_param = new WlCompMatflow();
			wlCompMatflow_param.setSrc_p_index(Integer.valueOf(src_p_index));
			wlCompMatflow_param.setDest_p_index(Integer.valueOf(p_index));
			wlCompMatflow_param.setIs_del(0);
			if (StringUtils.isBlank(charge_type)) {
				wlCompMatflow_param.setCharge_type(1);
			} else {
				wlCompMatflow_param.setCharge_type(Integer.valueOf(charge_type));
			}
			int matflow_type = 0;
			if ("2".equals(user_type)) {
				matflow_type = 10;
			}
			if ("3".equals(user_type)) {
				matflow_type = 20;
			}
			wlCompMatflow_param.setMatflow_type(matflow_type);
			int current_area_type = (p_index.length() == 6 ? 3 : 4);
			String next_p_index = p_index;
			while ((wlCompMatflow_result == null) && (current_area_type != 0)) {
				wlCompMatflow_result = this.getFacade().getWlCompMatflowService().getWlCompMatflow(wlCompMatflow_param);
				if (wlCompMatflow_result == null) { // 当前区域没有查到,则需下一次查询
					if (current_area_type == 4) { // 当前乡镇,下一次查询区县
						next_p_index = StringUtils.substring(p_index, 0, 6);
					} else if (current_area_type == 3) { // 当前区县,下一次查询市级
						next_p_index = StringUtils.substring(p_index, 0, 4).concat("00");
					} else if (current_area_type == 2) { // 当前市级,下一次查询省级
						next_p_index = StringUtils.substring(p_index, 0, 2).concat("0000");
					} else if (current_area_type == 1) { // 当前省级,下一次全国范围,则为默认邮费标准,暂时设为空
						next_p_index = "0";
					}
					wlCompMatflow_param.setDest_p_index(Integer.valueOf(next_p_index));
					current_area_type--;
				}
			}

			if (wlCompMatflow_result != null) {
				if (wlCompMatflow_result.getCharge_type().intValue() == 1) {
					/*
					 * matflow_memo = matflow_memo.append("该商家订单合计不包邮重量").append(
					 * entpWeight).append("g,首重(1KG)").append( wlCompMatflow_result
					 * .getFirst_kilo_fee()).append("元,续重每KG").append(
					 * wlCompMatflow_result.getOther_kilo_fee()).append("元.");
					 */
					if ((entpWeight.add(new BigDecimal(-1000))).compareTo(new BigDecimal("0")) <= 0) {
						entpMatflowPrice = wlCompMatflow_result.getFirst_kilo_fee();
					} else {
						MathContext mc = new MathContext(0, RoundingMode.UP);
						BigDecimal other_num = ((entpWeight.add(new BigDecimal(-1000)))
								.divide(new BigDecimal(1000), mc)).setScale(0, BigDecimal.ROUND_CEILING);
						entpMatflowPrice = wlCompMatflow_result.getFirst_kilo_fee().add(
								wlCompMatflow_result.getOther_kilo_fee().multiply(other_num));
					}
				}
			}
		}

		map.put("matflow_memo", matflow_memo.toString());
		map.put("entpMatflowPrice", entpMatflowPrice.toString());
		map.put("entpWeight", entpWeight.toString());
		map.put("entpAllWeight", entpAllWeight.toString());

		return map;
	}

	public Freight getFreight(Integer fre_id, Integer p_index, Integer num, BigDecimal weight, BigDecimal volume,
			BigDecimal totalSumPrice) {
		if (null != fre_id) {

			Freight fre = new Freight();
			fre.setId(fre_id);
			fre.setIs_del(new Integer(0));
			fre = getFacade().getFreightService().getFreight(fre);
			String is_send = "0";
			if (fre == null) {// 如果为空则直接返回
				fre = new Freight();
				fre.getMap().put("is_send", is_send);
				return fre;
			}
			for (int f = 1; f <= 3; f++) {// 查询计算支持配送方式及价格
				// 1快递,2EMS,3平邮
				FreightDetail freDetail = new FreightDetail();
				freDetail.setFre_id(fre_id);
				freDetail.setDelivery_way(new Integer(f));
				List<FreightDetail> freDetailList = getFacade().getFreightDetailService().getFreightDetailList(
						freDetail);
				String is_delivery = "0";// 是否0不送达，1送达
				BigDecimal fre_price = new BigDecimal("0.0");
				String delivery_way = "0";// 是否支持该配送方式0不支持，1支持
				if (fre.getIs_freeshipping().intValue() == 1) {

					// 如果选择了满包邮
					if (fre.getIs_open_freeShipping_money().intValue() == 1) {
						if (totalSumPrice.compareTo(new BigDecimal(fre.getOpen_money_freeShipping())) >= 0) {
							is_delivery = "1";
							fre_price = new BigDecimal("0.0");
						} else {

							if ((freDetailList != null) && (freDetailList.size() > 0)) {
								try {// 处理异常 所选区域计算运费出现错误 ，则无法配送
									if ((p_index != null) && (num != null)) {
										FreightDetail deaultFre = null;
										FreightDetail ppFre = null;
										for (int i = 0; i < freDetailList.size(); i++) {
											FreightDetail freightDetail = freDetailList.get(i);
											if ((freightDetail.getArea_pindex() != null)
													&& !"".equals(freightDetail.getArea_pindex())) {// 根据p_index
												// 匹配
												String[] p_indexs = freightDetail.getArea_pindex().split(",");
												if ((p_indexs != null) && (p_indexs.length > 0)) {
													for (String p_index3 : p_indexs) {
														String[] all_p_index = p_index3.split("\\|");
														String p_index1 = all_p_index[0];
														String p_index2 = "";
														if (all_p_index.length == 2) {
															if ("ALL".equals(all_p_index[1])) {
																p_index2 = p_index1;
															} else {
																p_index2 = all_p_index[1];
															}
														}
														if (!"".equals(p_index2) && (p_index2 != null)
																&& (p_index2.length() > 5)) {
															if (p_index2.endsWith("0000")) {
																if (p_index.toString().substring(0, 2)
																		.equals(p_index2.substring(0, 2))) {
																	ppFre = freDetailList.get(i);
																}
															} else if (p_index2.endsWith("00")) {
																if (p_index.toString().substring(0, 4)
																		.equals(p_index2.substring(0, 4))) {
																	ppFre = freDetailList.get(i);
																}
															} else {
																if (p_index.toString().substring(0, 6).equals(p_index2)) {
																	ppFre = freDetailList.get(i);
																}
															}
														}
													}
												}
											} else {
												deaultFre = freDetailList.get(i);
											}
										}
										if (fre.getArea_limit().intValue() == 1) {// 限制区域
																					// 无默认全国价格
											deaultFre = null;
										}
										if (ppFre == null) {
											ppFre = deaultFre;
										}
										if (ppFre != null) {// 计算运费
											fre_price = ppFre.getFirst_price();
											if (fre.getValuation().intValue() == 1) {// 按件
												log.info("Valuation:" + fre.getValuation() + "计算开始：num:" + num
														+ ",weight:" + weight + ",volume:" + volume + ",fre_detail:"
														+ ppFre.getId());
												BigDecimal sed_price = new BigDecimal("0.0");
												if ((ppFre.getFirst_weight() != null)
														&& (num > ppFre.getFirst_weight().intValue())
														&& (ppFre.getSed_price() != null)
														&& (ppFre.getSed_price().floatValue() > 0.0)) {
													sed_price = new BigDecimal(num - ppFre.getFirst_weight().intValue())
															.divide(ppFre.getSed_weight()).multiply(
																	ppFre.getSed_price());
												}
												fre_price = ppFre.getFirst_price().add(sed_price);
											} else if (fre.getValuation().intValue() == 2) {
												log.info("Valuation:" + fre.getValuation() + "计算开始：num:" + num
														+ ",weight:" + weight + ",volume:" + volume + "First_weight:"
														+ ppFre.getFirst_weight());
												BigDecimal total_weight = weight;
												BigDecimal sed_price = new BigDecimal("0.0");
												if ((ppFre.getFirst_weight() != null)
														&& (total_weight.subtract(ppFre.getFirst_weight()).floatValue() > 0)
														&& (ppFre.getSed_price() != null)
														&& (ppFre.getSed_price().floatValue() > 0.0)) {

													double sed_price1 = total_weight.subtract(ppFre.getFirst_weight())
															.divide(ppFre.getSed_weight())
															.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
													int sed_price2 = (int) Math.ceil(sed_price1);

													BigDecimal sed_pricer4 = new BigDecimal(sed_price2);
													sed_price = sed_pricer4.multiply(ppFre.getSed_price());
													// int flag_beishu = 1;
													// if
													// (total_weight.subtract(ppFre.getFirst_weight())
													// .divide(ppFre.getSed_weight()).compareTo(ppFre.getSed_weight())
													// >
													// 0)
													// {
													// flag_beishu = 2;
													// }
													// sed_price =
													// ppFre.getSed_price().multiply(new
													// BigDecimal(flag_beishu));

													fre_price = ppFre.getFirst_price().add(sed_price);
												}
												log.info("weight:" + sed_price + ",volume" + volume
														+ ",fre_price-------------->" + fre_price);
											} else if (fre.getValuation().intValue() == 3) {
												log.info("Valuation:" + fre.getValuation() + "计算开始：num:" + num
														+ ",weight:" + weight + ",volume:" + volume + "First_weight:"
														+ ppFre.getFirst_weight());
												BigDecimal total_weight = volume.multiply(new BigDecimal(num));
												BigDecimal sed_price = new BigDecimal("0.0");
												if ((ppFre.getFirst_weight() != null)
														&& (total_weight.subtract(ppFre.getFirst_weight()).floatValue() > 0)
														&& (ppFre.getSed_price() != null)
														&& (ppFre.getSed_price().floatValue() > 0.0)) {
													sed_price = total_weight.subtract(ppFre.getFirst_weight())
															.divide(ppFre.getSed_weight())
															.multiply(ppFre.getSed_price());
													fre_price = ppFre.getFirst_price().add(sed_price);
												}
												log.info("weight:" + weight + ",volume" + volume
														+ ",fre_price-------------->" + fre_price);
											}
											fre.getMap().put("total_weight" + f, weight);
											fre.getMap().put("total_volume" + f, volume.multiply(new BigDecimal(num)));
											is_delivery = "1";
										} else {// 该配送方式 所选择的地区不送达
											is_delivery = "0";
										}
										fre.getMap().put("freightDetail" + f, fre);
									}
								} catch (Exception e) {
									e.printStackTrace();
									is_delivery = "0";
								}
							}

						}
					} else {
						if ((freDetailList != null) && (freDetailList.size() > 0)) {
							try {// 处理异常 所选区域计算运费出现错误 ，则无法配送
								if ((p_index != null) && (num != null)) {
									FreightDetail deaultFre = null;
									FreightDetail ppFre = null;
									for (int i = 0; i < freDetailList.size(); i++) {
										FreightDetail freightDetail = freDetailList.get(i);
										if ((freightDetail.getArea_pindex() != null)
												&& !"".equals(freightDetail.getArea_pindex())) {// 根据p_index
											// 匹配
											String[] p_indexs = freightDetail.getArea_pindex().split(",");
											if ((p_indexs != null) && (p_indexs.length > 0)) {
												for (String p_index3 : p_indexs) {
													String[] all_p_index = p_index3.split("\\|");
													String p_index1 = all_p_index[0];
													String p_index2 = "";
													if (all_p_index.length == 2) {
														if ("ALL".equals(all_p_index[1])) {
															p_index2 = p_index1;
														} else {
															p_index2 = all_p_index[1];
														}
													}
													if (!"".equals(p_index2) && (p_index2 != null)
															&& (p_index2.length() > 5)) {
														if (p_index2.endsWith("0000")) {
															if (p_index.toString().substring(0, 2)
																	.equals(p_index2.substring(0, 2))) {
																ppFre = freDetailList.get(i);
															}
														} else if (p_index2.endsWith("00")) {
															if (p_index.toString().substring(0, 4)
																	.equals(p_index2.substring(0, 4))) {
																ppFre = freDetailList.get(i);
															}
														} else {
															if (p_index.toString().substring(0, 6).equals(p_index2)) {
																ppFre = freDetailList.get(i);
															}
														}
													}
												}
											}
										} else {
											deaultFre = freDetailList.get(i);
										}
									}
									if (fre.getArea_limit().intValue() == 1) {// 限制区域
																				// 无默认全国价格
										deaultFre = null;
									}
									if (ppFre == null) {
										ppFre = deaultFre;
									}
									if (ppFre != null) {// 计算运费
										fre_price = ppFre.getFirst_price();
										if (fre.getValuation().intValue() == 1) {// 按件
											log.info("Valuation:" + fre.getValuation() + "计算开始：num:" + num + ",weight:"
													+ weight + ",volume:" + volume + ",fre_detail:" + ppFre.getId());
											BigDecimal sed_price = new BigDecimal("0.0");
											if ((ppFre.getFirst_weight() != null)
													&& (num > ppFre.getFirst_weight().intValue())
													&& (ppFre.getSed_price() != null)
													&& (ppFre.getSed_price().floatValue() > 0.0)) {
												sed_price = new BigDecimal(num - ppFre.getFirst_weight().intValue())
														.divide(ppFre.getSed_weight()).multiply(ppFre.getSed_price());
											}
											fre_price = ppFre.getFirst_price().add(sed_price);
										} else if (fre.getValuation().intValue() == 2) {
											log.info("Valuation:" + fre.getValuation() + "计算开始：num:" + num + ",weight:"
													+ weight + ",volume:" + volume + "First_weight:"
													+ ppFre.getFirst_weight());
											BigDecimal total_weight = weight;
											BigDecimal sed_price = new BigDecimal("0.0");
											if ((ppFre.getFirst_weight() != null)
													&& (total_weight.subtract(ppFre.getFirst_weight()).floatValue() > 0)
													&& (ppFre.getSed_price() != null)
													&& (ppFre.getSed_price().floatValue() > 0.0)) {

												double sed_price1 = total_weight.subtract(ppFre.getFirst_weight())
														.divide(ppFre.getSed_weight())
														.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
												int sed_price2 = (int) Math.ceil(sed_price1);

												BigDecimal sed_pricer4 = new BigDecimal(sed_price2);
												sed_price = sed_pricer4.multiply(ppFre.getSed_price());
												// int flag_beishu = 1;
												// if
												// (total_weight.subtract(ppFre.getFirst_weight())
												// .divide(ppFre.getSed_weight()).compareTo(ppFre.getSed_weight())
												// > 0)
												// {
												// flag_beishu = 2;
												// }
												// sed_price =
												// ppFre.getSed_price().multiply(new
												// BigDecimal(flag_beishu));

												fre_price = ppFre.getFirst_price().add(sed_price);
											}
											log.info("weight:" + sed_price + ",volume" + volume
													+ ",fre_price-------------->" + fre_price);
										} else if (fre.getValuation().intValue() == 3) {
											log.info("Valuation:" + fre.getValuation() + "计算开始：num:" + num + ",weight:"
													+ weight + ",volume:" + volume + "First_weight:"
													+ ppFre.getFirst_weight());
											BigDecimal total_weight = volume.multiply(new BigDecimal(num));
											BigDecimal sed_price = new BigDecimal("0.0");
											if ((ppFre.getFirst_weight() != null)
													&& (total_weight.subtract(ppFre.getFirst_weight()).floatValue() > 0)
													&& (ppFre.getSed_price() != null)
													&& (ppFre.getSed_price().floatValue() > 0.0)) {
												sed_price = total_weight.subtract(ppFre.getFirst_weight())
														.divide(ppFre.getSed_weight()).multiply(ppFre.getSed_price());
												fre_price = ppFre.getFirst_price().add(sed_price);
											}
											log.info("weight:" + weight + ",volume" + volume
													+ ",fre_price-------------->" + fre_price);
										}
										fre.getMap().put("total_weight" + f, weight);
										fre.getMap().put("total_volume" + f, volume.multiply(new BigDecimal(num)));
										is_delivery = "1";
									} else {// 该配送方式 所选择的地区不送达
										is_delivery = "0";
									}
									fre.getMap().put("freightDetail" + f, fre);
								}
							} catch (Exception e) {
								e.printStackTrace();
								is_delivery = "0";
							}
						}
					}

				} else {// 选择包邮
					// 但是选择区域限售
					if (fre.getArea_limit() == 1) {
						if ((freDetailList != null) && (freDetailList.size() > 0)) {
							try {// 处理异常 所选区域计算运费出现错误 ，则无法配送
								if ((p_index != null) && (num != null)) {
									FreightDetail ppFre = null;
									for (int i = 0; i < freDetailList.size(); i++) {
										FreightDetail freightDetail = freDetailList.get(i);
										if ((freightDetail.getArea_pindex() != null)
												&& !"".equals(freightDetail.getArea_pindex())) {// 根据p_index
											// 匹配
											String[] p_indexs = freightDetail.getArea_pindex().split(",");
											if ((p_indexs != null) && (p_indexs.length > 0)) {
												for (String p_index3 : p_indexs) {
													String[] all_p_index = p_index3.split("\\|");
													String p_index1 = all_p_index[0];
													String p_index2 = "";
													if (all_p_index.length == 2) {
														if ("ALL".equals(all_p_index[1])) {
															p_index2 = p_index1;
														} else {
															p_index2 = all_p_index[1];
														}
													}
													if (!"".equals(p_index2) && (p_index2 != null)
															&& (p_index2.length() > 5)) {
														if (p_index2.endsWith("0000")) {
															if (p_index.toString().substring(0, 2)
																	.equals(p_index2.substring(0, 2))) {
																ppFre = freDetailList.get(i);
															}
														} else if (p_index2.endsWith("00")) {
															if (p_index.toString().substring(0, 4)
																	.equals(p_index2.substring(0, 4))) {
																ppFre = freDetailList.get(i);
															}
														} else {
															if (p_index.toString().substring(0, 6).equals(p_index2)) {
																ppFre = freDetailList.get(i);
															}
														}
													}
												}
											}
										}
									}

									if (ppFre != null) {
										is_delivery = "1";
									} else {
										is_delivery = "0";
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								is_delivery = "0";
							}
						}

					} else {// 没选择区域限售
						if ((freDetailList != null) && (freDetailList.size() > 0)) {
							is_delivery = "1";
							fre_price = new BigDecimal("0.0");
						}
					}
				}
				if ((freDetailList != null) && (freDetailList.size() > 0)) {
					delivery_way = "1";
				}
				if ("1".equals(delivery_way) && "1".equals(is_delivery)) {
					is_send = "1";
				}
				fre.getMap().put("is_delivery" + f, is_delivery);
				fre.getMap().put("fre_price" + f, fre_price);
				fre.getMap().put("delivery_way" + f, delivery_way);
			}
			fre.getMap().put("is_send", is_send);

			return fre;
		} else {
			return null;
		}
	}

	public Freight getFreightForTg(Integer fre_id, Long p_index, Integer num) {
		Freight fre = new Freight();
		fre.setId(fre_id);
		fre.setIs_del(new Integer(0));
		fre = getFacade().getFreightService().getFreight(fre);
		String is_send = "0";
		if (fre == null) {// 如果为空则直接返回
			fre = new Freight();
			fre.getMap().put("is_send", is_send);
			return fre;
		}
		for (int f = 1; f <= 3; f++) {// 查询计算支持配送方式及价格
			// 1快递,2EMS,3平邮
			FreightDetail freDetail = new FreightDetail();
			freDetail.setFre_id(fre_id);
			freDetail.setDelivery_way(new Integer(f));
			List<FreightDetail> freDetailList = getFacade().getFreightDetailService().getFreightDetailList(freDetail);
			String is_delivery = "0";// 是否0不送达，1送达
			BigDecimal fre_price = new BigDecimal("0.0");
			String delivery_way = "0";// 是否支持该配送方式0不支持，1支持
			if (fre.getIs_freeshipping().intValue() == 1) {
				if ((freDetailList != null) && (freDetailList.size() > 0)) {
					try {// 处理异常 所选区域计算运费出现错误 ，则无法配送
						if ((p_index != null) && (num != null)) {
							FreightDetail deaultFre = null;
							FreightDetail ppFre = null;
							for (int i = 0; i < freDetailList.size(); i++) {
								FreightDetail freightDetail = freDetailList.get(i);
								if ((freightDetail.getArea_pindex() != null)
										&& !"".equals(freightDetail.getArea_pindex())) {// 根据p_index
									// 匹配
									String[] p_indexs = freightDetail.getArea_pindex().split(",");
									if ((p_indexs != null) && (p_indexs.length > 0)) {
										for (String p_index3 : p_indexs) {
											String[] all_p_index = p_index3.split("\\|");
											String p_index1 = all_p_index[0];
											String p_index2 = "";
											if (all_p_index.length == 2) {
												if ("ALL".equals(all_p_index[1])) {
													p_index2 = p_index1;
												} else {
													p_index2 = all_p_index[1];
												}
											}
											if (!"".equals(p_index2) && (p_index2 != null) && (p_index2.length() > 5)) {
												if (p_index2.endsWith("0000")) {
													if (p_index.toString().substring(0, 2)
															.equals(p_index2.substring(0, 2))) {
														ppFre = freDetailList.get(i);
													}
												} else if (p_index2.endsWith("00")) {
													if (p_index.toString().substring(0, 4)
															.equals(p_index2.substring(0, 4))) {
														ppFre = freDetailList.get(i);
													}
												} else {
													if (p_index.toString().substring(0, 6).equals(p_index2)) {
														ppFre = freDetailList.get(i);
													}
												}
											}
										}
									}
								} else {
									deaultFre = freDetailList.get(i);
								}
							}
							if (fre.getArea_limit().intValue() == 1) {// 限制区域
																		// 无默认全国价格
								deaultFre = null;
							}
							if (ppFre == null) {
								ppFre = deaultFre;
							}
							if (ppFre != null) {// 计算运费
								fre_price = ppFre.getFirst_price();
								if (fre.getValuation().intValue() == 1) {// 按件
									BigDecimal sed_price = new BigDecimal("0.0");
									if ((ppFre.getFirst_weight() != null) && (num > ppFre.getFirst_weight().intValue())
											&& (ppFre.getSed_price() != null)
											&& (ppFre.getSed_price().floatValue() > 0.0)) {
										sed_price = new BigDecimal(num - ppFre.getFirst_weight().intValue()).divide(
												ppFre.getSed_weight()).multiply(ppFre.getSed_price());
									}
									fre_price = ppFre.getFirst_price().add(sed_price);
								}
								is_delivery = "1";
							} else {// 该配送方式 所选择的地区不送达
								is_delivery = "0";
							}
							fre.getMap().put("freightDetail" + f, fre);
						}
					} catch (Exception e) {
						e.printStackTrace();
						is_delivery = "0";
					}
				}
			} else {// 选择包邮
				// 但是选择区域限售
				if (fre.getArea_limit() == 1) {
					if ((freDetailList != null) && (freDetailList.size() > 0)) {
						try {// 处理异常 所选区域计算运费出现错误 ，则无法配送
							if ((p_index != null) && (num != null)) {
								FreightDetail ppFre = null;
								for (int i = 0; i < freDetailList.size(); i++) {
									FreightDetail freightDetail = freDetailList.get(i);
									if ((freightDetail.getArea_pindex() != null)
											&& !"".equals(freightDetail.getArea_pindex())) {// 根据p_index
										// 匹配
										String[] p_indexs = freightDetail.getArea_pindex().split(",");
										if ((p_indexs != null) && (p_indexs.length > 0)) {
											for (String p_index3 : p_indexs) {
												String[] all_p_index = p_index3.split("\\|");
												String p_index1 = all_p_index[0];
												String p_index2 = "";
												if (all_p_index.length == 2) {
													if ("ALL".equals(all_p_index[1])) {
														p_index2 = p_index1;
													} else {
														p_index2 = all_p_index[1];
													}
												}
												if (!"".equals(p_index2) && (p_index2 != null)
														&& (p_index2.length() > 5)) {
													if (p_index2.endsWith("0000")) {
														if (p_index.toString().substring(0, 2)
																.equals(p_index2.substring(0, 2))) {
															ppFre = freDetailList.get(i);
														}
													} else if (p_index2.endsWith("00")) {
														if (p_index.toString().substring(0, 4)
																.equals(p_index2.substring(0, 4))) {
															ppFre = freDetailList.get(i);
														}
													} else {
														if (p_index.toString().substring(0, 6).equals(p_index2)) {
															ppFre = freDetailList.get(i);
														}
													}
												}
											}
										}
									}
								}

								if (ppFre != null) {
									is_delivery = "1";
								} else {
									is_delivery = "0";
								}
							}
						} catch (Exception e) {
							is_delivery = "0";
						}
					}

				} else {// 没选择区域限售
					if ((freDetailList != null) && (freDetailList.size() > 0)) {
						is_delivery = "1";
						fre_price = new BigDecimal("0.0");
					}
				}
			}
			if ((freDetailList != null) && (freDetailList.size() > 0)) {
				delivery_way = "1";
			}
			if ("1".equals(delivery_way) && "1".equals(is_delivery)) {
				is_send = "1";
			}
			fre.getMap().put("is_delivery" + f, is_delivery);
			fre.getMap().put("fre_price" + f, fre_price);
			fre.getMap().put("delivery_way" + f, delivery_way);
		}
		fre.getMap().put("is_send", is_send);

		return fre;
	}

	/**
	 * 校验是否是非法字符
	 *
	 * @param str 不定字符串参数
	 * @return boolean true:
	 */
	public boolean testForbiddenChar(String... str) {
		boolean flag = false;
		// 读取type =9000的数据,是非法字符的集合
		BaseData bd = new BaseData();
		bd.setIs_del(0);
		bd.setType(9000);
		List<BaseData> baseDataList = new ArrayList();
		baseDataList = this.getFacade().getBaseDataService().getBaseDataList(bd);
		List<String> typeNameList = new ArrayList<String>();
		for (BaseData _bd : baseDataList) {
			typeNameList.add(_bd.getType_name());
		}

		int[] index = testForbiddenChar(typeNameList, str);
		for (int _i : index) {
			if (_i != -1) {
				flag = true;
				break;
			}

		}

		return flag;
	}

	private int[] testForbiddenChar(List<String> list, String... str) {
		// boolean flag = false;
		int[] index = new int[] { -1 };
		// System.out.println(str.length);
		// 屏蔽 new String[] {null} 和 空new String[] {}
		if ((str != null) && (str.length != 0)) {

			index = new int[str.length];
			for (int j = 0; j < index.length; j++) {
				index[j] = -1;

			}

			for (int k = 0; k < str.length; k++) {
				String a = str[k];
				if (a != null) {
					for (String b : list) {
						int _index = a.indexOf(b);
						if (_index > -1) {
							// flag = true;
							index[k] = _index;
						}

					}
				}
			}
		}
		return index;
	}

	public Date string2Date(String dataString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = sdf.parse(dataString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	// 检查是否签到
	public boolean checkIsRegistion(HttpServletRequest request) {
		boolean flag = false;
		UserInfo ui = this.getUserInfoFromSession(request);
		if (null != ui) {
			UserScoreRecord usr_entity = new UserScoreRecord();
			usr_entity.setIs_del(0);
			usr_entity.setScore_type(4);
			usr_entity.setLink_id(ui.getId());
			usr_entity.getMap().put("today_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			List<UserScoreRecord> usr_entityList = this.getFacade().getUserScoreRecordService()
					.getUserScoreRecordList(usr_entity);
			if ((null != usr_entityList) && (usr_entityList.size() == 1)) {
				flag = true;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean JudgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		// 去除 tosh
		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
				"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
				"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
				"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tsm-", "upg1", "upsi", "vk-v", "voda",
				"wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-", "Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}

	public void getSessionId(HttpServletRequest request) {

		String sessionId = request.getSession().getId();
		request.setAttribute("sessionId", sessionId);
	}

	public ActionForward showMsgForManager(HttpServletRequest request, HttpServletResponse response, String msg) {
		super.renderJavaScript(response, "alert('".concat(msg).concat("');history.back();"));
		return null;
	}

	public ActionForward showMsgForCustomer(HttpServletRequest request, HttpServletResponse response, String msg) {
		super.renderJavaScript(response, "alert('" + msg + "');parent.location.href='" + this.getCtxPath(request)
				+ "/manager/customer/index.shtml'");
		return null;
	}

	public void showShippingAddressForOrderInfo(OrderInfo entity) {
		log.info("==showShippingAddressForOrderInfo");
		if (null == entity) {
			return;
		}
		ShippingAddress sd = new ShippingAddress();
		sd.setRel_name(entity.getRel_name());
		sd.setRel_phone(entity.getRel_phone());
		sd.setRel_addr(entity.getRel_addr());
		if (null != entity.getRel_region()) {
			String full_name = getFullNameByPindex(entity.getRel_region());
			sd.getMap().put("full_name", full_name);
		}
		entity.getMap().put("shippingAddress", sd);
	}

	public void showShippingAddressForOrderInfoForRequest(HttpServletRequest request, OrderInfo entity) {
		if (null == entity) {
			return;
		}
		ShippingAddress sd = new ShippingAddress();
		sd.setRel_name(entity.getRel_name());
		sd.setRel_phone(entity.getRel_phone());
		sd.setRel_addr(entity.getRel_addr());
		if (null != entity.getRel_region()) {
			String full_name = getFullNameByPindex(entity.getRel_region());
			sd.getMap().put("full_name", full_name);
		}
		request.setAttribute("shippingAddress", sd);
	}

	public UserInfo getUserInfo(HttpServletRequest request, String user_id) {
		HttpSession session = request.getSession();
		UserInfo userInfoSession = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null != userInfoSession) {
			return userInfoSession;
		} else {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(Integer.valueOf(user_id));
			userInfo = this.getFacade().getUserInfoService().getUserInfo(userInfo);
			setUserInfoToSession(request, userInfo);
			return userInfo;
		}
	}

	public UserInfo getUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfo userInfoSession = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null != userInfoSession) {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userInfoSession.getId());
			userInfo = this.getFacade().getUserInfoService().getUserInfo(userInfo);
			setUserInfoToSession(request, userInfo);
			return userInfo;
		}

		return userInfoSession;
	}

	public UserInfo getUserInfo(Integer user_id) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(Integer.valueOf(user_id));
		userInfo.setIs_del(0);
		userInfo = this.getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null != userInfo) {
			BaseData baseData = this.getBaseData(userInfo.getUser_level());
			if (null != baseData) {
				userInfo.getMap().put("user_level", baseData.getPre_number3());
				userInfo.getMap().put("user_level_name", baseData.getType_name());
				// FIXME FEI
				userInfo.getMap().put("user_level_need_score", baseData.getPre_number());
			}
		}
		return userInfo;
	}

	public UserInfo getUserInfo(String user_id) {
		return this.getUserInfo(Integer.valueOf(user_id));
	}

	public UserInfo getUserInfoWithEntpId(Integer entp_id) {
		UserInfo userInfo = new UserInfo();
		userInfo.setOwn_entp_id(Integer.valueOf(entp_id));
		userInfo.setIs_del(0);
		userInfo = this.getFacade().getUserInfoService().getUserInfo(userInfo);
		return userInfo;
	}

	public UserInfo getUserInfoWithMobile(String mobile) {
		UserInfo userInfo = new UserInfo();
		userInfo.setMobile(mobile);
		userInfo.setIs_del(0);
		userInfo = this.getFacade().getUserInfoService().getUserInfo(userInfo);
		return userInfo;
	}

	public String getFullNameByPindex(Integer p_index) {
		String full_name = "";
		BaseProvince entity = new BaseProvince();
		entity.setIs_del(0);
		entity.setP_index(p_index.longValue());
		entity = getFacade().getBaseProvinceService().getBaseProvince(entity);
		if (null != entity) {
			full_name = entity.getFull_name();
		}
		return full_name;
	}

	// 只插入上一级
	public void setprovinceAndcityAndcountryToFromForPar(DynaBean dynaBean, Integer par_index) {
		if (null != par_index) {
			BaseProvince baseProvince = new BaseProvince();
			baseProvince.setP_index(par_index.longValue());
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			if (null != baseProvince) {
				if (null != baseProvince.getPar_index()) {
					if (baseProvince.getPar_index() == 0) {
						dynaBean.set("province", baseProvince.getP_index().toString());
					} else {
						BaseProvince baseP = new BaseProvince();
						baseP.setP_index(baseProvince.getPar_index());
						baseP = getFacade().getBaseProvinceService().getBaseProvince(baseP);
						if (baseP.getPar_index() == 0) {
							dynaBean.set("province", baseP.getP_index().toString());
							dynaBean.set("city", baseProvince.getP_index().toString());
						} else {
							BaseProvince bp = new BaseProvince();
							bp.setP_index(baseP.getPar_index());
							bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
							dynaBean.set("province", bp.getP_index().toString());
							dynaBean.set("city", baseP.getP_index().toString());
							dynaBean.set("country", baseProvince.getP_index().toString());
						}
					}

				}
			}
		}
	}

	/**
	 * 查询企业(未删除、审核通过)
	 */
	public EntpInfo getEntpInfo(Integer entp_id) {
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.setId(entp_id);
		entpInfo.setAudit_state(Keys.audit_state.audit_state_2.getIndex());
		entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		return entpInfo;
	}

	/**
	 * 查询企业 entp_id,is_del,audit_state
	 */
	public EntpInfo getEntpInfo(Integer entp_id, Integer is_del, Integer audit_state) {
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(is_del);
		entpInfo.setId(entp_id);
		entpInfo.setAudit_state(audit_state);
		entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		return entpInfo;
	}

	/**
	 * 产品类别，根据父类查询指定数量子类
	 *
	 * @param basePdClass
	 * @param count
	 * @return
	 */
	public List<BaseClass> getBaseClassParList(BaseClass basePdClass, Integer cls_scope, Integer count) {
		List<BaseClass> list = new ArrayList<BaseClass>();
		BaseClass basePdClass_2 = new BaseClass();
		basePdClass_2.setPar_id(basePdClass.getCls_id());
		basePdClass_2.setCls_scope(cls_scope);
		basePdClass_2.setIs_del(0);
		basePdClass_2.setIs_show(0);
		if (null != count) {
			basePdClass_2.getRow().setCount(count);
		}

		list = getFacade().getBaseClassService().getBaseClassList(basePdClass_2);
		return list;
	}

	public List<CommInfo> getCommInfoRoot_cls_id(String root_cls_group, String entp_id, String root_cls_id,
			String par_cls_id) {
		List<CommInfo> list = new ArrayList<CommInfo>();
		CommInfo commInfo = new CommInfo();
		if (null != entp_id) {
			commInfo.getMap().put("own_entp_id", entp_id);
		}
		if (null != root_cls_group) {
			commInfo.getMap().put("root_cls_group", true);
		}
		if (null != root_cls_id) {
			commInfo.getMap().put("root_cls_id", root_cls_id);
		}
		if (null != par_cls_id) {
			commInfo.getMap().put("par_cls_id", par_cls_id);
		}
		commInfo.setIs_del(0);
		commInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		commInfo.setIs_sell(Keys.IsSell.IS_SELL_1.getIndex());
		commInfo.setIs_has_tc(1);
		commInfo.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		list = getFacade().getCommInfoService().getCommInfoRootClsId(commInfo);
		return list;
	}

	/**
	 * 产品类别
	 *
	 * @param basePdClass
	 * @param count
	 * @return
	 */
	public BaseClass getBaseClass(Integer cls_id) {
		BaseClass basePdClass_2 = new BaseClass();
		basePdClass_2.setCls_id(cls_id);
		basePdClass_2.setIs_del(0);
		basePdClass_2 = getFacade().getBaseClassService().getBaseClass(basePdClass_2);
		return basePdClass_2;
	}

	public BaseClass getBaseClass(Integer cls_id, Integer type) {
		BaseClass basePdClass_2 = new BaseClass();
		basePdClass_2.setCls_scope(Keys.CLS_SCOPE_TYPE.CLS_SCOPE_2.getIndex());
		basePdClass_2.setCls_id(cls_id);
		basePdClass_2.setIs_del(0);
		basePdClass_2 = getFacade().getBaseClassService().getBaseClass(basePdClass_2);
		return basePdClass_2;
	}

	/**
	 * basePro
	 *
	 * @return
	 */
	public BaseProvince getBaseProvince(Long p_index) {
		BaseProvince temp = new BaseProvince();
		temp.setP_index(p_index);
		temp.setIs_del(0);
		temp = getFacade().getBaseProvinceService().getBaseProvince(temp);
		return temp;
	}

	/**
	 * basePro
	 *
	 * @return
	 */
	public String getJdArea(ShippingAddress shippingAddress) {
		if (null != shippingAddress) {

			// 这个地方需要去查找对于的京东代码
			BaseProvince tempProvince = this.getBaseProvince(Long.valueOf(shippingAddress.getRel_province()));
			BaseProvince tempCity = this.getBaseProvince(Long.valueOf(shippingAddress.getRel_city()));
			BaseProvince tempRegion = this.getBaseProvince(Long.valueOf(shippingAddress.getRel_region()));

			List<String> jd_areasList = new ArrayList<String>();
			jd_areasList.add(tempProvince.getJd_area_id().toString());

			BaseProvince tempRegionFour = new BaseProvince();
			if (shippingAddress.getRel_region_four() != null) {
				tempRegionFour = this.getBaseProvince(shippingAddress.getRel_region_four());
			}

			if (null == tempCity.getJd_area_id()) {// 如果市是空值
				jd_areasList.add(tempRegion.getJd_area_id().toString());
				jd_areasList.add(null != tempRegionFour.getJd_area_id() ? tempRegionFour.getJd_area_id().toString()
						: "");
				jd_areasList.add("");
			} else if (null != tempRegion.getJd_area_id()
					&& tempCity.getJd_area_id().intValue() == tempRegion.getJd_area_id().intValue()) {// 如果发现二级和三级是一样的
				jd_areasList.add(tempRegion.getJd_area_id().toString());
				jd_areasList.add(null != tempRegionFour.getJd_area_id() ? tempRegionFour.getJd_area_id().toString()
						: "");
				jd_areasList.add("");
			} else {
				if (null == tempRegionFour.getJd_area_id()) {// 如果发现四级是空值
					jd_areasList.add(tempCity.getJd_area_id().toString());
					jd_areasList.add(null != tempRegion.getJd_area_id() ? tempRegion.getJd_area_id().toString() : "");
					jd_areasList.add("");
				} else {
					jd_areasList.add(tempCity.getJd_area_id().toString());
					jd_areasList.add(null != tempRegion.getJd_area_id() ? tempRegion.getJd_area_id().toString() : "");
					jd_areasList.add(null != tempRegionFour.getJd_area_id() ? tempRegionFour.getJd_area_id().toString()
							: "");

				}
			}

			return StringUtils.join(jd_areasList, ",");
		}

		return null;

	}

	public String encryptPhone(String phone) {
		String encryptPhone = phone.substring(0, 3).concat("****").concat(phone.substring(7, phone.length()));
		return encryptPhone;
	}

	public String encryptEmail(String email) {
		if (StringUtils.isNotBlank(email)) {
			String[] encryptEmails = email.split("@");
			if (encryptEmails[0].length() > 3) {
				String encryptEmail = encryptEmails[0].substring(0, 3).concat("****").concat(encryptEmails[1]);
				return encryptEmail;
			} else {
				String encryptEmail = encryptEmails[0].concat("****").concat(encryptEmails[1]);
				return encryptEmail;
			}
		} else {
			return null;
		}
	}

	public String encryptIdCard(String idCard) {
		if (StringUtils.isNotBlank(idCard)) {
			if (idCard.length() >= 18) {
				String encryptIdCard = idCard.substring(0, 6).concat("********")
						.concat(idCard.substring(14, idCard.length()));
				return encryptIdCard;
			} else {
				return idCard;
			}
		} else {
			return null;
		}
	}

	public String encryptBankAccount(String bank) {
		if (StringUtils.isNotBlank(bank)) {
			if (bank.length() >= 16) {
				String encryptIdCard = bank.substring(0, 5).concat("***********")
						.concat(bank.substring(16, bank.length()));
				return encryptIdCard;
			} else {
				return bank;
			}
		} else {
			return null;
		}
	}

	/**
	 * 去自定义数据表Base_link表
	 *
	 * @param link_type
	 * @param count
	 * @return
	 */
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

	public BaseData getBaseData(Integer id) {
		BaseData baseData = new BaseData();
		baseData.setId(id);
		baseData.setIs_del(0);
		baseData = getFacade().getBaseDataService().getBaseData(baseData);
		return baseData;
	}

	public BaseData getBaseDataFromApplation(int id) {
		BaseData baseData = new BaseData();
		for (BaseData bd : Keys.keysBaseData900List) {
			if (bd.getId().intValue() == id) {
				baseData = bd;
				break;
			}
		}
		return baseData;
	}

	/**
	 * 各种币的之间的兑换
	 *
	 * @return
	 */
	public BigDecimal BiToBi(BigDecimal bi_old, Integer baseDataId) {
		BaseData baseData = this.getBaseDataFromApplation(baseDataId);
		BigDecimal new_bi = bi_old.multiply(new BigDecimal(baseData.getPre_number()).divide(new BigDecimal(baseData
				.getPre_number2())));
		return new_bi;

	}

	// 反过来计算的 和BiToBi相反
	public BigDecimal BiToBi2(BigDecimal bi_old, Integer baseDataId) {
		BaseData baseData = this.getBaseDataFromApplation(baseDataId);
		BigDecimal new_bi = bi_old.multiply(new BigDecimal(baseData.getPre_number2()).divide(new BigDecimal(baseData
				.getPre_number())));
		return new_bi;
	}

	/**
	 * @author Wu, Yang
	 * @version 2015-12-29
	 * @desc 获取用户等级 返回0-10，从BaseData中取
	 */
	public int getUserLevel(int user_level) {
		BaseData uiBaseData = new BaseData();
		uiBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		uiBaseData.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		uiBaseData.setId(user_level);
		uiBaseData = getFacade().getBaseDataService().getBaseData(uiBaseData);
		return uiBaseData.getPre_number3();
	}

	public void setUserInfoToSession(HttpServletRequest request, UserInfo userInfo) {
		if (null != userInfo) {
			HttpSession session = request.getSession();
			int user_level = getUserLevel(userInfo.getUser_level());
			userInfo.getMap().put("user_level", user_level);// 设置用户等级
			session.setAttribute(Keys.SESSION_USERINFO_KEY, userInfo);
		}
	}

	/**
	 * 发送站内信action版本
	 */
	public void sendMsg(Integer src_uid, Integer dest_uid, String msg_title, String msg_content) {
		try {
			String dest_uname = "";
			UserInfo desc_ui = this.getUserInfo(dest_uid);
			if (null == desc_ui) {
				return;
			}
			dest_uname = desc_ui.getUser_name() + "(" + desc_ui.getMobile() + ")";

			String src_uname = "站内通知";
			if (src_uid.intValue() != 1) {// 不是系统管理员
				UserInfo src_ui = this.getUserInfo(src_uid);
				if (null != src_ui) {
					src_uname = src_ui.getUser_name();
				}
			}
			Msg msg = new Msg();
			msg.setMsg_type(Keys.MSG_TYPE.MSG_TYPE_0.getIndex());
			msg.setMsg_title(msg_title);
			msg.setMsg_content(msg_content);
			msg.setUser_id(src_uid);
			msg.setUser_name(src_uname);
			msg.setSend_time(new Date());
			msg.setInfo_state(1);

			List<MsgReceiver> mrList = new ArrayList<MsgReceiver>();
			MsgReceiver mr = new MsgReceiver();
			mr.setUser_id(src_uid);
			mr.setReceiver_user_id(dest_uid);
			mr.setReceiver_user_mobile(dest_uname);
			mr.setIs_del(0);
			mr.setIs_read(0);
			mr.setIs_reply(0);
			mrList.add(mr);
			msg.setMsgReceiverList(mrList);
			getFacade().getMsgService().createMsg(msg);
		} catch (Exception e) {
			logger.info("==SendMsg Error:{}", e.getMessage());
		}

	}

	public void updateOrderInfoOpt(Integer is_opt, Integer order_id) {
		OrderInfo updateIsOpt = new OrderInfo();
		updateIsOpt.setId(order_id);
		updateIsOpt.setIs_opt(is_opt);
		this.getFacade().getOrderInfoService().modifyOrderInfo(updateIsOpt);
	}

	public int createEntpNo(Integer entp_id) {
		EntpInfo entpInfoCount = new EntpInfo();
		entpInfoCount.setAudit_state(Keys.audit_state.audit_state_2.getIndex());
		entpInfoCount.getMap().put("is_not_id", entp_id);
		entpInfoCount.getMap().put("audit_today_date", sdFormat_ymd.format(new Date()));
		int count = this.getFacade().getEntpInfoService().getEntpInfoCount(entpInfoCount);

		String sdFormatymdForNo1 = this.sdFormatymdForNo.format(new Date()).toString();
		sdFormatymdForNo1 = sdFormatymdForNo1 + StringUtils.leftPad(String.valueOf(count), 4, "0");
		int sdFormatymdForNoInt = Integer.valueOf(sdFormatymdForNo1) + 1;
		return sdFormatymdForNoInt;
	}

	public int createServiceCenterNo(Integer entp_id) {
		ServiceCenterInfo serviceCenterInfoCount = new ServiceCenterInfo();
		// serviceCenterInfoCount.setAudit_state(1);
		serviceCenterInfoCount.getMap().put("id_not_in", entp_id);
		serviceCenterInfoCount.getMap().put("servicecenter_no_not_null", "true");
		// serviceCenterInfoCount.getMap().put("pay_date_today",
		// sdFormat_ymd.format(new Date()));
		serviceCenterInfoCount.getMap().put("audit_today_date", sdFormat_ymd.format(new Date()));
		int count = this.getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(serviceCenterInfoCount);

		String sdFormatymdForNo1 = this.sdFormatymdForNo.format(new Date());
		sdFormatymdForNo1 = sdFormatymdForNo1 + StringUtils.leftPad(String.valueOf(count), 3, "0");
		int sdFormatymdForNoInt = Integer.valueOf(sdFormatymdForNo1) + 1;
		return sdFormatymdForNoInt;
	}

	public int createServiceCenterNo(Integer entp_id, Integer is_virtual) {
		ServiceCenterInfo serviceCenterInfoCount = new ServiceCenterInfo();
		// serviceCenterInfoCount.setAudit_state(1);
		serviceCenterInfoCount.setIs_virtual(is_virtual);
		serviceCenterInfoCount.getMap().put("id_not_in", entp_id);
		serviceCenterInfoCount.getMap().put("servicecenter_no_not_null", "true");
		// serviceCenterInfoCount.getMap().put("pay_date_today",
		// sdFormat_ymd.format(new Date()));
		serviceCenterInfoCount.getMap().put("audit_today_date", sdFormat_ymd.format(new Date()));
		int count = this.getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(serviceCenterInfoCount);

		String sdFormatymdForNo1 = this.sdFormatymdForNo.format(new Date());
		sdFormatymdForNo1 = sdFormatymdForNo1 + StringUtils.leftPad(String.valueOf(count), 3, "0");
		int sdFormatymdForNoInt = Integer.valueOf(sdFormatymdForNo1) + 1;
		return sdFormatymdForNoInt;
	}

	public int createVillageNo(Integer village_id) {
		VillageInfo villageInfo = new VillageInfo();
		villageInfo.setAudit_state(1);
		villageInfo.getMap().put("id_not_in", village_id);
		villageInfo.getMap().put("pay_date_today", sdFormat_ymd.format(new Date()));
		int count = this.getFacade().getVillageInfoService().getVillageInfoCount(villageInfo);

		String sdFormatymdForNo1 = this.sdFormatymdForNo.format(new Date());
		sdFormatymdForNo1 = sdFormatymdForNo1 + StringUtils.leftPad(String.valueOf(count), 3, "0");
		int sdFormatymdForNoInt = Integer.valueOf(sdFormatymdForNo1) + 1;
		return sdFormatymdForNoInt;
	}

	public int createServiceCenterNoForRepairData(int servicecenter_level, Integer entp_id, Date pay_date) {
		ServiceCenterInfo serviceCenterInfoCount = new ServiceCenterInfo();
		serviceCenterInfoCount.setServicecenter_level(servicecenter_level);
		serviceCenterInfoCount.setAudit_state(1);
		serviceCenterInfoCount.setPay_success(1);
		serviceCenterInfoCount.getMap().put("id_not_in", entp_id);
		serviceCenterInfoCount.getMap().put("pay_date_today", sdFormat_ymd.format(pay_date));
		int count = this.getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(serviceCenterInfoCount);

		String sdFormatymdForNo1 = this.sdFormatymdForNo.format(pay_date);
		sdFormatymdForNo1 = sdFormatymdForNo1 + StringUtils.leftPad(String.valueOf(count), 3, "0");
		int sdFormatymdForNoInt = Integer.valueOf(sdFormatymdForNo1) + 1;
		return sdFormatymdForNoInt;
	}

	// 获取类别列表，手机端用,后面改成 放到applacation中
	@SuppressWarnings("unchecked")
	public void setSlideNavForM(HttpServletRequest request) {

		ServletContext application = request.getSession().getServletContext();
		// List<BaseLink> baseLink20List = this.getBaseLinkList(20, 9, null);
		// for (BaseLink bi : baseLink20List) {
		// if (null != bi.getContent() &&
		// GenericValidator.isInt(bi.getContent())) {
		// Integer c_id = Integer.valueOf(bi.getContent());
		// BaseClass baseClassSon = new BaseClass();
		// baseClassSon.setPar_id(c_id);
		// baseClassSon.setIs_del(0);
		// List<BaseClass> baseClassSonList =
		// this.getFacade().getBaseClassService()
		// .getBaseClassList(baseClassSon);
		// bi.setBaseClassList(baseClassSonList);
		// }
		// }
		if (null != application.getAttribute(Keys.slideNavList)) {
			List<BaseLink> baseLink20List = (List<BaseLink>) application.getAttribute(Keys.slideNavList);
			request.setAttribute(Keys.slideNavList, baseLink20List);
		}
		// <section class="select"> <a data-main="category_300"> <span
		// class="hotel">酒店</span> <em
		// class="more">1631</em> </a> </section>
		// <section> <a data-main="category_481"> <span
		// class="edu">教育培训</span></a></section>
		// <section> <a data-main="category_400"> <span class="tour">旅游</span>
		// </a> </section>
		// <section> <a data-main="category_442"> <span class="beauty">丽人</span>
		// </a></section>
		// <section> <a data-main="category_29"> <span class="movis">电影</span>
		// </a> </section>
		// <section> <a data-main="category_37"> <span
		// class="sheying">摄影写真</span> </a></section>
		// <section> <a data-main="category_46"> <span class="life">生活服务</span>
		// </a> </section>
		// <section> <a data-main="category_2"> <span class="yule">休闲娱乐</span>
		// </a> </section>
		// <section> <a data-main="category_99"> <span class="foods">美食</span>
		// </a>
		// </section><a data-main="category_99"> <span class="foods">美食</span>
		// </a> </section>
	}

	/**
	 * 更新用户的金额相关信息
	 */
	public int updateUserInfoBi(Integer user_id, BigDecimal money, String chu_or_ru) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(user_id);
		userInfo.getMap().put(chu_or_ru, money);
		return this.facade.getUserInfoService().modifyUserInfo(userInfo);
	}

	/**
	 * 插入用户币记录UserInfo ui_dest,
	 */
	public int insertUserBiRecord(Integer add_user_id, UserInfo ui_dest, Integer chu_or_ru, Integer link_id,
			BigDecimal bi_no, Integer bi_type, Integer get_tpye) {
		UserInfo ui = this.getUserInfo(add_user_id);

		UserBiRecord userBiRecord = new UserBiRecord();
		userBiRecord.setAdd_user_id(add_user_id);
		userBiRecord.setAdd_uname(ui.getUser_name());
		userBiRecord.setAdd_date(new Date());
		userBiRecord.setBi_chu_or_ru(chu_or_ru);
		if (null != link_id) {
			userBiRecord.setLink_id(link_id);
			userBiRecord.setOrder_id(link_id);
		}

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_100.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_dianzi());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_dianzi().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_dianzi().subtract(bi_no));
			}
		}

		// TAG 20160107
		if (bi_type.intValue() == Keys.BiType.BI_TYPE_300.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_huokuan());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_huokuan().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_huokuan().subtract(bi_no));
			}
		}

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_500.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_aid());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_aid().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_aid().subtract(bi_no));
			}
		}
		if (bi_type.intValue() == Keys.BiType.BI_TYPE_700.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_welfare());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_welfare().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_welfare().subtract(bi_no));
			}
		}

		userBiRecord.setBi_type(bi_type);
		String bi_get_type_name = "";
		for (BiGetType temp : Keys.BiGetType.values()) {
			if (get_tpye == temp.getIndex()) {
				bi_get_type_name = temp.getName();
				break;
			}
		}
		userBiRecord.setBi_get_type(get_tpye);
		userBiRecord.setBi_get_memo(bi_get_type_name);

		if (null != ui_dest) {// 余额转让记录
			Double bi_rate = (Double) ui_dest.getMap().get("bi_rate");
			userBiRecord.setDest_uid(ui_dest.getId());
			userBiRecord.setDest_uname(ui_dest.getUser_name());
			userBiRecord.setBi_rate(new BigDecimal(bi_rate));
		}
		return this.facade.getUserBiRecordService().createUserBiRecord(userBiRecord);
	}

	public String ajaxReturnInfo(HttpServletResponse response, String code, String msg, Object datas) throws Exception {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("datas", datas);
		String jsonsring = json.toJSONString();
		logger.info("jsonsring:{}", jsonsring);
		super.renderJson(response, jsonsring);
		return null;
	}

	/**
	 * CommInfo
	 *
	 * @param CommInfo
	 * @return
	 */
	public CommInfo getCommInfo(Integer comm_id) {
		CommInfo entity = new CommInfo();
		entity.setId(comm_id);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.setIs_sell(1);
		entity = getFacade().getCommInfoService().getCommInfo(entity);
		return entity;
	}

	/**
	 * CommInfo
	 *
	 * @param CommInfo
	 * @return
	 */
	public CommInfo getCommInfoOnlyById(Integer comm_id) {
		CommInfo entity = new CommInfo();
		entity.setId(comm_id);
		entity = getFacade().getCommInfoService().getCommInfo(entity);
		return entity;
	}

	public CommInfo getCommInfo(Integer id, Integer comm_type) {
		CommInfo commInfo = new CommInfo();
		commInfo.setIs_del(0);
		commInfo.setId(id);
		commInfo.setComm_type(comm_type);
		commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
		return commInfo;
	}

	public Freight getFreightInfo(Integer fre_id) {
		Freight entity = new Freight();
		entity.setId(fre_id);
		entity = getFacade().getFreightService().getFreight(entity);
		return entity;
	}

	public BaseBrandInfo getBaseBrandInfo(Integer brand_id) {
		BaseBrandInfo baseBrandInfo = new BaseBrandInfo();
		baseBrandInfo.setBrand_id(brand_id);
		baseBrandInfo.setIs_del(0);
		baseBrandInfo = getFacade().getBaseBrandInfoService().getBaseBrandInfo(baseBrandInfo);
		return baseBrandInfo;
	}

	/**
	 * @author Wu, Yang
	 * @date 2011-09-06 生成订单交易流水号,长度：22位，（前17位：4年2月2日2时2分2秒3毫秒） + （后三位：3SEQ）
	 * @desc 后三位根据SEQ_ORDER_INFO_TRADE_INDEX生成，最大999循环生成
	 */
	public String creatTradeIndex() {
		// BigDecimal trade_index =
		// getFacade().getOrderInfoService().genOrderInfoTradeIndex(new
		// OrderInfo());
		String trade_no = sdFormatymdhmsS.format(new Date());

		return trade_no;
	}

	public void setEntpBaseClassList(HttpServletRequest request) {
		BaseClass baseClass = new BaseClass();
		baseClass.setCls_id(1);
		List<BaseClass> baseHyClassList = this.getBaseClassParList(baseClass, 1, null);
		request.setAttribute("baseHyClassList", baseHyClassList);

		baseClass = new BaseClass();
		baseClass.setCls_id(20000);
		baseHyClassList = this.getBaseClassParList(baseClass, 2, null);
		request.setAttribute("baseXianxiaClassList", baseHyClassList);
	}

	public void setAuditCommInfo(ActionForm form, HttpServletRequest request, UserInfo userInfo) {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String is_del = (String) dynaBean.get("is_del");
		String comm_no_like = (String) dynaBean.get("comm_no_like");
		String audit_state = (String) dynaBean.get("audit_state");
		String cls_id = (String) dynaBean.get("cls_id");
		String comm_type = (String) dynaBean.get("comm_type");
		String country = (String) dynaBean.get("country");
		String city = (String) dynaBean.get("city");
		String province = (String) dynaBean.get("province");

		ServiceCenterInfo sci = new ServiceCenterInfo();
		sci.setAdd_user_id(userInfo.getId());
		sci = getFacade().getServiceCenterInfoService().getServiceCenterInfo(sci);
		if (null != sci) {
			request.setAttribute("level", sci.getServicecenter_level());
		}

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);

		entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());

		if (StringUtils.isNotBlank(country)) {
			entity.setP_index(Integer.valueOf(country));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_city", StringUtils.substring(city, 0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_province", StringUtils.substring(province, 0, 2));
		}

		if (StringUtils.isNotBlank(comm_type)) {
			entity.setComm_type(Integer.valueOf(comm_type));
			dynaBean.set("comm_type", comm_type);
		}
		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.parseInt(audit_state));
		}
		if (StringUtils.isNotBlank(is_del)) {
			if ("-1".equals(is_del)) {
				entity.setIs_del(null);
			}
			if ("0".equals(is_del)) {
				entity.setIs_del(0);
			}
			if ("1".equals(is_del)) {
				entity.setIs_del(1);
			}
		} else {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(comm_no_like)) {
			entity.getMap().put("comm_no_like", comm_no_like);
		}

		if (StringUtils.isNotBlank(cls_id) && !cls_id.equals("1")) {
			entity.setCls_id(null);
			entity.getMap().put("allPd", "true");
			entity.getMap().put("par_cls_id", cls_id);
		}

		// entity.setOwn_entp_id(userInfo.getOwn_entp_id());
		entity.getMap().put("order_value", true);

		// ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		// serviceCenterInfo.setIs_del(0);
		// serviceCenterInfo.setAudit_state(1);
		// serviceCenterInfo.setEffect_state(1);
		// serviceCenterInfo.setAdd_user_id(userInfo.getId());
		// serviceCenterInfo =
		// getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
		// if (null != serviceCenterInfo) {
		// if (null != serviceCenterInfo.getP_index()) {
		// if (serviceCenterInfo.getP_index().toString().endsWith("0000")) {
		// entity.getMap().put("p_index_like",
		// serviceCenterInfo.getP_index().toString().substring(0, 2));
		// } else if (serviceCenterInfo.getP_index().toString().endsWith("00"))
		// {
		// entity.getMap().put("p_index_like",
		// serviceCenterInfo.getP_index().toString().substring(0, 4));
		// } else {
		// entity.setP_index(serviceCenterInfo.getP_index());
		// }
		// }
		// }

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (CommInfo ci : entityList) {
				// 套餐管理
				CommTczhPrice param_ctp = new CommTczhPrice();
				param_ctp.setComm_id(ci.getId().toString());
				param_ctp.getMap().put("order_by_inventory_asc", "true");
				List<CommTczhPrice> CommTczhPriceList = getFacade().getCommTczhPriceService().getCommTczhPriceList(
						param_ctp);
				if ((null != CommTczhPriceList) && (CommTczhPriceList.size() > 0)) {
					ci.getMap().put("count_tczh_price_inventory", CommTczhPriceList.get(0).getInventory());
				}
			}
		}
		request.setAttribute("entityList", entityList);

		List<CommType> commTypeList = new ArrayList<Keys.CommType>();
		commTypeList.add(Keys.CommType.valueOf(Keys.CommType.COMM_TYPE_2.getSonType().toString()));
		// commTypeList.add(Keys.CommType.valueOf(Keys.CommType.COMM_TYPE_3.getSonType().toString()));

		request.setAttribute("commTypeList", commTypeList);
	}

	public DynaBean setAuditEntpInfo(ActionForm form, HttpServletRequest request, UserInfo ui)
			throws NumberFormatException {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		String is_del = (String) dynaBean.get("is_del");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String today_date = (String) dynaBean.get("today_date");

		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("entp_name_like", entp_name_like);
		if (null == is_del) {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(today_date)) {
			entity.getMap().put("today_date", today_date);
		}

		UserInfo userInfo = getUserInfo(ui.getId());
		request.setAttribute("userInfoTemp", userInfo);

		String p_index = "";
		String p_index_like = "";
		if (userInfo.getIs_fuwu() == 1) { // 区域合伙人

			ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
			serviceCenterInfo.setIs_del(0);
			serviceCenterInfo.setAudit_state(1);
			serviceCenterInfo.setEffect_state(1);
			serviceCenterInfo.setAdd_user_id(ui.getId());
			serviceCenterInfo = getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
			if (null != serviceCenterInfo) {
				if (null != serviceCenterInfo.getP_index()) {
					if (serviceCenterInfo.getP_index().toString().endsWith("0000")) {
						p_index_like = serviceCenterInfo.getP_index().toString().substring(0, 2);
						entity.getMap().put("p_index_like", p_index_like);
					} else if (serviceCenterInfo.getP_index().toString().endsWith("00")) {
						p_index_like = serviceCenterInfo.getP_index().toString().substring(0, 4);
						entity.getMap().put("p_index_like", p_index_like);
					} else {
						entity.setP_index(serviceCenterInfo.getP_index());
						p_index = serviceCenterInfo.getP_index().toString();
					}
				}
			}
		}

		entity.getMap().put("orderByInfo", " a.update_date desc, ");
		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<EntpInfo> list = getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);

		request.setAttribute("entityList", list);
		return dynaBean;
	}

	public YhqInfoSon getYhqInfoSon(Integer id, Integer yhq_state) {
		YhqInfoSon entity = new YhqInfoSon();
		entity.setId(id);
		entity.setYhq_state(yhq_state);
		entity = getFacade().getYhqInfoSonService().getYhqInfoSon(entity);
		return entity;
	}

	public YhqInfo getYhqInfo(Integer yhq_id) {
		YhqInfo entity = new YhqInfo();
		entity.setId(yhq_id);
		entity.setIs_del(0);
		entity = getFacade().getYhqInfoService().getYhqInfo(entity);
		return entity;
	}

	public BigDecimal getEntpDistance(EntpInfo b) throws NumberFormatException {
		if (null != b.getMap().get("distance").toString()) {
			String distance = b.getMap().get("distance").toString();
			int index = distance.indexOf(".");
			distance = distance.substring(0, index);
			Integer int_distance = Integer.valueOf(distance);
			BigDecimal big_distance = new BigDecimal(int_distance);
			big_distance = big_distance.divide(new BigDecimal(1000));
			return big_distance;
		}

		return null;
	}

	// 合伙人销售额
	public void getServiceCenterInfoSaleMoney(HttpServletRequest request, DynaBean dynaBean,
			ServiceCenterInfo serviceCenterInfo) throws ParseException {
		String sereach_servecenter_st_date = (String) dynaBean.get("sereach_servecenter_st_date");
		String sereach_servecenter_en_date = (String) dynaBean.get("sereach_servecenter_en_date");
		logger.info("==sereach_servecenter_st_date:" + sereach_servecenter_st_date);
		logger.info("==sereach_servecenter_en_date:" + sereach_servecenter_en_date);

		ServiceCenterInfo serviceSaleMoney = new ServiceCenterInfo();
		serviceSaleMoney.getMap().put("left_join", true);
		serviceSaleMoney.getMap().put("link_id", serviceCenterInfo.getId());
		if (StringUtils.isNotBlank(sereach_servecenter_st_date) && StringUtils.isNotBlank(sereach_servecenter_en_date)) {
			// 选择时间年-月
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date st_date = sdf.parse(sereach_servecenter_st_date);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			Date en_date = sdf1.parse(sereach_servecenter_en_date);
			// logger.info("==en_date:" + en_date);
			request.setAttribute("sereach_servecenter_st_date", st_date);
			request.setAttribute("sereach_servecenter_en_date", en_date);
			logger.info("==st_date:" + st_date);
			logger.info("==en_date:" + en_date);
			serviceSaleMoney.getMap().put("sereach_servecenter_st_date", st_date);
			serviceSaleMoney.getMap().put("sereach_servecenter_en_date", en_date);
		}

		logger.info("====合伙人排名====");
		List<ServiceCenterInfo> list = getFacade().getServiceCenterInfoService().getServiceCenterInfoPaiMingList(
				serviceSaleMoney);
		if (null != list && list.size() > 0) {
			request.setAttribute("serviceSaleMoney", list.get(0).getMap().get("entp_sale_money"));
		} else {
			request.setAttribute("serviceSaleMoney", 0);
		}
	}

	public String dateForString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String audit_date = sdf.format(date);
		return audit_date;
	}

	// 合伙人下商家数量
	public void setServiceEntpCount(HttpServletRequest request, String p_index_like) {
		UserInfo userInfo = new UserInfo();
		userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userInfo.setIs_entp(new Integer(1));
		userInfo.getMap().put("right_join_entp_info", "true");
		userInfo.getMap().put("entp_p_index_like", p_index_like);
		Integer userInfoCountEntp = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		request.setAttribute("userInfoCountEntp", userInfoCountEntp);
		userInfo.getMap().put("today_date_audit", new Date());
		Integer userInfoCountEntpToday = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		request.setAttribute("userInfoCountEntpToday", userInfoCountEntpToday);
	}

	// 合伙人下联盟数量
	public void setServiceLianmengCount(HttpServletRequest request, String p_index_like) {
		UserInfo userInfo = new UserInfo();
		userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userInfo.setIs_lianmeng(new Integer(1));
		userInfo.getMap().put("p_index_like", p_index_like);
		Integer userInfoCountLianMeng = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		request.setAttribute("userInfoCountLianMeng", userInfoCountLianMeng);
		userInfo.getMap().put("eq_is_lianmeng_date", new Date());
		Integer userInfoCountLianMengToday = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		request.setAttribute("userInfoCountLianMengToday", userInfoCountLianMengToday);
	}

	// 合伙人下会员数量
	public void setServiceUserCount(HttpServletRequest request, String p_index_like) {
		UserInfo userInfo = new UserInfo();
		userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userInfo.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
		userInfo.getMap().put("p_index_like", p_index_like);
		Integer userInfoCountVip = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		request.setAttribute("userInfoCountVip", userInfoCountVip);
		userInfo.getMap().put("today_date", new Date());
		Integer userInfoCountVipToday = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		request.setAttribute("userInfoCountVipToday", userInfoCountVipToday);
	}

	// 合伙人下合伙人数量
	public void setServiceServiceCount(HttpServletRequest request, String p_index_like) {
		UserInfo userInfo = new UserInfo();
		userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userInfo.setIs_fuwu(new Integer(1));
		userInfo.getMap().put("p_index_like", p_index_like);
		Integer userInfoCountFuwu = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		request.setAttribute("userInfoCountFuwu", userInfoCountFuwu);
		userInfo.getMap().put("today_date", new Date());
		Integer userInfoCountFuwuToday = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		request.setAttribute("userInfoCountFuwuToday", userInfoCountFuwuToday);
	}

	// 区域商品
	public void setServiceCommInfo(HttpServletRequest request, ServiceCenterInfo serviceCenterInfo) {
		if (serviceCenterInfo.getServicecenter_level() != null
				&& serviceCenterInfo.getServicecenter_level().intValue() == 3 && serviceCenterInfo.getP_index() != null) {

			CommInfo commInfo = new CommInfo();
			commInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			commInfo.setP_index(serviceCenterInfo.getP_index());
			Integer commInfoCount = getFacade().getCommInfoService().getCommInfoCount(commInfo);
			commInfo.getMap().put("today_date", new Date());
			Integer commInfoCountToday = getFacade().getCommInfoService().getCommInfoCount(commInfo);
			request.setAttribute("commInfoCount", commInfoCount);
			request.setAttribute("commInfoCountToday", commInfoCountToday);
		}
	}

	public void setRequestCommentPic(HttpServletRequest request, CommentInfo commentInfo) {
		if (commentInfo.getHas_pic().intValue() == 1) {

			BaseFiles baseFiles = new BaseFiles();
			baseFiles.setLink_id(commentInfo.getId());
			baseFiles.setLink_tab("CommentInfo");
			baseFiles.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			baseFiles.setType(Keys.BaseFilesType.Base_Files_TYPE_40.getIndex());
			List<BaseFiles> baseFilesList = getFacade().getBaseFilesService().getBaseFilesList(baseFiles);
			if (null != baseFilesList && baseFilesList.size() > 0) {
				Integer size = 5 - baseFilesList.size();
				for (int i = 0; i < size; i++) {
					baseFilesList.add(new BaseFiles());
				}
				request.setAttribute("baseFilesList", baseFilesList);
			}

		}
	}

	public void setRequestCommentInfoSonList(HttpServletRequest request, Integer id) {
		CommentInfoSon commentInfoSon = new CommentInfoSon();
		commentInfoSon.setPar_id(id);
		commentInfoSon.setIs_del(0);
		List<CommentInfoSon> commentInfoSonList = getFacade().getCommentInfoSonService().getCommentInfoSonList(
				commentInfoSon);
		if (null != commentInfoSonList && commentInfoSonList.size() > 0) {
			request.setAttribute("commentInfoSonList", commentInfoSonList);
		}
	}

	public OrderInfoDetails getOrderInfoDetails(CommentInfo entity) {
		if (null != entity.getOrder_details_id()) {
			OrderInfoDetails ods = new OrderInfoDetails();
			ods.setId(entity.getOrder_details_id());
			ods = getFacade().getOrderInfoDetailsService().getOrderInfoDetails(ods);
			return ods;
		}
		return null;
	}

	public Double setCommTgInfo(CartInfo entity, Double pd_price) {
		CommInfoPromotion commTG = new CommInfoPromotion();
		commTG.setId(entity.getComm_tg_id());
		commTG.setIs_del(0);
		commTG.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		commTG.getMap().put("end_time_gt_new_date", "true");
		commTG = getFacade().getCommInfoPromotionService().getCommInfoPromotion(commTG);
		if (null != commTG) {
			pd_price = commTG.getProm_price().doubleValue();
		}
		return pd_price;
	}

	public Point getXY(String x, String y) {
		/** 经纬度转换为百度地图的经纬度 开始 **/
		Double d = 0.0;
		Double l = 0.0;

		if (StringUtils.isNotBlank(x) && StringUtils.isNotBlank(y)) {

			try {
				d = new Double(x);
			} catch (NumberFormatException ex) {
			}

			try {
				l = new Double(y);
			} catch (NumberFormatException ex) {
			}

			Point point = CoordinateConversion.google_bd_encrypt(d, l);

			/** 经纬度转换为百度地图的经纬度 结束 **/

			log.info(point);

			Keys.x = String.valueOf(point.getLat());
			Keys.y = String.valueOf(point.getLng());
			logger.info("转换后的x:" + Keys.x);
			logger.info("转换后的y:" + Keys.y);
			return point;
		}
		return null;

	}

	public void setMapCommentSonList(CommentInfo commentInfo) {
		CommentInfoSon son = new CommentInfoSon();
		son.setPar_id(commentInfo.getId());
		son.setIs_del(0);
		son.getMap().put("group_by_id_asc", true);
		List<CommentInfoSon> commentInfoSonList = getFacade().getCommentInfoSonService().getCommentInfoSonList(son);
		if (null != commentInfoSonList && commentInfoSonList.size() > 0) {
			commentInfo.getMap().put("commentInfoSonList", commentInfoSonList);
		}
	}

	/*
	 * 获取当前时间之前或之后几小时 hour
	 */

	public static String getTimeByHour(int hour) {

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

	}

	/*
	 * 获取当前时间之前或之后几分钟 minute
	 */

	public static Date getTimeByMinute(int minute) {

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();

	}

	// 验证商家名称
	public int validateEntpName(String entp_id, String entp_name) {
		EntpInfo entpName = new EntpInfo();
		entpName.setEntp_name(entp_name);
		entpName.setIs_del(0);
		if (StringUtils.isNotBlank(entp_id)) {
			entpName.getMap().put("id_not_in", entp_id);
		}
		int count = getFacade().getEntpInfoService().getEntpInfoCount(entpName);
		return count;
	}

	// 验证商品名称
	public int validateCommName(String id, String comm_name) {
		CommInfo commInfoName = new CommInfo();
		commInfoName.setComm_name(comm_name);
		commInfoName.setIs_del(0);
		if (StringUtils.isNotBlank(id)) {
			commInfoName.getMap().put("id_not_in", id);
		}
		int count = getFacade().getCommInfoService().getCommInfoCount(commInfoName);
		return count;
	}

	// 验证商品名称
	public int validateCommName(String id, String comm_name, Integer own_entp_id) {
		CommInfo commInfoName = new CommInfo();
		commInfoName.setComm_name(comm_name);
		commInfoName.setIs_del(0);
		commInfoName.setIs_sell(1);
		commInfoName.setOwn_entp_id(own_entp_id);
		if (StringUtils.isNotBlank(id)) {
			commInfoName.getMap().put("id_not_in", id);
			commInfoName.setId(Integer.valueOf(id));
		}
		int count = getFacade().getCommInfoService().getCommInfoNameCount(commInfoName);
		return count;
	}

	// 判断是否有未审核通过的申请
	public void setApplyEntpIsAudit(HttpServletRequest request, UserInfo ui) {
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setAdd_user_id(ui.getId());
		entpInfo.setAudit_state(Keys.audit_state.audit_state_fu_2.getIndex());
		entpInfo.setIs_del(0);
		int count = getFacade().getEntpInfoService().getEntpInfoCount(entpInfo);
		if (count > 0) {
			request.setAttribute("entp_not_audit", count);
			Msg c = new Msg();
			c.setSend_user_id(entpInfo.getAdd_user_id());
			List<Msg> c_list = getFacade().getMsgService().getMsgList(c);
			if (null != c_list && c_list.size() > 0) {
				request.setAttribute("msg", c_list.get(0));
			}
		}
	}

	// 获取商品名称
	public String getCommInfoName(Integer comm_id) {
		CommInfo a = new CommInfo();
		a.setId(comm_id);
		a.setIs_del(0);
		a = getFacade().getCommInfoService().getCommInfo(a);
		if (null != a) {
			return a.getComm_name();
		}
		return "";
	}

	// 获取class名称
	public String getClassName(Integer comm_id) {
		BaseClass baseClass = new BaseClass();
		baseClass.setCls_id(comm_id);
		baseClass.setIs_del(0);
		baseClass = getFacade().getBaseClassService().getBaseClass(baseClass);
		if (null != baseClass) {
			return baseClass.getCls_name();
		}
		return "";
	}

	/**
	 * @return 返回 币跟人民币 兑换比例
	 **/

	public Integer getBiToRmbScale(Integer id) {
		Integer bi_to_rmb_scale = 0;
		BaseData basedata = this.getBaseData(id);
		if (null != basedata) {
			bi_to_rmb_scale = basedata.getPre_number2() / basedata.getPre_number();
		}
		return bi_to_rmb_scale;
	}

	/**
	 * @return 购物车Step0方法公用条件
	 **/
	public void setWhereCartInfoStep0(HttpServletRequest request) {
		request.setAttribute("bi_to_rmb_scale", this.getBiToRmbScale(Keys.BASE_DATA_ID.BASE_DATA_ID_907.getIndex()));
	}

	/**
	 * @计算出 商品可使用红包最大金额
	 **/
	public BigDecimal getCommMaxRedMoney(CartInfo ci, CommInfo commInfo) {
		BigDecimal red_scale = new BigDecimal(0);
		red_scale = new BigDecimal(commInfo.getRed_scale()).divide(new BigDecimal(100));
		BigDecimal comm_max_red_money = new BigDecimal(ci.getPd_count()).multiply(ci.getPd_price().multiply(red_scale));
		return comm_max_red_money;
	}

	/**
	 * @计算出当前用户已提现金额
	 **/
	public BigDecimal getHasTiXianMoney(HttpServletRequest request, UserInfo userInfo) {
		BigDecimal has_tixian_money = new BigDecimal(0);
		UserBiRecord uRecord = new UserBiRecord();
		uRecord.setAdd_user_id(userInfo.getId());
		uRecord.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_NEGATIVE1.getIndex());
		uRecord.setBi_type(Keys.BiType.BI_TYPE_100.getIndex());
		uRecord.setBi_get_type(Keys.BiGetType.BI_OUT_TYPE_50.getIndex());
		List<UserBiRecord> recordList = getFacade().getUserBiRecordService().getUserBiRecordList(uRecord);
		if (recordList != null && recordList.size() > 0) {
			for (UserBiRecord dm : recordList) {
				has_tixian_money = has_tixian_money.add(dm.getBi_no());
			}
		}
		return has_tixian_money;
	}

	/**
	 * @计算出当前用户累计消费金额
	 **/
	public BigDecimal getLeiJiXiaoFeiMoney(HttpServletRequest request, UserInfo userInfo) {
		BigDecimal leiji_xiaofei_money = new BigDecimal(0);
		OrderInfo oInfo = new OrderInfo();
		oInfo.setAdd_user_id(userInfo.getId());
		oInfo.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
		List<OrderInfo> orderList = getFacade().getOrderInfoService().getOrderInfoList(oInfo);
		if (orderList != null && orderList.size() > 0) {
			for (OrderInfo dm : orderList) {
				leiji_xiaofei_money = leiji_xiaofei_money.add(dm.getOrder_money());
			}
		}
		return leiji_xiaofei_money;
	}

	/**
	 * @计算出当前用户实际可提现金额
	 **/
	public BigDecimal getUserCanTiXianMoney(HttpServletRequest request, UserInfo userInfo, BigDecimal has_tixian_money,
			BigDecimal leiji_xiaofei_money) {
		BigDecimal can_tixian_money = new BigDecimal(0);
		BigDecimal can_tixian_money_really = new BigDecimal(0);// 实际可提现余额
		BaseData baseData = new BaseData();
		baseData.setType(Keys.BaseDataType.Base_Data_type_100.getIndex());
		baseData.setIs_del(0);
		baseData.getMap().put("order_by_info", "pre_number asc,");
		List<BaseData> baseDatas = getFacade().getBaseDataService().getBaseDataList(baseData);
		if (baseDatas != null && baseDatas.size() > 0) {
			for (BaseData dm : baseDatas) {
				if (new BigDecimal(dm.getPre_number()).compareTo(leiji_xiaofei_money) == 1) {
					can_tixian_money = new BigDecimal(dm.getPre_number2());
					break;
				}
			}
			request.setAttribute("tixian_rule", baseDatas);
		}
		// 用户余额
		BigDecimal user_yu_e = userInfo.getBi_dianzi();
		BigDecimal shengyu_money = new BigDecimal(0);// 剩余可提现金额
		if (can_tixian_money.subtract(has_tixian_money).compareTo(new BigDecimal(0)) == 1) {
			// 可提现金额减去已提现金额>0
			shengyu_money = can_tixian_money.subtract(has_tixian_money);
		}
		if (shengyu_money.compareTo(user_yu_e) == 1) {
			// 如果剩余可提现余额大于用户当前余额
			can_tixian_money_really = user_yu_e;
		} else {
			can_tixian_money_really = shengyu_money;
		}
		return can_tixian_money_really;
	}

	/**
	 * 商品列表
	 **/
	public void setCommInfoList(ActionForm form, HttpServletRequest request, DynaBean dynaBean, Pager pager, UserInfo ui) {
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String is_del = (String) dynaBean.get("is_del");
		String comm_no_like = (String) dynaBean.get("comm_no_like");
		String audit_state = (String) dynaBean.get("audit_state");
		String cls_id = (String) dynaBean.get("cls_id");
		String country = (String) dynaBean.get("country");
		String city = (String) dynaBean.get("city");
		String province = (String) dynaBean.get("province");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String today_date = (String) dynaBean.get("today_date");
		String comm_type = (String) dynaBean.get("comm_type");

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);

		// 非管理员只显示自己添加的商品
		if (ui.getUser_type().intValue() != Keys.UserType.USER_TYPE_1.getIndex()) {
			entity.setAdd_user_id(ui.getId());
		}

		if (StringUtils.isNotBlank(own_entp_id)) {
			EntpInfo entpInfo = getEntpInfo(Integer.valueOf(own_entp_id), null, null);
			if (null != entpInfo) {
				request.setAttribute("entp_name", entpInfo.getEntp_name());
			}
		}

		if (StringUtils.isNotBlank(today_date)) {
			entity.getMap().put("today_date", today_date);
		}
		if (StringUtils.isNotBlank(country)) {
			entity.setP_index(Integer.valueOf(country));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_city", StringUtils.substring(city, 0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_province", StringUtils.substring(province, 0, 2));
		}

		if (StringUtils.isBlank(comm_type)) {
			entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		} else {
			entity.setComm_type(Integer.valueOf(comm_type));
		}
		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.parseInt(audit_state));
		}
		if (StringUtils.isNotBlank(is_del)) {
			if ("-1".equals(is_del)) {
				entity.setIs_del(null);
			}
			if ("0".equals(is_del)) {
				entity.setIs_del(0);
			}
			if ("1".equals(is_del)) {
				entity.setIs_del(1);
			}
		} else {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(comm_no_like)) {
			entity.getMap().put("comm_no_like", comm_no_like);
		}
		if (StringUtils.isNotBlank(cls_id) && !cls_id.equals("1")) {
			entity.setCls_id(null);
			entity.setCls_name(null);
			entity.getMap().put("allPd", "true");
			entity.getMap().put("par_cls_id", cls_id);
		}

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		for (CommInfo ci : entityList) {
			EntpInfo entpInfo = getEntpInfo(ci.getOwn_entp_id(), null, null);
			if (null != entpInfo) {
				ci.getMap().put("entp_name", entpInfo.getEntp_name());
			}
			UserInfo userInfo = getUserInfo(ci.getAdd_user_id());
			if (null != userInfo) {
				ci.getMap().put("mobile", userInfo.getMobile());
				ci.getMap().put("user_name", userInfo.getUser_name());
				ci.getMap().put("real_name", userInfo.getReal_name());
			}
			// 套餐管理
			CommTczhPrice param_ctp = new CommTczhPrice();
			param_ctp.setComm_id(ci.getId().toString());
			param_ctp.getMap().put("order_by_inventory_asc", "true");
			List<CommTczhPrice> CommTczhPriceList = getFacade().getCommTczhPriceService().getCommTczhPriceList(
					param_ctp);
			if ((null != CommTczhPriceList) && (CommTczhPriceList.size() > 0)) {
				ci.getMap().put("count_tczh_price_inventory", CommTczhPriceList.get(0).getInventory());
			}
		}
		request.setAttribute("entityList", entityList);
	}

	/**
	 * 添加商品
	 **/
	public void setCommInfoAdd(HttpServletRequest request, EntpInfo entpInfo, DynaBean dynaBean) {
		String comm_type = (String) dynaBean.get("comm_type");
		dynaBean.set("comm_type", comm_type);

		List<PdImgs> CommImgsList = new ArrayList<PdImgs>();
		request.setAttribute("CommImgsListCount", 0);
		for (int i = 0; i < 5; i++) {
			PdImgs pdImgs = new PdImgs();
			CommImgsList.add(pdImgs);
		}

		request.setAttribute("CommImgsList", CommImgsList);

		dynaBean.set("province", Keys.P_INDEX_INIT);
		dynaBean.set("city", Keys.P_INDEX_CITY);
		dynaBean.set("is_sell", "1");

		getSessionId(request);

		dynaBean.set("own_entp_id", entpInfo.getId());
		dynaBean.set("entp_name", entpInfo.getEntp_name());
		dynaBean.set("up_date", new Date());
		dynaBean.set("down_date", DateUtils.addDays(new Date(), Keys.COMM_UP_DATE));
	}

	/**
	 * 三级分类
	 **/
	public void setBaseClassLv3(HttpServletRequest request) {
		BaseClass baseClass = new BaseClass();
		baseClass.setCls_level(3);
		baseClass.setCls_scope(Keys.CLS_SCOPE_TYPE.CLS_SCOPE_1.getIndex());
		baseClass.setIs_del(0);
		baseClass.getMap().put("order_by_par_id", true);
		List<BaseClass> classList = getFacade().getBaseClassService().getBaseClassList(baseClass);
		request.setAttribute("baseClassList", classList);
	}

	public void setFreightList(HttpServletRequest request, Integer entp_id) {
		Freight entity = new Freight();
		entity.setIs_del(0);
		entity.setEntp_id(entp_id);
		List<Freight> freightList = getFacade().getFreightService().getFreightList(entity);
		request.setAttribute("freightList", freightList);
	}

	public void setCommTczhPriceList(HttpServletRequest request, String comm_id, Integer type) {
		CommTczhPrice param_ctp = new CommTczhPrice();
		param_ctp.setComm_id(comm_id);
		param_ctp.setTczh_type(type);
		logger.info("====CommTczhPrice");
		List<CommTczhPrice> list_CommTczhPrice = getFacade().getCommTczhPriceService().getCommTczhPriceList(param_ctp);
		request.setAttribute("list_CommTczhPrice", list_CommTczhPrice); // 套餐价格及对应各属性
	}

	public ServiceCenterInfo getUserLinkServiceInfo(Integer user_id) {
		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		serviceCenterInfo.getMap().put("is_virtual_no_def", true);
		serviceCenterInfo.setAdd_user_id(user_id);
		serviceCenterInfo.setIs_del(0);
		return getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
	}

	/**
	 * RwYhqInfo
	 *
	 * @param RwYhqInfo
	 * @return
	 */
	public RwYhqInfo getRwYhqInfo(Integer yhq_id) {
		RwYhqInfo entity = new RwYhqInfo();
		entity.setId(yhq_id);
		entity.setIs_used(0);
		entity.setIs_del(0);
		entity = getFacade().getRwYhqInfoService().getRwYhqInfo(entity);
		return entity;
	}

	public RwYhqInfo setPublicRwYhqInfo(RwYhqInfo t, String title, Integer origin_type, Integer hb_class,
			Integer ruel_type, Integer min_money, Integer amount, Date effect_start_date, Date effect_end_date,
			Integer is_del, Integer is_used, Integer link_id) {
		t.setTitle(title);
		t.setOrigin_type(origin_type);
		t.setHb_class(hb_class);
		t.setRuel_type(ruel_type);
		t.setMin_money(min_money);
		t.setAmount(amount);
		t.setEffect_start_date(effect_start_date);
		t.setEffect_end_date(effect_end_date);
		t.setIs_del(is_del);
		t.setLink_id(link_id);
		t.setIs_used(is_used);
		return t;
	}

	public RwHbRule setPublicRwHbRule(RwHbRule t, String title, Integer hb_class, Integer hb_type,
			Integer share_user_money, Integer hb_money, Integer max_money, Integer min_money, Integer effect_count,
			Integer is_del, Integer is_closed) {
		t.setTitle(title);
		t.setHb_class(hb_class);
		t.setHb_type(hb_type);
		t.setShare_user_money(share_user_money);
		t.setHb_money(hb_money);
		t.setMax_money(max_money);
		t.setMin_money(min_money);
		t.setEffect_count(effect_count);
		t.setIs_del(is_del);
		t.setIs_closed(is_closed);

		return t;
	}

	public List<SysModule> getManagerSysModuleList(List<SysModule> sysAllList) {

		List<SysModule> sysModuleLevelOneList = new ArrayList<SysModule>();
		List<SysModule> sysModuleLevelTwoList = new ArrayList<SysModule>();
		List<SysModule> sysModuleLevelThreeList = new ArrayList<SysModule>();
		for (SysModule sys : sysAllList) {
			if (sys.getMod_level().intValue() == Keys.ModLevel.MOD_LEVEL_1.getIndex()) {
				sysModuleLevelOneList.add(sys);
			}
			if (sys.getMod_level().intValue() == Keys.ModLevel.MOD_LEVEL_2.getIndex()) {
				sysModuleLevelTwoList.add(sys);
			}
			if (sys.getMod_level().intValue() == Keys.ModLevel.MOD_LEVEL_3.getIndex()) {
				sysModuleLevelThreeList.add(sys);
			}
		}

		if (sysModuleLevelOneList.size() > 0) {
			for (SysModule temp : sysModuleLevelOneList) {
				List<SysModule> sysModuleSonList = new ArrayList<SysModule>();
				if (sysModuleLevelTwoList.size() > 0) {
					for (SysModule tempSon : sysModuleLevelTwoList) {
						List<SysModule> sysModuleSonSonList = new ArrayList<SysModule>();
						if (null != sysModuleLevelThreeList && sysModuleLevelThreeList.size() > 0) {
							for (SysModule tempSonSon : sysModuleLevelThreeList) {
								if (tempSon.getMod_id().intValue() == tempSonSon.getPar_id().intValue()) {

									String mod_url = tempSonSon.getMod_url();
									if (!StringUtils.contains(mod_url, "mod_id")) {
										// 判断添加mod_id
										String linkUrl = "?";
										if (StringUtils.contains(mod_url, linkUrl)) {
											linkUrl = "&";
										}
										mod_url = mod_url + linkUrl + "mod_id=" + tempSonSon.getMod_id();
										tempSonSon.setMod_url(mod_url);
									}

									sysModuleSonSonList.add(tempSonSon);
								}
							}
						}
						if (temp.getMod_id().intValue() == tempSon.getPar_id().intValue()) {
							tempSon.getMap().put("sysModuleSonSonList", sysModuleSonSonList);
							sysModuleSonList.add(tempSon);
						}
					}
				}
				temp.getMap().put("sysModuleSonList", sysModuleSonList);
			}
		}

		return sysModuleLevelOneList;
	}

	/**
	 * @return 网页端 跟 m 端查看商品页面，是否带购物车
	 **/
	public ActionForward returnJsp(String m_or_index, Integer comm_type) {
		// 2.实物、4.促销、6.红包 商品
		if (m_or_index.equals("index")) {
			if (comm_type == Keys.CommType.COMM_TYPE_2.getIndex() || comm_type == Keys.CommType.COMM_TYPE_4.getIndex()) {
				return new ActionForward("/IndexEntpInfo/view.jsp");
			} else {
				return new ActionForward("/IndexEntpInfo/viewType3.jsp");
			}
		}
		if (m_or_index.equals("m")) {
			if (comm_type == Keys.CommType.COMM_TYPE_2.getIndex() || comm_type == Keys.CommType.COMM_TYPE_4.getIndex()) {
				return new ActionForward("/MEntpInfo/view.jsp");// 带购物车
			} else {
				return new ActionForward("/MEntpInfo/viewType3.jsp");// 不带购物车
			}
		}
		return null;

	}

	// ios这里传入的datas是一个json，不一样
	public String returnInfo(HttpServletResponse response, String code, String msg, Object datas) throws Exception {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("datas", datas);
		String jsonsring = json.toJSONString();
		logger.info("jsonsring:{}", jsonsring);
		super.renderJson(response, jsonsring);
		return null;
	}

	public void setMbaseLinkList(HttpServletRequest request, Integer link_type, Integer count, String no_null_image_path) {
		List<MBaseLink> mBaseLinkList = this.getMBaseLinkList(link_type, count, no_null_image_path);
		request.setAttribute("mBaseLinkList" + link_type, mBaseLinkList);
	}

	public List<MBaseLink> getMBaseLinkList(Integer linkType, Integer count, String no_null_image_path) {
		MBaseLink blk = new MBaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		log.info("====mBaseLinkList:" + linkType);
		List<MBaseLink> mBaseLinkList = getFacade().getMBaseLinkService().getMBaseLinkList(blk);
		return mBaseLinkList;
	}

	/**
	 * @author Wu, Yang
	 * @version 2011-04-26
	 * @desc 回显省市县单个的信息
	 */
	public void setprovinceAndcityAndcountryToFrom(DynaBean dynaBean, Long p_index) {
		if (null != p_index) {
			BaseProvince baseProvince = new BaseProvince();
			baseProvince.setP_index(p_index);
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			if (null != baseProvince) {
				if (baseProvince.getPar_index() == 0) {
					dynaBean.set("province", baseProvince.getP_index().toString());
				} else {
					BaseProvince baseP = new BaseProvince();
					baseP.setP_index(baseProvince.getPar_index());
					baseP = getFacade().getBaseProvinceService().getBaseProvince(baseP);
					if (baseP.getPar_index() == 0) {
						dynaBean.set("province", baseP.getP_index().toString());
						dynaBean.set("city", baseProvince.getP_index().toString());
					} else {
						BaseProvince bap = new BaseProvince();
						bap.setP_index(baseP.getPar_index());
						bap = getFacade().getBaseProvinceService().getBaseProvince(bap);
						if (bap.getPar_index() == 0) {
							dynaBean.set("province", bap.getP_index().toString());
							dynaBean.set("city", baseP.getP_index().toString());
							dynaBean.set("country", baseProvince.getP_index().toString());
						} else {
							BaseProvince bsp = new BaseProvince();
							bsp.setP_index(bap.getPar_index());
							bsp = getFacade().getBaseProvinceService().getBaseProvince(bsp);
							if (bsp.getPar_index() == 0) {
								dynaBean.set("province", bsp.getP_index().toString());
								dynaBean.set("city", bap.getP_index().toString());
								dynaBean.set("country", baseP.getP_index().toString());
								dynaBean.set("town", baseProvince.getP_index().toString());
							} else {
								BaseProvince bp = new BaseProvince();
								bp.setP_index(bsp.getPar_index());
								bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
								dynaBean.set("province", bp.getP_index().toString());
								dynaBean.set("city", bsp.getP_index().toString());
								dynaBean.set("country", bap.getP_index().toString());
								dynaBean.set("town", baseP.getP_index().toString());
								dynaBean.set("village", baseProvince.getP_index().toString());
							}
						}
					}
				}

			}
		}
	}

	/**
	 * @param ret
	 * @param msg
	 * @param data
	 * @param response
	 * @return
	 * @desc 返回Ajax接口
	 */
	public ActionForward returnJson(int ret, String msg, JSONObject data, HttpServletResponse response) {
		data.put("ret", ret);
		data.put("msg", msg);
		log.info("data.toString():" + data.toString());
		super.renderJson(response, data.toString());
		return null;
	}

	/**
	 * @param audit_state
	 * @return 返回村列表
	 */
	public List<VillageInfo> getVillageInfoList(Integer audit_state) {
		VillageInfo villageInfo = new VillageInfo();
		villageInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		if (null != audit_state) {
			villageInfo.setAudit_state(audit_state);
		}
		List<VillageInfo> list = getFacade().getVillageInfoService().getVillageInfoList(villageInfo);
		return list;
	}

	/**
	 * @param id
	 * @param audit_static
	 * @return 返回存子信息
	 */
	public VillageInfo getVillageInfo(String id, Integer audit_static) {
		VillageInfo entity = new VillageInfo();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		if (null != audit_static) {
			entity.setAudit_state(audit_static);
		}
		entity = getFacade().getVillageInfoService().getVillageInfo(entity);
		return entity;
	}

	public VillageInfo getVillageInfo(String id) {

		return getVillageInfo(Integer.valueOf(id));
	}

	public VillageInfo getVillageInfo(Integer id) {
		VillageInfo entity = new VillageInfo();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.getMap().put("is_virtual_no_def", "true");
		entity = getFacade().getVillageInfoService().getVillageInfo(entity);
		return entity;
	}

	/**
	 * @param entity
	 * @return
	 * @desc 村子的管理员用户
	 */
	public UserInfo getVillageManageUser(VillageInfo entity) {
		UserInfo userInfo = new UserInfo();
		userInfo.setIs_village(1);
		userInfo.setOwn_village_id(entity.getId());
		userInfo.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
		userInfo.setIs_del(0);
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		return userInfo;
	}

	/**
	 * @param contact_user_id 被关注人
	 * @param add_user_id 关注人
	 * @return 关注数量
	 * @desc 是否关注
	 */
	public int isGuanzhu(Integer contact_user_id, Integer add_user_id) {
		VillageContactList entity = new VillageContactList();
		entity.setAdd_user_id(add_user_id);
		entity.setContact_user_id(contact_user_id);
		int count = getFacade().getVillageContactListService().getVillageContactListCount(entity);
		return count;
	}

	/**
	 * @param village_id 村id
	 * @param add_user_id 用户id
	 * @return
	 * @desc 用户加入村信息
	 */
	public VillageMember getApplyVillage(Integer village_id, Integer add_user_id) {
		VillageMember entity = new VillageMember();
		entity.setVillage_id(village_id);
		entity.setUser_id(add_user_id);
		entity.setIs_del(0);
		entity = getFacade().getVillageMemberService().getVillageMember(entity);
		return entity;
	}

	public VillageMember getApplyVillage(Integer village_id, Integer add_user_id, Integer audit_state) {
		VillageMember entity = new VillageMember();
		entity.setVillage_id(village_id);
		entity.setUser_id(add_user_id);
		entity.setIs_del(0);
		if (null != audit_state) {
			entity.setAudit_state(audit_state);
		}
		entity = getFacade().getVillageMemberService().getVillageMember(entity);
		return entity;
	}

	/**
	 * @param response
	 * @return
	 * @desc 返回json数据
	 */
	public ActionForward returnAjaxData(HttpServletResponse response, String code, String msg, JSONObject data)
			throws Exception {
		this.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward returnErr(HttpServletResponse response, String msg, JSONObject data) throws Exception {
		this.returnInfo(response, "-1", msg, data);
		return null;
	}

	public ActionForward returnSus(HttpServletResponse response, String msg, JSONObject data) throws Exception {
		this.returnInfo(response, "1", msg, data);
		return null;
	}

	/**
	 * @param item
	 * @desc 动态查询图片 map
	 */
	public void setMapDynamicImgs(VillageDynamic item) {
		BaseImgs img = new BaseImgs();
		img.setLink_id(item.getId());
		img.getMap().put("img_type_in",
				Keys.BaseImgsType.Base_Imgs_TYPE_40.getIndex() + "," + Keys.BaseImgsType.Base_Imgs_TYPE_50.getIndex());
		img.getRow().setCount(9);
		List<BaseImgs> imgList = getFacade().getBaseImgsService().getBaseImgsList(img);
		item.getMap().put("imgList", imgList);
	}

	/**
	 * @param link_id
	 * @param comment_type
	 * @param count 查询数量
	 * @return
	 * @desc 查询动态的评论、回复、点赞
	 */
	public List<VillageDynamicComment> getVillageDynamicCommentList(Integer link_id, Integer comment_type,
			Integer count, String map) {
		log.info("====查询动态的评论、回复、点赞====");
		VillageDynamicComment comment = new VillageDynamicComment();
		comment.setLink_id(link_id);
		comment.setIs_del(0);
		comment.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		if (null != comment_type) {
			comment.setComment_type(comment_type);
		}
		if (null != map) {
			comment.getMap().put("comment_type_in",
					Keys.CommentType.COMMENT_TYPE_1.getIndex() + "," + Keys.CommentType.COMMENT_TYPE_2.getIndex());
			if (null != count) {
				comment.getRow().setCount(count);
			}
		}

		List<VillageDynamicComment> commentList = getFacade().getVillageDynamicCommentService()
				.getVillageDynamicCommentList(comment);
		return commentList;
	}

	/**
	 * @param id
	 * @param type
	 * @return
	 * @desc 查询动态的评论、回复、点赞
	 */
	public List<VillageDynamicComment> getVillageDynamicCommentList(Integer id, Integer type) {

		return getVillageDynamicCommentList(id, type, null, null);
	}

	/**
	 * @param id
	 * @param type
	 * @return
	 * @desc 获取动态的评论、回复总数
	 */
	public int getVillageDynamicCommentListCount(Integer id, Integer type) {
		VillageDynamicComment comment = new VillageDynamicComment();
		comment.setLink_id(id);
		comment.setIs_del(0);
		comment.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		if (null != type) {
			comment.setComment_type(type);
		}
		int count = getFacade().getVillageDynamicCommentService().getVillageDynamicCommentCount(comment);
		return count;
	}

	/**
	 * 生成二维码图片
	 * 
	 * @param Path 文件的绝对路径
	 * @param Jump_path 二维码跳转的路径
	 * @param LogoFile 商品的头像
	 * @param name 底部添加的文字
	 * @param Local_folders 要存文件夹的名称
	 * @param comm_id 商品的id
	 */
	public void createQrcode(String Path, String Jump_path, String Logo, String name, String Local_folders, int comm_id) {
		CodeCreator creator = new CodeCreator();
		CodeModel info = new CodeModel();
		info.setWidth(400);
		info.setHeight(400);
		info.setFontSize(30);
		info.setContents(Jump_path);// 二维码跳转的路径
		info.setLogoFile(new File(Logo));// 商品的头像
		info.setDesc(name);// 底部添加的文字
		String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
		if (!ctxDir.endsWith(File.separator)) {
			ctxDir = ctxDir + File.separator;
		}
		File savePath = new File(ctxDir + Local_folders);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		String imgPath = ctxDir + Local_folders + File.separator + comm_id + ".png";
		File imgFile = new File(imgPath);
		if (!imgFile.exists()) {
			creator.createCodeImage(info, Path + Local_folders + File.separator + comm_id + "." + info.getFormat());// 本地储存的路径
		}

	}

	/**
	 * @desc 用户是否点赞
	 */
	public int isDynamicUserZan(Integer user_id, VillageDynamic item, Integer type) {
		VillageDynamicComment comment = new VillageDynamicComment();
		comment.setLink_id(item.getId());
		comment.setIs_del(0);
		if (user_id != null) {
			comment.setAdd_user_id(user_id);
		}
		comment.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		if (null != type) {
			comment.setComment_type(type);
		}
		int count = getFacade().getVillageDynamicCommentService().getVillageDynamicCommentCount(comment);
		return count;
	}

	/**
	 * @param ui
	 * @param villageDynamicList
	 * @desc 查询动态的图片，回复，点赞
	 */
	public void setVillageDynamic(UserInfo ui, List<VillageDynamic> villageDynamicList) {
		if (null != villageDynamicList && villageDynamicList.size() > 0) {
			for (VillageDynamic item : villageDynamicList) {
				this.setMapDynamicImgs(item);// 查询图片
				// 商品类型查询商品
				if (item.getType().intValue() == Keys.DynamicType.dynamic_type_2.getIndex()) {
					CommInfo commInfo = new CommInfo();
					commInfo.setId(item.getComm_id());
					commInfo.setIs_del(0);
					commInfo.setIs_sell(1);
					commInfo.setIs_has_tc(1);
					commInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
					commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
					if (null != commInfo) {
						CommTczhPrice tc = new CommTczhPrice();
						tc.setComm_id(commInfo.getId().toString());
						tc = getFacade().getCommTczhPriceService().getCommTczhPrice(tc);
						item.getMap().put("commInfoTczh", tc);
					}
					item.getMap().put("commInfo", commInfo);
				}

				// 评论列表
				List<VillageDynamicComment> commentList = this.getVillageDynamicCommentList(item.getId(), null, null,
						"true");
				item.getMap().put("commentList", commentList);

				// 用户是否点赞该条动态
				if (null != ui) {
					int zan_count = isDynamicUserZan(ui.getId(), item, Keys.CommentType.COMMENT_TYPE_3.getIndex());
					item.getMap().put("zan_count", zan_count);
				}

				// 点赞用户列表
				String zanNameList = "";
				List<VillageDynamicComment> zanList = new ArrayList<VillageDynamicComment>();
				zanList = this.getVillageDynamicCommentList(item.getId(), Keys.CommentType.COMMENT_TYPE_3.getIndex(),
						5, null);
				if (null != zanList && zanList.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for (VillageDynamicComment zan : zanList) {
						sb.append(zan.getAdd_user_name()).append("、");
					}
					zanNameList = sb.toString();
					if (zanNameList.length() > 0) {
						zanNameList = zanNameList.substring(0, zanNameList.lastIndexOf("、"));
					}
				}
				item.getMap().put("zanList", zanList);
				item.getMap().put("zanNameList", zanNameList);
			}
		}
	}

	/**
	 * @param commInfo
	 * @return 判断商品是否正常
	 */
	public boolean isCommInfoTrue(CommInfo commInfo) {
		if (null == commInfo) {
			return false;
		}
		if (commInfo.getIs_del().intValue() == 1) {
			return false;
		}
		if (commInfo.getAudit_state().intValue() != 1) {
			return false;
		}
		if (commInfo.getIs_sell().intValue() == 0) {
			return false;
		}
		if (commInfo.getDown_date().compareTo(new Date()) < 0) {
			return false;
		}
		if (null == commInfo || commInfo.getIs_del().intValue() == 1 || commInfo.getAudit_state().intValue() != 1
				|| commInfo.getIs_sell().intValue() == 0 || commInfo.getDown_date().compareTo(new Date()) < 0) {
			return false;
		}

		return true;
	}

	/**
	 * @desc 关注数量
	 * @param userInfo
	 */
	public int getGuanZhuCount(UserInfo userInfo) {
		VillageContactList entity = new VillageContactList();
		entity.setAdd_user_id(userInfo.getId());
		entity.setIs_del(0);
		int count = getFacade().getVillageContactListService().getVillageContactListCount(entity);
		return count;
	}

	public ServiceCenterInfo getServiceCenterInfo(String id) {
		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(0);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
		return entity;
	}

	public String createUserInfoNo(Integer userId) {
		String user_no = "C" + StringUtils.leftPad(String.valueOf(userId), 8, "0");
		return user_no;
	}

	public String createUserNo() {
		UserInfo userInfo = new UserInfo();
		userInfo.getMap().put("st_add_date", DateTools.getStringDate(new Date(), "yyyy-MM-dd"));
		userInfo.getMap().put("en_add_date", DateTools.getStringDate(new Date(), "yyyy-MM-dd"));
		int count = this.getFacade().getUserInfoService().getUserInfoCount(userInfo);

		String sdFormatymdForNo1 = this.sdFormatymdForNo.format(new Date());
		sdFormatymdForNo1 = sdFormatymdForNo1 + StringUtils.leftPad(String.valueOf(count + 1), 4, "0");
		return sdFormatymdForNo1;
	}

	public String getShippingAddressInfo(HttpServletRequest request, Integer user_id, String shipping_address_id) {
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setAdd_user_id(user_id);
		shippingAddress.setIs_del(0);
		List<ShippingAddress> shippingAddressList = getFacade().getShippingAddressService().getShippingAddressList(
				shippingAddress);

		ShippingAddress dftAddress = new ShippingAddress();

		if ((null != shippingAddressList) && (shippingAddressList.size() > 0)) {
			int i = 0, j = 0;
			for (ShippingAddress sa : shippingAddressList) {

				String province = this.getProvinceName(Long.valueOf(sa.getRel_province()));
				String city = this.getProvinceName(Long.valueOf(sa.getRel_city()));
				String region = this.getProvinceName(Long.valueOf(sa.getRel_region()));

				sa.getMap().put("province", province);
				sa.getMap().put("city", city);
				sa.getMap().put("region", region);

				if (null != sa.getRel_region_four()) {
					String regionFour = this.getProvinceName(Long.valueOf(sa.getRel_region_four()));
					sa.getMap().put("regionFour", regionFour);
					sa.getMap().put(
							"full_addr",
							province.concat(" ").concat(city).concat(" ").concat(region).concat(" ").concat(regionFour)
									.concat(" ").concat(sa.getRel_addr()));
				} else {
					sa.getMap().put(
							"full_addr",
							province.concat(" ").concat(city).concat(" ").concat(region).concat(" ")
									.concat(sa.getRel_addr()));
				}

				if (StringUtils.isNotBlank(shipping_address_id)) {
					if (sa.getId().toString().equals(shipping_address_id)) {
						dftAddress = sa;
						j = i;
					}
				} else {
					if (sa.getIs_default() == 1) {
						dftAddress = sa;
						j = i;
					} else {
						dftAddress = shippingAddressList.get(0);
					}
				}
				i++;
			}
			ShippingAddress temp = shippingAddressList.get(j);
			shippingAddressList.remove(j);
			shippingAddressList.add(0, temp);
		}

		request.setAttribute("shippingAddressList", shippingAddressList);

		if (null != dftAddress.getId()) {
			request.setAttribute("p_index", dftAddress.getRel_region());
			request.setAttribute("shipping_address_id", dftAddress.getId());
			request.setAttribute("dftAddress", dftAddress);
			return dftAddress.getId().toString();
		}
		return null;
	}

	public String getProvinceName(Long p_index) {
		String p_name = "";
		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setP_index(p_index);
		baseProvince.setIs_del(0);
		baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
		if (null != baseProvince) {
			p_name = baseProvince.getP_name();
		}
		return p_name;
	}

	// 我加入的村
	public void myJoinVillageList(JSONObject data, UserInfo ui) {
		VillageMember myJoinVillage = new VillageMember();
		myJoinVillage.setAdd_user_id(ui.getId());
		myJoinVillage.getMap().put("audit_state_in", "1,2");
		myJoinVillage.setIs_del(0);
		List<VillageMember> myJoinVillageList = getFacade().getVillageMemberService().getVillageMemberList(
				myJoinVillage);
		data.put("myJoinVillageList", myJoinVillageList);
	}

	// 贫困列表
	public void poorInfoList(JSONObject data, String p_index) {
		PoorInfo poorInfo = new PoorInfo();
		poorInfo.getMap().put("p_index_like", p_index);
		poorInfo.setIs_del(0);
		poorInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		poorInfo.getRow().setFirst(0);
		poorInfo.getRow().setCount(10);
		List<PoorInfo> poorInfoList = getFacade().getPoorInfoService().getPoorInfoPaginatedList(poorInfo);
		data.put("poorInfoList", poorInfoList);
	}

	public void poorInfoList(JSONObject data, DynaBean dynaBean, String p_index) {

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("poorStartPage");
		String pageSize = (String) dynaBean.get("pageSize");
		pageSize = "20";
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		logger.info("startPage:" + startPage);
		logger.info("pageSize:" + pageSize);

		PoorInfo poorInfo = new PoorInfo();
		poorInfo.getMap().put("p_index_like", p_index);
		poorInfo.setIs_del(0);
		poorInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		// poorInfo.getRow().setFirst(0);
		// poorInfo.getRow().setCount(10);

		Integer recordCount = getFacade().getPoorInfoService().getPoorInfoCount(poorInfo);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		poorInfo.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		poorInfo.getRow().setCount(Integer.valueOf(pageSize));

		List<PoorInfo> poorInfoList = getFacade().getPoorInfoService().getPoorInfoPaginatedList(poorInfo);
		data.put("poorInfoList", poorInfoList);
	}

	// 销售排行
	public void saleRankList(JSONObject data, String p_index_like, Integer count) {
		OrderInfoDetails ods = new OrderInfoDetails();
		ods.getMap().put("p_index_like", p_index_like);
		ods.getRow().setFirst(0);
		ods.getRow().setCount(count);
		List<OrderInfoDetails> saleRankList = getFacade().getOrderInfoDetailsService().getUserSaleRankingList(ods);
		data.put("saleRankList", saleRankList);
	}

	public void setAjaxDataPage(JSONObject data, DynaBean dynaBean, String p_index) {

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		String user_id = (String) dynaBean.get("user_id");
		String village_id = (String) dynaBean.get("village_id");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		VillageDynamic villageDynamic = new VillageDynamic();
		if (StringUtils.isNotBlank(village_id)) {
			villageDynamic.setVillage_id(Integer.valueOf(village_id));
		} else {
			villageDynamic.getMap().put("p_index_like", p_index);
		}
		villageDynamic.setIs_del(0);
		villageDynamic.setType(Keys.DynamicType.dynamic_type_2.getIndex());
		villageDynamic.setAudit_state(1);
		villageDynamic.setIs_public(1);// 公开
		villageDynamic.getMap().put("order_by_id_desc", "true");
		villageDynamic.getMap().put("left_join_comm_info", true);
		villageDynamic.getMap().put("not_out_sell_time", true);

		Integer recordCount = getFacade().getVillageDynamicService().getVillageDynamicCount(villageDynamic);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		villageDynamic.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		villageDynamic.getRow().setCount(Integer.valueOf(pageSize));

		List<VillageDynamic> villageDynamicList = getFacade().getVillageDynamicService()
				.getVillageDynamicPaginatedList(villageDynamic);
		if (null != villageDynamicList && villageDynamicList.size() > 0) {
			for (VillageDynamic item : villageDynamicList) {
				this.setMapDynamicImgs(item);// 查询图片
				if (item.getType().intValue() == Keys.DynamicType.dynamic_type_2.getIndex()) {
					CommInfo commInfo = new CommInfo();
					commInfo.setId(item.getComm_id());
					commInfo.setIs_del(0);
					commInfo.setIs_sell(1);
					commInfo.setIs_has_tc(1);
					commInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
					commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
					if (null != commInfo) {
						CommTczhPrice tc = new CommTczhPrice();
						tc.setComm_id(commInfo.getId().toString());
						tc = getFacade().getCommTczhPriceService().getCommTczhPrice(tc);
						item.getMap().put("commInfoTczh", tc);

					}
					item.getMap().put("commInfo", commInfo);

				} // 评论列表
				List<VillageDynamicComment> commentList = this.getVillageDynamicCommentList(item.getId(), null, 5,
						"true");
				item.getMap().put("commentList", commentList);

				// 用户动态点在数
				int zan_count = isDynamicUserZan(null, item, Keys.CommentType.COMMENT_TYPE_3.getIndex());
				item.getMap().put("zan_count", zan_count);

				// 用户是否点赞该条动态
				if (StringUtils.isNotBlank(user_id)) {
					int count = isDynamicUserZan(Integer.valueOf(user_id), item,
							Keys.CommentType.COMMENT_TYPE_3.getIndex());
					if (count > 0) {
						item.getMap().put("is_zan", 1);
					} else {
						item.getMap().put("is_zan", 0);
					}
				} else {
					item.getMap().put("is_zan", 0);
				}
				// 点赞用户列表
				String zanNameList = "";
				List<VillageDynamicComment> zanList = this.getVillageDynamicCommentList(item.getId(),
						Keys.CommentType.COMMENT_TYPE_3.getIndex(), 5, null);
				if (null != zanList && zanList.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for (VillageDynamicComment zan : zanList) {
						sb.append(zan.getAdd_user_name()).append("、");
					}
					zanNameList = sb.toString();
					if (zanNameList.length() > 0) {
						zanNameList = zanNameList.substring(0, zanNameList.lastIndexOf("、"));
					}
				}
				item.getMap().put("zanList", zanList);
				item.getMap().put("zanNameList", zanNameList);
			}
		}
		data.put("villageDynamicList", villageDynamicList);
	}

	public void setTuihuoAudit(ActionForm form, HttpServletRequest request, UserInfo ui, DynaBean dynaBean) {
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String st_audit_date = (String) dynaBean.get("st_audit_date");
		String en_audit_date = (String) dynaBean.get("en_audit_date");
		String trade_index = (String) dynaBean.get("trade_index");
		String order_type = (String) dynaBean.get("order_type");
		String is_del = (String) dynaBean.get("is_del");
		Pager pager = (Pager) dynaBean.get("pager");
		String return_no = (String) dynaBean.get("return_no");
		String expect_return_way_in = (String) dynaBean.get("expect_return_way_in");

		OrderReturnInfo entity = new OrderReturnInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(is_del)) {
			entity.setIs_del(Integer.valueOf(is_del));
		} else {
			entity.setIs_del(0);
		}

		if (ui.getUser_type().intValue() != Keys.UserType.USER_TYPE_1.getIndex()) {
			entity.setEntp_id(ui.getOwn_entp_id());
		}
		if (StringUtils.isNotBlank(expect_return_way_in)) {
			entity.getMap().put("expect_return_way_in", "1,2,3");// 只能看到已经发货申请退款的订单申请
		}

		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		entity.getMap().put("st_audit_date", st_audit_date);
		entity.getMap().put("en_audit_date", en_audit_date);
		entity.getMap().put("comm_name_like", comm_name_like);
		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("trade_index", trade_index);
		entity.getMap().put("order_type", order_type);
		entity.getMap().put("return_no", return_no);

		Integer recordCount = getFacade().getOrderReturnInfoService().getOrderReturnInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderReturnInfo> list = getFacade().getOrderReturnInfoService().getOrderReturnInfoPaginatedList(entity);
		if ((null != list) && (list.size() > 0)) {
			for (OrderReturnInfo item : list) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setId(item.getOrder_id());
				orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
				if (orderInfo != null) {
					OrderInfoDetails ods = new OrderInfoDetails();
					ods.setOrder_id(orderInfo.getId());
					// 退单个
					if (null != item.getOrder_detail_id()) {
						ods.setId(item.getOrder_detail_id());
					}
					orderInfo.setOrderInfoDetailsList(getFacade().getOrderInfoDetailsService().getOrderInfoDetailsList(
							ods));
					item.getMap().put("orderInfo", orderInfo);
				}

				if (null != item.getAudit_user_id()) {
					UserInfo user = new UserInfo();
					user.setId(item.getAudit_user_id());
					user = getFacade().getUserInfoService().getUserInfo(user);
					if (null != user) {
						item.getMap().put("audit_user_name", user.getUser_name());
					}
				}

				List<OrderReturnMsg> msglist = getOrderReturnMsg(item.getId());
				item.getMap().put("msglist", msglist);
			}
		}
		BaseData returnType = new BaseData();
		returnType.setType(10000);
		returnType.setIs_del(0);
		List<BaseData> returnTypeList = getFacade().getBaseDataService().getBaseDataList(returnType);
		if (null != returnTypeList && returnTypeList.size() > 0) {
			request.setAttribute("returnTypeList", returnTypeList);
		}

		request.setAttribute("entityList", list);
	}

	public List<OrderReturnMsg> getOrderReturnMsg(Integer order_return_id) {
		OrderReturnMsg msg = new OrderReturnMsg();
		msg.setOrder_return_id(order_return_id);
		msg.setIs_del(0);
		List<OrderReturnMsg> msglist = getFacade().getOrderReturnMsgService().getOrderReturnMsgList(msg);
		return msglist;
	}

	public void setOrderReturnMsg(HttpServletRequest request, OrderReturnInfo entity) {
		List<OrderReturnMsg> msglist = getOrderReturnMsg(entity.getId());
		request.setAttribute("msglist", msglist);
	}

	public OrderInfo getOrderInfo(Integer order_id) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(order_id);
		orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		return orderInfo;
	}

	public ActionForward saveTuiHuoAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception, UnsupportedEncodingException {

		UserInfo ui = getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String is_audit = (String) dynaBean.get("is_audit");
		String mod_id = (String) dynaBean.get("mod_id");
		String remark = (String) dynaBean.get("remark");

		OrderReturnInfo entity = new OrderReturnInfo();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getOrderReturnInfoService().getOrderReturnInfo(entity);
		super.copyProperties(entity, form);
		entity.getMap().put("remark", remark);
		if (StringUtils.isBlank(id) || !GenericValidator.isLong(id)) {
			String msg = "参数有误！";
			showMsgForManager(request, response, msg);
			return null;
		}
		entity.setId(new Integer(id));
		entity.setAudit_date(new Date());
		entity.setAudit_user_id(ui.getId());
		entity.setAudit_date(new Date());

		int i = getFacade().getOrderReturnInfoService().modifyOrderReturnInfo(entity);
		if (i == -1) {
			String msg = "退款失败！请重新提交。";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back(-2);}");
			return null;
		}
		if (StringUtils.isNotBlank(is_audit)) {
			saveMessage(request, "entity.audit");
		} else {
			saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public int viewOrderReturnInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String id) throws UnsupportedEncodingException {
		OrderReturnInfo orderReturnInfo = new OrderReturnInfo();
		orderReturnInfo.setId(new Integer(id));
		orderReturnInfo = getFacade().getOrderReturnInfoService().getOrderReturnInfo(orderReturnInfo);

		BaseData returnType = new BaseData();
		returnType.setId(orderReturnInfo.getReturn_type());
		returnType.setIs_del(0);
		returnType = getFacade().getBaseDataService().getBaseData(returnType);
		if (null != returnType) {
			request.setAttribute("returnTypeName", returnType.getType_name());
		}

		BaseImgs imgs = new BaseImgs();
		imgs.setLink_id(Integer.valueOf(id));
		imgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_20.getIndex());
		List<BaseImgs> imgsList = getFacade().getBaseImgsService().getBaseImgsList(imgs);
		request.setAttribute("imgsList", imgsList);
		orderReturnInfo.setQueryString(super.serialize(request, "id", "method"));
		// end
		super.copyProperties(form, orderReturnInfo);

		// 订单信息
		OrderInfo orderInfo = getOrderInfo(orderReturnInfo.getOrder_id());
		if (null == orderInfo) {
			return -1;
		}
		request.setAttribute("orderInfo", orderInfo);
		// 收货人信息
		this.showShippingAddressForOrderInfo(orderInfo);
		// super.copyProperties(form, orderInfo);

		// 产品详细
		OrderInfoDetails orderInfoDetail = new OrderInfoDetails();
		if (null != orderReturnInfo.getOrder_detail_id()) {
			orderInfoDetail.setId(orderReturnInfo.getOrder_detail_id());
		}
		orderInfoDetail.setOrder_id(orderReturnInfo.getOrder_id());
		List<OrderInfoDetails> orderInfoDetailList = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsList(
				orderInfoDetail);
		request.setAttribute("orderInfoDetailList", orderInfoDetailList);

		request.setAttribute("orderStateList", Keys.OrderState.values());
		request.setAttribute("payTypeList", Keys.PayType.values());

		WlOrderInfo wlOrderInfo = getWlOrderInfo(orderInfo.getId());
		request.setAttribute("wlOrderInfo", wlOrderInfo);

		// 反馈内容
		this.setOrderReturnMsg(request, orderReturnInfo);
		// 审核信息
		setOrderReturnAuditList(request, orderReturnInfo);
		return 1;
	}

	public void setOrderReturnAuditList(HttpServletRequest request, OrderReturnInfo entity) {
		List<OrderReturnAudit> auditlist = getOrderReturnAuditList(entity);
		request.setAttribute("orderReturnAuditList", auditlist);
	}

	public List<OrderReturnAudit> getOrderReturnAuditList(OrderReturnInfo entity) {
		OrderReturnAudit audit = new OrderReturnAudit();
		audit.setOrder_return_id(entity.getId());
		List<OrderReturnAudit> auditlist = getFacade().getOrderReturnAuditService().getOrderReturnAuditList(audit);
		return auditlist;
	}

	public void setBaseImgs(HttpServletRequest request, Integer link_id, Integer type) {
		List<BaseImgs> returnImgs = getBaseImgs(link_id, type);
		request.setAttribute("returnOrderInfoImgs", returnImgs);
	}

	public List<BaseImgs> getBaseImgs(Integer link_id, Integer type) {
		BaseImgs baseImgs = new BaseImgs();
		baseImgs.setImg_type(type);
		baseImgs.setLink_id(link_id);
		List<BaseImgs> returnImgs = getFacade().getBaseImgsService().getBaseImgsList(baseImgs);
		return returnImgs;
	}

	public OrderReturnInfo setOrderReturnInfo(HttpServletRequest request, String tuikuan_id) {
		OrderReturnInfo orderReturnInfo = getOrderReturnInfo(tuikuan_id);
		request.setAttribute("orderReturnInfo", orderReturnInfo);
		return orderReturnInfo;
	}

	public OrderReturnInfo getOrderReturnInfo(String tuikuan_id) {
		OrderReturnInfo orderReturnInfo = new OrderReturnInfo();
		orderReturnInfo.setId(Integer.valueOf(tuikuan_id));
		orderReturnInfo.setIs_del(0);
		orderReturnInfo = getFacade().getOrderReturnInfoService().getOrderReturnInfo(orderReturnInfo);
		return orderReturnInfo;
	}

	public void setWlOrderInfo(HttpServletRequest request, OrderInfo orderInfo) {
		WlOrderInfo wlOrderInfo = getWlOrderInfo(orderInfo.getId());
		request.setAttribute("wlOrderInfo", wlOrderInfo);
	}

	public WlOrderInfo getWlOrderInfo(Integer order_id) {
		WlOrderInfo wlOrderInfo = new WlOrderInfo();
		wlOrderInfo.setOrder_id(order_id);
		wlOrderInfo = getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
		return wlOrderInfo;
	}

	public List<OrderInfoDetails> getOrderInfoDetailsList(Integer order_id) {
		OrderInfoDetails orderInfoDetail = new OrderInfoDetails();
		orderInfoDetail.setOrder_id(order_id);
		List<OrderInfoDetails> orderInfoDetailList = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsList(
				orderInfoDetail);
		return orderInfoDetailList;
	}

	/**
	 * @desc 用户可使用的优惠券
	 * @return
	 */
	public YhqInfoSon getYhqInfoSonAvailable(String yhq_son_id) {

		return getYhqInfoSonAvailable(Integer.valueOf(yhq_son_id));
	}

	/**
	 * @desc 用户可使用的优惠券
	 * @return
	 */
	public YhqInfoSon getYhqInfoSonAvailable(Integer yhq_son_id) {
		YhqInfoSon yhqson = new YhqInfoSon();
		yhqson.setId(yhq_son_id);
		yhqson.setYhq_state(Keys.YhqState.YHQ_STATE_10.getIndex());
		yhqson.getMap().put("yhq_start_time", new Date());
		yhqson.getMap().put("yhq_end_time", new Date());
		yhqson = getFacade().getYhqInfoSonService().getYhqInfoSon(yhqson);
		return yhqson;
	}

	public void getCartYhqInfo(CartInfo ci) {
		// 可使用优惠券信息
		List<YhqInfoSon> yhqlist = new ArrayList<YhqInfoSon>();
		// 这个商品可以用的优惠券
		YhqLink yhqlink = new YhqLink();
		yhqlink.setComm_id(ci.getComm_id());
		List<YhqLink> yhqlinkList = getFacade().getYhqLinkService().getYhqLinkList(yhqlink);
		if (null != yhqlinkList && yhqlinkList.size() > 0) {
			for (YhqLink item : yhqlinkList) {
				YhqInfo yhq = new YhqInfo();
				yhq.setId(item.getYhq_id());
				yhq.setIs_del(0);
				yhq = getFacade().getYhqInfoService().getYhqInfo(yhq);
				if (null != yhq) {
					YhqInfoSon yhqson = new YhqInfoSon();
					yhqson.setLink_user_id(ci.getUser_id());
					yhqson.setLink_id(yhq.getId());
					// yhqson.setComm_id(ci.getComm_id());
					yhqson.setYhq_state(Keys.YhqState.YHQ_STATE_10.getIndex());
					yhqson.getMap().put("yhq_start_time", new Date());
					yhqson.getMap().put("yhq_end_time", new Date());
					yhqson = getFacade().getYhqInfoSonService().getYhqInfoSon(yhqson);
					if (null != yhqson) {
						BigDecimal cart_money = ci.getPd_price().multiply(new BigDecimal(ci.getPd_count()));
						log.info("===价格对比===" + cart_money + ":" + yhq.getYhq_sytj() + "="
								+ cart_money.compareTo(yhq.getYhq_sytj()));
						if (cart_money.compareTo(yhq.getYhq_sytj()) >= 0) {
							yhqson.getMap().put("yhq", yhq);
							yhqlist.add(yhqson);
						}

					}
				}

			}
		}
		log.info("==yhqlist.size():" + yhqlist.size());
		ci.getMap().put("yhqsonlist", yhqlist);
		ci.getMap().put("yhqCount", yhqlist.size());

		// 购物车的优惠券置为未选择
		if (null != ci.getYhq_id()) {
			CartInfo update = new CartInfo();
			update.setId(ci.getId());
			update.getMap().put("set_yhq_id_null", "true");
			update.getMap().put("set_yhq_money_null", "true");
			int i = getFacade().getCartInfoService().modifyCartInfo(update);
		}
	}

	public void setVillageCommClass(JSONObject data, String village_id) {
		BaseData basedata = new BaseData();
		basedata.setType(Keys.BaseDataType.Base_Data_type_1123.getIndex());
		basedata.setIs_del(0);
		basedata.setPre_number3(Integer.valueOf(village_id));
		List<BaseData> list = getFacade().getBaseDataService().getBaseDataList(basedata);
		if (null != list && list.size() > 0) {
			JSONArray commClassList = new JSONArray();
			for (BaseData item : list) {
				JSONObject p_indexs = new JSONObject();
				p_indexs.put("value", item.getId());
				p_indexs.put("text", item.getType_name());
				commClassList.add(p_indexs);
			}
			data.put("commClassList", commClassList);
			data.put("commClassDataList", list);
		}
	}

	/**
	 * @desc 退货订单号
	 */
	public String getReturnNo() {
		String retrun_no = "th" + this.creatTradeIndex();
		return retrun_no;
	}

	public void createCommInfoCode(HttpServletRequest request, Integer id) {
		VillageDynamic viilageDynamic = new VillageDynamic();
		viilageDynamic.setId(id);
		viilageDynamic = getFacade().getVillageDynamicService().getVillageDynamic(viilageDynamic);// 查找到商品
		if (null != viilageDynamic && viilageDynamic.getType().intValue() == Keys.DynamicType.dynamic_type_2.getIndex()) {
			HttpSession session = request.getSession(false);
			String ctx = getCtxPath(request, false);
			Integer comm_id = viilageDynamic.getComm_id();
			String Path = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));// 文件绝对路径

			CommInfo commInfoQuery = getCommInfo(comm_id);
			String LogoFile = Path + "/styles/imagesPublic/user_header.png";
			if (null != commInfoQuery && StringUtils.isNotBlank(commInfoQuery.getMain_pic())) {
				File tempFile = new File(Path + commInfoQuery.getMain_pic());
				if (tempFile.exists()) {
					LogoFile = Path + commInfoQuery.getMain_pic();// 商品的头像
				}
			}

			String Jump_path = ctx + "/m/MUserCenter.do?method=MUserCommInfo&id=" + id;// 二维码跳转的路径
			String name = commInfoQuery.getComm_name();// 底部添加的文字
			String uploadDir = "files" + File.separator + "commInfo";// 文件夹的名称
			createQrcode(Path, Jump_path, LogoFile, name, uploadDir, comm_id);
			viilageDynamic.setQrcode_image_path(uploadDir + File.separator + comm_id + ".png");
			getFacade().getVillageDynamicService().modifyVillageDynamic(viilageDynamic);
		}
	}

	public void setActivitiListPublic(ActionForm form, HttpServletRequest request, String type, Integer entp_id) {
		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");
		String mod_id = (String) dynaBean.get("mod_id");
		String st_start_date = (String) dynaBean.get("st_pub_date");
		String en_end_date = (String) dynaBean.get("en_end_date");
		String title_like = (String) dynaBean.get("title_like");
		String is_admin = (String) dynaBean.get("is_admin");

		if (StringUtils.isBlank(is_admin)) {
			is_admin = "0";
		}

		Pager pager = (Pager) dynaBean.get("pager");

		Activity entity = new Activity();
		entity.getMap().put("title_like", title_like);

		if (null == is_del) {
			entity.setIs_del(Integer.valueOf("0"));
			dynaBean.set("is_del", "0");
		}

		entity.getMap().put("st_start_date", st_start_date);
		entity.getMap().put("en_end_date", en_end_date);
		super.copyProperties(entity, form);

		Integer recordCount = getFacade().getActivityService().getActivityCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<Activity> newsInfoList = getFacade().getActivityService().getActivityPaginatedList(entity);
		if (is_admin.equals("0")) {

			UserInfo ui = getUserInfo(request);
			for (Activity cur : newsInfoList) {
				if (type.equals("entp")) {
					ActivityApply a = new ActivityApply();
					a.setUser_id(ui.getId());
					a.setLink_id(cur.getId());
					a = getFacade().getActivityApplyService().getActivityApply(a);
					if (null != a) {
						cur.getMap().put("remark", a.getRemark());
						cur.getMap().put("audit_state", a.getAudit_state());
					}

				}

				if (type.equals("getData")) {
					ActivityApply a = new ActivityApply();
					a.setLink_id(cur.getId());
					// 参与商家数量
					int count = getFacade().getActivityApplyService().getActivityApplyCount(a);
					cur.getMap().put("count", count);

					// 活动订单数量
					Activity activityOrder = new Activity();
					activityOrder.getMap().put("link_id", cur.getId());
					activityOrder = getFacade().getActivityService().selectActivityOrderSum(activityOrder);

					cur.getMap().put("sum_money", activityOrder.getMap().get("sum_money"));
					cur.getMap().put("order_count", activityOrder.getMap().get("order_count"));
					System.out.println(activityOrder.getMap().get("sum_money"));
					System.out.println(activityOrder.getMap().get("order_count"));
				}
			}

		}

		request.setAttribute("entityList", newsInfoList);
	}

	public void setActivitiListPublic1(ActionForm form, HttpServletRequest request, String type, Integer entp_id) {
		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");
		String mod_id = (String) dynaBean.get("mod_id");
		String st_start_date = (String) dynaBean.get("st_pub_date");
		String en_end_date = (String) dynaBean.get("en_end_date");
		String title_like = (String) dynaBean.get("title_like");
		String is_admin = (String) dynaBean.get("is_admin");

		if (StringUtils.isBlank(is_admin)) {
			is_admin = "0";
		}

		Pager pager = (Pager) dynaBean.get("pager");

		Activity entity = new Activity();
		entity.getMap().put("title_like", title_like);

		if (null == is_del) {
			entity.setIs_del(Integer.valueOf("0"));
			dynaBean.set("is_del", "0");
		}

		entity.getMap().put("st_start_date", st_start_date);
		entity.getMap().put("en_end_date", en_end_date);
		super.copyProperties(entity, form);

		Integer recordCount = getFacade().getActivityService().getActivityCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<Activity> newsInfoList = getFacade().getActivityService().getActivityPaginatedList(entity);
		if (is_admin.equals("0")) {

			UserInfo ui = getUserInfo(request);
			for (Activity cur : newsInfoList) {
				if (type.equals("entp")) {
					ActivityApply a = new ActivityApply();
					a.setUser_id(ui.getId());
					a.setLink_id(cur.getId());
					a = getFacade().getActivityApplyService().getActivityApply(a);
					if (null != a) {
						cur.getMap().put("remark", a.getRemark());
						cur.getMap().put("audit_state", a.getAudit_state());
					}

				}

				if (type.equals("getData")) {
					ActivityApply a = new ActivityApply();
					a.setEntp_id(entp_id);
					a.setLink_id(cur.getId());
					// 参与商家数量
					int count = getFacade().getActivityApplyService().getActivityApplyCount(a);
					cur.getMap().put("count", count);

					// 活动订单数量
					Activity activityOrder = new Activity();
					activityOrder.getMap().put("link_id", cur.getId());
					activityOrder = getFacade().getActivityService().selectActivityOrderSum(activityOrder);

					cur.getMap().put("sum_money", activityOrder.getMap().get("sum_money"));
					cur.getMap().put("order_count", activityOrder.getMap().get("order_count"));
					System.out.println(activityOrder.getMap().get("sum_money"));
					System.out.println(activityOrder.getMap().get("order_count"));
				}
			}

		}

		request.setAttribute("entityList", newsInfoList);
	}

	public void setActivitiListPublic(ActionForm form, HttpServletRequest request, String type) {
		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");
		String mod_id = (String) dynaBean.get("mod_id");
		String st_start_date = (String) dynaBean.get("st_pub_date");
		String en_end_date = (String) dynaBean.get("en_end_date");
		String title_like = (String) dynaBean.get("title_like");
		String is_admin = (String) dynaBean.get("is_admin");

		if (StringUtils.isBlank(is_admin)) {
			is_admin = "0";
		}

		Pager pager = (Pager) dynaBean.get("pager");

		Activity entity = new Activity();
		entity.getMap().put("title_like", title_like);

		if (null == is_del) {
			entity.setIs_del(Integer.valueOf("0"));
			dynaBean.set("is_del", "0");
		}

		entity.getMap().put("st_start_date", st_start_date);
		entity.getMap().put("en_end_date", en_end_date);
		super.copyProperties(entity, form);

		Integer recordCount = getFacade().getActivityService().getActivityCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<Activity> newsInfoList = getFacade().getActivityService().getActivityPaginatedList(entity);
		if (is_admin.equals("0")) {

			UserInfo ui = getUserInfo(request);
			for (Activity cur : newsInfoList) {
				if (type.equals("entp")) {
					ActivityApply a = new ActivityApply();
					a.setUser_id(ui.getId());
					a.setLink_id(cur.getId());
					a = getFacade().getActivityApplyService().getActivityApply(a);
					if (null != a) {
						cur.getMap().put("apply_id", a.getId());
						cur.getMap().put("remark", a.getRemark());
						cur.getMap().put("audit_state", a.getAudit_state());
						cur.getMap().put("qrcode_path", a.getQrcode_path());
					}

				}

				if (type.equals("getData")) {
					ActivityApply a = new ActivityApply();
					a.setLink_id(cur.getId());
					// 参与商家数量
					int count = getFacade().getActivityApplyService().getActivityApplyCount(a);
					cur.getMap().put("count", count);

					// 活动订单数量
					Activity activityOrder = new Activity();
					activityOrder.getMap().put("link_id", cur.getId());
					activityOrder = getFacade().getActivityService().selectActivityOrderSum(activityOrder);
					cur.getMap().put("sum_money",
							new BigDecimal(activityOrder.getMap().get("sum_money").toString()).setScale(2));
					cur.getMap().put("order_count", activityOrder.getMap().get("order_count"));
					System.out.println(activityOrder.getMap().get("sum_money"));
					System.out.println(activityOrder.getMap().get("order_count"));
				}
			}

		}

		request.setAttribute("entityList", newsInfoList);
	}

	public ActionForward returnJsMsg(HttpServletResponse response, String msg) {
		super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');window.history.back(-1)}");
		return null;
	}

	/**
	 * 根据商品类别获取商品编号
	 */
	public String getCommNoBase(String cls_id) {
		String comm_no = "";
		BaseClass baseClass = new BaseClass();
		baseClass.setCls_id(Integer.valueOf(cls_id));
		baseClass.setIs_del(0);
		baseClass = getFacade().getBaseClassService().getBaseClass(baseClass);
		if (null != baseClass) {
			CommInfo commInfo = new CommInfo();
			Integer count = getFacade().getCommInfoService().getCommInfoCount(commInfo);
			if (StringUtils.isBlank(baseClass.getCls_code())) {// 如果为空的话，主动调用
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
			BaseClass bpz = new BaseClass();
			bpz.setCls_id(Integer.valueOf(cls_id));
			bpz.setIs_del(0);
			bpz = getFacade().getBaseClassService().getBaseClass(bpz);
			if (null != bpz) {
				comm_no = bpz.getCls_code() + StringUtils.leftPad(String.valueOf(count), 4, "0");
			}

		}
		return comm_no;
	}

	/**
	 * 当前时间的过去几天
	 * 
	 * @return
	 */
	public String getNewDateGoOverDay(Integer goOverDay) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		// 过去七天
		c.setTime(new Date());
		c.add(Calendar.DATE, goOverDay);
		Date d = c.getTime();
		return dateFormat.format(d);

	}

	public OrderInfo pulibcWhere(String order_type, OrderInfo entity) {
		// 线下
		entity = publicWhereOrderType(order_type, entity);
		entity.setIs_test(0);// 过滤测试订单

		entity.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
		entity.setIs_check(Keys.AddIsCleck.ADD_IS_CLECK_0.getIndex());
		return entity;
	}

	// 获取最后结算时间
	public String getDate(UserInfo ui) {
		String st_finish_date = "2000-01-01 00:00:00";
		// 查看商家对账记录，看里面是否有数据，如果没数据设置st_finish_date为
		EntpDuiZhang entpDZ1 = new EntpDuiZhang();
		entpDZ1.setEntp_id(ui.getOwn_entp_id());
		entpDZ1.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		// entpDZ1.setIs_check(Keys.IsCleck.IS_CLECK_0.getIndex());
		entpDZ1.getMap().put("order_time", true);
		List<EntpDuiZhang> entpDZList = getFacade().getEntpDuiZhangService().getEntpDuiZhangList(entpDZ1);
		log.info("entpDZList===" + entpDZList.size());
		log.info("entpDZList===" + entpDZList);
		if (null != entpDZList && entpDZList.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = entpDZList.get(0).getEnd_check_date();
			st_finish_date = sdf.format(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.SECOND, 1);
			st_finish_date = sdf.format(cal.getTime());
		}
		return st_finish_date;
	}

	public OrderInfo publicWhereOrderType(String order_type, OrderInfo entity) {
		if (StringUtils.isNotBlank(order_type)
				&& Keys.OrderType.ORDER_TYPE_90.getIndex() == Integer.valueOf(order_type)) {
			entity.setOrder_type(Keys.OrderType.ORDER_TYPE_90.getIndex());
			// 支付时间一天前
			entity.getMap().put("en_pay_date", getNewDateGoOverDay(-1));

		} else {
			entity.getMap().put(
					"order_type_in",
					Keys.OrderType.ORDER_TYPE_10.getIndex() + "," + Keys.OrderType.ORDER_TYPE_7.getIndex() + ","
							+ Keys.OrderType.ORDER_TYPE_20.getIndex());
		}
		return entity;
	}

	/**
	 * 获取微信token
	 * 
	 * @return
	 */
	public static String getWeixinToKenBase() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", Keys.APP_ID);
		params.put("secret", "ee8905decfeb5ec2d9316a6d54b2bff7");
		params.put("grant_type", "client_credential");
		String url = "https://api.weixin.qq.com/cgi-bin/token";

		String returnResult = HttpUtils.sendPost(url, params);

		if (StringUtils.isNotBlank(returnResult)) {
			JSONObject data = JSONObject.parseObject(returnResult);
			String access_token = (String) data.get("access_token");
			return access_token;
		}
		return "";
	}

}