package com.ebiz.webapp.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 */
public class ZipUtils {

	public static void zipFile(File[] subs, String baseName, ZipOutputStream zos) throws IOException {

		for (int i = 0; i < subs.length; i++) {
			File f = subs[i];
			if (null != f) {
				zos.putNextEntry(new ZipEntry(baseName + f.getName()));
				FileInputStream fis = new FileInputStream(f);
				byte[] buffer = new byte[1024];
				int r = 0;
				while ((r = fis.read(buffer)) != -1) {
					zos.write(buffer, 0, r);
				}
			}
		}
		zos.flush();
		zos.close();
	}

	public static void zipFile(File[] subs, String[] baseName, ZipOutputStream zos) throws IOException {

		for (int i = 0; i < subs.length; i++) {
			File f = subs[i];
			if (null != f) {
				zos.putNextEntry(new ZipEntry(baseName[i]));
				FileInputStream fis = new FileInputStream(f);
				byte[] buffer = new byte[1024];
				int r = 0;
				while ((r = fis.read(buffer)) != -1) {
					zos.write(buffer, 0, r);
				}
			}
		}
		zos.flush();
		zos.close();
	}

}
