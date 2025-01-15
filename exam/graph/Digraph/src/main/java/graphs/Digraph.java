package graphs;


import java.util.LinkedList;

/**
 * Implement the Digraph.java interface in
 * the Digraph.java class using an adjacency list
 * data structure to represent directed graphs.
 */
public class Digraph {
    private int V, E;
    private LinkedList<Integer>[] adjs;

    public Digraph(int V) {
        // TODO
        this.V = V;
        this.E = 0;
        this.adjs = new LinkedList[V];

        for(int i = 0; i < V; i++){
            this.adjs[i] = new LinkedList<Integer>();
        }
    }

    /**
     * The number of vertices
     */
    public int V() {
        // TODO
        return this.V;
    }

    /**
     * The number of edges
     */
    public int E() {
        // TODO
        return this.E;
    }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w) {
        // TODO
        this.E++;
        this.adjs[v].add(w);
    }

    /**
     * The nodes adjacent to node v
     * that is the nodes w such that there is an edge v->w
     */
    public Iterable<Integer> adj(int v) {
        // TODO
        return this.adjs[v];
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse() {
        // TODO
        Digraph rg = new Digraph(this.V);

        for(int v = 0; v < this.V; v++){
            for(Integer w : this.adjs[v]){
                rg.addEdge(w, v);
            }
        }

        return rg;
    }

}
