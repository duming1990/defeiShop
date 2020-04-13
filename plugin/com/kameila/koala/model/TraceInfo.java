package com.kameila.koala.model;

public class TraceInfo {
	private String trace_status;
	private int trace_flag;
	private int trace_time;

	public void setTrace_status(String trace_status) {
		this.trace_status = trace_status;
	}

	public String getTrace_status() {
		return trace_status;
	}

	public void setTrace_flag(int trace_flag) {
		this.trace_flag = trace_flag;
	}

	public int getTrace_flag() {
		return trace_flag;
	}

	public void setTrace_time(int trace_time) {
		this.trace_time = trace_time;
	}

	public int getTrace_time() {
		return trace_time;
	}

	@Override
	public String toString() {
		return "TraceInfo [trace_status=" + trace_status + ", trace_flag=" + trace_flag + ", trace_time=" + trace_time
				+ "]";
	}
	
}