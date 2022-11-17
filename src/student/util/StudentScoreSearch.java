package student.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.github.andrewoma.dexx.collection.ArrayList;

/**
 * 破解成绩链接地址
 * 
 * @author Xing
 *
 */
public class StudentScoreSearch {
	public static void main(String[] args) throws UnsupportedEncodingException {

		ArrayList<String> list = new ArrayList<>();

		// Esc Escape
		// Alt+/
		String name = new String("woshi zifu");

		String decode = URLDecoder.decode(
				"20%BC%C6%CB%E3%BB%FA%D3%A6%D3%C3%BC%BC%CA%F5%28%CE%E5%C4%EA%D6%C6%B8%DF%D6%B0%29%282%29--%C8%EB%D1%A7%BD%CC%D3%FD%A3%A8%BA%AC%BE%FC%D1%B5%A3%A9--1--1--%C0%EE%D0%C7--&button=%CC%E1%BD%BB",
				"GBK");
		System.err.println(decode);
		String strings[] = { "数学2", "语文3", "政治2", "英语2", "体育2", "VB程序设计基础", "计算机应用基础2", "计算机应用基础2实训",
				"visual Basic实训" };
		for (String string : strings) {
			String s = String.format("20计算机应用技术(五年制高职)(1)--%s--2--1--李星--&button=提交", string);
			System.out.println(
					string + " " + "http://192.168.55.2:9890/xkcj_printer.asp?sjcs=" + URLEncoder.encode(s, "GBK"));
		}
	}
}

class Human {
	private String type;// 黄种人、白种人
	private String name;// 姓名
	private String sex;// 性别

	public Human(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public void thinking() {

	}

	public void language() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class YellowHuman extends Human {

	public YellowHuman(String name) {
		super("黄种人", name);
	}

}