public class Ref {
	public static void main(String[] args) {
		int[] test = {0,1,2,3,4};
		new Ref().incArr(test);
		for(int x : test) {
			System.out.println(x);
		}
	}
	public void incArr(int[] a) {
		for(int i = 0; i < a.length; i++) {
			a[i]++;
		}
	}
}
