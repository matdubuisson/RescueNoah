package sorting;

import org.javagrader.Grade;
import org.javagrader.CustomGradingResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.*;

@Grade
public class CardSorterTest {


    @Test
    public void testExample() {
        // 7, 8, 2, 22, 102, 1
        LinkedListImpl l = new LinkedListImpl(new int[]{7, 8, 2, 22, 102, 1});
        // LinkedListImpl l = new LinkedListImpl(new int[]{2, 1, 4, 3, 5});
        CardSorter.sort(l);
        System.out.println(l.toString());
        assertTrue(l.isSorted());
    }

}


