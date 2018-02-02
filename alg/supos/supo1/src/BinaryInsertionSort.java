import java.util.Arrays;
import java.util.Random;

public class BinaryInsertionSort {
    public static void insertSort(int[] a){
        for(int i = 0; i < a.length; i++){
            // Returns (-k-1) where k is insertion point iff a doesn't contain the key
            int pos = Math.abs(Arrays.binarySearch(a, 0, i, a[i]) + 1);
            int temp = a[i];
            for(int z = i-1; z >= pos; z--){
                a[z+1] = a[z];
            }
            a[pos] = temp;
        }
    }
    public static void main(String[] args){
        Random rand = new Random();
        int[] test = new int[rand.nextInt(15)+1];
        for(int i = 0; i < test.length; i++){
            test[i] = rand.nextInt(1000);
        }
        for(int x : test){
            System.out.print(x + ",");
        }
        BinaryInsertionSort.insertSort(test);
        System.out.println();

        for(int x : test){
            System.out.print(x + ",");
        }

    }

}
