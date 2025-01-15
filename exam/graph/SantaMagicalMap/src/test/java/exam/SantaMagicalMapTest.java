package exam;

import org.javagrader.ConditionalOrderingExtension;
import org.javagrader.Grade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ConditionalOrderingExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Grade
class SantaMagicalMapTest {

    @Test
    @Order(0)
    @Grade(value = 1, cpuTimeout = 2000)
    void testSmallMatrixAdjustment() {
        int[][] original = {
                {0, 10, 50},
                {10, 0, 20},
                {15, 20, 0}};
        int[][] adjusted = {
                {0, 10, 15},
                {10, 0, 20},
                {15, 20, 0}};

        SantaMagicalMap.adjustDistanceMatrix(adjusted);

        // Check if the adjusted distances respect the original distances
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                assertTrue(adjusted[i][j] <= original[i][j]);
            }
        }
    }

    @Test
    @Order(0)
    @Grade(value = 1, cpuTimeout = 2000)
    void testTriangleInequality() {



        int[][] matrix = {
                {0, 3, 10},
                {3, 0, 7},
                {10, 7, 0}};
        SantaMagicalMap.adjustDistanceMatrix(matrix);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    if (i != j && j != k && i != k) {
                        assertTrue(matrix[i][k] <= matrix[i][j] + matrix[j][k]);
                    }
                }
            }
        }
    }


    @Test
    @Order(1)
    @Grade(value = 1, cpuTimeout = 2000)
    void testMatchExpected(){
        // If we are using all pairs shortest path the final matrix can be known

        int [][] original =  {{0, 1 , 10, 5},
                {11, 0, 4, 12},
                {15, 12, 0, 2},
                {12, 13, 14, 0}};

        int [][] adjusted = {{0, 1 , 5, 5},
                {11, 0, 4, 6},
                {14, 12, 0, 2},
                {12, 13, 14, 0}};
        SantaMagicalMap.adjustDistanceMatrix(original);
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                assertEquals(adjusted[i][j], original[i][j]);
            }
        }

    }

    @Test
    @Order(1)
    @Grade(value = 1, cpuTimeout = 2000)
    void testAnotherInequality() {



        int[][] matrix = {{0, 1 , 10, 5},
                {11, 0, 4, 12},
                {15, 12, 0, 2},
                {12, 13, 14, 0}};
        SantaMagicalMap.adjustDistanceMatrix(matrix);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    if (i != j && j != k && i != k) {
                        assertTrue(matrix[i][k] <= matrix[i][j] + matrix[j][k]);
                    }
                }
            }
        }
    }

}
