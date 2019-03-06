package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.TestUtils.assertTestMatcher;
import static io.github.leoniedermeier.matcher.matchers.LogicalOperators.and;
import static io.github.leoniedermeier.matcher.matchers.LogicalOperators.not;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.TestMatcher;

class LogicalOperatorsTest {

    @Nested
    class And {

        @Test
        void matches() {
            TestMatcher testMatcher_1 = new TestMatcher(true);
            TestMatcher testMatcher_2 = new TestMatcher(true);
            assertMatcherTrue("123", and(testMatcher_1, testMatcher_2));
            assertTestMatcher(testMatcher_1, 1, "123");
            assertTestMatcher(testMatcher_2, 1, "123");
        }

        @Test
        void not_matches() {
            TestMatcher testMatcher_1 = new TestMatcher(false);
            TestMatcher testMatcher_2 = new TestMatcher(true);
            assertMatcherFalse("123", and(testMatcher_1, testMatcher_2));
            assertTestMatcher(testMatcher_1, 1, "123");
        }
    }

    @Nested
    class Not {

        @Test
        void matches() {
            TestMatcher testMatcher = new TestMatcher(false);
            assertMatcherTrue("123", not(testMatcher));
            assertTestMatcher(testMatcher, 1, "123");
        }

        @Test
        void not_matches() {
            TestMatcher testMatcher = new TestMatcher(true);
            assertMatcherFalse("123", not(testMatcher));
            assertTestMatcher(testMatcher, 1, "123");
        }
    }
}
