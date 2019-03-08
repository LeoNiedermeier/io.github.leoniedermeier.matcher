package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.matchers.ComparableMatchers.greaterOrEqualsThan;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

import java.util.Comparator;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

// TODO: all tests,
class ComparableMatchersTest {

    @Nested
    class GreaterOrEqualsThan {
        @Test
        void matches_equals() {
            assertMatcherTrue(ONE, greaterOrEqualsThan(ONE));
        }

        @Test
        void matches_greater() {
            assertMatcherTrue(ONE, greaterOrEqualsThan(ZERO));
        }

        @Test
        void not_matches_less() {
            assertMatcherFalse(ZERO, greaterOrEqualsThan(ONE));
        }

    }

    @Nested
    class GreaterOrEqualsThanWithComparator {
        @Test
        void matches_equals() {
            assertMatcherTrue("12", greaterOrEqualsThan("AB", Comparator.comparing(String::length)));
        }

        @Test
        void matches_greater() {
            assertMatcherTrue("1234", greaterOrEqualsThan("ABC", Comparator.comparing(String::length)));
        }

        @Test
        void not_matches_less() {
            assertMatcherFalse("12", greaterOrEqualsThan("ABC", Comparator.comparing(String::length)));
        }

    }

}
