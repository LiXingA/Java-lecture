package student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckFileUtil {
	public static void main(String[] args) throws IOException {
		List<String> names = getNames(null);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("./银行卡号.txt")));
		String line;
		List<String> outputList = new ArrayList<>();
		while ((line = bufferedReader.readLine()) != null) {
			outputList.add(line);
		}
		bufferedReader.close();
		StringBuilder builder = new StringBuilder();
		for (String name : names) {
			for (String nwo : outputList)
				if (nwo.indexOf(name) != -1) {// 判断这一行银行卡数据有没有我班的学生姓名
					builder.append(nwo).append("\n");
				}
		}

		System.out.println(builder);
	}

	static List<String> getNames(String filePath) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new FileReader(new File(filePath != null ? filePath : "./学生姓名.txt")));
		ArrayList<String> arrayList = new ArrayList<>();// 存所有学生的链表（可以认为是比较好用的数组）
		String line;
		while ((line = bufferedReader.readLine()) != null) {// 是否文件读完�?
			String 学生姓名 = line.trim();
			if (!学生姓名.equals("")) {// 判断学生姓名有效
				arrayList.add(学生姓名);
				// System.err.println(学生姓名);
			}
		}
		bufferedReader.close();
		return arrayList;

	}
}
