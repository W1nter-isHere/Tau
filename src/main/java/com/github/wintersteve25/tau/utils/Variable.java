package com.github.wintersteve25.tau.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Variable<T> {
    
    private T value;
    private boolean changedSince;
    private final List<Consumer<T>> listeners;
    
    public Variable(T initial) {
        this.value = initial;
        listeners = new ArrayList<>();
        changedSince = false;
    }

    public T getValue() {
        changedSince = false;
        return value;
    }

    public void setValue(T value) {
        if (this.value == null && value == null) {
            return;
        }
        
        if (this.value != null) {
            if (this.value == value) return;
            if (this.value.equals(value)) return;
        }

        this.value = value;
        changedSince = true;

        for (Consumer<T> listener : listeners) {
            listener.accept(value);
        }
    }
    
    public void addListener(Consumer<T> listener) {
        listeners.add(listener);
    }
    
    public void removeListener(Consumer<T> listener) {
        listeners.remove(listener);
    }
    
    public boolean hasChangedSinceLastGet() {
        return changedSince;
    }
}
