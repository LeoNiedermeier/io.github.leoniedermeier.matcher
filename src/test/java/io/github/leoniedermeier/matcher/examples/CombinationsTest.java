package io.github.leoniedermeier.matcher.examples;

import static io.github.leoniedermeier.matcher.MatcherAssert.assertThat;
import static io.github.leoniedermeier.matcher.TestUtils.assertMatcherFalse;
import static io.github.leoniedermeier.matcher.matchers.ComparableMatchers.greaterThan;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.*;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.equalTo;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.length;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.startsWith;
import static java.util.Arrays.asList;

import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.matchers.LogicalOperators;
import io.github.leoniedermeier.matcher.matchers.ObjectMatchers;

class CombinationsTest {

    @Test
    void test() {
        assertThat("1234", LogicalOperators.not(startsWith("123")));
        assertThat("1234", length(greaterThan(3)).and(startsWith("X123")));
    }

    @Test
    void test_2() {
        assertThat(asList("11", "22", "X3","44"), noneMatch(startsWith("X")));
    }
    
    @Test
    void test_3() {
        assertThat(asList("X1", "X2", "X3","YX4"), allMatch(startsWith("X")));
    }

}
