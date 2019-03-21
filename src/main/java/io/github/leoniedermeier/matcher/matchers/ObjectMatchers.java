package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;

public final class ObjectMatchers {

    public static <T> Matcher<T> equalTo(T expected) {
        return new AbstractTerminalMatcher<T>("is equal to <%s>", expected) {

            @Override
            protected void doesMatch(ExecutionContext executionContext, T actual) {
                if (!Objects.equals(expected, actual)) {
                    executionContext.mismatch("<%s> is not equal to <%s>", actual, expected);
                }
            }
        };
    }

    public static <T> Matcher<T> isInstanceOf(Class<?> expected) {
        Objects.requireNonNull(expected, "ObjectMatchers.isInstanceOf - expected is <null>");
        return new AbstractTerminalMatcher<T>("is instance of <%s>", expected) {

            @Override
            protected void doesMatch(ExecutionContext executionContext, @NonNull T actual) {
                if (!expected.isInstance(actual)) {
                    executionContext.mismatch("<%s> is not instance of <%s>", actual, expected);
                }
            }
        };
    }

    public static <T> Matcher<T> isNotNull() {
        return new AbstractTerminalMatcher<T>("is not <null>") {

            @Override
            protected void doesMatch(ExecutionContext executionContext, T actual) {
                if (Objects.isNull(actual)) {
                    executionContext.mismatch("is <null>");
                }
            }
        };
    }

    public static <T> Matcher<T> isNull() {
        return new AbstractTerminalMatcher<T>("is <null>") {

            @Override
            protected void doesMatch(ExecutionContext executionContext, T actual) {
                if (!Objects.isNull(actual)) {
                    executionContext.mismatch("<%s> is not <null>", actual, null);
                }
            }
        };
    }

    public static <T> Matcher<T> isSameInstance(T expected) {
        return new AbstractTerminalMatcher<T>("is same instance as <%s>", expected) {

            @Override
            protected void doesMatch(ExecutionContext executionContext, T actual) {
                if (expected != actual) {
                    executionContext.mismatch("<%s> is not same instance as <%s>", actual, expected);
                }
            }
        };
    }

    private ObjectMatchers() {
        throw new AssertionError("No ObjectMatchers instances for you!");
    }
}
