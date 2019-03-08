package io.github.leoniedermeier.matcher.matchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.leoniedermeier.matcher.BaseMatcher;
import io.github.leoniedermeier.matcher.Entry;
import io.github.leoniedermeier.matcher.Matcher;

public abstract class AbstractIntermediateMatcher<T> implements BaseMatcher<T> {

    private final List<BaseMatcher<?>> childs = new ArrayList<>();
    private final Entry expectation;

    public AbstractIntermediateMatcher(String text, Matcher<?>... childs) {
        super();
        for (Matcher<?> matcher : childs) {
            if (matcher instanceof BaseMatcher<?>) {
                this.childs.add((BaseMatcher<?>) matcher);
            } else {
                this.childs.add(new BaseMatcherAdapter<>(matcher));
            }
        }
        this.expectation = text != null ? new Entry(text) : null;
    }

    @Override
    public Collection<BaseMatcher<?>> getChilds() {
        return this.childs;
    }

    @Override
    public Entry getExpectation() {
        return this.expectation;
    }
}
