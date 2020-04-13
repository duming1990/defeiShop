package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
public class EntpInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Date add_date;

	private Integer add_user_id;

	private String add_user_name;

	private String alipay_account;

	private Date audit_date;

	private String audit_desc_one;

	private String audit_desc_two;

	private Integer audit_state;

	private Integer audit_user_id;

	private String bank_account;

	List<CartInfo> cartInfoList = new ArrayList<CartInfo>();

	private String custom_url;

	private Date del_date;

	private Integer del_user_id;

	private String entp_addr;

	private BigDecimal entp_area;

	private Date entp_build_date;

	private String entp_corporation;

	private String entp_desc;

	private String entp_email;

	private String entp_ename;

	private String entp_fax;

	private String entp_honor;

	private String entp_kind;

	private String entp_latlng;

	private String entp_level;

	private String entp_licence;

	private String entp_licence_img;

	private String entp_linkman;

	private String entp_logo;

	private String entp_name;

	private String entp_org_code;

	private String entp_org_code_img;

	private String entp_own_org;

	private Integer entp_persons;

	private String entp_postcode;

	private BigDecimal entp_reg_money;

	private String entp_sname;

	private String entp_tax_licence;

	private String entp_tax_licence_img;

	private String entp_tel;

	private Integer entp_template;

	private Integer entp_type;

	private String entp_www;

	private Integer id;

	private Integer is_commend;

	private Integer is_del;

	private String main_pd_class_ids;

	private String main_pd_class_names;

	private String xianxia_pd_class_ids;

	private String xianxia_pd_class_names;

	private Integer order_value;

	private String own_bank;

	private Integer p_index;

	private String qq;

	private Date update_date;

	private Integer update_user_id;

	private String uuid;

	private Integer own_sys;

	private Integer store_type;

	private Integer is_self_support; // 自营

	private String seo_title;

	private String seo_keyword;

	private String seo_desc;

	private String shop_name;

	private Integer is_zc_hdfk;

	private String hdfk_pindexs;

	private String customer_code;

	private String yy_sj_between;

	private Integer fanxian_rule;

	private Integer fanxian_rule_new;

	private Date fanxian_rule_new_date;

	private Integer is_has_yinye_no;

	private String id_card_no;

	private String img_id_card_zm;

	private String img_id_card_fm;

	private String img_entp_mentou;

	private String entp_service;

	private Date entp_refresh_date;

	private Integer is_closed;

	private Date entp_closed_date;

	private Integer is_show;

	private Integer p_index_pro;

	private Integer hy_cls_id;

	private Integer comment_count; // 评论数

	private BigDecimal xiaofei_qibu_price; // 消费起步价格

	private BigDecimal renjun_xiaofei_price; // 人均消费价格

	private String tax_reg_certificate;// 税务登记证

	private String org_code;// 组织机构代码

	private String production_license;// 生产许可证

	private String food_liutong_texu_license;// 食品流通特许经营证

	private String proxy_author_certificate;// 代理授权证明

	private String brand_reg_license;// 商标注册证

	private Integer is_buy_dalibao;

	private Integer is_lianmeng;

	private String entp_no;

	private Integer view_count;// 浏览量

	private Integer sale_count;// 销售量

	private Integer is_nx_entp;

	private BigDecimal sum_sale_money;

	private Integer xianxia_cls_id;

	private Integer is_has_open_online_shop;

	private Integer is_open_ad;

	private Integer link_service_center_id;

	private String socket_ip;

	private Integer socket_port;

	private Integer is_entpstyle;

	private Integer is_coupons;

	private String kefu_qr_code;// 客服二维码

	private String kefu_tel;// 客服电话

	private Integer mark_entp_id;// 无人超市店铺id

	public Integer getMark_entp_id() {
		return mark_entp_id;
	}

	public void setMark_entp_id(Integer mark_entp_id) {
		this.mark_entp_id = mark_entp_id;
	}

	public String getKefu_qr_code() {
		return kefu_qr_code;
	}

	public void setKefu_qr_code(String kefu_qr_code) {
		this.kefu_qr_code = kefu_qr_code;
	}

	public String getKefu_tel() {
		return kefu_tel;
	}

	public void setKefu_tel(String kefu_tel) {
		this.kefu_tel = kefu_tel;
	}

	public Integer getIs_coupons() {
		return is_coupons;
	}

	public void setIs_coupons(Integer is_coupons) {
		this.is_coupons = is_coupons;
	}

	public Integer getIs_entpstyle() {
		return is_entpstyle;
	}

	public void setIs_entpstyle(Integer is_entpstyle) {
		this.is_entpstyle = is_entpstyle;
	}

	public Integer getXianxia_cls_id() {
		return xianxia_cls_id;
	}

	public void setXianxia_cls_id(Integer xianxia_cls_id) {
		this.xianxia_cls_id = xianxia_cls_id;
	}

	public String getXianxia_pd_class_ids() {
		return xianxia_pd_class_ids;
	}

	public void setXianxia_pd_class_ids(String xianxia_pd_class_ids) {
		this.xianxia_pd_class_ids = xianxia_pd_class_ids;
	}

	public String getXianxia_pd_class_names() {
		return xianxia_pd_class_names;
	}

	public void setXianxia_pd_class_names(String xianxia_pd_class_names) {
		this.xianxia_pd_class_names = xianxia_pd_class_names;
	}

	public Integer getIs_self_support() {
		return is_self_support;
	}

	public void setIs_self_support(Integer isSelfSupport) {
		is_self_support = isSelfSupport;
	}

	public EntpInfo() {

	}

	public Date getAdd_date() {
		return add_date;
	}

	public Integer getAdd_user_id() {
		return add_user_id;
	}

	public String getAdd_user_name() {
		return add_user_name;
	}

	public String getAlipay_account() {
		return alipay_account;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public Integer getAudit_state() {
		return audit_state;
	}

	public Integer getAudit_user_id() {
		return audit_user_id;
	}

	public String getBank_account() {
		return bank_account;
	}

	public List<CartInfo> getCartInfoList() {
		return cartInfoList;
	}

	public String getCustom_url() {
		return custom_url;
	}

	public Date getDel_date() {
		return del_date;
	}

	public Integer getDel_user_id() {
		return del_user_id;
	}

	public String getEntp_addr() {
		return entp_addr;
	}

	public BigDecimal getEntp_area() {
		return entp_area;
	}

	public Date getEntp_build_date() {
		return entp_build_date;
	}

	public String getEntp_corporation() {
		return entp_corporation;
	}

	public String getEntp_desc() {
		return entp_desc;
	}

	public String getEntp_email() {
		return entp_email;
	}

	public String getEntp_ename() {
		return entp_ename;
	}

	public String getEntp_fax() {
		return entp_fax;
	}

	public String getEntp_honor() {
		return entp_honor;
	}

	public String getEntp_kind() {
		return entp_kind;
	}

	public String getEntp_latlng() {
		return entp_latlng;
	}

	public String getEntp_level() {
		return entp_level;
	}

	public String getEntp_licence() {
		return entp_licence;
	}

	public String getEntp_licence_img() {
		return entp_licence_img;
	}

	public String getEntp_linkman() {
		return entp_linkman;
	}

	public String getEntp_logo() {
		return entp_logo;
	}

	public String getEntp_name() {
		return entp_name;
	}

	public String getEntp_org_code() {
		return entp_org_code;
	}

	public String getEntp_org_code_img() {
		return entp_org_code_img;
	}

	public String getEntp_own_org() {
		return entp_own_org;
	}

	public Integer getEntp_persons() {
		return entp_persons;
	}

	public String getEntp_postcode() {
		return entp_postcode;
	}

	public BigDecimal getEntp_reg_money() {
		return entp_reg_money;
	}

	public String getEntp_sname() {
		return entp_sname;
	}

	public String getEntp_tax_licence() {
		return entp_tax_licence;
	}

	public String getEntp_tax_licence_img() {
		return entp_tax_licence_img;
	}

	public String getEntp_tel() {
		return entp_tel;
	}

	public Integer getEntp_template() {
		return entp_template;
	}

	public Integer getEntp_type() {
		return entp_type;
	}

	public String getEntp_www() {
		return entp_www;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIs_commend() {
		return is_commend;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public String getMain_pd_class_ids() {
		return main_pd_class_ids;
	}

	public String getMain_pd_class_names() {
		return main_pd_class_names;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public String getOwn_bank() {
		return own_bank;
	}

	public Integer getP_index() {
		return p_index;
	}

	public String getQq() {
		return qq;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public Integer getUpdate_user_id() {
		return update_user_id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public void setAdd_user_id(Integer add_user_id) {
		this.add_user_id = add_user_id;
	}

	public void setAdd_user_name(String add_user_name) {
		this.add_user_name = add_user_name;
	}

	public void setAlipay_account(String alipayAccount) {
		alipay_account = alipayAccount;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}

	public void setAudit_user_id(Integer audit_user_id) {
		this.audit_user_id = audit_user_id;
	}

	public void setBank_account(String bankAccount) {
		bank_account = bankAccount;
	}

	public void setCartInfoList(List<CartInfo> cartInfoList) {
		this.cartInfoList = cartInfoList;
	}

	public void setCustom_url(String customUrl) {
		custom_url = customUrl;
	}

	public void setDel_date(Date del_date) {
		this.del_date = del_date;
	}

	public void setDel_user_id(Integer del_user_id) {
		this.del_user_id = del_user_id;
	}

	public void setEntp_addr(String entp_addr) {
		this.entp_addr = entp_addr;
	}

	public void setEntp_area(BigDecimal entp_area) {
		this.entp_area = entp_area;
	}

	public void setEntp_build_date(Date entp_build_date) {
		this.entp_build_date = entp_build_date;
	}

	public void setEntp_corporation(String entp_corporation) {
		this.entp_corporation = entp_corporation;
	}

	public void setEntp_desc(String entp_desc) {
		this.entp_desc = entp_desc;
	}

	public void setEntp_email(String entp_email) {
		this.entp_email = entp_email;
	}

	public void setEntp_ename(String entp_ename) {
		this.entp_ename = entp_ename;
	}

	public void setEntp_fax(String entp_fax) {
		this.entp_fax = entp_fax;
	}

	public void setEntp_honor(String entp_honor) {
		this.entp_honor = entp_honor;
	}

	public void setEntp_kind(String entp_kind) {
		this.entp_kind = entp_kind;
	}

	public void setEntp_latlng(String entp_latlng) {
		this.entp_latlng = entp_latlng;
	}

	public void setEntp_level(String entpLevel) {
		entp_level = entpLevel;
	}

	public void setEntp_licence(String entp_licence) {
		this.entp_licence = entp_licence;
	}

	public void setEntp_licence_img(String entp_licence_img) {
		this.entp_licence_img = entp_licence_img;
	}

	public void setEntp_linkman(String entp_linkman) {
		this.entp_linkman = entp_linkman;
	}

	public void setEntp_logo(String entp_logo) {
		this.entp_logo = entp_logo;
	}

	public void setEntp_name(String entp_name) {
		this.entp_name = entp_name;
	}

	public void setEntp_org_code(String entp_org_code) {
		this.entp_org_code = entp_org_code;
	}

	public void setEntp_org_code_img(String entp_org_code_img) {
		this.entp_org_code_img = entp_org_code_img;
	}

	public void setEntp_own_org(String entp_own_org) {
		this.entp_own_org = entp_own_org;
	}

	public void setEntp_persons(Integer entp_persons) {
		this.entp_persons = entp_persons;
	}

	public void setEntp_postcode(String entp_postcode) {
		this.entp_postcode = entp_postcode;
	}

	public void setEntp_reg_money(BigDecimal entp_reg_money) {
		this.entp_reg_money = entp_reg_money;
	}

	public void setEntp_sname(String entp_sname) {
		this.entp_sname = entp_sname;
	}

	public void setEntp_tax_licence(String entp_tax_licence) {
		this.entp_tax_licence = entp_tax_licence;
	}

	public void setEntp_tax_licence_img(String entp_tax_licence_img) {
		this.entp_tax_licence_img = entp_tax_licence_img;
	}

	public void setEntp_tel(String entp_tel) {
		this.entp_tel = entp_tel;
	}

	public void setEntp_template(Integer entpTemplate) {
		entp_template = entpTemplate;
	}

	public void setEntp_type(Integer entp_type) {
		this.entp_type = entp_type;
	}

	public void setEntp_www(String entp_www) {
		this.entp_www = entp_www;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIs_commend(Integer is_commend) {
		this.is_commend = is_commend;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public void setMain_pd_class_ids(String main_pd_class_ids) {
		this.main_pd_class_ids = main_pd_class_ids;
	}

	public void setMain_pd_class_names(String main_pd_class_names) {
		this.main_pd_class_names = main_pd_class_names;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public void setOwn_bank(String ownBank) {
		own_bank = ownBank;
	}

	public void setP_index(Integer p_index) {
		this.p_index = p_index;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getOwn_sys() {
		return own_sys;
	}

	public void setOwn_sys(Integer own_sys) {
		this.own_sys = own_sys;
	}

	public void setStore_type(Integer store_type) {
		this.store_type = store_type;
	}

	public Integer getStore_type() {
		return store_type;
	}

	public String getSeo_title() {
		return seo_title;
	}

	public void setSeo_title(String seo_title) {
		this.seo_title = seo_title;
	}

	public String getSeo_keyword() {
		return seo_keyword;
	}

	public void setSeo_keyword(String seo_keyword) {
		this.seo_keyword = seo_keyword;
	}

	public String getSeo_desc() {
		return seo_desc;
	}

	public void setSeo_desc(String seo_desc) {
		this.seo_desc = seo_desc;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public Integer getIs_zc_hdfk() {
		return is_zc_hdfk;
	}

	public void setIs_zc_hdfk(Integer is_zc_hdfk) {
		this.is_zc_hdfk = is_zc_hdfk;
	}

	public String getHdfk_pindexs() {
		return hdfk_pindexs;
	}

	public void setHdfk_pindexs(String hdfk_pindexs) {
		this.hdfk_pindexs = hdfk_pindexs;
	}

	public String getCustomer_code() {
		return customer_code;
	}

	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}

	public String getYy_sj_between() {
		return yy_sj_between;
	}

	public void setYy_sj_between(String yy_sj_between) {
		this.yy_sj_between = yy_sj_between;
	}

	public Integer getFanxian_rule() {
		return fanxian_rule;
	}

	public void setFanxian_rule(Integer fanxian_rule) {
		this.fanxian_rule = fanxian_rule;
	}

	public Integer getIs_has_yinye_no() {
		return is_has_yinye_no;
	}

	public void setIs_has_yinye_no(Integer is_has_yinye_no) {
		this.is_has_yinye_no = is_has_yinye_no;
	}

	public String getId_card_no() {
		return id_card_no;
	}

	public void setId_card_no(String id_card_no) {
		this.id_card_no = id_card_no;
	}

	public String getImg_id_card_zm() {
		return img_id_card_zm;
	}

	public void setImg_id_card_zm(String img_id_card_zm) {
		this.img_id_card_zm = img_id_card_zm;
	}

	public String getImg_id_card_fm() {
		return img_id_card_fm;
	}

	public void setImg_id_card_fm(String img_id_card_fm) {
		this.img_id_card_fm = img_id_card_fm;
	}

	public String getImg_entp_mentou() {
		return img_entp_mentou;
	}

	public void setImg_entp_mentou(String img_entp_mentou) {
		this.img_entp_mentou = img_entp_mentou;
	}

	public String getEntp_service() {
		return entp_service;
	}

	public void setEntp_service(String entp_service) {
		this.entp_service = entp_service;
	}

	public Date getEntp_refresh_date() {
		return entp_refresh_date;
	}

	public void setEntp_refresh_date(Date entp_refresh_date) {
		this.entp_refresh_date = entp_refresh_date;
	}

	public Integer getIs_closed() {
		return is_closed;
	}

	public void setIs_closed(Integer is_closed) {
		this.is_closed = is_closed;
	}

	public Date getEntp_closed_date() {
		return entp_closed_date;
	}

	public void setEntp_closed_date(Date entp_closed_date) {
		this.entp_closed_date = entp_closed_date;
	}

	public Integer getIs_show() {
		return is_show;
	}

	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}

	public Integer getFanxian_rule_new() {
		return fanxian_rule_new;
	}

	public Date getFanxian_rule_new_date() {
		return fanxian_rule_new_date;
	}

	public void setFanxian_rule_new(Integer fanxian_rule_new) {
		this.fanxian_rule_new = fanxian_rule_new;
	}

	public void setFanxian_rule_new_date(Date fanxian_rule_new_date) {
		this.fanxian_rule_new_date = fanxian_rule_new_date;
	}

	public String getAudit_desc_one() {
		return audit_desc_one;
	}

	public void setAudit_desc_one(String audit_desc_one) {
		this.audit_desc_one = audit_desc_one;
	}

	public String getAudit_desc_two() {
		return audit_desc_two;
	}

	public void setAudit_desc_two(String audit_desc_two) {
		this.audit_desc_two = audit_desc_two;
	}

	public Integer getP_index_pro() {
		return p_index_pro;
	}

	public void setP_index_pro(Integer p_index_pro) {
		this.p_index_pro = p_index_pro;
	}

	public Integer getHy_cls_id() {
		return hy_cls_id;
	}

	public void setHy_cls_id(Integer hy_cls_id) {
		this.hy_cls_id = hy_cls_id;
	}

	public Integer getComment_count() {
		return comment_count;
	}

	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}

	public BigDecimal getXiaofei_qibu_price() {
		return xiaofei_qibu_price;
	}

	public void setXiaofei_qibu_price(BigDecimal xiaofei_qibu_price) {
		this.xiaofei_qibu_price = xiaofei_qibu_price;
	}

	public BigDecimal getRenjun_xiaofei_price() {
		return renjun_xiaofei_price;
	}

	public void setRenjun_xiaofei_price(BigDecimal renjun_xiaofei_price) {
		this.renjun_xiaofei_price = renjun_xiaofei_price;
	}

	public String getTax_reg_certificate() {
		return tax_reg_certificate;
	}

	public void setTax_reg_certificate(String tax_reg_certificate) {
		this.tax_reg_certificate = tax_reg_certificate;
	}

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

	public String getProduction_license() {
		return production_license;
	}

	public void setProduction_license(String production_license) {
		this.production_license = production_license;
	}

	public String getFood_liutong_texu_license() {
		return food_liutong_texu_license;
	}

	public void setFood_liutong_texu_license(String food_liutong_texu_license) {
		this.food_liutong_texu_license = food_liutong_texu_license;
	}

	public String getProxy_author_certificate() {
		return proxy_author_certificate;
	}

	public void setProxy_author_certificate(String proxy_author_certificate) {
		this.proxy_author_certificate = proxy_author_certificate;
	}

	public String getBrand_reg_license() {
		return brand_reg_license;
	}

	public void setBrand_reg_license(String brand_reg_license) {
		this.brand_reg_license = brand_reg_license;
	}

	public Integer getIs_buy_dalibao() {
		return is_buy_dalibao;
	}

	public void setIs_buy_dalibao(Integer is_buy_dalibao) {
		this.is_buy_dalibao = is_buy_dalibao;
	}

	public Integer getIs_lianmeng() {
		return is_lianmeng;
	}

	public void setIs_lianmeng(Integer is_lianmeng) {
		this.is_lianmeng = is_lianmeng;
	}

	public String getEntp_no() {
		return entp_no;
	}

	public void setEntp_no(String entp_no) {
		this.entp_no = entp_no;
	}

	public Integer getView_count() {
		return view_count;
	}

	public void setView_count(Integer view_count) {
		this.view_count = view_count;
	}

	public Integer getSale_count() {
		return sale_count;
	}

	public void setSale_count(Integer sale_count) {
		this.sale_count = sale_count;
	}

	public Integer getIs_nx_entp() {
		return is_nx_entp;
	}

	public void setIs_nx_entp(Integer is_nx_entp) {
		this.is_nx_entp = is_nx_entp;
	}

	public BigDecimal getSum_sale_money() {
		return sum_sale_money;
	}

	public void setSum_sale_money(BigDecimal sum_sale_money) {
		this.sum_sale_money = sum_sale_money;
	}

	public Integer getIs_has_open_online_shop() {
		return is_has_open_online_shop;
	}

	public void setIs_has_open_online_shop(Integer is_has_open_online_shop) {
		this.is_has_open_online_shop = is_has_open_online_shop;
	}

	public Integer getIs_open_ad() {
		return is_open_ad;
	}

	public void setIs_open_ad(Integer is_open_ad) {
		this.is_open_ad = is_open_ad;
	}

	public Integer getLink_service_center_id() {
		return link_service_center_id;
	}

	public void setLink_service_center_id(Integer link_service_center_id) {
		this.link_service_center_id = link_service_center_id;
	}

	public String getSocket_ip() {
		return socket_ip;
	}

	public void setSocket_ip(String socket_ip) {
		this.socket_ip = socket_ip;
	}

	public Integer getSocket_port() {
		return socket_port;
	}

	public void setSocket_port(Integer socket_port) {
		this.socket_port = socket_port;
	}

}