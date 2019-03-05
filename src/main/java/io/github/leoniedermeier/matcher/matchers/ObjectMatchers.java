package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public final class ObjectMatchers {

    public static <T> Matcher<T> equalTo(T expected) {
        return (T actual, ExecutionContext context) -> equalTo(expected, actual, context);
    }

    public static <T> Matcher<T> isInstanceOf(Class<?> expected) {
        Objects.requireNonNull(expected, "ObjectMatchers.isInstanceOf - expected is <null>");
        return (T actual, ExecutionContext context) -> {
            context.setExpectation("is instance of <%s>", expected);
            if (actual == null) {
                context.setMismatch("is null");
            }
            context.setMismatch("<%s> is not instance of <%s>", actual, expected);
            return expected.isInstance(actual);
        };
    }

    public static <T> Matcher<T> isNotNull() {
        return (T actual, ExecutionContext context) -> {
            context.setExpectation("is not <null>");
            context.setMismatch("is <null>");
            return Objects.nonNull(actual);
        };
    }

    public static <T> Matcher<T> isNull() {
        return (T actual, ExecutionContext context) -> {
            context.setExpectation("is <null>");
            context.setMismatch("<%s> is not <null>", actual);
            return Objects.isNull(actual);
        };
    }

    public static <T> Matcher<T> isSameInstance(T expected) {
        return (T actual, ExecutionContext context) -> {
            context.setExpectation("is same instance as <%s>", expected);
            context.setMismatch("<%s> is not same instance as <%s>", actual, expected);
            return expected == actual;
        };
    }

    static <T> boolean equalTo(T expected, T actual, ExecutionContext context) {
        context.setExpectation("is equal to <%s>", expected);
        context.setMismatch("<%s> is not equal to <%s>", actual, expected);
        return Objects.equals(expected, actual);
    }

    private ObjectMatchers() {
        throw new AssertionError("No ObjectMatchers instances for you!");
    }
}
