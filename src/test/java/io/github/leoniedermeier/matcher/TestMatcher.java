package io.github.leoniedermeier.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import io.github.leoniedermeier.matcher.imp.BaseMatcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;
import io.github.leoniedermeier.matcher.imp.ExpectedMessage;
import io.github.leoniedermeier.matcher.imp.Message;

public class TestMatcher extends BaseMatcher<Object> {

    private List<Object> actuals = new ArrayList<>();
    private int numberOfInvocations = 0;
    private Predicate<Object> predicate;

    public TestMatcher(boolean value) {
        super();
        predicate = x -> value;
    }

    public TestMatcher(Predicate<Object> predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    protected void doesMatch(ExecutionContext executionContext, Object actual) {
        numberOfInvocations++;
        actuals.add(actual);
        if (!predicate.test(actual)) {
            executionContext.mismatch("TestMatcher mismatch");
        }
    }

    public Object getActual() {
        if (actuals.size() == 0) {
            return null;
        } else if (actuals.size() == 1) {
            return actuals.get(0);
        }
        return actuals;
    }

    @Override
    protected Message getExpectation() {
        return new ExpectedMessage("Testmatcher", null);
    }

    public int getNumberOfInvocations() {
        return numberOfInvocations;
    }

    public boolean isCalled() {
        return numberOfInvocations > 0;
    }
}
