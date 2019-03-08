package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.equalTo;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.isInstanceOf;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.isNotNull;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.isNull;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.isSameInstance;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ObjectMatchersTest {

    @Nested
    class EqualTo {
        @Test
        void matches() {
            assertMatcherTrue("123", equalTo("123"));
        }

        @Test
        void matches_null() {
            assertMatcherTrue(null, equalTo(null));
        }

        @Test
        void not_matches() {
            assertMatcherFalse("XXX", equalTo("123"));
        }
    }

    @Nested
    class IsInstanceOf {
        @Test
        void matches() {
            assertMatcherTrue("123", isInstanceOf(String.class));
        }

        @Test
        void not_matches() {
            assertMatcherFalse("XXX", isInstanceOf(Number.class));
        }

        @Test
        void not_matches_null() {
            assertMatcherFalse(null, isInstanceOf(String.class));
        }
    }

    @Nested
    class IsNotNull {
        @Test
        void matches() {
            assertMatcherTrue("1", isNotNull());
        }

        @Test
        void not_matches() {
            assertMatcherFalse(null, isNotNull());
        }
    }

    @Nested
    class IsNull {
        @Test
        void matches() {
            assertMatcherTrue(null, isNull());
        }

        @Test
        void not_matches() {
            assertMatcherFalse("1", isNull());
        }
    }

    @Nested
    class IsSameInstance {
        @Test
        void matches() {
            assertMatcherTrue("123", isSameInstance("123"));
        }

        @Test
        void not_matches() {
            assertMatcherFalse("XXX", isSameInstance("123"));
        }

        @Test
        void not_matches_null() {
            assertMatcherFalse(null, isSameInstance("123"));
        }
    }
}
