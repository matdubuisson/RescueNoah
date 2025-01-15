package sorting;


import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Given an array of (closed) intervals, you are asked to implement the union operation.
 * This operation will return the minimal array of sorted intervals covering exactly the union
 * of the points covered by the input intervals.
 * For example, the union of intervals [7,9],[5,8],[2,4] is [2,4],[5,9].
 * The Interval class allowing to store the intervals is provided
 * to you.
 *
 */
public class Union {

    /**
     * A class representing an interval with two integers. Hence the interval is
     * [min, max].
     */
    public static class Interval implements Comparable<Union.Interval> {

        public final int min;
        public final int max;

        public Interval(int min, int max) {
            assert(min <= max);
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Union.Interval) obj).min == min && ((Union.Interval) obj).max == max;
        }

        @Override
        public String toString() {
            return "["+min+","+max+"]";
        }

        @Override
        public int compareTo(Union.Interval o) {
            if (min < o.min) return -1;
            else if (min == o.min) return max - o.max;
            else return 1;
        }
    }

    /**
     * Returns the union of the intervals given in parameters.
     * This is the minimal array of (sorted) intervals covering
     * exactly the same points than the intervals in parameter.
     * 
     * @param intervals the intervals to unite.
     */
    public static Interval[] union(Interval[] intervals) {
        // TODO
        Arrays.sort(intervals);

        int i, j = intervals.length;
        Interval[] intervalsAux = new Interval[intervals.length];

        for(i = 0; i < intervals.length; i++){
            intervalsAux[i] = intervals[i];
        }

        for(i = 1; i < intervals.length; i++){
            if(intervalsAux[i - 1].max >= intervalsAux[i].min){
                intervalsAux[i] = new Interval(Math.min(intervalsAux[i - 1].min, intervalsAux[i].min), Math.max(intervalsAux[i - 1].max, intervalsAux[i].max));
                intervalsAux[i - 1] = null;
                j--;
            }
        }

        Interval[] newIntervals = new Interval[j];
        j = 0;

        for(i = 0; i < intervals.length; i++){
            if(intervalsAux[i] != null){
                newIntervals[j++] = intervalsAux[i];
            }
        }

        return newIntervals;
    }

}
