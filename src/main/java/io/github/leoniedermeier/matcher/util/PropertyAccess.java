package io.github.leoniedermeier.matcher.util;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Is;
import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.With;

public final class PropertyAccess {

    public static <T, R> Matcher<T> property(Function<T, R> transformer, String description,
            Matcher<? super R> matcher) {
        return (T actual, ExecutionContext context) -> {
            context.setExpectation(description);
            context.setMismatch(description);
            return matcher.matches(transformer.apply(requireNonNull(actual, "Element to access is <null>")), context);
        };
    }

    
    interface OKMatcher<T,R> extends With<T,R> {
        @Override
        default boolean matches(T actual, ExecutionContext context) {
            return true;
        }
        
        default boolean doesMatch(T actual, ExecutionContext context) {
            return true;
        }
    }
    public static <T, R> Is<T, R> property(Function<T, R> transformer, String description) {
        return new OKMatcher<T,R>() {
        }.with(transformer, description);

    }
    
    public static <T, R> Is<T, R> propertyX(Function<T, R> transformer, String description) {
        return (Matcher<? super R> matcher) -> {
            return (T actual, ExecutionContext context) -> {
                context.setExpectation(description);
                context.setMismatch(description);
                return matcher.matches(transformer.apply(requireNonNull(actual, "Element to access is <null>")),
                        context);
            };
        };

    }
    

    private PropertyAccess() {
        throw new AssertionError("No PropertyAccess instances for you!");
    }
}
