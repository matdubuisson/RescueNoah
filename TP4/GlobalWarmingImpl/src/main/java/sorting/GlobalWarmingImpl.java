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
    int[][] altitude;
    int[] sortedAltitude;

    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);
        // TODO
        // expected pre-processing time in the constructror : O(n^2 log(n^2))

        this.altitude = altitude;
        this.sortedAltitude = new int[altitude.length * altitude.length];

        for(int i = 0; i < this.sortedAltitude.length; i++){
            this.sortedAltitude[i] = altitude[i / altitude.length][i % altitude.length];
        }

        Arrays.sort(this.sortedAltitude);
    }

    public int search(int waterLevel){
        int start = 0, stop = this.sortedAltitude.length, middle;

        while(start <= stop - 1){
            middle = (start + stop - 1) / 2;

            if(this.sortedAltitude[middle] == waterLevel){
                return middle;
            } else if(this.sortedAltitude[middle] < waterLevel){
                start = middle + 1;
            } else {
                stop = middle;
            }
        }

        return start;
    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    public int nbSafePoints(int waterLevel) {
        // TODO
        // expected time complexity O(log(n^2))
        if(this.sortedAltitude[this.sortedAltitude.length - 1] < waterLevel) return 0;

        int index = this.search(waterLevel);

        while(index < this.sortedAltitude.length && this.sortedAltitude[index] == waterLevel) index++;

        return this.sortedAltitude.length - index;
    }
}
