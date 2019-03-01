package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherTrue;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.*;

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
		void not_matches() {
			assertMatcherFalse("XXX", equalTo("123"));
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
		void not_matches2() {
			assertMatcherFalse("123", LogicMatchers.not(isSameInstance("123")));
		}
	}
}
