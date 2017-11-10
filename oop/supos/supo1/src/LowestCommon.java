
public class LowestCommon {
	public static void main(String[] args) {
		System.out.println(LowestCommon.lowestCommon(14, 25));
	}

	public static int lowestCommon(long a, long b) {
		// Check if there is no intersection, if so return -1
		if ((a & b) == 0) {
			return -1;
		}
		int index = 0;
		long intersection = a & b;
		// This will always terminate since there must be an
		// intersection if this part of the code is reached
		while (true) {
			// Intersection reached so return index
			// Could easily be completed without break
			if ((intersection & 1) == 1) {
				break;
			} else {
				index++;
				intersection = intersection >> 1;
			}
		}
		return index;
	}
}
