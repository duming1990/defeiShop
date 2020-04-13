package com.kameila.koala.sdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

public class DefaultSign {
	
	public String genSignbefore(TreeMap<String, String> signMap, String appSec) {
		List<String> names = new ArrayList<String>();
		names.addAll(signMap.keySet());
        //降序排序
        Collections.sort(names);
        
        StringBuilder signBuilder = new StringBuilder(appSec);
        for(String key:names){
        	signBuilder.append(key).append(signMap.get(key));
        }
        signBuilder.append(appSec);
		return signBuilder.toString();
	}

	public String sign(String str) {
		return DigestUtils.md5Hex(str);
	}

}
