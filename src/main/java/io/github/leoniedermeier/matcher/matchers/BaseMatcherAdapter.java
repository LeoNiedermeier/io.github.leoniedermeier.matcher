package io.github.leoniedermeier.matcher.matchers;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;

public class BaseMatcherAdapter<T> extends AbstractTerminalMatcher<T> {

    private final Matcher<T> matcher;

    public BaseMatcherAdapter(Matcher<T> matcher) {
        super("Adapt: ", matcher.getClass());
        this.matcher = matcher;
    }

    @Override
    protected void doesMatch(ExecutionContext executionContext, T actual) {
        if( !this.matcher.matches(executionContext, actual)){
            executionContext.mismatch("");
        }
    }
}
