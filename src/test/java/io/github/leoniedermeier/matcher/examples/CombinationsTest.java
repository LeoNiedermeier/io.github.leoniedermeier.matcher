package io.github.leoniedermeier.matcher.examples;

import static io.github.leoniedermeier.matcher.MatcherAssert.assertThat;
import static io.github.leoniedermeier.matcher.matchers.ComparableMatchers.greaterThan;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.length;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.startsWith;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.matchers.ExceptionMatchers;
import io.github.leoniedermeier.matcher.matchers.ObjectMatchers;
import io.github.leoniedermeier.matcher.util.PropertyAccess;

class CombinationsTest {

	@Test
	void test() {
		assertThat("1234", length(greaterThan(3)).and(startsWith("123")));
	}

}
