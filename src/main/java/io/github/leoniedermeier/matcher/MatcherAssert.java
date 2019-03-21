package io.github.leoniedermeier.matcher;

import io.github.leoniedermeier.matcher.imp.BaseMatcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;
import io.github.leoniedermeier.matcher.imp.ExpectationMessageCreator;

public final class MatcherAssert {
    public static void assertThat(String reason, boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason);
        }
    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        ExecutionContext executionContext = new ExecutionContext((BaseMatcher<?>) matcher);
        boolean matches = matcher.matches(executionContext, actual);
        if (!matches) {
            ExpectationMessageCreator messageCreator = new ExpectationMessageCreator(executionContext.getRootState(), executionContext.getLastActual());

            messageCreator.createAndThrowMultipleFailuresError(reason);
        }
    }

    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat(null, actual, matcher);
    }

    private MatcherAssert() {
        throw new AssertionError("No MatcherAssert instances for you!");
    }
}
