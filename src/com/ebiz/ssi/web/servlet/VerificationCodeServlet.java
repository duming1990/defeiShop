package com.ebiz.ssi.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HttpServletBean;

import com.ebiz.webapp.web.Keys;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class VerificationCodeServlet extends HttpServletBean {
	private static final long serialVersionUID = -6099394048549869091L;

	private int codeType = 1;

	private int imgWidth = 105;

	private int imgHeight = 35;

	private String fontName = "Arial Black";

	private int fontStyle = 0;

	private int fontSize = 28;

	private String sessionKey = Keys.VERIFICATION_CODE;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		response.setHeader("Cache-Control", "pre-check=0,post-check=0");
		response.setDateHeader("Expires", 0L);

		BufferedImage image = new BufferedImage(this.imgWidth, this.imgHeight, 1);
		Random random = new Random();
		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.imgWidth, this.imgHeight);

		for (int i = 0; i < 155; i++) {
			g.setColor(new Color(220 + random.nextInt(30), 150 + random.nextInt(75), 150 + random.nextInt(75)));
			int x = random.nextInt(this.imgWidth);
			int y = random.nextInt(this.imgHeight);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String sRand = "";
		Font font = new Font(this.fontName, this.fontStyle, this.fontSize);
		g.setFont(font);
		sRand = getVerificationCode(4, this.codeType);

		for (int i = 0; i < 4; i++) {
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(String.valueOf(sRand.charAt(i)), ((fontSize - 3) * i) + 6, fontSize); // 字体间距控制
		}

		HttpSession session = request.getSession(true);
		session.setAttribute(this.sessionKey, sRand);
		// String veri_code_session = (String) request.getSession().getAttribute(Keys.VERIFICATION_CODE);
		// System.out.println("set seesion veri:" + veri_code_session);

		g.dispose();

		ServletOutputStream sos = response.getOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
		encoder.encode(image);
	}

	private String getVerificationCode(int size, int type) {
		StringBuffer verificationCode = new StringBuffer();
		for (int i = 0; i < size; i++) {
			verificationCode.append(getOneChar(type));
		}
		return verificationCode.toString();
	}

	private String getOneChar(int codeType) {
		Random random = new Random();
		switch (codeType) {
		case 1:
			return String.valueOf(random.nextInt(10));
		case 2:
			return String.valueOf((char) (random.nextInt(26) + 65));
		case 3:
			if (random.nextBoolean()) {
				return String.valueOf(random.nextInt(10));
			}
			return String.valueOf((char) (random.nextInt(26) + 65));
		}

		throw new IllegalArgumentException("code type is error!");
	}

	public int getCodeType() {
		return this.codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public int getImgWidth() {
		return this.imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return this.imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}

	public String getFontName() {
		return this.fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int getFontStyle() {
		return this.fontStyle;
	}

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}

	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getSessionKey() {
		return this.sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
}