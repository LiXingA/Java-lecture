package 第八周代码;

public class StringBufferTest {
	public static void main(String[] args) {
		StringBuffer stringBuffer = new StringBuffer("abc");
		stringBuffer.append("def").append(123).append(true);
		System.out.println("1:"+stringBuffer.toString());
//		stringBuffer.setCharAt(2, 'E');
//		System.err.println("2:"+stringBuffer);
//		stringBuffer.insert(0, "hello world!");
//		System.err.println("3:"+stringBuffer);
//		stringBuffer.delete(0, 3);
//		System.err.println("4:"+stringBuffer);
//		stringBuffer.indexOf("a");
	}

	// StringBuilder
}
