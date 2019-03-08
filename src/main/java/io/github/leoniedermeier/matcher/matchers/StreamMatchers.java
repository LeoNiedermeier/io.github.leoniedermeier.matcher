package io.github.leoniedermeier.matcher.matchers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import io.github.leoniedermeier.matcher.ExecutionContext;
import io.github.leoniedermeier.matcher.Matcher;

public final class StreamMatchers {

    public static <T> Matcher<Stream<T>> allMatch(Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "StreamMatchers.allMatch - matcher is <null>");
        return new AbstractIntermediateMatcher<Stream<T>>("every item", matcher) {

            @Override
            public boolean doesMatch(@NonNull Stream<T> actual, ExecutionContext context) {

                List<T> theActuals = new ArrayList<>();
                boolean allMatched = true;
                for (Iterator<T> iterator = actual.iterator(); iterator.hasNext();) {
                    T t = iterator.next();
                    theActuals.add(t);
                    if (!matcher.matches(t, context)) {
                        allMatched = false;
                        break;
                    }
                }
                if (!allMatched) {
                    context.setMismatch("one item");
                }
                return allMatched;
            }
        };
    }

    public static <T> Matcher<Stream<T>> anyMatch(Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "StreamMatchers.anyMatch - matcher is <null>");
        return new AbstractIntermediateMatcher<Stream<T>>("at least one item", matcher) {

            @Override
            public boolean doesMatch(@NonNull Stream<T> actual, ExecutionContext context) {
                List<T> theActuals = new ArrayList<>();
                context.switchInvers();
                @SuppressWarnings("squid:S3864")
                // we use peek to collect the processed elements
                boolean anyMatched = actual.peek(theActuals::add).anyMatch(t -> matcher.matches(t, context));
                context.setActual(theActuals);
                if (!anyMatched) {
                    context.setMismatch("no item");
                }
                context.switchInvers();
                return anyMatched;
            }
        };

    }

    public static <T> Matcher<Stream<T>> containsAll(Matcher<? super T>... matchers) {
        Objects.requireNonNull(matchers, "StreamMatchers.containsAll - matcher is <null>");
        return new AbstractIntermediateMatcher<Stream<T>>("contains all", matchers) {
            class Pair {
                boolean matched;
                Matcher<? super T> matcher;
            }

            @Override
            public boolean doesMatch(Stream<T> actual, ExecutionContext context) {

                List<Pair> toTest = fill(matchers);
                List<T> theActuals = new ArrayList<>();
                int numberOfMatched = 0;
                for (Iterator<T> iterator = actual.iterator(); iterator.hasNext()
                        && numberOfMatched < matchers.length;) {
                    T t = iterator.next();
                    theActuals.add(t);

                    for (Pair current : toTest) {
                        // more than one matcher can match an element
                        if (!current.matched && current.matcher.matches(t, context)) {
                            current.matched = true;
                            numberOfMatched++;
                        }
                    }

                }
                context.setActual(theActuals);
                if (numberOfMatched != matchers.length) {
                    context.setMismatch("does not contain all of");
                    return false;
                }
                return true;
            }

            private List<Pair> fill(Matcher<? super T>... matchers) {
                List<Pair> toTest = new ArrayList<>();
                for (Matcher<? super T> matcher : matchers) {
                    Pair pair = new Pair();
                    pair.matcher = matcher;
                    toTest.add(pair);
                }
                return toTest;
            }
        };
    }

    public static <T> Matcher<Stream<T>> noneMatch(Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "StreamMatchers.noneMatch - matcher is <null>");
        return new AbstractIntermediateMatcher<Stream<T>>("no item", matcher) {

            @Override
            public boolean doesMatch(@NonNull Stream<T> actual, ExecutionContext context) {
                List<T> theActuals = new ArrayList<>();
                @SuppressWarnings("squid:S3864")
                // we use peek to collect the processed elements
                boolean noneMatched = actual.peek(theActuals::add).noneMatch(t -> matcher.matches(t, context));
                context.setActual(theActuals);
                if (!noneMatched) {
                    context.setMismatch("one item");
                }
                return noneMatched;
            }
        };
    }

    public static <T> Matcher<Stream<T>> size(long expectation) {
        return size(ObjectMatchers.equalTo(expectation));
    }

    public static <T> Matcher<Stream<T>> size(Matcher<? super Long> matcher) {
        Objects.requireNonNull(matcher, "StreamMatchers.size - matcher is <null>");
        return new AbstractIntermediateMatcher<Stream<T>>("a stream with size", matcher) {

            @Override
            public boolean doesMatch(@NonNull Stream<T> actual, ExecutionContext context) {
                if (!matcher.matches(actual.count(), context)) {
                    context.setMismatch("a stream with size");
                    return false;
                }
                return true;
            }

        };

    }

    private StreamMatchers() {
        throw new AssertionError("No StreamMatchers instances for you!");
    }
}
