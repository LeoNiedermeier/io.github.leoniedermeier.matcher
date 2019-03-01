package io.github.leoniedermeier.matcher;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.internal.SimpleExecutionContext;

public interface With<T, R> extends Matcher<T> {

    default Is<T, R> with(Function<T, R> transformer, String description) {
        return with(this, transformer, description);
    }

    static <T, R> Is<T, R> with(Matcher<? super T> matcher, Function<T, R> transformer, String description) {
        return new Is<T, R>() {
            @Override
            public Matcher<T> is(Matcher<? super R> subMatcher) {
                return (T actual, ExecutionContext context) -> {
                    boolean matches = matcher.matches(actual, context);
                    if (!matches) {
                        return false;
                    }
                    SimpleExecutionContext downstream = new SimpleExecutionContext();
                    context.addChild(downstream);
                    downstream.setExpectation(description);
                    downstream.setMismatch(description);
                    R transformed = transformer.apply(actual);
                    return subMatcher.matches(transformed, downstream);
                };
            }
        };
    }
}