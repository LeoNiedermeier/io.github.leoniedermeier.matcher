package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.TestUtils.assertTestMatcher;
import static io.github.leoniedermeier.matcher.matchers.ArrayMatchers.equalTo;
import static io.github.leoniedermeier.matcher.matchers.ArrayMatchers.length;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.TestMatcher;

class ArraysMatcherTest {

    /**
     * See also tests for
     * {@link ArrayMatchersUtils#deepEquals0(Object, Object, java.util.Deque)} in
     * {@link ArrayMatchersUtilsTest}
     */
    @Nested
    class EuqalTo {

        @Test
        void matches() {
            assertMatcherTrue(new String[] { "1", "AB" }, equalTo(new String[] { "1", "AB" }));
        }

        @Test
        void not_matches() {
            assertMatcherFalse(new String[] { "1", "AB" }, equalTo(new String[] { "1", "XY" }));
        }
    }

    @Nested
    class IsArray {
        @Test
        void matches() {
            assertMatcherTrue(new String[0], ArrayMatchers.isArray());
            assertMatcherTrue(new int[0], ArrayMatchers.isArray());
        }

        @Test
        void no_matches() {
            assertMatcherFalse("1", ArrayMatchers.isArray());
            assertMatcherFalse(1, ArrayMatchers.isArray());
        }
    }

    @Nested
    class Length {

        @Test
        void matches_object_array() {
            TestMatcher testMatcher = new TestMatcher(true);
            assertMatcherTrue(new String[2], length(testMatcher));
            assertTestMatcher(testMatcher, 1, 2);
        }

        @Test
        void matches_primitive_array() {
            TestMatcher testMatcher = new TestMatcher(true);
            assertMatcherTrue(new int[5], length(testMatcher));
            assertTestMatcher(testMatcher, 1, 5);
        }

        @Test
        void not_matches_no_array() {
            TestMatcher testMatcher = new TestMatcher(false);
            assertMatcherFalse("", length(testMatcher));
            assertTestMatcher(testMatcher, 0, null);
        }

        @Test
        void not_matches_object_array() {
            TestMatcher testMatcher = new TestMatcher(false);
            assertMatcherFalse(new String[2], length(testMatcher));
            assertTestMatcher(testMatcher, 1, 2);
        }

        @Test
        void not_matches_primitive_array() {
            TestMatcher testMatcher = new TestMatcher(false);
            assertMatcherFalse(new int[5], length(testMatcher));
            assertTestMatcher(testMatcher, 1, 5);
        }
    }
}
