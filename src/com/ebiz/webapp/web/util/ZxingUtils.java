package com.ebiz.webapp.web.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 利用zxing开源工具生成二维码QRCode
 * 
 * @version 2.2及以前 支持jdk6
 * @version 2.3及以后 支持jdk7
 */
public class ZxingUtils {

	private static final Logger logger = LoggerFactory.getLogger(ZxingUtils.class);

	private static final String CODE = "utf-8";

	private static final int BLACK = 0xff000000;

	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String file1 = ("F:\\test1.png");
		ZxingUtils.encodeQrcode("hello, welcome to :http://www.baidu.com", "F:\\test1.png", "F:\\logo.jpg");
		String file2 = ("F:\\Barcode.png");
		try {
			ZxingUtils.encodeBarcode("1234567890", file2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String ret1 = ZxingUtils.decode(file1);
		logger.info("ret1:{}", ret1);
		String ret2 = ZxingUtils.decode(file2);
		logger.info("ret2:{}", ret2);

	}

	public static void encodeQrcode(String contents, String file_path, String waterMarkPath) {
		encodeQrcode(contents, file_path, 200, 200, waterMarkPath);
	}

	public static void encodeQrcode(String contents, String file, int width, int height, String waterMarkPath) {
		encode(contents, file, BarcodeFormat.QR_CODE, width, height, waterMarkPath);
	}

	/**
	 * 生成一维码，写到文件中
	 * 
	 * @author wuhongbo
	 * @param str
	 * @param height
	 * @param file
	 * @throws IOException
	 */
	public static void encodeBarcode(String contents, String file) throws IOException {
		// BufferedImage image = getBarcode(str, width, height);
		// ImageIO.write(image, "png", file);
		encodeBarcode(contents, 200, 100, file);
	}

	public static void encodeBarcode(String contents, Integer width, Integer height, String file) throws IOException {
		// BufferedImage image = getBarcode(str, width, height);
		// ImageIO.write(image, "png", file);
		encode(contents, file, BarcodeFormat.CODE_128, width, height, null);
	}

	public static BufferedImage encodeQrcodeBufferedImage(String contents, int width, int height) {
		return encodeBufferedImage(contents, BarcodeFormat.QR_CODE, width, height);
	}

	public static BufferedImage encodeBufferedImage(String contents, BarcodeFormat format, int width, int height) {
		try {
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			// 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			// 内容所使用字符集编码
			hints.put(EncodeHintType.CHARACTER_SET, CODE);
			// hints.put(EncodeHintType.MAX_SIZE, 350);//设置图片的最大值
			// hints.put(EncodeHintType.MIN_SIZE, 100);//设置图片的最小值
			hints.put(EncodeHintType.MARGIN, 1);// 设置二维码边的空度，非负数
			BitMatrix matrix = new MultiFormatWriter().encode(contents, format, width, height, hints);
			int width_n = matrix.getWidth();
			int height_n = matrix.getHeight();
			// 创建一张bitmap图片，采用图片效果TYPE_INT_ARGB
			BufferedImage image = new BufferedImage(width_n, height_n, BufferedImage.TYPE_INT_ARGB);
			for (int x = 0; x < width_n; x++) {
				for (int y = 0; y < height_n; y++) {
					image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
				}
			}
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成QRCode二维和一维码<br>
	 * 在编码时需要将com.google.zxing.qrcode.encoder.Encoder.java中的<br>
	 * static final String DEFAULT_BYTE_MODE_ENCODING = "ISO8859-1";<br>
	 * 修改为UTF-8，否则中文编译后解析不了<br>
	 */
	public static void encode(String contents, String file, BarcodeFormat format, int width, int height,
			String waterMarkPath) {
		try {
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			// 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			// 内容所使用字符集编码
			hints.put(EncodeHintType.CHARACTER_SET, CODE);
			// hints.put(EncodeHintType.MAX_SIZE, 350);//设置图片的最大值
			// hints.put(EncodeHintType.MIN_SIZE, 100);//设置图片的最小值
			hints.put(EncodeHintType.MARGIN, 1);// 设置二维码边的空度，非负数
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height, hints);
			writeToFile(bitMatrix, "png", new File(file));

			// 生成二维码QRCode图片

			if (StringUtils.isNotBlank(waterMarkPath)) {
				Positions posi = Positions.CENTER;
				Thumbnails.of(file).size(width, height).watermark(posi, ImageIO.read(new File(waterMarkPath)), 1f)
						.outputQuality(1f).toFile(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成二维码图片<br>
	 * 
	 * @param matrix
	 * @param format 图片格式
	 * @param file 生成二维码图片位置
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		ImageIO.write(image, format, file);
	}

	/**
	 * 生成二维码内容<br>
	 * 
	 * @param matrix
	 * @return
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 创建一张bitmap图片，采用图片效果TYPE_INT_ARGB
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
			}
		}
		return image;
	}

	/**
	 * 解析QRCode二维码
	 */
	public static String decode(String file) {
		try {
			BufferedImage image;
			try {
				image = ImageIO.read(new File(file));
				if (image == null) {
					logger.warn("Could not decode image");
				}
				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
				Result result;
				Hashtable hints = new Hashtable();
				// 解码设置编码方式为：utf-8
				hints.put(DecodeHintType.CHARACTER_SET, CODE);
				result = new MultiFormatReader().decode(bitmap, hints);
				String resultStr = result.getText();
				// logger.warn("解析后内容：" + resultStr);
				return resultStr;
			} catch (IOException ioe) {
				logger.warn(ioe.toString());
			} catch (ReaderException re) {
				logger.warn(re.toString());
			}
		} catch (Exception ex) {
			logger.warn(ex.toString());
		}
		return null;
	}

}
