package graphs;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,5,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Given a global water level, all positions in the matrix
 * with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3,
 * all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4),(5),(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is
 * a method that find a safe-path between
 * two positions (row,column) on the matrix.
 * The path assume you only make horizontal or vertical moves
 * but not diagonal moves.
 *
 * For a water level of 4, the shortest path
 * between (1,0) and (1,3) is
 * (1,0) -> (2,0) -> (2,1) -> (2,2) -> (2,3) -> (1,3)
 *
 *
 * Complete the code below so that the {@code  shortestPath} method
 * works as expected
 */
public class GlobalWarmingPaths {

    int waterLevel;
    int [][] altitude;

    public GlobalWarmingPaths(int[][] altitude, int waterLevel) {
        this.waterLevel = waterLevel;
        this.altitude = altitude;
        // TODO
    }

    /**
     * Computes the shortest path between point p1 and p2
     * @param p1 the starting point
     * @param p2 the ending point
     * @return the list of the points starting
     *         from p1 and ending in p2 that corresponds
     *         the shortest path.
     *         If no such path, an empty list.
     */
    private boolean isValid(Point p){
        if(p.x < 0 || p.x >= this.altitude.length) return false;
        else if(p.y < 0 || p.y >= this.altitude[0].length) return false;
        else if(this.altitude[p.x][p.y] <= this.waterLevel) return false;
        else return true;
    }

    private Iterable<Point> adj(Point v){
        LinkedList<Point> ws = new LinkedList<Point>();

        Point p = new Point(v.x - 1, v.y);
        if(isValid(p)) ws.add(p);

        p = new Point(v.x + 1, v.y);
        if(isValid(p)) ws.add(p);

        p = new Point(v.x, v.y - 1);
        if(isValid(p)) ws.add(p);

        p = new Point(v.x, v.y + 1);
        if(isValid(p)) ws.add(p);

        return ws;
    }

    private int getPosition(Point p){
        return p.x * this.altitude[0].length + p.y;
    }

    public List<Point> shortestPath(Point p1, Point p2) {
        // TODO
        // expected time complexity O(n^2)
        LinkedList<Point> path = new LinkedList<Point>();

        if(!isValid(p1) || !isValid(p2)) return path;

        int pos = getPosition(p1);

        boolean[] marked = new boolean[this.altitude.length * this.altitude[0].length];
        marked[pos] = true;

        Point[] edgeTo = new Point[marked.length];
        edgeTo[pos] = p1;

        LinkedList<Point> queue = new LinkedList<Point>();
        queue.addLast(p1);

        while(!queue.isEmpty()){
            Point v = queue.removeFirst();

            for(Point w : this.adj(v)){
                pos = getPosition(w);

                if(!marked[pos]){
                    marked[pos] = true;
                    edgeTo[pos] = v;
                    queue.addLast(w);
                }
            }
        }

        if(!marked[getPosition(p2)]) return path;

        Point p = p2;

        while(!p.equals(p1)){ // Attention possible que p1 != p2 && p1.equals(p2)
            path.addFirst(p);
            p = edgeTo[getPosition(p)];
        }

        path.addFirst(p1);

        return path;
    }

    /**
     * This class represent a point in a 2-dimension discrete plane.
     * This is used to identify the cells of a grid
     * with X = row, Y = column
     */
    static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }
    }
}
