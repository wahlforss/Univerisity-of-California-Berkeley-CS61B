package lab8;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Alfred on 20/12/16.
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;

    private class Node {
        private K key;
        private V value;
        private int size;
        private Node left, right;
        public Node(K KV, V val, int size) {
            key = KV;
            value = val;
            this.size = size;
        }
    }



    //Creates an empty BSTmap.
    public BSTMap() {

    }


    @Override
    public void clear() {
        root = null;

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
    public V get(K key) {
        return get(root, key);

    }

    private V get(Node x, K key) {
        if (x == null) return null;
        if (x.key.equals(key)) {
            return x.value;
        }

        int cmp = key.compareTo(x.key);

        if (cmp > 0) {
            return get(x.right, key);
        } else {
            return get(x.left, key);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) return new Node(key, value, 1);
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else {
            x.value = value;
        }

        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        V val = get(key);
        root = remove(root, key);
        return val;
    }

    private void append(Node x, Node append) {
        if (x == null) {
            x = append;
        } else if (x.left == null) {
            x.left = append;
        } else {
            append(x.left, append);
        }
    }

    private Node remove(Node x, K key) {
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
                x.right = remove(x.right, key);
        } else if (cmp < 0) {
                x.left = remove(x.left, key);
        } else {
            Node oldLeft = x.left;
            x = x.right;
            append(x, oldLeft);
        }
        return x;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }
}
