package 第十四周代码;

public abstract class Fruit { // 定义抽象类
	public String color; // 定义颜色成员变量
	// 定义构造方法

	public Fruit() {
		color = "绿色"; // 对变量color进行初始化
	}

	// 定义抽象方法
	public abstract void harvest(); // 收获的方法
}
