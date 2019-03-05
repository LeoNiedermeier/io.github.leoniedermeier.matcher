package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public final class LogicMatchers {

    public static <T> Matcher<T> and(Matcher<T> matcher1, Matcher<T> matcher2) {
        return (T actual, ExecutionContext context) -> and(matcher1, matcher2, actual, context);
    }

    public static <T> Matcher<T> not(Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "LogicMatchers.not - matcher is <null>");
        return (T actual, ExecutionContext context) -> not(matcher, actual, context);
    }

    private static <T> boolean and(Matcher<T> matcher1, Matcher<T> matcher2, T actual, ExecutionContext description) {
        Objects.requireNonNull(matcher1, "LogicMatchers.and - matcher1 is <null>");
        Objects.requireNonNull(matcher2, "LogicMatchers.and - matcher2 is <null>");
        description.setExpectation("and");
        return matcher1.matches(actual, description) && matcher2.matches(actual, description);
    }

    private static <T> boolean not(Matcher<T> matcher, T actual, ExecutionContext description) {
        description.setExpectation("not");
        return !matcher.matches(actual, description);
    }

    private LogicMatchers() {
        throw new AssertionError("No LogicMatchers instances for you!");
    }

}
