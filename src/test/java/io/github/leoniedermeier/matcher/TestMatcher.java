package io.github.leoniedermeier.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TestMatcher implements BaseMatcher<Object> {

    private List<Object> actuals = new ArrayList<>();
    private int numberOfInvocations = 0;
    private Predicate<Object> predicate;

    public TestMatcher(boolean value) {
        super();
        this.predicate = x -> value;
    }

    public TestMatcher(Predicate<Object> predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public boolean doesMatch(Object actual, ExecutionContext context) {
        this.numberOfInvocations++;
        this.actuals.add(actual);
        if (!this.predicate.test(actual)) {
            context.setMismatch("TestMatcher mismatch");
            return false;
        }
        return true;
    }

    public Object getActual() {
        if (this.actuals.size() == 0) {
            return null;
        } else if (this.actuals.size() == 1) {
            return this.actuals.get(0);
        }
        return this.actuals;
    }

    @Override
    public Entry getExpectation() {
        return new Entry("Testmatcher");
    }

    public int getNumberOfInvocations() {
        return this.numberOfInvocations;
    }

    public boolean isCalled() {
        return this.numberOfInvocations > 0;
    }
}
