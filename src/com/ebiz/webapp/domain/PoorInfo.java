package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-01-20 15:08
 */
public class PoorInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String pet_name;

	private String mobile;

	private String appid_weixin;

	private Long p_index;

	private Date add_date;

	private Integer add_user_id;

	private String add_user_name;

	private Integer user_id;

	private String user_name;

	private Date update_date;

	private Integer update_user_id;

	private Date del_date;

	private Integer del_user_id;

	private Integer is_del;

	private Integer is_tuo_pin;

	private Date residence_start_time;

	private Date residence_end_time;

	private String addr;

	private String email;

	private String head_logo;

	private String nation;

	private String education;

	private String graduate_school;

	private String person_introduce;

	private Integer audit_state;

	private Integer audit_user_id;

	private Date audit_date;

	private String audit_desc;

	private String position_latlng;

	private Integer sex;

	private Date brithday;

	private String qq;

	private String id_card;

	private String img_id_card_zm;

	private String img_id_card_fm;

	private String real_name;

	private Integer family_num;

	private Integer gendi_arear;

	private Integer lindi_arear;

	private Integer mucaodi_arear;

	private Integer house_arear;

	private String poor_reason;

	private Integer report_step;

	private Integer report_state;

	private Date jian_dang_date;

	private Date tuo_pin_date;

	private String dang_an_img;

	private Date tuo_pin_plan_date;

	private Integer village_id;

	private String remark;

	private String poor_qrcode;

	public String getPoor_qrcode() {
		return poor_qrcode;
	}

	public void setPoor_qrcode(String poor_qrcode) {
		this.poor_qrcode = poor_qrcode;
	}

	private List<PoorInfo> poorInfoList = new ArrayList<PoorInfo>();

	private List<PoorFamily> poorFamilyList = new ArrayList<PoorFamily>();

	public PoorInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPet_name() {
		return pet_name;
	}

	public void setPet_name(String pet_name) {
		this.pet_name = pet_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAppid_weixin() {
		return appid_weixin;
	}

	public void setAppid_weixin(String appid_weixin) {
		this.appid_weixin = appid_weixin;
	}

	public Long getP_index() {
		return p_index;
	}

	public void setP_index(Long p_index) {
		this.p_index = p_index;
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

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Date getResidence_start_time() {
		return residence_start_time;
	}

	public void setResidence_start_time(Date residence_start_time) {
		this.residence_start_time = residence_start_time;
	}

	public Date getResidence_end_time() {
		return residence_end_time;
	}

	public void setResidence_end_time(Date residence_end_time) {
		this.residence_end_time = residence_end_time;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHead_logo() {
		return head_logo;
	}

	public void setHead_logo(String head_logo) {
		this.head_logo = head_logo;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getGraduate_school() {
		return graduate_school;
	}

	public void setGraduate_school(String graduate_school) {
		this.graduate_school = graduate_school;
	}

	public String getPerson_introduce() {
		return person_introduce;
	}

	public void setPerson_introduce(String person_introduce) {
		this.person_introduce = person_introduce;
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

	public String getPosition_latlng() {
		return position_latlng;
	}

	public void setPosition_latlng(String position_latlng) {
		this.position_latlng = position_latlng;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
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

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Integer getMucaodi_arear() {
		return mucaodi_arear;
	}

	public void setMucaodi_arear(Integer mucaodi_arear) {
		this.mucaodi_arear = mucaodi_arear;
	}

	public Integer getFamily_num() {
		return family_num;
	}

	public void setFamily_num(Integer family_num) {
		this.family_num = family_num;
	}

	public Integer getGendi_arear() {
		return gendi_arear;
	}

	public void setGendi_arear(Integer gendi_arear) {
		this.gendi_arear = gendi_arear;
	}

	public String getPoor_reason() {
		return poor_reason;
	}

	public void setPoor_reason(String poor_reason) {
		this.poor_reason = poor_reason;
	}

	public Integer getHouse_arear() {
		return house_arear;
	}

	public void setHouse_arear(Integer house_arear) {
		this.house_arear = house_arear;
	}

	public Integer getLindi_arear() {
		return lindi_arear;
	}

	public void setLindi_arear(Integer lindi_arear) {
		this.lindi_arear = lindi_arear;
	}

	public Integer getReport_step() {
		return report_step;
	}

	public void setReport_step(Integer report_step) {
		this.report_step = report_step;
	}

	public Integer getReport_state() {
		return report_state;
	}

	public void setReport_state(Integer report_state) {
		this.report_state = report_state;
	}

	public String getDang_an_img() {
		return dang_an_img;
	}

	public void setDang_an_img(String dang_an_img) {
		this.dang_an_img = dang_an_img;
	}

	public Date getTuo_pin_date() {
		return tuo_pin_date;
	}

	public void setTuo_pin_date(Date tuo_pin_date) {
		this.tuo_pin_date = tuo_pin_date;
	}

	public Date getJian_dang_date() {
		return jian_dang_date;
	}

	public void setJian_dang_date(Date jian_dang_date) {
		this.jian_dang_date = jian_dang_date;
	}

	public Date getTuo_pin_plan_date() {
		return tuo_pin_plan_date;
	}

	public void setTuo_pin_plan_date(Date tuo_pin_plan_date) {
		this.tuo_pin_plan_date = tuo_pin_plan_date;
	}

	public Integer getIs_tuo_pin() {
		return is_tuo_pin;
	}

	public void setIs_tuo_pin(Integer is_tuo_pin) {
		this.is_tuo_pin = is_tuo_pin;
	}

	public Integer getVillage_id() {
		return village_id;
	}

	public void setVillage_id(Integer village_id) {
		this.village_id = village_id;
	}

	public List<PoorInfo> getPoorInfoList() {
		return poorInfoList;
	}

	public void setPoorInfoList(List<PoorInfo> poorInfoList) {
		this.poorInfoList = poorInfoList;
	}

	public List<PoorFamily> getPoorFamilyList() {
		return poorFamilyList;
	}

	public void setPoorFamilyList(List<PoorFamily> poorFamilyList) {
		this.poorFamilyList = poorFamilyList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}