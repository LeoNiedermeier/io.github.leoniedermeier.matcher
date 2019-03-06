package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;
import java.util.stream.Stream;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.NullSafeMatcher;

public final class StreamMatchers {

    public static <T> NullSafeMatcher<Stream<T>> allMatch(Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "StreamMatchers.allMatch - matcher is <null>");
        return (Stream<T> actual, ExecutionContext context) -> {
            context.setExpectation("every item");
            context.setMismatch("one item");
            return actual.allMatch(t -> callDownstreamMatcher(t,matcher, context));
        };
    }

    public static <T> NullSafeMatcher<Stream<T>> anyMatch(Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "StreamMatchers.anyMatch - matcher is <null>");
        return (Stream<T> actual, ExecutionContext context) -> {
            context.setExpectation("at least one item");
            context.setMismatch("no item matches:");
            return actual.anyMatch(t -> matcher.matches(t, context));
        };
    }

    public static <T> NullSafeMatcher<Stream<T>> noneMatch(Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "StreamMatchers.noneMatch - matcher is <null>");
        return (Stream<T> actual, ExecutionContext context) -> {
            context.setExpectation("no item");
            context.setMismatch("one item");
            context.setInvers(true);
            return actual.noneMatch(t -> callDownstreamMatcher(t,matcher, context));
        };
    }

    
    private static<T> boolean callDownstreamMatcher(T actual, Matcher<? super T> matcher, ExecutionContext context) {
        context.clearChildren();
        return matcher.matches(actual, context);
    }
    public static <T> NullSafeMatcher<Stream<T>> size(long expectation) {
        return (actual, context) -> size(ObjectMatchers.equalTo(expectation), actual, context);
    }

    public static <T> NullSafeMatcher<Stream<T>> size(Matcher<? super Long> matcher) {
        Objects.requireNonNull(matcher, "StreamMatchers.size - matcher is <null>");
        return (actual, context) -> size(matcher, actual, context);
    }

    private static <T> boolean size(Matcher<? super Long> matcher, Stream<T> actual, ExecutionContext description) {
        description.setExpectation("a stream with size");
        description.setMismatch("a stream with size");
        return matcher.matches(actual.count(), description);
    }

    private StreamMatchers() {
        throw new AssertionError("No StreamMatchers instances for you!");
    }
}
