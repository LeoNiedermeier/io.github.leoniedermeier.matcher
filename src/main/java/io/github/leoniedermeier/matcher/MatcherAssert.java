package io.github.leoniedermeier.matcher;

public final class MatcherAssert {
    public static void assertThat(String reason, boolean assertion) {
        if (!assertion) {
            throw new AssertionError(reason);
        }
    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        ExecutionContext context = new ExecutionContext();

        boolean matches = matcher.matches(actual, context);
        if (!matches) {
            ExpectationMessageCreator collector = new ExpectationMessageCreator(context.getTreeEntry());
            
            String message = (reason != null ? reason : "") //
                    + System.lineSeparator() //
                    +  collector.getMessage();
            if (context.getActual() != null) {
                message += System.lineSeparator() //
                        + ("Actual:")//
                        + System.lineSeparator() //
                        + "    " + context.getActual();
            }
            throw new AssertionError(message);
        }
    }

    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat(null, actual, matcher);
    }

    private MatcherAssert() {
        throw new AssertionError("No MatcherAssert instances for you!");
    }
}
