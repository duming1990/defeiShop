/**
  * Copyright 2017 bejson.com 
  */
package com.kameila.koala.model;

import java.util.List;

public class OrderLogistic {

	private String kml_order_no;
	private List<ExpressTrace> express_trace;

	public void setKml_order_no(String kml_order_no) {
		this.kml_order_no = kml_order_no;
	}

	public String getKml_order_no() {
		return kml_order_no;
	}

	public void setExpress_trace(List<ExpressTrace> express_trace) {
		this.express_trace = express_trace;
	}

	public List<ExpressTrace> getExpress_trace() {
		return express_trace;
	}

	@Override
	public String toString() {
		return "OrderLogistic [kml_order_no=" + kml_order_no + ", express_trace=" + express_trace + "]";
	}

}