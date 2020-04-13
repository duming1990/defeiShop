package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2019-02-22 17:51
 */
public class ImportBi extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String index_no;

	private Date add_date;

	private Integer add_user_id;

	private String add_user_name;

	private Date audit_date;
	private Integer audit_state;

	private String audit_desc;

	private String remark;

	private Integer audit_user_id;

	private String audit_user_name;

	private BigDecimal bi_sum;

	private Integer sum_count;

	private String file_path;

	private Integer is_del;

	private List<ImportBi> importBiList = new ArrayList<ImportBi>();

	private List<ImportBiSon> importBiSonList = new ArrayList<ImportBiSon>();

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public String getIndex_no() {
		return index_no;
	}

	public void setIndex_no(String index_no) {
		this.index_no = index_no;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public List<ImportBiSon> getImportBiSonList() {
		return importBiSonList;
	}

	public void setImportBiSonList(List<ImportBiSon> importBiSonList) {
		this.importBiSonList = importBiSonList;
	}

	public List<ImportBi> getImportBiList() {
		return importBiList;
	}

	public void setImportBiList(List<ImportBi> importBiList) {
		this.importBiList = importBiList;
	}

	public ImportBi() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIndex() {
		return index_no;
	}

	public void setIndex(String index) {
		this.index_no = index;
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

	public Integer getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}

	public String getAudit_desc() {
		return audit_desc;
	}

	public void setAudit_desc(String audit_desc) {
		this.audit_desc = audit_desc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getAudit_user_id() {
		return audit_user_id;
	}

	public void setAudit_user_id(Integer audit_user_id) {
		this.audit_user_id = audit_user_id;
	}

	public String getAudit_user_name() {
		return audit_user_name;
	}

	public void setAudit_user_name(String audit_user_name) {
		this.audit_user_name = audit_user_name;
	}

	public BigDecimal getBi_sum() {
		return bi_sum;
	}

	public void setBi_sum(BigDecimal bi_sum) {
		this.bi_sum = bi_sum;
	}

	public Integer getSum_count() {
		return sum_count;
	}

	public void setSum_count(Integer sum_count) {
		this.sum_count = sum_count;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
}