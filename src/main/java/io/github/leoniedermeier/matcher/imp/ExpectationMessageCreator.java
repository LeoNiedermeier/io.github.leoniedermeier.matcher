package io.github.leoniedermeier.matcher.imp;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.opentest4j.AssertionFailedError;
import org.opentest4j.MultipleFailuresError;

public class ExpectationMessageCreator {

    private static int level(BaseMatcherState baseMatcherState) {
        int level = 0;
        while (baseMatcherState != null) {
            level++;
            baseMatcherState = baseMatcherState.getParent();
        }
        return level;
    }

    private static String withSpacer(final int spaces, Object object) {
        if (object == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spaces; i++) {
            sb.append(' ');
        }
        sb.append(object);
        return sb.toString();
    }

    private final List<String> expectationLines = new ArrayList<>();

    private final List<Message> mismatchEntries = new ArrayList<>();

    private final List<String> mismatchLines = new ArrayList<>();

    private final BaseMatcherState state;

    private final Object lastActual;

    public ExpectationMessageCreator(BaseMatcherState state, Object lastActual) {
        this.state = state;
        this.lastActual = lastActual;
    }

    public void createAndThrowMultipleFailuresError(String reason) {
        throw new MultipleFailuresError(createMessage(reason), createAssertionFailedErrors());
    }

    public String getMessage() {
        expectationLines.add("Conditions (\u2714: matched, \u2716: not matches, ?: not executed)");
        mismatchLines.add("Mismatch hint:");
        state.acceptRecursice(this::addLines);
        return Stream.concat(expectationLines.stream(), mismatchLines.stream()).filter(s -> !s.trim().isEmpty())
                .collect(joining(lineSeparator()));
    }

    private void addLines(BaseMatcherState current) {
        Message expectation = current.getMatcher().getExpectation();
        if (expectation == null) {
            return;
        }

        Message mismatch = null;
        String prefix = "?  ";
        if (current.isExecuted()) {
            if (current.isMatched()) {
                // \u2713: check mark
                prefix = "\u2714 ";
                mismatch = current.getMatcher().getExpectation();
            } else {
                // ballot x
                prefix = "\u2716  ";
                mismatch = current.getMismatch();
                mismatchEntries.add(mismatch);
            }
        }
        int level = level(current);
        expectationLines.add(withSpacer(level * 4, prefix + expectation));
        if (mismatch != null) {
            String mms = mismatch.toString();
            if (mms != null && !mms.isEmpty()) {
                mismatchLines.add(withSpacer(2 + level * 4, mms));
            }
        }
    }

    private List<AssertionFailedError> createAssertionFailedErrors() {
        return mismatchEntries.stream()//
                // for the delta only entries with data are relevant (null entries do not
                // provide useful information)
                .filter(MismatchMessage.class::isInstance).map(MismatchMessage.class::cast)
                .filter(e -> e.getActual() != null || e.getExpected() != null)
                .map(msg -> new AssertionFailedError(msg.toString(), msg.getExpected(), msg.getActual()))
                .collect(toList());
    }

    private String createMessage(String reason) {
        // Eclipse Junit 5 viewer removes leading and trailing whitespace. Therefore
        // use a no-break space
        String message = (reason != null ? reason : "\u00A0") //
                + System.lineSeparator() //
                + getMessage();
        if (lastActual != null) {
            message += System.lineSeparator() //
                    + ("Actual:")//
                    + System.lineSeparator() //
                    + " " + lastActual//
                    + System.lineSeparator() //
                    + "=========" //
                    + System.lineSeparator() + "\u00A0";//
        }
        return message;
    }
}
