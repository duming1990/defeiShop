package com.ebiz.webapp.web.servlet.uploader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.web.util.FtpUtils;
import com.mchange.v1.util.ArrayUtils;

/**
 * @author Wu,Yang
 * @version 2010-6-4
 * @desc KindEditor编辑器，上传图片
 * @version 2012-04-02增加同时 ftp上传文件功能跟
 */
public class KindEditorJsonUploaderServlent extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("=================== BEGIN KindEditorJsonUploaderServlent DOPOST=====================");
		HttpSession session = request.getSession();
		String type = request.getParameter("dir");
		logger.info("=========dir:{}", type);
		// 图片image swf flash media file
		// 文件保存目录路径
		String uploadDirLocal = "files" + File.separatorChar + "upload" + File.separatorChar;
		String autoCreatedDateDirByParttern = "yyyy" + File.separatorChar + "MM" + File.separatorChar + "dd"
				+ File.separatorChar;
		String autoCreatedDateDir = DateFormatUtils.format(new java.util.Date(), autoCreatedDateDirByParttern);
		String ctxDir = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
		if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
			ctxDir = ctxDir + File.separatorChar;
		}
		String savePath = ctxDir + uploadDirLocal + autoCreatedDateDir;

		// 文件保存目录URL
		String saveUrl = request.getContextPath() + String.valueOf(File.separatorChar) + uploadDirLocal
				+ autoCreatedDateDir;
		// 定义允许上传的文件扩展名
		String[] fileTypes = this.setFileTypes(type);
		logger.info("fileTypes:{}", ArrayUtils.toString(fileTypes));
		// 最大文件大小
		long maxSize = 10000000;

		response.setContentType("text/html; charset=UTF-8");

		if (!ServletFileUpload.isMultipartContent(request)) {
			response.getWriter().println(getError("请选择文件。"));
			return;
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			response.getWriter().println(getError("上传目录没有写权限。"));
			return;
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		try {
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				String fileName = item.getName();
				// long fileSize = item.getSize();
				if (!item.isFormField()) {
					// 检查文件大小
					if (item.getSize() > maxSize) {
						response.getWriter().println(getError("上传文件大小超过限制。"));
						return;
					}
					// 检查扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if (!Arrays.<String> asList(fileTypes).contains(fileExt)) {
						response.getWriter().println(getError("上传文件扩展名是不允许的扩展名。"));
						return;
					}
					String newFileName = UUID.randomUUID().toString() + "." + fileExt;
					try {
						File uploadedFile = new File(savePath, newFileName);
						item.write(uploadedFile);

						// ftpClientTemplate.storeFile(fileSavePath, new File(ctxDir + fileSavePath));
						String fileSavePath = uploadDirLocal + autoCreatedDateDir + newFileName;

						logger.info("===ctxDir:{}", ctxDir);
						logger.info("===fileSavePath:{}", fileSavePath);

						if (item.getContentType().indexOf("image/") != -1) {
							// 水印
							// String watermarkPath = "watermark".concat(File.separator).concat("watermark.png");
							// FtpImageUtils.waterMark(ctxDir + fileSavePath, ctxDir + watermarkPath, null);
						}

						FtpUtils.uploadFile(ctxDir + fileSavePath, new File(ctxDir + fileSavePath));

					} catch (Exception e) {
						response.getWriter().println(getError("上传文件失败。"));
						return;
					}

					JSONObject obj = new JSONObject();
					obj.put("error", 0);
					obj.put("url", saveUrl + newFileName);
					response.getWriter().println(obj.toJSONString());
				}
			}
		} catch (FileUploadException e1) {

			e1.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}

	private String[] setFileTypes(String type) {
		// 图片image flash文件 flash 音频 media 文件file
		String[] fileTypes = null;
		if (StringUtils.isBlank(type)) {
			type = "image";
		}
		if ("image".equalsIgnoreCase(type)) {
			fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp", "ico" };
		}
		if ("flash".equalsIgnoreCase(type)) {
			fileTypes = new String[] { "swf", "fla" };
		}
		if ("media".equalsIgnoreCase(type)) {
			fileTypes = new String[] { "mp3", "wma" };
		}
		if ("file".equalsIgnoreCase(type)) {
			// fileTypes = new String[] { "doc", "docx", "xlsx", "xls", "txt" };
			fileTypes = new String[] { "7z", "aiff", "asf", "avi", "bmp", "csv", "doc", "fla", "flv", "gif", "gz",
					"gzip", "jpeg", "jpg", "mid", "mov", "mp3", "mp4", "mpc", "mpeg", "mpg", "ods", "odt", "pdf",
					"png", "ppt", "pxd", "qt", "ram", "rar", "rm", "rmi", "rmvb", "rtf", "sdc", "sitd", "swf", "sxc",
					"sxw", "tar", "tgz", "tif", "tiff", "txt", "vsd", "wav", "wma", "wmv", "xls", "xml", "zip" };
		}
		return fileTypes;
	}
}
