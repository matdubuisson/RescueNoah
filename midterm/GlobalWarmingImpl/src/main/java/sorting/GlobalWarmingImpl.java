package sorting;

import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,1,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Each entry in the matrix represents an altitude point at the corresponding grid coordinate.
 * The goal is to implement a GlobalWarmingImpl class that extends the GlobalWarming abstract class described below.
 *
 * Given a global water level, all positions in the matrix with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3, all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4), 1 ,(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is nbSafePoints
 * - calculating the number of safe points for a given water level
 *
 * Complete the code below. Pay attention to the expected time complexity of each method.
 * To meet this time complexity, you need to do some pre-computation in the constructor.
 * Feel free to create internal classes in GlobalWarmingImpl to make your implementation easier.
 * Feel free to use any method or data structure available in the Java language and API.
 */

abstract class GlobalWarming {


    protected final int[][] altitude;

    /**
     * @param altitude is a n x n matrix of int values representing altitudes (positive or negative)
     */
    public GlobalWarming(int[][] altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @param waterLevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified waterLevel.
     *         Warning: this is not the waterLevel given in the constructor/
     */
    public abstract int nbSafePoints(int waterLevel);

}


public class GlobalWarmingImpl extends GlobalWarming {
    int[] array;

    private int partition(int[] array, int lo, int hi){
        int i = lo + 1, j = hi, key = array[lo], tmp;

        while(i < j){
            while(array[++i] <= key){
                if(i + 1 == hi) break;
            }

            while(array[--j] >= key){
                if(j == lo) break;
            }

            tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }

        array[lo] = array[j];
        array[j] = key;

        return j;
    }

    private void quickSort(int[] array, int lo, int hi){
        if(lo >= hi) return;

        int top = -1;
        int[] stack = new int[hi - lo + 1];

        while(true){
            while(lo < hi - 1){
                stack[++top] = hi;
                hi = partition(array, lo, hi);
            }

            if(top == -1) break;

            lo = hi + 1;
            hi = stack[top--];
        }
    }

    private void quickSort(int[] array){
        this.quickSort(array, 0, array.length - 1);
    }

    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);
        // TODO
        // expected pre-processing time in the constructror : O(n^2 log(n^2))

        if(altitude.length == 0) this.array = null;
        else this.array = new int[altitude.length * altitude[0].length];

        for(int i = 0, j, k = 0; i < altitude.length; i++){
            for(j = 0; j < altitude[0].length; j++){
                this.array[k++] = altitude[i][j];
            }
        }

        Arrays.sort(this.array);
        //this.quickSort(this.array);
    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    public int nbSafePoints(int waterLevel) {
        // TODO
        // expected time complexity O(log(n^2))

        int count = 0;

        for(int i = 0; i < this.array.length; i++){
            if(this.array[i] > waterLevel) break;

            count++;
        }

        return this.array.length - count;
    }
}
