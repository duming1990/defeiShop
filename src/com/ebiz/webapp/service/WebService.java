package com.ebiz.webapp.service;

import java.text.ParseException;

public interface WebService {

	public String getMails(String serKind, String serSign, String mailId) throws ParseException;

}