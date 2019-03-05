package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.TestUtils.assertTestMatcher;
import static io.github.leoniedermeier.matcher.TestUtils.testActualNull;
import static io.github.leoniedermeier.matcher.matchers.StreamMatchers.allMatch;
import static io.github.leoniedermeier.matcher.matchers.StreamMatchers.anyMatch;
import static io.github.leoniedermeier.matcher.matchers.StreamMatchers.noneMatch;
import static io.github.leoniedermeier.matcher.matchers.StreamMatchers.size;
import static java.util.Arrays.asList;

import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.TestMatcher;

class StreamMatchersTest {

    @Nested
    class AllMatch {

        @Test
        void match() {
            TestMatcher alwaysMatch = new TestMatcher(true);
            assertMatcherTrue(Stream.of("1", "2", "3"), allMatch(alwaysMatch));
            assertTestMatcher(alwaysMatch, 3, asList("1", "2", "3"));
        }

        @Test
        void no_match() {
            TestMatcher equalsX = new TestMatcher("X"::equals);
            assertMatcherFalse(Stream.of("X", "X", "3", "X"), allMatch(equalsX));
            assertTestMatcher(equalsX, 3, asList("X", "X", "3"));
        }

        @Test
        void not_matches_null() {
            testActualNull(StreamMatchers::allMatch);
        }
    }

    @Nested
    class AnyMatch {

        @Test
        void match() {
            TestMatcher equalsX = new TestMatcher("X"::equals);
            assertMatcherTrue(Stream.of("1", "X", "3"), anyMatch(equalsX));
            assertTestMatcher(equalsX, 2, asList("1", "X"));
        }

        @Test
        void no_match() {
            TestMatcher equalsX = new TestMatcher("X"::equals);
            assertMatcherFalse(Stream.of("1", "2", "3"), anyMatch(equalsX));
            assertTestMatcher(equalsX, 3, asList("1", "2", "3"));
        }

        @Test
        void not_matches_null() {
            testActualNull(StreamMatchers::anyMatch);
        }
    }

    @Nested
    class NoneMatch {

        @Test
        void match() {
            TestMatcher equalsX = new TestMatcher("X"::equals);
            assertMatcherTrue(Stream.of("1", "2", "3"), noneMatch(equalsX));
            assertTestMatcher(equalsX, 3, asList("1", "2", "3"));
        }

        @Test
        void no_match() {
            TestMatcher equalsX = new TestMatcher("X"::equals);
            assertMatcherFalse(Stream.of("1", "X", "3"), noneMatch(equalsX));
            assertTestMatcher(equalsX, 2, asList("1", "X"));
        }

        @Test
        void not_matches_null() {
            testActualNull(StreamMatchers::noneMatch);
        }
    }

    @Nested
    class Size {
        @Test
        void matches() {
            assertMatcherTrue(Stream.of("1", "2", "3"), size(3));
        }

        @Test
        void no_matches() {
            assertMatcherFalse(Stream.of("1", "2", "3"), size(4));
        }

        @Test
        void no_matches_null() {
            assertMatcherFalse(null, size(0));
        }
    }

    @Nested
    class SizeWitMatcher {
        @Test
        void matches() {
            TestMatcher testMatcher = new TestMatcher(true);
            assertMatcherTrue(Stream.of("1", "2", "3"), size(testMatcher));
            assertTestMatcher(testMatcher, 1, 3L);
        }

        @Test
        void no_matches() {
            TestMatcher testMatcher = new TestMatcher(false);
            assertMatcherFalse(Stream.of("1", "2", "3"), size(testMatcher));
            assertTestMatcher(testMatcher, 1, 3L);
        }
    }

}
