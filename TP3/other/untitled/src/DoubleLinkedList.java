import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class DoubleLinkedList<E>{
    public class Node<E>{
        Node<E> prv, nxt;
        E item;

        public Node(E item, Node<E> prv, Node<E> nxt){
            this.item = item;
            this.prv = prv;
            this.nxt = nxt;
        }
    }

    int size = 0;
    Node<E> last = null;

    public void add(E item){
        Node<E> newNode = new Node<E>(item, null, null);

        if(this.last == null){
            newNode.prv = newNode.nxt = newNode;
        } else {
            this.last.nxt.prv = newNode;
            newNode.prv = this.last;
            newNode.nxt = this.last.nxt;
            this.last.nxt = newNode;
        }

        this.last = newNode;
        this.size++;
    }

    public void print(){
        if(this.last == null){
            System.out.println("{}");
        } else {
            Node<E> curNode = this.last.nxt;
            System.out.print("size:" + this.size + "{");

            do{
                System.out.print("[" + curNode.item + "]");
                curNode = curNode.nxt;
            } while (curNode != this.last);

            System.out.print("[" + this.last.item + "]");

            System.out.println("}");
        }
    }

    public void sort(){ // Ex 2.1.5
        // Convert to array, sort it and insert sorted array in linked list
        if(this.last == null) return;

        E[] array = (E[]) new Object[this.size];
        int i = 0;
        Node<E> curNode = this.last.nxt;

        for(i = 0; i < this.size; i++){
            array[i] = curNode.item;
            curNode = curNode.nxt;
        }

        Arrays.sort(array);
        curNode = this.last.nxt;

        for(i = 0; i < this.size; i++){
            curNode.item = array[i];
            curNode = curNode.nxt;
        }
    }

    public static void main(String[] args) {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
        Random random = new Random();

        for(int i = 0; i < 10; i++){
            list.add(random.nextInt() % 10);
        }

        list.print();
        list.sort();
        list.print();
    }
}
