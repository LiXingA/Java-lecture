package 第十四周代码;

class Strawberry extends Fruit {
	public void harvest() {
		System.out.println("草莓已经收获！");			//输出字符串“苹果已经收获！”
	}
}
public class Farm {
	public static void main(String[] args) {
		System.out.println("调用Strawberry对象的harvest()方法的结果：");
		Strawberry strawberry=new Strawberry();		//声明Apple类的一个对象apple，并为其分配内存
		strawberry.harvest();				//调用Apple类的harvest()方法				//调用Orange类的harvest()方法
	}
}
