package 第十一;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import net.duguying.pinyin.Pinyin;
import net.duguying.pinyin.PinyinException;

class Student implements Comparable<Student> {
	private String name;
	private int 学号;
	private int 成绩;

	public Student(String name, int 学号, int 成绩) {
		super();
		this.name = name;
		this.学号 = 学号;
		this.成绩 = 成绩;
	}

	@Override
	public int compareTo(Student other) {
		return other.成绩 - this.成绩;
	}

	@Override
	public String toString() {
		return "Student [姓名=" + this.name + "，学号=" + 学号 + ", 成绩=" + 成绩 + "]";
	}

	public String getName() {
		return name;
	}

	public int get学号() {
		return 学号;
	}

	public int get成绩() {
		return 成绩;
	}

}

class StudentComparator implements Comparator<Student> {

	private final Pinyin pinyin;

	public StudentComparator() throws PinyinException {
		
		pinyin = new Pinyin();
	}

	@Override
	public int compare(Student o1, Student o2) {

		return  pinyin.translate(o1.getName()).compareTo(pinyin.translate(o2.getName()));
	}

}

public class TreeSetTest {

	public static void main(String[] args) throws PinyinException {
		Pinyin pinyin = new Pinyin();
		Student stu1 = new Student("张三", 20190102, 80);
		Student stu2 = new Student("李四", 20190103, 90);
		Student stu3 = new Student("王五", 20190104, 75);
		Student stu4 = new Student("赵云", 20190105, 68);
		Student stu5 = new Student("关羽", 20190106, 94);
		Student stu6 = new Student("张飞", 20190107, 67);
		Student stu7 = new Student("貂蝉", 20190101, 100);
		TreeSet<Student> set = new TreeSet<>(new StudentComparator());
		set.add(stu1);
		set.add(stu2);
		set.add(stu3);
		set.add(stu4);
		set.add(stu5);
		set.add(stu6);
		set.add(stu7);
		for (Student student : set) {
			System.out.println(student);
		}
		SortedSet<Student> headSet = set.headSet(stu1);
		System.err.println("截取" + stu1 + "之前的数据");
		for (Student student : headSet) {
			System.out.println(student);
		}
	}

	public static void main2(String[] args) {
		TreeSet<Integer> set = new TreeSet<>();
		set.add(123);
		set.add(67);
		set.add(888);
		set.add(138);
		for (Integer integer : set) {
			System.out.println(integer);
		}
	}
}
