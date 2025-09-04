package sys.pro;

public class HeapSort {
    private static int parentIndex(int i) {
        return (i + 1) / 2 - 1;
    }

    private static int leftChildIndex(int i) {
        return (i + 1) * 2 - 1;
    }

    private static void swap(int[] items, int i, int j) {
        int tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }

    private static void siftDown(int[] items, int i, int end) {
        while (leftChildIndex(i) < end) {
            int child = leftChildIndex(i);
            if (child + 1 < end && items[child] < items[child + 1]) {
                child = child + 1;
            }

            if (items[i] < items[child]) {
                swap(items, i, child);
                i = child;
            } else {
                break;
            }
        }
    }

    private static void heapify(int[] items) {
        for (int i = items.length - 1; i >= 0; --i) {
            siftDown(items, i, items.length - i);
        }
    }

    public static void sort(int[] items) {
        heapify(items);

        for (int i = items.length - 1; i > 0; --i) {
            swap(items, 0, i);
            siftDown(items, 0, i);
        }
    }
}
