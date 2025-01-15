package strings;

import java.util.*;

/**
 * Implement the class WordCounter that counts the number of occurrences
 * of each word in a given piece of text.
 * Feel free to use existing java classes.
 */
public class WordCounter implements Iterable<String> {
    private int size, nOp;
    private String[] words;
    private int[] counts;

    private class R implements Comparable<R>{
        public String s;

        public R(String s){
            this.s = s;
        }

        @Override
        public int compareTo(R other){
            return - other.s.compareTo(this.s);
        }
    }

    private PriorityQueue<R> PQ = new PriorityQueue<R>();

    public WordCounter(){
        this.size = this.nOp = 0;
        this.words = new String[100];
        this.counts = new int[this.words.length];
    }

    private void resize(int size){
        this.size = 0;
        String[] old = this.words;
        this.words = new String[size];
        int[] oldC = this.counts;
        this.counts = new int[size];

        int h;

        for(int i = 0; i < old.length; i++){
            if(old[i] == null) continue;

            h = this.hash(old[i]) % this.words.length;

            while(this.words[h] != null){
                h = (h + 1) % this.words.length;
            }

            this.words[h] = old[i];
            this.counts[h] = oldC[i];
        }
    }

    private int hash(String word){
        return word.hashCode();
    }

    /**
     * Add the word so that the counter of the word is increased by 1
     */
    public void addWord(String word){
        this.nOp++;

        if(this.size < 3 * this.words.length / 4) this.resize(this.words.length * 2);

        this.size++;

        int h = this.hash(word) % this.words.length;

        while(this.words[h] != null){
            if(this.words[h].compareTo(word) == 0){
                this.counts[h]++;
                return;
            }

            h = (h + 1) % this.words.length;
        }

        this.words[h] = word;
        this.counts[h] = 1;
        this.PQ.add(new R(word));
    }

    /**
     * Return the number of times the word has been added so far
     */
    public int getCount(String word) {
         int h = this.hash(word) % this.words.length;
         int niter = 0;

         while((this.words[h] == null || this.words[h].compareTo(word) != 0) && niter < this.words.length){
             h = (h + 1) % this.words.length;
             niter++;
         }

         if(niter == this.words.length) return 0;
         else return this.counts[h];
    }

    private class WordCounterIterator implements Iterator<String>{
        private int nOp = WordCounter.this.nOp;
        private int size = WordCounter.this.size;
        private int index = -1;

        private Iterator<R> iter;

        public WordCounterIterator(Iterator<R> i){
            this.iter = i;
        }

        @Override
        public boolean hasNext(){
            if(this.nOp != WordCounter.this.nOp) throw new ConcurrentModificationException();
            else return this.iter.hasNext();
        }

        @Override
        public String next(){
            if(this.nOp != WordCounter.this.nOp) throw new ConcurrentModificationException();
            else if(!this.hasNext()) throw new NoSuchElementException();
            else {
                this.size--;

                return this.iter.next().s;
            }
        }
    }

    // iterate over the words in ascending lexicographical order
    @Override
    public Iterator<String> iterator() {
         return new WordCounterIterator(this.PQ.iterator());
    }
}
