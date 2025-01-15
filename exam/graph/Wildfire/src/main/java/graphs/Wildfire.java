package graphs;

import java.nio.IntBuffer;
import java.util.*;


/**
 * Let's consider a forest represented as a 2D grid.
 * Each cell of the grid can be in one of three states:
 *
 * 0 representing an empty spot.
 * 1 representing a tree.
 * 2 representing a burning tree (indicating a wildfire).
 *
 * The fire spreads from a burning tree to its four neighboring cells (up, down, left, and right) if there's a tree there.
 * Each minute, the trees in the neighboring cells of burning tree catch on fire.
 *
 * Your task is to calculate how many minutes it would take for the fire to spread throughout the forest i.e. to burn all the trees.
 *
 * If there are trees that cannot be reached by the fire (for example, isolated trees with no adjacent burning trees),
 * we consider that the fire will never reach them and -1 is returned.
 *
 * The time-complexity of your algorithm must be O(n) with n the number of cells in the forest.
 */
public class Wildfire {

    static final int EMPTY = 0;
    static final int TREE = 1;
    static final int BURNING = 2;


    /**
     * This method calculates how many minutes it would take for the fire to spread throughout the forest.
     *
     * @param forest
     * @return the number of minutes it would take for the fire to spread throughout the forest,
     *         -1 if the forest cannot be completely burned.
     */
    private class Pair implements Comparable<Pair>{
        int x, y, distance;

        public Pair(int x, int y, int distance){
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public int compareTo(Pair other) {
            return this.distance - other.distance;
        }
    }

    private Iterable<int[]> adj(int[][] forest, int x, int y){
        LinkedList<int[]> ws = new LinkedList<int[]>();

        if(x > 0 && forest[x - 1][y] == TREE) ws.addLast(new int[]{x - 1, y});
        if(x < forest.length - 1 && forest[x + 1][y] == TREE) ws.addLast(new int[]{x + 1, y});
        if(y > 0 && forest[x][y - 1] == TREE) ws.addLast(new int[]{x, y - 1});
        if(y < forest[0].length - 1 && forest[x][y + 1] == TREE) ws.addLast(new int[]{x, y + 1});

        return ws;
    }

    public int burnForest(int[][] forest) {
        // TODO
        int[][] distances = new int[forest.length][forest[0].length];
        PriorityQueue<Pair> PQ = new PriorityQueue<Pair>();

        int ntrees = 0;

        int x, y;
        for(x = 0; x < forest.length; x++){
            for(y = 0; y < forest[0].length; y++){
                if(forest[x][y] == EMPTY) ntrees++;
                else if(forest[x][y] == BURNING){
                    ntrees++;
                    distances[x][y] = 0;
                    PQ.add(new Pair(x, y, 0));
                } else distances[x][y] = Integer.MAX_VALUE;
            }
        }

        int maxDistance = 0;

        while(!PQ.isEmpty()){
            Pair v = PQ.poll();

            for(int[] xy : adj(forest, v.x, v.y)){
                if(distances[xy[0]][xy[1]] > distances[v.x][v.y] + 1){
                    if(distances[xy[0]][xy[1]] == Integer.MAX_VALUE) ntrees++;

                    distances[xy[0]][xy[1]] = distances[v.x][v.y] + 1;
                    PQ.add(new Pair(xy[0], xy[1], distances[v.x][v.y] + 1));

                    if(maxDistance < distances[xy[0]][xy[1]]) maxDistance = distances[xy[0]][xy[1]];
                }
            }
        }

        return ntrees == forest.length * forest[0].length ? maxDistance : -1;
    }
}