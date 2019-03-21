package io.github.leoniedermeier.matcher.imp;

public class ExecutionContext {

    private BaseMatcherState currentState;

    private Object lastActual;

    private BaseMatcherState rootState;

    public ExecutionContext(BaseMatcher<?> matcher) {
        super();
        rootState = buildFrom(matcher);
        currentState = rootState;
    }

    private static BaseMatcherState buildFrom(BaseMatcher<?> current) {
        BaseMatcherState state = new BaseMatcherState(current);
        for (BaseMatcher<?> matcher : current.getChilds()) {
            state.addChild(buildFrom(matcher));
        }
        return state;
    }

    public Object getLastActual() {
        return lastActual;
    }

    public BaseMatcherState getRootState() {
        return rootState;
    }

    public void mismatch(MismatchMessage message) {
        currentState.setMismatch(message);
    }

    public void mismatch(String message) {
        currentState.setMismatch(new MismatchMessage(message, null, null));
    }

    public void mismatch(String message, Object actual, Object expected) {
        currentState.setMismatch(new MismatchMessage(message, actual, expected));
    }

    public void onEnter(BaseMatcher<?> matcher) {
        if (currentState.getMatcher() != matcher) {
            currentState = currentState.findFirstChild(t -> t.getMatcher() == matcher).orElseThrow(
                    () -> new IllegalStateException("No child found with matcher equals to current matcher"));
        }
        currentState.setExecuted();
    }

    public void onExit() {
        if (currentState.getParent() != null) {
            currentState = currentState.getParent();
        }
    }

    public void setLastActual(Object lastActual) {
        this.lastActual = lastActual;
    }

    public void setRootState(BaseMatcherState rootState) {
        this.rootState = rootState;
    }

    public boolean isMatched() {
        return currentState.isMatched();
    }
}
