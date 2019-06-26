package io.github.leoniedermeier.matcher.matchers;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;
import io.github.leoniedermeier.matcher.matchers.ArrayMatchersUtils.IndexEntry;

public class ArrayMatchers {

    public static <T> Matcher<T> equalTo(T expected) {
        Objects.requireNonNull(expected, "ArrayMatchers.euqalTo -  expected array is <null>");
        AbstractTerminalMatcher<T> matcher = new AbstractTerminalMatcher<T>("arrays equals", null) {

            @Override
            protected void doesMatch(ExecutionContext executionContext, T actual) {
                LinkedList<IndexEntry> indices = new LinkedList<>();
                boolean matches = ArrayMatchersUtils.deepEquals0(actual, expected, indices);
                if (!matches) {
                    createMessage(executionContext, indices);
                }

            }

            private void createMessage(ExecutionContext executionContext, LinkedList<IndexEntry> indices) {
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
                executionContext.mismatch(message, last.getValue1(), last.getValue2());
            }
        };
        return Is.<T, T>createFrom(isArray(), Function.identity(), "and").is(matcher);
    }

    public static <T> Matcher<T> isArray() {

        return new AbstractTerminalMatcher<T>("is an array", null) {

            @Override
            protected void doesMatch(ExecutionContext executionContext, T actual) {
                if (!actual.getClass().isArray()) {
                    executionContext.mismatch("<%s> is not an array", actual, null);
                }
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
