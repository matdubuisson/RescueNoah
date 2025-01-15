package graphs;

//feel free to import anything here


import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * You just bought yourself the latest game from the Majong™
 * studio (recently acquired by Macrosoft™): MineClimb™.
 * In this 3D game, the map is made up of size 1
 * dimensional cube blocks, aligned on a grid,
 * forming vertical columns of cubes.
 * There are no holes in the columns, so the state
 * of the map can be represented as a matrix M of size n⋅m
 * where the entry M_{i,j} is the height of
 * the cube column located at i,j (0 ≤ i < n, 0 ≤ j < m).
 * You can't delete or add cubes, but you do have
 * climbing gear that allows you to move from one column to another
 * (in the usual four directions (north, south, east, west),
 * but not diagonally). The world of MineClimb™ is round:
 * the position north of (0,j) is (n-1,j), the position
 * west of (i,0) is (i,m-1) , and vice versa.
 * <p>
 * The time taken to climb up or down a column depends on
 * the difference in height between the current column and the next one.
 * Precisely, the time taken to go from column (i,j)
 * to column (k,l) is |M_{i,j}-M_{k,l}|
 * <p>
 * Given the map of the mine, an initial position
 * and an end position, what is the minimum time needed
 * to reach the end position from the initial position?
 */
public class MineClimbing {
    /**
     * Returns the minimum distance between (startX, startY) and (endX, endY), knowing that
     * you can climb from one mine block (a,b) to the other (c,d) with a cost Math.abs(map[a][b] - map[c][d])
     * <p>
     * Do not forget that the world is round: the position (map.length,i) is the same as (0, i), etc.
     * <p>
     * map is a matrix of size n times m, with n,m > 0.
     * <p>
     * 0 <= startX, endX < n
     * 0 <= startY, endY < m
     */
    private static class Pair implements Comparable<Pair>{
        public int x, y, distance;

        public Pair(int x, int y, int distance){
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public int compareTo(Pair other){
            return this.distance - other.distance;
        }
    }

    private static Iterable<int[]> adj(int[][] map, int x, int y){
        LinkedList<int[]> ws = new LinkedList<int[]>();

        ws.add(new int[]{x == 0 ? map.length - 1 : x - 1, y});
        ws.add(new int[]{x == map.length - 1 ? 0 : x + 1, y});
        ws.add(new int[]{x, y == 0 ? map[0].length - 1 : y - 1});
        ws.add(new int[]{x, y == map[0].length - 1 ? 0 : y + 1});

        return ws;
    }

    public static int best_distance(int[][] map, int startX, int startY, int endX, int endY) {
        // TODO
        int x, y;

        int[][] distances = new int[map.length][map[0].length];
        for(x = 0; x < map.length; x++){
            for(y = 0; y < map[x].length; y++){
                distances[x][y] = Integer.MAX_VALUE;
            }
        }
        distances[startX][startY] = 0;

        PriorityQueue<Pair> PQ = new PriorityQueue<Pair>();
        PQ.add(new Pair(startX, startY, 0));

        while(!PQ.isEmpty()){
            Pair v = PQ.poll();

            if(v.x == endX && v.y == endY) break;

            for(int[] xy : adj(map, v.x, v.y)){
                int cost = Math.abs(map[v.x][v.y] - map[xy[0]][xy[1]]);

                if(distances[xy[0]][xy[1]] > distances[v.x][v.y] + cost){
                    distances[xy[0]][xy[1]] = distances[v.x][v.y] + cost;
                    PQ.add(new Pair(xy[0], xy[1], distances[xy[0]][xy[1]]));
                }
            }
        }

        return distances[endX][endY];
    }
}
