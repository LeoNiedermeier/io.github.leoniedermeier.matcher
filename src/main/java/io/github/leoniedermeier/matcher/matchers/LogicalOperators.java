package io.github.leoniedermeier.matcher.matchers;

import java.util.Objects;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public final class LogicalOperators {

    public static <T> Matcher<T> and(Matcher<? super T> matcher1, Matcher<? super T> matcher2) {
        Objects.requireNonNull(matcher1, "LogicalOperators.and - matcher1 is <null>");
        Objects.requireNonNull(matcher2, "LogicalOperators.and - matcher2 is <null>");
        return new AbstractIntermediateMatcher<T>("and", matcher1, matcher2) {

            @Override
            public boolean doesMatch(T actual, ExecutionContext context) {
                if (!matcher1.matches(actual, context) || !matcher2.matches(actual, context)) {
                    context.setMismatch("and");
                    return false;
                }
                return true;
            }

        };

    }

    public static <T> Matcher<T> not(Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "LogicalOperators.not - matcher is <null>");
        return new AbstractIntermediateMatcher<T>("not", matcher) {

            @Override
            public boolean doesMatch(T actual, ExecutionContext context) {
                context.switchInvers();
                try {

                    if (matcher.matches(actual, context)) {
                        context.setMismatch("not");
                        return false;
                    }
                    return true;
                } finally {
                    context.switchInvers();
                }
            }
        };
    }

    private LogicalOperators() {
        throw new AssertionError("No LogicalOperators instances for you!");
    }

}
