package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-01-15 16:11
 */
public class VillageInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String village_name;

	private Long p_index;

	private String village_mobile;

	private String owner_name;

	private Date add_date;

	private Integer add_user_id;

	private String add_user_name;

	private Date update_date;

	private Integer update_user_id;

	private Date del_date;

	private Integer del_user_id;

	private Integer is_del;

	private Integer is_ren_zheng;

	private Integer is_virtual;

	private Date service_online_date;

	private Date service_operation_date;

	private Date service_operation_date_end;

	private String shop_name;

	private String shop_licence;

	private String shop_licence_img;

	private String shop_faith_code;

	private String food_license;

	private Date audit_date;

	private String audit_desc;

	private Integer audit_state;

	private Integer audit_user_id;

	private String position_latlng;

	private String food_licence_img;

	private String village_logo;

	private String village_banner;

	private String village_qrcode;

	private Integer sex;

	private Date birthday;

	private String id_card;

	private String appid_qq;

	private String img_id_card_zm;

	private String img_id_card_fm;

	private String email;

	private String village_address;

	private Integer count;

	private String village_condition;

	private String village_qrcode_path;

	public VillageInfo() {

	}

	public String getVillage_qrcode_path() {
		return village_qrcode_path;
	}

	public void setVillage_qrcode_path(String village_qrcode_path) {
		this.village_qrcode_path = village_qrcode_path;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVillage_name() {
		return village_name;
	}

	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}

	public Long getP_index() {
		return p_index;
	}

	public void setP_index(Long p_index) {
		this.p_index = p_index;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
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

	public Date getService_online_date() {
		return service_online_date;
	}

	public void setService_online_date(Date service_online_date) {
		this.service_online_date = service_online_date;
	}

	public Date getService_operation_date() {
		return service_operation_date;
	}

	public void setService_operation_date(Date service_operation_date) {
		this.service_operation_date = service_operation_date;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getShop_licence() {
		return shop_licence;
	}

	public void setShop_licence(String shop_licence) {
		this.shop_licence = shop_licence;
	}

	public String getShop_licence_img() {
		return shop_licence_img;
	}

	public void setShop_licence_img(String shop_licence_img) {
		this.shop_licence_img = shop_licence_img;
	}

	public String getShop_faith_code() {
		return shop_faith_code;
	}

	public void setShop_faith_code(String shop_faith_code) {
		this.shop_faith_code = shop_faith_code;
	}

	public String getFood_license() {
		return food_license;
	}

	public void setFood_license(String food_license) {
		this.food_license = food_license;
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

	public String getPosition_latlng() {
		return position_latlng;
	}

	public void setPosition_latlng(String position_latlng) {
		this.position_latlng = position_latlng;
	}

	public String getFood_licence_img() {
		return food_licence_img;
	}

	public void setFood_licence_img(String food_licence_img) {
		this.food_licence_img = food_licence_img;
	}

	public String getVillage_mobile() {
		return village_mobile;
	}

	public void setVillage_mobile(String village_mobile) {
		this.village_mobile = village_mobile;
	}

	public String getVillage_logo() {
		return village_logo;
	}

	public void setVillage_logo(String village_logo) {
		this.village_logo = village_logo;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getAppid_qq() {
		return appid_qq;
	}

	public void setAppid_qq(String appid_qq) {
		this.appid_qq = appid_qq;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVillage_address() {
		return village_address;
	}

	public void setVillage_address(String village_address) {
		this.village_address = village_address;
	}

	public Date getService_operation_date_end() {
		return service_operation_date_end;
	}

	public void setService_operation_date_end(Date service_operation_date_end) {
		this.service_operation_date_end = service_operation_date_end;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getVillage_banner() {
		return village_banner;
	}

	public void setVillage_banner(String village_banner) {
		this.village_banner = village_banner;
	}

	public String getVillage_qrcode() {
		return village_qrcode;
	}

	public void setVillage_qrcode(String village_qrcode) {
		this.village_qrcode = village_qrcode;
	}

	public String getVillage_condition() {
		return village_condition;
	}

	public void setVillage_condition(String village_condition) {
		this.village_condition = village_condition;
	}

	public Integer getIs_ren_zheng() {
		return is_ren_zheng;
	}

	public void setIs_ren_zheng(Integer is_ren_zheng) {
		this.is_ren_zheng = is_ren_zheng;
	}

	public Integer getIs_virtual() {
		return is_virtual;
	}

	public void setIs_virtual(Integer is_virtual) {
		this.is_virtual = is_virtual;
	}

}