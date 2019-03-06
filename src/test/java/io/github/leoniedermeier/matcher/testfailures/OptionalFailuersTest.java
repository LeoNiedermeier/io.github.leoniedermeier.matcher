package io.github.leoniedermeier.matcher.testfailures;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.MatcherAssert;
import io.github.leoniedermeier.matcher.matchers.OptinalMatchers;
import io.github.leoniedermeier.matcher.matchers.StringMatchers;

class OptionalFailuersTest {

    @Test
    void test1() {
        MatcherAssert.assertThat(Optional.empty(), OptinalMatchers.isPresent());
    }
    
    @Test
    void test() {
        MatcherAssert.assertThat(Optional.of("x"), OptinalMatchers.<String>isPresent().withValue(StringMatchers.startsWith("Z")));
    }

}
