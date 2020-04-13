package com.kameila.koala.model;

import java.util.List;

public class ExpressTrace {
	private int isSupport;
	private int express_region;
	private String express_status;
	private String express_name;
	private String express_phone;
	private String express_number;
	private List<TraceInfo> traceInfos;
	private List<TraceInfo> traceBeforeInfos;
	private int express_id;
	private String express_website;

	public void setIsSupport(int isSupport) {
		this.isSupport = isSupport;
	}

	public int getIsSupport() {
		return isSupport;
	}

	public void setExpress_region(int express_region) {
		this.express_region = express_region;
	}

	public int getExpress_region() {
		return express_region;
	}

	public void setExpress_status(String express_status) {
		this.express_status = express_status;
	}

	public String getExpress_status() {
		return express_status;
	}

	public void setExpress_name(String express_name) {
		this.express_name = express_name;
	}

	public String getExpress_name() {
		return express_name;
	}

	public void setExpress_phone(String express_phone) {
		this.express_phone = express_phone;
	}

	public String getExpress_phone() {
		return express_phone;
	}

	public void setExpress_number(String express_number) {
		this.express_number = express_number;
	}

	public String getExpress_number() {
		return express_number;
	}

	public void setTraceInfos(List<TraceInfo> traceInfos) {
		this.traceInfos = traceInfos;
	}

	public List<TraceInfo> getTraceInfos() {
		return traceInfos;
	}

	public void setTraceBeforeInfos(List<TraceInfo> traceBeforeInfos) {
		this.traceBeforeInfos = traceBeforeInfos;
	}

	public List<TraceInfo> getTraceBeforeInfos() {
		return traceBeforeInfos;
	}

	public void setExpress_id(int express_id) {
		this.express_id = express_id;
	}

	public int getExpress_id() {
		return express_id;
	}

	public void setExpress_website(String express_website) {
		this.express_website = express_website;
	}

	public String getExpress_website() {
		return express_website;
	}

	@Override
	public String toString() {
		return "ExpressTrace [isSupport=" + isSupport + ", express_region=" + express_region + ", express_status="
				+ express_status + ", express_name=" + express_name + ", express_phone=" + express_phone
				+ ", express_number=" + express_number + ", traceInfos=" + traceInfos + ", traceBeforeInfos="
				+ traceBeforeInfos + ", express_id=" + express_id + ", express_website=" + express_website + "]";
	}
	
}