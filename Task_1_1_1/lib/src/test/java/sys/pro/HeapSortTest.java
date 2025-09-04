package sys.pro;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HeapSortTest {
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; ++i) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void testSort() {
        int[] array = new int[] {3, 1, 5, 4, 2};
        HeapSort.sort(array);

        assertTrue(isSorted(array));
    }
}
