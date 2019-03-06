package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public final class LogicalOperators {

    public static <T> Matcher<T> and(Matcher<T> matcher1, Matcher<T> matcher2) {
        return (T actual, ExecutionContext context) -> and(matcher1, matcher2, actual, context);
    }

    public static <T> Matcher<T> not(Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "LogicalOperators.not - matcher is <null>");
        return (T actual, ExecutionContext context) -> not(matcher, actual, context);
    }

    private static <T> boolean and(Matcher<T> matcher1, Matcher<T> matcher2, T actual, ExecutionContext context) {
        Objects.requireNonNull(matcher1, "LogicalOperators.and - matcher1 is <null>");
        Objects.requireNonNull(matcher2, "LogicalOperators.and - matcher2 is <null>");
        context.setExpectation("and");
        return matcher1.matches(actual, context) && matcher2.matches(actual, context);
    }

    private static <T> boolean not(Matcher<T> matcher, T actual, ExecutionContext context) {
        context.setExpectation("not");
        context.setInvers(true);
        return !matcher.matches(actual, context);
    }

    private LogicalOperators() {
        throw new AssertionError("No LogicalOperators instances for you!");
    }

}
