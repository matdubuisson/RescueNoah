package sorting;

import org.javagrader.Grade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*normal import*/
import java.util.Random;



/**
 * Created by johnaoga on 22/10/2018.
 * 
 * updated to junit5: 11/09/2023
 */
@Grade
public class GlobalWarmingImplTest {


    public int[][] getRandomMatrix(){
        Random random = new Random();

        int n = 20;

        int[][] matrix = new int[n][n];

        for(int i = 0, j; i < n; i++){
            for(j = 0; j < n; j++){
                matrix[i][j] = Math.abs(random.nextInt() % 100);
            }
        }

        return matrix;
    }

    public int getCount(int[][] matrix, int level){
        int count = 0;

        for(int i = 0, j; i < matrix.length; i++){
            for(j = 0; j < matrix.length; j++){
                if(matrix[i][j] > level){
                    count++;
                }
            }
        }

        return count;
    }

    @Test
    @Grade(value=1)
    public void testSimpleCase() {
        int[][] matrix = new int[][]{
                {1, 3, 4, 7},
                {7, 1, 0, 2},
                {6, 4, 1, 2},
                {5, 4, 4, 0} // 0, 0, 1, 1, 1, 2, 2, 3, 4, 4, 4, 4, 5, 6, 7, 7
        };

        GlobalWarming global = new GlobalWarmingImpl(matrix);
        assertEquals(8, global.nbSafePoints(3));
        assertEquals(0, global.nbSafePoints(10));
    }

    public void print_matrix(int[][] matrix){
        System.out.println("{");
        for(int i = 0, j; i < matrix.length; i++){
            System.out.print("{");
            for(j = 0; j < matrix.length; j++){
                System.out.print("[" + matrix[i][j] + "]");
            }
            System.out.println("}");
        }
        System.out.println("}");
    }

    @Test
    @Grade(value=1)
    public void testComplexCase(){
        int[][] matrix = new int[][]{
                {56, 51,61,71,79,39,59,65,68,19,38,56,37,93,68,14,30,3,16,35},
                {66,80,56,35,46,4,14,33,55,48,14,29,63,42,78,97,86,32,25,86},
                {61,12,87,54,78,34,77,98,12,33,43,50,30,92,45,7,46,58,56,16},
                {4,17,13,48,27,82,32,64,45,35,91,4,58,82,71,27,34,91,35,75},
                {15,49,14,31,82,91,42,80,79,97,76,76,80,76,79,45,64,76,87,0},
                {92,97,63,35,82,79,98,27,13,2,38,69,92,78,78,40,22,50,68,50},
                {92,95,87,19,57,66,55,62,30,55,34,89,57,87,8,85,10,52,97,43},
                {62,0,13,73,66,53,95,30,62,49,70,51,38,75,11,51,16,46,34,14},
                {2,97,31,34,98,25,53,0,20,45,73,47,11,41,19,28,31,89,92,90},
                {38,40,96,83,57,54,71,86,53,56,42,81,38,15,59,67,33,18,81,78},
                {2,11,30,59,90,64,82,36,93,82,22,2,43,7,63,71,35,15,60,10},
                {59,73,95,43,24,8,99,22,43,13,63,78,85,52,81,53,42,1,9,91},
                {35,25,67,4,84,15,14,96,41,99,62,27,12,61,26,4,21,55,11,9},
                {73,69,73,60,35,93,69,15,99,36,38,35,77,86,89,95,20,40,13,68},
                {26,76,30,47,82,87,26,76,11,41,78,63,97,79,95,9,2,27,30,78},
                {37,46,86,22,47,33,3,30,6,20,92,74,11,73,33,32,6,55,40,52},
                {15,18,25,16,13,26,28,57,95,69,39,93,79,79,84,78,74,37,86,87},
                {69,8,64,18,8,94,35,96,28,46,40,75,37,53,71,44,86,33,89,23},
                {38,80,27,44,5,62,93,43,64,13,3,41,48,88,35,12,13,27,11,84},
                {16,71,11,93,5,82,82,70,14,75,30,75,39,29,94,56,12,9,47,20}
        };

        GlobalWarming g = new GlobalWarmingImpl(matrix);
        assertEquals(getCount(matrix, 10), g.nbSafePoints(10));
        assertEquals(getCount(matrix, 30), g.nbSafePoints(30));
        assertEquals(getCount(matrix, 70), g.nbSafePoints(70));
        assertEquals(getCount(matrix, 100), g.nbSafePoints(100));
        assertEquals(getCount(matrix, 200), g.nbSafePoints(200));
        assertEquals(getCount(matrix, 0), g.nbSafePoints(0));
        assertEquals(getCount(matrix, -1), g.nbSafePoints(-1));
    }

    @Test
    @Grade(value = 1)
    public void testRandomCase() {
        int level;
        int[][] matrix = getRandomMatrix();
        GlobalWarming global;
        Random random = new Random();

        for(int length = 0; length < 10; length++){
            for(int i = 0; i < length; i++) {
                level = Math.abs(random.nextInt() % 100);
                global = new GlobalWarmingImpl(matrix);
                assertEquals(getCount(matrix, level), global.nbSafePoints(level));
            }
        }
    }
}
