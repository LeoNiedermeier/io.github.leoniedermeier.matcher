package io.github.leoniedermeier.matcher.matchers;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public final class PropertyAccess {

    public static <T, R> Is<T, R> property(Function<T, R> transformer, String description) {
        return Is.<T, R>createFrom(transformer, description);
    }

    public static <T, R> Matcher<T> property(Function<T, R> transformer, String description,
            Matcher<? super R> matcher) {
        return new AbstractIntermediateMatcher<T>(description, matcher) {

            @Override
            public boolean doesMatch(@NonNull T actual, ExecutionContext context) {
                if (!matcher.matches(transformer.apply(actual), context)) {
                    context.setMismatch(description);
                    return false;
                }
                return true;
            }
        };
    }

    private PropertyAccess() {
        throw new AssertionError("No PropertyAccess instances for you!");
    }
}
