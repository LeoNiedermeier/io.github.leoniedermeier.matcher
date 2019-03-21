package io.github.leoniedermeier.matcher.imp;

public class ExpectedMessage implements Message {

    private Object expected;
    private String text;

    public ExpectedMessage(String text, Object expected) {
        this.text = text;
        this.expected = expected;
    }

    public Object getExpected() {
        return expected;
    }

    @Override
    public String toString() {
        return String.format(text, expected);
    }
}