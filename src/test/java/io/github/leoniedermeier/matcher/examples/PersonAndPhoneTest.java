package io.github.leoniedermeier.matcher.examples;

import static io.github.leoniedermeier.matcher.MatcherAssert.assertThat;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.allMatch;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.size;
import static io.github.leoniedermeier.matcher.matchers.PropertyAccess.property;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.startsWith;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.Matcher;
import io.github.leoniedermeier.matcher.imp.ExecutionContext;
import io.github.leoniedermeier.matcher.matchers.AbstractTerminalMatcher;

class PersonAndPhoneTest {

    /**
     * Reusable Matcher for person's phone number
     */
    static Matcher<Person> allPhoneNumbersStartWith(String string) {
        return property(Person::getPhones, "person phone numbers")
                .is(allMatch(property(Phone::getNumber, "phone number").is(startsWith(string))));
    }

    static Matcher<Person> handCodedAllPhoneNumbersStartWith(String expectation) {
        return new AbstractTerminalMatcher<Person>("all phone number starts with <%s>", expectation) {

            @Override
            protected void doesMatch(ExecutionContext executionContext, Person actual) {
                Optional<String> findAny = actual.getPhones().stream().map(Phone::getNumber)
                        .filter(s -> !s.startsWith(expectation)).findAny();
                if (findAny.isPresent()) {
                    executionContext.mismatch("<%s> a phone number starts not with <%s>", findAny.get(), expectation);
                }
            }
        };
    }

    @Test
    void person_has_no_phones() {
        Person person = new Person("Name");

        assertThat(person.getPhones(), size(0));
        assertThat(person, property(Person::getPhones, "person phones").is(size(0)));

    }

    /**
     * <h1>Failure example</h1>
     *
     * <pre>
     * {@code
     *  Expected:
     *       person phone numbers
     *           every item
     *                  phone number
     *                       a string starts with <0049>
     *  Mismatches:
     *      person phone numbers
     *          one item
     *                  phone number
     *                      <X0049-ABCD> which not starts with <0049>
     * }
     * </pre>
     */
    @Test
    void person_has_phones_with_number_starts_with_0049() {
        Person person = new Person("Name");
        person.addPhone(new Phone("0049-1234"));
        person.addPhone(new Phone("0049-ABCD"));
        assertThat(person, allPhoneNumbersStartWith("0049"));

    }
}
