package io.github.leoniedermeier.matcher;

public interface NullSafeMatcher<T> extends Matcher<T> {

    @Override
    default boolean matches(T actual, ExecutionContext context) {
        ExecutionContext downstream = new ExecutionContext();
        context.addChild(downstream);
        downstream.setActual(actual);
        if (actual == null) {
            downstream.setMismatch("value is <null>");
            downstream.setMatched(false);
            return false;
        }
        boolean matched = doesMatch(actual, downstream);
        downstream.setMatched(matched);
        return matched;
    }
}
