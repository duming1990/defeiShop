package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2019-02-23 15:20
 */
public class ImportBiSon extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer id;

    private Integer link_id;

    private String mobile;

    private BigDecimal bi_no;

    private Integer user_id;

    private String user_name;

    private Integer audit_state;

    private Date add_date;

    private Date audit_date;

    private Integer is_del;

    private Integer add_user_id;

    private String import_user_name;

    public String getImport_user_name() {
        return import_user_name;
    }

    public void setImport_user_name(String import_user_name) {
        this.import_user_name = import_user_name;
    }

    public Integer getAdd_user_id() {
        return add_user_id;
    }

    public void setAdd_user_id(Integer add_user_id) {
        this.add_user_id = add_user_id;
    }

    public ImportBiSon() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLink_id() {
        return link_id;
    }

    public void setLink_id(Integer link_id) {
        this.link_id = link_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getBi_no() {
        return bi_no;
    }

    public void setBi_no(BigDecimal bi_no) {
        this.bi_no = bi_no;
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

    public Integer getAudit_state() {
        return audit_state;
    }

    public void setAudit_state(Integer audit_state) {
        this.audit_state = audit_state;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public Date getAudit_date() {
        return audit_date;
    }

    public void setAudit_date(Date audit_date) {
        this.audit_date = audit_date;
    }

    public Integer getIs_del() {
        return is_del;
    }

    public void setIs_del(Integer is_del) {
        this.is_del = is_del;
    }
}