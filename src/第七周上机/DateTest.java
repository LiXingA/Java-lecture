package 第七周上机;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String format = simpleDateFormat.format(new Date());
		System.out.println(format);
		System.out.println(simpleDateFormat.parse("2022年10月28日"));
	}

	public static void main2(String[] args) {
		Date date = new Date();
		int year = 1900 + date.getYear();
		int month = 1 + date.getMonth();
		int day = date.getDate();
		System.out.println(year + "年" + month + "月" + day + "日");
	}
}
