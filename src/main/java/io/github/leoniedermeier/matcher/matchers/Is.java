package io.github.leoniedermeier.matcher.matchers;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

@FunctionalInterface
@SuppressWarnings("squid:S1711")
//We do not extend UnaryOperator because we do not want to UnaryOperator's default methods.
public interface Is<T, R> {

    static <T, R> Is<T, R> createFrom(Function<T, R> transformer, String description) {
        return (Matcher<? super R> matcherForTransformedValue) -> {
            TransformingMatcher<T, R> transformingMatcher = new TransformingMatcher<>(transformer, description,
                    matcherForTransformedValue);
            return new AbstractIntermediateMatcher<T>(null, transformingMatcher) {

                @Override
                public boolean doesMatch(T actual, ExecutionContext context) {
                    return transformingMatcher.matches(actual, context);
                }
            };

        };

    }

    static <T, R> Is<T, R> createFrom(Matcher<? super T> matcher, Function<T, R> transformer, String description) {
        return (Matcher<? super R> matcherForTransformedValue) -> {
            TransformingMatcher<T, R> transformingMatcher = new TransformingMatcher<>(transformer, description,
                    matcherForTransformedValue);
            return new AbstractIntermediateMatcher<T>(null, matcher, transformingMatcher) {

                @Override
                public boolean doesMatch(T actual, ExecutionContext context) {
                    return matcher.matches(actual, context) && transformingMatcher.matches(actual, context);
                }
            };

        };

    }

    Matcher<T> is(Matcher<? super R> matcher);
}
