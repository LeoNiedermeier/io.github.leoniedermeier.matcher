package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.*;
import static io.github.leoniedermeier.matcher.matchers.LogicMatchers.and;
import static io.github.leoniedermeier.matcher.matchers.LogicMatchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.TestMatcher;
import io.github.leoniedermeier.matcher.TestUtils;

class LogicMatchersTest {

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
