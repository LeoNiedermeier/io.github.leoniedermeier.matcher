package io.github.leoniedermeier.matcher.internal;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import io.github.leoniedermeier.matcher.ExecutionContext;

public class DefaultSimpleExecutionContextMessageCreator implements MessageCreator  {

    private static final int INDENT = 4;

    private static final class DistinctFromPreceeding implements Predicate<String> {

        public DistinctFromPreceeding() {
            super();
        }

        private String last;

        @Override
        public boolean test(String t) {
            boolean equals = Objects.equals(this.last, t);
            if (!equals) {
                this.last = t;
                return true;
            }
            return false;
        }
    }

    private static void addExpectations(ExecutionContext context, List<String> lines, int spaces) {
        lines.add(withSpacer(spaces, context.getExpectation()));

        for (ExecutionContext downstreamContext : context.getChilds()) {
            addExpectations(downstreamContext, lines, spaces + INDENT);
        }
    }

    private static void addMismatch(ExecutionContext context, List<String> lines, int spaces, boolean invers) {
        if (invers && context.isMatched()) {
            lines.add(withSpacer(spaces, context.getExpectation()));
            if (context.getChilds().isEmpty()) {
                lines.add(withSpacer(spaces + INDENT, "Actual: " + context.getActual()));
            }
        }
        if (!invers && !context.isMatched()) {
            lines.add(withSpacer(spaces, context.getMismatch()));
            if (context.getChilds().isEmpty()) {
                lines.add(withSpacer(spaces + INDENT, "Actual: " + context.getActual()));
            }
        }
        for (ExecutionContext downstreamContext : context.getChilds()) {
            addMismatch(  downstreamContext, lines, spaces + INDENT,
                    invers ^ context.isInvers());
        }
    }

    private static String withSpacer(final int spaces, Object object) {
        if (object == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(spaces);
        for (int i = 0; i < spaces; i++) {
            sb.append(" ");
        }
        sb.append(object);
        return sb.toString();
    }

    public DefaultSimpleExecutionContextMessageCreator() {
        super();
    }

    @Override
    public String toMessage(ExecutionContext context) {
        List<String> lines = new ArrayList<>();
        lines.add("Expected:");

        addExpectations(context, lines, INDENT);

        lines.add("Mismatches:");
        addMismatch(context, lines, INDENT, context.isInvers());

        return lines.stream().filter(Objects::nonNull).filter(new DistinctFromPreceeding())
                .collect(joining(lineSeparator()));
    }
}
