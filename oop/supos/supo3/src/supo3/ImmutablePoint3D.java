package supo3;

import java.util.Collections;
import java.util.LinkedList;

public class ImmutablePoint3D implements Comparable<ImmutablePoint3D> {

	private final int x;
	private final int y;
	private final int z;

	public ImmutablePoint3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	@Override
	public int compareTo(ImmutablePoint3D p) {
		if (z != p.getZ()) {
			return z - p.getZ();
		} else {
			if (y != p.getY()) {
				return y - p.getY();
			} else {
				return x - p.getX();
			}
		}
	}

	public String toString() {
		return "( " + x + "," + y + "," + z + ")";
	}
	public static void main(String[] args) {
		// Test cases
		LinkedList<ImmutablePoint3D> list = new LinkedList<>();
		list.add(new ImmutablePoint3D(1,2,3));
		list.add(new ImmutablePoint3D(2,2,3));
		list.add(new ImmutablePoint3D(1,2,4));
		list.add(new ImmutablePoint3D(2,1,3));
		list.add(new ImmutablePoint3D(4,6,3));
		Collections.sort(list);
		System.out.println(list);
	}

}
