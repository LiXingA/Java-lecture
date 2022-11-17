package 第七周代码;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

	public static void main(String[] args) {
		Date today = new Date();
		System.err.println(String.format("%tF", today));
		System.out.println(String.format("%tD", today));
		System.out.println(String.format("%tT", today));
		System.out.println(String.format("%tR", today));
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/YY");  //
		String format2 = format.format(today);
		System.err.println(format2);
	}

	public static void main3(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		System.err.println("当前时间long表示：" + currentTimeMillis);
		Date date = new Date(0);
		System.err.println(date);
	}

	public static void main2(String[] args) {
		Date date = new Date();
		System.err.println(date);
		System.err.println("年：" + (1900 + date.getYear()));
		System.err.println("月：" + (1 + date.getMonth()));
		System.err.println("日：" + date.getDate());
		System.err.println("星期几：" + date.getDay());
	}
}
