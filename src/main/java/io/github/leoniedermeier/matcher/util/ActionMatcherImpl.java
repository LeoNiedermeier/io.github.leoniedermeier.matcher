package io.github.leoniedermeier.matcher.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public class ActionMatcherImpl<T, X> implements ActionMatcher<T,X> {

	private final List<Matcher<? super X>> downstreams = new LinkedList<>();

	private final Function<T, X> transformer;

	private final String description;

	public ActionMatcherImpl(Function<T, X> fransformer, String description) {
		this.transformer = fransformer;
		this.description = description;
	}

	@Override
	public boolean doesMatch(T actual, ExecutionContext context) {
		X x = transformer.apply(actual);
		context.setExpectation(description);
		context.setMismatch(description);
		return executeDownstreamMatchers(context, x);
	}

	protected boolean executeDownstreamMatchers(ExecutionContext context, X x) {
		for (Matcher<? super X> matcher : downstreams) {
			boolean matches = matcher.matches(x, context);
			if (!matches) {
				return false;
			}
		}
		return true;
	}

	public ActionMatcherImpl<T, X> is(Matcher<? super X> downstream) {
		downstreams.add(downstream);
		return this;
	}
}
