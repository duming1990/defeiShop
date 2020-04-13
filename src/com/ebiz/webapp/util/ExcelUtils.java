package com.ebiz.webapp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Wu,Yang
 * @version 2012-7-6
 * @use : String[] rowLines = ExcelUtils.readExcelRowLines("文件路径", 0, 0); for
 *      (int i = 0; i < rowLines.length; i++) {
 *      logger.info("====rowLines======:{}", rowLines[i]); if
 *      (StringUtils.isNotBlank(rowLines[i])) { for (int i = 0; i <
 *      rowLines.length; i++) { String values[] = StringUtils.split(rowLines[i],
 *      ExcelUtils.EXCEL_LINE_DELIMITER); } } }
 */

public class ExcelUtils {

	public static String EXCEL_LINE_DELIMITER = "卍卐";

	public static String[] readExcelRowLines(String filePath, int curSheet) throws IOException {
		return readExcelRowLines(filePath, curSheet, 0);
	}

	/**
	 * @param filePath
	 *            excel文件路径
	 * @param curSheet
	 *            excel的sheet index, 从0开始
	 * @param startRow
	 *            读取exce的 开始行数,从0开始
	 */
	public static String[] readExcelRowLines(String filePath, int curSheet, int startRow) throws IOException {
		FileInputStream isFile = new FileInputStream(filePath);
		String filetype = filePath.substring(filePath.lastIndexOf(".") + 1);
		if (!filetype.equalsIgnoreCase("xls")) { return null; }
		HSSFWorkbook workbook = new HSSFWorkbook(isFile);
		HSSFSheet sheet = workbook.getSheetAt(curSheet);

		int rowNum = sheet.getLastRowNum() + 1;//
		if (startRow > rowNum) {
			startRow = rowNum - 1;
		}
		if (rowNum == 0) { return null; }
		String[] rowLines = new String[rowNum];
		for (int i = startRow; i < rowNum; i++) {
			rowLines[i] = getExcelCells(sheet.getRow(i));
		}
		return rowLines;
	}

	private static String getExcelCells(HSSFRow rowLine) {
		StringBuffer buffer = new StringBuffer();
		if (null == rowLine) { return ""; }
		int cellCount = rowLine.getLastCellNum();
		for (int j = 0; j < cellCount; j++) {
			HSSFCell cell = rowLine.getCell(j);
			if (null != cell) {
				switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC:
						// System.out.println("==cell.getCellType()==" +
						// cell.getCellType());

						if (HSSFDateUtil.isCellDateFormatted(cell)) {// date
																		// type
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							buffer.append(df.format(cell.getDateCellValue())).append(EXCEL_LINE_DELIMITER);
						} else {
							// Float num = new Float((float)
							// cell.getNumericCellValue());
							BigDecimal num = new BigDecimal(cell.getNumericCellValue());
							// System.out.println("==cell.getNumericCellValue()=="
							// + cell.getNumericCellValue());
							buffer.append(num.toString()).append(EXCEL_LINE_DELIMITER);
						}
						break;

					case HSSFCell.CELL_TYPE_STRING:
						if (StringUtils.isBlank(cell.getRichStringCellValue().toString())) {
							buffer.append(" ").append(EXCEL_LINE_DELIMITER);
						} else
							buffer.append(cell.getRichStringCellValue().toString().replaceAll("'", "''")).append(
									EXCEL_LINE_DELIMITER);
						break;

					case HSSFCell.CELL_TYPE_FORMULA:
						buffer.append(cell.getNumericCellValue()).append(EXCEL_LINE_DELIMITER);
						break;

					default:
						buffer.append(" ").append(EXCEL_LINE_DELIMITER);
						break;
				}
			} else {
				buffer.append(" ").append(EXCEL_LINE_DELIMITER);
			}
		}
		// System.out.println("==excel line==" + buffer.toString());
		return buffer.toString();
	}
}