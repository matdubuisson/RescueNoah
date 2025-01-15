package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Consider this class, BreadthFirstShortestPaths, which computes the shortest path between
 * multiple node sources and any node in an undirected graph using a BFS path.
 * The Graph class is already implemented and here it is:
 * <p>
 * public class Graph {
 * // @return the number of vertices
 * public int V() { }
 * <p>
 * // @return the number of edges
 * public int E() { }
 * <p>
 * // Add edge v-w to this graph
 * public void addEdge(int v, int w) { }
 * <p>
 * // @return the vertices adjacent to v
 * public Iterable<Integer> adj(int v) { }
 * <p>
 * // @return a string representation
 * public String toString() { }
 * }
 * <p>
 * You are asked to implement all the TODOs of the BreadthFirstShortestPaths class.
 */
public class BreadthFirstShortestPaths {

    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked; // marked[v] = is there an s-v path
    private int[] distTo;     // distTo[v] = number of edges shortest s-v path

    /**
     * Computes the shortest path between any
     * one of the sources and very other vertex
     *
     * @param G       the graph
     * @param sources the source vertices
     */
    public BreadthFirstShortestPaths(Graph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        bfs(G, sources);
    }

    // Breadth-first search from multiple
    private int[] bfs(Graph G, int v){
        boolean[] marked = new boolean[G.V()];
        marked[v] = true;

        int[] distTo = new int[G.V()];
        for(int i = 0; i < distTo.length; i++) distTo[i] = INFINITY;
        distTo[v] = 0;

        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(v);

        while(!queue.isEmpty()){
            v = queue.removeFirst();

            for(Integer w : G.adj(v)){
                if(!marked[w]){
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    queue.addLast(w);
                }
            }
        }

        return distTo;
    }

    private void bfs(Graph G, Iterable<Integer> sources) {
        // TODO
        for(Integer v : sources){
            int[] distTo = this.bfs(G, v);

            for(int i = 0; i < this.distTo.length; i++){
                if(distTo[i] < this.distTo[i]){
                    this.distTo[i] = distTo[i];
                }
            }
        }
    }

    /**
     * Is there a path between (at least one of) the sources and vertex v?
     *
     * @param v the vertex
     * @return true if there is a path, and false otherwise
     */
    public boolean hasPathTo(int v) {
        // TODO
        return this.distTo[v] != INFINITY;
    }

    /**
     * Returns the number of edges in a shortest path
     * between one of the sources and vertex v?
     *
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(int v) {
        // TODO
        return this.distTo[v];
    }

    static class Graph {

        private List<Integer>[] edges;

        public Graph(int nbNodes)
        {
            this.edges = (ArrayList<Integer>[]) new ArrayList[nbNodes];
            for (int i = 0;i < edges.length;i++)
            {
                edges[i] = new ArrayList<>();
            }
        }

        /**
         * @return the number of vertices
         */
        public int V() {
            return edges.length;
        }

        /**
         * @return the number of edges
         */
        public int E() {
            int count = 0;
            for (List<Integer> bag : edges) {
                count += bag.size();
            }

            return count/2;
        }

        /**
         * Add edge v-w to this graph
         */
        public void addEdge(int v, int w) {
            edges[v].add(w);
            edges[w].add(v);
        }

        /**
         * @return the vertices adjacent to v
         */
        public Iterable<Integer> adj(int v) {
            return edges[v];
        }
    }
}
