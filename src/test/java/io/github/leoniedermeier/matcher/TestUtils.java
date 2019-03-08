package io.github.leoniedermeier.matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;

public class TestUtils {

    public static <T> TestMatcher assertMatcherFalse(T input, Function<TestMatcher, Matcher<T>> matcher) {
        ExecutionContext context = new ExecutionContext();
        TestMatcher testMatcher = new TestMatcher(true);
        assertFalse(matcher.apply(testMatcher).matches(input, context), "Matcher returned true");
        return testMatcher;
    }

    public static <T> ExecutionContext assertMatcherFalse(T actual, Matcher<T> matcher) {

        ExecutionContext context = new ExecutionContext();
        assertFalse(matcher.matches(actual, context));
        return context;

    }

    public static <T> TestMatcher assertMatcherTrue(T input, Function<TestMatcher, Matcher<T>> matcher) {
        ExecutionContext context = new ExecutionContext();
        TestMatcher testMatcher = new TestMatcher(true);
        assertTrue(matcher.apply(testMatcher).matches(input, context), "Matcher returned false");
        return testMatcher;
    }

    public static <T> ExecutionContext assertMatcherTrue(T actual, Matcher<T> matcher) {
        ExecutionContext context = new ExecutionContext();
        assertTrue(matcher.matches(actual, context));
        return context;
    }

    public static void assertTestMatcher(TestMatcher testMatcher, int numberOfInvocations, Object actuals) {
        assertEquals(numberOfInvocations, testMatcher.getNumberOfInvocations(), "TestMatcher called unexpeced");
        assertEquals(actuals, testMatcher.getActual(), "TestMatcher called with unexpected value");

    }

    public static <T> void testNullNotMatches(Function<TestMatcher, Matcher<T>> matcher) {
        TestMatcher testMatcher = assertMatcherFalse(null, matcher);
        assertTestMatcher(testMatcher, 0, null);
    }
}
