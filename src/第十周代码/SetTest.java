package 第十周代码;

import java.util.HashSet;
import java.util.Set;

class Person {
	private String name;
	private int id_card;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId_card() {
		return id_card;
	}

	public void setId_card(int id_card) {
		this.id_card = id_card;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (int) (id_card * (id_card >>> 32));
		result = PRIME * result + (name == null ? 0 : name.hashCode());
		return result;
		// super.hashCode()
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Person other = (Person) obj;
		if (id_card != other.id_card) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}

public class SetTest {

	public static void main(String[] args) {

	}

	public static void main2(String[] args) {
		Set set = new HashSet<>();
		set.add(123);
		set.add(123);
		set.add(123);
		System.err.println(set);

	}
}
