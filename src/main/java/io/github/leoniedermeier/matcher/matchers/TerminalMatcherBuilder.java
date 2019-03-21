package io.github.leoniedermeier.matcher.matchers;

import java.util.function.Function;
import java.util.function.Predicate;

import io.github.leoniedermeier.matcher.imp.BaseMatcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;
import io.github.leoniedermeier.matcher.imp.ExpectedMessage;
import io.github.leoniedermeier.matcher.imp.MismatchMessage;

public class TerminalMatcherBuilder {

    private static class TerminalMatcherBuilderHelper<T> extends AbstractTerminalMatcher<T> implements WithPredicate<T>, AndMismatch<T> {

        private Function<T, MismatchMessage> mismatchMessageSupplier;
        private Predicate<T> predicate;

        public TerminalMatcherBuilderHelper(ExpectedMessage expectation) {
            super(expectation);
        }

        @Override
        public BaseMatcher<T> andMismatch(Function<T, MismatchMessage> mismatchMessageSupplier) {
            this.mismatchMessageSupplier = mismatchMessageSupplier;
            return this;
        }

        @Override
        public AndMismatch<T> withPredicate(Predicate<T> predicate) {
            this.predicate = predicate;
            return this;
        }

        @Override
        protected void doesMatch(ExecutionContext executionContext, @NonNull T actual) {
            if (!predicate.test(actual)) {
                executionContext.mismatch(mismatchMessageSupplier.apply(actual));
            }
        }
    }

    interface AndMismatch<T> {
        BaseMatcher<T> andMismatch(Function<T, MismatchMessage> mismatchMessageSupplier);
    }

    interface WithPredicate<T> {
        AndMismatch<T> withPredicate(Predicate<T> predicate);
    }

    public static <T> WithPredicate<T> withExpectation(String text, Object expected) {
        return new TerminalMatcherBuilderHelper<>(new ExpectedMessage(text, expected));
    }
}
