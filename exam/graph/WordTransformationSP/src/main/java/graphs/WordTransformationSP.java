package graphs;

import java.util.*;

/**
 * You are asked to implement the WordTransformationSP
 * class which allows to find the shortest path
 * from a string A to another string B
 * (with the certainty that there is a path from A to B).
 * To do this, we define a rotation(x, y) operation that
 * reverses the order of the letters between the x and y positions (not included).
 * For example, with A=``HAMBURGER``, if we do rotation(A, 4, 8), we get HAMBEGRUR.
 * So you can see that the URGE sub-string
 * has been inverted to EGRU and the rest of the string
 * has remained unchanged: HAMB + ECRU + R = HAMBEGRUR.
 * Let's say that a rotation(x, y) has a cost of y-x.
 * For example going from HAMBURGER to HAMBEGRUR costs 8-4 = 4.
 * The question is what is the minimum cost to reach a string B from A?
 * So you need to implement a public static int minimalCost(String A, String B)
 * function that returns the minimum cost to reach String B
 * from A using the rotation operation.
 */
public class WordTransformationSP {

    /**
     * Rotate the substring between start and end of a given string s
     * eg. s = HAMBURGER, rotation(s,4,8) = HAMBEGRUR i.e. HAMB + EGRU + R
     */
    /**
     * Rotate the substring between start and end of a given string s
     * eg. s = HAMBURGER, rotation(s,4,8) = HAMBEGRUR i.e. HAMB + EGRU + R
     *
     * @param s
     * @param start
     * @param end
     * @return rotated string
     */
    public static String rotation(String s, int start, int end) {
        return s.substring(0, start) + new StringBuilder(s.substring(start, end)).reverse().toString() + s.substring(end);
    }

    /**
     * Compute the minimal cost from string "from" to string "to" representing the shortest path
     *
     * @param from
     * @param to
     * @return
     */

    private static class Pair implements Comparable<Pair>{
        public String word;
        public int distance;

        public Pair(String word, int distance){
            this.word = word;
            this.distance = distance;
        }

        @Override
        public int compareTo(Pair other) {
            return this.distance - other.distance;
        }
    }

    private static Iterable<Pair> adj(HashSet<String> marked, Pair v){
        LinkedList<Pair> ws = new LinkedList<Pair>();
        String newWord;

        for(int perm = 2, i; perm <= v.word.length(); perm++){
            for(i = 0; i + perm <= v.word.length(); i++){
                newWord = rotation(v.word, i, i + perm);

                if(!marked.contains(newWord)) ws.add(new Pair(newWord, v.distance + perm));
            }
        }

        return ws;
    }

    public static int minimalCost(String from, String to) {
        // TODO
        HashMap<String, Integer> distances = new HashMap<String, Integer>(); // null == INFINITY
        distances.put(from, 0);

        HashSet<String> marked = new HashSet<String>();
        marked.add(from);

        PriorityQueue<Pair> PQ = new PriorityQueue<Pair>();
        PQ.add(new Pair(from, 0));

        while(!PQ.isEmpty()){
            Pair v = PQ.poll();

            if(v.word.equals(to)) break;

            for(Pair w : adj(marked, v)){
                Integer distance = distances.get(w.word);

                if(distance == null || distance > w.distance){
                    distances.put(w.word, w.distance);
                    PQ.add(w);
                }
            }
        }

        Integer distance = distances.get(to);
        return distance == null ? Integer.MAX_VALUE : distance;
    }
}
