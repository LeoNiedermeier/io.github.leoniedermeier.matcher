package io.github.leoniedermeier.matcher.matchers;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

/**
 * <h1>NOTE:</h1> An {@code Error} is a subclass of {@code Throwable} that
 * indicates serious problems that a reasonable application should not try to
 * catch. Most such errors are abnormal conditions.
 **/
public final class ExceptionMatchers {

    public static class ExecutableThrowsMatcher<T extends Exception> implements Matcher<Executable> {

        private Class<T> expected;
        private Matcher<? super T> matcher = (x, y) -> true;

        public ExecutableThrowsMatcher(Class<T> expected) {
            this.expected = expected;
        }

        @Override
        public boolean doesMatch(Executable actual, ExecutionContext context) {
            // Details in matcher below
            context.setExpectation("execution throws");
            context.setMismatch("execution throws");
            Exception exception = execute(actual);
            // cast checked in first matches
            @SuppressWarnings("unchecked")
            boolean b = isExceptionOfType(expected).matches(exception, context)
                    // TODO: message + "with"
                    && matcher.matches((T) exception, context);
            return b;
        }

        public Matcher<Executable> with(Matcher<? super T> matcher) {
            this.matcher = matcher;
            return this;
        }
    }

    @FunctionalInterface
    public interface Executable {

        void execute() throws Exception;

    }

    public static <T extends Exception> ExecutableThrowsMatcher<T> throwsA(Class<T> expected) {
        return new ExecutableThrowsMatcher<>(expected);
    }

    public static <T extends Exception> ExecutableThrowsMatcher<T> throwsAX(Class<T> expected) {
        return new ExecutableThrowsMatcher<>(expected);
    }

    public static <T extends Exception> Matcher<Executable> throwsA(Class<T> expected, Matcher<? super T> matcher) {

        return new ExecutableThrowsMatcher<>(expected).with(matcher);
    }

    private static <T extends Exception> Matcher<T> isExceptionOfType(Class<?> expected) {
        return (T actual, ExecutionContext context) -> {
            // same as isInstanceOf, but different messages
            context.setExpectation("exception of type <%s>", expected);
            if (expected.isInstance(actual)) {
                return true;
            }
            if (actual == null) {
                context.setMismatch("nothing");
            } else {
                context.setMismatch("<%s> wihich is not an exception of type <%s>", actual.getClass(), expected);
            }
            return false;
        };
    }

    private static Exception execute(Executable actual) {
        try {
            actual.execute();
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    private ExceptionMatchers() {
        throw new AssertionError("No ExceptionMatchers instances for you!");
    }
}
