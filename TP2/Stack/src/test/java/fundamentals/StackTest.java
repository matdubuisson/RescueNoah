package fundamentals;

import org.javagrader.Grade;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Grade
public class StackTest {
    @Grade
    @Test
    public void testSimpleStack() {

        Stack<Integer> [] stacks = new Stack[2];
        stacks[0] = new ArrayStack<>();
        stacks[1] = new LinkedStack<>();
        for (Stack<Integer> stack : stacks) {
            stack.push(1);
            assertEquals(1, stack.peek());
            stack.push(2);
            stack.push(3);
            assertFalse(stack.empty());
            assertEquals(3, (int) stack.pop());
            assertEquals(2, (int) stack.pop());
            assertEquals(1, (int) stack.pop());
            assertTrue(stack.empty());

        }
    }

    public void test_stack(Stack<Integer> stack){
        assertEquals(true, stack.empty());

        int i;
        for(i = 0; i < 10; i++){
            stack.push(i * i);
            assertEquals(false, stack.empty());
            assertEquals(i * i, stack.peek());
        }

        Integer value;
        for(i = 9; i >= 0; i--) {
            value = stack.pop();
            assertEquals(i == 0, stack.empty());
            assertEquals(i * i, value);

            if(i > 0){
                assertEquals((i - 1) * (i - 1), stack.peek());
            }
        }

        assertThrows(EmptyStackException.class, () -> {
            stack.peek();
        });

        assertThrows(EmptyStackException.class, () -> {
            stack.pop();
        });

        return;
    }

    @Order(0)
    @Test
    public void test_linked_stack(){
        test_stack(new LinkedStack<Integer>());
        return;
    }

    @Order(0)
    @Test
    public void test_array_stack(){
        test_stack(new ArrayStack<Integer>());
        return;
    }
}

