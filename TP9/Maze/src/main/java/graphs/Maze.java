package graphs;

import java.util.*;

/**
 * We are interested in solving a maze represented
 * by a matrix of integers 0-1 of size nxm.
 * This matrix is a two-dimensional array.
 * An entry equal to '1' means that there
 * is a wall and therefore this position is not accessible,
 * while '0' means that the position is free.
 * We ask you to write a Java code to discover
 * the shortest path between two coordinates
 * on this matrix from (x1, y1) to (x2, y2).
 * The moves can only be vertical (up/down) or horizontal (left/right)
 * (not diagonal), one step at a time.
 * The result of the path is an Iterable of
 * coordinates from the origin to the destination.
 * These coordinates are represented by integers
 * between 0 and n * m-1, where an integer 'a'
 * represents the position x =a/m and y=a%m.
 * If the start or end position is a wall
 * or if there is no path, an empty Iterable must be returned.
 * The same applies if there is no path
 * between the origin and the destination.
 */
public class Maze {
    /*
    * 0 1 2
    * 3 4 5
    * 6 7 8
    * */
    public static int toIndex(int[][] matrix, int x, int y){
        return matrix.length * y + x;
    }

    public static int[] toXY(int[][] matrix, int i){
        return new int[]{i / matrix.length, i % matrix[0].length};
    }

    public static boolean isValid(int[][] matrix, int x, int y){
        return 0 <= x && x < matrix[0].length && 0 <= y && y < matrix.length && matrix[y][x] == 0;
    }

    public static boolean isValid(int[][] matrix, int i){
        int[] xy = toXY(matrix, i);
        return isValid(matrix, xy[0], xy[1]);
    }

    public static int[][] getNeighbors(int[][] matrix, int x, int y){
        return new int[][]{
                isValid(matrix, x - 1, y) ? new int[]{x - 1, y} : null,
                isValid(matrix, x + 1, y) ? new int[]{x + 1, y} : null,
                isValid(matrix, x, y - 1) ? new int[]{x, y - 1} : null,
                isValid(matrix, x, y + 1) ? new int[]{x, y + 1} : null
        };
    }

    public static int[][] getNeighbors(int[][] matrix, int i){
        int[] xy = toXY(matrix, i);
        return getNeighbors(matrix, xy[0], xy[1]);
    }

    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        if(!isValid(maze, x1, y1) || !isValid(maze, x2, y2)) return new ArrayList<>();

        int src = toIndex(maze, x1, y1), dest;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.addFirst(src);

        boolean[] visited = new boolean[maze.length * maze[0].length];
        visited[src] = true;

        int[] edgeTo = new int[visited.length];

        int[] xy;
        while(!queue.isEmpty()){
            src = queue.removeFirst();
            xy = toXY(maze, src);

            for(int[] xyn : getNeighbors(maze, xy[0], xy[1])){
                if(xyn == null) continue;

                dest = toIndex(maze, xyn[0], xyn[1]);

                System.out.println("==================");
                System.out.println(Arrays.toString(xy));
                System.out.println(Arrays.toString(xyn));

                if(!visited[dest]){
                    queue.addLast(dest);
                    visited[dest] = true;
                    edgeTo[dest] = src;
                }

                System.out.println(queue.isEmpty());
            }
        }

        src = toIndex(maze, x1, y1);
        dest = toIndex(maze, x2, y2);
        while(src != dest){
            queue.addFirst(dest);
            dest = edgeTo[dest];
        }

        System.out.println(Arrays.toString(edgeTo));

        return null;
    }

    public static int ind(int x, int y, int lg) {
        return x * lg + y;
    }

    public static int row(int pos, int mCols) {
        return pos / mCols;
    }

    public static int col(int pos, int mCols) {
        return pos % mCols;
    }

    public static int[][] maze1 = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 1, 0, 0},
            {1, 1, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 0}
    };

    public static int[][] maze2 = new int[][]{
            {0, 0, 0, 1, 0, 0, 0},
            {1, 1, 0, 0, 0, 1, 0}
    };

    public static void main(String[] args) {
        Iterable<Integer> iter = shortestPath(maze2, 0, 0, maze2[0].length - 1, maze2.length - 1);

        if(iter == null) return;

        for(Integer node : iter){
            System.out.println(node);
        }
    }
}
