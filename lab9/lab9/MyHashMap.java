package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import static java.lang.Math.abs;

/**
 * Created by Alfred on 23/12/16.
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private int REFACTOR = 5;
    private HashArray hashArray;
    private double loadFactor = 3;
    private int size;
    private int buckets;

    public MyHashMap() {
        hashArray = new HashArray(100);
        size = 0;
    }
    public MyHashMap(int initialSize) {
        hashArray =  new HashArray(initialSize);
        size = 0;

    }
    public MyHashMap(int initialSize, double loadFactor) {
        hashArray = new HashArray(initialSize);
        this.loadFactor = loadFactor;
        size = 0;

    }

    public void resize() {
        MyHashMap newMyHashMap = new MyHashMap(size * REFACTOR, loadFactor);
        for (int i = 0; i < buckets; i++) {
            LinkedList currentList = hashArray.getFromIndex(i);
            if (currentList != null) {
                for (int a = 0; a < currentList.size(); a++) {
                    newMyHashMap.hashArray.insert(currentList.get(a).hashCode(), (KeyValuePair) currentList.get(a));
                }
            }

        }
        this.hashArray = newMyHashMap.hashArray;
        this.buckets = newMyHashMap.buckets;
    }


    private class HashArray {
        private LinkedList<KeyValuePair>[] array;

        public HashArray(int intialSize) {
            array = new LinkedList[intialSize];
            buckets = intialSize;
        }

        public int convertHash(int hashCode) {
            if (hashCode >= 0) {
                return hashCode % buckets;
            } else {
                if (hashCode == buckets) {
                    return 0;
                }
                return buckets - (abs(hashCode) % buckets) - 1;
            }
        }
        
        public LinkedList<KeyValuePair> get(int hashCode) {
            return array[convertHash(hashCode)];
        }

        public LinkedList<KeyValuePair> getFromIndex(int index) {
            return array[index];
        }

        public void insert(int hashCode, KeyValuePair newPair) {
            if (array[convertHash(hashCode)] == null) {
                array[convertHash(hashCode)] = new LinkedList();
            }
            array[convertHash(hashCode)].add(newPair);
        }

        public void insertAtIndex(int index, LinkedList list) {
            array[index] = list;
        }


    }

    private class KeyValuePair {
        private K key;
        private V value;

        public KeyValuePair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey(){
            return key;
        }

        public V getValue(){
            return value;
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        hashArray = new HashArray(100);
        size = 0;

    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key){
        if (hashArray.get(key.hashCode()) != null) {
            for (KeyValuePair k : hashArray.get(key.hashCode())) {
                if (k.key.equals(key)) {
                    return k.value;
                }
            }
        }
        return null;



    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;

    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        KeyValuePair newPair = new KeyValuePair(key, value);
        if (containsKey(key)) {
            for (KeyValuePair kv : hashArray.get(key.hashCode())) {
                if (kv.key.equals(key)) kv.value = value;
            }
        } else {
            LinkedList currentList = hashArray.get(key.hashCode());
            hashArray.insert(newPair.hashCode(), newPair);
            size += 1;
        }

        if ((double) size/buckets > loadFactor) {
            resize();
        }

    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<K>();
        for (int i = 0; i < buckets; i++) {
            LinkedList currentList = hashArray.getFromIndex(i);
            if (currentList != null) {
                for (int a = 0; a < currentList.size(); a++) {
                    KeyValuePair thisKey = (KeyValuePair) currentList.get(a);
                    keySet.add(thisKey.getKey());
                }
            }

        }
        return keySet;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();

    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
