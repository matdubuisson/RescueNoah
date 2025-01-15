import java.util.StringTokenizer;

import fundamentals.*;

public class InversePolish {
    public static void main(String[] args) {
        // String in = "4 20 + 3 5 1 * * +";
        String in = "1 4 + 15 + 5 * 5 2 * * 1000 8000 + +"; // Should be 10000
        StringTokenizer tokenizer = new StringTokenizer(in);
        Stack<Integer> stack = new ArrayStack<Integer>();
        String element;

        while (tokenizer.hasMoreTokens()) {
            element = tokenizer.nextToken();

            if(element.equals("+")){
                stack.push(stack.pop() + stack.pop());
            } else if(element.equals("*")){
                stack.push(stack.pop() * stack.pop());
            } else {
                stack.push(Integer.parseInt(element));
            }
        }

        System.out.println("Result => " + stack.pop());
        return;
    }
}
