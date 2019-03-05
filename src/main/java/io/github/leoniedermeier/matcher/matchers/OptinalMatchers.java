package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;
import java.util.Optional;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.NullSafeMatcher;
import io.github.leoniedermeier.matcher.With;

public class OptinalMatchers {

    public interface WithValue<T> extends NullSafeMatcher<Optional<T>> {

        default Matcher<Optional<T>> withValue(Matcher<? super T> matcher) {
            Objects.requireNonNull(matcher, "WithValue.withValue - matcher is <null>");
            return With.with(this, Optional::get, "with value").is(matcher);
        }
    }

    public static <T> WithValue<T> isPresent() {
        return (Optional<T> actual, ExecutionContext context) -> {
            context.setExpectation("Optional is present");
            context.setMismatch("Optional is not present");
            return actual.isPresent();
        };
    }

    public static <T> NullSafeMatcher<Optional<T>> isEmpty() {
        return (Optional<T> actual, ExecutionContext context) -> {
            context.setExpectation("Optional is empty");
            context.setMismatch("Optional is not empty");
            return !actual.isPresent();
        };
    }
}
