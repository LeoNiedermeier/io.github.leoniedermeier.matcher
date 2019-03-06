package io.github.leoniedermeier.matcher;

import java.util.ArrayList;
import java.util.List;

public class ExecutionContext {

    public static class Entry {
        private Object[] arguments;
        private String text;

        public Entry(String text, Object... arguments) {
            this.text = text;
            this.arguments = arguments;
        }

        @Override
        public String toString() {
            return String.format(this.text, this.arguments);
        }
    }

    private Object actual;

    private final List<ExecutionContext> childs = new ArrayList<>();

    private Entry expectation;
    private boolean invers;

    private boolean matched;

    private Entry mismatch;

    public ExecutionContext() {
        super();
    }

    
    public void addChild(ExecutionContext context) {
        this.childs.add(context);
    }

     
    public void clearChildren() {
        this.childs.clear();
    }

     
    public Object getActual() {
        return this.actual;
    }

    public List<ExecutionContext> getChilds() {
        return this.childs;
    }

    public Entry getExpectation() {
        return this.expectation;
    }

    public Entry getMismatch() {
        return this.mismatch;
    }

    
    public boolean isInvers() {
        return this.invers;
    }

    public boolean isMatched() {
        return this.matched;
    }

    public void setActual(Object actual) {
        this.actual = actual;
    }

    public void setExpectation(String text, Object... arguments) {
        this.expectation = new Entry(text, arguments);
    }

    public void setInvers(boolean invers) {
        this.invers = invers;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public void setMismatch(String text, Object... arguments) {
        this.mismatch = new Entry(text, arguments);
    }
}
