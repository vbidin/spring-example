package eu.bidin.persistence;

import java.lang.Math;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a hash table, a collection made of key-value pairs, that enables O(1) retrieval of collection elements.
 *
 * @param <K> key type
 * @param <V> value type
 * @author Vedran Biđin
 * @version 1.0
 */
public class MyHashtable<K, V> implements Iterable<MyHashtable.TableEntry<K, V>> {

    /**
     * Represents one key-value pair of a hash table.
     *
     * @param <K> key type
     * @param <V> value type
     * @author Vedran Biđin
     * @version 1.0
     */
    public static class TableEntry<K, V> {

        /**
         * Key of the key-value pair.
         */
        private K key;

        /**
         * Value of the key-value pair.
         */
        private V value;

        /**
         * A reference to the next key-value pair.
         * {@code null} if this pair is the last element of a hash table slot.
         */
        private TableEntry<K, V> next;

        /**
         * Creates a table entry using specified {@code key}, {@code value}, and {@code entry}.
         *
         * @param key   {@link #key}
         * @param value {@link #value}
         * @param entry {@link #next}
         * @throws IllegalArgumentException if key is {@code null}
         */
        public TableEntry(K key, V value, TableEntry<K, V> entry) throws IllegalArgumentException {
            if (key == null)
                throw new IllegalArgumentException("Key can not be null.");

            this.key = key;
            this.value = value;
            this.next = entry;
        }

        /**
         * Returns key.
         *
         * @return {@link #key}
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns value.
         *
         * @return {@link #value}
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets value.
         *
         * @param value {@link #value}
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * Returns a {@link String} representation of this key-value pair.
         *
         * @return {@link String} representation of this key-value pair
         */
        public String toString() {
            return key.toString() + "=" + value == null ? "null" : value.toString();
        }
    }

    /**
     * Number of elements (key-value pairs) currently stored in this collection.
     */
    private int size;

    /**
     * Number of hash table slots.
     */
    private int capacity;

    /**
     * Number of times this collection was modified.
     * Used to prevent changing of the collection during its iteration.
     */
    private int modificationCount = 0;

    /**
     * Array that represents all the slots of the hash table.
     */
    private TableEntry<K, V> table[];

    /**
     * Creates a hash table with specified number of slots.
     *
     * @param capacity number of slots
     * @throws IllegalArgumentException if {@code capacity} < 1
     */
    @SuppressWarnings("unchecked")
    public MyHashtable(int capacity) throws IllegalArgumentException {
        if (capacity < 1)
            throw new IllegalArgumentException("Capacity must be higher than 0");

        int sum = 1;
        while (true) {
            if (sum >= capacity) {
                this.capacity = sum;
                break;
            }
            sum *= 2;
        }

        this.size = 0;
        this.table = (TableEntry<K, V>[]) new TableEntry[this.capacity];
    }

    /**
     * Default constructor, creates a hash table with capacity {@code 16}.
     */
    public MyHashtable() {
        this(16);
    }

