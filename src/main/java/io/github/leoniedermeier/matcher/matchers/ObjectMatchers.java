package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public final class ObjectMatchers {

    public static <T> Matcher<T> equalTo(T expected) {
        return new AbstractTerminalMatcher<T>("is equal to <%s>", expected) {

            @Override
            public boolean doesMatch(T actual, ExecutionContext context) {
                if (!Objects.equals(expected, actual)) {
                    context.setMismatch("<%s> is not equal to <%s>", actual, expected);
                    return false;
                }
                return true;
            }
        };
    }

    public static <T> Matcher<T> isInstanceOf(Class<?> expected) {
        Objects.requireNonNull(expected, "ObjectMatchers.isInstanceOf - expected is <null>");
        return new AbstractTerminalMatcher<T>("is instance of <%s>", expected) {

            @Override
            public boolean doesMatch(@NonNull T actual, ExecutionContext context) {
                if (!expected.isInstance(actual)) {
                    context.setMismatch("<%s> is not instance of <%s>", actual, expected);
                    return false;
                }
                return true;
            }

        };
    }

    public static <T> Matcher<T> isNotNull() {
        return new AbstractTerminalMatcher<T>("is not <null>") {

            @Override
            public boolean doesMatch(T actual, ExecutionContext context) {
                if (Objects.isNull(actual)) {
                    context.setMismatch("is <null>");
                    return false;
                }
                return true;
            }
        };
    }

    public static <T> Matcher<T> isNull() {
        return new AbstractTerminalMatcher<T>("is <null>") {

            @Override
            public boolean doesMatch(T actual, ExecutionContext context) {
                if (!Objects.isNull(actual)) {
                    context.setMismatch("<%s> is not <null>", actual);
                    return false;
                }
                return true;
            }

        };
    }

    public static <T> Matcher<T> isSameInstance(T expected) {
        return new AbstractTerminalMatcher<T>("is same instance as <%s>", expected) {

            @Override
            public boolean doesMatch(T actual, ExecutionContext context) {
                if (expected != actual) {
                    context.setMismatch("<%s> is not same instance as <%s>", actual, expected);
                    return false;
                }
                return true;
            }
        };
    }

    private ObjectMatchers() {
        throw new AssertionError("No ObjectMatchers instances for you!");
    }
}
