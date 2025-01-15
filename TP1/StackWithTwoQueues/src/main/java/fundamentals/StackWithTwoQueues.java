package fundamentals;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.Queue;
import java.util.Stack;

/**
 * Author: Pierre Schaus and Auguste Burlats
 * Implement the abstract data type stack using two queues
 * You are not allowed to modify or add the instance variables,
 * only the body of the methods
 */
public class StackWithTwoQueues<E> {

    Queue<E> queue1;
    Queue<E> queue2;

    public StackWithTwoQueues() {
        queue1 = new ArrayDeque();
        queue2 = new ArrayDeque();
    }

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty() {
         return this.queue1.isEmpty();
    }

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException {
        if(this.queue1.isEmpty()) throw new EmptyStackException();

        E elem = null;

        while(!this.queue1.isEmpty()){
            elem = this.queue1.remove();

            this.queue2.add(elem);
        }

        Queue<E> tmp = this.queue1;
        this.queue1 = this.queue2;
        this.queue2 = tmp;

        return elem;
    }

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException {
        if(this.queue1.isEmpty()) throw new EmptyStackException();

        E elem = null;

        while(true){
            elem = this.queue1.remove();

            if(this.queue1.isEmpty()){
                break;
            } else {
                this.queue2.add(elem);
            }
        }

        Queue<E> tmp = this.queue1;
        this.queue1 = this.queue2;
        this.queue2 = tmp;

        return elem;
    }

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item) {
        this.queue1.add(item);
    }

    public static void main(String[] args) {
        StackWithTwoQueues<Integer> stack = new StackWithTwoQueues();
        int i, j = 5;

        for(i = 0; i < j; i++){
            stack.push(i);
        }

        System.out.println(stack.peek());

        for(i = 0; i < j; i++){
            System.out.println(stack.pop());
        }
    }

}
