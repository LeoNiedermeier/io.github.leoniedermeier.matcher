package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.TestUtils.assertTestMatcher;
import static io.github.leoniedermeier.matcher.matchers.OptinalMatchers.isEmpty;
import static io.github.leoniedermeier.matcher.matchers.OptinalMatchers.isPresent;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.TestMatcher;

class OptinalMatchersTest {

    @Nested
    class IsPresent {
        @Test
        void matches() {
            assertMatcherTrue(Optional.of("X"), isPresent());
        }

        @Test
        void not_matches() {
            assertMatcherFalse(Optional.empty(), isPresent());
        }

        @Test
        void not_matches_null() {
            assertMatcherFalse(null, isPresent());
        }
    }

    @Nested
    class IsPresentWithValue {
        @Test
        void matches() {
            TestMatcher testMatcher = new TestMatcher(true);
            assertMatcherTrue(Optional.of("X"), isPresent().withValue(testMatcher));
            assertTestMatcher(testMatcher, 1, "X");
        }

        @Test
        void not_matches() {
            TestMatcher testMatcher = new TestMatcher(false);
            assertMatcherFalse(Optional.of("X"), isPresent().withValue(testMatcher));
            assertTestMatcher(testMatcher, 1, "X");
        }

    }

    @Nested
    class IsEmpty {
        @Test
        void matches() {
            assertMatcherTrue(Optional.empty(), isEmpty());
        }

        @Test
        void not_matches() {
            assertMatcherFalse(Optional.of("X"), isEmpty());
        }

        @Test
        void not_matches_null() {
            assertMatcherFalse(null, isEmpty());
        }
    }

}
