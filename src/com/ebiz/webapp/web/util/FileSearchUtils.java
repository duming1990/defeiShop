package com.ebiz.webapp.web.util;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jin,QingHua
 */
public class FileSearchUtils {

	@SuppressWarnings("unchecked")
	public File search(String dir, String fileNameBeSearched) {
		if (StringUtils.isBlank(fileNameBeSearched)) {
			return null;
		}

		File root = new File(dir);
		Collection<File> files = FileUtils.listFiles(root, new String[] { "doc" }, true);

		if (null == files) {
			return null;
		}

		for (File file : files) {
			if (file.getName().equals(fileNameBeSearched)) {
				return file;
			}
		}
		return null;
	}
}
