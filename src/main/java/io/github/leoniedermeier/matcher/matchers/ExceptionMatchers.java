package io.github.leoniedermeier.matcher.matchers;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;

/**
 * <h1>NOTE (from javadoc):</h1> An {@code Error} is a subclass of
 * {@code Throwable} that indicates serious problems that a reasonable
 * application should not try to catch. Most such errors are abnormal
 * conditions.
 **/
public final class ExceptionMatchers {

    @FunctionalInterface
    public interface Executable {

        @SuppressWarnings("squid:S00112")
        // throw generic exception
        void execute() throws Exception;

    }

    public static class ExecutableThrowsMatcher<T extends Exception> extends AbstractIntermediateMatcher<Executable> {

        private static Exception execute(Executable actual) {
            try {
                actual.execute();
            } catch (Exception e) {
                return e;
            }
            return null;
        }

        private Matcher<Exception> exceptionOfType;

        private Class<T> expected;

        public ExecutableThrowsMatcher(Class<T> expected, Matcher<Exception> exceptionOfType) {
            super("execution throws", exceptionOfType);
            this.expected = expected;
            this.exceptionOfType = exceptionOfType;
        }

        public Matcher<Executable> with(Matcher<? super T> matcher) {
            return PropertyAccess.<Executable, Exception>property(ExecutableThrowsMatcher::execute, "throws a ")
                    .is(Is.createFrom(this.exceptionOfType, this.expected::cast, "and").is(matcher));
        }

        @Override
        protected void doesMatch(ExecutionContext executionContext, Executable actual) {
            // Details in matcher below
            Exception exception = execute(actual);
            if (!this.exceptionOfType.matches(executionContext, exception)) {
                executionContext.mismatch("execution throws");
            }
        }
    }

    public static <T extends Exception> ExecutableThrowsMatcher<T> throwsA(Class<T> expected) {
        return new ExecutableThrowsMatcher<>(expected, isExceptionOfType(expected));
    }

    public static <T extends Exception> Matcher<Executable> throwsA(Class<T> expected, Matcher<? super T> matcher) {

        return new ExecutableThrowsMatcher<>(expected, isExceptionOfType(expected)).with(matcher);
    }

    private static <T extends Exception, R> Matcher<T> isExceptionOfType(Class<R> expected) {
        return new AbstractTerminalMatcher<T>("exception of type <%s>", expected) {

            @Override
            protected void doesMatch(ExecutionContext executionContext, T actual) {
                if (expected.isInstance(actual)) {
                    return;
                }
                if (actual == null) {
                    executionContext.mismatch("nothing", null, expected);
                } else {
                    executionContext.mismatch("<%s> is not an exception of type <%s>", actual.getClass(), expected);
                }
            }
        };
    }

    private ExceptionMatchers() {
        throw new AssertionError("No ExceptionMatchers instances for you!");
    }
}
