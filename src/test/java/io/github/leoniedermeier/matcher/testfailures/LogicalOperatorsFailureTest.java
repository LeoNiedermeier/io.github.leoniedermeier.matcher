package io.github.leoniedermeier.matcher.testfailures;

import static io.github.leoniedermeier.matcher.matchers.LogicalOperators.not;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.endsWith;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.startsWith;

import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.MatcherAssert;

class LogicalOperatorsFailureTest {

    @Test
    void not_failure() {
        MatcherAssert.assertThat("123X", not(endsWith("X")));
    }

    @Test
    void and_failure() {
        MatcherAssert.assertThat("1234", startsWith("1").and(endsWith("X")));
    }

}
