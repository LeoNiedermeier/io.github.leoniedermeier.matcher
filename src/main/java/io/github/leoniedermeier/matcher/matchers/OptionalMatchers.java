package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;
import java.util.Optional;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public class OptionalMatchers {

    public abstract static class AbstractIsPresentWithValue<T> extends AbstractTerminalMatcher<Optional<T>> {

        public AbstractIsPresentWithValue() {
            super("Optional is present");
        }

        public Matcher<Optional<T>> withValue(Matcher<? super T> matcher) {
            Objects.requireNonNull(matcher, "AbstractIsPresentWithValue.withValue - matcher is <null>");
            return Is.createFrom(this, Optional::get, "with value").is(matcher);
        }
    }

    public static <T> Matcher<Optional<T>> isEmpty() {
        return new AbstractTerminalMatcher<Optional<T>>("Optional is empty") {

            @Override
            @SuppressWarnings("squid:S3553")
            // Optional it the thing 
            public boolean doesMatch(@NonNull Optional<T> actual, ExecutionContext context) {
                if (actual.isPresent()) {
                    context.setMismatch("Optional is not empty");
                    return false;
                }
                return true;
            }

        };
    }

    public static <T> AbstractIsPresentWithValue<T> isPresent() {

        return new AbstractIsPresentWithValue<T>() {

            @Override
            @SuppressWarnings("squid:S3553")
            // Optional it the thing 
            public boolean doesMatch(@NonNull Optional<T> actual, ExecutionContext context) {
                if (!actual.isPresent()) {
                    context.setMismatch("Optional is not present");
                    return false;
                }
                return true;
            }
        };
    }

    private OptionalMatchers() {
        throw new AssertionError("No OptionalMatchers instances for you!");
    }
}
