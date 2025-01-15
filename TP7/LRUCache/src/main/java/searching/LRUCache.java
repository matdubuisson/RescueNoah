package searching;

import java.security.Key;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * We are interested in the implementation of an LRU cache,
 * i.e. a (hash)-map of limited capacity where the addition of
 * a new entry might induce the suppression of the Least Recently Used (LRU)
 * entry if the maximum capacity is exceeded.
 *
 * Your LRU cache implements the same two methods as a MAP :
 * - put(key, elem) inserts the given element in the cache,
 *      this element becomes the most recently used element
 *      and if needed (the cache is full and the key not yet present),
 *      the least recently used element is removed.
 * - get(key) returns the entry with the given key from the cache,
 *      this element becomes the most recently used element
 *
 * These methods need to be implemented with an expected time complexity of O(1).
 * You are free to choose the type of data structure that you consider
 * to best support this cache. You can also use data-structures from Java.
 *
 * Hint for your implementation:
 *       Use a doubly linked list to store the elements from the least
 *       recently used (head) to the most recently used (tail).
 *       If needed the element to suppress is the head of the list.
 *
 *       Use java.util.HashMap with the <key,node> where node is a reference to the node
 *       in the doubly linked list.
 *
 *       Of course, at every put/get the list will need to be updated so that
 *       the "accessed node" is placed at the end of the list.
 *
 *       Feel free to use existing java classes.
 *
 *  Example of usage of an LRU cache with capacity of 3:
 *  // step 0:
 *  put(A,7)  // map{(A,7)}  A is the LRU
 *  // step 1:
 *  put(B,10) // map{(A,7),(B,10)}  A is the LRU
 *  // step 2:
 *  put(C,5)  // map{(A,7),(B,10),(C,5)}  A is the LRU
 *  // step 3:
 *  put(D,8)  // map{(B,10),(C,5),(D,8)}  A is suppressed, B is the LRU
 *  // step 4:
 *  get(B)    // C is the LRU
 *  // step 5
 *  put(E,9)  // map{(B,10),(D,8),(E,9)} D is the LRU
 *  // step 6
 *  put(D,3)  // map{(B,10),(D,3),(E,9)} B is the LRU
 *  // step 7
 *  get(B)    // map{(B,10),(D,3),(E,9)} E is the LRU
 *  // step 8
 *  put(A,2)  // map{(B,10),(D,3),(A,2)} D is the LRU
 *
 *  Feel free to use existing java classes from Java
 */
public class LRUCache<K,V> {
    public class Node<K, V>{
        K key = null;
        V value = null;
        Node<K, V> left = null, right = null;

        public Node(K key, V value, Node<K, V> left, Node<K, V> right){
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public class DoubleLinkedList<K, V>{
        int size = 0;
        Node<K, V> first = null, last = null;

        public Node<K, V> addLast(K key, V value){
            Node<K, V> newNode = new Node<>(key, value, this.last, this.first);
            this.size++;

            if(this.first == null){
                this.first = this.last = newNode;
            }

            newNode.left = this.last;
            newNode.right = this.first;
            this.first.left = newNode;
            this.last.right = newNode;
            this.last = newNode;
            return newNode;
        }

        public Node<K, V> delFirst() {
            if(this.size == 0) return null;

            Node<K, V> oldNode = this.first;
            this.size--;

            if(oldNode == null) return null;
            else if(this.first == this.last) {
                this.first = this.last = null;
                return oldNode;
            } else {
                this.first = oldNode.right;
                this.first.left = this.last;
                this.last.right = this.first;
                return oldNode;
            }
        }

        public void delNode(Node<K, V> node){
            this.size--;

            if (node == this.first) this.first = node.right;
            if (node == this.last) this.last = node.left;

            node.left.right = node.right;
            node.right.left = node.left;
        }
    }

    private int capacity;
    K LRU = null;
    private DoubleLinkedList<K, V> list = new DoubleLinkedList<>();
    private HashMap<K, Node<K, V>> map = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public void show(){
        System.out.println(this.list.size);
        System.out.print("{");
        Node<K, V> node = this.list.first;
        for(int i = 0; node != this.list.last; i++){
            System.out.print("[" + node.key + "]");
            node = node.right;
        }
        System.out.print("[" + node.key + "]");
        System.out.println("}");
    }

    public V get(K key) {
        if(!this.map.containsKey(key)) return null;
        else {
            Node<K, V> node = this.map.get(key);
            this.list.delNode(node);
            this.map.put(key, this.list.addLast(key, node.value));
            return node.value;
        }
    }

    public void put(K key, V value) {
        if (this.list.size == this.capacity) {
            if(!this.map.containsKey(key)){
                this.map.remove(this.list.delFirst().key);
            } else {
                this.list.delNode(this.map.get(key));
            }
        }

        this.map.put(key, this.list.addLast(key, value));
    }

    public static void main(String[] args) {
        LRUCache<String, Integer> cache = new LRUCache<>(3);

        cache.put("A", 0);
        cache.put("B", 1);
        cache.put("C", 2);
        cache.put("D", 3);
        cache.put("E", 4);
        cache.put("E", 5);
        cache.put("E", 6);
        cache.put("C", 5);
        cache.put("E", 5);
        System.out.println("========");
        cache.put("A", 5);
        System.out.println(cache.list.last.key);
        System.out.println(cache.list.first.key);

    }
}