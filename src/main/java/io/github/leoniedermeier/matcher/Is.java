package io.github.leoniedermeier.matcher;

@FunctionalInterface
public interface Is<T, R> {

    Matcher<T> is(Matcher<? super R> matcher);
}
