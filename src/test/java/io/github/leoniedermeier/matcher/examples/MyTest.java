package io.github.leoniedermeier.matcher.examples;

import static io.github.leoniedermeier.matcher.MatcherAssert.assertThat;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.allMatch;
import static io.github.leoniedermeier.matcher.matchers.IterableMatchers.size;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.isNotNull;
import static io.github.leoniedermeier.matcher.matchers.StringMatchers.startsWith;
import static io.github.leoniedermeier.matcher.util.PropertyAccess.property;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.Matcher;

class MyTest {

	@Test
	void test() {
		Person person = new Person("Name");
		person.addPhone(new Phone("0049-1234"));
		person.addPhone(new Phone("0X049-ABCD"));

		assertThat(person.getPhones(), size(2));
		//assertThat(person.getPhones(), allMatch(property(Phone::getNumber).is(startsWith("0049"))));

		assertThat(person, property(Person::getPhones,"phones").is(isNotNull()));

		assertThat(person, property(Person::getPhones,"phones").is(allMatch(isNotNull())));

		assertThat(person, allPhoneNumbersStartWith("0049"));
	}
	
	static Matcher<Person> allPhoneNumbersStartWith(String string) {
		return property(Person::getPhones,"phones").is(allMatch(property(Phone::getNumber,"phone number").is(startsWith(string))));
	}
	
	static Matcher<Person> allPhoneNumbersStartWith2(String expectation) {
		return (actual, description) -> {
			description.setExpectation("all phone number starts with <%s>", expectation);
			  Optional<String> findAny = actual.getPhones().stream().map(Phone::getNumber).filter(s -> !s.startsWith(expectation)).findAny();
			  if(findAny.isPresent()) {
				  description.setMismatch("<%s> a phone number starts not with <%s>", findAny.get(), expectation);
			  }
			  return !findAny.isPresent();
		};
	
		
	}
	

}
