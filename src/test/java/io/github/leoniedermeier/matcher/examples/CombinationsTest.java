package io.github.leoniedermeier.matcher.examples;

import static io.github.leoniedermeier.matcher.MatcherAssert.assertThat;
import static io.github.leoniedermeier.matcher.matchers.ComparableMatchers.greaterThan;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.length;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.startsWith;

import org.junit.jupiter.api.Test;

class CombinationsTest {

    @Test
    void test() {
        assertThat("1234", length(greaterThan(3)).and(startsWith("123")));
    }

}
