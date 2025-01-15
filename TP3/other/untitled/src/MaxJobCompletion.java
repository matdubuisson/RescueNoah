import java.util.Arrays;

// Ex 2.1.2 : see paper

public class MaxJobCompletion {
    public static double calculate(int[] array){
        Arrays.sort(array);
        double average = 0;

        for(int i = 0; i < array.length; i++){
            average += array[i] / array.length;
        }

        return average;
    }
}
