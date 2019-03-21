package io.github.leoniedermeier.matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.imp.BaseMatcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;

public class TestUtils {

    public static <T> TestMatcher assertMatcherFalse(T input, Function<TestMatcher, Matcher<T>> matcher) {
        TestMatcher testMatcher = new TestMatcher(true);
        Matcher<T> matcherToUse = matcher.apply(testMatcher);
        ExecutionContext executionContext = new ExecutionContext((BaseMatcher<?>) matcherToUse);
        assertFalse(matcherToUse.matches(executionContext, input), "Matcher returned true");
        return testMatcher;
    }

    public static <T> ExecutionContext assertMatcherFalse(T actual, Matcher<T> matcher) {
        ExecutionContext executionContext = new ExecutionContext((BaseMatcher<?>) matcher);
        assertFalse(matcher.matches(executionContext, actual));
        return executionContext;
    }

    public static <T> TestMatcher assertMatcherTrue(T input, Function<TestMatcher, Matcher<T>> matcher) {
        TestMatcher testMatcher = new TestMatcher(true);
        Matcher<T> matcherToUse = matcher.apply(testMatcher);
        ExecutionContext executionContext = new ExecutionContext((BaseMatcher<?>) matcherToUse);
        assertTrue(matcherToUse.matches(executionContext, input), "Matcher returned false");
        return testMatcher;
    }

    public static <T> ExecutionContext assertMatcherTrue(T actual, Matcher<T> matcher) {
        ExecutionContext executionContext = new ExecutionContext((BaseMatcher<?>) matcher);
        assertTrue(matcher.matches(executionContext, actual));
        return executionContext;
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
