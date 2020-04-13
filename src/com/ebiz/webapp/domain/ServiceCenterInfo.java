package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @version 2015-12-06 14:47
 */
public class ServiceCenterInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String servicecenter_name;

	private Integer p_index;

	private Integer servicecenter_level;

	private String servicecenter_addr;

	private Integer servicecenter_corporation;

	private String servicecenter_type;

	private BigDecimal servicecenter_reg_money;

	private Date servicecenter_build_date;

	private Long servicecenter_persons;

	private String servicecenter_area;

	private String servicecenter_jy_desc;

	private String servicecenter_linkman;

	private String servicecenter_linkman_tel;

	private String servicecenter_linkman_qq;

	private String servicecenter_linkman_addr;

	private String servicecenter_linkman_jg;

	private String servicecenter_linkman_wixin_nu;

	private String servicecenter_help_linkman;

	private String servicecenter_help_linkman_tel;

	private String id_card;

	private Integer is_commend;

	private Integer is_del;

	private Date add_date;

	private Integer add_user_id;

	private String add_user_name;

	private Date update_date;

	private Integer update_user_id;

	private Date del_date;

	private Integer del_user_id;

	private Integer audit_state;

	private Integer audit_user_id;

	private Date audit_date;

	private String audit_desc;

	private Integer is_closed;

	private Date servicecenter_closed_date;

	private Date project_kc_date;

	private Integer except_end_day;

	private Integer hy_cls_id;

	private String kfsc_content;

	private String jbys_content;

	private String trzy_content;

	private String qdjy_content;

	private Integer pay_success;

	private Date pay_date;

	private Integer effect_state;

	private Date effect_date;

	private Date effect_end_date;

	private Integer pay_type;

	private Integer p_index_pro;

	private String trade_no;

	private String servicecenter_no;

	private Integer wl_order_state;

	private Date wl_fahuo_date;

	private Date wl_qrsh_date;

	private String wl_comm;

	private Integer wl_comp_id;

	private String wl_code;

	private String wl_comp_name;

	private String wl_waybill_no;

	private String position_latlng;

	private Integer is_open_ad;

	private Integer village_count_limit;

	private String banner;

	private String qrcode;

	private String condition_info;

	private String logo;

	private String business_license_path;// 营业执照路径

	private String brought_account;// 对公账户名称

	private String bank_name;// 开户行名称

	private String brought_account_no;// 对公账号

	private String domain_site;// 县域地址

	private Integer is_virtual;

	private String service_qrcode_path;// 小程序二维码

	public String getService_qrcode_path() {
		return service_qrcode_path;
	}

	public void setService_qrcode_path(String service_qrcode_path) {
		this.service_qrcode_path = service_qrcode_path;
	}

	public String getDomain_site() {
		return domain_site;
	}

	public void setDomain_site(String domain_site) {
		this.domain_site = domain_site;
	}

	public String getBrought_account() {
		return brought_account;
	}

	public void setBrought_account(String brought_account) {
		this.brought_account = brought_account;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBrought_account_no() {
		return brought_account_no;
	}

	public void setBrought_account_no(String brought_account_no) {
		this.brought_account_no = brought_account_no;
	}

	public String getCondition_info() {
		return condition_info;
	}

	public void setCondition_info(String condition_info) {
		this.condition_info = condition_info;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public ServiceCenterInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServicecenter_name() {
		return servicecenter_name;
	}

	public void setServicecenter_name(String servicecenter_name) {
		this.servicecenter_name = servicecenter_name;
	}

	public Integer getP_index() {
		return p_index;
	}

	public void setP_index(Integer p_index) {
		this.p_index = p_index;
	}

	public Integer getServicecenter_level() {
		return servicecenter_level;
	}

	public void setServicecenter_level(Integer servicecenter_level) {
		this.servicecenter_level = servicecenter_level;
	}

	public String getServicecenter_addr() {
		return servicecenter_addr;
	}

	public void setServicecenter_addr(String servicecenter_addr) {
		this.servicecenter_addr = servicecenter_addr;
	}

	public Integer getServicecenter_corporation() {
		return servicecenter_corporation;
	}

	public void setServicecenter_corporation(Integer servicecenter_corporation) {
		this.servicecenter_corporation = servicecenter_corporation;
	}

	public String getServicecenter_type() {
		return servicecenter_type;
	}

	public void setServicecenter_type(String servicecenter_type) {
		this.servicecenter_type = servicecenter_type;
	}

	public BigDecimal getServicecenter_reg_money() {
		return servicecenter_reg_money;
	}

	public void setServicecenter_reg_money(BigDecimal servicecenter_reg_money) {
		this.servicecenter_reg_money = servicecenter_reg_money;
	}

	public Date getServicecenter_build_date() {
		return servicecenter_build_date;
	}

	public void setServicecenter_build_date(Date servicecenter_build_date) {
		this.servicecenter_build_date = servicecenter_build_date;
	}

	public Long getServicecenter_persons() {
		return servicecenter_persons;
	}

	public void setServicecenter_persons(Long servicecenter_persons) {
		this.servicecenter_persons = servicecenter_persons;
	}

	public String getServicecenter_area() {
		return servicecenter_area;
	}

	public void setServicecenter_area(String servicecenter_area) {
		this.servicecenter_area = servicecenter_area;
	}

	public String getServicecenter_jy_desc() {
		return servicecenter_jy_desc;
	}

	public void setServicecenter_jy_desc(String servicecenter_jy_desc) {
		this.servicecenter_jy_desc = servicecenter_jy_desc;
	}

	public String getServicecenter_linkman() {
		return servicecenter_linkman;
	}

	public void setServicecenter_linkman(String servicecenter_linkman) {
		this.servicecenter_linkman = servicecenter_linkman;
	}

	public String getServicecenter_linkman_tel() {
		return servicecenter_linkman_tel;
	}

	public void setServicecenter_linkman_tel(String servicecenter_linkman_tel) {
		this.servicecenter_linkman_tel = servicecenter_linkman_tel;
	}

	public String getServicecenter_linkman_qq() {
		return servicecenter_linkman_qq;
	}

	public void setServicecenter_linkman_qq(String servicecenter_linkman_qq) {
		this.servicecenter_linkman_qq = servicecenter_linkman_qq;
	}

	public String getServicecenter_linkman_addr() {
		return servicecenter_linkman_addr;
	}

	public void setServicecenter_linkman_addr(String servicecenter_linkman_addr) {
		this.servicecenter_linkman_addr = servicecenter_linkman_addr;
	}

	public String getServicecenter_linkman_jg() {
		return servicecenter_linkman_jg;
	}

	public void setServicecenter_linkman_jg(String servicecenter_linkman_jg) {
		this.servicecenter_linkman_jg = servicecenter_linkman_jg;
	}

	public String getServicecenter_linkman_wixin_nu() {
		return servicecenter_linkman_wixin_nu;
	}

	public void setServicecenter_linkman_wixin_nu(String servicecenter_linkman_wixin_nu) {
		this.servicecenter_linkman_wixin_nu = servicecenter_linkman_wixin_nu;
	}

	public String getServicecenter_help_linkman() {
		return servicecenter_help_linkman;
	}

	public void setServicecenter_help_linkman(String servicecenter_help_linkman) {
		this.servicecenter_help_linkman = servicecenter_help_linkman;
	}

	public String getServicecenter_help_linkman_tel() {
		return servicecenter_help_linkman_tel;
	}

	public void setServicecenter_help_linkman_tel(String servicecenter_help_linkman_tel) {
		this.servicecenter_help_linkman_tel = servicecenter_help_linkman_tel;
	}

	public Integer getIs_commend() {
		return is_commend;
	}

	public void setIs_commend(Integer is_commend) {
		this.is_commend = is_commend;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getAdd_user_id() {
		return add_user_id;
	}

	public void setAdd_user_id(Integer add_user_id) {
		this.add_user_id = add_user_id;
	}

	public String getAdd_user_name() {
		return add_user_name;
	}

	public void setAdd_user_name(String add_user_name) {
		this.add_user_name = add_user_name;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Integer getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}

	public Date getDel_date() {
		return del_date;
	}

	public void setDel_date(Date del_date) {
		this.del_date = del_date;
	}

	public Integer getDel_user_id() {
		return del_user_id;
	}

	public void setDel_user_id(Integer del_user_id) {
		this.del_user_id = del_user_id;
	}

	public Integer getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}

	public Integer getAudit_user_id() {
		return audit_user_id;
	}

	public void setAudit_user_id(Integer audit_user_id) {
		this.audit_user_id = audit_user_id;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public String getAudit_desc() {
		return audit_desc;
	}

	public void setAudit_desc(String audit_desc) {
		this.audit_desc = audit_desc;
	}

	public Integer getIs_closed() {
		return is_closed;
	}

	public void setIs_closed(Integer is_closed) {
		this.is_closed = is_closed;
	}

	public Date getServicecenter_closed_date() {
		return servicecenter_closed_date;
	}

	public void setServicecenter_closed_date(Date servicecenter_closed_date) {
		this.servicecenter_closed_date = servicecenter_closed_date;
	}

	public Date getProject_kc_date() {
		return project_kc_date;
	}

	public void setProject_kc_date(Date project_kc_date) {
		this.project_kc_date = project_kc_date;
	}

	public Integer getExcept_end_day() {
		return except_end_day;
	}

	public void setExcept_end_day(Integer except_end_day) {
		this.except_end_day = except_end_day;
	}

	public Integer getHy_cls_id() {
		return hy_cls_id;
	}

	public void setHy_cls_id(Integer hy_cls_id) {
		this.hy_cls_id = hy_cls_id;
	}

	public String getKfsc_content() {
		return kfsc_content;
	}

	public void setKfsc_content(String kfsc_content) {
		this.kfsc_content = kfsc_content;
	}

	public String getJbys_content() {
		return jbys_content;
	}

	public void setJbys_content(String jbys_content) {
		this.jbys_content = jbys_content;
	}

	public String getTrzy_content() {
		return trzy_content;
	}

	public void setTrzy_content(String trzy_content) {
		this.trzy_content = trzy_content;
	}

	public String getQdjy_content() {
		return qdjy_content;
	}

	public void setQdjy_content(String qdjy_content) {
		this.qdjy_content = qdjy_content;
	}

	public Integer getPay_success() {
		return pay_success;
	}

	public void setPay_success(Integer pay_success) {
		this.pay_success = pay_success;
	}

	public Date getPay_date() {
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public Integer getEffect_state() {
		return effect_state;
	}

	public void setEffect_state(Integer effect_state) {
		this.effect_state = effect_state;
	}

	public Date getEffect_date() {
		return effect_date;
	}

	public void setEffect_date(Date effect_date) {
		this.effect_date = effect_date;
	}

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public Integer getP_index_pro() {
		return p_index_pro;
	}

	public void setP_index_pro(Integer p_index_pro) {
		this.p_index_pro = p_index_pro;
	}

	public Date getEffect_end_date() {
		return effect_end_date;
	}

	public void setEffect_end_date(Date effect_end_date) {
		this.effect_end_date = effect_end_date;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getServicecenter_no() {
		return servicecenter_no;
	}

	public void setServicecenter_no(String servicecenter_no) {
		this.servicecenter_no = servicecenter_no;
	}

	public Integer getWl_order_state() {
		return wl_order_state;
	}

	public void setWl_order_state(Integer wl_order_state) {
		this.wl_order_state = wl_order_state;
	}

	public Date getWl_fahuo_date() {
		return wl_fahuo_date;
	}

	public void setWl_fahuo_date(Date wl_fahuo_date) {
		this.wl_fahuo_date = wl_fahuo_date;
	}

	public Date getWl_qrsh_date() {
		return wl_qrsh_date;
	}

	public void setWl_qrsh_date(Date wl_qrsh_date) {
		this.wl_qrsh_date = wl_qrsh_date;
	}

	public String getWl_comm() {
		return wl_comm;
	}

	public void setWl_comm(String wl_comm) {
		this.wl_comm = wl_comm;
	}

	public Integer getWl_comp_id() {
		return wl_comp_id;
	}

	public void setWl_comp_id(Integer wl_comp_id) {
		this.wl_comp_id = wl_comp_id;
	}

	public String getWl_code() {
		return wl_code;
	}

	public void setWl_code(String wl_code) {
		this.wl_code = wl_code;
	}

	public String getWl_comp_name() {
		return wl_comp_name;
	}

	public void setWl_comp_name(String wl_comp_name) {
		this.wl_comp_name = wl_comp_name;
	}

	public String getWl_waybill_no() {
		return wl_waybill_no;
	}

	public void setWl_waybill_no(String wl_waybill_no) {
		this.wl_waybill_no = wl_waybill_no;
	}

	public String getPosition_latlng() {
		return position_latlng;
	}

	public void setPosition_latlng(String position_latlng) {
		this.position_latlng = position_latlng;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public Integer getIs_open_ad() {
		return is_open_ad;
	}

	public void setIs_open_ad(Integer is_open_ad) {
		this.is_open_ad = is_open_ad;
	}

	public Integer getVillage_count_limit() {
		return village_count_limit;
	}

	public void setVillage_count_limit(Integer village_count_limit) {
		this.village_count_limit = village_count_limit;
	}

	public String getBusiness_license_path() {
		return business_license_path;
	}

	public void setBusiness_license_path(String business_license_path) {
		this.business_license_path = business_license_path;
	}

	public Integer getIs_virtual() {
		return is_virtual;
	}

	public void setIs_virtual(Integer is_virtual) {
		this.is_virtual = is_virtual;
	}

}