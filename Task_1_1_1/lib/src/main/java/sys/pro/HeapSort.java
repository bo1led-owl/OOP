package sys.pro;

public class HeapSort {
  static private int parentIndex(int i) {
    return (i + 1) / 2 - 1;
  }

  static private int leftChildIndex(int i) {
    return (i + 1) * 2 - 1;
  }

  static private void swap(int[] items, int i, int j) {
    int tmp = items[i];
    items[i] = items[j];
    items[j] = tmp;
  }

  static private void siftDown(int[] items, int i, int end) {
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

  static private void heapify(int[] items) {
    for (int i = items.length - 1; i >= 0; --i) {
      siftDown(items, i, items.length - i);
    }
  }

  static public void sort(int[] items) {
    heapify(items);

    for (int i = items.length - 1; i > 0; --i) {
      swap(items, 0, i);
      siftDown(items, 0, i);
    }
  }
}
