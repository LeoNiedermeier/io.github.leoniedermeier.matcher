package io.github.leoniedermeier.matcher;

import io.github.leoniedermeier.matcher.internal.DescriptionStringifier;
import io.github.leoniedermeier.matcher.internal.SimpleExecutionContext;

public final class MatcherAssert {
    private MatcherAssert() {
        throw new AssertionError("No MatcherAssert instances for you!");
    }
    
    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat(null, actual, matcher);
    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        SimpleExecutionContext description = new SimpleExecutionContext();
        boolean matches = matcher.matches(actual, description);
        if (!matches) {
            // TODO reason
            throw new AssertionError(DescriptionStringifier.getExpectationsAsSting(description));
        }
        System.out.println("======================================");
        System.out.println(DescriptionStringifier.getExpectationsAsSting(description));
    }

    public static void assertThat(String reason, boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason);
        }
    }
}
