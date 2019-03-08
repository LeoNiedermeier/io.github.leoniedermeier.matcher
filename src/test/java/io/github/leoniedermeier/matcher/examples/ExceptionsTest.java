package io.github.leoniedermeier.matcher.examples;

import static io.github.leoniedermeier.matcher.MatcherAssert.assertThat;
import static io.github.leoniedermeier.matcher.matchers.ExceptionMatchers.throwsA;
import static io.github.leoniedermeier.matcher.matchers.ObjectMatchers.equalTo;
import static io.github.leoniedermeier.matcher.matchers.PropertyAccess.property;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.Matcher;

class ExceptionsTest {

    private static Matcher<SQLException> sqlState(String expected) {
        return property(SQLException::getSQLState, "sql-state").is(equalTo(expected));
    }

    private void throwsException() throws Exception {
        throw new SQLException("Reason", "SQL-STATE");
    }

    @Test
    void check_SQLException_and_state() {
        assertThat(() -> throwsException(), throwsA(SQLException.class)
                .with(property(SQLException::getSQLState, "sql-state").is(equalTo("SQL-STATE"))));
    }

    @Test
    void check_SQLException_and_state_with_custom_matcher() {
        assertThat(() -> throwsException(), throwsA(SQLException.class).with(sqlState("SQL-STATE")));
    }

}
