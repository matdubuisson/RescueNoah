package strings;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implement the class WordCounter that counts the number of occurrences
 * of each word in a given piece of text.
 * Feel free to use existing java classes.
 */
public class WordCounter implements Iterable<String> {
    Map<String, Integer> map = new TreeMap();

    public WordCounter() {

    }

    /**
     * Add the word so that the counter of the word is increased by 1
     */
    public void addWord(String word) {
        if (!this.map.containsKey(word)) {
            this.map.put(word, 1);
        } else {
            this.map.put(word, this.map.get(word) + 1);
        }
    }

    /**
     * Return the number of times the word has been added so far
     */
    public int getCount(String word) {
        if (!this.map.containsKey(word)) {
            return 0;
        } else {
            return this.map.get(word);
        }
    }

    // iterate over the words in ascending lexicographical order
    @Override
    public Iterator<String> iterator() {
         return this.map.keySet().iterator();
    }
}
