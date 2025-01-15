package fundamentals;

import fundamentals.Stack;

import java.util.EmptyStackException;

/**
 * Implement the Stack interface above using a simple linked list.
 * You should have at least one constructor withtout argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
public class LinkedStack<E> implements Stack<E> {

    private Node<E> top;        // the node on the top of the stack
    private int size;        // size of the stack

    // helper linked list class
    private class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }

    @Override
    public boolean empty() {
        // TODO Implement empty method
        return this.top == null;
    }

    @Override
    public E peek() throws EmptyStackException {
        // TODO Implement peek method
        if (this.empty()) {
            throw new EmptyStackException();
        } else {
            return this.top.item;
        }
    }

    @Override
    public E pop() throws EmptyStackException {
        // TODO Implement pop method
        if (this.empty()) {
            throw new EmptyStackException();
        } else {
            Node<E> oldNode = this.top;
            this.top = oldNode.next;
            return oldNode.item;
        }
    }

    @Override
    public void push(E item) {
        // TODO Implement push method
        this.top = new Node<>(item, this.top);
    }
}