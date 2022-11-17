package student;

public abstract class Fruit {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
Strawberry str=new Strawberry();
str.harvest();
	}

}
class Strawberry extends Fruit{
	public void harvest() {
		System.out.println("草莓已经收获了");
	}
}