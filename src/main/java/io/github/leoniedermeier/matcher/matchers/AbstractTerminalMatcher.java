package io.github.leoniedermeier.matcher.matchers;

import io.github.leoniedermeier.matcher.BaseMatcher;
import io.github.leoniedermeier.matcher.Entry;

public abstract class AbstractTerminalMatcher<T> implements BaseMatcher<T> {

    private final Entry expectation;

    public AbstractTerminalMatcher(String text, Object... arguments) {
        super();
        this.expectation = text != null ? new Entry(text, arguments) : null;
    }

    @Override
    public Entry getExpectation() {
        return this.expectation;
    }
}
