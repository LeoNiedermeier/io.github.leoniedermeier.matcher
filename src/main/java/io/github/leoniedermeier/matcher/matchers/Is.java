package io.github.leoniedermeier.matcher.matchers;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;

@FunctionalInterface
@SuppressWarnings("squid:S1711")
//We do not extend UnaryOperator because we do not want to UnaryOperator's default methods.
public interface Is<T, R> {

    static <T, R> Is<T, R> createFrom(Function<T, R> transformer, String description) {
        return (Matcher<? super R> matcherForTransformedValue) -> new TransformingMatcher<>(transformer, description,
                matcherForTransformedValue);
    }

    static <T, R> Is<T, R> createFrom(Matcher<? super T> matcher, Function<T, R> transformer, String description) {
        return (Matcher<? super R> matcherForTransformedValue) -> {
            TransformingMatcher<T, R> transformingMatcher = new TransformingMatcher<>(transformer, description,
                    matcherForTransformedValue);
            return new AbstractIntermediateMatcher<T>(null, matcher, transformingMatcher) {

                @Override
                protected void doesMatch(ExecutionContext executionContext, T actual) {
                    if (!matcher.matches(executionContext, actual) || !transformingMatcher.matches(executionContext, actual)) {
                        executionContext.mismatch("XX");
                    }
                }
            };
        };
    }

    Matcher<T> is(Matcher<? super R> matcher);
}
