package supo3;

import java.util.LinkedList;

public class CarList extends LinkedList<Car> {

	@Override
	public boolean add(Car c) {

		for (int i = 0; i < this.size(); i++) {
			if (c.getManu().compareTo(this.get(i).getManu()) <= 0) {
				this.add(i, c);
				return true;
			}
		}
		// If not added yet, it must be larger than the whole list
		this.add(this.size(), c);
		return true;
	}
}
