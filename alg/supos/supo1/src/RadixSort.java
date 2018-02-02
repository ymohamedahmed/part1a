public class RadixSort {
    public static void sort(int[] a) {
        int max = max(a);
        for(int i = 0; i < Math.floor(Math.log10(max)+1); i++){
            CountingSort.countingRadixSort(a, i);
        }

    }

    public static int max(int[] a) {
        int max = 0;
        for (int i : a) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }
}
