package io.github.leoniedermeier.matcher;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ExpectationMessageCreator {

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

    private int level = 0;

    private final List<String> expectationLines = new ArrayList<>();

    private final List<String> mismatchLines = new ArrayList<>();

    private final TreeEntry treeEntry;

    public ExpectationMessageCreator(TreeEntry treeEntry) {

        this.treeEntry = treeEntry;
    }

    public String getMessage() {
        expectationLines.add("Conditions (\u2714: matched, \u2716: not matches, ?: not executed)");
        mismatchLines.add("Mismatch hint:");
        traverse(treeEntry);
        return Stream.concat(expectationLines.stream(), mismatchLines.stream()).filter(s -> !s.trim().isEmpty())
                .collect(joining(lineSeparator()));
    }

    private void addLines(TreeEntry current) {
        Entry expectation = current.getMatcher().getExpectation();
        if (expectation != null) {
            Entry mismatch = null;

            String expectationText = expectation.toString();
            if (current.isMatched()) {
                // \u2713: check mark
                this.expectationLines.add(withSpacer(this.level * 4, "\u2714 " + expectationText));
                mismatch = current.getMatcher().getExpectation();
            } else if (!current.isMatched() && current.isExecuted()) {
                // ballot x
                this.expectationLines.add(withSpacer(2 + this.level * 4 - 2, "\u2716  " + expectationText));
                mismatch = current.getMismatch();

            } else {
                this.expectationLines.add(withSpacer(2 + this.level * 4 - 2, "? " + expectationText));
            }

            if (mismatch != null) {
                String mms = mismatch.toString();
                if (mms != null && !mms.isEmpty()) {
                    this.mismatchLines.add(withSpacer(2 + this.level * 4, mms));

                }
            }
        }
    }

    private void traverse(TreeEntry current) {
        this.level++;
        addLines(current);
        for (TreeEntry child : current.getChilds()) {
            traverse(child);
        }
        this.level--;
    }
}
