package com.ebiz.webapp.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebiz.webapp.domain.BaseData;

/**
 * @author Wu,Yang
 */
/**
 * @author 王志雄
 * @date 2018年3月24日
 */
public class Keys {
	private static final Log log = LogFactory.getLog(Keys.class);

	public static String ctxService = "";

	public static final String VERIFICATION_CODE = "verificationCode";

	public static final String SESSION_USERINFO_KEY = "userInfo";

	public static final String COOKIE_USERINFO_KEY_JSESSIONID = "cookie_userinfo_key_jsessionid";

	public static final String SYS_ENCODING = "UTF-8";

	public static final String DEFAULT_PASSWORD = "jgtf123456abc";

	public static final String BASE_PROVINCE_LIST = "base_province_list";

	public static final String COOKIE_P_INDEX = "cookie_p_index";

	public static final String IS_APP = "is_app";

	public static final String COOKIE_P_NAME = "cookie_p_name";

	public static final String CUSTOMER_COOKIE = "parId_cookie";

	public static final String COOKIE_ENTP_ID = "cookie_entp_id";

	public static final String IMAGE_EXT = "bmp,gif,jpeg,jpg,png";

	public static String ENTP_NOTE_MOD_ID = "1300520100";// 店铺公告mod_id

	public static String deafult_pass = "888888";// 默认密码

	public static Integer deafult_comm_attr_id = 0;// 默认规格id

	public static String deafult_comm_attr_name = "默认规格";// 默认规格id

	public static final String slideNavList = "slideNavList";

	public static List<BaseData> keysBaseData200List = new ArrayList<BaseData>();

	public static List<BaseData> keysBaseData900List = new ArrayList<BaseData>();

	public static BigDecimal rmb_to_fanxianbi_rate = new BigDecimal(0.16);

	public static Integer deafult_group_id = 0;// 默认分组id

	/**
	 * 商家返现积分比例
	 */
	public static BigDecimal rmb_to_fanxianbi_rate_en = new BigDecimal(0.32);

	public static final String push_action_show_news = "push_action_show_news";

	public static final String ads_mod_id = "1006002200";

	public static final String lun_bo_ads_mod_id = "1006002300";

	public static final String INIT_PWD = "123456";

	public static final String XIANXIA_CLS_ID = "20000";

	public static final int LOGIN_ERROR_TIME = 3;

	public static final String ENTP_ID = "1";

	public static final String DEFAULT_COMMENT = "五星好评";

	public static final Integer bi_dianzi_tixian_min = 10;

	public static String x = "";

	public static String y = "";

	// 其他类别cls_id
	public static Integer qita_cls_id = 20045;

	// 其他类别cls_id
	public static String qita_cls_name = "其他";

	public static Integer qita_cls_par_id = 20044;

	// 其他类别cls_id
	public static String activity_comm_name = "活动商品";

	/**
	 * APP UserAgent
	 */
	public final static String USER_AGENT = "Html5Plus";

	public final static String USER_AGENT_USER_ID = "user_id=";

	public final static Integer JD_NO_STOCK_CODE = 34;

	/**
	 * 微信支付begin
	 */

	// 微信支付用 APP_ID
	public static String APP_ID;

	// #商户号
	public static String MCH_ID;

	// #到商户后台设置
	public static String API_KEY;

	/**
	 * 微信支付end
	 */

	public static String app_name;

	public static String app_name_min;

	public static String nineporters_supermarket;

	public static String nineporters_jxc;

	public static String nineporters_sx;

	public static String nineporters_app_key;

	public static String nineporters_app_secret;

	public static String app_domain;

	public static String file_domain;

	public static String app_domain_no_www;

	public static String app_domain_m;

	public static String app_keywords;

	public static String app_description;

	public static String app_copyright;

	public static String app_tel;

	public static String app_fax;

	public static String app_beian_no;

	public static String app_jingying_no;

	public static String app_gongan_no;

	public static String app_gongan_url;

	public static String app_addr;

	public static String app_cls_level;

	public static String P_INDEX_INIT;

	public static String P_INDEX_P_NAME;

	public static String P_INDEX_CITY;

	public static String P_INDEX_CITY_NAME;

	public static String app_is_dandian;

	public static String app_qq;

	public static Boolean pay_type_is_audit_success;

	public static int ORDER_END_DATE = 7;// 订单默认7天后失效

	public static int COMM_UP_DATE = 365;// 商品默认上架时间

	public static int ORDER_TUIKUAN_RATE = 3;// 如果订单已经失效，退款时则扣除3%的手续费

	public static int SYS_ADMIN_ID = 1;// 平台管理账户ID

	public static int SYS_ADMIN_ENTP_ID = 1;// 平台管理账户企业ID

	public static String QUANGUO_P_INDEX = "100000";

	public static String QUANGUO_P_NAME = "全国";

	public static Integer USER_LEVEL_FX = 201;// 分享会员

	public static Integer USER_LEVEL_ONE = 202;// 1星会员

	public static Integer USER_LEVEL_TWO = 203;// 2星会员

	public static Integer USER_LEVEL_THREE = 204;// 3星会员

	public static Integer USER_LEVEL_FOUR = 205;// 4星会员

	public static Integer USER_LEVEL_FIVE = 206;// 5星会员

	public static Integer USER_LEVEL_MAX = 208;// 至尊会员等级

	public static Integer fp_money_limit_id = 1904;// 扶贫金额度超出，打款提醒

	public static Long PAR_ID = 1000000000l;

	public static Boolean THIRD_PAY_IS_OPEN_WITH_APP = true; // 第三方支付是否开启直接调用app方式支付

	public static String sms_url = "";

	public static String sms_account = "";

	public static String sms_password = "";

	public static String alipay_partner_pc = "";

	public static String alipay_seller_email_pc = "";

	public static String alipay_key_pc = "";

	public static String alipay_partner_m = "";

	public static String alipay_private_key_m = "";

	public static String alipay_app_id = "";

	public static String is_zhonghui = "";

	public static Boolean is_open_weixin_native_pay;

	public static Boolean is_open_pay_alipay;

	public static Boolean is_open_pay_weixin;

	public static Boolean is_open_pay_union;

	public static Boolean is_open_bi_dian;

	// 连连支付3个参数
	public static String lianlian_oid_partner = "";

	public static String lianlian_yt_pub_key = "";

	public static String lianlian_trader_pri_key = "";

	public static final String app_scan_login_url = "/app-login-{0}.html";

	// 微信支付用 APP_ID_APP
	public static String APP_ID_APP;

	// #商户号 FOR APP
	public static String MCH_ID_APP;

	// #到商户后台设置 FOR APP
	public static String API_KEY_APP;

	// 支付宝APP支付
	public static String alipay_appid_app;// APP_ID

	public static String alipay_partner_app;// 合作身份者ID

	public static String alipay_private_key_app;// 私钥

	public static String alipay_pubilc_key_app;// 支付宝公钥

	public static String alipay_private_key_app_rsa1;// 私钥rsa1

	public static String alipay_pubilc_key_app_rsa1;// 支付宝公钥rsa1

	// 通联支付
	public static String allinpay_url;

	public static String allinpay_cusid;

	public static String allinpay_appid;

	public static String allinpay_key;

	public static String jd_yuan_appkey;// 京东自营接口应用码

	public static String jd_entp_id;// 京东自营店铺ID

	public static String jd_freight_id;// 京东物流运费模板ID

	public static String jd_yuan_secret;// 京东自营接口签名

	public static String jd_customer_uid;// 京东默认客户uid

	public static String web_local_dir;// 项目部署目录

	public static final String JD_API_RESULT_STATUS_CODE = "200";// 京东自营接口返回状态的成功编码

