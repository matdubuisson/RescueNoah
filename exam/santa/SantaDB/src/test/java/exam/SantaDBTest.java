package exam;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
class SantaDBTest {

        @Test
        @Grade(value=1, cpuTimeout=1)
        void testInnerJoinTimeComplexity() {
            // Prepare large test data
            int numberOfChildren = 10000; // Large number of children
            int numberOfGifts = 100000;   // Large number of gifts

            Child[] children = new Child[numberOfChildren*2];
            Gift[] gifts = new Gift[numberOfGifts];

            for (int i = 0; i < numberOfChildren*2; i++) {
                children[i] = new Child(i, "Child " + i);
            }

            for (int i = 0; i < numberOfGifts; i++) {
                gifts[i] = new Gift(i, i % numberOfChildren, "Gift " + i);
            }

            List<GiftAssignment> assignments = SantaDB.innerJoin(children, gifts);

            // Verify the number of assignments matches the expected size
            assertEquals(numberOfGifts, assignments.size(), "Number of assignments does not match");
            Set<Integer> giftAssigned = new HashSet<>();
            for (GiftAssignment assignment : assignments) {
                giftAssigned.add(assignment.giftId);
            }
            assertEquals(numberOfGifts, giftAssigned.size(), "Number of gifts assigned does not match");

            int [] giftPerChild = new int[numberOfChildren];
            for (GiftAssignment assignment: assignments) {
                giftPerChild[assignment.childId]++;
            }
            for (int i = 0; i < numberOfChildren; i++) {
                assertEquals(10,giftPerChild[i],"Number of gifts assigned to child " + i + " does not match");
            }
        }

    @Test
    @Grade(value=1, cpuTimeout=1)
    void testInnerJoinCorrectness() {
        // Example small data set
        Child[] children = {
                new Child(1, "Alice"),
                new Child(2, "Bob"),
                new Child(3, "Roger")
        };

        Gift[] gifts = {
                new Gift(1001, 1, "Toy Train"),
                new Gift(1002, 1, "Doll"),
                new Gift(1003, 2, "Bicycle")
        };

        List<GiftAssignment> assignments = SantaDB.innerJoin(children, gifts);

        // Expected results in a set for easy lookup
        Set<String> expectedAssignments = new HashSet<>();
        expectedAssignments.add("Alice (Child ID: 1) receives Toy Train (Gift ID: 1001)");
        expectedAssignments.add("Alice (Child ID: 1) receives Doll (Gift ID: 1002)");
        expectedAssignments.add("Bob (Child ID: 2) receives Bicycle (Gift ID: 1003)");

        // Verify each assignment is in the expected results
        for (GiftAssignment assignment : assignments) {
            assertTrue(expectedAssignments.contains(assignment.toString()), "Unexpected assignment: " + assignment);
        }

        // Verify the number of assignments matches the expected size
        assertEquals(expectedAssignments.size(), assignments.size(), "Number of assignments does not match");
    }



}