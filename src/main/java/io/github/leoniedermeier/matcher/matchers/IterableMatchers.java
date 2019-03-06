package io.github.leoniedermeier.matcher.matchers;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.NullSafeMatcher;

public final class IterableMatchers {

    private static class StreamMatcherToIterableMatcher<T> implements Matcher<Iterable<T>> {

        private final Matcher<Stream<T>> downsteam;

        public StreamMatcherToIterableMatcher(Matcher<Stream<T>> downsteam) {
            super();
            this.downsteam = downsteam;
        }

        @Override
        public boolean doesMatch( Iterable<T> actual, ExecutionContext context) {
            throw new IllegalStateException("doesMatch called");
        }

        @Override
        public boolean matches(Iterable<T> actual, ExecutionContext context) {
            if(actual == null) {
                context.setMismatch("value is <null>");
                context.setMatched(false);
                return false;
            }
            Stream<T> stream = StreamSupport.stream(actual.spliterator(), false);
            return downsteam.matches(stream, context);
        }

    }

    public static <T> Matcher<Iterable<T>> allMatch(final Matcher<? super T> matcher) {
        return new StreamMatcherToIterableMatcher<>(StreamMatchers.allMatch(matcher));
    }

    public static <T> Matcher<Iterable<T>> anyMatch(final Matcher<? super T> matcher) {
        return new StreamMatcherToIterableMatcher<>(StreamMatchers.anyMatch(matcher));

    }

    public static <T> Matcher<Iterable<T>> noneMatch(final Matcher<? super T> matcher) {
        return new StreamMatcherToIterableMatcher<>(StreamMatchers.noneMatch(matcher));
    }

    public static <T> NullSafeMatcher<Iterable<T>> size(int expectation) {
        return (Iterable<T> actual, ExecutionContext context) -> size(ObjectMatchers.equalTo(expectation), actual, context);
    }

    public static <T> Matcher<Iterable<T>> size(Matcher<? super Integer> matcher) {
        Objects.requireNonNull(matcher, "IterableMatchers.size - matcher is <null>");
        return (Iterable<T> actual, ExecutionContext context) -> size(matcher, actual, context);
    }

    private static int calculateSize(Iterable<?> actual) {
        if (actual instanceof Collection<?>) {
            return ((Collection<?>) actual).size();
        } else {
            long size = 0;
            for (@SuppressWarnings("squid:S1481")
            Object object : actual) {
                size++;
            }
            if (size > Integer.MAX_VALUE) {
                throw new AssertionError("Size of collection greater than " + Integer.MAX_VALUE);
            }
            return (int) size;
        }
    }

    private static <T> boolean size(Matcher<? super Integer> matcher, Iterable<T> actual,
            ExecutionContext context) {
        context.setExpectation("a iterable with size");
        context.setMismatch("a iterable with size");
        return matcher.matches(calculateSize(actual), context);
    }

    private IterableMatchers() {
        throw new AssertionError("No IterableMatchers instances for you!");
    }
}
