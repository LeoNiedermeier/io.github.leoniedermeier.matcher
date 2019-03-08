package io.github.leoniedermeier.matcher.matchers;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public class BaseMatcherAdapter<T> extends AbstractTerminalMatcher<T> {

    private final Matcher<T> matcher;

    public BaseMatcherAdapter(Matcher<T> matcher) {
        super("Adapt: " + matcher.getClass());
        this.matcher = matcher;
    }

    @Override
    public boolean doesMatch(T actual, ExecutionContext context) {
        return this.matcher.matches(actual, context);
    }

}
