package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.TestUtils.assertTestMatcher;
import static io.github.leoniedermeier.matcher.TestUtils.testNullNotMatches;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.allMatch;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.anyMatch;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.noneMatch;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.size;
import static java.util.Arrays.asList;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.TestMatcher;

class IterableMatchersTest {

    @Nested
    class AllMatch {

        @Test
        void matches() {
            TestMatcher notEqualsX = new TestMatcher(x -> !"X".equals(x));
            assertMatcherTrue(Arrays.asList("1", "2", "3"), allMatch(notEqualsX));
            assertTestMatcher(notEqualsX, 3, asList("1", "2", "3"));
        }

        @Test
        void not_matches() {

            TestMatcher notEqualsX = new TestMatcher(x -> !"X".equals(x));
            assertMatcherFalse(Arrays.asList("1", "X", "3"), allMatch(notEqualsX));
            assertTestMatcher(notEqualsX, 2, asList("1", "X"));
        }

        @Test
        void not_matches_null() {
            testNullNotMatches(IterableMatchers::allMatch);
        }
    }

    @Nested
    class AnyMatch {

        @Test
        void matches() {
            TestMatcher equalsX = new TestMatcher("X"::equals);
            assertMatcherTrue(asList("1", "X", "3"), anyMatch(equalsX));
            assertTestMatcher(equalsX, 2, asList("1", "X"));
        }

        @Test
        void not_matches() {
            TestMatcher equalsX = new TestMatcher("X"::equals);
            assertMatcherFalse(asList("1", "2", "3"), anyMatch(equalsX));
            assertTestMatcher(equalsX, 3, asList("1", "2", "3"));
        }

        @Test
        void not_matches_null() {
            testNullNotMatches(IterableMatchers::anyMatch);
        }
    }

    @Nested
    class NoneMatch {

        @Test
        void matches() {
            TestMatcher equalsX = new TestMatcher("X"::equals);
            assertMatcherTrue(asList("1", "2", "3"), noneMatch(equalsX));
            assertTestMatcher(equalsX, 3, asList("1", "2", "3"));
        }

        @Test
        void not_matches() {
            TestMatcher equalsX = new TestMatcher("X"::equals);
            assertMatcherFalse(asList("1", "X", "3"), noneMatch(equalsX));
            assertTestMatcher(equalsX, 2, asList("1", "X"));
        }

        @Test
        void not_matches_null() {
            testNullNotMatches(IterableMatchers::noneMatch);
        }
    }

    @Nested
    class NullActual {

        @Test
        void matches() {
            TestMatcher testMatcher = new TestMatcher(true);
            assertMatcherFalse(null, allMatch(testMatcher));
            assertTestMatcher(testMatcher, 0, null);
        }
    }

    @Nested
    class Size {
        @Test
        void matches_collection() {
            // path is Iterable but not a Collection
            assertMatcherTrue(asList("1", "2"), size(2));
        }

        @Test
        void matches_iterable() {
            // path is Iterable but not a Collection
            assertMatcherTrue(Paths.get("a", "b"), size(2));
        }

        @Test
        void not_matches() {
            // path is Iterable but not a Collection
            assertMatcherFalse(asList("1", "2"), size(3));
        }

        @Test
        void not_matches_null() {
            assertMatcherFalse(null, size(0));
        }
    }
}
