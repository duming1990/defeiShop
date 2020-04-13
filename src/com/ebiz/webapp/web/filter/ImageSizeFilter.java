package com.ebiz.webapp.web.filter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.ImageChangeSize;

/**
 * @author Liu,zhiXiang 图片尺寸过滤，根据尺寸要求创建并返回新尺寸地址：#s400x400
 */
public class ImageSizeFilter extends OncePerRequestFilter {

	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String return_url = request.getSession().getServletContext().getRealPath(request.getServletPath());
		if (return_url.toString().contains("@s")) {
			String source = return_url.toString().split("@s")[0];
			String size = return_url.toString().split("@s")[1];
			int width = Integer.valueOf(size.split("x")[0]);
			int length = Integer.valueOf(size.split("x")[1]);
			//System.out.println("********source:"+source);
			String desc = StringUtils.substringBeforeLast(source, ".") + "_"
					+ String.valueOf(width) + "x"
					+ String.valueOf(length) + "."
					+ FilenameUtils.getExtension(source);
			if(Keys.IMAGE_EXT.toLowerCase().contains(FilenameUtils.getExtension(source).toLowerCase())){//只有图片扩展名，执行以下裁剪方法
				try {
					ImageChangeSize.compressImage(source, width, length);
					//FtpImageUtils.resize(source, source, width, length);
				} catch (IOException e1) {
					desc = source;//图片裁剪异常返回原图
					e1.printStackTrace();
				}

				File file_temp = new File(desc);
				if(!file_temp.exists()){//图片不存在，返回默认图
					desc = request.getSession().getServletContext().getRealPath("")+"/styles/imagesPublic/no_image.jpg";
					String desc_temp = StringUtils.substringBeforeLast(desc, ".") + "_"
					+ String.valueOf(width) + "x"
					+ String.valueOf(length) + "."
					+ FilenameUtils.getExtension(desc);
					try {
						ImageChangeSize.compressImage(desc, width, length);
						//FtpImageUtils.resize(desc, desc, width, length);
						desc = desc_temp;//切图成功，将新尺寸文件返回
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				// 得到图片
				BufferedImage src = ImageChangeSize.InputImage(source);
				if (null != src) {
					// 得到源图宽
					int old_w = src.getWidth();
					// 得到源图长
					int old_h = src.getHeight();
					
					//如果裁剪尺寸与原图一致，则返回原图
					if(width==old_w&&length==old_h){
						desc = source;
					}
				}
				
				FileInputStream fis = null;
				response.setContentType("image/jped");
				try {
					OutputStream out = response.getOutputStream();
					File file = new File(desc);
					fis = new FileInputStream(file);
					byte[] b = new byte[fis.available()];
					fis.read(b);
					out.write(b);
					out.flush();
					return;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}else if(return_url.toString().endsWith("@compress")){
			String source = return_url.toString().split("@compress")[0];
			
			if(Keys.IMAGE_EXT.toLowerCase().contains(FilenameUtils.getExtension(source).toLowerCase())){//只有图片扩展名，执行以下裁剪方法
				String desc = StringUtils.substringBeforeLast(source, ".") + "_compress"
						+ "." + FilenameUtils.getExtension(source);
				
				File file_source = new File(source);
				File file_temp = new File(desc);
				if(!file_temp.exists()){//图片不存在，返回默认图
					try {
						long file_size = file_source.length();//原图大小byte
						if (file_size <= 102400) {// 如果发现图片小于等于100kb不压缩
							Thumbnails.of(file_source).scale(1f).toFile(file_temp);
						}else{
							Thumbnails.of(file_source).scale(1f).outputQuality(0.25f).toFile(file_temp);
							//FtpImageUtils.resize(desc, desc, width, length);
						} 
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				FileInputStream fis = null;
				response.setContentType("image/jped");
				try {
					OutputStream out = response.getOutputStream();
					File file = new File(desc);
					fis = new FileInputStream(file);
					byte[] b = new byte[fis.available()];
					fis.read(b);
					out.write(b);
					out.flush();
					return;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}

		chain.doFilter(request, response);
	}

}
