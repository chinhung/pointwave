package com.github.chinhung.pointwave;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Decoratee<T> {

    private final T decoratee;
    private final List<Function<T, T>> decorators;

    Decoratee(final T decoratee) {
        this.decoratee = decoratee;
        this.decorators = new ArrayList<>();
    }

    public Decoratee<T> decorated(final Function<T, T> decorator) {
        decorators.add(decorator);
        return this;
    }

    public T complete() {
        T decorated = decoratee;
        for (Function<T, T> decorator : decorators) {
            decorated = decorator.apply(decorated);
        }
        return decorated;
    }
}
