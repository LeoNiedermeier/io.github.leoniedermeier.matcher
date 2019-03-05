package io.github.leoniedermeier.matcher;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.internal.SimpleExecutionContext;

public interface With<T, R> extends Matcher<T> {

    static <T, R> Is<T, R> with(Matcher<? super T> matcher, Function<T, R> transformer, String description) {
        return (Matcher<? super R> downstreamMatcher) -> (T actual, ExecutionContext context) -> {
            if (!matcher.matches(actual, context)) {
                return false;
            }
            SimpleExecutionContext downstreamContext = new SimpleExecutionContext();
            context.addChild(downstreamContext);
            downstreamContext.setExpectation(description);
            downstreamContext.setMismatch(description);
            R transformed = transformer.apply(actual);
            return downstreamMatcher.matches(transformed, downstreamContext);
        };
    }

    default Is<T, R> with(Function<T, R> transformer, String description) {
        return with(this, transformer, description);
    }
}
