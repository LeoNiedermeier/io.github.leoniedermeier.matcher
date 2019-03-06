package io.github.leoniedermeier.matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.internal.DefaultSimpleExecutionContextMessageCreator;

public class TestUtils {

    public static <T> ExecutionContext assertMatcher(T input, boolean downstreamMatcherResult,
            Function<TestMatcher, Matcher<T>> matcher, boolean expectedMatcherOutcome,
            Object expectedActualInDownstreamMatcher) {
        ExecutionContext context = new ExecutionContext();
        TestMatcher testMatcher = new TestMatcher(downstreamMatcherResult);
        assertTrue(expectedMatcherOutcome == matcher.apply(testMatcher).matches(input, context),
                "Macther returned false");
        assertTrue(testMatcher.isCalled(), "Downstream matcher not called");

        assertEquals(expectedActualInDownstreamMatcher, testMatcher.getActual(),
                "Downstream matcher called with unexpected value");
        print(context);

        return context;
    }

    public static <T> TestMatcher assertMatcherFalse(T input, Function<TestMatcher, Matcher<T>> matcher) {
        ExecutionContext context = new ExecutionContext();
        TestMatcher testMatcher = new TestMatcher(true);
        assertFalse(matcher.apply(testMatcher).matches(input, context), "Matcher returned true");
        print(context);
        return testMatcher;
    }

    public static <T> ExecutionContext assertMatcherFalse(T actual, Matcher<T> matcher) {
        ExecutionContext context = new ExecutionContext();
        assertFalse(matcher.matches(actual, context));
        print(context);

        return context;
    }

    public static <T> ExecutionContext assertMatcherTrue(T input, boolean downstreamMatcherResult,
            Function<TestMatcher, Matcher<T>> matcher, Object expectedActualInDownstreamMatcher) {

        return assertMatcher(input, downstreamMatcherResult, matcher, true, expectedActualInDownstreamMatcher);
    }

    // (x -> true) ->
    public static <T> TestMatcher assertMatcherTrue(T input, Function<TestMatcher, Matcher<T>> matcher) {
        ExecutionContext context = new ExecutionContext();
        TestMatcher testMatcher = new TestMatcher(x -> true);
        assertTrue(matcher.apply(testMatcher).matches(input, context), "Matcher returned false");
        print(context);
        return testMatcher;
    }

    public static <T> ExecutionContext assertMatcherTrue(T actual, Matcher<T> matcher) {
        ExecutionContext context = new ExecutionContext();
        assertTrue(matcher.matches(actual, context));
        print(context);

        return context;
    }

    public static void assertTestMatcher(TestMatcher testMatcher, int numberOfInvocations, Object actuals) {
        assertEquals(numberOfInvocations, testMatcher.getNumberOfInvocations(), "TestMatcher called unexpeced");
        assertEquals(actuals, testMatcher.getActual(), "TestMatcher called with unexpected value");

    }

    public static <T> void testActualNull(Function<TestMatcher, Matcher<T>> matcher) {
        TestMatcher testMatcher = assertMatcherFalse(null, matcher);
        assertTestMatcher(testMatcher, 0, null);
    }

    private static void print(ExecutionContext context) {
        System.out.println("====================");
        System.out.println(new DefaultSimpleExecutionContextMessageCreator().toMessage(context));
    }

}
