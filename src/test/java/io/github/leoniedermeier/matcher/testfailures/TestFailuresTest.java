package io.github.leoniedermeier.matcher.testfailures;

import static io.github.leoniedermeier.matcher.MatcherAssert.assertThat;
import static io.github.leoniedermeier.matcher.matchers.ArrayMatchers.length;
import static io.github.leoniedermeier.matcher.matchers.ComparableMatchers.greaterOrEqualsThan;
import static io.github.leoniedermeier.matcher.matchers.ComparableMatchers.greaterThan;
import static io.github.leoniedermeier.matcher.matchers.ComparableMatchers.lessOrEqualsThan;
import static io.github.leoniedermeier.matcher.matchers.ComparableMatchers.lessThan;
import static io.github.leoniedermeier.matcher.matchers.ExceptionMatchers.throwsA;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.allMatch;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.anyMatch;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.containsAll;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.noneMatch;
import static io.github.leoniedermeier.matcher.matchers.LogicalOperators.not;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.equalTo;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.isNull;
import static io.github.leoniedermeier.matcher.matchers.OptionalMatchers.isPresent;
import static io.github.leoniedermeier.matcher.matchers.PropertyAccess.property;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.contains;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.endsWith;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.startsWith;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.MatcherAssert;
import io.github.leoniedermeier.matcher.matchers.ArrayMatchers;
import io.github.leoniedermeier.matcher.matchers.ExceptionMatchers;
import io.github.leoniedermeier.matcher.matchers.IterableMatchers;
import io.github.leoniedermeier.matcher.matchers.ObjectMatchers;
import io.github.leoniedermeier.matcher.matchers.OptionalMatchers;
import io.github.leoniedermeier.matcher.matchers.StringMatchers;

@org.junit.jupiter.api.Disabled
class TestFailuresTest {

    @Nested
    @DisplayName("ArrayMatchers")
    class ArrayMatchersTest {

        @Test
        @DisplayName("one dimensinal: is array and arrays are equals")
        void equalTo_mismatch() {
            assertThat(new String[] { "A", "B" }, ArrayMatchers.equalTo(new String[] { "A", "X" }));
        }

        @Test
        @DisplayName("two dimensinal: is array and arrays are equals")
        void equalTo_mismatch_2d() {
            assertThat(new String[][] { { "0-0", "0-1" }, { "1-0", "1-1" } },
                    ArrayMatchers.equalTo(new String[][] { { "0-0", "0-1" }, { "1-0", "1-X" } }));
        }

        @Test
        @DisplayName("is array")
        void isArray_mismatch() {
            assertThat("X", ArrayMatchers.isArray());
        }

        @Test
        @DisplayName("is array with length is equal to <2>")
        void length_mismatch() {
            assertThat(new int[3], length(equalTo(2)));
        }
    }

    @Nested
    class Combinations {

        @Test
        void test_all_phones_start_with() {
            Person person;
            person = new Person("Name");
            person.addPhone(new Phone("0049-1234"));
            person.addPhone(new Phone("xx49-ABCD"));

            assertThat(person, property(Person::getPhones, "phones list")
                    .is(allMatch(property(Phone::getNumber, "phone number").is(startsWith("0049")))));
        }

        @Test
        void test_phones_list_null() {
            Person person;
            person = new Person("Name");
            person.addPhone(new Phone("0049-1234"));
            person.addPhone(new Phone("xx49-ABCD"));
            assertThat(person, property(Person::getPhones, "phones list").is(isNull()));
        }
    }

    @Nested
    @DisplayName("ComparableMatchers")
    class ComparableMatchersTest {
        @Test
        void greaterOrEqualsThan_with_Comparator_failure() {
            assertThat("9", greaterOrEqualsThan("12", Comparator.comparing(String::length)));
        }

        @Test
        void greaterThan_failure() {
            assertThat(Integer.valueOf(1), greaterThan(Integer.valueOf(2)));
        }

