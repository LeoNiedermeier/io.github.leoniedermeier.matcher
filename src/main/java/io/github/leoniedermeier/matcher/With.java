package io.github.leoniedermeier.matcher;

import java.util.function.Function;

public interface With<T, R> extends Matcher<T> {

    static <T, R> Is<T, R> with(Matcher<? super T> matcher, Function<T, R> transformer, String description) {
        return
        // Implementierung der is(..) methode: liefert einen Matcher<T>: (T actual,
        // ExecutionContext context) -> ....
        (Matcher<? super R> downstreamMatcher) -> (T actual, ExecutionContext context) -> {
            // matcher ist der Matcher<T>, bei dem with(...) methode aufgerufen wird

            // !! doesMatch erzeugt keinen Child-Context
            if (!matcher.doesMatch(actual, context)) {
                return false;
            }
            // eigentlich sollte with kind von matcher /Matcher<T> Kontext sein, kein
            // sibling (ist mit doesMatch, s.o. erreicht)
            ExecutionContext downstreamContext = new ExecutionContext();
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
