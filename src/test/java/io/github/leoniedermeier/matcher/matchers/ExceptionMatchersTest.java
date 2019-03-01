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
import io.github.leoniedermeier.matcher.matchers.ExceptionMatchers.Executable;

class ExceptionMatchersTest {

	@Nested
	class ThrowsA {

		Executable doThrow(Exception e) {
			return () -> {
				throw e;
			};
		}

		@Test
		void matches() {
			assertMatcherTrue(doThrow(new SQLDataException()), throwsA(SQLException.class));
		}

		@Test
		void matches_with() {
			TestMatcher testMatcher = new TestMatcher(x -> true);
			SQLException exception = new SQLException();
			assertMatcherTrue(doThrow(exception), throwsA(SQLException.class).with(testMatcher));
			assertTestMatcher(testMatcher, 1, exception);
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
}
