package graphs;

import java.util.LinkedList;
import java.util.PriorityQueue;

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
 * represents the position x=a/m and y=a%m.
 * If the start or end position is a wall
 * or if there is no path, an empty Iterable must be returned.
 * The same applies if there is no path
 * between the origin and the destination.
 */
public class Maze {
    public static boolean isWall(int[][] maze, int x, int y){
        if(x < 0 || x >= maze.length) return true;
        else if(y < 0 || y >= maze[0].length) return true;
        else if(maze[x][y] == 1) return true;
        else return false;
    }

    public static int getPosition(int[][] maze, int x, int y){
        return maze[0].length * x + y;
    }

    private static Iterable<Integer> adj(int[][] maze, int v){
        int x = v / maze[0].length, y = v % maze[0].length;
        LinkedList<Integer> ws = new LinkedList<Integer>();

        if(!isWall(maze, x + 1, y)) ws.add(getPosition(maze, x + 1, y));
        if(!isWall(maze, x - 1, y)) ws.add(getPosition(maze, x - 1, y));
        if(!isWall(maze, x, y + 1)) ws.add(getPosition(maze, x , y + 1));
        if(!isWall(maze, x, y - 1)) ws.add(getPosition(maze, x, y - 1));

        return ws;
    }

    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        // TODO
        LinkedList<Integer> path = new LinkedList<Integer>();

        if(isWall(maze, x1, y1) || isWall(maze, x2, y2)) return path;

        if(x1 == x2 && y1 == y2){
            path.add(getPosition(maze, x1, y1));
            return path;
        }

        // Use BFS

        int v = getPosition(maze, x1, y1);
        boolean[] marked = new boolean[maze.length * maze[0].length];
        marked[v] = true; // init
        int[] edgeTo = new int[marked.length];
        edgeTo[v] = -1; // init
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.addLast(v);

        int end = getPosition(maze, x2, y2);

        while(!queue.isEmpty()){
            v = queue.removeFirst();

            //System.out.println("===========");
            for(Integer w : adj(maze, v)){
                if(!marked[w]){
                    //System.out.format("x = %d; y = %d\n", w / maze[0].length, w % maze[0].length);
                    //System.out.format("v => %d; w => %d\n", v, (int) w);
                    marked[w] = true;
                    edgeTo[w] = v;
                    queue.addLast(w);
                }
            }
        }

        if(!marked[end]) return path;

        path.addFirst(end);
        v = getPosition(maze, x1, y1);

        //System.out.format("x = %d; y = %d\n", end / maze[0].length, end % maze[0].length);

        do{
            end = edgeTo[end];
            //System.out.format("x = %d; y = %d\n", end / maze[0].length, end % maze[0].length);
            path.addFirst(end);
        } while(end != v);

        return path;
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
}
