package 第十三周;

public class Test {
	
	final static int CONST_ABC=123;
	
	public static void main(String[] args) {
		
		int b = Integer.MAX_VALUE;
		
		
		try {
			
			
			
			int a =123;
			int $123 =123;
			
			
//			throw new Exception("我出错 了");
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println("捕捉到并处理错误");
		}finally {
			System.err.println("一定会执行的代码");
		}
	}
	
	
	
	public static void main2(String[] args) {
		int 累计 = 0;
		int i = 1;
		do {
			累计 = 累计 + i;
		} while (i++ <= 100);
		System.out.println(累计);
	}

}
