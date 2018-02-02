import java.util.ArrayList;

public class CountingSort {
    public static void countingRadixSort(int[] a, int digitIndex){
        ArrayList<ArrayList<Integer>> count = new ArrayList<>(10);
        for(int i = 0; i < 10; i++){
            count.add(new ArrayList<Integer>());
        }
        for(int i = 0; i < a.length; i++){
                count.get(digit(a[i], digitIndex)).add(a[i]);
        }
        int k = 0;
        for(ArrayList<Integer> b : count){
            for(int value : b){
                a[k] = value;
                k++;
            }
        }

    }
    public static void sort(int[] a, int range){
        int[] count = new int[range];
        for(int i = 0; i < a.length; i++){
            count[a[i]]++;
        }
        int k = 0;
        for(int i = 0; i < count.length; i++){
            while(count[i] > 0){
                a[k] = i;
                k++;
                count[i]--;
            }
        }
    }
    public static int digit(int value, int digitIndex){
        while(digitIndex > 0){
            value /= 10;
            digitIndex--;
        }
        return value % 10;
    }
}
