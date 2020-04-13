package com.baidu.ueditor.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.ebiz.webapp.web.util.FtpUtils;

public class BinaryUploader {
	protected final static Logger logger = LoggerFactory.getLogger(BinaryUploader.class);

	public static final State save(HttpServletRequest request, Map<String, Object> conf) {

		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField()) {
					break;
				}
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("savePath");
			logger.info("======savePath:{}", savePath);
			String mydir = request.getParameter("mydir");
			logger.info("======mydir:{}", mydir);// 在这里设置上传的目录
			if (StringUtils.isNotBlank(mydir)) {
				savePath = StringUtils.replace(savePath, "mydir", mydir);
			} else {
				savePath = StringUtils.replace(savePath, "mydir/", "");
			}
			logger.info("======savePath:{}", savePath);
			String originFileName = fileStream.getName();
			logger.info("======originFileName:{}", originFileName);
			String suffix = FileType.getSuffixByFilename(originFileName);
			originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			logger.info("======savePath:{}", savePath);

			String physicalPath = (String) conf.get("rootPath") + savePath;

			InputStream is = fileStream.openStream();
			State storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
			is.close();

			if (storageState.isSuccess()) {

				// resize image
				String ctxDir = request.getSession().getServletContext()
						.getRealPath(String.valueOf(File.separatorChar));
				if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
					ctxDir = ctxDir + File.separatorChar;
				}
				String fileName = fileStream.getName();
				String lf = StringUtils.lowerCase(FilenameUtils.getExtension(fileName));
				String imgtype = "jpg,jpeg,png,gif";
				if (StringUtils.contains(imgtype, lf)) {
					// // resizeByMaxSize
					// for (int mSize : Keys.NEWS_INFO_IMAGE_SIZE) {
					// String source = physicalPath;
					// FtpImageUtils.resize(source, savePath, mSize);
					// }
					// 水印
					// String watermarkPath = "watermark".concat(File.separator).concat("watermark.png");
					// FtpImageUtils.waterMark(physicalPath, ctxDir + watermarkPath, null);

				}
				FtpUtils.uploadFile(ctxDir.substring(0,ctxDir.length()-1) + savePath, new File(ctxDir.substring(0,ctxDir.length()-1) + savePath));

				String contextPath = request.getContextPath();
				if (contextPath.length() > 0) {
					storageState.putInfo("url", contextPath + PathFormat.format(savePath));
				} else {
					storageState.putInfo("url", PathFormat.format(savePath));
				}
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
