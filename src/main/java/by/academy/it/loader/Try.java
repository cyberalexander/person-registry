package by.academy.it.loader;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created : 22/08/2021 09:50
 * Project : hibernate-crm
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
public class Try<V> {

    private final V success;
    private final Throwable failure;

    public Try(V success, Throwable failure) {
        this.success = success;
        this.failure = failure;
    }

    public boolean isSuccess() {
        return success != null;
    }

    public V get() {
        return success;
    }

    public boolean isFailure() {
        return failure != null;
    }

    public Throwable failureReason() {
        return failure;
    }

    public static <V> Try<V> success(V value) {
        return new Try<>(value, null);
    }

    public static <V> Try<V> failure(Throwable failure) {
        return new Try<>(null, failure);
    }

    @Override
    public String toString() {
        if (isSuccess()) {
            return "Try{success=" + success + '}';
        } else {
            return "Try{failure=" + failure + '}';
        }
    }

    public static <V> Try<V> of(Callable<V> action) {
        try {
            return success(action.call());
        } catch (Throwable e) {
            return failure(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <B> Try<B> map(Function<V, B> mappingFunction) {
        if (isSuccess()) {
            try {
                return success(mappingFunction.apply(get()));
            } catch (Throwable e) {
                return failure(e);
            }
        } else {
            return (Try<B>) this;
        }
    }

    @SuppressWarnings({"unchecked", "PMD.ReturnFromFinallyBlock"})
    public <B> Try<B> map(Function<V, B> mappingFunction, Consumer<V> finalizer) {
        if (isSuccess()) {
            try {
                return success(mappingFunction.apply(get()));
            } catch (Throwable e1) {
                return failure(e1);
            } finally {
                try {
                    finalizer.accept(get());
                } catch (Throwable e2) {
                    return failure(e2);
                }
            }
        } else {
            return (Try<B>) this;
        }
    }
}
