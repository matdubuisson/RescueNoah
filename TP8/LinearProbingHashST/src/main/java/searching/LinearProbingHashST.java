package searching;

/**
 * With the given partial implementation of LinearProbingHashST, we ask you to
 * implement the resize, get and put methods
 * You are not allowed to use already existing classes and methods from Java
 */
public class LinearProbingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;
    
    // Please do not add/remove variables/modify their visibility.
    protected int n;           // number of key-value pairs in the symbol table
    protected int m;           // size of linear probing table
    protected Key[] keys;      // the keys
    protected Value[] vals;    // the values


    /**
     * Initializes an empty symbol table.
     */
    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     *
     * @param capacity the initial capacity
     */
    public LinearProbingHashST(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[])   new Object[m];
        vals = (Value[]) new Object[m];
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Returns the current capacity of the table
     *
     * @return the current capacity of the table
     */
    public int capacity() {
        return m;
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key};
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    protected int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    /**
     * TODO
     * Resizes the hash table to the given capacity by re-hashing all of the keys
     *
     * @param capacity the capacity
     */
    protected void resize(int capacity){
        Key[] oldKeys = this.keys;
        this.keys = (Key[]) new Object[capacity];
        Value[] oldValues = this.vals;
        this.vals = (Value[]) new Object[capacity];
        this.m = capacity;
        int oldN = this.n;
        this.n = 0;

        for(int i = 0; i < oldKeys.length; i++){
            if(oldKeys[i] != null) this.put(oldKeys[i], oldValues[i]);
        }
    }

    /**
     * TODO
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * The load factor should never exceed 50% so make sure to resize correctly
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if(key == null) throw new IllegalArgumentException();

        if(this.n >= this.keys.length / 2) this.resize(this.keys.length * 2);

        int hashKey = this.hash(key);
        int i;

        for(i = hashKey; i < this.m; i++){
            if(this.keys[i] == null) {
                this.n++;
                break;
            }  else if(this.keys[i].equals(key)) break;
        }

        this.keys[i] = key;
        this.vals[i] = val;
    }

    /**
     * TODO
     * Returns the value associated with the specified key.
     * @param key the key
     * @return the value associated with {@code key};
     *         {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if(key == null) throw new IllegalArgumentException();

        int hashKey = this.hash(key);

        for(int i = hashKey; i < this.m; i++){
            if(this.keys[i] == null) return null;
            else if(this.keys[i].equals(key)) return this.vals[i];
        }

        return null;
    }

    /**
     * Returns all keys in this symbol table
     */
    public Object[] keys() {
        Object[] exportKeys = new Object[n];
        int j = 0;
        for (int i = 0; i < m; i++)
            if (keys[i] != null) exportKeys[j++] = keys[i];
        return exportKeys;
    }

    public static void main(String[] args) {
        LinearProbingHashST<String, Integer> hashMap = new LinearProbingHashST<>();

        System.out.println(hashMap.get("test"));
        hashMap.put("test", 14);
        System.out.println(hashMap.get("test"));
        hashMap.put("retest", 16);
        System.out.println(hashMap.get("test"));
        System.out.println(hashMap.size());
        System.out.println("===============");
        hashMap.put("test", 17);
        System.out.println(hashMap.get("retest"));
        System.out.println(hashMap.size());
    }
}