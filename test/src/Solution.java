import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String[] unsorted = new String[n];
        for(int unsorted_i=0; unsorted_i < n; unsorted_i++){
            unsorted[unsorted_i] = in.next();
        }
        Arrays.sort(unsorted, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if(o1.length() == o2.length()) {
					return o1.compareTo(o2);
				}else {
					return o1.length() - o2.length();
				}
			}
		});
        for(String s : unsorted) {
        	System.out.println(s);
        }
        // your code goes here
    }
}
