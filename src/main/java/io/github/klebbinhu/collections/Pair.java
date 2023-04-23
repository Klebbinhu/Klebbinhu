package io.github.klebbinhu.collections;

public class Pair<K,V>{
    private final K key;
    private V value;

    public Pair(K arg1, V arg2){
        this.key = arg1;
        this.value = arg2;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Pair(" +
                 key +
                "," + value +
                ')';
    }
}
