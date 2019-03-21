package io.github.leoniedermeier.matcher;

import io.github.leoniedermeier.matcher.imp.ExecutionContext;
import io.github.leoniedermeier.matcher.matchers.LogicalOperators;

public interface Matcher<T> {

    default Matcher<T> and(Matcher<? super T> matcher) {
        return LogicalOperators.and(this, matcher);
    }
    
    boolean matches(ExecutionContext executionContext, T actual);

    default Matcher<T> or(Matcher<? super T> matcher) {
        return LogicalOperators.or(this, matcher);
    }

}
