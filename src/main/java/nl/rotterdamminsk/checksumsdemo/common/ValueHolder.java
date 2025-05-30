package nl.rotterdamminsk.checksumsdemo.common;

import java.util.Optional;
import java.util.function.Function;

public class ValueHolder<T> implements Functor<T, ValueHolder<?>> {
    private final T value;
    public ValueHolder(T value) { this.value = value; }
    public <R> ValueHolder<R> map(Function<T,R> f) {
        final R result = f.apply(value);
        return new ValueHolder<>(result);
    }
    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }
}