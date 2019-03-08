package io.github.leoniedermeier.matcher.matchers;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public final class IterableMatchers {

    private static class StreamMatcherToIterableMatcher<T> extends AbstractIntermediateMatcher<Iterable<T>> {

        private final Matcher<Stream<T>> streamMatcher;

        public StreamMatcherToIterableMatcher(Matcher<Stream<T>> streamMatcher) {
            super("iterable", streamMatcher);
            this.streamMatcher = streamMatcher;
        }

        @Override
        public boolean doesMatch(@NonNull Iterable<T> actual, ExecutionContext context) {
            Stream<T> stream = StreamSupport.stream(actual.spliterator(), false);
            if (!this.streamMatcher.matches(stream, context)) {
                context.setMismatch("iterable");
                return false;
            }
            return true;
        }
    }

    public static <T> Matcher<Iterable<T>> allMatch(final Matcher<? super T> matcher) {
        return new StreamMatcherToIterableMatcher<>(StreamMatchers.allMatch(matcher));
    }

    public static <T> Matcher<Iterable<T>> anyMatch(final Matcher<? super T> matcher) {
        return new StreamMatcherToIterableMatcher<>(StreamMatchers.anyMatch(matcher));

    }

    public static <T> Matcher<Iterable<T>> containsAll(final Matcher<? super T>... matchers) {
        return new StreamMatcherToIterableMatcher<>(StreamMatchers.containsAll(matchers));
    }

    public static <T> Matcher<Iterable<T>> noneMatch(final Matcher<? super T> matcher) {
        return new StreamMatcherToIterableMatcher<>(StreamMatchers.noneMatch(matcher));
    }

    public static <T> Matcher<Iterable<T>> size(int expectation) {
        return size(ObjectMatchers.equalTo(expectation));
    }

    public static <T> Matcher<Iterable<T>> size(Matcher<? super Integer> matcher) {
        Objects.requireNonNull(matcher, "IterableMatchers.size - matcher is <null>");
        return new AbstractIntermediateMatcher<Iterable<T>>("a iterable with size", matcher) {

            @Override
            public boolean doesMatch(@NonNull Iterable<T> actual, ExecutionContext context) {
                if (!matcher.matches(calculateSize(actual), context)) {
                    context.setMismatch("a iterable with size");
                    return false;
                }
                return true;
            }

        };
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

    private IterableMatchers() {
        throw new AssertionError("No IterableMatchers instances for you!");
    }
}
