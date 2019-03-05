package io.github.leoniedermeier.matcher.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.github.leoniedermeier.matcher.ExecutionContext;

public final class DescriptionStringifier {

    private static class DistinctFromPreceeding implements Predicate<String> {

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

    public static String getExpectationsAsSting(SimpleExecutionContext context) {
        List<String> lines = new ArrayList<>();
        lines.add("\nExpected:");

        doExpectations(context, lines, 4);

        lines.add("Mismatches:");
        doMismatch(context, lines, 4);
        return lines.stream().filter(Objects::nonNull).filter(new DistinctFromPreceeding())
                .collect(Collectors.joining("\n"));
    }

    private static void doExpectations(SimpleExecutionContext context, List<String> lines, int spaces) {
        lines.add(withSpacer(spaces, context.getExpectation()));

        for (ExecutionContext description : context.getChilds()) {
            doExpectations((SimpleExecutionContext) description, lines, spaces + 4);
        }
    }

    private static void doMismatch(SimpleExecutionContext context, List<String> lines, int spaces) {
        if (context.getMismatch() != null) {
            lines.add(withSpacer(spaces, (!context.isMatched() ? "> " : " ") + context.getMismatch()));
            lines.add(withSpacer(spaces + 4, "Actual: " + context.getActual()));
        }
        for (ExecutionContext description : context.getChilds()) {
            doMismatch((SimpleExecutionContext) description, lines, spaces + 4);
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

    private DescriptionStringifier() {
        throw new AssertionError("No DescriptionStringifier instances for you!");
    }
}
