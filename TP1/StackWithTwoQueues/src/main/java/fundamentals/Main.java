package fundamentals;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<Integer> q = new ArrayDeque();
        System.out.println(q.toString());
        for (int i = 0; i < 5; i++) {
            q.add(i*i);
            System.out.println(q.toString());
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(q.remove());
        }
    }
}
