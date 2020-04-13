package com.baidu.ueditor.hunter;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;

public class FileManager {

	protected final static Logger logger = LoggerFactory.getLogger(FileManager.class);

	private String dir = null;

	private String rootPath = null;

	private String contextPath = null;

	private String[] allowFiles = null;

	private int count = 0;

	private Map<String, Object> paraMy = null;

	public FileManager(Map<String, Object> conf) {

		this.rootPath = (String) conf.get("rootPath");
		this.contextPath = (String) conf.get("contextPath");
		this.dir = this.rootPath + (String) conf.get("dir");
		this.allowFiles = this.getAllowFiles(conf.get("allowFiles"));
		this.count = (Integer) conf.get("count");

		this.paraMy = (Map<String, Object>) conf.get("paraMy");

	}

	public State listFile(int index) {

		logger.info("======paraMy:{}", this.paraMy.toString());
		logger.info("======dir:{}", this.dir);
		String cur_dir = this.dir;
		if (null != paraMy) {
			String mydir = (String) paraMy.get("mydir");
			logger.info("======mydir:{}", mydir);
			if (StringUtils.isNotBlank(mydir)) {
				cur_dir += mydir + File.separator;
			}
			logger.info("======cur_dir:{}", cur_dir);
		}
		File dir = new File(cur_dir);
		State state = null;

		if (!dir.exists()) {
			return new BaseState(false, AppInfo.NOT_EXIST);
		}

		if (!dir.isDirectory()) {
			return new BaseState(false, AppInfo.NOT_DIRECTORY);
		}

		Collection<File> list = FileUtils.listFiles(dir, this.allowFiles, true);

		if ((index < 0) || (index > list.size())) {
			state = new MultiState(true);
		} else {
			Object[] fileList = Arrays.copyOfRange(list.toArray(), index, index + this.count);
			state = this.getState(fileList);
		}

		state.putInfo("start", index);
		state.putInfo("total", list.size());

		return state;

	}

	private State getState(Object[] files) {

		MultiState state = new MultiState(true);
		BaseState fileState = null;

		File file = null;

		// logger.info("======rootPath:{}", this.rootPath);
		// logger.info("======dir:{}", this.dir);
		// logger.info("======contextPath:{}", this.contextPath);
		// logger.info("===================分隔=================");

		for (Object obj : files) {
			if (obj == null) {
				break;
			}
			file = (File) obj;
			fileState = new BaseState(true);
			String file_path = this.getPath(file);
			// logger.info("======file_path:{}", file_path);
			String file_path_f = PathFormat.format(file_path);
			// logger.info("======file_path_f1:{}", file_path_f);
			file_path_f = StringUtils.replace(file_path_f, this.rootPath, "");
			if (this.contextPath.length() > 0) {
				file_path_f = this.contextPath + "/" + file_path_f;
			}
			// logger.info("======file_path_f2:{}", file_path_f);
			fileState.putInfo("url", file_path_f);
			state.addState(fileState);
		}

		return state;

	}

	private String getPath(File file) {

		String path = file.getAbsolutePath();

		return path.replace(this.rootPath, "/");

	}

	private String[] getAllowFiles(Object fileExt) {

		String[] exts = null;
		String ext = null;

		if (fileExt == null) {
			return new String[0];
		}

		exts = (String[]) fileExt;

		for (int i = 0, len = exts.length; i < len; i++) {

			ext = exts[i];
			exts[i] = ext.replace(".", "");

		}

		return exts;

	}

}
