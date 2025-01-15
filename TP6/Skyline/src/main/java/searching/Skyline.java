package searching;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Skyline {
    public static class BuildingPoint implements Comparable{
        int x, height;
        boolean isStart;

        BuildingPoint(int x, boolean isStart, int height) {
            this.x = x;
            this.isStart = isStart;
            this.height = height;
        }

        @Override
        public int compareTo(Object o) {
            BuildingPoint other = (BuildingPoint) o;
            int code = this.x - other.x;

            if (code == 0) {
                if (this.isStart) return 1;
                else if (other.isStart) return -1;
                else return 1;
            } else return code;
        }
    }

    /**
     *   The buildings are defined with triplets (left, height, right).
     *         int[][] buildings = {{1, 11, 5}, {2, 6, 7}, {3, 13, 9}, {12, 7, 16}, {14, 3, 25}, {19, 18, 22}, {23, 13, 29}, {24, 4, 28}};
     *
     *         [{1,11},{3,13},{9,0},{12,7},{16,3},{19,18},{22,3},{23,13},{29,0}]
     *
     * @param buildings
     * @return  the skyline in the form of a list of "key points [x, height]".
     *          A key point is the left endpoint of a horizontal line segment.
     *          The key points are sorted by their x-coordinate in the list.
     */

    public static List<int[]> getSkyline(int[][] buildings) {
        BuildingPoint[] bs = new BuildingPoint[buildings.length * 2];

        int i;

        for (i = 0; i < buildings.length; i++) {
            bs[2 * i] = new BuildingPoint(buildings[i][0], true, buildings[i][1]);
            bs[2 * i + 1] = new BuildingPoint(buildings[i][2], false, buildings[i][1]);
        }

        Arrays.sort(bs);

        ArrayList<int[]> list = new ArrayList<>();
        TreeMap<Integer, Integer> resmap = new TreeMap<>();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(0, 1);
        int previousMaximalHeight = -1;
        int currentMaximalHeight = 0;

        for (i = 0; i < bs.length; i++) {
            if (bs[i].isStart) {
                map.compute(bs[i].height, (key, value) -> {
                    if (value == null) return 1;
                    else return value + 1;
                });
            } else {
                map.compute(bs[i].height, (key, value) -> {
                    if (value == 1) return null;
                    else return value - 1;
                });
            }

            currentMaximalHeight = map.lastKey();

            if (previousMaximalHeight != currentMaximalHeight) {
                resmap.put(bs[i].x, currentMaximalHeight);

                previousMaximalHeight = currentMaximalHeight;
            }
        }

        for (Integer key : resmap.keySet()) {
            list.add(new int[]{key, resmap.get(key)});
        }

        return list;
    }

    /*
    public static List<int[]> getSkyline(int[][] buildings) {
        // Create a list of points.
        BuildingPoint[] points = new BuildingPoint[buildings.length * 2];
        int index = 0;
        for (int[] building : buildings) {
            points[index] = new BuildingPoint(building[0], true, building[1]);
            points[index + 1] = new BuildingPoint(building[2], false, building[1]);
            index += 2;
        }

        Arrays.sort(points); // Sort the points.

        // Use a tree map to represent the active buildings.
        TreeMap<Integer, Integer> queue = new TreeMap<>();
        queue.put(0, 1); // Add a ground level (height 0).
        int prevMaxHeight = 0;

        List<int[]> result = new ArrayList<>();

        for (BuildingPoint point : points) {
            if (point.isStart) {
                // If it's a start point, add the height to the map, or increment the existing height's count.
                queue.compute(point.height, (key, value) -> {
                    if (value != null) return value + 1;
                    return 1;
                });
            } else {
                // If it's an end point, decrement or remove the height from the map.
                queue.compute(point.height, (key, value) -> {
                    if (value == 1) return null;
                    return value - 1;
                });
            }
            // Get current max height after the addition/removal above.
            int currentMaxHeight = queue.lastKey();

            // If the current max height is different from the previous one, we have a critical point.
            if (prevMaxHeight != currentMaxHeight) {
                result.add(new int[]{point.x, currentMaxHeight});
                prevMaxHeight = currentMaxHeight;
            }
        }

        return result;
    }
    */

    public static void main(String[] args) {
        int[][] buildings = {{0,4,7},{0,8,6},{6,6,12},{6,4,16},{10,5,20},{22,2,26}};
        int [][] res = {{0,8},{6,6},{12,5},{20,0},{22,2},{26,0}};

        List<int[]> list = Skyline.getSkyline(buildings);

        System.out.print("Result : [");
        for (int i = 0, j; i < list.size(); i++) {
            System.out.print("(");
            for (j = 0; j < list.get(i).length; j++) {
                System.out.print(list.get(i)[j]);

                if (j + 1 != list.get(i).length) {
                    System.out.print(", ");
                }
            }
            if (i + 1 == list.size()) System.out.print(")");
            else System.out.print("), ");
        }
        System.out.println("]");
    }
}
