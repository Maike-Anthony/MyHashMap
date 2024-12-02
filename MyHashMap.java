import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * The MyHashMap class implements the Map ADT using a hashtable.
 * 
 * @author Maike Anthony dos Santos Silva
 */
public class MyHashMap<K, V> {
    /**An array of linked lists storing values of type SimpleEntry<K, V>.*/
    private LinkedList<SimpleEntry<K, V>>[] hashtable;

    /**The number of key-value pairs in the hashtable.*/
    private int size;

    /** The initial length to use for the hashtable. */
    private static final int INITIAL_LENGTH = 11;

    /** The load factor to use for deciding when to resize our hashtable. */
    private static final float LOAD_FACTOR = 0.75f;

    /** An array of possible table sizes. */
    private static final int TABLE_SIZE[] = { 5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437,
            102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977, 26339969, 52679969, 105359939,
            210719881, 421439783, 842879579, 1685759167 };

    /**
     * Creates a new MyHashMap.
     */
    public MyHashMap() {
        this.size = 0;
        hashtable = this.createTable(INITIAL_LENGTH);
    }

    /**
     * Create a new array of {@link LinkedList}s with a prime length.
     * 
     * @param length The minimum length of the array
     * 
     * @return The new array with empty {@link LinkedList}s
     */
    @SuppressWarnings("unchecked")
    private LinkedList<SimpleEntry<K, V>>[] createTable(int length) {
        // get the closest prime length
        for (int primeCapacity : TABLE_SIZE) {
            if (primeCapacity >= length) {
                length = primeCapacity;
                break;
            }
        }

        // create the array of empty lists
        LinkedList<SimpleEntry<K, V>>[] list = (LinkedList<SimpleEntry<K, V>>[]) new LinkedList[length];
        for (int i = 0; i < list.length; i++) {
            list[i] = new LinkedList<SimpleEntry<K, V>>();
        }

        return list;
    }

    /**
     * Method to return the size instance variable.
     * @return the size instance variable.
     */
    public int size() {
        return this.size;
    }

    /**
     * Method to inform if the hashtable is empty or not.
     * @return true if hashtable is empty and false otherwise.
     */
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**Method to remove all key/value pairs from the hashtable and set size to zero. */
    public void clear() {
        for (LinkedList<SimpleEntry<K, V>> list : this.hashtable) {
            list.clear();
        }
        this.size = 0;
    }

    /**
     * Method to get a SimpleEntry<K, V> given the key.
     * @param key the key being looked for.
     * @return the SimpleEntry that contains the key.
     */
    private SimpleEntry<K, V> find(K key) {
        int hash = key.hashCode() & Integer.MAX_VALUE;
        int index = hash % this.hashtable.length;
        LinkedList<SimpleEntry<K, V>> list = this.hashtable[index];
        Iterator<SimpleEntry<K, V>> iter = list.iterator();
        while (iter.hasNext()) {
            SimpleEntry<K, V> entry = iter.next();
            K curKey = entry.getKey();
            if (curKey.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Method to get the value associated with a given key.
     * @param key the key whose value we are looking for.
     * @return the value of the key inserted as a parameter.
     */
    public V get(K key) {
        SimpleEntry<K, V> entry = this.find(key);
        if (entry == null) {
            return null;
        } else {
            return entry.getValue();
        }
    }

    /**
     * Method to insert new entries into the hashtable
     * @param entry is the new key/value pair inserted into the hashtable.
     */
    private void insert(SimpleEntry<K, V> entry) {
        K key = entry.getKey();
        int hash = key.hashCode() & Integer.MAX_VALUE;
        int index = hash % this.hashtable.length;
        LinkedList<SimpleEntry<K, V>> list = this.hashtable[index];
        list.add(entry);
    }

    /**Method to resize the hashtable.*/
    private void resize() {
        LinkedList<SimpleEntry<K, V>>[] old = hashtable;
        hashtable = createTable(2*old.length);
        for (LinkedList<SimpleEntry<K, V>> list : old) {
            for (SimpleEntry<K, V> entry : list) {
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Method to add new items to the hashtable.
     * @param key new key to be added.
     * @param value new value associated to the new key.
     * @return the value previously associated with that key if it was already in the hashtable.
     */
    public V put(K key, V value) {
        SimpleEntry<K, V> found = this.find(key);
        if (found != null) {
            V oldValue = found.getValue();
            found.setValue(value);
            return oldValue;
        } else {
            SimpleEntry<K, V> newEntry = new SimpleEntry<>(key, value);
            insert(newEntry);
            this.size += 1;
            if (this.size > LOAD_FACTOR * this.hashtable.length) {
                this.resize();
            }
            return null;
        }
    }

    /**
     * Helper method to remove a given entry from the linked list inside the hashmap.
     * @param entry entry to be removed.
     */
    private void removeEntry(SimpleEntry<K, V> entry) {
        K key = entry.getKey();
        int hash = key.hashCode() & Integer.MAX_VALUE;
        int index = hash % this.hashtable.length;
        LinkedList<SimpleEntry<K, V>> list = this.hashtable[index];
        list.remove(entry);
    }

    /**
     * Method to remove key/value pairs from the hashmap.
     * @param key the key of the key/value pair to be removed.
     * @return the value of the key/value pair removed.
     */
    public V remove(K key) {
        SimpleEntry<K, V> entry = this.find(key);
        if (entry != null) {
            V value = entry.getValue();
            this.removeEntry(entry);
            this.size -= 1;
            return value;
        } else {
            return null;
        }
    }

    /**
     * Method to return a set with all the elements of the hashmap in a set.
     * @return set containing all the elements of the hashmap.
     */
    public Set<SimpleEntry<K, V>> entrySet() {
        HashSet<SimpleEntry<K, V>> hashset = new HashSet<>();
        for (LinkedList<SimpleEntry<K, V>> list : this.hashtable) {
            hashset.addAll(list);
        }
        return hashset;
    }

    /**
     * Method to see if a certain key is present in the hashtable.
     * @param key the key being looked for.
     * @return true if the key is already present and false otherwise.
     */
    public boolean containsKey(K key) {
        SimpleEntry<K, V> entry = this.find(key);
        if (entry == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to see if a certain value is present in the hashmap.
     * @param value the value being looked for.
     * @return true if the value is contained in the hashmap and false otherwise.
     */
    public boolean containsValue(V value) {
        for (LinkedList<SimpleEntry<K, V>> list : this.hashtable) {
            for (SimpleEntry<K, V> entry : list) {
                if (entry.getValue() == value) {
                    return true;
                }
            }
        }
        return false;
    }

}
