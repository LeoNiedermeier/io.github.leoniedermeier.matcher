package io.github.leoniedermeier.matcher;

import io.github.leoniedermeier.matcher.matchers.LogicalOperators;

@FunctionalInterface
// We do not extend BiPredicate because we do not want to BiPredicate's default methods.
@SuppressWarnings("squid:S1711")
public interface Matcher<T> {

    default boolean matches(T actual, ExecutionContext context) {
        ExecutionContext downstream = new ExecutionContext();
        context.addChild(downstream);
        downstream.setActual(actual);
        boolean matched = doesMatch(actual, downstream);
        downstream.setMatched(matched);
        return matched;
    }

    boolean doesMatch(T actual, ExecutionContext context);

    default Matcher<T> and(Matcher<T> matcher) {
        return LogicalOperators.and(this, matcher);
    }
}
