package supo3;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Car implements Comparable<Car> {
	private String manufacturer;
	private int age;

	@Override
	public int compareTo(Car o) {
		return manufacturer.compareTo(o.getManu());
	}

	public Car(String manufacturer, int age) {
		this.manufacturer = manufacturer;
		this.age = age;
	}

	public String getManu() {
		return manufacturer;
	}

	public int getAge() {
		return age;
	}

	public String toString() {
		return "(" + manufacturer + ", " + age + ")";
	}

	public static void main(String[] args) {
		List<Car> cars = new LinkedList<>();
		cars.add(new Car("D", 10));
		cars.add(new Car("A", 10));
		cars.add(new Car("A", 9));
		cars.add(new Car("C", 10));
		Collections.sort(cars);
		System.out.println(cars);
		Collections.sort(cars, new CarComparator());
		System.out.println(cars);
	}

}
