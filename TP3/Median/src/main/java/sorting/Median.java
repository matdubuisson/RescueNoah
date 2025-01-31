package sorting;

import java.util.Random;

/**
 * Author Pierre Schaus
 *
 * We give you the API of a Vector class allowing to access,
 * modify and exchange two elements in constant time.
 * Your task is to implement a method to calculate the median of a Vector.
 *
 * public interface Vector {
 *     // size of the vector
 *     public int size();
 *     // set the value v to the index i of the vector
 *     public void set(int i, int v);
 *     // returns the value at index i of the vector
 *     public int get(int i);
 *     // exchanges the values at positions i and j
 *     public void swap(int i, int j);
 * }
 * You must implement a method that has the following signature:
 * public static int median(Vector a, int lo, int hi)
 *
 * This method calculates the median of vector "a" between the positions "lo" and "hi" (included).
 * You can consider that the vector "a" has always an odd size.
 * To help you, an IntelliJ project with a minimalist test to check if your code computes the right median value is given.
 * Indeed, it is not advised to debug your code via Inginious.
 * Warning It is not forbidden to modify or swap elements of the vector "a" during the calculation (with the get/set/swap methods).
 * It is forbidden to call other methods of the standard Java library. It is also forbidden to make a "new".
 *
 * The evaluation is based on 10 points:
 *  - good return value: 3 points,
 *  - good return value and complexity O(n log n): 5 points,
 *  - good return value and complexity O(n) expected (average case on a random uniform distribution): 10 points.
 *
 *  All the code you write in the text field will be substituted in the place indicated below.
 *  You are free to implement other methods to help you in this class, but the method "median" given above must at least be included.
 */
public class Median {

    public static class Vector {

        private int [] array;
        private int nOp = 0;


        public Vector(int n) {
            array = new int[n];
        }

        /**
         * Returns the size of the vector
         */
        public int size() {
            return array.length;
        }

        /**
         * Set the index in the vector to the given value
         *
         * @param i the index of the vector
         * @param v the value to set
         */
        public void set(int i, int v) {
            nOp++;
            array[i] = v;
        }

        /**
         * Returns the value at the given index
         *
         * @param i The index from which to retrieve the value
         */
        public int get(int i) {
            nOp++;
            return array[i];
        }

        /**
         * Exchanges elements in the array
         *
         * @param i the first index to swap
         * @param j the second index to swap
         */
        public void swap(int i, int j) {
            nOp++;
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }

        /**
         * Returns the number of operation that has been made
         */
        public int nOp() {
            return nOp;
        }
    }

    public static int partition(Vector vec, int lo, int hi){
        int key = vec.get(lo), tmp;

        int i = lo, j = hi + 1;

        while(true){
            while(key > vec.get(++i)){
                if(i == hi) break;
            }

            while(key < vec.get(--j)){
                if(j == lo) break;
            }

            if(i >= j) break;

            tmp = vec.get(i);
            vec.set(i, vec.get(j));
            vec.set(j, tmp);
        }

        vec.set(lo, vec.get(j));
        vec.set(j, key);
        return j;
    }

    /**
     * Returns the median value of the vector between two indices
     *
     * @param vec the vector
     * @param lo the lowest index from which the median is computed
     * @param hi the highest index from which the median is computed
     */
    public static int median(Vector vec, int lo, int hi) {
        int cut;

        do{
            cut = partition(vec, lo, hi);

            if(cut < vec.size() / 2){ // median is on the right
                lo = cut + 1;
            } else { // median is on the left
                hi = cut - 1;
            }

            // System.out.println(cut + " " + vec.size() / 2);
        } while(cut != vec.size() / 2);

        return vec.get(cut);
    }

    public static void main(String[] args) {
        Vector vector = new Vector(10);
        Random random = new Random();

        int[] array = new int[]{1, 4, 7, 9, 3, 2, 5, 6, 8};

        System.out.print("{");

        for(int i = 0; i < array.length; i++){
            //vector.set(i, random.nextInt() % 10);
            vector.set(i, array[i]);
            System.out.print("[" + vector.get(i) + "]");
        }

        System.out.println("}");

        System.out.println(median(vector, 0, vector.size() - 1));

        System.out.print("{");

        for(int i = 0; i < 10; i++){
            System.out.print("[" + vector.get(i) + "]");
        }

        System.out.println("}");
    }
}
