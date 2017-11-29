package supo3;

import java.util.Comparator;

public class CarComparator implements Comparator<Car> {

	@Override
	public int compare(Car o1, Car o2) {
		if(o1.getManu().compareTo(o2.getManu())!=0){
			return o1.getManu().compareTo(o2.getManu());
		}else {
			return o1.getAge()-o2.getAge();
		}
	}
}
