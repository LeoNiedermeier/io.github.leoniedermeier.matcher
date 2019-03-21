package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.MismatchMessage;

public final class StringMatchers {

    public static Matcher<String> contains(String expected) {
        Objects.requireNonNull(expected, "StringMatchers.startsWith - expected is <null>");
        return TerminalMatcherBuilder.<String>withExpectation("a string contains <%s>", expected)
                .withPredicate(a -> a.contains(expected))
                .andMismatch(a -> new MismatchMessage("<%s> does not contain <%s>", a, expected));
    }

    public static Matcher<String> endsWith(String expected) {
        Objects.requireNonNull(expected, "StringMatchers.endsWith - expected is <null>");

        return TerminalMatcherBuilder.<String>withExpectation("a string ends with <%s>", expected)
                .withPredicate(a -> a.endsWith(expected))
                .andMismatch(a -> new MismatchMessage("<%s> does not end with <%s>", a, expected));
    }

    public static Matcher<String> length(int expectation) {
        return length(ObjectMatchers.equalTo(expectation));
    }

    public static Matcher<String> length(Matcher<? super Integer> matcher) {
        Objects.requireNonNull(matcher, "StringMatchers.length - matcher is <null>");

        return PropertyAccess.<String, Integer>property(String::length, "a string with length").is(matcher);
    }

    public static Matcher<String> startsWith(String expected) {
        Objects.requireNonNull(expected, "StringMatchers.startsWith - expected is <null>");
        return TerminalMatcherBuilder.<String>withExpectation("a string starts with <%s>", expected)
                .withPredicate(a -> a.startsWith(expected))
                .andMismatch(a -> new MismatchMessage("<%s> does not start with <%s>", a, expected));
    }

    private StringMatchers() {
        throw new AssertionError("No StringMatchers instances for you!");
    }
}