    /**
     * Returns current number of elements in the collection.
     *
     * @return number of elements in the collection
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@link #capacity}
     *
     * @return {@link #capacity}
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Checks if collection is empty.
     *
     * @return true: if {@link #size} == 0,
     * <br>    false: otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns index (slot) of the collection for specified key.
     *
     * @param key key value
     * @return index of specified key
     */
    private int index(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * Returns the value of a pair with the specified key.
     *
     * @param key key value
     * @return value belonging to specified key
     * <br>    if no matching key is found, returns null
     */
    public V get(K key) {
        if (key == null)
            return null;

        int index = index(key);
        TableEntry<K, V> entry = table[index];

        while (entry != null)
            if (entry.key.equals(key))
                return entry.value;
            else
                entry = entry.next;

        return null;
    }

    /**
     * Adds a new element with specified key and value to the collection.
     *
     * @param key   key of the key-value pair
     * @param value value of the key-value pair
     * @throws IllegalArgumentException if key is {@code null}
     */
    public void put(K key, V value) throws IllegalArgumentException {
        if (key == null)
            throw new IllegalArgumentException("Key can not be null.");

        modificationCount += 1;
        int index = index(key);
        TableEntry<K, V> entry = table[index];

        // if slot is empty
        if (entry == null) {
            table[index] = new TableEntry<>(key, value, null);
            size += 1;
            optimize();
            return;
        }

        while (true) {
            // if slot already contains key, overwrite previous value
            if (entry.key.equals(key)) {
                entry.value = value;
                modificationCount -= 1;
                return;
            }
            // otherwise, create a new entry
            if (entry.next == null) {
                entry.next = new TableEntry<K, V>(key, value, null);
                size += 1;
                return;
            }
            entry = entry.next;
        }
    }

    /**
     * Removes element with matching key from the collection (if it exists).
     *
     * @param key key of key-value pair to remove
     */
    public void remove(K key) {
        if (key == null) {
            return;
        }
        int index = index(key);
        TableEntry<K, V> entry = table[index];

        // if list is empty
        if (entry == null) {
            return;
        }
        // if entry is first in list
        if (entry.key.equals(key)) {
            table[index] = entry.next;
            size -= 1;
            modificationCount += 1;
            return;
        }
        // general case
        TableEntry<K, V> previous = entry;
        while (!entry.key.equals(key)) {
            previous = entry;
            entry = entry.next;
            if (entry == null) {
                return;
            }
        }
        previous.next = entry.next;
        size -= 1;
        modificationCount += 1;
    }

    /**
     * Checks if collection contains a pair with specified key.
     *
     * @param key key of the key-value pair
     * @return true: if key was found in the collection
     * <br>    false: otherwise
     */
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        int index = index(key);
        TableEntry<K, V> entry = table[index];

        while (entry != null) {
            if (entry.key.equals(key)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    /**
     * Checks if collection contains a pair with specified value.
     *
     * @param value value of the key-value pair
     * @return true: if value was found in the collection
     * <br>    false: otherwise
     */
    public boolean containsValue(V value) {
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                if (entry.value == null || value == null) {
                    if (entry.value == null && value == null) {
                        return true;
                    }
                } else {
                    if (entry.value.equals(value)) {
                        return true;
                    }
                }
                entry = entry.next;
            }
        }
        return false;
    }

    /**
     * Returns a {@link String} representation of this hash table.
     *
     * @return {@link String} representation of this hash table
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        String prefix = "";

        output.append("[");
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                output.append(prefix);
                prefix = ", ";
                output.append(entry.toString());
                entry = entry.next;
            }
        }
        output.append("]");
        return output.toString();
    }

    /**
     * Checks if more than 75% slots of the collection are filled, and calls {@link #resize} if true.
     */
    private void optimize() {
        int n = 0;
        for (TableEntry<K, V> entry : table) {
            if (entry != null) {
                n += 1;
            }
        }
        if (1. * n / capacity >= 0.75) {
            resize();
        }
    }

    /**
     * Doubles the capacity of the hash table.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        TableEntry<K, V> oldTable[] = this.table;

        this.size = 0;
        this.capacity *= 2;
        this.table = (TableEntry<K, V>[]) new TableEntry[this.capacity];

        for (TableEntry<K, V> entry : oldTable) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    /**
     * Removes all key-value pairs from the collection.
     */
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    /**
     * Returns a new iterator of this collection.
     */
    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    /**
     * An iterator that can iterate through a {@link MyHashtable} collection.
     *
     * @author Vedran Biđin
     * @version 1.0
     */
    private class IteratorImpl implements Iterator<TableEntry<K, V>> {

        /**
         * Current slot of the iterator.
         */
        private int index = -1;
        /**
         * Amount of elements remaining to be iterated.
         */
        private int remaining = size;
        /**
         * Number of times the collection was changed from its creation.
         * Used to prevent the collection being changed during its iteration.
         */
        private int modificationCount = MyHashtable.this.modificationCount;
        /**
         * Flag that signals if {@link #remove} can be called.
         */
        private boolean isRemovable = false;
        /**
         * Current entry of the iteration.
         */
        private TableEntry<K, V> entry = null;

        /**
         * Checks if iterator has any elements left to iterate through.
         *
         * @throws ConcurrentModificationException if collection has changed during iteration
         */
        @Override
        public boolean hasNext() {
            if (modificationCount != MyHashtable.this.modificationCount) {
                throw new ConcurrentModificationException();
            }
            return remaining != 0;
        }

        /**
         * Gets the next element of the iteration.
         *
         * @throws ConcurrentModificationException if collection has changed during iteration
         * @throws NoSuchElementException          if no more elements remain to be iterated through
         */
        @Override
        public TableEntry<K, V> next() {
            if (modificationCount != MyHashtable.this.modificationCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            remaining -= 1;
            if (entry == null) {
                while (entry == null) {
                    index += 1;
                    entry = table[index];
                }
            } else {
                entry = entry.next;
                if (entry == null) {
                    while (entry == null) {
                        index += 1;
                        entry = table[index];
                    }
                }
            }
            isRemovable = true;
            return entry;
        }

        /**
         * Removes the current element from the collection.
         * This is the only way to safely change the collection during an iteration. Cannot be called twice on the same element.
         *
         * @throws ConcurrentModificationException if collection has changed during iteration
         * @throws IllegalStateException           if method was called twice on the same element
         */
        @Override
        public void remove() {
            if (modificationCount != MyHashtable.this.modificationCount) {
                throw new ConcurrentModificationException();
            }
            if (isRemovable == false) {
                throw new IllegalStateException();
            }

            MyHashtable.this.remove(entry.key);
            modificationCount += 1;
            isRemovable = false;
        }
    }
}

