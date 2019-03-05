package io.github.leoniedermeier.matcher;

@FunctionalInterface
@SuppressWarnings("squid:S1711")
//We do not extend UnaryOperator because we do not want to UnaryOperator's default methods.
public interface Is<T, R> {

    Matcher<T> is(Matcher<? super R> matcher);
}
