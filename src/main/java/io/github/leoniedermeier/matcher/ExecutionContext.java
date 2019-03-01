package io.github.leoniedermeier.matcher;

public interface ExecutionContext {

    void addChild(ExecutionContext description);

    void clearChildren();

    Object getActual();

    void setActual(Object object);

    void setExpectation(String text, Object... object);

    void setMismatch(String text, Object... object);

    void setMatched(boolean matched);

    boolean isMatched();
}
