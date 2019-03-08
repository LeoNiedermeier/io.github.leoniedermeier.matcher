package io.github.leoniedermeier.matcher.matchers;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.matchers.ArrayMatchersUtils.IndexEntry;

public class ArrayMatchers {

    public static <T> Matcher<T> equalTo(T expected) {
        Objects.requireNonNull(expected, "ArrayMatchers.euqalTo -  expected array is <null>");
        AbstractTerminalMatcher<T> matcher = new AbstractTerminalMatcher<T>("arrays equals") {

            @Override
            public boolean doesMatch(T actual, ExecutionContext context) {
                LinkedList<IndexEntry> indices = new LinkedList<>();
                boolean matches = ArrayMatchersUtils.deepEquals0(actual, expected, indices);
                if (!matches) {
                    IndexEntry last = indices.getLast();
                    String index = indices.stream().map(IndexEntry::getIndex).filter(Objects::nonNull)
                            .map(String::valueOf).collect(Collectors.joining("][", "[", "]"));

                    String message = "arrays different";
                    if (!"[]".equals(index)) {
                        message += " at index" + index;
                    }

                    if (last.getMismatch() != null) {
                        message += ": " + last.getMismatch().toString();
                    } else {
                        message += ", expected <%s> but was <%s>";
                    }
                    context.setMismatch(message, last.getValue1(), last.getValue2());
                }
                return matches;
            }
        };
        return Is.<T, T>createFrom(isArray(), Function.identity(), "and").is(matcher);
    }

    public static <T> Matcher<T> isArray() {

        return new AbstractTerminalMatcher<T>("is an array") {

            @Override
            public boolean doesMatch(T actual, ExecutionContext context) {
                if (!actual.getClass().isArray()) {
                    context.setMismatch("<%s> is not an array", actual);
                    return false;
                }
                return true;
            }
        };
    }

    public static <T> Matcher<T> length(Matcher<? super Integer> matcher) {
        Objects.requireNonNull(matcher, "ArrayMatchers.length - matcher is <null>");
        return Is.<T, Integer>createFrom(isArray(), Array::getLength, "with length").is(matcher);
    }

    private ArrayMatchers() {
        throw new AssertionError("No ArrayMatchers instances for you!");
    }
}
