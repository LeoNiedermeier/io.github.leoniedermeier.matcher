package io.github.leoniedermeier.matcher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MatcherAssertTest {

    @Test
    void assertThat_fails_simple() {
        assertThrows(AssertionError.class, () -> MatcherAssert.assertThat("Some Error", false));
    }

    @Test
    void assertThat_ok() {
        MatcherAssert.assertThat("Some Error", true);
    }

    @Test
    void assertThat_fails_with_matcher() {
        assertThrows(AssertionError.class, () -> MatcherAssert.assertThat("Some Error", new TestMatcher(false)));
    }
}
