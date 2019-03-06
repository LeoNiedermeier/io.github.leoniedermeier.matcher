package io.github.leoniedermeier.matcher.testfailures;

import static io.github.leoniedermeier.matcher.MatcherAssert.assertThat;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.allMatch;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.anyMatch;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.noneMatch;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.contains;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.startsWith;
import static java.util.Arrays.asList;

import org.junit.jupiter.api.Test;

class StreamFailuresTest {

    @Test
    void noneMatch_failure() {
        assertThat(asList("11", "22", "X3", "44"), noneMatch(startsWith("X")));
    }

    @Test
    void anyMatch_failure() {
        assertThat(asList("11", "22", "33", "44"), anyMatch(contains("X")));
    }

    @Test
    void allMatch_failure() {
        assertThat(asList("X1", "X2", "33", "X4"), allMatch(startsWith("X")));
    }

}
