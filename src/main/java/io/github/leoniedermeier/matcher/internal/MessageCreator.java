package io.github.leoniedermeier.matcher.internal;

import io.github.leoniedermeier.matcher.ExecutionContext;

@FunctionalInterface
public interface MessageCreator {

    String toMessage(ExecutionContext context);
}
