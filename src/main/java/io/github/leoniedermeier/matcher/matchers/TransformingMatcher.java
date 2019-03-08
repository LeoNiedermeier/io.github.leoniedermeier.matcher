package io.github.leoniedermeier.matcher.matchers;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

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
    public boolean doesMatch(@NonNull T actual, ExecutionContext context) {
        R transformed = this.transformer.apply(actual);
        if (!this.matcherForTransformedValue.matches(transformed, context)) {
            context.setMismatch(this.description);
            return false;
        }
        return true;
    }
}
