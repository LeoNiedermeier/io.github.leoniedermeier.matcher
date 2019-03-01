package io.github.leoniedermeier.matcher.matchers;

import java.util.Collection;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public final class IterableMatchers {

    // anyMatch, allMatch, noneMatch
    public static <T> Matcher<Iterable<T>> allMatch(final Matcher<? super T> matcher) {
        return (Iterable<T> actual, ExecutionContext context) -> {
            context.setExpectation("every item");
            context.setMismatch("one item");
            for (T t : actual) {
                if (!matcher.matches(t, context)) {
                    return false;
                }
            }
            return true;
        };
    }

    // java.util.stream.Stream.anyMatch(Predicate<? super T>)
    public static <T> Matcher<Iterable<T>> anyMatch(final Matcher<? super T> matcher) {
        return (Iterable<T> actual, ExecutionContext context) -> {
            context.setExpectation("at least one item");
            context.setMismatch("no item");
            for (T t : actual) {
                if (matcher.matches(t, context)) {
                    return true;
                }
            }
            return false;
        };
    }

    // java.util.stream.Stream.noneMatch(Predicate<? super T>)
    public static <T> Matcher<Iterable<T>> noneMatch(final Matcher<? super T> matcher) {
        return (Iterable<T> actual, ExecutionContext context) -> {
            context.setExpectation("no item");
            context.setMismatch("one item");
            for (T t : actual) {
                if (matcher.matches(t, context)) {
                    return false;
                }
            }
            return true;
        };
    }

    public static <T> Matcher<Iterable<T>> size(int expectation) {
        return (actual, description) -> size(ObjectMatchers.equalTo(expectation), actual, description);
    }

    public static <T> Matcher<Iterable<T>> size(Matcher<? super Integer> matcher) {
        return (actual, description) -> size(matcher, actual, description);
    }

    private static int calculateSize(Iterable<?> actual) {
        if (actual instanceof Collection<?>) {
            return ((Collection<?>) actual).size();
        } else {
            long size = 0;
            for (@SuppressWarnings("unused")
            Object object : actual) {
                size++;
            }
            // TODO: check for max size / max int
            return (int) size;
        }
    }

    private static <T> boolean size(Matcher<? super Integer> matcher, Iterable<T> actual,
            ExecutionContext description) {
        description.setExpectation("a iterable with size");
        description.setMismatch("a iterable with size");
        return matcher.matches(calculateSize(actual), description);
    }

    private IterableMatchers() {
        throw new AssertionError("No IterableMatchers instances for you!");
    }
}
