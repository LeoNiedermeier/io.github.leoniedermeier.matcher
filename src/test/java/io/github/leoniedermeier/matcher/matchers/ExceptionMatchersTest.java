package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.TestUtils.assertTestMatcher;
import static io.github.leoniedermeier.matcher.matchers.ExceptionMatchers.throwsA;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.TestMatcher;
import io.github.leoniedermeier.matcher.TestUtils;
import io.github.leoniedermeier.matcher.matchers.ExceptionMatchers.Executable;

class ExceptionMatchersTest {
    @Nested
    class ThrowsA {

        @Test
        void matches() {
            assertMatcherTrue(doThrow(new SQLDataException()), throwsA(SQLException.class));
        }

        @Test
        void no_matches() {
            assertMatcherFalse(doThrow(new IOException()), throwsA(SQLException.class));

        }

        @Test
        void no_matches_no_exception() {
            assertMatcherFalse(() -> {
            }, ExceptionMatchers.throwsA(Exception.class));
        }
    }

    @Nested
    class ThrowsAWith {
        @Test
        void matches() {
            TestMatcher testMatcher = new TestMatcher(true);
            SQLException exception = new SQLException();
            assertMatcherTrue(doThrow(exception), throwsA(SQLException.class).with(testMatcher));
            assertTestMatcher(testMatcher, 1, exception);
        }

        @Test
        void not_matches() {
            TestMatcher testMatcher = new TestMatcher(false);
            SQLException exception = new SQLException();
            assertMatcherFalse(doThrow(exception), throwsA(SQLException.class).with(testMatcher));
            TestUtils.assertTestMatcher(testMatcher, 1, exception);
        }
    }

    private static Executable doThrow(Exception e) {
        return () -> {
            throw e;
        };
    }
}
