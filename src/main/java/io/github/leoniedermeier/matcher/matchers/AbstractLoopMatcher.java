package io.github.leoniedermeier.matcher.matchers;

import io.github.leoniedermeier.matcher.Matcher;

public abstract class AbstractLoopMatcher<T> extends AbstractIntermediateMatcher<T> {

    public AbstractLoopMatcher(String text, Matcher<?>... childs) {
        super(text, childs);
    }
}
