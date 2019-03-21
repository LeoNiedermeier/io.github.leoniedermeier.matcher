package io.github.leoniedermeier.matcher.matchers;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;

public class TransformingMatcher<T, R> extends AbstractIntermediateMatcher<T> {
    private String description;
    private Matcher<? super R> matcherForTransformedValue;
    private Function<T, R> transformer;

    public TransformingMatcher(Function<T, R> transformer, String description,
            Matcher<? super R> matcherForTransformedValue) {
        super(description, matcherForTransformedValue);
        this.transformer = transformer;
        this.description = description;
        this.matcherForTransformedValue = matcherForTransformedValue;
    }

    @Override
    protected void doesMatch(ExecutionContext executionContext, @NonNull T actual) {
        R transformed = this.transformer.apply(actual);
        if (!this.matcherForTransformedValue.matches(executionContext, transformed)) {
            executionContext.mismatch(description);
        }
    }
}
