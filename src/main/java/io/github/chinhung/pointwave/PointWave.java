package io.github.chinhung.pointwave;

public interface PointWave {

    static <T> Decoratee<T> decoratee(T t) {
        return new Decoratee<>(t);
    }
}
