package io.github.leoniedermeier.matcher.matchers;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;

public final class PropertyAccess {

    public static <T, R> Is<T, R> property(Function<T, R> transformer, String description) {
        return Is.<T, R>createFrom(transformer, description);
    }

    public static <T, R> Matcher<T> property(Function<T, R> transformer, String description,
            Matcher<? super R> matcher) {
        return new AbstractIntermediateMatcher<T>(description, matcher) {

            @Override
            protected void doesMatch(ExecutionContext executionContext, @NonNull T actual) {
                R transformed = transformer.apply(actual);
                if (!matcher.matches(executionContext, transformed)) {
                    executionContext.mismatch(description);
                }
            }
        };
    }

    private PropertyAccess() {
        throw new AssertionError("No PropertyAccess instances for you!");
    }
}
