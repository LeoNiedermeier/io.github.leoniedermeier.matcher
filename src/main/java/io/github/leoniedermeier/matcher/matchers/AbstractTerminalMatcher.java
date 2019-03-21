package io.github.leoniedermeier.matcher.matchers;

import io.github.leoniedermeier.matcher.imp.BaseMatcher;
import io.github.leoniedermeier.matcher.imp.Message;
import io.github.leoniedermeier.matcher.imp.ExpectedMessage;

public abstract class AbstractTerminalMatcher<T> extends BaseMatcher<T> {

    private final ExpectedMessage expectation;

    protected AbstractTerminalMatcher(ExpectedMessage expectation) {
        this.expectation = expectation;
    }
    
    protected AbstractTerminalMatcher(String text ) {
        super();
        this.expectation = text != null ? new ExpectedMessage(text, null) : null;
    }

    protected AbstractTerminalMatcher(String text, Object expected) {
        super();
        this.expectation = text != null ? new ExpectedMessage(text, expected) : null;
    }

    @Override
    protected Message getExpectation() {
        return this.expectation;
    }
}
