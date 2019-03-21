package io.github.leoniedermeier.matcher.imp;

public class MismatchMessage implements Message {

    private Object actual;
    private Object expected;
    private String text;

    public MismatchMessage(String text, Object actual, Object expected) {
        this.text = text;
        this.actual = actual;
        this.expected = expected;
    }

    public Object getActual() {

        return actual;
    }

    public Object getExpected() {
        return expected;
    }

    @Override
    public String toString() {
        return String.format(text, actual, expected);
    }
}