        @Test
        void lessOrEqualsThan_with_Comparator_failure() {
            assertThat(2, lessOrEqualsThan(1));
        }

        @Test
        void lessThan_failure() {
            assertThat(Integer.valueOf(2), lessThan(Integer.valueOf(1)));
        }
    }

    @Nested
    @DisplayName("ExceptionMatchers")
    class ExceptionMatchersTest {
        @Test
        void no_exception_thrown() {
            assertThat(() -> {
            }, ExceptionMatchers.throwsA(Exception.class));
        }

        @Test
        void other_exception_thrown() {
            assertThat(() -> {
                throw new IOException();
            }, ExceptionMatchers.throwsA(RuntimeException.class)
                    .with(property(Exception::getCause, "cause").is(ObjectMatchers.isNotNull())));
        }

        @Test
        void proper_exception_thrown_but_mismatch_in_property() {

            assertThat(() -> {
                throw new SQLException("Reason", "SQL-STATE");
            }, throwsA(SQLException.class)
                    .with(property(SQLException::getSQLState, "sql-state").is(equalTo("Expected-SQL-STATE"))));
        }
    }

    @Nested
    @DisplayName("IterableMatchers and StreamMatchers")
    class IterableMatchersTest {

        @Test
        void allMatch_failure() {
            assertThat(asList("X1", "X2", "33", "X4"), allMatch(startsWith("X")));
        }

        @Test
        void anyMatch_failure() {
            assertThat(asList("11", "22", "33", "44"), anyMatch(startsWith("2").and(contains("X"))));
        }

        @Test
        void anyMatch_failure_2() {
            assertThat(asList("11", "22", "33", "44"), anyMatch(startsWith("X")));
        }

        @Test
        void containsAll_failure() {
            assertThat(asList("A1", "22", "B3", "44"),
                    containsAll(startsWith("A").and(endsWith("X")), startsWith("B"), startsWith("A"), startsWith("C")));
        }

        @Test
        void noneMatch_failure() {
            assertThat(asList("11", "22", "X", "44"), noneMatch(equalTo("X")));
        }

        @Test
        void not_containsAll_failure() {
            assertThat(asList("A1", "22", "B3", "44"),
                    not(containsAll(startsWith("4"), startsWith("A").and(endsWith("1")))));
        }

        @Test
        void not_containsAll_failure_2() {
            assertThat(asList("A1", "22", "B3", "44"), not(containsAll(not(startsWith("4")))));
        }

        @Test
        void size_failure() {
            assertThat(asList("A1", "22", "B3", "44"), IterableMatchers.size(2));
        }
    }

    @Nested
    @DisplayName("LogicalOperators")
    class LogicalOperatorsTest {

        @Test
        void and_failure_first() {
            MatcherAssert.assertThat("1234", startsWith("X").and(endsWith("X")));
        }

        @Test
        void and_failure_secound() {
            MatcherAssert.assertThat("1234", startsWith("1").and(endsWith("X")));
        }

        @Test
        void not_failure() {
            MatcherAssert.assertThat("123X", not(endsWith("X")));
        }

    }

    @Nested
    @DisplayName("OptionalMatchers")
    class OptionalMatchersTest {

        @Test
        void isPresent_failure() {
            MatcherAssert.assertThat(Optional.empty(), isPresent());
        }

        @Test
        void isPresent_withValue_failure() {
            MatcherAssert.assertThat(Optional.of("x"),
                    OptionalMatchers.<String>isPresent().withValue(StringMatchers.startsWith("Z")));
        }

    }

    @Nested
    @DisplayName("StringMatchers")
    class StringMatchersTest {
        @Test
        void contains_failure() {
            assertThat("1234", contains("ABC"));
        }

        @Test
        void endsWith_failure() {
            assertThat("1234", endsWith("ABC"));
        }

        @Test
        void length_failure() {
            assertThat("1234", StringMatchers.length(greaterOrEqualsThan(6)));
        }

        @Test
        void startsWith_failure() {
            assertThat("1234", startsWith("ABC"));
        }
    }

}
