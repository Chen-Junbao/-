package encrypt;

import java.util.ArrayList;

public class CoordinateUtil {
	/**
	 * 根据坐标文件路径读取坐标信息
	 * @param path	坐标文件路径
	 * @return 坐标数组
	 */
	public static int[][] readCoordinate(String path) {
		java.io.BufferedReader br = null;
		ArrayList<Coordinate> list = new ArrayList();
		try {
			br = new java.io.BufferedReader(new java.io.FileReader(path));
			String line = null;
			int col;
			while ((line = br.readLine()) != null) {
				//以空格或制表符分割
				String[] temp = line.split(" |\t");
				int row = Integer.parseInt(temp[0]);
				col = Integer.parseInt(temp[1]);
				Coordinate coordinate = new Coordinate(row, col);
				list.add(coordinate);
			}
			int[][] coordinateArray = new int[list.size()][2];
			int index = 0;
			for (Coordinate coordinate : list) {
				coordinateArray[index][0] = coordinate.getRow();
				coordinateArray[index][1] = coordinate.getCol();
				index++;
			}
			return coordinateArray;
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
