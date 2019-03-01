package io.github.leoniedermeier.matcher.util;

import io.github.leoniedermeier.matcher.Matcher;

public interface ActionMatcher<T, X> extends Matcher<T> {
    ActionMatcher<T, X> is(Matcher<? super X> downstream);
}
