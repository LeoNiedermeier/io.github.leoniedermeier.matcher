package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;

public final class LogicalOperators {

    public static <T> Matcher<T> and(Matcher<? super T> matcher1, Matcher<? super T> matcher2) {
        Objects.requireNonNull(matcher1, "LogicalOperators.and - matcher1 is <null>");
        Objects.requireNonNull(matcher2, "LogicalOperators.and - matcher2 is <null>");
        return new AbstractIntermediateMatcher<T>("and", matcher1, matcher2) {

            @Override
            protected void doesMatch(ExecutionContext executionContext, T actual) {
                if (!(matcher1.matches(executionContext, actual) && matcher2.matches(executionContext, actual))) {
                    executionContext.mismatch("and");
                }
            }

        };

    }

    public static <T> Matcher<T> not(Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "LogicalOperators.not - matcher is <null>");
        return new AbstractIntermediateMatcher<T>("not", matcher) {

            @Override
            protected void doesMatch(ExecutionContext executionContext, T actual) {
                if (matcher.matches(executionContext, actual)) {
                    executionContext.mismatch("not");
                }
            }
        };
    }

    public static <T> Matcher<T> or(Matcher<? super T> matcher1, Matcher<? super T> matcher2) {
        Objects.requireNonNull(matcher1, "LogicalOperators.and - matcher1 is <null>");
        Objects.requireNonNull(matcher2, "LogicalOperators.and - matcher2 is <null>");
        return new AbstractIntermediateMatcher<T>("or", matcher1, matcher2) {

            @Override
            protected void doesMatch(ExecutionContext executionContext, T actual) {
                if (!(matcher1.matches(executionContext, actual) || matcher2.matches(executionContext, actual))) {
                    executionContext.mismatch("or");
                }
            }

        };

    }

    private LogicalOperators() {
        throw new AssertionError("No LogicalOperators instances for you!");
    }

}
