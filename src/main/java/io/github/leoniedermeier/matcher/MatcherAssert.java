package io.github.leoniedermeier.matcher;

import io.github.leoniedermeier.matcher.internal.DefaultSimpleExecutionContextMessageCreator;

public final class MatcherAssert {
    private MatcherAssert() {
        throw new AssertionError("No MatcherAssert instances for you!");
    }

    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat(null, actual, matcher);
    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        ExecutionContext context = new ExecutionContext();
        boolean matches = matcher.matches(actual, context);
        if (!matches) {
            // TODO reason
            throw new AssertionError(new DefaultSimpleExecutionContextMessageCreator().toMessage(context));
        }
    }

    public static void assertThat(String reason, boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason);
        }
    }
}
