package com.harbourspace.lesson09;

public class GiftBox<T> {
    private T thing;

    public void put(T thing) {
        this.thing = thing;
    }

    public T get() {
        return thing;
    }

}
