import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class MyHashMapTest {
    @Test
    void testClear() {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        hashmap.put("first", 1);
        hashmap.clear();
        assertTrue(hashmap.isEmpty());
    }

    @Test
    void testGet() {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        hashmap.put("first", 1);
        hashmap.put("second", 2);
        Integer ret1 = hashmap.get("first");
        Integer ret2 = hashmap.get("second");
        assertEquals(1, (int) ret1);
        assertEquals(2, (int) ret2);
        hashmap.put("second", 3);
        Integer ret3 = hashmap.get("second");
        assertEquals(3, (int) ret3);
        Integer ret4 = hashmap.get("third");
        assertNull(ret4);
    }

    @Test
    void testIsEmpty() {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        assertTrue(hashmap.isEmpty());
        hashmap.put("first", 1);
        assertFalse(hashmap.isEmpty());
        hashmap.clear();
        assertTrue(hashmap.isEmpty());
    }

    @Test
    void testPut() {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        Integer ret = hashmap.put("first", 1);
        assertNull(ret);
        Integer ret2 = hashmap.put("first", 2);
        assertEquals(1, (int) ret2);
    }

    @Test
    void testSize() {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        int size = hashmap.size();
        assertEquals(0, size);
        hashmap.put("first", 1);
        size = hashmap.size();
        assertEquals(1, size);
        hashmap.put("second", 2);
        size = hashmap.size();
        assertEquals(2, size);
        hashmap.clear();
        size = hashmap.size();
        assertEquals(0, size);
    }

    @Test
    void testRemove() {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        hashmap.put("first", 1);
        hashmap.put("second", 2);
        int size = hashmap.size();
        assertEquals(2, size);
        Integer value = hashmap.remove("second");
        size = hashmap.size();
        assertEquals(2, (int) value);
        assertEquals(1, size);
        value = hashmap.remove("second");
        assertNull(value);
    }

    @Test
    void testEntrySet() {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        hashmap.put("first", 1);
        hashmap.put("second", 2);
        Set<SimpleEntry<String, Integer>> set = hashmap.entrySet();
        Iterator<SimpleEntry<String, Integer>> iter = set.iterator();
        Integer first = iter.next().getValue();
        Integer second = iter.next().getValue();
        assertTrue((first == 1) || (second == 1));
        assertTrue((first == 2) || (second == 2));
    }

    @Test
    void testContainsKey() {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        hashmap.put("first", 1);
        hashmap.put("second", 2);
        boolean contains = hashmap.containsKey("first");
        boolean contains2 = hashmap.containsKey("third");
        assertTrue(contains);
        assertFalse(contains2);
    }

    @Test
    void containsValue() {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        hashmap.put("first", 1);
        hashmap.put("second", 2);
        boolean contains = hashmap.containsValue(1);
        boolean contains2 = hashmap.containsValue(3);
        assertTrue(contains);
        assertFalse(contains2);
    }
}
