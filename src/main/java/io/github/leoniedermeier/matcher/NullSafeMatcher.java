package io.github.leoniedermeier.matcher;

import io.github.leoniedermeier.matcher.internal.SimpleExecutionContext;

public interface NullSafeMatcher<T> extends Matcher<T> {

    @Override
    default boolean matches(T actual, ExecutionContext context) {
        ExecutionContext downstream = new SimpleExecutionContext();
        context.addChild(downstream);
        downstream.setActual(actual);
        if(actual == null) {
            downstream.setMismatch("value is <null>");
            downstream.setMatched(false);
            return false;
        }
        boolean matched = doesMatch(actual, downstream);
        downstream.setMatched(matched);
        return matched;
    }
}
