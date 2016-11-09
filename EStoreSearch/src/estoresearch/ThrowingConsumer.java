/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estoresearch;

import java.util.function.Consumer;

/**
 * Functional interface used to be able to throw exceptions from a lambda
 * https://stackoverflow.com/questions/18198176/java-8-lambda-function-that-throws-exception
 *
 * @author courtney
 * @param <T> argument type for lambda
 */
@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {

    @Override
    default void accept(final T t) {
        try {
            acceptThrows(t);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param t input to operation
     * @throws java.lang.Exception checked
     */
    void acceptThrows(final T t) throws Exception;
}
