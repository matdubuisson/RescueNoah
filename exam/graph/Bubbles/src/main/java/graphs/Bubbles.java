package graphs;

import java.util.*;

/**
 * Sophie and Marc want to reduce the bubbles
 * of contacts in the belgian population
 * to contain an evil virus (weird idea but
 * nevertheless inspired by a true belgian
 * story in 2020, don't ask ...).
 *
 * Help them!
 *
 * The Belgian government has imposed on the
 * population to limit the number of contacts, via "bubbles".
 *
 * The principle is quite simple:
 * If you have a (close) contact with someone,
 * You are then in his bubble, and he is in yours.
 *
 * Let's say the following contacts have taken place: [
 * [Alice, Bob], [Alice, Carol], [Carol, Alice], [Eve, Alice], [Alice, Frank],
 * [Bob, Carole], [Bob, Eve], [Bob, Frank], [Bob, Carole], [Eve, Frank]
 * ].
 *
 * Note that the contacts are two-by-two and
 * can occur several times. The order within
 * of a contact does not matter.
 *
 * The resulting bubbles are :
 *
 * - Alice's bubble = [Bob, Carol, Eve, Frank]
 * - Bob's bubble = [Bob, Carol, Eve, Frank]
 * - Bob's bubble = [Alice, Carol, Eve, Frank]
 * - Carole's bubble = [Alice, Bob]
 * - Frank's Bubble = [Alice, Bob, Eve]
 *
 * Note that the relationship is symmetric
 * (if Alice is in Bob's bubble, then Bob is in Alice's bubble)
 * but not transitive (if Bob is in Alice's bubble,
 * then Bob is in Alice's bubble)
 * and Carol is in Bob's bubble, Carol is
 * not necessarily in Alice's.
 *
 * Since at most n people can be in someone's
 * bubble without him being outlaw
 * given a list of contacts, select pairs of people
 * that you will forbid to meet, so that eventually
 * each person has a bubble of NO MORE than n people
 * (not counting themselves).
 * You need to ban AS FEW AS POSSIBLE (pairs of) people to meet.
 *
 * For example, if n = 3, in the example above,
 * you could forbid Alice and Carol to see each other, but also
 * Bob and Carol. This removes 2 links
 * (even though Alice and Carol appear twice in the contacts!).
 * But there is a better solution: prevent Alice and Bob
 * from seeing each other, which only removes one link.
 * Finding an algorithm that solves this problem is complex,
 * that's why we give you a rather vague idea of an algorithm:
 *
 * - As long as there are links between two bubbles
 *   each "too big", remove one of these links;
 * - Then, as long as there are bubbles that are too big,
 *   remove any link connected to one of these bubbles
 *   (removing is equivalent to "adding the link
 *   to the list of forbidden relationships")
 *
 * Implementing this algorithm as it is a bad idea.
 * Think of an optimal way to implement it in the
 * method {@code cleanBubbles}
 *
 * You CANNOT modify the `contacts` list directly (nor the lists inside)
 * If you try, you will receive an UnsupportedOperationException.
 *
 */
public class Bubbles {

    /**
     *
     * @param contacts
     * @param n the number of persons in the population ranges from 0 to n-1 (included)
     * @return a list of people you are going to forbid to see each other.
     *         There MUST NOT be any duplicates.
     *         The order doesn't matter, both within the
     *         ForbiddenRelation and in the list.
     */
    private static void addEdge(HashMap<String, LinkedList<String>> adjs, Contact c){
        LinkedList<String> lst;

        lst = adjs.get(c.a);

        if(lst == null){
            lst = new LinkedList<String>();
            lst.add(c.b);
            adjs.put(c.a, lst);
        } else if(!lst.contains(c.b)) lst.add(c.b);

        lst = adjs.get(c.b);

        if(lst == null){
            lst = new LinkedList<String>();
            lst.add(c.a);
            adjs.put(c.b, lst);
        } else if(!lst.contains(c.a)) lst.add(c.a);
    }

