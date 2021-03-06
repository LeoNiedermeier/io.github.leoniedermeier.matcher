package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.endsWith;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.length;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StringMatchersTest {

    @Nested
    class EndsWith {

        @Test
        void matches() {
            assertMatcherTrue("123_xyz", endsWith("xyz"));
        }

        @Test
        void not_matches() {
            assertMatcherFalse("123_XXX", endsWith("xyz"));
        }

        @Test
        void not_matches_null() {
            assertMatcherFalse(null, endsWith("xyz"));
        }
    }

    @Nested
    class Length {

        @Test
        void matches() {
            assertMatcherTrue("123", length(3));
        }

        @Test
        void not_matches() {
            assertMatcherFalse("123", length(4));
        }

        @Test
        void not_matches_null() {
            assertMatcherFalse(null, length(0));
        }
    }

    @Nested
    class StartsWith {

        @Test
        void matches() {
            assertMatcherTrue("xyz_123", startsWith("xyz"));
        }

        @Test
        void not_matches() {
            assertMatcherFalse("XXX_123", startsWith("xyz"));
        }

        @Test
        void not_matches_null() {
            assertMatcherFalse(null, startsWith("xyz"));
        }
    }
    
    @Nested
    class Contains {

        @Test
        void matches() {
            assertMatcherTrue("1xyz2", contains("xyz"));
        }

        @Test
        void not_matches() {
            assertMatcherFalse("1xyZ", startsWith("xyz"));
        }

        @Test
        void not_matches_null() {
            assertMatcherFalse(null, startsWith("xyz"));
        }
    }
}
