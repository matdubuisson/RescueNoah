package graphs;


import java.util.LinkedList;

/**
 * In this exercise, we revisit the GlobalWarming
 * class from the sorting package.
 * You are still given a matrix of altitude in
 * parameter of the constructor, with a water level.
 * All the entries whose altitude is under, or equal to,
 * the water level are submerged while the other constitute small islands.
 *
 * For example let us assume that the water
 * level is 3 and the altitude matrix is the following
 *
 *      | 1 | 3 | 3 | 1 | 3 |
 *      | 4 | 2 | 2 | 4 | 5 |
 *      | 4 | 4 | 1 | 4 | 2 |
 *      | 1 | 4 | 2 | 3 | 6 |
 *      | 1 | 1 | 1 | 6 | 3 |
 * 
 * If we replace the submerged entries
 * by _, it gives the following matrix
 *
 *      | _ | _ | _ | _ | _ |
 *      | 4 | _ | _ | 4 | 5 |
 *      | 4 | 4 | _ | 4 | _ |
 *      | _ | 4 | _ | _ | 6 |
 *      | _ | _ | _ | 6 | _ |
 *
 * The goal is to implement two methods that
 * can answer the following questions:
 *      1) Are two entries on the same island?
 *      2) How many islands are there
 *
 * Two entries above the water level are
 * connected if they are next to each other on
 * the same row or the same column. They are
 * **not** connected **in diagonal**.
 * Beware that the methods must run in O(1)
 * time complexity, at the cost of a pre-processing in the constructor.
 * To help you, you'll find a `Point` class
 * in the utils package which identified an entry of the grid.
 * Carefully read the expected time complexity of the different methods.
 */
public class GlobalWarming {
    private int[][] altitude;
    private int waterlevel;

    private boolean[] marked;
    private int id;
    private int[] ids;

    /**
     * Constructor. The run time of this method is expected to be in 
     * O(n x log(n)) with n the number of entry in the altitude matrix.
     *
     * @param altitude the matrix of altitude
     * @param waterLevel the water level under which the entries are submerged
     */
    public GlobalWarming(int [][] altitude, int waterLevel) {
        this.altitude = altitude;
        this.waterlevel = waterLevel;

        this.id = 1;
        this.ids = new int[this.altitude.length * this.altitude[0].length];

        this.marked = new boolean[this.ids.length];

        for(int v = 0; v < this.ids.length; v++){
            if(!this.marked[v] && this.altitude[v / this.altitude.length][v % this.altitude.length] > this.waterlevel){
                this.dfs(v);
                this.id++;
            }
        }
    }

    private Iterable<Integer> adj(int v){
        int x = v / this.altitude[0].length, y = v % this.altitude[0].length;
        LinkedList<Integer> ws = new LinkedList<Integer>();

        if(x - 1 >= 0 && this.altitude[x - 1][y] > this.waterlevel) ws.add(getPosition(x - 1, y));

        if(x + 1 < this.altitude.length && this.altitude[x + 1][y] > this.waterlevel) ws.add(getPosition(x + 1, y));

        if(y - 1 >= 0 && this.altitude[x][y - 1] > this.waterlevel) ws.add(getPosition(x, y - 1));

        if(y + 1 < this.altitude[0].length && this.altitude[x][y + 1] > this.waterlevel) ws.add(getPosition(x , y + 1));

        return ws;
    }

    public void dfs(int v){
        this.marked[v] = true;
        this.ids[v] = this.id;

        for(Integer w : this.adj(v)){
            if(!this.marked[w]){
                this.dfs(w);
            }
        }
    }

    public int getPosition(int x, int y){
        return this.altitude[0].length * x + y;
    }

    /**
     * Returns the number of island
     *
     * Expected time complexity O(1)
     */
    public int nbIslands() {
         return this.id - 1;
    }

    /**
     * Return true if p1 is on the same island as p2, false otherwise
     *
     * Expected time complexity: O(1)
     *
     * @param p1 the first point to compare
     * @param p2 the second point to compare
     */
    public boolean onSameIsland(Point p1, Point p2) {
        int id1 = this.ids[getPosition(p1.getX(), p1.getY())], id2 = this.ids[getPosition(p2.getX(), p2.getY())];

        return id1 > 0 && id2 > 0 && id1 == id2;
    }


    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
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
