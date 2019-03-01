package io.github.leoniedermeier.matcher.util;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public class PropertyAccess {

    @FunctionalInterface
	public interface Is<T, R> {
		Matcher<T> is(Matcher<? super R> matcher);
	}

	public static <T, R> ActionMatcher<T, R> property(Function<T, R> transformer, String description) {
		return new ActionMatcherImpl<>(transformer, description);
	}

	public static <T, R> Is<T, R> propertyX(Function<T, R> transformer) {
		return matcher -> (T actual, ExecutionContext context) -> matcher
				.matches(transformer.apply(requireNonNull(actual, "Element to access is <null>")), context);

	}

	public static <T, R> Matcher<T> property(Function<T, R> transformer, Matcher<? super R> matcher) {
		return (T actual, ExecutionContext context) -> matcher
				.matches(transformer.apply(requireNonNull(actual, "Element to access is <null>")), context);
	}
}