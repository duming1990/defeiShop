package com.ebiz.webapp.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Wu,Yang
 * @version 2012-04-01
 * @desc FTP上传文件类工具
 */
public class FtpUtils {
	private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

	private static boolean isEnabledFtpUpload = Boolean.valueOf(
			FtpPropertiesLoader.getProperty("ftp.isenabledftpupload")).booleanValue();// required

	private static String server = FtpPropertiesLoader.getProperty("ftp.server");;// required

	private static String username = FtpPropertiesLoader.getProperty("ftp.username"); // required

	private static String password = FtpPropertiesLoader.getProperty("ftp.password"); // required

	private static int port = Integer.valueOf(FtpPropertiesLoader.getProperty("ftp.port")).intValue();// optional

	private static final String DEFAULT_ENCODER = "UTF-8";

	public static boolean uploadFile(final String remote, final File file) throws IOException {
		return uploadFile(remote, new FileInputStream(file));
	}

	public static boolean uploadFile(final String remote, final InputStream is) throws IOException {
		if (isEnabledFtpUpload) {
			logger.info("====开启FTP服务器上传功能====");
			boolean success = false; // 初始表示上传失败
			String ftp_remote = remote.replace(File.separator, "/");
			String filename = StringUtils.substringAfterLast(ftp_remote, "/");
			String dir = StringUtils.substringBeforeLast(ftp_remote, "/");

			FTPClient ftp = new FTPClient();// 创建FTPClient对象
			try {
				ftp.setControlEncoding(DEFAULT_ENCODER);
				FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
				conf.setServerLanguageCode("zh");
				// 连接FTP服务器
				// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
				ftp.connect(server, port);
				ftp.enterLocalActiveMode(); //主动模式：ftp服务器开放20端口传输数据
				// ftp.enterLocalPassiveMode(); //被动模式：ftp服务器开发随机端口传输数据，我们不要使用被动模式
				// 登录ftp
				ftp.login(username, password);

				int reply = ftp.getReplyCode();// 看返回的值是不是230，如果是，表示登陆成功
				logger.info("====ftp.getReplyCode()===="+reply);
				if (!FTPReply.isPositiveCompletion(reply)) {// 以2开头的返回值就会为真
					ftp.disconnect();
					return success;
				}
				if (!ftp.changeWorkingDirectory(dir)) {
					forceMkdir(dir, ftp);
					ftp.changeWorkingDirectory(dir);
				}

				ftp.setFileType(FTP.BINARY_FILE_TYPE);// 上传类型 二进制
				// 还有ASCII_FILE_TYPE类型，区别在于ASCII_FILE_TYPE类型上传时，会对文件中的回车换行符做些必要的格式化

				ftp.storeFile(filename, is);// 将上传文件存储到指定目录

				is.close();// 关闭输入流

				ftp.logout();// 退出ftp

				success = true;// 表示上传成功
				logger.info("====FTP服务器上传文件成功,dir:[{}] name:[{}]", dir, filename);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("====ftp上传失败===="+e.getMessage());
			} finally {
				if (ftp.isConnected()) {
					try {
						ftp.disconnect();
					} catch (IOException ioe) {
					}
				}
			}
			return success;
		} else {// 不开启FTP服务器上传功能
			logger.error("====不开启FTP服务器上传功能====");
		}
		return false;

	}

	public static boolean forceMkdir(String remote, FTPClient ftpClient) throws IOException {
		if (!StringUtils.endsWith(remote, "/")) {
			remote += "/";
		}
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(directory)) {
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = remote.substring(start, end);
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						return false;
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);

				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return true;
	}

}
