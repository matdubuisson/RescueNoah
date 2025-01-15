package sorting;

/**
 * Author Pierre Schaus
 *
 * Complete the Merge Sort (top-down) algorithm below making use
 * of the provided merge method.
 * You are not allowed to use imports or other external classes of Java.
 * Hint: Merger Sort this is a divide and conquer algorithm.
 *       It is easier to implement it recursively.
 *       As an alternative exercise, you can try to implement it
 *       non recursively, using a loop instead.
 */
public class MergeSort {
    /**
     * Pre-conditions: a[lo..mid] and a[mid+1..hi] are sorted
     * Post-conditions: a[lo..hi] is sorted
     */
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (aux[j].compareTo(aux[i]) < 0) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    // Mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        // TODO
        if(lo >= hi) return;
        int length = hi - lo + 1;

        for(int size = 1, cursor, midCursor, endCursor; size < length; size *= 2){
            for(cursor = 0; cursor < length; cursor += 2 * size){
                midCursor = (cursor + size) >= length ? (length - 1) : (cursor + size - 1);
                endCursor = (cursor + 2 * size) >= length ? (length - 1) : (cursor + 2 * size - 1);
                // System.out.println(cursor + " " + midCursor + " " + endCursor);
                merge(a, aux, cursor, midCursor, endCursor);
            }
        }
    }

    /**
     * Rearranges the array in ascending order, using the natural order
     */
    public static void sort(Comparable[] a) {
        // TODO
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }
}