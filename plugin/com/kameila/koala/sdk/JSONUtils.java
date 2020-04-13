package com.kameila.koala.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author lindezhi
 * 2016年5月28日 上午10:18:43
 */
public class JSONUtils {

	protected static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);
	
	private static ObjectMapper objectMapper;
	static {
		objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	};
	
	public static ObjectMapper getJsonMapper() {
		return objectMapper;
	}

	public static String toJSON(Object obj) {
		try {
			return getJsonMapper().writeValueAsString(obj);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public static <T> T fromJSON(String json, Class<T> clz) {
		try {
			return getJsonMapper().readValue(json, clz);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public static <T> T fromJSON(String json, TypeReference<T> typeReference) {
		try {
			return getJsonMapper().readValue(json, typeReference);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}
