package com.ebiz.webapp.web.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpPropertiesLoader {

	private static final Logger logger = LoggerFactory.getLogger(FtpPropertiesLoader.class);

	private static Properties properties = new Properties();

	static {
		InputStream in2 = FtpPropertiesLoader.class.getResourceAsStream("/ftp.properties");

		if (in2 == null) {
			logger.info("===ftp.properties not found===");
		} else {
			if (!(in2 instanceof BufferedInputStream))
				in2 = new BufferedInputStream(in2);
			try {
				properties.load(in2);
				in2.close();
				logger.info("===ftp.properties loaded===");
			} catch (Exception e) {
				logger.error("Error while processing ftp.properties");
				throw new RuntimeException("Error while processing ftp.properties", e);
			}
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
}
