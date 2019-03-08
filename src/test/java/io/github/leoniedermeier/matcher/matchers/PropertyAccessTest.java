package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.TestUtils.assertTestMatcher;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.TestMatcher;
import io.github.leoniedermeier.matcher.matchers.PropertyAccess;

class PropertyAccessTest {

    @Nested
    class Property {
        @Test
        void matches() {
            TestMatcher testMatcher = new TestMatcher(true);
            assertMatcherTrue("1234", PropertyAccess.property(String::length, "string length").is(testMatcher));
            assertTestMatcher(testMatcher, 1, 4);
        }

        @Test
        void not_matches() {
            TestMatcher testMatcher = new TestMatcher(false);
            assertMatcherFalse("1234", PropertyAccess.property(String::length, "string length").is(testMatcher));
            assertTestMatcher(testMatcher, 1, 4);
        }

        @Test
        void not_matches_null() {
            assertMatcherFalse(null,
                    PropertyAccess.property(String::length, "string length").is(new TestMatcher(false)));
        }
    }
}
