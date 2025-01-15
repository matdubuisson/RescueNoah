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

    public int[][] getSimpleMatrix(){
        Random random = new Random();

        int n = 10;
        int[][] matrix = new int[n][n];

        for(int i = 0, j; i < n; i++){
            for(j = 0; j < n; j++){
                matrix[i][j] = random.nextInt(100);
            }
        }

        return matrix;
    }

    public int nbSafePointsNaive(int[][] matrix, int level){
        int count = 0;

        for(int i = 0, j; i < matrix.length; i++){
            for(j = 0; j < matrix[i].length; j++){
                if(matrix[i][j] > level) count++;
            }
        }

        return count;
    }

    @Test
    @Grade(value=1)
    public void testSimpleAll() {
        int [][] matrix = getSimpleMatrix();
        GlobalWarming warming = new GlobalWarmingImpl(matrix);

        Random random = new Random();

        for(int i = 0, j; i < 100; i++){
            j = random.nextInt(100);

            assertEquals(nbSafePointsNaive(matrix, j), warming.nbSafePoints(j));
        }
    }


}
