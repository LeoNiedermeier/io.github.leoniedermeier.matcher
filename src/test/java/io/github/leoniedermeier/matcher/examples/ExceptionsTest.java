package io.github.leoniedermeier.matcher.examples;

import static io.github.leoniedermeier.matcher.MatcherAssert.assertThat;
import static io.github.leoniedermeier.matcher.matchers.ExceptionMatchers.throwsA;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.equalTo;
import static io.github.leoniedermeier.matcher.util.PropertyAccess.property;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class ExceptionsTest {

	@Test
	void check_SQLException_and_state() {

		assertThat(() -> {
			throw new SQLException("Reason", "SQL-STATE");
		}, throwsA(SQLException.class).with(property(SQLException::getSQLState, "sql-state").is(equalTo("SQL-STATE"))));
	}

}
