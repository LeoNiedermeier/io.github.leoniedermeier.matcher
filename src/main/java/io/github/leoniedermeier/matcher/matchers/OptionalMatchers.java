package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;
import java.util.Optional;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;
import io.github.leoniedermeier.matcher.imp.MismatchMessage;

public class OptionalMatchers {

    public static class OptionalIsPresentWithValue<T> extends AbstractTerminalMatcher<Optional<T>> {

        public OptionalIsPresentWithValue() {
            super("Optional is present");
        }

        public Matcher<Optional<T>> withValue(Matcher<? super T> matcher) {
            Objects.requireNonNull(matcher, "OptionalIsPresentWithValue.withValue - matcher is <null>");
            return Is.createFrom(this, Optional::get, "with value").is(matcher);
        }

        @Override
        // Optional it the thing to test
        @SuppressWarnings("squid:S3553")
        protected void doesMatch(ExecutionContext executionContext, @NonNull Optional<T> actual) {
            if (!actual.isPresent()) {
                executionContext.mismatch("Optional is not present");
            }
        }
    }

    public static <T> Matcher<Optional<T>> isEmpty() {
        return TerminalMatcherBuilder.<Optional<T>>withExpectation("Optional is empty", null)
                .withPredicate(a -> !a.isPresent())
                .andMismatch(a -> new MismatchMessage("Optional is not empty", a.get(), null));
    }

    public static <T> OptionalIsPresentWithValue<T> isPresent() {
        return new OptionalIsPresentWithValue<>();
    }

    private OptionalMatchers() {
        throw new AssertionError("No OptionalMatchers instances for you!");
    }
}
