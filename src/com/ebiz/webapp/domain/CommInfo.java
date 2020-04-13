package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-21 10:41
 */
public class CommInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer pd_id;

	private String pd_name;

	private Integer cls_id;

	private String cls_name;

	private Integer par_cls_id;

	private Integer root_cls_id;

	private String comm_name;

	private String comm_no;

	private Integer comm_type;

	private String sub_title;

	private Integer own_entp_id;

	private String main_pic;

	private BigDecimal price_ref;

	private Integer is_sell;

	private Date up_date;

	private Date down_date;

	private Integer p_index;

	private String comm_desc;

	private Integer prom_type;

	private String prom_info;

	private String prom_url;

	private Integer order_value;

	private Integer is_zp;

	private Integer save_step;

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

	private String audit_service_desc;

	private Integer is_locked;

	private Integer lock_user_id;

	private Integer brand_id;

	private String comm_barcode;

	private BigDecimal comm_weight;

	private Integer is_freeship;

	private String key_word;

	private BigDecimal sale_price;

	private String seo_title;

	private String seo_keyword;

	private String seo_desc;

	private Integer view_count;

	private Integer gn_type;

	private List<PdImgs> commImgsList;

	private Integer is_sync_jxc;

	private String comm_content;

	private String comm_paramenter;

	private String comm_asrc_content;

	private List<PdImgs> pdImgsList;

	private Integer freight_id;

	private BigDecimal comm_volume;

	private Integer comment_count; // 评论数

	private Integer sale_count; // 销售数量

	private Integer sale_count_update; // 销售数量（可以修改的）

	private Integer inventory; // 库存

	private Integer is_self_support; // 自营

	private String price_ref_desc;

	private Integer is_commend;

	private Long tb_id;

	private String tb_url;

	private BigDecimal cost_price;

	private Integer line_type;

	private Integer fanxian_rule;

	private Integer is_has_tc;

	private Integer show_notes;

	private String comm_notes;

	private Integer red_scale;

	private Date last_input_date;

	private Integer is_rebate;

	private BigDecimal rebate_scale;

	private Integer is_aid;

	private BigDecimal aid_scale;

	private List<CommInfoPoors> poorsList;

	private BigDecimal sum_rebate_money;

	private BigDecimal sum_aid_money;

	private Integer is_zingying;

	private Integer is_jd;

	private BigDecimal jd_agent_price;

	private String jd_skuid;

	private Integer is_fapiao;

	private CommTczhPrice commTczhPrice;

	private String comm_qrcode_path;// 商品二维码

	private Integer is_ziti;

	private Integer multiple_order_value;

	private Integer entp_comm_class_id;

	private Integer mark_comm_id;

	private Integer is_temp;

	private Date presell_date;

	// 新增拼团相关字段
	private Integer group_count;// 拼团人数

	private Integer group_type;// 拼团类型

	private Integer group_time;// 拼团时间周期（单位：小时）

	private Integer is_activity_default;// 是否活动默认商品

	private Integer copy_id;// 是否活动默认商品

	public Integer getIs_activity_default() {
		return is_activity_default;
	}

	public void setIs_activity_default(Integer is_activity_default) {
		this.is_activity_default = is_activity_default;
	}

	/*** 新增字段get、set ***/
	public Integer getGroup_count() {
		return group_count;
	}

	public void setGroup_count(Integer group_count) {
		this.group_count = group_count;
	}

	public Integer getGroup_type() {
		return group_type;
	}

	public void setGroup_type(Integer group_type) {
		this.group_type = group_type;
	}

	public Integer getGroup_time() {
		return group_time;
	}

	public void setGroup_time(Integer group_time) {
		this.group_time = group_time;
	}

	/*** 新增字段get、set ***/

	public Integer getMark_comm_id() {
		return mark_comm_id;
	}

	public void setMark_comm_id(Integer mark_comm_id) {
		this.mark_comm_id = mark_comm_id;
	}

	public Integer getEntp_comm_class_id() {
		return entp_comm_class_id;
	}

	public void setEntp_comm_class_id(Integer entp_comm_class_id) {
		this.entp_comm_class_id = entp_comm_class_id;
	}

	public String getComm_qrcode_path() {
		return comm_qrcode_path;
	}

	public void setComm_qrcode_path(String comm_qrcode_path) {
		this.comm_qrcode_path = comm_qrcode_path;
	}

	public Integer getRed_scale() {
		return red_scale;
	}

	public void setRed_scale(Integer red_scale) {
		this.red_scale = red_scale;
	}

	public Integer getShow_notes() {
		return show_notes;
	}

	public void setShow_notes(Integer show_notes) {
		this.show_notes = show_notes;
	}

	public String getComm_notes() {
		return comm_notes;
	}

	public void setComm_notes(String comm_notes) {
		this.comm_notes = comm_notes;
	}

	public Integer getIs_self_support() {
		return is_self_support;
	}

	public void setIs_self_support(Integer isSelfSupport) {
		is_self_support = isSelfSupport;
	}

	public CommInfo() {

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

	public Date getAudit_date() {
		return audit_date;
	}

	public String getAudit_desc() {
		return audit_desc;
	}

	public Integer getAudit_state() {
		return audit_state;
	}

	public Integer getAudit_user_id() {
		return audit_user_id;
	}

	public Integer getBrand_id() {
		return brand_id;
	}

	public Integer getCls_id() {
		return cls_id;
	}

	public String getCls_name() {
		return cls_name;
	}

	public String getComm_asrc_content() {
		return comm_asrc_content;
	}

	public String getComm_barcode() {
		return comm_barcode;
	}

	public String getComm_content() {
		return comm_content;
	}

	public String getComm_paramenter() {
		return comm_paramenter;
	}

	public String getComm_desc() {
		return comm_desc;
	}

	public String getComm_name() {
		return comm_name;
	}

	public String getComm_no() {
		return comm_no;
	}

	public Integer getComm_type() {
		return comm_type;
	}

	public BigDecimal getComm_weight() {
		return comm_weight;
	}

	public List<PdImgs> getCommImgsList() {
		return commImgsList;
	}

	public Date getDel_date() {
		return del_date;
	}

	public Integer getDel_user_id() {
		return del_user_id;
	}

	public Date getDown_date() {
		return down_date;
	}

	public Integer getGn_type() {
		return gn_type;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public Integer getIs_freeship() {
		return is_freeship;
	}

	public Integer getIs_locked() {
		return is_locked;
	}

	public Integer getIs_sell() {
		return is_sell;
	}

	public Integer getIs_sync_jxc() {
		return is_sync_jxc;
	}

	public Integer getIs_zp() {
		return is_zp;
	}

	public String getKey_word() {
		return key_word;
	}

	public Integer getLock_user_id() {
		return lock_user_id;
	}

	public String getMain_pic() {
		return main_pic;
	}

	public BigDecimal getSale_price() {
		return sale_price;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public Integer getOwn_entp_id() {
		return own_entp_id;
	}

	public Integer getP_index() {
		return p_index;
	}

	public Integer getPar_cls_id() {
		return par_cls_id;
	}

	public Integer getPd_id() {
		return pd_id;
	}

	public String getPd_name() {
		return pd_name;
	}

	public BigDecimal getPrice_ref() {
		return price_ref;
	}

	public String getProm_info() {
		return prom_info;
	}

	public Integer getProm_type() {
		return prom_type;
	}

	public String getProm_url() {
		return prom_url;
	}

	public Integer getSave_step() {
		return save_step;
	}

	public String getSeo_desc() {
		return seo_desc;
	}

	public String getSeo_keyword() {
		return seo_keyword;
	}

	public String getSeo_title() {
		return seo_title;
	}

	public String getSub_title() {
		return sub_title;
	}

	public Date getUp_date() {
		return up_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public Integer getUpdate_user_id() {
		return update_user_id;
	}

	public Integer getView_count() {
		return view_count;
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

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public void setAudit_desc(String audit_desc) {
		this.audit_desc = audit_desc;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}

	public void setAudit_user_id(Integer audit_user_id) {
		this.audit_user_id = audit_user_id;
	}

	public void setBrand_id(Integer brand_id) {
		this.brand_id = brand_id;
	}

	public void setCls_id(Integer cls_id) {
		this.cls_id = cls_id;
	}

	public void setCls_name(String cls_name) {
		this.cls_name = cls_name;
	}

	public void setComm_asrc_content(String comm_asrc_content) {
		this.comm_asrc_content = comm_asrc_content;
	}

	public void setComm_barcode(String comm_barcode) {
		this.comm_barcode = comm_barcode;
	}

	public void setComm_content(String comm_content) {
		this.comm_content = comm_content;
	}

	public void setComm_paramenter(String comm_paramenter) {
		this.comm_paramenter = comm_paramenter;
	}

	public void setComm_desc(String comm_desc) {
		this.comm_desc = comm_desc;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public void setComm_no(String comm_no) {
		this.comm_no = comm_no;
	}

	public void setComm_type(Integer comm_type) {
		this.comm_type = comm_type;
	}

	public void setComm_weight(BigDecimal comm_weight) {
		this.comm_weight = comm_weight;
	}

	public void setCommImgsList(List<PdImgs> commImgsList) {
		this.commImgsList = commImgsList;
	}

	public void setDel_date(Date del_date) {
		this.del_date = del_date;
	}

	public void setDel_user_id(Integer del_user_id) {
		this.del_user_id = del_user_id;
	}

	public void setDown_date(Date down_date) {
		this.down_date = down_date;
	}

	public void setGn_type(Integer gn_type) {
		this.gn_type = gn_type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public void setIs_freeship(Integer is_freeship) {
		this.is_freeship = is_freeship;
	}

	public void setIs_locked(Integer is_locked) {
		this.is_locked = is_locked;
	}

	public void setIs_sell(Integer is_sell) {
		this.is_sell = is_sell;
	}

	public void setIs_sync_jxc(Integer is_sync_jxc) {
		this.is_sync_jxc = is_sync_jxc;
	}

	public void setIs_zp(Integer is_zp) {
		this.is_zp = is_zp;
	}

	public void setKey_word(String key_word) {
		this.key_word = key_word;
	}

	public void setLock_user_id(Integer lock_user_id) {
		this.lock_user_id = lock_user_id;
	}

	public void setMain_pic(String main_pic) {
		this.main_pic = main_pic;
	}

	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public void setOwn_entp_id(Integer own_entp_id) {
		this.own_entp_id = own_entp_id;
	}

	public void setP_index(Integer p_index) {
		this.p_index = p_index;
	}

	public void setPar_cls_id(Integer par_cls_id) {
		this.par_cls_id = par_cls_id;
	}

	public void setPd_id(Integer pd_id) {
		this.pd_id = pd_id;
	}

	public void setPd_name(String pd_name) {
		this.pd_name = pd_name;
	}

	public void setPrice_ref(BigDecimal price_ref) {
		this.price_ref = price_ref;
	}

	public void setProm_info(String prom_info) {
		this.prom_info = prom_info;
	}

	public void setProm_type(Integer prom_type) {
		this.prom_type = prom_type;
	}

	public void setProm_url(String prom_url) {
		this.prom_url = prom_url;
	}

	public void setSave_step(Integer save_step) {
		this.save_step = save_step;
	}

	public void setSeo_desc(String seo_desc) {
		this.seo_desc = seo_desc;
	}

	public void setSeo_keyword(String seo_keyword) {
		this.seo_keyword = seo_keyword;
	}

	public void setSeo_title(String seo_title) {
		this.seo_title = seo_title;
	}

	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}

	public void setUp_date(Date up_date) {
		this.up_date = up_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}

	public void setView_count(Integer view_count) {
		this.view_count = view_count;
	}

	public List<PdImgs> getPdImgsList() {
		return pdImgsList;
	}

	public void setPdImgsList(List<PdImgs> pdImgsList) {
		this.pdImgsList = pdImgsList;
	}

	public Integer getFreight_id() {
		return freight_id;
	}

	public void setFreight_id(Integer freight_id) {
		this.freight_id = freight_id;
	}

	public BigDecimal getComm_volume() {
		return comm_volume;
	}

	public void setComm_volume(BigDecimal comm_volume) {
		this.comm_volume = comm_volume;
	}

	public Integer getComment_count() {
		return comment_count;
	}

	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}

	public Integer getSale_count() {
		return sale_count;
	}

	public void setSale_count(Integer sale_count) {
		this.sale_count = sale_count;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public String getPrice_ref_desc() {
		return price_ref_desc;
	}

	public void setPrice_ref_desc(String price_ref_desc) {
		this.price_ref_desc = price_ref_desc;
	}

	public Integer getIs_commend() {
		return is_commend;
	}

	public void setIs_commend(Integer is_commend) {
		this.is_commend = is_commend;
	}

	public Long getTb_id() {
		return tb_id;
	}

	public void setTb_id(Long tb_id) {
		this.tb_id = tb_id;
	}

	public String getTb_url() {
		return tb_url;
	}

	public void setTb_url(String tb_url) {
		this.tb_url = tb_url;
	}

	public BigDecimal getCost_price() {
		return cost_price;
	}

	public void setCost_price(BigDecimal cost_price) {
		this.cost_price = cost_price;
	}

	public Integer getLine_type() {
		return line_type;
	}

	public void setLine_type(Integer line_type) {
		this.line_type = line_type;
	}

	public Integer getFanxian_rule() {
		return fanxian_rule;
	}

	public void setFanxian_rule(Integer fanxian_rule) {
		this.fanxian_rule = fanxian_rule;
	}

	public Integer getIs_has_tc() {
		return is_has_tc;
	}

	public void setIs_has_tc(Integer is_has_tc) {
		this.is_has_tc = is_has_tc;
	}

	public String getAudit_service_desc() {
		return audit_service_desc;
	}

	public void setAudit_service_desc(String audit_service_desc) {
		this.audit_service_desc = audit_service_desc;
	}

	public Integer getRoot_cls_id() {
		return root_cls_id;
	}

	public void setRoot_cls_id(Integer root_cls_id) {
		this.root_cls_id = root_cls_id;
	}

	public Integer getSale_count_update() {
		return sale_count_update;
	}

	public void setSale_count_update(Integer sale_count_update) {
		this.sale_count_update = sale_count_update;
	}

	public Date getLast_input_date() {
		return last_input_date;
	}

	public void setLast_input_date(Date last_input_date) {
		this.last_input_date = last_input_date;
	}

	public Integer getIs_rebate() {
		return is_rebate;
	}

	public void setIs_rebate(Integer is_rebate) {
		this.is_rebate = is_rebate;
	}

	public BigDecimal getRebate_scale() {
		return rebate_scale;
	}

	public void setRebate_scale(BigDecimal rebate_scale) {
		this.rebate_scale = rebate_scale;
	}

	public Integer getIs_aid() {
		return is_aid;
	}

	public void setIs_aid(Integer is_aid) {
		this.is_aid = is_aid;
	}

	public BigDecimal getAid_scale() {
		return aid_scale;
	}

	public void setAid_scale(BigDecimal aid_scale) {
		this.aid_scale = aid_scale;
	}

	public List<CommInfoPoors> getPoorsList() {
		return poorsList;
	}

	public void setPoorsList(List<CommInfoPoors> poorsList) {
		this.poorsList = poorsList;
	}

	public BigDecimal getSum_rebate_money() {
		return sum_rebate_money;
	}

	public void setSum_rebate_money(BigDecimal sum_rebate_money) {
		this.sum_rebate_money = sum_rebate_money;
	}

	public BigDecimal getSum_aid_money() {
		return sum_aid_money;
	}

	public void setSum_aid_money(BigDecimal sum_aid_money) {
		this.sum_aid_money = sum_aid_money;
	}

	public Integer getIs_zingying() {
		return is_zingying;
	}

	public void setIs_zingying(Integer is_zingying) {
		this.is_zingying = is_zingying;
	}

	public Integer getIs_jd() {
		return is_jd;
	}

	public void setIs_jd(Integer is_jd) {
		this.is_jd = is_jd;
	}

	public BigDecimal getJd_agent_price() {
		return jd_agent_price;
	}

	public void setJd_agent_price(BigDecimal jd_agent_price) {
		this.jd_agent_price = jd_agent_price;
	}

	public String getJd_skuid() {
		return jd_skuid;
	}

	public void setJd_skuid(String jd_skuid) {
		this.jd_skuid = jd_skuid;
	}

	public CommTczhPrice getCommTczhPrice() {
		return commTczhPrice;
	}

	public void setCommTczhPrice(CommTczhPrice commTczhPrice) {
		this.commTczhPrice = commTczhPrice;
	}

	public Integer getIs_fapiao() {
		return is_fapiao;
	}

	public void setIs_fapiao(Integer is_fapiao) {
		this.is_fapiao = is_fapiao;
	}

	public Integer getIs_ziti() {
		return is_ziti;
	}

	public void setIs_ziti(Integer is_ziti) {
		this.is_ziti = is_ziti;
	}

	public Integer getMultiple_order_value() {
		return multiple_order_value;
	}

	public void setMultiple_order_value(Integer multiple_order_value) {
		this.multiple_order_value = multiple_order_value;
	}

	public Integer getIs_temp() {
		return is_temp;
	}

	public void setIs_temp(Integer is_temp) {
		this.is_temp = is_temp;
	}

	public Date getPresell_date() {
		return presell_date;
	}

	public void setPresell_date(Date presell_date) {
		this.presell_date = presell_date;
	}

	public Integer getCopy_id() {
		return copy_id;
	}

	public void setCopy_id(Integer copy_id) {
		this.copy_id = copy_id;
	}

}