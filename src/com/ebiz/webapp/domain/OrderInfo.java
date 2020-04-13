package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
/**
 * @author gagaliu
 */
public class OrderInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String trade_index;

	private Integer order_num;

	private BigDecimal order_money;

	private Integer order_state;

	private Integer shipping_address_id;

	private Date order_date;

	private Integer invoice_type;

	private String invoices_payable;

	private String invoices_no;

	private String delivery_time;

	private Integer invoice_status;

	private Integer pay_type;

	private String remark;

	private Date add_date;

	private Date end_date;

	private Integer entp_id;

	private String entp_name;

	private Integer add_user_id;

	private String add_user_name;

	private Date update_date;

	private Integer update_user_id;

	private Integer order_type;

	private BigDecimal matflow_price;

	private Integer add_entp_id;

	private String add_entp_name;

	private Date pay_date;

	private Date confirm_date;

	private BigDecimal service_price;

	private BigDecimal order_weight;

	private Integer is_freeship;

	private Integer order_state_admin;

	private Integer is_price_modify;

	private BigDecimal price_modify_old;

	private Date price_modify_date;

	private String price_modify_remark;

	private BigDecimal matflow_price_modify_old;

	private Integer price_modify_user_id;

	private String rel_name;

	private String rel_phone;

	private String rel_tel;

	private Integer rel_province;

	private Integer rel_city;

	private Integer rel_region;

	private String rel_addr;

	private String rel_email;

	private Integer rel_zip;

	private Integer gn_type;

	private Integer qdyh_id;

	private String qdyh_tip_desc;

	private Integer yhq_id;

	private String yhq_tip_desc;

	private BigDecimal jfdh_money;

	private String trade_no;

	private String trade_merger_index;

	private Date qrsh_date;

	private Integer is_sq_yj;

	private Integer is_test;

	private Integer rule_version;

	private Integer is_fuxiao;

	private Integer line_type;

	private BigDecimal order_money_cost;

	private String order_password;

	private Integer is_shixiao;

	private Date fahuo_date;

	private Integer is_opt;

	private String fahuo_remark;

	private BigDecimal money_cash;

	private BigDecimal money_bi;

	private BigDecimal money_bi_lock;

	private BigDecimal no_dis_money;

	private BigDecimal dis_bl;

	private List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();

	private List<OrderInfoDetails> orderInfoDetailsList = new ArrayList<OrderInfoDetails>();

	private String pay_extra_params;

	private Integer is_ziti;

	private Integer send_user_id;

	private Integer pay_platform;

	private BigDecimal profit_money;

	private BigDecimal profit_bl;

	private Integer daishou_or_haspay;

	private BigDecimal daishou_profit_bl;

	private Date finish_date;

	private BigDecimal xiadan_user_sum;

	private BigDecimal xiadan_user_balance;

	private BigDecimal entp_user_balance;

	private BigDecimal entp_huokuan_bi;

	private Integer delay_shouhuo;

	private BigDecimal res_balance;

	private Integer is_ruchi;

	private BigDecimal reward_money;

	private BigDecimal pre_bigdecimal;

	private Integer is_zhuanyuan;

	private BigDecimal red_money;

	private String device_no;

	private Integer village_id;

	private Integer is_sync_jd;

	private Date sync_date;

	private String jd_order_no;

	private Integer is_confirm_jd;

	private Date jd_confirm_date;

	private Integer is_comment;

	private Integer fp_state;

	private String entp_charge_no;

	private Integer publish_user_id;

	private BigDecimal yhq_sun_money;

	private Integer is_check;

	private Integer link_check_id;

	private BigDecimal real_pay_money;

	private BigDecimal welfare_pay_money;

	private BigDecimal card_pay_money;

	private Integer mark_order_id;

	private Integer audit_state;

	private Integer audit_user_id;

	private Integer card_id;

	private Date audit_date;

	private String audit_desc;

	private Integer leader_order_id;

	private Integer is_leader;

	private Integer is_group_success;

	private String jd_oid;

	private Integer is_reload_pay;

	private Integer group_number;

	public Integer getGroup_number() {
		return group_number;
	}

	public void setGroup_number(Integer group_number) {
		this.group_number = group_number;
	}

	public Integer getIs_reload_pay() {
		return is_reload_pay;
	}

	public void setIs_reload_pay(Integer is_reload_pay) {
		this.is_reload_pay = is_reload_pay;
	}

	private Integer activity_id;

	public Integer getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}

	public String getJd_oid() {
		return jd_oid;
	}

	public void setJd_oid(String jd_oid) {
		this.jd_oid = jd_oid;
	}

	public Integer getLeader_order_id() {
		return leader_order_id;
	}

	public void setLeader_order_id(Integer leader_order_id) {
		this.leader_order_id = leader_order_id;
	}

	public Integer getIs_leader() {
		return is_leader;
	}

	public void setIs_leader(Integer is_leader) {
		this.is_leader = is_leader;
	}

	public Integer getIs_group_success() {
		return is_group_success;
	}

	public void setIs_group_success(Integer is_group_success) {
		this.is_group_success = is_group_success;
	}

	public Integer getMark_order_id() {
		return mark_order_id;
	}

	public void setMark_order_id(Integer mark_order_id) {
		this.mark_order_id = mark_order_id;
	}

	public BigDecimal getReal_pay_money() {
		return real_pay_money;
	}

	public void setReal_pay_money(BigDecimal real_pay_money) {
		this.real_pay_money = real_pay_money;
	}

	public Integer getIs_check() {
		return is_check;
	}

	public void setIs_check(Integer is_check) {
		this.is_check = is_check;
	}

	public Integer getLink_check_id() {
		return link_check_id;
	}

	public void setLink_check_id(Integer link_check_id) {
		this.link_check_id = link_check_id;
	}

	public BigDecimal getYhq_sun_money() {
		return yhq_sun_money;
	}

	public void setYhq_sun_money(BigDecimal yhq_sun_money) {
		this.yhq_sun_money = yhq_sun_money;
	}

	public Integer getIs_comment() {
		return is_comment;
	}

	public void setIs_comment(Integer is_comment) {
		this.is_comment = is_comment;
	}

	public BigDecimal getRed_money() {
		return red_money;
	}

	public void setRed_money(BigDecimal red_money) {
		this.red_money = red_money;
	}

	public List<OrderInfo> getOrderInfoList() {
		return orderInfoList;
	}

	public void setOrderInfoList(List<OrderInfo> orderInfoList) {
		this.orderInfoList = orderInfoList;
	}

	public List<OrderInfoDetails> getOrderInfoDetailsList() {
		return orderInfoDetailsList;
	}

	public void setOrderInfoDetailsList(List<OrderInfoDetails> orderInfoDetailsList) {
		this.orderInfoDetailsList = orderInfoDetailsList;
	}

	public OrderInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTrade_index() {
		return trade_index;
	}

	public void setTrade_index(String trade_index) {
		this.trade_index = trade_index;
	}

	public Integer getOrder_num() {
		return order_num;
	}

	public void setOrder_num(Integer order_num) {
		this.order_num = order_num;
	}

	public BigDecimal getOrder_money() {
		return order_money;
	}

	public void setOrder_money(BigDecimal order_money) {
		this.order_money = order_money;
	}

	public Integer getOrder_state() {
		return order_state;
	}

	public void setOrder_state(Integer order_state) {
		this.order_state = order_state;
	}

	public Integer getShipping_address_id() {
		return shipping_address_id;
	}

	public void setShipping_address_id(Integer shipping_address_id) {
		this.shipping_address_id = shipping_address_id;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public Integer getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(Integer invoice_type) {
		this.invoice_type = invoice_type;
	}

	public String getInvoices_payable() {
		return invoices_payable;
	}

	public void setInvoices_payable(String invoices_payable) {
		this.invoices_payable = invoices_payable;
	}

	public String getDelivery_time() {
		return delivery_time;
	}

	public void setDelivery_time(String delivery_time) {
		this.delivery_time = delivery_time;
	}

	public Integer getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(Integer invoice_status) {
		this.invoice_status = invoice_status;
	}

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public String getEntp_name() {
		return entp_name;
	}

	public void setEntp_name(String entp_name) {
		this.entp_name = entp_name;
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

	public Integer getOrder_type() {
		return order_type;
	}

	public void setOrder_type(Integer order_type) {
		this.order_type = order_type;
	}

	public BigDecimal getMatflow_price() {
		return matflow_price;
	}

	public void setMatflow_price(BigDecimal matflow_price) {
		this.matflow_price = matflow_price;
	}

	public Integer getAdd_entp_id() {
		return add_entp_id;
	}

	public void setAdd_entp_id(Integer add_entp_id) {
		this.add_entp_id = add_entp_id;
	}

	public String getAdd_entp_name() {
		return add_entp_name;
	}

	public void setAdd_entp_name(String add_entp_name) {
		this.add_entp_name = add_entp_name;
	}

	public Date getPay_date() {
		return pay_date;
	}

	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}

	public Date getConfirm_date() {
		return confirm_date;
	}

	public void setConfirm_date(Date confirm_date) {
		this.confirm_date = confirm_date;
	}

	public BigDecimal getService_price() {
		return service_price;
	}

	public void setService_price(BigDecimal service_price) {
		this.service_price = service_price;
	}

	public BigDecimal getOrder_weight() {
		return order_weight;
	}

	public void setOrder_weight(BigDecimal order_weight) {
		this.order_weight = order_weight;
	}

	public Integer getIs_freeship() {
		return is_freeship;
	}

	public void setIs_freeship(Integer is_freeship) {
		this.is_freeship = is_freeship;
	}

	@Deprecated
	public Integer getOrder_state_admin() {
		return order_state_admin;
	}

	@Deprecated
	public void setOrder_state_admin(Integer order_state_admin) {
		this.order_state_admin = order_state_admin;
	}

	public Integer getIs_price_modify() {
		return is_price_modify;
	}

	public void setIs_price_modify(Integer is_price_modify) {
		this.is_price_modify = is_price_modify;
	}

	public BigDecimal getPrice_modify_old() {
		return price_modify_old;
	}

	public void setPrice_modify_old(BigDecimal price_modify_old) {
		this.price_modify_old = price_modify_old;
	}

	public Date getPrice_modify_date() {
		return price_modify_date;
	}

	public void setPrice_modify_date(Date price_modify_date) {
		this.price_modify_date = price_modify_date;
	}

	public String getPrice_modify_remark() {
		return price_modify_remark;
	}

	public void setPrice_modify_remark(String price_modify_remark) {
		this.price_modify_remark = price_modify_remark;
	}

	public BigDecimal getMatflow_price_modify_old() {
		return matflow_price_modify_old;
	}

	public void setMatflow_price_modify_old(BigDecimal matflow_price_modify_old) {
		this.matflow_price_modify_old = matflow_price_modify_old;
	}

	public Integer getPrice_modify_user_id() {
		return price_modify_user_id;
	}

	public void setPrice_modify_user_id(Integer price_modify_user_id) {
		this.price_modify_user_id = price_modify_user_id;
	}

	public String getRel_name() {
		return rel_name;
	}

	public void setRel_name(String rel_name) {
		this.rel_name = rel_name;
	}

	public String getRel_phone() {
		return rel_phone;
	}

	public void setRel_phone(String rel_phone) {
		this.rel_phone = rel_phone;
	}

	public String getRel_tel() {
		return rel_tel;
	}

	public void setRel_tel(String rel_tel) {
		this.rel_tel = rel_tel;
	}

	public Integer getRel_province() {
		return rel_province;
	}

	public void setRel_province(Integer rel_province) {
		this.rel_province = rel_province;
	}

	public Integer getRel_city() {
		return rel_city;
	}

	public void setRel_city(Integer rel_city) {
		this.rel_city = rel_city;
	}

	public Integer getRel_region() {
		return rel_region;
	}

	public void setRel_region(Integer rel_region) {
		this.rel_region = rel_region;
	}

	public String getRel_addr() {
		return rel_addr;
	}

	public void setRel_addr(String rel_addr) {
		this.rel_addr = rel_addr;
	}

	public String getRel_email() {
		return rel_email;
	}

	public void setRel_email(String rel_email) {
		this.rel_email = rel_email;
	}

	public Integer getRel_zip() {
		return rel_zip;
	}

	public void setRel_zip(Integer rel_zip) {
		this.rel_zip = rel_zip;
	}

	public Integer getGn_type() {
		return gn_type;
	}

	public void setGn_type(Integer gn_type) {
		this.gn_type = gn_type;
	}

	public Integer getQdyh_id() {
		return qdyh_id;
	}

	public void setQdyh_id(Integer qdyh_id) {
		this.qdyh_id = qdyh_id;
	}

	public String getQdyh_tip_desc() {
		return qdyh_tip_desc;
	}

	public void setQdyh_tip_desc(String qdyh_tip_desc) {
		this.qdyh_tip_desc = qdyh_tip_desc;
	}

	public Integer getYhq_id() {
		return yhq_id;
	}

	public void setYhq_id(Integer yhq_id) {
		this.yhq_id = yhq_id;
	}

	public String getYhq_tip_desc() {
		return yhq_tip_desc;
	}

	public void setYhq_tip_desc(String yhq_tip_desc) {
		this.yhq_tip_desc = yhq_tip_desc;
	}

	public BigDecimal getJfdh_money() {
		return jfdh_money;
	}

	public void setJfdh_money(BigDecimal jfdh_money) {
		this.jfdh_money = jfdh_money;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getTrade_merger_index() {
		return trade_merger_index;
	}

	public void setTrade_merger_index(String trade_merger_index) {
		this.trade_merger_index = trade_merger_index;
	}

	public Date getQrsh_date() {
		return qrsh_date;
	}

	public void setQrsh_date(Date qrsh_date) {
		this.qrsh_date = qrsh_date;
	}

	public Integer getIs_sq_yj() {
		return is_sq_yj;
	}

	public void setIs_sq_yj(Integer is_sq_yj) {
		this.is_sq_yj = is_sq_yj;
	}

	public Integer getIs_test() {
		return is_test;
	}

	public void setIs_test(Integer is_test) {
		this.is_test = is_test;
	}

	public Integer getRule_version() {
		return rule_version;
	}

	public Integer getIs_fuxiao() {
		return is_fuxiao;
	}

	public Integer getLine_type() {
		return line_type;
	}

	public void setRule_version(Integer rule_version) {
		this.rule_version = rule_version;
	}

	public void setIs_fuxiao(Integer is_fuxiao) {
		this.is_fuxiao = is_fuxiao;
	}

	public void setLine_type(Integer line_type) {
		this.line_type = line_type;
	}

	public BigDecimal getOrder_money_cost() {
		return order_money_cost;
	}

	public void setOrder_money_cost(BigDecimal order_money_cost) {
		this.order_money_cost = order_money_cost;
	}

	public String getOrder_password() {
		return order_password;
	}

	public void setOrder_password(String order_password) {
		this.order_password = order_password;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Integer getIs_shixiao() {
		return is_shixiao;
	}

	public void setIs_shixiao(Integer is_shixiao) {
		this.is_shixiao = is_shixiao;
	}

	public Date getFahuo_date() {
		return fahuo_date;
	}

	public void setFahuo_date(Date fahuo_date) {
		this.fahuo_date = fahuo_date;
	}

	public Integer getIs_opt() {
		return is_opt;
	}

	public void setIs_opt(Integer is_opt) {
		this.is_opt = is_opt;
	}

	public String getFahuo_remark() {
		return fahuo_remark;
	}

	public void setFahuo_remark(String fahuo_remark) {
		this.fahuo_remark = fahuo_remark;
	}

	public BigDecimal getMoney_cash() {
		return money_cash;
	}

	public void setMoney_cash(BigDecimal money_cash) {
		this.money_cash = money_cash;
	}

	public BigDecimal getMoney_bi() {
		return money_bi;
	}

	public void setMoney_bi(BigDecimal money_bi) {
		this.money_bi = money_bi;
	}

	public BigDecimal getMoney_bi_lock() {
		return money_bi_lock;
	}

	public void setMoney_bi_lock(BigDecimal money_bi_lock) {
		this.money_bi_lock = money_bi_lock;
	}

	public Integer getIs_ziti() {
		return is_ziti;
	}

	public void setIs_ziti(Integer is_ziti) {
		this.is_ziti = is_ziti;
	}

	public BigDecimal getNo_dis_money() {
		return no_dis_money;
	}

	public void setNo_dis_money(BigDecimal no_dis_money) {
		this.no_dis_money = no_dis_money;
	}

	public BigDecimal getDis_bl() {
		return dis_bl;
	}

	public void setDis_bl(BigDecimal dis_bl) {
		this.dis_bl = dis_bl;
	}

	public Integer getSend_user_id() {
		return send_user_id;
	}

	public void setSend_user_id(Integer send_user_id) {
		this.send_user_id = send_user_id;
	}

	public Integer getPay_platform() {
		return pay_platform;
	}

	public void setPay_platform(Integer pay_platform) {
		this.pay_platform = pay_platform;
	}

	public BigDecimal getProfit_money() {
		return profit_money;
	}

	public void setProfit_money(BigDecimal profit_money) {
		this.profit_money = profit_money;
	}

	public BigDecimal getProfit_bl() {
		return profit_bl;
	}

	public void setProfit_bl(BigDecimal profit_bl) {
		this.profit_bl = profit_bl;
	}

	public Integer getDaishou_or_haspay() {
		return daishou_or_haspay;
	}

	public void setDaishou_or_haspay(Integer daishou_or_haspay) {
		this.daishou_or_haspay = daishou_or_haspay;
	}

	public BigDecimal getDaishou_profit_bl() {
		return daishou_profit_bl;
	}

	public void setDaishou_profit_bl(BigDecimal daishou_profit_bl) {
		this.daishou_profit_bl = daishou_profit_bl;
	}

	public Date getFinish_date() {
		return finish_date;
	}

	public void setFinish_date(Date finish_date) {
		this.finish_date = finish_date;
	}

	public BigDecimal getXiadan_user_balance() {
		return xiadan_user_balance;
	}

	public void setXiadan_user_balance(BigDecimal xiadan_user_balance) {
		this.xiadan_user_balance = xiadan_user_balance;
	}

	public BigDecimal getEntp_user_balance() {
		return entp_user_balance;
	}

	public void setEntp_user_balance(BigDecimal entp_user_balance) {
		this.entp_user_balance = entp_user_balance;
	}

	public BigDecimal getEntp_huokuan_bi() {
		return entp_huokuan_bi;
	}

	public void setEntp_huokuan_bi(BigDecimal entp_huokuan_bi) {
		this.entp_huokuan_bi = entp_huokuan_bi;
	}

	public Integer getDelay_shouhuo() {
		return delay_shouhuo;
	}

	public void setDelay_shouhuo(Integer delay_shouhuo) {
		this.delay_shouhuo = delay_shouhuo;
	}

	public BigDecimal getRes_balance() {
		return res_balance;
	}

	public void setRes_balance(BigDecimal res_balance) {
		this.res_balance = res_balance;
	}

	public String getPay_extra_params() {
		return pay_extra_params;
	}

	public void setPay_extra_params(String pay_extra_params) {
		this.pay_extra_params = pay_extra_params;
	}

	public Integer getIs_ruchi() {
		return is_ruchi;
	}

	public void setIs_ruchi(Integer is_ruchi) {
		this.is_ruchi = is_ruchi;
	}

	public BigDecimal getReward_money() {
		return reward_money;
	}

	public void setReward_money(BigDecimal reward_money) {
		this.reward_money = reward_money;
	}

	public BigDecimal getXiadan_user_sum() {
		return xiadan_user_sum;
	}

	public void setXiadan_user_sum(BigDecimal xiadan_user_sum) {
		this.xiadan_user_sum = xiadan_user_sum;
	}

	public BigDecimal getPre_bigdecimal() {
		return pre_bigdecimal;
	}

	public void setPre_bigdecimal(BigDecimal pre_bigdecimal) {
		this.pre_bigdecimal = pre_bigdecimal;
	}

	public Integer getIs_zhuanyuan() {
		return is_zhuanyuan;
	}

	public void setIs_zhuanyuan(Integer is_zhuanyuan) {
		this.is_zhuanyuan = is_zhuanyuan;
	}

	public String getDevice_no() {
		return device_no;
	}

	public void setDevice_no(String device_no) {
		this.device_no = device_no;
	}

	public Integer getVillage_id() {
		return village_id;
	}

	public void setVillage_id(Integer village_id) {
		this.village_id = village_id;
	}

	public Integer getIs_sync_jd() {
		return is_sync_jd;
	}

	public void setIs_sync_jd(Integer is_sync_jd) {
		this.is_sync_jd = is_sync_jd;
	}

	public Date getSync_date() {
		return sync_date;
	}

	public void setSync_date(Date sync_date) {
		this.sync_date = sync_date;
	}

	public String getJd_order_no() {
		return jd_order_no;
	}

	public void setJd_order_no(String jd_order_no) {
		this.jd_order_no = jd_order_no;
	}

	public Integer getIs_confirm_jd() {
		return is_confirm_jd;
	}

	public void setIs_confirm_jd(Integer is_confirm_jd) {
		this.is_confirm_jd = is_confirm_jd;
	}

	public Date getJd_confirm_date() {
		return jd_confirm_date;
	}

	public void setJd_confirm_date(Date jd_confirm_date) {
		this.jd_confirm_date = jd_confirm_date;
	}

	public Integer getFp_state() {
		return fp_state;
	}

	public void setFp_state(Integer fp_state) {
		this.fp_state = fp_state;
	}

	public String getEntp_charge_no() {
		return entp_charge_no;
	}

	public void setEntp_charge_no(String entp_charge_no) {
		this.entp_charge_no = entp_charge_no;
	}

	public Integer getPublish_user_id() {
		return publish_user_id;
	}

	public void setPublish_user_id(Integer publish_user_id) {
		this.publish_user_id = publish_user_id;
	}

	public String getInvoices_no() {
		return invoices_no;
	}

	public void setInvoices_no(String invoices_no) {
		this.invoices_no = invoices_no;
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

	public BigDecimal getWelfare_pay_money() {
		return welfare_pay_money;
	}

	public void setWelfare_pay_money(BigDecimal welfare_pay_money) {
		this.welfare_pay_money = welfare_pay_money;
	}

	public Integer getCard_id() {
		return card_id;
	}

	public void setCard_id(Integer card_id) {
		this.card_id = card_id;
	}

	public BigDecimal getCard_pay_money() {
		return card_pay_money;
	}

	public void setCard_pay_money(BigDecimal card_pay_money) {
		this.card_pay_money = card_pay_money;
	}

}