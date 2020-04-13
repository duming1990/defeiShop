package com.ebiz.webapp.web.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Hui,Gang
 * @version 1.0 build 2008-6-26
 */
public class ExcelReaderUtils {

	private BufferedReader reader = null;

	private String filetype;

	private InputStream is = null;

	private int currSheet = 0;

	private int currPosition;

	private int sheetCount;

	private HSSFWorkbook workbook = null;

	private static String EXCEL_LINE_DELIMITER = "#";

	// private static int MAX_EXCEL_COLUMNS = 64;

	/**
	 * @param inputfile input file dir
	 * @param currSheet
	 * @see #ExcelReaderUtils(String inputfile)
	 */

	public ExcelReaderUtils(String inputfile, int currSheet) throws IOException, Exception {
		this(inputfile);
		this.currSheet = currSheet;
	}

	/**
	 * @param inputfile input file dir
	 * @param currSheet
	 * @param currPosition begin number of rows , when use the readLine() method this param is valid
	 * @see #ExcelReaderUtils(String inputfile)
	 */

	public ExcelReaderUtils(String inputfile, int currSheet, int currPosition) throws IOException, Exception {
		this(inputfile);
		this.currSheet = currSheet;
		this.currPosition = currPosition;
	}

	/**
	 * @param inputfile input file dir
	 */

	public ExcelReaderUtils(String inputfile) throws IOException, Exception {
		if (inputfile == null || inputfile.trim().equals("")) {
			throw new IOException("no input file specified");
		}
		this.filetype = inputfile.substring(inputfile.lastIndexOf(".") + 1);

		currPosition = 0;

		is = new FileInputStream(inputfile);

		if (filetype.equalsIgnoreCase("txt")) { // is txt file
			reader = new BufferedReader(new InputStreamReader(is));
		} else if (filetype.equalsIgnoreCase("xls")) { // is excel file
			workbook = new HSSFWorkbook(is);
			sheetCount = workbook.getNumberOfSheets();
		} else {
			throw new Exception("File Type Not Supported");
		}
	}

	public boolean haxNextLine() throws IOException {
		if (filetype.equalsIgnoreCase("txt")) { // is txt file
			return false;
		}

		HSSFSheet sheet = workbook.getSheetAt(currSheet);

		if (currPosition > sheet.getLastRowNum()) { // arrived ending
			if (currSheet != sheetCount - 1) { // have next sheet
				return true;
			}
		}

		if (currPosition <= sheet.getLastRowNum()) {
			return true;
		}
		return false;
	}

	public String readLine() throws IOException {
		if (filetype.equalsIgnoreCase("txt")) {
			String str = reader.readLine();
			while (null == str || str.trim().equals("")) { // this line is empty
				str = reader.readLine(); // read next line
			}
			return str;
		} else if (filetype.equalsIgnoreCase("xls")) {
			HSSFSheet sheet = workbook.getSheetAt(currSheet);
			if (currPosition > sheet.getLastRowNum()) {
				currPosition = 0;
				while (currSheet != sheetCount - 1) {
					currSheet++;
					sheet = workbook.getSheetAt(currSheet);
					if (currPosition == sheet.getLastRowNum()) {
						continue;
					} else {
						int row = currPosition;
						currPosition++;
						return getLine(sheet, row);
					}
				}
				return null;
			}
			int row = currPosition;
			currPosition++;
			return getLine(sheet, row);
		}
		return null;
	}

	public String[] readAllLines() {
		return this.getAllLines(currSheet);
	}

	public String[] readAllLines(int currSheet) {
		return this.getAllLines(currSheet);
	}

	private String getLine(HSSFSheet sheet, int row) {
		HSSFRow rowLine = sheet.getRow(row);
		return this.getAllCells(rowLine);
	}

	/**
	 * @param currSheet
	 * @return String[]
	 */

	private String[] getAllLines(int currSheet) {
		HSSFSheet sheet = workbook.getSheetAt(currSheet);

		String[] strs = new String[sheet.getLastRowNum() + 1];
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			HSSFRow rowLine = sheet.getRow(i);
			strs[i] = this.getAllCells(rowLine);
		}

		return strs;
	}

	/**
	 * @param HSSFRow
	 * @return String the rowLine of the cells
	 */
	private String getAllCells(HSSFRow rowLine) {
		StringBuffer buffer = new StringBuffer();
		if (null == rowLine) {
			return "";
		} else {
			int cellCount = rowLine.getLastCellNum();
			for (int j = 0; j < cellCount; j++) {
				HSSFCell cell = rowLine.getCell(j);
				if (null != cell) {
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {// date type
							DateFormat df = DateFormat.getDateInstance();
							buffer.append(df.format(cell.getDateCellValue()));
						} else {
							Float num = new Float((float) cell.getNumericCellValue());
							buffer.append(num.toString());
						}
						break;

					case HSSFCell.CELL_TYPE_STRING:
						buffer.append(cell.getRichStringCellValue().toString().replaceAll("'", "''"));
						break;

					case HSSFCell.CELL_TYPE_FORMULA:
						buffer.append(cell.getNumericCellValue());
						break;

					default:
						buffer.append(" ");
					}
				} else {
					buffer.append(" ");
				}
				buffer.append(EXCEL_LINE_DELIMITER);
			}
		}
		return buffer.toString();
	}

	public void setCurrPosition(int currPosition) {
		this.currPosition = currPosition;
	}

	public int getSheetCount() {
		return sheetCount;
	}

	public void close() {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				is = null;
			}
		}
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				reader = null;
			}
		}
	}
}