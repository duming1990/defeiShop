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
public class ReturnsInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer order_info_details_id;

	private Integer apply_type;

	private Integer comm_id;

	private String comm_name;

	private Integer comm_count;

	private String apply_proof;

	private String wl_code;

	private String wl_comp;

	private String waybill_no;

	private String return_desc;

	private String prom_desc;

	private String return_addr;

	private Integer audit_status;

	private Integer user_id;

	private String user_name;

	private Date add_date;

	private Integer is_del;

	private Date del_date;

	private BigDecimal total_price;

	private BigDecimal return_wl_price;

	private Integer return_wl;

	private BigDecimal send_wl_price;

	private Integer send_wl;

	private Integer is_confirm;

	private String memo;

	private String sj_audit_desc;

	private Integer return_type;

	private Integer th_type;

	private Integer yhq_id;

	// 基础数据表
	private BaseData baseData;

	public BaseData getBaseData() {
		return baseData;
	}

	public void setBaseData(BaseData baseData) {
		this.baseData = baseData;
	}

	// 订单明细信息表
	private OrderInfo orderInfo;

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public List<ReturnsInfoFj> getReturnsinfofj() {
		return returnsinfofj;
	}

	public void setReturnsinfofj(List<ReturnsInfoFj> returnsinfofj) {
		this.returnsinfofj = returnsinfofj;
	}

	// 退货附件信息表
	private List<ReturnsInfoFj> returnsinfofj = new ArrayList<ReturnsInfoFj>();

	public Integer getIs_confirm() {
		return is_confirm;
	}

	public void setIs_confirm(Integer is_confirm) {
		this.is_confirm = is_confirm;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<ReturnsSwapDetail> getRsdetail() {
		return rsdetail;
	}

	public void setRsdetail(List<ReturnsSwapDetail> rsdetail) {
		this.rsdetail = rsdetail;
	}

	private List<ReturnsSwapDetail> rsdetail = new ArrayList<ReturnsSwapDetail>();

	public Integer getReturn_type() {
		return return_type;
	}

	public void setReturn_type(Integer return_type) {
		this.return_type = return_type;
	}

	public List<BaseFiles> getBaseFilesList() {
		return baseFilesList;
	}

	public void setBaseFilesList(List<BaseFiles> baseFilesList) {
		this.baseFilesList = baseFilesList;
	}

	private List<BaseFiles> baseFilesList = new ArrayList<BaseFiles>();

	public ReturnsInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrder_info_details_id() {
		return order_info_details_id;
	}

	public void setOrder_info_details_id(Integer order_info_details_id) {
		this.order_info_details_id = order_info_details_id;
	}

	public Integer getApply_type() {
		return apply_type;
	}

	public void setApply_type(Integer apply_type) {
		this.apply_type = apply_type;
	}

	public Integer getComm_id() {
		return comm_id;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}

	public String getComm_name() {
		return comm_name;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public Integer getComm_count() {
		return comm_count;
	}

	public void setComm_count(Integer comm_count) {
		this.comm_count = comm_count;
	}

	public String getApply_proof() {
		return apply_proof;
	}

	public void setApply_proof(String apply_proof) {
		this.apply_proof = apply_proof;
	}

	public String getWl_code() {
		return wl_code;
	}

	public void setWl_code(String wl_code) {
		this.wl_code = wl_code;
	}

	public String getWl_comp() {
		return wl_comp;
	}

	public void setWl_comp(String wl_comp) {
		this.wl_comp = wl_comp;
	}

	public String getWaybill_no() {
		return waybill_no;
	}

	public void setWaybill_no(String waybill_no) {
		this.waybill_no = waybill_no;
	}

	public String getReturn_desc() {
		return return_desc;
	}

	public void setReturn_desc(String return_desc) {
		this.return_desc = return_desc;
	}

	public String getProm_desc() {
		return prom_desc;
	}

	public void setProm_desc(String prom_desc) {
		this.prom_desc = prom_desc;
	}

	public String getReturn_addr() {
		return return_addr;
	}

	public void setReturn_addr(String return_addr) {
		this.return_addr = return_addr;
	}

	public Integer getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(Integer audit_status) {
		this.audit_status = audit_status;
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

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Date getDel_date() {
		return del_date;
	}

	public void setDel_date(Date del_date) {
		this.del_date = del_date;
	}

	public BigDecimal getTotal_price() {
		return total_price;
	}

	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}

	public BigDecimal getReturn_wl_price() {
		return return_wl_price;
	}

	public void setReturn_wl_price(BigDecimal return_wl_price) {
		this.return_wl_price = return_wl_price;
	}

	public Integer getReturn_wl() {
		return return_wl;
	}

	public void setReturn_wl(Integer return_wl) {
		this.return_wl = return_wl;
	}

	public BigDecimal getSend_wl_price() {
		return send_wl_price;
	}

	public void setSend_wl_price(BigDecimal send_wl_price) {
		this.send_wl_price = send_wl_price;
	}

	public Integer getSend_wl() {
		return send_wl;
	}

	public void setSend_wl(Integer send_wl) {
		this.send_wl = send_wl;
	}

	public String getSj_audit_desc() {
		return sj_audit_desc;
	}

	public void setSj_audit_desc(String sj_audit_desc) {
		this.sj_audit_desc = sj_audit_desc;
	}

	public Integer getTh_type() {
		return th_type;
	}

	public void setTh_type(Integer th_type) {
		this.th_type = th_type;
	}

	public Integer getYhq_id() {
		return yhq_id;
	}

	public void setYhq_id(Integer yhq_id) {
		this.yhq_id = yhq_id;
	}

}