package 第十一;

import net.duguying.pinyin.Pinyin;
import net.duguying.pinyin.PinyinException;

public class StringTest {
	public static void main(String[] args) throws PinyinException {
		String aString ="a";
		String bString ="b";
		int compareTo = aString.compareTo(bString);
		Pinyin pinyin = new Pinyin();
		String translate = pinyin.translate("赵云");
System.out.println(translate);
	}
}
