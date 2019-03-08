package io.github.leoniedermeier.matcher;

public class Entry {
    public final Object[] arguments;
    public final String text;

    public Entry(String text, Object... arguments) {
        this.text = text;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return String.format(this.text, this.arguments);
    }
}