package io.github.leoniedermeier.matcher;

import io.github.leoniedermeier.matcher.internal.SimpleExecutionContext;
import io.github.leoniedermeier.matcher.matchers.LogicMatchers;

@FunctionalInterface
public interface Matcher<T> {

    default boolean matches(T actual, ExecutionContext context) {
        ExecutionContext downstream = new SimpleExecutionContext();
        context.addChild(downstream);
        downstream.setActual(actual);
        boolean matched = doesMatch(actual, downstream);
        downstream.setMatched(matched);
        return matched;
    }

    boolean doesMatch(T actual, ExecutionContext context);

    default Matcher<T> and(Matcher<T> matcher) {
        return LogicMatchers.and(this, matcher);
    }
}