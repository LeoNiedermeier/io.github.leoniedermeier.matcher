package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.NullSafeMatcher;

public final class StringMatchers {

    public static NullSafeMatcher<String> endsWith(String expectation) {
        Objects.requireNonNull(expectation, "StringMatchers.endsWith - expectation is <null>");
        return (String actual, ExecutionContext context) -> {
            context.setExpectation("a string ends with <%s>", expectation);
            context.setMismatch("<%s> which not ends not with <%s>", actual, expectation);
            return actual.endsWith(expectation);
        };
    }

    public static NullSafeMatcher<String> length(int expectation) {
        return (String actual, ExecutionContext context) -> length(ObjectMatchers.equalTo(expectation), actual,
                context);
    }

    public static NullSafeMatcher<String> length(Matcher<? super Integer> matcher) {
        Objects.requireNonNull(matcher, "StringMatchers.length - matcher is <null>");
        return (String actual, ExecutionContext context) -> length(matcher, actual, context);
    }

    public static NullSafeMatcher<String> startsWith(String expectation) {
        Objects.requireNonNull(expectation, "StringMatchers.startsWith - expectation is <null>");
        return (String actual, ExecutionContext context) -> {
            context.setExpectation("a string starts with <%s>", expectation);
            context.setMismatch("<%s> which not starts not with <%s>", actual, expectation);
            return actual.startsWith(expectation);
        };
    }

    private static boolean length(Matcher<? super Integer> matcher, String actual, ExecutionContext context) {
        context.setExpectation("a string with length");
        context.setMismatch("a string with length");
        return matcher.matches(Integer.valueOf(actual.length()), context);
    }

    private StringMatchers() {
        throw new AssertionError("No StringMatchers instances for you!");
    }

}
