package demo;

import encrypt.*;
import rsa.RSA;
import signature.HashUtil;

public class Main {
	public static void main(String[] args) {
		//encrypt.jar
		if (args[0].equals("1")) {
			//加密文件
			try {
				int[][] coordinateArray = CoordinateUtil.readCoordinate(args[2]);
				String srcPath = args[1];
				if ((srcPath.endsWith(".xls")) || (srcPath.endsWith("xlsx"))) {
					for (int i = 0; i < coordinateArray.length; i++) {
						ExcelUtil excelUtil = new ExcelUtil(srcPath);
						int row = coordinateArray[i][0];
						int col = coordinateArray[i][1];
						String oriValue = excelUtil.readCellValue(row, col);
						excelUtil.writeCellValue(rsa.RSA.encrypt(oriValue, args[3]), row, col);
					}
				} else if (srcPath.endsWith("txt")) {
					for (int i = 0; i < coordinateArray.length; i++) {
						TextUtil textUtil = new TextUtil(srcPath);
						int row = coordinateArray[i][0];
						int col = coordinateArray[i][1];
						String oriValue = textUtil.readValueByTab(row, col);
						textUtil.writeValueByTab(rsa.RSA.encrypt(oriValue, args[3]), row, col);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (args[0].equals("2")) {
			//解密文件
			try {
				int[][] coordinateArray = CoordinateUtil.readCoordinate(args[2]);
				String srcPath = args[1];
				if ((srcPath.endsWith(".xls")) || (srcPath.endsWith("xlsx"))) {
					for (int i = 0; i < coordinateArray.length; i++) {
						ExcelUtil excelUtil = new ExcelUtil(srcPath);
						int row = coordinateArray[i][0];
						int col = coordinateArray[i][1];
						String oriValue = excelUtil.readCellValue(row, col);
						excelUtil.writeCellValue(rsa.RSA.decrypt(oriValue, args[3]), row, col);
					}
				} else if (srcPath.endsWith("txt")) {
					for (int i = 0; i < coordinateArray.length; i++) {
						TextUtil textUtil = new TextUtil(srcPath);
						int row = coordinateArray[i][0];
						int col = coordinateArray[i][1];
						String oriValue = textUtil.readValueByTab(row, col);
						textUtil.writeValueByTab(rsa.RSA.decrypt(oriValue, args[3]), row, col);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (args[0].equals("3")) {
			//通配符替换
			try {
				int[][] coordinateArray = CoordinateUtil.readCoordinate(args[2]);
				String srcPath = args[1];
				if ((srcPath.endsWith(".xls")) || (srcPath.endsWith("xlsx"))) {
					for (int i = 0; i < coordinateArray.length; i++) {
						ExcelUtil excelUtil = new ExcelUtil(srcPath);
						int row = coordinateArray[i][0];
						int col = coordinateArray[i][1];
						excelUtil.writeCellValue("***", row, col);
					}
				} else if (srcPath.endsWith("txt")) {
					for (int i = 0; i < coordinateArray.length; i++) {
						TextUtil textUtil = new TextUtil(srcPath);
						int row = coordinateArray[i][0];
						int col = coordinateArray[i][1];
						textUtil.writeValueByTab("***", row, col);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//signature.jar
		if (args[0].equals("0")) {
			//生成密钥对
			RSA.generateKeyPair();
		} else if (args[0].equals("1")) {
			//生成电子签名
			HashUtil hashUtil = new HashUtil();
			hashUtil.generateSignature(args[1], args[2], args[3]);
		} else if (args[0].equals("2")) {
			System.out.println(new HashUtil().audit(args[1], args[2], Integer.parseInt(args[3])));
		}
	}
}
