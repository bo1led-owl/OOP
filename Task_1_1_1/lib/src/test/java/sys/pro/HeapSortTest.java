package sys.pro;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HeapSortTest {
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; ++i) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }

        return true;
    }

    @Test
    void oddLength() {
        int[] array = new int[] {3, 1, 5, 4, 2};
        HeapSort.sort(array);

        assertTrue(isSorted(array));
    }

    @Test
    void evenLength() {
        int[] array = new int[] {3, 1, 5, 4, 7, 2};
        HeapSort.sort(array);

        assertTrue(isSorted(array));
    }

    @Test
    void singleElement() {
        int[] array = new int[] {42};
        HeapSort.sort(array);
        assertTrue(array[0] == 42);
    }

    @Test
    void nullArray() {
        HeapSort.sort(null);
    }

    @Test
    void empty() {
        HeapSort.sort(new int[] {});
    }
}
