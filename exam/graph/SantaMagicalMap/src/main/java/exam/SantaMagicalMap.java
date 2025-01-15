package exam;


import java.util.PriorityQueue;

/**
 * Santa Claus is streamlining his delivery routes with a magical map.
 * This year, the Elves' initial calculations for the distances between houses are a bit off, failing to respect the triangle inequality,
 * which states that the direct path between two points should not be longer than any indirect path.
 * However, Santa also wants to ensure that the new distances do not imply a shorter path than originally calculated,
 * keeping the realism of actual travel distances.
 *
 * Santa needs this map adjusted to balance efficiency and realism.
 * Your task is to modify the magical map so that it adheres to the triangle inequality
 * while ensuring that no direct path becomes shorter than in the original map.
 * In other terms it means that for all pairs (i, j) the value
 * the cost i->j must be the shortest path between i and j in the original map.
 */
public class SantaMagicalMap {


    /**
     * Adjust minimally the distance matrix to satisfy the triangle inequality.
     * The cost i->j must become the shortest path between i and j in the original map.
     *
     *
     * @param matrix The distance matrix, matrix[i][j] is the distance between house i and house j.
     *               It is a square matrix with the same number of rows and columns.
     *               The matrix is not necessarily symmetric, i.e. matrix[i][j] != matrix[j][i],
     *               but it is zero on the diagonal, i.e. matrix[i][i] = 0.
     */
    private static class Pair implements Comparable<Pair>{
        int source, distance;

        public Pair(int source, int distance){
            this.source = source;
            this.distance = distance;
        }

        @Override
        public int compareTo(Pair other){
            return this.distance - other.distance;
        }
    }

    private static int[] dijkstra(int[][] matrix, int source){
        int[] distances = new int[matrix.length];
        for(int i = 0; i < distances.length; i++) distances[i] = Integer.MAX_VALUE;
        distances[source] = 0;

        PriorityQueue<Pair> PQ = new PriorityQueue<Pair>();
        PQ.add(new Pair(source, 0));

        while(!PQ.isEmpty()){
            int v = PQ.poll().source;

            for(int w = 0; w < matrix.length; w++){ // Simple adj function
                if(v != w && distances[w] > distances[v] + matrix[v][w]){
                    distances[w] = distances[v] + matrix[v][w];
                    PQ.add(new Pair(w, distances[w]));
                }
            }
        }

        return distances;
    }

    public static void adjustDistanceMatrix(int[][] matrix){
        for(int v = 0; v < matrix.length; v++){
            int[] distances = dijkstra(matrix, v);

            for(int w = 0; w < matrix.length; w++){
                if(distances[w] < matrix[v][w]){
                    matrix[v][w] = distances[w];
                }
            }
        }
    }
}
