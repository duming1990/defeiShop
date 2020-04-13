package com.ebiz.webapp.web.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

/**
 * utils of resize image and waterMark
 * 
 * @author Wu,Yang
 * @version 2013.6.9
 */
public class FtpImageUtils {

	// private static final Logger logger = LoggerFactory.getLogger(FtpImageUtils.class);

	public static void resize(String source, String fileSavePath, int maxSize) throws IOException {
		String desc = StringUtils.substringBeforeLast(source, ".") + "_"
				+ StringUtils.leftPad(String.valueOf(maxSize), 3, "0") + "." + FilenameUtils.getExtension(source);
		File sourceFile = new File(source);
		File descFile = new File(desc);
		Thumbnails.of(sourceFile).size(maxSize, maxSize).toFile(descFile);

		String uploadFile = StringUtils.substringBeforeLast(fileSavePath, ".") + "_"
				+ StringUtils.leftPad(String.valueOf(maxSize), 3, "0") + "." + FilenameUtils.getExtension(fileSavePath);

		FtpUtils.uploadFile(uploadFile, descFile);
	}

	/**
	 * 给图片添加水印
	 * 
	 * @param originalImagePath 待水印的图片路径
	 * @param waterMarkPath 水印的图片路径
	 * @param Positions 水印位置BOTTOM_CENTER,BOTTOM_LEFT,BOTTOM_RIGHT,CENTER,CENTER_LEFT,CENTER_RIGHT,TOP_CENTER ,TOP_LEFT
	 *            ,TOP_RIGHT
	 * @author Wu,Yang
	 * @version 2013.06.09
	 */
	public static void waterMark(String originalImagePath, String waterMarkPath, String positions) throws IOException {
		Positions posi = Positions.BOTTOM_RIGHT;
		if (StringUtils.isBlank(positions)) {
			positions = "BOTTOM_RIGHT";
		}
		if (StringUtils.equals("BOTTOM_CENTER", positions)) {
			posi = Positions.BOTTOM_CENTER;
		}
		if (StringUtils.equals("BOTTOM_LEFT", positions)) {
			posi = Positions.BOTTOM_LEFT;
		}
		if (StringUtils.equals("BOTTOM_RIGHT", positions)) {
			posi = Positions.BOTTOM_RIGHT;
		}
		if (StringUtils.equals("CENTER", positions)) {
			posi = Positions.CENTER;
		}
		if (StringUtils.equals("CENTER_LEFT", positions)) {
			posi = Positions.CENTER_LEFT;
		}
		if (StringUtils.equals("CENTER_RIGHT", positions)) {
			posi = Positions.CENTER_RIGHT;
		}
		if (StringUtils.equals("TOP_CENTER", positions)) {
			posi = Positions.BOTTOM_CENTER;
		}
		if (StringUtils.equals("TOP_CENTER", positions)) {
			posi = Positions.TOP_CENTER;
		}
		if (StringUtils.equals("TOP_LEFT", positions)) {
			posi = Positions.TOP_LEFT;
		}
		if (StringUtils.equals("TOP_RIGHT", positions)) {
			posi = Positions.TOP_RIGHT;
		}
		ImageIcon imgIcon = new ImageIcon(originalImagePath);
		Image theImg = imgIcon.getImage();
		int width = theImg.getWidth(null);
		int height = theImg.getHeight(null);
		File imgFile = new File(originalImagePath);
		Thumbnails.of(imgFile).size(width, height).watermark(posi, ImageIO.read(new File(waterMarkPath)), 0.25f)
				.outputQuality(1f).toFile(imgFile);
	}

	public static void resize(String source, String fileSavePath, int width, int length) throws IOException {
		String desc = StringUtils.substringBeforeLast(source, ".") + "_" + String.valueOf(width) + "x"
				+ String.valueOf(length) + "." + FilenameUtils.getExtension(source);
		File sourceFile = new File(source);
		File descFile = new File(desc);
		if (!descFile.exists()) {
			Thumbnails.of(sourceFile).size(width, length).toFile(descFile);

			String uploadFile = StringUtils.substringBeforeLast(fileSavePath, ".") + "_" + String.valueOf(width) + "x"
					+ String.valueOf(length) + "." + FilenameUtils.getExtension(fileSavePath);

			FtpUtils.uploadFile(uploadFile, descFile);
		}
	}
}