    private static void delEdge(HashMap<String, LinkedList<String>> adjs, ForbiddenRelation c){
        LinkedList<String> lst;

        lst = adjs.get(c.a);

        if(lst.contains(c.b)) lst.remove(c.b);

        lst = adjs.get(c.b);

        if(lst.contains(c.a)) lst.remove(c.a);
    }

    private static LinkedList<String> copy(LinkedList<String> lst){
        LinkedList<String> nLst = new LinkedList<String>();

        for(String v : lst) nLst.add(v);

        return nLst;
    }

    public static List<ForbiddenRelation> cleanBubbles(List<Contact> contacts, int n) {
        // TODO
        // Generate a graph :
        LinkedList<ForbiddenRelation> rlst = new LinkedList<ForbiddenRelation>();

        if(contacts.isEmpty()) return rlst;

        HashMap<String, LinkedList<String>> adjs = new HashMap<String, LinkedList<String>>();

        for(Contact c : contacts){
            addEdge(adjs, c);
        }

        HashSet<String> marked = new HashSet<String>();
        marked.add(contacts.get(0).a);
        LinkedList<String> queue = new LinkedList<String>();
        queue.addLast(contacts.get(0).a);

        while(!queue.isEmpty()){
            String v = queue.removeFirst();

            for(String w : copy(adjs.get(v))){
                if(!marked.contains(w)){
                    marked.add(w);
                    queue.addLast(w);

                    if(adjs.get(v).size() > n && adjs.get(w).size() > n){
                        ForbiddenRelation r = new ForbiddenRelation(v, w);
                        delEdge(adjs, r);
                        rlst.add(r);
                    }
                }
            }
        }

        marked.clear();
        marked.add(contacts.get(0).a);
        queue.addLast(contacts.get(0).a);

        while(!queue.isEmpty()){
            String v = queue.removeFirst();

            for(String w : copy(adjs.get(v))){
                if(!marked.contains(w)){
                    marked.add(w);
                    queue.addLast(w);
                }

                if(adjs.get(v).size() > n && adjs.get(v).contains(w)){
                    System.out.format("size => %d; from = %s; to = %s\n", adjs.get(v).size(), v, w);
                    ForbiddenRelation r = new ForbiddenRelation(v, w);
                    delEdge(adjs, r);
                    rlst.add(r);
                }
            }
        }

        return rlst;
    }

    public static void main(String[] args) {
        /*
         *  * Let's say the following contacts have taken place: [
         * [Alice, Bob], [Alice, Carol], [Carol, Alice], [Eve, Alice], [Alice, Frank],
         * [Bob, Carole], [Bob, Eve], [Bob, Frank], [Bob, Carole], [Eve, Frank]
         * ].
         * */

        LinkedList<Contact> contacts = new LinkedList<Contact>();
        contacts.add(new Contact("A", "B")); //
        contacts.add(new Contact("B", "C"));
        contacts.add(new Contact("B", "D"));
        contacts.add(new Contact("A", "C")); //
        contacts.add(new Contact("A", "D")); //
        contacts.add(new Contact("A", "E"));
        contacts.add(new Contact("A", "F"));

        List<ForbiddenRelation> list = cleanBubbles(contacts, 2);

        for(ForbiddenRelation r : list){
            System.out.format("Separation %s <==> %s\n", r.a, r.b);
        }
    }
}



class Contact {
    public final String a, b;

    public Contact(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }
}

class ForbiddenRelation implements Comparable<ForbiddenRelation> {
    public final String a, b;

    public ForbiddenRelation(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ForbiddenRelation)
            return a.equals(((ForbiddenRelation) obj).a) && b.equals(((ForbiddenRelation) obj).b);
        return false;
    }

    @Override
    public int compareTo(ForbiddenRelation o) {
        if(a.equals(o.a))
            return b.compareTo(o.b);
        return a.compareTo(o.a);
    }
}
