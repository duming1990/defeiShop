package com.ebiz.webapp.web.servlet.uploader;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.listener.MySessionContext;
import com.ebiz.webapp.web.util.FtpImageUtils;
import com.ebiz.webapp.web.util.FtpUtils;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * @author Wu,Yang
 * @version 2010-07-02
 * @desc Cos大附件上传
 * @version 2012-04-02增加同时 ftp上传文件功能跟
 */
public class CosUploaderServlet extends HttpServlet {
	private static final long serialVersionUID = -3096800116651263134L;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String fileSizeLimit;

	public void init(ServletConfig config) throws ServletException {
		this.fileSizeLimit = config.getInitParameter("fileSizeLimit");
	}

	public void destroy() {
		this.fileSizeLimit = null;
		super.destroy();
	}

	class MyFileRenamePolicy implements FileRenamePolicy {
		public File rename(File file) {
			String fileSaveName = StringUtils.join(new String[] { java.util.UUID.randomUUID().toString(), ".",
					FilenameUtils.getExtension(file.getName()) });
			File result = new File(file.getParentFile(), fileSaveName);
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("=================== BEGIN CosUploaderServlet DOPOST=====================");

		HttpSession session = request.getSession();

		String jsessionid = request.getParameter("jsessionid");

		if (StringUtils.isNotBlank(jsessionid)) {
			session = MySessionContext.getSession(jsessionid);
		}

		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == ui) {
			// String msg = "传入的产品参数为空";
			// super.renderJavaScript(response,
			// "window.onload=function(){alert('" + msg +
			// "');history.back();}");
			return;
		}

		String upLoadIdString = null;
		if (null == ui.getOwn_entp_id()) {
			upLoadIdString = ui.getId().toString();
		} else {
			upLoadIdString = ui.getOwn_entp_id().toString();
		}
		String uploadDir = StringUtils.join(new String[] { "files", "entp", upLoadIdString, "images", "" },
				File.separator);

		// String uploadDir = "files" + File.separatorChar + "entp" +
		// File.separatorChar + ui.getOwn_entp_id()
		// + File.separatorChar;
		String autoCreatedDateDirByParttern = "yyyy" + File.separatorChar + "MM" + File.separatorChar + "dd"
				+ File.separatorChar;
		String autoCreatedDateDir = DateFormatUtils.format(new java.util.Date(), autoCreatedDateDirByParttern);
		String ctxDir = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
		if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
			ctxDir = ctxDir + File.separatorChar;
		}
		File savePath = new File(ctxDir + uploadDir + autoCreatedDateDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}

		String saveDirectory = ctxDir + uploadDir + autoCreatedDateDir;

		if (StringUtils.isBlank(this.fileSizeLimit.toString())) {
			this.fileSizeLimit = "100";// 默认100M
		}
		int maxPostSize = Integer.valueOf(this.fileSizeLimit).intValue() * 1024 * 1024;

		String encoding = "UTF-8";
		FileRenamePolicy rename = new MyFileRenamePolicy();
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, rename);
		} catch (IOException e) {
			logger.info(e.getMessage());
			return;
		}

		Enumeration files = multi.getFileNames();
		while (files.hasMoreElements()) {
			String name = (String) files.nextElement();
			File f = multi.getFile(name);
			if (f != null) {
				String fileName = multi.getFilesystemName(name);
				String lastFileName = saveDirectory + File.separatorChar + fileName;
				String fileSavePath = uploadDir + autoCreatedDateDir + fileName;
				// logger.info("CosUploaderServlet");
				// logger.info("文件地址:" + lastFileName);
				// logger.info("保存路径:" + fileSavePath);
				logger.info("File Path:{}", lastFileName);
				logger.info("Save Path:{}", fileSavePath);

				logger.info("===ctxDir:{}", ctxDir);
				logger.info("===fileSavePath:{}", fileSavePath);

				for (int v = 0; v < Keys.NEWS_INFO_IMAGE_SIZE.length; v++) {
					int maxSize = Keys.NEWS_INFO_IMAGE_SIZE[v];
					String source = ctxDir + fileSavePath;
					FtpImageUtils.resize(source, fileSavePath, maxSize);
				}
				FtpUtils.uploadFile(ctxDir + fileSavePath, new File(ctxDir + fileSavePath));

				fileSavePath = StringUtils.replace(fileSavePath, String.valueOf(File.separatorChar), "/");
				response.getWriter().write(fileSavePath);// 必须要，返回文件路径
			}
		}
		logger.info("===================END CosUploaderServlet DOPOST===================");

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public String getFileSizeLimit() {
		return fileSizeLimit;
	}

	public void setFileSizeLimit(String fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
	}

}
