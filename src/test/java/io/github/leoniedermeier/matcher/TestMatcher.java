package io.github.leoniedermeier.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TestMatcher implements Matcher<Object> {

	private int numberOfInvocations = 0;
	private List<Object> actuals = new ArrayList<>();
	private Predicate<Object> predicate;

	public TestMatcher(boolean value) {
		this.predicate = x -> value;
	}
	public TestMatcher(Predicate<Object> predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean doesMatch(Object actual, ExecutionContext context) {
		context.setExpectation("TestMatcher");
		context.setMismatch("TestMatcher mismatch");
		this.numberOfInvocations++;
		this.actuals.add(actual);
		return this.predicate.test(actual);
	}

	public Object getActual() {
		if (actuals.size() == 0) {
			return null;
		} else if (actuals.size() == 1) {
			return actuals.get(0);
		}
		return actuals;
	}

	public boolean isCalled() {
		return numberOfInvocations > 0;
	}

	public int getNumberOfInvocations() {
		return numberOfInvocations;
	}

}
