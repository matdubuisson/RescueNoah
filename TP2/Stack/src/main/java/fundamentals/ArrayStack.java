package fundamentals;

import fundamentals.Stack;

import java.util.EmptyStackException;

/**
 * Implement the Stack interface above using an array as internal representation
 * The capacity of the array should double when the number of elements exceed its length.
 * You should have at least one constructor withtout argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
public class ArrayStack<E> implements Stack<E> {

    private E[] array;        // array storing the elements on the stack
    private int size;        // size of the stack

    public ArrayStack() {
        array = (E[]) new Object[10];
    }

    @Override
    public boolean empty() {
        // TODO Implement empty method
        return this.size == 0;
    }

    @Override
    public E peek() throws EmptyStackException {
        // TODO Implement peek method
        if(this.empty()){
            throw new EmptyStackException();
        } else {
            return this.array[this.size - 1];
        }
    }

    @Override
    public E pop() throws EmptyStackException {
        // TODO Implement pop method
        if(this.empty()){
            throw new EmptyStackException();
        } else {
            E oldElement = this.array[--this.size];

            if(this.size < this.array.length / 4){
                E[] newArray = (E[]) new Object[this.array.length / 2];

                for(int i = 0; i < this.size; i++){
                    newArray[i] = this.array[i];
                }

                this.array = newArray;
            }

            return oldElement;
        }
    }

    @Override
    public void push(E item) {
        // TODO Implement push method
        if(this.size == this.array.length){
            E[] newArray = (E[]) new Object[this.array.length * 2];

            for(int i = 0; i < this.array.length; i++){
                newArray[i] = this.array[i];
            }

            this.array = newArray;
        }

        this.array[this.size++] = item;
        return;
    }
}