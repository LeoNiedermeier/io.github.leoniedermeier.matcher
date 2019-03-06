package io.github.leoniedermeier.matcher.testfailures;

import static io.github.leoniedermeier.matcher.MatcherAssert.assertThat;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.allMatch;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.isNull;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.startsWith;
import static io.github.leoniedermeier.matcher.util.PropertyAccess.property;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CombinationsTest {

    private Person person;

    @BeforeEach
    void beforeEach() {
        this.person = new Person("Name");
        this.person.addPhone(new Phone("0049-1234"));
        this.person.addPhone(new Phone("xx49-ABCD"));
    }

    @Test
    void test_phones_list_null() {
        assertThat(this.person, property(Person::getPhones, "phones list").is(isNull()));
    }

    @Test
    void test_all_phones_start_with() {
        assertThat(this.person, property(Person::getPhones, "phones list")
                .is(allMatch(property(Phone::getNumber, "phone number").is(startsWith("0049")))));
    }
}
