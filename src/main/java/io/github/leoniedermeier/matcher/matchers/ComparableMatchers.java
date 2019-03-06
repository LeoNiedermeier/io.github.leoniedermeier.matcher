package io.github.leoniedermeier.matcher.matchers;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.IntPredicate;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public final class ComparableMatchers {

    private enum Compare {
        EQUALS_TO(x -> x > 0, "equals to"), //
        GREATER_OR_EQUALS_THAN(x -> x >= 0, "greater or equals than"), //
        GREATER_THAN(x -> x > 0, "greater than"), //
        LESS_OR_EQUALS_THAN(x -> x <= 0, "less or equals than"), //
        LESS_THAN(x -> x < 0, "less than");

        private String expectationText;
        private IntPredicate predicate;

        Compare(IntPredicate predicate, String expectationText) {
            this.predicate = predicate;
            this.expectationText = expectationText;
        }
    }

    public static <T extends Comparable<T>> Matcher<T> greaterOrEqualsThan(T expected) {
        return createMatcher(expected, T::compareTo, false, Compare.GREATER_OR_EQUALS_THAN);
    }

    public static <T> Matcher<T> greaterOrEqualsThan(T expected, Comparator<? super T> comparator) {
        return createMatcher(expected, comparator, true, Compare.GREATER_OR_EQUALS_THAN);
    }

    public static <T extends Comparable<T>> Matcher<T> greaterThan(T expected) {
        return createMatcher(expected, T::compareTo, false, Compare.GREATER_THAN);
    }

    public static <T> Matcher<T> greaterThan(T expected, Comparator<? super T> comparator) {
        return createMatcher(expected, comparator, true, Compare.GREATER_THAN);
    }

    public static <T extends Comparable<T>> Matcher<T> lessOrEqualsThan(T expected) {
        return createMatcher(expected, T::compareTo, false, Compare.LESS_OR_EQUALS_THAN);
    }

    public static <T> Matcher<T> lessOrEqualsThan(T expected, Comparator<? super T> comparator) {
        return createMatcher(expected, comparator, true, Compare.LESS_OR_EQUALS_THAN);
    }

    public static <T extends Comparable<T>> Matcher<T> lessThan(T expected) {
        return createMatcher(expected, T::compareTo, false, Compare.LESS_THAN);
    }

    public static <T> Matcher<T> lessThan(T expected, Comparator<? super T> comparator) {
        return createMatcher(expected, comparator, true, Compare.LESS_THAN);
    }

    private static <T> Matcher<T> createMatcher(T expected, Comparator<? super T> comparator, boolean ld,
            Compare compare) {
        Objects.requireNonNull(expected, "ComparableMatchers - expected is <null>");
        Objects.requireNonNull(comparator, "ComparableMatchers - comparator is <null>");

        return (T actual, ExecutionContext context) -> {
            context.setExpectation("is " + compare.expectationText + " <%s>" + (ld ? " compared by <%s>" : ""),
                    expected, comparator);
            context.setMismatch("<%s> which is not " + compare.expectationText + " <%s>", actual, expected);
            return compare.predicate.test(comparator.compare(actual, expected));
        };
    }

    private ComparableMatchers() {
        throw new AssertionError("No ComparableMatchers instances for you!");
    }
}
