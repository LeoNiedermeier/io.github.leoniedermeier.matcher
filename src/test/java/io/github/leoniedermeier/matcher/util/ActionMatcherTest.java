package io.github.leoniedermeier.matcher.util;

import static io.github.leoniedermeier.matcher.matchers.ComparableMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.MatcherAssert;
import io.github.leoniedermeier.matcher.matchers.ComparableMatchers;

class ActionMatcherTest {

	@Test
	void test() {
		// TODO: eigentlich was mit mockito
		ActionMatcherImpl<String, Integer> matcher = new ActionMatcherImpl<>(String::length,"string length").is(greaterThan(2).and(lessThan(10)));
		MatcherAssert.assertThat("11234", matcher);
	}

}
