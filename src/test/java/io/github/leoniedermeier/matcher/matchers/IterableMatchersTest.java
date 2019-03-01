package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.*;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.*;
import static java.util.Arrays.asList;

import java.util.Arrays;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.TestMatcher;

class IterableMatchersTest {

	@Nested
	class AllMatch {

		@Test
		void matches() {
			TestMatcher notEqualsX = new TestMatcher(x -> !"X".equals(x));
			assertMatcherTrue(Arrays.asList("1", "2", "3"), allMatch(notEqualsX));
			assertTestMatcher(notEqualsX, 3, asList("1", "2","3"));
		}

		@Test
		void not_matches() {

			TestMatcher notEqualsX = new TestMatcher(x -> !"X".equals(x));
			assertMatcherFalse(Arrays.asList("1", "X", "3"), allMatch(notEqualsX));
			assertTestMatcher(notEqualsX, 2, asList("1", "X"));
		}
	}

	@Nested
	class AnyMatch {

		@Test
		void matches() {
			TestMatcher equalsX = new TestMatcher("X"::equals);
			assertMatcherTrue(asList("1", "X", "3"), anyMatch(equalsX));
			assertTestMatcher(equalsX, 2, asList("1", "X"));
		}

		@Test
		void not_matches() {
			TestMatcher equalsX = new TestMatcher("X"::equals);
			assertMatcherFalse(asList("1", "2", "3"), anyMatch(equalsX));
			assertTestMatcher(equalsX, 3, asList("1", "2", "3"));
		}
	}

	@Nested
	class NoneMatch {

		@Test
		void matches() {
			TestMatcher equalsX = new TestMatcher("X"::equals);
			assertMatcherTrue(asList("1", "2", "3"), noneMatch(equalsX));
			assertTestMatcher(equalsX, 3, asList("1", "2","3"));
		}

		@Test
		void not_matches() {
			TestMatcher equalsX = new TestMatcher("X"::equals);
			assertMatcherFalse(asList("1", "X", "3"), noneMatch(equalsX));
			assertTestMatcher(equalsX, 2, asList("1", "X"));
		}
	}

}
