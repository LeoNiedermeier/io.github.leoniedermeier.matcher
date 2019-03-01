package io.github.leoniedermeier.matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.internal.DescriptionStringifier;
import io.github.leoniedermeier.matcher.internal.SimpleExecutionContext;

public class TestUtils {

	public static <T> SimpleExecutionContext assertMatcherTrue(T input, boolean downstreamMatcherResult,
			Function<TestMatcher, Matcher<T>> matcher, Object expectedActualInDownstreamMatcher) {

		return assertMatcher(input, downstreamMatcherResult, matcher, true, expectedActualInDownstreamMatcher);
	}

	public static void assertTestMatcher(TestMatcher testMatcher, int numberOfInvocations, Object actuals) {
		assertEquals(numberOfInvocations, testMatcher.getNumberOfInvocations(), "TestMatcher called unexpeced");
		assertEquals(actuals, testMatcher.getActual(), "TestMatcher called with unexpected value");

	}

	// (x -> true) ->
	public static <T> TestMatcher assertMatcherTrue(T input, Function<TestMatcher, Matcher<T>> matcher) {
		SimpleExecutionContext context = new SimpleExecutionContext();
		TestMatcher testMatcher = new TestMatcher(x -> true);
		assertTrue(matcher.apply(testMatcher).matches(input, context), "Matcher returned false");
		print(context);
		return testMatcher;
	}

	public static <T> TestMatcher assertMatcherFalse(T input, Function<TestMatcher, Matcher<T>> matcher) {
		SimpleExecutionContext context = new SimpleExecutionContext();
		TestMatcher testMatcher = new TestMatcher(x -> true);
		assertFalse(matcher.apply(testMatcher).matches(input, context), "Matcher returned true");
		print(context);
		return testMatcher;
	}

	public static <T> SimpleExecutionContext assertMatcher(T input, boolean downstreamMatcherResult,
			Function<TestMatcher, Matcher<T>> matcher, boolean expectedMatcherOutcome,
			Object expectedActualInDownstreamMatcher) {
		SimpleExecutionContext context = new SimpleExecutionContext();
		TestMatcher testMatcher = new TestMatcher(downstreamMatcherResult);
		assertTrue(expectedMatcherOutcome == matcher.apply(testMatcher).matches(input, context),
				"Macther returned false");
		assertTrue(testMatcher.isCalled(), "Downstream matcher not called");

		assertEquals(expectedActualInDownstreamMatcher, testMatcher.getActual(),
				"Downstream matcher called with unexpected value");
		print(context);

		return context;
	}

	public static <T> SimpleExecutionContext assertMatcherFalse(T actual, Matcher<T> matcher) {
		SimpleExecutionContext context = new SimpleExecutionContext();
		assertFalse(matcher.matches(actual, context));
		SimpleExecutionContext contextOfMatcher = (SimpleExecutionContext) context.getChilds().get(0);
		System.out.println("====================");
		System.out.println(DescriptionStringifier.getExpectationsAsSting(contextOfMatcher));

		return contextOfMatcher;
	}

	public static <T> SimpleExecutionContext assertMatcherTrue(T actual, Matcher<T> matcher) {
		SimpleExecutionContext context = new SimpleExecutionContext();
		assertTrue(matcher.matches(actual, context));
		print(context);

		return context;
	}

	private static void print(SimpleExecutionContext context) {
		SimpleExecutionContext contextOfMatcher = (SimpleExecutionContext) context.getChilds().get(0);
		System.out.println("====================");
		System.out.println(DescriptionStringifier.getExpectationsAsSting(contextOfMatcher));
	}

}
