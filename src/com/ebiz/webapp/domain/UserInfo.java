package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:38
 */
public class UserInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String user_name;

	private String real_name;

	private Integer user_type;

	private String password;

	private Integer sex;

	private Date birthday;

	private String positions;

	private String mobile;

	private String office_tel;

	private String email;

	private Integer login_count;

	private String last_login_ip;

	private Date last_login_time;

	private Integer order_value;

	private Integer is_lock;

	private Integer is_del;

	private Integer is_start;

	private Date add_date;

	private Integer add_user_id;

	private Date update_date;

	private Integer update_user_id;

	private Date del_date;

	private Integer del_user_id;

	private Integer is_audit;

	private Integer is_active;

	private Integer own_entp_id;

	private String own_entp_name;

	private Integer is_activate;

	private String sign;

	private String id_card;

	private Integer user_level;

	private Integer key_is_active;

	private BigDecimal cur_score;

	private Integer bank_pindex;

	private String bank_pname;

	private String bank_branch_name;

	private String bank_name;

	private String bank_account;

	private String bank_account_name;

	private String user_logo;

	private String appid_weixin;

	private String appid_weixin_unionid;

	private Integer appid_weixin_is_follow;

	private String appid_qq;

	private String appid_weibo;

	private Integer p_index;

	private String user_no;

	private Integer is_entp;

	private Integer is_fuwu;

	private Integer is_lianmeng;

	private Date is_lianmeng_date;

	private Date is_lianmeng_canceled_date;

	private Integer is_daqu;

	private BigDecimal bi_dianzi;

	private BigDecimal bi_fuxiao;

	private BigDecimal bi_dianzi_lock;

	private BigDecimal bi_dianzi_lost;

	private BigDecimal bi_xiaofei;

	private BigDecimal bi_huokuan;

	private BigDecimal leiji_money_user;

	private BigDecimal leiji_money_entp;

	private BigDecimal leiji_money_tuiguang;

	private String ymid;

	private BigDecimal user_score_union;

	private String password_pay;

	private Integer is_set_security;

	private BigDecimal bi_dianzi_lock_tuiguan;

	private Date score_update_date;

	private Integer is_closed;

	private String device_token;

	private String device_token_app;

	private Integer is_renzheng;

	private Integer is_city_manager;

	private Integer is_has_update_pass;

	private Integer link_entp_id;

	private String img_id_card_zm;

	private String img_id_card_fm;

	private Integer is_village;

	private Integer is_poor;

	private Integer own_village_id;

	private String own_village_name;

	private Integer poor_id;

	private BigDecimal bi_huokuan_lock;

	private BigDecimal bi_aid;

	private BigDecimal bi_aid_sended;

	private BigDecimal bi_aid_lock;

	private Date card_end_date;

	private String autograph;

	private String app_key;

	private Integer is_shuadan;

	private String link_area;

	private String domain_site;

	private Integer mark_user_id;

	private BigDecimal bi_welfare;

	public Integer getMark_user_id() {
		return mark_user_id;
	}

	public void setMark_user_id(Integer mark_user_id) {
		this.mark_user_id = mark_user_id;
	}

	public String getLink_area() {
		return link_area;
	}

	public String getDomain_site() {
		return domain_site;
	}

	public void setDomain_site(String domain_site) {
		this.domain_site = domain_site;
	}

	public void setLink_area(String link_area) {
		this.link_area = link_area;
	}

	public Integer getIs_shuadan() {
		return is_shuadan;
	}

	public void setIs_shuadan(Integer is_shuadan) {
		this.is_shuadan = is_shuadan;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public UserInfo() {

	}

	public UserInfo(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser_name() {

		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOffice_tel() {
		return office_tel;
	}

	public void setOffice_tel(String office_tel) {
		this.office_tel = office_tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getLogin_count() {
		return login_count;
	}

	public void setLogin_count(Integer login_count) {
		this.login_count = login_count;
	}

	public String getLast_login_ip() {
		return last_login_ip;
	}

	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}

	public Date getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Integer getIs_lock() {
		return is_lock;
	}

	public void setIs_lock(Integer is_lock) {
		this.is_lock = is_lock;
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

	public Integer getIs_audit() {
		return is_audit;
	}

	public void setIs_audit(Integer is_audit) {
		this.is_audit = is_audit;
	}

	public Integer getIs_active() {
		return is_active;
	}

	public void setIs_active(Integer is_active) {
		this.is_active = is_active;
	}

	public Integer getOwn_entp_id() {
		return own_entp_id;
	}

	public void setOwn_entp_id(Integer own_entp_id) {
		this.own_entp_id = own_entp_id;
	}

	public String getOwn_entp_name() {
		return own_entp_name;
	}

	public void setOwn_entp_name(String own_entp_name) {
		this.own_entp_name = own_entp_name;
	}

	public Integer getIs_activate() {
		return is_activate;
	}

	public void setIs_activate(Integer is_activate) {
		this.is_activate = is_activate;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public Integer getUser_level() {
		return user_level;
	}

	public void setUser_level(Integer user_level) {
		this.user_level = user_level;
	}

	public Integer getKey_is_active() {
		return key_is_active;
	}

	public void setKey_is_active(Integer key_is_active) {
		this.key_is_active = key_is_active;
	}

	public BigDecimal getCur_score() {
		return cur_score;
	}

	public void setCur_score(BigDecimal cur_score) {
		this.cur_score = cur_score;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_account() {
		return bank_account;
	}

	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}

	public String getBank_account_name() {
		return bank_account_name;
	}

	public void setBank_account_name(String bank_account_name) {
		this.bank_account_name = bank_account_name;
	}

	public String getUser_logo() {
		return user_logo;
	}

	public void setUser_logo(String user_logo) {
		this.user_logo = user_logo;
	}

	public String getAppid_weixin() {
		return appid_weixin;
	}

	public void setAppid_weixin(String appid_weixin) {
		this.appid_weixin = appid_weixin;
	}

	public String getAppid_weixin_unionid() {
		return appid_weixin_unionid;
	}

	public void setAppid_weixin_unionid(String appid_weixin_unionid) {
		this.appid_weixin_unionid = appid_weixin_unionid;
	}

	public Integer getAppid_weixin_is_follow() {
		return appid_weixin_is_follow;
	}

	public void setAppid_weixin_is_follow(Integer appid_weixin_is_follow) {
		this.appid_weixin_is_follow = appid_weixin_is_follow;
	}

	public String getAppid_qq() {
		return appid_qq;
	}

	public void setAppid_qq(String appid_qq) {
		this.appid_qq = appid_qq;
	}

	public String getAppid_weibo() {
		return appid_weibo;
	}

	public void setAppid_weibo(String appid_weibo) {
		this.appid_weibo = appid_weibo;
	}

	public Integer getP_index() {
		return p_index;
	}

	public void setP_index(Integer p_index) {
		this.p_index = p_index;
	}

	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	public Integer getIs_entp() {
		return is_entp;
	}

	public void setIs_entp(Integer is_entp) {
		this.is_entp = is_entp;
	}

	/**
	 * 是否合伙人，1表示是，0表是否
	 * 
	 * @return
	 */
	public Integer getIs_fuwu() {
		return is_fuwu;
	}

	public void setIs_fuwu(Integer is_fuwu) {
		this.is_fuwu = is_fuwu;
	}

	public Integer getIs_lianmeng() {
		return is_lianmeng;
	}

	public void setIs_lianmeng(Integer is_lianmeng) {
		this.is_lianmeng = is_lianmeng;
	}

	public BigDecimal getBi_dianzi() {
		return bi_dianzi;
	}

	public void setBi_dianzi(BigDecimal bi_dianzi) {
		this.bi_dianzi = bi_dianzi;
	}

	public BigDecimal getBi_xiaofei() {
		return bi_xiaofei;
	}

	public void setBi_xiaofei(BigDecimal bi_xiaofei) {
		this.bi_xiaofei = bi_xiaofei;
	}

	public BigDecimal getBi_huokuan() {
		return bi_huokuan;
	}

	public void setBi_huokuan(BigDecimal bi_huokuan) {
		this.bi_huokuan = bi_huokuan;
	}

	public BigDecimal getLeiji_money_user() {
		return leiji_money_user;
	}

	public BigDecimal getLeiji_money_entp() {
		return leiji_money_entp;
	}

	public void setLeiji_money_user(BigDecimal leiji_money_user) {
		this.leiji_money_user = leiji_money_user;
	}

	public void setLeiji_money_entp(BigDecimal leiji_money_entp) {
		this.leiji_money_entp = leiji_money_entp;
	}

	public String getYmid() {
		return ymid;
	}

	public void setYmid(String ymid) {
		this.ymid = ymid;
	}

	public Integer getIs_daqu() {
		return is_daqu;
	}

	public void setIs_daqu(Integer is_daqu) {
		this.is_daqu = is_daqu;
	}

	public BigDecimal getLeiji_money_tuiguang() {
		return leiji_money_tuiguang;
	}

	public void setLeiji_money_tuiguang(BigDecimal leiji_money_tuiguang) {
		this.leiji_money_tuiguang = leiji_money_tuiguang;
	}

	public BigDecimal getUser_score_union() {
		return user_score_union;
	}

	public void setUser_score_union(BigDecimal user_score_union) {
		this.user_score_union = user_score_union;
	}

	public BigDecimal getBi_dianzi_lock() {
		return bi_dianzi_lock;
	}

	public void setBi_dianzi_lock(BigDecimal bi_dianzi_lock) {
		this.bi_dianzi_lock = bi_dianzi_lock;
	}

	public String getPassword_pay() {
		return password_pay;
	}

	public void setPassword_pay(String password_pay) {
		this.password_pay = password_pay;
	}

	public Integer getIs_set_security() {
		return is_set_security;
	}

	public void setIs_set_security(Integer is_set_security) {
		this.is_set_security = is_set_security;
	}

	public BigDecimal getBi_dianzi_lost() {
		return bi_dianzi_lost;
	}

	public void setBi_dianzi_lost(BigDecimal bi_dianzi_lost) {
		this.bi_dianzi_lost = bi_dianzi_lost;
	}

	public BigDecimal getBi_dianzi_lock_tuiguan() {
		return bi_dianzi_lock_tuiguan;
	}

	public void setBi_dianzi_lock_tuiguan(BigDecimal bi_dianzi_lock_tuiguan) {
		this.bi_dianzi_lock_tuiguan = bi_dianzi_lock_tuiguan;
	}

	public Date getIs_lianmeng_date() {
		return is_lianmeng_date;
	}

	public void setIs_lianmeng_date(Date is_lianmeng_date) {
		this.is_lianmeng_date = is_lianmeng_date;
	}

	public Date getIs_lianmeng_canceled_date() {
		return is_lianmeng_canceled_date;
	}

	public void setIs_lianmeng_canceled_date(Date is_lianmeng_canceled_date) {
		this.is_lianmeng_canceled_date = is_lianmeng_canceled_date;
	}

	public Date getScore_update_date() {
		return score_update_date;
	}

	public void setScore_update_date(Date score_update_date) {
		this.score_update_date = score_update_date;
	}

	public Integer getIs_closed() {
		return is_closed;
	}

	public void setIs_closed(Integer is_closed) {
		this.is_closed = is_closed;
	}

	public String getDevice_token() {
		return device_token;
	}

	public void setDevice_token(String device_token) {
		this.device_token = device_token;
	}

	public String getDevice_token_app() {
		return device_token_app;
	}

	public void setDevice_token_app(String device_token_app) {
		this.device_token_app = device_token_app;
	}

	public Integer getIs_renzheng() {
		return is_renzheng;
	}

	public void setIs_renzheng(Integer is_renzheng) {
		this.is_renzheng = is_renzheng;
	}

	public Integer getIs_city_manager() {
		return is_city_manager;
	}

	public void setIs_city_manager(Integer is_city_manager) {
		this.is_city_manager = is_city_manager;
	}

	public Integer getBank_pindex() {
		return bank_pindex;
	}

	public void setBank_pindex(Integer bank_pindex) {
		this.bank_pindex = bank_pindex;
	}

	public String getBank_pname() {
		return bank_pname;
	}

	public void setBank_pname(String bank_pname) {
		this.bank_pname = bank_pname;
	}

	public String getBank_branch_name() {
		return bank_branch_name;
	}

	public void setBank_branch_name(String bank_branch_name) {
		this.bank_branch_name = bank_branch_name;
	}

	public Integer getIs_start() {
		return is_start;
	}

	public void setIs_start(Integer is_start) {
		this.is_start = is_start;
	}

	public Integer getIs_has_update_pass() {
		return is_has_update_pass;
	}

	public void setIs_has_update_pass(Integer is_has_update_pass) {
		this.is_has_update_pass = is_has_update_pass;
	}

	public BigDecimal getBi_fuxiao() {
		return bi_fuxiao;
	}

	public void setBi_fuxiao(BigDecimal bi_fuxiao) {
		this.bi_fuxiao = bi_fuxiao;
	}

	public Integer getLink_entp_id() {
		return link_entp_id;
	}

	public void setLink_entp_id(Integer link_entp_id) {
		this.link_entp_id = link_entp_id;
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

	public Integer getIs_village() {
		return is_village;
	}

	public void setIs_village(Integer is_village) {
		this.is_village = is_village;
	}

	public Integer getOwn_village_id() {
		return own_village_id;
	}

	public void setOwn_village_id(Integer own_village_id) {
		this.own_village_id = own_village_id;
	}

	public String getOwn_village_name() {
		return own_village_name;
	}

	public void setOwn_village_name(String own_village_name) {
		this.own_village_name = own_village_name;
	}

	public Integer getIs_poor() {
		return is_poor;
	}

	public void setIs_poor(Integer is_poor) {
		this.is_poor = is_poor;
	}

	public Integer getPoor_id() {
		return poor_id;
	}

	public void setPoor_id(Integer poor_id) {
		this.poor_id = poor_id;
	}

	public BigDecimal getBi_huokuan_lock() {
		return bi_huokuan_lock;
	}

	public void setBi_huokuan_lock(BigDecimal bi_huokuan_lock) {
		this.bi_huokuan_lock = bi_huokuan_lock;
	}

	public BigDecimal getBi_aid() {
		return bi_aid;
	}

	public void setBi_aid(BigDecimal bi_aid) {
		this.bi_aid = bi_aid;
	}

	public BigDecimal getBi_aid_lock() {
		return bi_aid_lock;
	}

	public void setBi_aid_lock(BigDecimal bi_aid_lock) {
		this.bi_aid_lock = bi_aid_lock;
	}

	public Date getCard_end_date() {
		return card_end_date;
	}

	public void setCard_end_date(Date card_end_date) {
		this.card_end_date = card_end_date;
	}

	public String getApp_key() {
		return app_key;
	}

	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}

	public BigDecimal getBi_aid_sended() {
		return bi_aid_sended;
	}

	public void setBi_aid_sended(BigDecimal bi_aid_sended) {
		this.bi_aid_sended = bi_aid_sended;
	}

	public BigDecimal getBi_welfare() {
		return bi_welfare;
	}

	public void setBi_welfare(BigDecimal bi_welfare) {
		this.bi_welfare = bi_welfare;
	}

}