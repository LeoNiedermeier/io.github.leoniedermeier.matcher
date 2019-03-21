package io.github.leoniedermeier.matcher.matchers;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;

public final class StreamMatchers {

    abstract static class AbstractStreamMatcher<T> extends AbstractLoopMatcher<Stream<T>> {

        public AbstractStreamMatcher(String text, Matcher<?>... childs) {
            super(text, childs);
        }

        @Override
        protected void doesMatch(ExecutionContext executionContext, @NonNull Stream<T> actual) {
            List<T> theActuals = new ArrayList<>();
            doesMatch2(executionContext, actual.peek(theActuals::add));
            executionContext.setLastActual(theActuals);
        }

        protected abstract void doesMatch2(ExecutionContext executionContext, Stream<T> actual);
    }

    public static <T> Matcher<Stream<T>> allMatch(Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "StreamMatchers.allMatch - matcher is <null>");
        return new AbstractStreamMatcher<T>("every item", matcher) {

            @Override
            protected void doesMatch2(ExecutionContext executionContext, Stream<T> actual) {
                Optional<T> any = actual.filter(t -> !matcher.matches(executionContext, t)).findAny();
                if (any.isPresent()) {
                    executionContext.mismatch("one item");
                }
            }
        };
    }

    public static <T> Matcher<Stream<T>> anyMatch(Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "StreamMatchers.anyMatch - matcher is <null>");
        return new AbstractStreamMatcher<T>("at least one item", matcher) {

            @Override
            protected void doesMatch2(ExecutionContext executionContext, Stream<T> actual) {
                boolean anyMatched = actual.anyMatch(t -> matcher.matches(executionContext, t));
                if (!anyMatched) {
                    executionContext.mismatch("no item");
                }
            }
        };

    }

    public static <T> Matcher<Stream<T>> containsAll(Matcher<? super T>... matchers) {
        Objects.requireNonNull(matchers, "StreamMatchers.containsAll - matcher is <null>");
        return new AbstractStreamMatcher<T>("contains all", matchers) {
            class Pair {
                boolean matched;
                final Matcher<? super T> matcher;

                public Pair(Matcher<? super T> matcher) {
                    this.matcher = matcher;
                }
            }

            @Override
            protected void doesMatch2(ExecutionContext executionContext, Stream<T> actual) {

                List<Pair> toTest = Stream.of(matchers).map(Pair::new).collect(toList());
                int numberOfMatched = 0;
                for (Iterator<T> iterator = actual.iterator(); iterator.hasNext()
                        && numberOfMatched < matchers.length;) {
                    T t = iterator.next();
                    for (Pair current : toTest) {
                        // more than one matcher can match an element
                        if (!current.matched && current.matcher.matches(executionContext, t)) {
                            current.matched = true;
                            numberOfMatched++;
                        }
                    }
                }
                if (numberOfMatched != matchers.length) {
                    executionContext.mismatch("does not contain all of");
                }
            }
        };
    }

    public static <T> Matcher<Stream<T>> noneMatch(Matcher<? super T> matcher) {
        Objects.requireNonNull(matcher, "StreamMatchers.noneMatch - matcher is <null>");
        return new AbstractStreamMatcher<T>("no item", matcher) {

            @Override
            protected void doesMatch2(ExecutionContext executionContext, Stream<T> actual) {
                boolean noneMatched = actual.noneMatch(t -> matcher.matches(executionContext, t));
                if (!noneMatched) {
                    executionContext.mismatch("one item");
                }
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
            protected void doesMatch(ExecutionContext executionContext, @NonNull Stream<T> actual) {
                if (!matcher.matches(executionContext, actual.count())) {
                    executionContext.mismatch("a stream with size");
                }
            }
        };
    }

    private StreamMatchers() {
        throw new AssertionError("No StreamMatchers instances for you!");
    }
}
