import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    public static void sort(int[] a, int l, int r){
        if(r - l <= 0){
            return;
        }else{
            int part = part(a, l, r, a[l]);
            sort(a, l, part);
            sort(a, part+1, r);
        }
    }
    public static void sortWithRandomPivot(int[] a, int l, int r){
        int randomIndex = new Random().nextInt(a.length);
        swap(a, 0, randomIndex);
        sort(a, 0, a.length-1);
    }
    private static int part(int[] a, int low, int high, int pivot){
       int left = low -1;
       int right = high+1;
       while(true){
           while(a[++left] < pivot){}
           while(right > 0 && a[--right] > pivot){}

           if(left >= right){
               return right;
           }
           swap(a, left, right);

       }
    }
    private static void swap(int[] a, int i, int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    public static void main(String[] args){
        Random rand = new Random();
        int[] test = new int[rand.nextInt(15)+1];
        for(int i = 0; i < test.length; i++){
            test[i] = rand.nextInt(150);
        }
        System.out.print(Arrays.toString(test));
        System.out.println(CountingSort.digit(153, 0));
        RadixSort.sort(test);
        System.out.println();
        System.out.print(Arrays.toString(test));

    }
}
