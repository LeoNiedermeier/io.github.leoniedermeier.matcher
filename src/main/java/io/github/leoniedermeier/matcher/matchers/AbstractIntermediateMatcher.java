package io.github.leoniedermeier.matcher.matchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.BaseMatcher;
import io.github.leoniedermeier.matcher.imp.Message;
import io.github.leoniedermeier.matcher.imp.ExpectedMessage;

public abstract class AbstractIntermediateMatcher<T> extends BaseMatcher<T> {

    private final List<BaseMatcher<?>> childs = new ArrayList<>();
    private final Message expectation;

    public AbstractIntermediateMatcher(String text, Matcher<?>... childs) {
        super();
        for (Matcher<?> matcher : childs) {
            if (matcher instanceof BaseMatcher<?>) {
                this.childs.add((BaseMatcher<?>) matcher);
            } else {
                this.childs.add(new BaseMatcherAdapter<>(matcher));
            }
        }
        this.expectation = text != null ? new ExpectedMessage(text, null) : null;
    }

    @Override
    protected Collection<BaseMatcher<?>> getChilds() {
        return this.childs;
    }

    @Override
    protected Message getExpectation() {
        return this.expectation;
    }
}
