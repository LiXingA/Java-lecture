package 第十一;

import 第十五周.Animal;

public class Dog extends Animal {

	public Dog(String 名字, int 年龄, String 体型, int 身高) {
		super(名字, 年龄, 体型, 身高);
		
		System.out.println(this.体型);
		System.out.println(this.年龄);
	}
	
	

	// public static void main(String[] args) {
	// Animal animal = new Animal("狗", 12, "小体型", 50);
	// System.out.println(animal.年龄);
	// System.out.println(animal.体型);
	// System.out.println(animal.名字);
	// System.out.println(animal.身高);
	// }
}
