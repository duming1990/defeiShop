package com.ebiz.webapp.web.servlet.uploader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.FtpImageUtils;
import com.ebiz.webapp.web.util.FtpUtils;
import com.mchange.v1.util.ArrayUtils;

/**
 * @author Wu,Yang
 * @version 2010-07-02
 * @desc Cos大附件上传
 * @version 2012-04-02增加同时 ftp上传文件功能跟
 */
public class ApploaderServlet extends HttpServlet {
	private static final long serialVersionUID = -3096800116651263134L;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("=================== BEGIN UploaderServlet DOPOST=====================");

		PrintWriter out = response.getWriter();
		try {

			HttpSession session = request.getSession();

			// 文件保存目录路径
			String ctxDir = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
			if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
				ctxDir = ctxDir + File.separatorChar;
			}
			String savePath = ctxDir + "files" + String.valueOf(File.separatorChar);
			// 文件保存目录URL
			String saveUrl = "files" + String.valueOf(File.separatorChar);
			logger.info("=========request.getContextPath():{}", request.getContextPath());
			// 最大文件大小
			long maxSize = 10240000;// 5M
			response.setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(request)) {
				out.println(getError("请选择文件。"));
				return;
			}
			// 检查目录
			File uploadDir = new File(savePath);

			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				out.println(getError("上传目录没有写权限。"));
				return;
			}

			String type = request.getParameter("dir");
			String dirNameForm = request.getParameter("dirName");
			logger.info("=========type:{}", type);
			logger.info("=========dirNameForm:{}", dirNameForm);

			if (StringUtils.isBlank(type)) {
				type = "image";
			}

			// 定义允许上传的文件扩展名
			String[] fileTypes = this.setFileTypes(type);
			String filetypes_string = ArrayUtils.toString(fileTypes);
			logger.info("fileTypes_string:{}", filetypes_string);

			String dirName = StringUtils.join(new String[] { "user", "" }, File.separatorChar);
			if (StringUtils.isNotBlank(dirNameForm)) {
				dirName = StringUtils.join(new String[] { dirNameForm, "" }, File.separatorChar);
			}

			// 创建文件夹
			savePath += dirName;
			saveUrl += dirName;
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + String.valueOf(File.separatorChar);
			saveUrl += ymd + String.valueOf(File.separatorChar);
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			logger.info("==savePath:{}", savePath);

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				String fileName = item.getName();
				if (!item.isFormField()) {
					logger.info("==item.getSize():{}", item.getSize());
					// 检查文件大小
					long file_size = item.getSize();
					if (file_size > maxSize) {

						out.println(getError("上传文件大小超过限制。大小限制：" + (maxSize / 1024 / 1000) + "M。"));
						return;
					}
					// 检查扩展名
					String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();
					if (!Arrays.<String> asList(fileTypes).contains(fileExt)) {
						out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + filetypes_string + "格式。"));
						return;
					}

					// SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					// String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
					String newFileName = UUID.randomUUID().toString() + "." + fileExt;
					try {
						File uploadedFile = new File(savePath, newFileName);
						item.write(uploadedFile);
						FtpUtils.uploadFile(savePath + newFileName, uploadedFile);

						if (type.equals("image")) {// 如果是图片 尺寸压缩
							for (int v = 0; v < Keys.NEWS_INFO_IMAGE_SIZE.length; v++) {
								int maxSizeimg = Keys.NEWS_INFO_IMAGE_SIZE[v];
								String source = savePath + newFileName;
								FtpImageUtils.resize(source, saveUrl + newFileName, maxSizeimg);
							}
							String desc = StringUtils.substringBeforeLast(savePath + newFileName, ".") + "_compress"
									+ "." + FilenameUtils.getExtension(savePath + newFileName);
							File descFile = new File(desc);

							if (file_size <= 102400) {// 如果发现图片小于等于100kb不压缩
								Thumbnails.of(uploadedFile).scale(1f).toFile(descFile);// 保存一张和原图一样的
							} else {
								Thumbnails.of(uploadedFile).scale(1f).outputQuality(0.25f).toFile(descFile);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
						out.println(getError("上传文件失败:" + e.getMessage()));
						return;
					}

					JSONObject obj = new JSONObject();
					String file_real_path = saveUrl + newFileName;
					String file_path = StringUtils.replace(file_real_path, String.valueOf(File.separatorChar), "/");
					String url_min = "";
					if (type.equals("image")) {// 如果是图片则将压缩后的图片传到前台
						url_min = file_path + "@s400x400";
					}
					obj.put("ret", 1);
					obj.put("url", file_path);
					obj.put("url_min", url_min);
					out.println(obj.toString());

					// obj.put("ret", 1);
					// obj.put("file_path", file_path);
					// obj.put("file_real_path", file_real_path);
					// obj.put("file_name", fileName);
					// obj.put("file_size", file_size);
					// obj.put("file_type", fileExt);
					logger.info("==obj:{}", obj.toString());
				}
			}
		} catch (FileUploadException e1) {
			e1.printStackTrace();
			out.println(getError(e1.getMessage()));
		}

		logger.info("===================END UploaderServlet DOPOST===================");

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("ret", 0);
		obj.put("msg", message);
		return obj.toString();
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
