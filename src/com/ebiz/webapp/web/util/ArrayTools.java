package com.ebiz.webapp.web.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Wu,Yang
 * @version 2012-02-03
 * @desc 去除重复的List元素
 */
public class ArrayTools {
	protected static final Logger logger = LoggerFactory.getLogger(ArrayTools.class);

	private static final char SEPARATOR = '|';

	/**
	 * Remove the duplicate element in List according to the specified keys in List bean and return a new list.</br> If
	 * the parameters are empty or exception occurred, original list will be returned.
	 * 
	 * @param list To be processed list
	 * @param keys The fields in List bean as keys
	 * @return
	 * @use List<TestBean> list2 = removeDuplication(list, "field1", "field3");
	 */
	public static <T> List<T> removeDuplication(List<T> list, String... keys) {
		if (list == null || list.isEmpty()) {
			logger.info("List is empty.");
			return list;
		}

		if (keys == null || keys.length < 1) {
			logger.info("Missing parameters.");
			return list;
		}

		for (String key : keys) {
			if (StringUtils.isBlank(key)) {
				logger.info("Key is empty.");
				return list;
			}
		}

		List<T> newList = new ArrayList<T>();
		Set<String> keySet = new HashSet<String>();

		for (T t : list) {
			StringBuffer logicKey = new StringBuffer();
			for (String keyField : keys) {
				try {
					logicKey.append(BeanUtils.getProperty(t, keyField));
					logicKey.append(SEPARATOR);
				} catch (Exception e) {
					e.printStackTrace();
					return list;
				}
			}
			if (!keySet.contains(logicKey.toString())) {
				keySet.add(logicKey.toString());
				newList.add(t);
			} else {
				logger.info(logicKey + " has duplicated.");
			}
		}
		return newList;
	}

}