	public static final String JD_API_RESULT_OK = "true";// 京东自营接口调用成功

	public static String ali_access_key_id;// 阿里云短信服务平台

	public static String ali_access_key_secret;// 阿里云短信服务平台

	public static String dysms_sign_name;// 阿里云短信服务短信签名

	public static String api_url = "http://api.9tiaofu.com";// 接口地址

	public final static String Merchant_entry_MOD_ID = "1019015001";// 商家入驻电子协议mod_id

	// 微信小程序支付用 APP_ID
	public static String MP_APP_ID;

	// #微信小程序关联商户号
	public static String MP_MCH_ID;

	// #微信小程序关联商户后台设置
	public static String MP_API_KEY;

	static {
		log.info("=======init keys===");
		Properties prop = new Properties();
		Properties propay = new Properties();
		Properties proAppPay = new Properties();
		try {
			prop.load(Keys.class.getResource("/webapp.config.properties").openStream());
			app_name = prop.getProperty("app_name");
			app_name_min = prop.getProperty("app_name_min");
			nineporters_supermarket = prop.getProperty("nineporters_supermarket");
			nineporters_jxc = prop.getProperty("nineporters_jxc");
			nineporters_sx = prop.getProperty("nineporters_sx");
			nineporters_app_key = prop.getProperty("nineporters_app_key");
			nineporters_app_secret = prop.getProperty("nineporters_app_secret");

			String str_rmb_to_fanxianbi_rate_en = prop.getProperty("rmb_to_fanxianbi_rate_en");

			// 获取最小的返比例
			try {
				rmb_to_fanxianbi_rate_en = new BigDecimal(str_rmb_to_fanxianbi_rate_en);
			} catch (Exception e) {
				rmb_to_fanxianbi_rate_en = new BigDecimal("0.32");
			}

			app_domain = prop.getProperty("app_domain");

			file_domain = prop.getProperty("file_domain");

			app_domain_no_www = prop.getProperty("app_domain_no_www");
			app_domain_m = prop.getProperty("app_domain_m");
			app_keywords = prop.getProperty("app_keywords");
			app_description = prop.getProperty("app_description");
			app_copyright = prop.getProperty("app_copyright");
			app_is_dandian = prop.getProperty("app_is_dandian");

			app_tel = prop.getProperty("app_tel");
			app_fax = prop.getProperty("app_fax");
			app_beian_no = prop.getProperty("app_beian_no");
			app_jingying_no = prop.getProperty("app_jingying_no");
			app_gongan_no = prop.getProperty("app_gongan_no");
			app_gongan_url = prop.getProperty("app_gongan_url");
			app_addr = prop.getProperty("app_addr");
			app_qq = prop.getProperty("app_qq");

			app_cls_level = prop.getProperty("app_cls_level");

			P_INDEX_INIT = prop.getProperty("p_index_init");
			P_INDEX_P_NAME = prop.getProperty("p_index_p_name");
			P_INDEX_CITY = prop.getProperty("p_index_city");
			P_INDEX_CITY_NAME = prop.getProperty("p_index_city_name");
			sms_url = prop.getProperty("sms_url");
			sms_account = prop.getProperty("sms_account");
			sms_password = prop.getProperty("sms_password");

			pay_type_is_audit_success = Boolean.valueOf(prop.getProperty("pay_type_is_audit_success"));

			is_open_pay_alipay = Boolean.valueOf(prop.getProperty("is_open_pay_alipay"));
			is_open_pay_weixin = Boolean.valueOf(prop.getProperty("is_open_pay_weixin"));
			is_open_pay_union = Boolean.valueOf(prop.getProperty("is_open_pay_union"));

			is_open_bi_dian = Boolean.valueOf(prop.getProperty("is_open_bi_dian"));

			jd_yuan_appkey = prop.getProperty("jd_yuan_appkey");
			jd_entp_id = prop.getProperty("jd_entp_id");
			jd_freight_id = prop.getProperty("jd_freight_id");
			jd_yuan_secret = prop.getProperty("jd_yuan_secret");
			jd_customer_uid = prop.getProperty("jd_customer_uid");
			web_local_dir = prop.getProperty("web_local_dir");

			ali_access_key_id = prop.getProperty("ali_access_key_id");
			ali_access_key_secret = prop.getProperty("ali_access_key_secret");
			dysms_sign_name = prop.getProperty("dysms_sign_name");

			propay.load(Keys.class.getResource("/pay.properties").openStream());

			APP_ID = propay.getProperty("APP_ID");
			MCH_ID = propay.getProperty("MCH_ID");
			API_KEY = propay.getProperty("API_KEY");

			alipay_partner_pc = propay.getProperty("alipay_partner_pc");
			alipay_seller_email_pc = propay.getProperty("alipay_seller_email_pc");
			alipay_key_pc = propay.getProperty("alipay_key_pc");
			alipay_partner_m = propay.getProperty("alipay_partner_m");
			alipay_private_key_m = propay.getProperty("alipay_private_key_m");

			alipay_app_id = propay.getProperty("alipay_app_id");
			alipay_partner_m = propay.getProperty("alipay_partner_m");

			is_open_weixin_native_pay = Boolean.valueOf(propay.getProperty("is_open_weixin_native_pay"));

			lianlian_oid_partner = propay.getProperty("lianlian_oid_partner");
			lianlian_yt_pub_key = propay.getProperty("lianlian_yt_pub_key");
			lianlian_trader_pri_key = propay.getProperty("lianlian_trader_pri_key");

			allinpay_url = propay.getProperty("allinpay_url");
			allinpay_cusid = propay.getProperty("allinpay_cusid");
			allinpay_appid = propay.getProperty("allinpay_appid");
			allinpay_key = propay.getProperty("allinpay_key");

			proAppPay.load(Keys.class.getResource("/pay_app.properties").openStream());

			APP_ID_APP = proAppPay.getProperty("APP_ID_APP");
			MCH_ID_APP = proAppPay.getProperty("MCH_ID_APP");
			API_KEY_APP = proAppPay.getProperty("API_KEY_APP");

			alipay_appid_app = proAppPay.getProperty("alipay_appid_app");
			alipay_partner_app = proAppPay.getProperty("alipay_partner_app");
			alipay_private_key_app = proAppPay.getProperty("alipay_private_key_app");
			alipay_pubilc_key_app = proAppPay.getProperty("alipay_pubilc_key_app");
			alipay_private_key_app_rsa1 = proAppPay.getProperty("alipay_private_key_app_rsa1");
			alipay_pubilc_key_app_rsa1 = proAppPay.getProperty("alipay_pubilc_key_app_rsa1");

			propay.load(Keys.class.getResource("/pay_mp.properties").openStream());

			MP_APP_ID = propay.getProperty("APP_ID");
			MP_MCH_ID = propay.getProperty("MCH_ID");
			MP_API_KEY = propay.getProperty("API_KEY");

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 首页是否显示拼团模块
	 */
	public static final String INDEX_SHOW_PT = "index_show_pt";

	/**
	 * 首页是否显示拼团模块
	 */
	public static final String INDEX_SHOW_YS = "index_show_ys";

	/**
	 * 是否启用验证码
	 */
	public static final String IS_ENABLED_CODE = "isEnabledCode";

	/**
	 * 是否启电子币支付
	 */
	public static final String isPayDianzi = "isPayDianzi";

	/**
	 * 首页是否启用静态页面加载
	 */
	public static final String IS_ENABLED_INDEX_STATIC = "isEnabledIndexStatic";

	/**
	 * 充许访问的IP
	 */
	public static final String AUTHORISED_IP = "authorisedIp";

	/**
	 * 是否启用接口IP过滤
	 */
	public static final String ISENABLED_WEBSERVICE_IP_FILTER = "isEnabledWebServiceIpFilter";

	/**
	 * 新闻上传图片大小
	 */
	public static final int[] NEWS_INFO_IMAGE_SIZE = new int[] {};

	/**
	 * 自动确认收货天数
	 */
	public static final String autoConfirmReceiptDays = "autoConfirmReceiptDays";

	/**
	 * 发货后收货延长天数
	 */
	public static final String fahuoShouhuoYanDays = "fahuoShouhuoYanDays";

	/**
	 * 系统版权信息
	 */
	public static final String websiteCopyright = "websiteCopyright";

	/**
	 * 描述
	 */
	public static final String APP_DESCRIPTION = "app_description";

	/**
	 * 店铺便签数量
	 */
	public static final String labelManager = "labelManager";

	/**
	 * 升级成付费会员需要缴费的金额
	 */
	public static final String upLevelNeedPayMoney = "upLevelNeedPayMoney";

	/**
	 * 首页分享
	 */
	public static final String ShareIndexDesc = "ShareIndexDesc";

	/**
	 * 二维码分享
	 */
	public static final String ShareQrCodeDesc = "ShareQrCodeDesc";

	/**
	 * 村门户分享
	 */
	public static final String ShareVillagePortalDesc = "ShareVillagePortalDesc";

	// 微信token
	public static final String weixin_token = "weixin_token";

	public static String weixin_token_key = "";

	/**
	 * 微信关注链接
	 */
	public static final String weixinGzUrl = "weixinGzUrl";

	public static final String MOBILE_VERI_CODE = "mobileVeriCode";

	public static final String EMAIL_VERI_CODE = "emailVeriCode";

	public static final String MOBILE_CODE = "mobileCode";

	/**
	 * 系统管理员手机号
	 */
	public static final String adminMobile = "adminMobile";

	/**
	 * 系统管理员手机号
	 */
	public static final String financialMobile = "financialMobile";

	/**
	 * 推荐村，推荐县
	 */
	public static final String tuijian_xian = "tuijian_xian";

	public static final String tuijian_cun = "tuijian_cun";

	/** 用户类型 **/
	public enum UserType {
		USER_TYPE_1("系统超级管理员", 1), USER_TYPE_2("注册会员", 2), USER_TYPE_4("村站", 4), USER_TYPE_100("后台管理人员", 100), USER_TYPE_19(
				"市门户管理员", 19);

		public static String getName(int index) {
			for (UserType c : UserType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private UserType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 角色类型 **/
	public enum RoleType {
		ROLE_TYPE_1("系统超级管理员", 1), ROLE_TYPE_2("注册会员", 2), ROLE_TYPE_3("商家", 3), ROLE_TYPE_4("驿站", 4), ROLE_TYPE_5(
				"合伙人", 5), ROLE_TYPE_19("市门户管理员", 19);

		public static String getName(int index) {
			for (RoleType c : RoleType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private RoleType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 删除状态 **/
	public enum IsDel {
		IS_DEL_0("未删除", 0), IS_DEL_1("已删除", 1);

		public static String getName(int index) {
			for (IsDel c : IsDel.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private IsDel(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 站内信状态 **/
	public enum CASH_TYPE {
		CASH_TYPE_10("账户余额提现", 10), CASH_TYPE_20("发放扶贫金", 20), CASH_TYPE_30("货款提现", 30), CASH_TYPE_40("福利金提现", 40);

		public static String getName(int index) {
			for (CASH_TYPE c : CASH_TYPE.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private CASH_TYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum INFO_STATE {
		INFO_STATE_0("待审核", 0), INFO_STATE_1("审核通过", 1), INFO_STATE_Not("审核不通过", -1), INFO_STATE_2("已付款", 2), INFO_STATE_X2(
				"已退款", -2);

		public static String getName(int index) {
			for (INFO_STATE c : INFO_STATE.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private INFO_STATE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum CARD_INFO_STATE {
		CARD_INFO_STATE_X1("作废", -1), CARD_INFO_STATE_0("新卡", 0), CARD_INFO_STATE_1("部分分配", 1), CARD_INFO_STATE_2(
				"全部分配", 2);

		public static String getName(int index) {
			for (CARD_INFO_STATE c : CARD_INFO_STATE.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private CARD_INFO_STATE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum SEND_STATE {
		SEND_STATE_0("未发送", 0), SEND_STATE_1("发送不成功", 1), SEND_STATE_2("发送成功", 2);

		public static String getName(int index) {
			for (SEND_STATE c : SEND_STATE.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private SEND_STATE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 站内信状态 **/
	public enum MSG_TYPE {
		MSG_TYPE_0("站内信息", 0), MSG_TYPE_10("商家申请留言", 10), MSG_TYPE_20("意见反馈", 20), MSG_TYPE_30("商家申请审核不通过", 30);

		public static String getName(int index) {
			for (MSG_TYPE c : MSG_TYPE.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private MSG_TYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 货币类型 **/
	public enum BiType {
		BI_TYPE_100("余额", 100), BI_TYPE_200("待返余额", 200), BI_TYPE_300("货款", 300), BI_TYPE_400("待返货款", 400), BI_TYPE_500(
				"扶贫金", 500), BI_TYPE_600("待返扶贫金", 600), BI_TYPE_700("福利金", 700), BI_TYPE_800("福利卡", 800);

		public static String getName(int index) {
			for (BiType c : BiType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private BiType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 余额、货款获取方式 账户余额支出 **/
	public enum BiGetType {
		BI_GET_TYPE_X1("系统修复", -1, "BI_GET_TYPE_X1"), BI_GET_TYPE_1("系统赠送", 1, "BI_GET_TYPE_1"), BI_GET_TYPE_20(
				"推广消费奖励", 20, "BI_GET_TYPE_20"), BI_GET_TYPE_30("商家直推奖", 30, "BI_GET_TYPE_30"), BI_GET_TYPE_100("货款提现",
				100, "BI_GET_TYPE_100"), BI_GET_TYPE_130("消费分红", 130, "BI_GET_TYPE_130"), BI_GET_TYPE_131("销售分红", 131,
				"BI_GET_TYPE_131"), BI_GET_TYPE_140("账户余额充值", 140, "BI_GET_TYPE_140"), BI_GET_TYPE_150("普通订单货款", 150,
				"BI_GET_TYPE_150"), BI_GET_TYPE_160("退款获得账户余额", 160, "BI_GET_TYPE_160"), BI_GET_TYPE_170(
				"取消商家获得货款额度的账户余额", 170, "BI_GET_TYPE_170"), BI_GET_TYPE_200("账户余额转入", 200, "BI_GET_TYPE_200"), BI_OUT_TYPE_10(
				"账户余额购买订单", -10, "BI_OUT_TYPE_10"), BI_OUT_TYPE_30("货款充值账户余额", -30, "BI_OUT_TYPE_30"), BI_OUT_TYPE_40(
				"账户余额线下扫码", -40, "BI_OUT_TYPE_40"), BI_OUT_TYPE_50("账户余额提现", -50, "BI_OUT_TYPE_50"), BI_OUT_TYPE_51(
				"发放扶贫金", -51, "BI_OUT_TYPE_51"), BI_OUT_TYPE_52("福利金体现", -52, "BI_OUT_TYPE_52"), BI_OUT_TYPE_60("货款提现",
				-60, "BI_OUT_TYPE_60"), BI_OUT_TYPE_70("取消商家货款提现转账户余额", -70, "BI_OUT_TYPE_70"), BI_OUT_TYPE_80(
				"锁定账户余额转账户余额", -80, "BI_OUT_TYPE_80"), BI_OUT_TYPE_90("账户余额转出", -90, "BI_OUT_TYPE_90"), BI_OUT_TYPE_110(
				"锁定账户余额购买订单", -110, "BI_OUT_TYPE_110"), BI_OUT_TYPE_150("账户余额商家扫码支付", -150, "BI_OUT_TYPE_150"), BI_OUT_TYPE_160(
				"货款商家扫码支付", -160, "BI_OUT_TYPE_160"), BI_GET_TYPE_111("提现未成功账户余额退回", 111, "BI_GET_TYPE_111"), BI_GET_TYPE_112(
				"提现未成功货款退回", 112, "BI_GET_TYPE_112"), BI_GET_TYPE_113("提现未成功扶贫金退回", 113, "BI_GET_TYPE_113"), BI_GET_TYPE_114(
				"提现未成功福利金退回", 114, "BI_GET_TYPE_114"), BI_GET_TYPE_220("平台退还货款", 220, "BI_GET_TYPE_X1"), BI_GET_TYPE_300(
				"退货退款（买家承担物流费用）", 300, "BI_GET_TYPE_220"), BI_GET_TYPE_310("退货退款（卖家承担物流费用）", 310, "BI_GET_TYPE_310"), BI_GET_TYPE_320(
				"换货（买家承担物流费用）", 320, "BI_GET_TYPE_320"), BI_GET_TYPE_330("换货（卖家承担物流费用）", 330, "BI_GET_TYPE_330"), BI_GET_TYPE_340(
				"仅退款", 340, "BI_GET_TYPE_340"), BI_GET_TYPE_350("用户仅退款，扣除商家货款", 350, "BI_GET_TYPE_350"), BI_GET_TYPE_380(
				"订单运费货款", 380, "BI_GET_TYPE_380"), BI_OUT_TYPE_400("货款购买订单", -400, "BI_OUT_TYPE_400"), BI_GET_TYPE_500(
				"商家扶贫", 500, "BI_GET_TYPE_500"), BI_GET_TYPE_1001("消费返现奖励", 1001, "BI_GET_TYPE_1001"), BI_GET_TYPE_1002(
				"代言人奖励", 1002, "BI_GET_TYPE_1002"), BI_GET_TYPE_1003("驿站合伙人奖励", 1003, "BI_GET_TYPE_1003"), BI_GET_TYPE_1004(
				"普通县域合伙人奖励", 1004, "BI_GET_TYPE_1004"), BI_GET_TYPE_1005("股份县域合伙人奖励", 1005, "BI_GET_TYPE_1005"), BI_GET_TYPE_2001(
				"个人商品销售款", 2001, "BI_GET_TYPE_2001"), BI_GET_TYPE_71("商家对账", 71, "BI_GET_TYPE_71"), BI_GET_TYPE_72(
				"对账未通过货款增加", 72, "BI_GET_TYPE_72"), BI_GET_TYPE_3001("九个挑夫生鲜扶贫", 3001, "BI_GET_TYPE_3001"), BI_GET_TYPE_3002(
				"线下订单删除", 3002, "BI_GET_TYPE_3002"), BI_GET_TYPE_4000("福利金导入", 4000, "BI_GET_TYPE_4000"), BI_OUT_TYPE_801(
				"福利卡抵扣", -801, "BI_OUT_TYPE_801"), BI_GET_TYPE_4100("线下活动扫码订单货款", 4100, "BI_GET_TYPE_4100"), ;

		public static String getName(int index) {
			for (BiGetType c : BiGetType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private BiGetType(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSon_type() {
			return son_type;
		}

		public void setSon_type(String son_type) {
			this.son_type = son_type;
		}
	}

	public enum ScoreType {
		SCORE_TYPE_0("商品", 0), SCORE_TYPE_1("返现积分(个人)", 1), SCORE_TYPE_2("赠送积分(个人)", 2), SCORE_TYPE_5("申请合伙人(个人)", 5), SCORE_TYPE_11(
				"赠送积分(商家)", 11);

		public static String getName(int index) {
			for (ScoreType c : ScoreType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private ScoreType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum JifenType {
		JifenType_10("推广消费奖励", 10), JifenType_20("商家直推奖", 20), JifenType_21("商家派送奖励", 21), JifenType_30("合伙人奖励", 30), JifenType_31(
				"省级合伙人奖励", 31), JifenType_32("市级合伙人奖励", 32), JifenType_33("县级合伙人奖励", 33), JifenType_40("至尊会员奖励", 40), JifenType_50(
				"消费分红", 50), JifenType_60("合伙人上级增加30%奖励", 60), JifenType_70("直推服务专员", 70), JifenType_81("省合伙人一级奖励", 81), JifenType_82(
				"市合伙人一级奖励", 82), JifenType_83("县合伙人一级奖励", 83), JifenType_90("专员奖励", 90), JifenType_91("省级专员奖励", 91), JifenType_92(
				"市级专员奖励", 92), JifenType_93("县级专员奖励", 93), JifenType_101("专员一级奖励", 101);

		public static String getShowName(int index) {
			for (ScoreType c : ScoreType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private JifenType(String showName, int index) {
			this.showName = showName;
			this.index = index;
		}

		private String showName;

		private int index;

		public String getShowName() {
			return showName;
		}

		public void setShowName(String showName) {
			this.showName = showName;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum JifenTypeShow {
		JifenTypeShow_1("系统赠送", 1), JifenTypeShow_10("返现卡返现", 10), JifenTypeShow_11("返现积分(个人)", 11), JifenTypeShow_12(
				"返现积分(联盟)", 12), JifenTypeShow_20("积分返利", 20), JifenTypeShow_30("同级奖励", 30), JifenTypeShow_40(
				"联盟主4级奖励", 40), JifenTypeShow_50("合伙人区域奖励", 50), JifenTypeShow_90("直接邀请人奖励", 90);

		public static String getName(int index) {
			for (ScoreType c : ScoreType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private JifenTypeShow(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 企业类型 **/
	public enum EntpType {
		ENTP_TYPE_10("普通店铺", 10), ENTP_TYPE_20("合伙人店铺", 20), ENTP_TYPE_30("无人超市", 30);

		public static String getName(int index) {
			for (EntpType c : EntpType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private EntpType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 店铺类型 **/
	public enum ShopType {
		SHOP_TYPE_10("个人", 10), SHOP_TYPE_20("企业", 20);

		public static String getName(int index) {
			for (EntpType c : EntpType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private ShopType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 合伙人级别 **/
	public enum ServiceCenterLevel {
		SERVICE_CENTER_LEVEL_1("普通合伙人", 1, "SERVICE_CENTER_LEVEL_1"), SERVICE_CENTER_LEVEL_2("股份合伙人", 2,
				"SERVICE_CENTER_LEVEL_2");

		public static String getName(int index) {
			for (ServiceCenterLevel c : ServiceCenterLevel.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private ServiceCenterLevel(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSon_type() {
			return son_type;
		}

		public void setSon_type(String son_type) {
			this.son_type = son_type;
		}

	}

	// 分享设置
	public enum ShareSettings {
		SystemSettings_10("系统设置", 10, "SystemSettings_10"), SystemSettings_SHARE_20("分享设置", 20,
				"SystemSettings_SHARE_20"), SystemSettings_30("推荐县村站设置", 30, "SystemSettings_30"), ;
		public static String getName(int index) {
			for (ShareSettings c : ShareSettings.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private ShareSettings(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSon_type() {
			return son_type;
		}

		public void setSon_type(String son_type) {
			this.son_type = son_type;
		}

	}

	/** 订单类型 **/
	public enum OrderType {
		ORDER_TYPE_10("商品订单", 10, "ORDER_TYPE_10"), ORDER_TYPE_7("村圈子商品订单", 7, "ORDER_TYPE_7"), ORDER_TYPE_20("付费升级订单",
				20, "ORDER_TYPE_20"), ORDER_TYPE_30("京东订单", 30, "ORDER_TYPE_30"), ORDER_TYPE_40("无人超市订单", 40,
				"ORDER_TYPE_40"), ORDER_TYPE_50("鲜食汇订单", 50, "ORDER_TYPE_50"), ORDER_TYPE_60("线下订单", 60,
				"ORDER_TYPE_60"), ORDER_TYPE_70("福利商城订单", 70, "ORDER_TYPE_70"), ORDER_TYPE_80("预售订单", 80,
				"ORDER_TYPE_80"), ORDER_TYPE_90("线下活动订单", 90, "ORDER_TYPE_90"), ORDER_TYPE_100("拼团订单", 100,
				"ORDER_TYPE_100");

		public static String getName(int index) {
			for (OrderType c : OrderType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private OrderType(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSonType() {
			return son_type;
		}

		public void setSonType(String son_type) {
			this.son_type = son_type;
		}
	}

	/** 商品类型 **/
	public enum CommType {
		COMM_TYPE_2("实物商品", 2, "COMM_TYPE_2"), COMM_TYPE_4("促销商品", 4, "COMM_TYPE_4"), COMM_TYPE_5("抢购商品", 5,
				"COMM_TYPE_5"), COMM_TYPE_7("村圈子商品", 7, "COMM_TYPE_7"), COMM_TYPE_9("无人超市商品", 9, "COMM_TYPE_9"), COMM_TYPE_10(
				"预售商品", 10, "COMM_TYPE_10"), COMM_TYPE_20("拼团商品", 20, "COMM_TYPE_20"), COMM_TYPE_30("线下扫码活动商品", 30,
				"COMM_TYPE_30");

		public static String getName(int index) {
			for (CommType c : CommType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private CommType(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSonType() {
			return son_type;
		}

		public void setSonType(String son_type) {
			this.son_type = son_type;
		}
	}

	/** 购物车类型 **/
	public enum CartType {
		CART_TYPE_10("商品订单", 10, "CART_TYPE_10"), CART_TYPE_20("福利商品订单", 20, "CART_TYPE_20"), CART_TYPE_30("预售订单", 30,
				"CART_TYPE_30"), CART_TYPE_100("拼团订单", 100, "CART_TYPE_100");

		public static String getName(int index) {
			for (CartType c : CartType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private CartType(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSonType() {
			return son_type;
		}

		public void setSonType(String son_type) {
			this.son_type = son_type;
		}
	}

	/** 福利卡类型 **/
	public enum CardType {
		CARD_TYPE_10("实体卡", 10, "CARD_TYPE_10"), CARD_TYPE_20("电子卡", 20, "CARD_TYPE_20");

		public static String getName(int index) {
			for (CartType c : CartType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private CardType(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSonType() {
			return son_type;
		}

		public void setSonType(String son_type) {
			this.son_type = son_type;
		}
	}

	/** 支付类型 **/
	public enum PayType {
		PAY_TYPE_0("货到付款", 0, "PAY_TYPE_0"), PAY_TYPE_1("支付宝", 1, "PAY_TYPE_1"), PAY_TYPE_3("微信支付", 3, "PAY_TYPE_3"), PAY_TYPE_4(
				"通联支付", 4, "PAY_TYPE_4"), PAY_TYPE_6("现金", 6, "PAY_TYPE_6"), PAY_TYPE_7("福利卡支付", 7, "PAY_TYPE_7");
		public static String getName(int index) {
			for (PayType c : PayType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private PayType(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSonType() {
			return son_type;
		}

		public void setSonType(String son_type) {
			this.son_type = son_type;
		}

	}

	public enum PayTypeName {
		ALIPAY("支付宝订单", 1), UNIONPAY("服务费银联支付订单", 2), WEIXIN("微信支付订单", 3), WEIXINAPP("微信APP支付订单", 3);
		public static String getName(int index) {
			for (PayType c : PayType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private PayTypeName(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum BASE_DATA_ID {
		BASE_DATA_ID_200("用户等级", 200), BASE_DATA_ID_900("币种兑换规则", 900), BASE_DATA_ID_904("100人民币可以兑换100账户余额", 904), BASE_DATA_ID_905(
				"100货款可以兑换100人民币", 905), BASE_DATA_ID_906("100货款可以兑换100账户余额", 906), BASE_DATA_ID_907("1余额可以兑换100积分",
				907), BASE_DATA_ID_601("货款提现0%手续费", 601), BASE_DATA_ID_602("账户余额提现0‰手续费", 602), BASE_DATA_ID_1043(
				"推广消费奖励", 1043), BASE_DATA_ID_10000("退货原因", 10000), BASE_DATA_ID_2100("促销时间设置", 2100), BASE_DATA_ID_605(
				"余额提现最低手续费2元", 605), BASE_DATA_ID_606("余额提现最高手续费25元", 606), BASE_DATA_ID_1901("一个商品最多扶贫对象数量", 1901), BASE_DATA_ID_1902(
				"一个贫困户最多拥有扶贫商品数量", 1902), BASE_DATA_ID_1903("一个贫困户年度获取扶贫金额限制", 1903), BASE_DATA_ID_604("余额提现最低金额", 604), BASE_DATA_ID_603(
				"扶贫金提现手续费", 603), BASE_DATA_ID_607("商家结算手续费", 607), BASE_DATA_ID_608("福利金提现手续费", 608), BASE_DATA_ID_609(
				"福利金提现最低金额", 609), BASE_DATA_ID_610("福利金提现最低手续费2元", 610), BASE_DATA_ID_611("福利金提现最高手续费25元", 611);

		public static String getName(int index) {
			for (BASE_DATA_ID c : BASE_DATA_ID.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private BASE_DATA_ID(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum BI_CHU_OR_RU {
		BASE_DATA_ID_1("入", 1), BASE_DATA_ID_NEGATIVE1("出", -1);

		public static String getName(int index) {
			for (BI_CHU_OR_RU c : BI_CHU_OR_RU.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private BI_CHU_OR_RU(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 是否添加对账 */
	public enum AddIsCleck {
		ADD_IS_CLECK_0("未添加对账", 0), ADD_IS_CLECK_1("添加对账", 1);
		public static String getName(int index) {
			for (IsCleck c : IsCleck.values()) {
				if (c.getIndex() == index) {
					return c.getName();
				}
			}
			return null;
		}

		private String name;

		private int index;

		private AddIsCleck(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	/** 是否对账 */
	public enum IsCleck {
		IS_CLECK_0("待结算", 0), IS_CLECK_X1("结算失败", -1), IS_CLECK_1("结算成功", 1), IS_CLECK_15("付款成功", 15), IS_CLECK_20(
				"退款成功", 20);
		public static String getName(int index) {
			for (IsCleck c : IsCleck.values()) {
				if (c.getIndex() == index) {
					return c.getName();
				}
			}
			return null;
		}

		private String name;

		private int index;

		private IsCleck(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	/** 企业是否关店 **/
	public enum IsClosed {
		IS_CLOSED_0("否", 0), IS_CLOSED_1("是", 1);

		public static String getName(int index) {
			for (IsClosed c : IsClosed.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private IsClosed(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 商品是否上架 **/
	public enum IsSell {
		IS_SELL_0("下架", 0), IS_SELL_1("上架", 1);

		public static String getName(int index) {
			for (IsSell c : IsSell.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private IsSell(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 订单状态 **/
	public enum OrderState {
		ORDER_STATE_X90("异常订单", -90), ORDER_STATE_X20("已退款", -20), ORDER_STATE_X10("已取消", -10), ORDER_STATE_0("已预订", 0), ORDER_STATE_10(
				"等待发货", 10), ORDER_STATE_15("退款/换货申请中", 15), ORDER_STATE_20("已发货(待确认收货)", 20), ORDER_STATE_40("已收货", 40), ORDER_STATE_50(
				"交易成功", 50), ORDER_STATE_90("关闭", 90);

		public static String getName(int index) {
			for (OrderState c : OrderState.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private OrderState(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 订单状态 **/
	public enum Fp_State {
		Fp_State_0("不需要发票", 0), Fp_State_1("需要发票", 1), Fp_State_2("发票已寄送", 2);

		public static String getName(int index) {
			for (Fp_State c : Fp_State.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private Fp_State(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 合伙人缴费 **/
	public enum BaseDataServiceCenter {
		BASE_DATA_SERVICE_CENTER_1801("省级合伙人缴费金额", 1801), BASE_DATA_SERVICE_CENTER_1802("市级合伙人缴费金额", 1802), BASE_DATA_SERVICE_CENTER_1803(
				"县级合伙人缴费金额", 1803), BASE_DATA_SERVICE_CENTER_1804("申请市级合伙人平台赠送积分", 1804), BASE_DATA_SERVICE_CENTER_1805(
				"申请区县合伙人平台赠送积分", 1805), BASE_DATA_SERVICE_CENTER_1806("申请省级合伙人平台赠送积分", 1806);

		public static String getName(int index) {
			for (BaseDataServiceCenter c : BaseDataServiceCenter.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private BaseDataServiceCenter(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	/** 折扣规则：商家 **/
	public enum FanXianTypeEntp {
		FanXianType_100("满100元赠100元", 701), FanXianType_200("满200元赠100元", 702);

		public static String getName(int index) {
			for (FanXianTypeEntp c : FanXianTypeEntp.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private FanXianTypeEntp(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum FanXianTypeComm {
		FanXianType_100("满100元赠100元", 701), FanXianType_200("满200元赠100元", 702);

		public static String getName(int index) {
			for (FanXianTypeComm c : FanXianTypeComm.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private FanXianTypeComm(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum TongjiType {
		TONGJITYPE_10("用户注册统计", 10), TONGJITYPE_20("平台返现统计(按年)", 20), TONGJITYPE_21("平台返现统计(按月)", 21), TONGJITYPE_30(
				"个人返现统计(按年)", 30), TONGJITYPE_31("个人返现统计(按月)", 31), TONGJITYPE_40("平台提现总金额", 40);

		public static String getName(int index) {
			for (TongjiType c : TongjiType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private TongjiType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum ChiType {
		CHI_TYPE_0("保障池", 0), CHI_TYPE_1("主池", 1);

		public static String getName(int index) {
			for (ChiType c : ChiType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private ChiType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum LineType {
		LINE_TYPE_300("300通道", 300);

		public static String getName(int index) {
			for (LineType c : LineType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private LineType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum FuXiaoState {
		FUXIAO_STATE_0("不需要复消", 0), FUXIAO_STATE_1("需要复消", 1), FUXIAO_STATE_2("已复消", 2);

		public static String getName(int index) {
			for (FuXiaoState c : FuXiaoState.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private FuXiaoState(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum BaseFilesType {
		Base_Files_TYPE_30("登录图片", 30), Base_Files_TYPE_40("评论商品图片", 40), Base_Files_TYPE_50("村相册", 50), Base_Files_TYPE_60(
				"县相册", 60), Base_Files_TYPE_CITY("市相册", 70), BASE_FILES_TYPE_CITY_BACKGROUND("市背景", 80), BASE_FILES_TYPE_DAPINSHOW100(
				"大屏显示图片", 100);

		public static String getName(int index) {
			for (BaseFilesType c : BaseFilesType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private BaseFilesType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/*
	 * Base_Data_type
	 */
	public enum BaseDataType {
		Base_Data_type_10("用户类型", 10), Base_Data_type_90("短信模板", 90), Base_Data_type_301("标签类型", 301), Base_Data_type_100(
				"消费提现金额", 100), Base_Data_type_101("余额复销券", 101), Base_Data_type_200("用户等级", 200), Base_Data_type_1123(
				"村产品类别", 1123), Base_Data_type_3100("公司性质", 3100);

		public static String getName(int index) {
			for (BaseDataType c : BaseDataType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private BaseDataType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** BiState **/
	public enum BiState {
		BI_STATE_0("正常账户余额", 0), BI_STATE_1("错失账户余额", 1), BI_STATE_2("锁定账户余额", 2), BI_STATE_3("推广账户余额解锁", 3);

		public static String getName(int index) {
			for (BiState c : BiState.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private BiState(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** OptType **/
	public enum OptType {
		OPT_TYPE_1("取消合伙人", 1), OPT_TYPE_2("普通商品上架审核", 2), OPT_TYPE_3("普通商品下架审核", 3), OPT_TYPE_4("取消村驿站", 4), OPT_TYPE_10(
				"实名认证审核", 10), OPT_TYPE_11("退款/换货审核", 11);

		public static String getName(int index) {
			for (OptType c : OptType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private OptType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** OptType **/
	public enum CommentType {
		COMMENT_TYPE_1("评论", 1), COMMENT_TYPE_2("回复", 2), COMMENT_TYPE_3("点赞", 3), COMMENT_TYPE_10("消费商品评论", 10), COMMENT_TYPE_11(
				"实物商品评论", 11), COMMENT_TYPE_140("团购商品评论", 140);

		public static String getName(int index) {
			for (CommentType c : CommentType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private CommentType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 操作日志类型 **/
	public enum SysOperType {
		SysOperType_10("管理端登录日志", 10), SysOperType_11("移动端登录日志", 11), SysOperType_20("取消合伙人", 20), SysOperType_30(
				"输入密码错误", 30), SysOperType_40("最后检测订单时间", 40), SysOperType_50("订单红包返利记录", 50), SysOperType_60(
				"订单APP推送记录", 60), SysOperType_70("用户升级日志", 70), SysOperType_80("取消村驿站", 80), SysOperType_90("退款审核", 90), SysOperType_1000(
				"自定义日志", 1000);

		public static String getName(int index) {
			for (SysOperType c : SysOperType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private SysOperType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 触屏版TopBtns **/
	public enum TopBtns {
		REGISTER("注册", 1), ADD("添加", 2), VIEW("查看明细", 3), INDEX("电脑版", 4), SEARCH("搜索", 5), TOJS("去结算", 6);

		public static String getName(int index) {
			for (TopBtns c : TopBtns.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private TopBtns(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	// 支付平台：PC,WAP,APP_ANDROID,APP_IPHONE
	public enum PayPlatform {
		PC("PC", 10), WAP("WAP", 20), APP_ANDROID("APP_ANDROID", 31), APP_IPHONE("APP_IPHONE", 32), MIN("MIN", 40);

		public static String getName(int index) {
			for (TopBtns c : TopBtns.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private PayPlatform(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum ModGroup {
		MOD_GROUP_1("管理员后台节点", 1), MOD_GROUP_2("注册会员后台节点", 2), MOD_GROUP_3("商家后台节点", 3), MOD_GROUP_4("村站后台节点", 4), MOD_GROUP_5(
				"合伙人后台节点", 5), MOD_GROUP_6("市门户后台节点", 6), MOD_GROUP_10("共用节点", 10);

		public static String getName(int index) {
			for (ModGroup c : ModGroup.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private ModGroup(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum ModLevel {
		MOD_LEVEL_1("一级", 1), MOD_LEVEL_2("二级", 2), MOD_LEVEL_3("三级", 3);

		public static String getName(int index) {
			for (ModLevel c : ModLevel.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private ModLevel(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	// 图片类型
	public enum BaseImgsType {
		Base_Imgs_TYPE_10("身份证", 10), Base_Imgs_TYPE_20("退款退货图片", 20), Base_Imgs_TYPE_30("合伙人身份证", 30), Base_Imgs_TYPE_40(
				"动态图片", 40), Base_Imgs_TYPE_50("动态商品图片", 50), Base_Imgs_TYPE_60("发票凭证", 60);

		public static String getName(int index) {
			for (BaseImgsType c : BaseImgsType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private BaseImgsType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 红包大类HB_CLASS **/
	public enum HbClass {
		HbClass_10("用户推广分享红包", 10), HbClass_20("订单红包", 20);

		public static String getName(int index) {
			for (HbClass c : HbClass.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private HbClass(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 红包类型HB_TYPE **/
	public enum HbType {
		HbType_10("定额红包", 10), HbType_20("随机红包", 20);

		public static String getName(int index) {
			for (HbType c : HbType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private HbType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 优惠券规则类型RUEL_TYPE **/
	public enum RuleType {
		RuleType_10("满减", 10);

		public static String getName(int index) {
			for (RuleType c : RuleType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private RuleType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 优惠劵来源ORIGIN_TYPE **/
	public enum OriginType {
		OriginType_11("注册领取红包", 11), OriginType_12("分享邀请会员奖励", 12), OriginType_20("订单红包", 20);

		public static String getName(int index) {
			for (OriginType c : OriginType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private OriginType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 退货审核状态AUDIT_STATUS **/
	public enum audit_state {
		audit_state_fu_2("商家审核不通过", -2), audit_state_fu_1("平台审核不通过", -1), audit_state_0("待审核", 0), audit_state_1(
				"平台审核通过", 1), audit_state_2("商家审核通过", 2), audit_state_3("待平台处理", 3);

		public static String getName(int index) {
			for (audit_state c : audit_state.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private audit_state(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 返现等级参数 **/
	public enum ReturnMoneyLevel {
		ReturnMoneyLevel_20001("返现等级", 20001);

		public static String getName(int index) {
			for (ReturnMoneyLevel c : ReturnMoneyLevel.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private ReturnMoneyLevel(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 系统类型 **/
	public enum SystemType {
		SystemType_10001("系统类型", 10001);

		public static String getName(int index) {
			for (SystemType c : SystemType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private SystemType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 退货标识 **/
	public enum Is_Tuihuo {
		TuiHuo_0("未申请", 0), TuiHuo_1("已申请", 1), TuiHuo_2("已换货", 2);

		public static String getName(int index) {
			for (Is_Tuihuo c : Is_Tuihuo.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private Is_Tuihuo(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/**
	 * 退货类型
	 **/
	public enum expectReturnWay {
		expectReturnWay_1("退货退款", 1), expectReturnWay_2("换货", 2), expectReturnWay_4("未发货退款", 4);

		public static String getName(int index) {
			for (expectReturnWay c : expectReturnWay.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private expectReturnWay(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 退货原因 **/
	public enum return_why {
		return_why_11630("未发货前无理由退款", 83958);

		public static String getName(int index) {
			for (return_why c : return_why.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private return_why(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 售后方式 **/
	public enum return_type {
		return_type_1_TuiHuoTuiKuan("退货退款", 1), return_type_2_HuanHuo("换货", 2), return_type_3_tuikuan("退款", 3), return_type_4_tuikuan(
				"未发货退款", 4);

		public static String getName(int index) {
			for (return_type c : return_type.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private return_type(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 商品类型 **/
	public enum PromotionType {
		PROMOTION_TYPE_0("团购 ", 0, "PROMOTION_TYPE_0"), PROMOTION_TYPE_10("拼团商品 ", 3, "COMM_TYPROMOTION_TYPE_10PE_3");

		public static String getName(int index) {
			for (PromotionType c : PromotionType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private PromotionType(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSonType() {
			return son_type;
		}

		public void setSonType(String son_type) {
			this.son_type = son_type;
		}
	}

	/** 套餐组合type **/
	public enum tczh_type {
		tczh_type_0("普通商品套餐", 0, "tczh_type_0"), tczh_type_1("团购套餐类型", 1, "tczh_type_1");

		public static String getName(int index) {
			for (tczh_type c : tczh_type.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private tczh_type(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSonType() {
			return son_type;
		}

		public void setSonType(String son_type) {
			this.son_type = son_type;
		}
	}

	/** 分类类型 **/
	public enum CLS_SCOPE_TYPE {
		CLS_SCOPE_1("线上店铺分类", 1), CLS_SCOPE_2("线下店铺分类", 2), CLS_SCOPE_3("商城新闻", 3);

		public static String getName(int index) {
			for (CLS_SCOPE_TYPE c : CLS_SCOPE_TYPE.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private CLS_SCOPE_TYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	/** base_link类型 **/
	public enum M_BASE_LINK_TYPE {
		LINK_TYPE_40("标签分类", 40), LINK_TYPE_100("合作人分类", 100), LINK_TYPE_200("县推荐", 200), LINK_TYPE_1000("县轮播图", 1000);

		public static String getName(int index) {
			for (M_BASE_LINK_TYPE c : M_BASE_LINK_TYPE.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private M_BASE_LINK_TYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	/**
	 * 是否 0：不是 1：是
	 **/
	public enum YhqState {
		YHQ_STATE_10_N("不可用", -10), YHQ_STATE_0("未领取", 0), YHQ_STATE_10("已领取", 10), YHQ_STATE_20("已使用", 20);

		public static String getName(int index) {
			for (YhqState c : YhqState.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private YhqState(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 优惠券使用规则 **/
	public enum YhqSygz {
		YHQ_SYGZ_10("全场通用", 10, "YHQ_SYGZ_10"), YHQ_SYGZ_20("部分类别可用", 20, "YHQ_SYGZ_20"), YHQ_SYGZ_30("部分类别不可用", 30,
				"YHQ_SYGZ_30"), YHQ_SYGZ_40("部分商品可用 ", 40, "YHQ_SYGZ_40");

		public static String getName(int index) {
			for (YhqSygz c : YhqSygz.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private YhqSygz(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSonType() {
			return son_type;
		}

		public void setSonType(String son_type) {
			this.son_type = son_type;
		}
	}

	/** 优惠券类型 **/
	public enum YhqType {
		YHQ_TYPE_10("普通优惠券", 10, "YHQ_TYPE_10"), YHQ_TYPE_20("初次购物券", 20, "YHQ_TYPE_20");

		public static String getName(int index) {
			for (YhqType c : YhqType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private YhqType(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSonType() {
			return son_type;
		}

		public void setSonType(String son_type) {
			this.son_type = son_type;
		}
	}

	/**
	 * 是否 0：不是 1：是
	 **/
	public enum YesOrNo {
		No("不是", 0), Yes("是", 1);

		public static String getName(int index) {
			for (YesOrNo c : YesOrNo.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private YesOrNo(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/**
	 * 是否 0：不是 1：是
	 **/
	public enum COMM_INFO_SON_TYPE {
		COMM_INFO_SON_TYPE_1("普通评论", 1), COMM_INFO_SON_TYPE_2("赞", 2), COMM_INFO_SON_TYPE_3("踩", 3);

		public static String getName(int index) {
			for (COMM_INFO_SON_TYPE c : COMM_INFO_SON_TYPE.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private COMM_INFO_SON_TYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	// 优惠和运费类型
	public enum DiscountType {
		DISCOUNT_TYPE_0("DISCOUNT_TYPE_0", 0);

		public static String getName(int index) {
			for (DiscountType c : DiscountType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private DiscountType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	// 进出货类型
	public enum StockType {
		STOCK_TYPE_0("进货", 0), STOCK_TYPE_1("出货", 1);

		public static String getName(int index) {
			for (StockType c : StockType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private StockType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 用户是否非法 **/
	public enum IsClosedUser {
		IS_CLOSED_USER_0("正常", 0), IS_CLOSED_USER_1("非法", 1);

		public static String getName(int index) {
			for (IsClosedUser c : IsClosedUser.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private IsClosedUser(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 是否特价 **/
	public enum IsTejia {
		IS_TEJIA_0("不是特价", 0), IS_TEJIA_1("特价", 1);

		public static String getName(int index) {
			for (IsTejia c : IsTejia.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private IsTejia(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 销售类型 **/
	public enum CommStockType {
		COMM_STOCK_TYPE_17000("正常销售", 17000), COMM_STOCK_TYPE_17001("退货", 17001);

		public static String getName(int index) {
			for (CommStockType c : CommStockType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private CommStockType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	// 设备类型（RFID=0、进门禁=1、出门禁=2）
	public enum DeviceType {
		DEVICE_TYPE_0("RFID", 0), DEVICE_TYPE_1("进门禁", 1), DEVICE_TYPE_2("出门禁", 2);

		public static String getName(int index) {
			for (CommStockType c : CommStockType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private DeviceType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	// 操作类型（结算=0、出门=1、退还标签=2、盘存=3）
	public enum WireType {
		WIRE_TYPE_0("结算", 0), WIRE_TYPE_1("出门", 1), WIRE_TYPE_2("退还标签", 2), WIRE_TYPE_3("盘存", 3);

		public static String getName(int index) {
			for (CommStockType c : CommStockType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private WireType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	// 命令类型（标准模式=0、天线切换=1、标签读=2、标签写=3、心跳=4）
	public enum CommandType {
		COMMAND_TYPE_0("标准模式", 0), COMMAND_TYPE_1("天线切换", 1), COMMAND_TYPE_2("标签读", 2), COMMAND_TYPE_3("标签写", 3), COMMAND_TYPE_4(
				"心跳", 4);

		public static String getName(int index) {
			for (CommStockType c : CommStockType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private CommandType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	// echartstype
	public enum EchartsType {
		EchartsTypeBar("bar", 0), EchartsTypeLine("line", 1);

		public static String getName(int index) {
			for (EchartsType c : EchartsType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private EchartsType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum REBATE_BASE_DATA_ID {
		REBATE_BASE_DATA_ID_1001("本人消费奖励", 1001), REBATE_BASE_DATA_ID_1002("会员直邀奖励", 1002), REBATE_BASE_DATA_ID_1003(
				"驿站合伙人奖励", 1003), REBATE_BASE_DATA_ID_1004("普通县域合伙人奖励", 1004), REBATE_BASE_DATA_ID_1005("股份县域合伙人奖励",
				1005);

		public static String getName(int index) {
			for (REBATE_BASE_DATA_ID c : REBATE_BASE_DATA_ID.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private REBATE_BASE_DATA_ID(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/** 动态类型type **/
	public enum DynamicType {
		dynamic_type_1("发布动态", 1, "dynamic_type_1"), dynamic_type_2("发布商品", 2, "dynamic_type_2");

		public static String getName(int index) {
			for (DynamicType c : DynamicType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private DynamicType(String name, int index, String son_type) {
			this.name = name;
			this.index = index;
			this.son_type = son_type;
		}

		private String name;

		private int index;

		private String son_type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getSonType() {
			return son_type;
		}

		public void setSonType(String son_type) {
			this.son_type = son_type;
		}
	}

	/** 分组 **/
	public enum GroupType {
		GroupType_0("未分组", 0), GroupType_1("家人", 1), GroupType_2("朋友", 2);

		public static String getName(int index) {
			for (GroupType c : GroupType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private GroupType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	public enum DynamicRecordType {
		DynamicRecordType_1("关注", 1), DynamicRecordType_2("购买商品", 2), DynamicRecordType_3("评论", 3), DynamicRecordType_4(
				"回复", 4), DynamicRecordType_5("点赞", 5), DynamicRecordType_6("发布动态", 6), DynamicRecordType_7("发布商品", 7);

		public static String getName(int index) {
			for (DynamicRecordType c : DynamicRecordType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private DynamicRecordType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	public enum CommZyType {
		COMM_ZY_TYPE_1("京东自营", 1), COMM_ZY_TYPE_2("挑夫自营", 2), COMM_ZY_TYPE_3("厂家直销", 3), COMM_ZY_TYPE_4("源头直供", 4);

		public static String getName(int index) {
			for (CommZyType c : CommZyType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private CommZyType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	public enum mod_id {
		mod_id_xian("县头条", 1500501030), mod_id_cun("村头条", 1500301060), mod_id_shi("市头条", 1600100103);

		public static String getName(int index) {
			for (mod_id c : mod_id.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private mod_id(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	public enum return_money_type {
		return_money_type_0("电子币", 0), return_money_type_1("原路退回", 1), return_money_type_2("原路退回+电子币", 2);

		public static String getName(int index) {
			for (return_money_type c : return_money_type.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private return_money_type(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	/** 链接类型 **/
	public enum LinkType {

		LINK_TYPE_10000("编辑楼层", 10000), LINK_TYPE_10010("编辑核心业务", 10010), LINK_TYPE_10020("编辑集团优势", 10020), LINK_TYPE_10030(
				"编辑集团分布", 10030), LINK_TYPE_10040("编辑公司动态", 10040), LINK_TYPE_10050("编辑案例中心楼层", 10050), LINK_TYPE_10060(
				"编辑合作伙伴", 10060), LINK_TYPE_10070("编辑导航栏", 10070), LINK_TYPE_10071("编辑导航栏电话号码", 10071), LINK_TYPE_10080(
				"编辑轮播图", 10080), LINK_TYPE_10090("编辑底部", 10090), LINK_TYPE_10091("编辑copyright", 10091), LINK_TYPE_10100(
				"编辑文字", 10100), LINK_TYPE_10109("编辑案例中心头部", 10109), LINK_TYPE_10110("案例中心-图片在左边", 10110), LINK_TYPE_10120(
				"编辑专业团队", 10120), LINK_TYPE_10130("编辑多元服务", 10130), LINK_TYPE_10140("编辑规模宏大", 10140), LINK_TYPE_10150(
				"编辑集团简介", 10150), LINK_TYPE_10160("编辑集团发展", 10160), LINK_TYPE_10170("编辑集团环境图片", 10170), LINK_TYPE_10180(
				"编辑集团大事件", 10180), LINK_TYPE_10190("编辑旗下分公司图片", 10190), LINK_TYPE_10200("编辑会员体系", 10200);
		;
		public static String getName(int index) {
			for (IsDel c : IsDel.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private LinkType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	// 用户身份类型
	public enum STATUS_TYPE {
		STATUS_TYPE_1("商家", 1), STATUS_TYPE_2("合伙人", 2);

		public static String getName(int index) {
			for (STATUS_TYPE c : STATUS_TYPE.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private STATUS_TYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum CARD_STATE {
		CARD_STATE_X1("作废", -1), CARD_STATE_0("未激活", 0), CARD_STATE_1("已激活", 1), CARD_STATE_2("已用完", 2);

		public static String getName(int index) {
			for (CARD_STATE c : CARD_STATE.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private CARD_STATE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum ActivityPayType {
		ActivityPayType_0("选择商品", 0), ActivityPayType_1("直接支付", 1);

		public static String getName(int index) {
			for (ActivityPayType c : ActivityPayType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		private ActivityPayType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		private String name;

		private int index;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

}
