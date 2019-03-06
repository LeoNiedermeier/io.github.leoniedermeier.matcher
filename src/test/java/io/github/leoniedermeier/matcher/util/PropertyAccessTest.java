package io.github.leoniedermeier.matcher.util;

import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.TestUtils;
import io.github.leoniedermeier.matcher.matchers.ObjectMatchers;

class PropertyAccessTest {

    @Test
    void test() {
        TestUtils.assertMatcherFalse("1234",
                PropertyAccess.property(String::length, "string length").is(ObjectMatchers.equalTo(3)));
    }

}
