package fundamentals;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.*;


@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
public class CircularLinkedListTest {


    @Grade(value=0.5)
    @Order(0)
    @Test
    public void simpleTestWithoutRemove(){
        // Build a circular linked list as follows
        // 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9
        CircularLinkedList<Integer> student = new CircularLinkedList<>();
        List<Integer> correct = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            student.enqueue(i);
            correct.add(i);
        }
        Iterator<Integer> aIter = student.iterator();
        Iterator<Integer> bIter = correct.iterator();
        assertEquals(correct.size(),student.size());
        while (bIter.hasNext()) {
            assertTrue(aIter.hasNext());
            assertEquals(bIter.next(),aIter.next());
        }
    }

    @Order(0)
    @Test
    public void test_size_enqueue_remove_methods(){
        CircularLinkedList<Integer> lst = new CircularLinkedList<>();

        assertEquals(lst.size(), 0);

        int i;

        for(i = 0; i < 10; i++){
            lst.enqueue(i * i);
            lst.print();
            assertEquals(lst.size(), i + 1);
        }

        assertEquals(lst.remove(lst.size() - 1), 81);
        assertEquals(lst.size(), 9);

        assertEquals(lst.remove(0), 0);
        assertEquals(lst.size(), 8);

        assertEquals(lst.remove(1), 4);
        assertEquals(lst.size(), 7);

        assertEquals(lst.remove(3), 25);
        assertEquals(lst.size(), 6);
    }

    @Order(1)
    @Test
    public void test_iterator(){
        CircularLinkedList<Integer> lst = new CircularLinkedList<>();

        int i;
        for(i = 0; i < 10; i++){
            lst.enqueue(i * i);
        }

        Iterator<Integer> iterator = lst.iterator();

        i = 0;
        while(iterator.hasNext()){
            assertEquals(i * i, iterator.next());
            i++;
        }
    }

    @Order(1)
    @Test
    public void test_concurent_exception(){
        CircularLinkedList<Integer> lst = new CircularLinkedList<>();

        for(int i = 0; i < 10; i++){
            lst.enqueue(i * i);
        }

        Exception exception = assertThrows(ConcurrentModificationException.class, () -> {
            Iterator<Integer> iterator = lst.iterator();

            Integer value;
            while(iterator.hasNext()){
                lst.enqueue(14);
                value = iterator.next();
            }
        });
    }

    @Grade(value=0.5)
    @Order(0)
    @Test
    public void simpleTestWithRemoving(){
        // Build a circular linked list as follows
        // 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 .. -> 49
        CircularLinkedList<Integer> student = new CircularLinkedList<>();
        List<Integer> correct = new LinkedList<>();
        for (int i = 0; i < 50; i++) {
            student.enqueue(i);
            correct.add(i);
        }

        // Remove 0, 10, [End], 25, 30
        student.remove(0);
        correct.remove(0);
        student.remove(10);
        correct.remove(10);
        student.remove(correct.size() - 1);
        correct.remove(correct.size() - 1);
        student.remove(25);
        correct.remove(25);
        student.remove(30);
        correct.remove(30);


        Iterator<Integer> aIter = student.iterator();
        Iterator<Integer> bIter = correct.iterator();
        assertEquals(correct.size(),student.size());
        while (bIter.hasNext()) {
            assertTrue(aIter.hasNext());
            assertEquals(bIter.next(),aIter.next());
        }
    }
}
