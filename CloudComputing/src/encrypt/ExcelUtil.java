package encrypt;

import java.io.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {
	private String path;
	private Workbook workbook;
	private Sheet sheet;

	public ExcelUtil(String path) {
		this.path = path;
		InputStream is = null;
		try {
			is = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (is != null) {
			String fileType = path.substring(path.lastIndexOf(".") + 1);
			if (fileType.equals("xls")) {
				//以.xls结尾的Excel文件
				try {
					workbook = new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (fileType.equals("xlsx")) {
				//以.xlsx结尾的Excel文件
				try {
					workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (workbook != null) {
				sheet = workbook.getSheetAt(0);
			}
		}
	}

	/**
	 * 获取总行数
	 * @return	Excel表中总行数
	 */
	public int getRowNum() {
		return sheet.getLastRowNum() + 1;
	}

	/**
	 * 读取Excel单元格内容
	 * @param row	行号
	 * @param col	列号
	 * @return	单元格内容
	 */
	public String readCellValue(int row, int col) {
		Row readRow = null;
		if (sheet != null) {
			readRow = sheet.getRow(row);
		} else {
			return null;
		}
		String value = null;

		int type = readRow.getCell(col).getCellType();
		if (type == 0) {
			//单元格内容为数字
			double result = readRow.getCell(col).getNumericCellValue();
			value = new Double(result).toString();
		} else if (type == 1) {
			//单元格内容为字符串
			value = readRow.getCell(col).getStringCellValue();
		}
		return value;
	}

	/**
	 * 对Excel内容进行修改
	 * @param info	修改内容
	 * @param row	行号
	 * @param col	列号
	 */
	public void writeCellValue(String info, int row, int col) {
		Row writeRow = null;
		if (sheet != null) {
			writeRow = sheet.getRow(row);
		} else {
			return;
		}
		Cell cell = writeRow.getCell(col);
		cell.setCellValue(info);

		OutputStream os = null;
		try {
			os = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (os != null) {
			try {
				workbook.write(os);
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
