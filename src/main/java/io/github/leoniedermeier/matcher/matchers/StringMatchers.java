package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public final class StringMatchers {

    public static Matcher<String> contains(String expectation) {
        Objects.requireNonNull(expectation, "StringMatchers.startsWith - expectation is <null>");
        return new AbstractTerminalMatcher<String>("a string contains <%s>", expectation) {

            @Override
            public boolean doesMatch(String actual, ExecutionContext context) {
                if (!actual.contains(expectation)) {
                    context.setMismatch("<%s> does not contain <%s>", actual, expectation);
                    return false;
                }
                return true;
            }

        };
    }

    public static Matcher<String> endsWith(String expectation) {
        Objects.requireNonNull(expectation, "StringMatchers.endsWith - expectation is <null>");
        return new AbstractTerminalMatcher<String>("a string ends with <%s>", expectation) {

            @Override
            public boolean doesMatch(@NonNull String actual, ExecutionContext context) {
                if (!actual.endsWith(expectation)) {
                    context.setMismatch("<%s> which not ends with <%s>", actual, expectation);
                    return false;
                }
                return true;
            }
        };
    }

    public static Matcher<String> length(int expectation) {
        return length(ObjectMatchers.equalTo(expectation));
    }

    public static Matcher<String> length(Matcher<? super Integer> matcher) {
        Objects.requireNonNull(matcher, "StringMatchers.length - matcher is <null>");

        return PropertyAccess.<String, Integer>property(String::length, "a string with length")
                .is(matcher);
    }

    public static Matcher<String> startsWith(String expectation) {
        Objects.requireNonNull(expectation, "StringMatchers.startsWith - expectation is <null>");
        return new AbstractTerminalMatcher<String>("a string starts with <%s>", expectation) {

            @Override
            public boolean doesMatch(@NonNull String actual, ExecutionContext context) {
                if (!actual.startsWith(expectation)) {
                    context.setMismatch("<%s> does not start with <%s>", actual, expectation);
                    return false;
                }
                return true;
            }

        };
    }

    private StringMatchers() {
        throw new AssertionError("No StringMatchers instances for you!");
    }

}
