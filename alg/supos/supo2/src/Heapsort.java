import java.util.Arrays;

public class Heapsort {

    // Max heap implementation
    private static void heapify(int[] heap) {
        for (int i = heap.length - 1; i > 0; i--) {
            int parentIndex = i % 2 == 0 ? (i - 2) / 2 : (i - 1) / 2;
            int child = heap[i];
            int parent = heap[parentIndex];
            if (child > parent) {
                int temp = child;
                heap[i] = parent;
                heap[parentIndex] = child;
            }
        }
    }

    private static void siftDown(int[] heap, int end) {
        int root = 0;
        while (2 * root + 1 <= end) {
            int child = 2 * root + 1;
            int swap = root;
            if (heap[swap] < heap[child]) {
                swap = child;
            }
            if (child + 1 <= end && heap[swap] < heap[child + 1]) {
                swap = child + 1;
            }
            if (swap == root) {
                return;
            } else {
                swap(heap, root, swap);
                root = swap;
            }
        }
    }

    private static void swap(int[] a, int indexA, int indexB) {
        int temp = a[indexA];
        a[indexA] = a[indexB];
        a[indexB] = temp;
    }

    private static void sort(int[] toSort) {
        heapify(toSort);
        for (int i = toSort.length - 1; i >= 0; i--) {
            swap(toSort, 0, i);
            siftDown(toSort, i - 1);
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 6, 2, 5, 19};
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
