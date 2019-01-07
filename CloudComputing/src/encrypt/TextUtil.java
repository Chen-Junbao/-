package encrypt;

import java.io.*;

public class TextUtil {
	private String path;
	private java.util.ArrayList<String> content;

	public TextUtil(String path) {
		this.path = path;
		content = new java.util.ArrayList();
		readContent();
	}

	/**
	 * 读取文本文档内容
	 */
	private void readContent() {
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new FileReader(path);
			br = new BufferedReader(isr);
			String info;
			while ((info = br.readLine()) != null) {
				content.add(info);
			}

			br.close();
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 以制表符对文本文档内容进行分割
	 * @param row	行号
	 * @param col	列号
	 * @return 读取内容
	 */
	public String readValueByTab(int row, int col) {
		String line = (String) content.get(row);
		String[] splitInfo = line.split("\t");

		return splitInfo[col];
	}

	/**
	 * 对以制表符为分割的文本文档内容进行修改
	 * @param newValue	修改内容
	 * @param row	行号
	 * @param col	列号
	 */
	public void writeValueByTab(String newValue, int row, int col) {
		String line = (String) content.get(row);
		String[] splitInfo = line.split("\t");
		splitInfo[col] = newValue;
		StringBuilder sb = new StringBuilder();
		for (String info : splitInfo) {
			sb.append(info + "\t");
		}
		sb.deleteCharAt(sb.lastIndexOf("\t"));
		content.set(row, sb.toString());

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path));
			for (String info : content) {
				bw.write(info + "\n");
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
