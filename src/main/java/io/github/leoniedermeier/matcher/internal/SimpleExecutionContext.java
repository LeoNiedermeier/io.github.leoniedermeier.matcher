package io.github.leoniedermeier.matcher.internal;

import java.util.ArrayList;
import java.util.List;

import io.github.leoniedermeier.matcher.ExecutionContext;

public class SimpleExecutionContext implements ExecutionContext {

   
    public SimpleExecutionContext() {
        super();
    }
	private static class Entry {
		private Object[] arguments;
		private String text;

		public Entry(String text, Object... arguments) {
			this.text = text;
			this.arguments = arguments;
		}

		@Override
		public String toString() {
			return String.format(text, arguments);
		}
	}

	private Object actual;

	private final List<ExecutionContext> childs = new ArrayList<>();
	private Entry expectation;

	private Entry mismatch;

	private boolean matched;

	@Override
	public void addChild(ExecutionContext description) {
		childs.add(description);
	}

	@Override
	public Object getActual() {
		return actual;
	}

	public List<ExecutionContext> getChilds() {
		return childs;
	}

	public Entry getExpectation() {
		return expectation;
	}

	public Entry getMismatch() {
		return mismatch;
	}

	@Override
	public void setActual(Object actual) {
		this.actual = actual;
	}

	@Override
	public void setExpectation(String text, Object... arguments) {
		expectation = new Entry(text, arguments);
	}

	@Override
	public void setMismatch(String text, Object... arguments) {
		mismatch = new Entry(text, arguments);
	}

	@Override
	public void setMatched(boolean matched) {
		this.matched = matched;
	}

	public boolean isMatched() {
		return matched;
	}

	@Override
	public void clearChildren() {
		this.childs.clear();
	}
}
