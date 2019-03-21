package io.github.leoniedermeier.matcher.imp;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Collections;

import io.github.leoniedermeier.matcher.Matcher;

public abstract class BaseMatcher<T> implements Matcher<T> {

    @Target({ ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface NonNull {
    }

    private static final Method findDoesMatchMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if ("doesMatch".equals(method.getName()) && !method.isBridge()) {
                return method;
            }
        }
        return findDoesMatchMethod(clazz.getSuperclass());
    }

    private static boolean hasNonNullAnnotation(Class<?> clazz) {
        try {
            Method method = findDoesMatchMethod(clazz);
            Parameter[] parameters = method.getParameters();
            Annotation[] annotations = parameters[1].getAnnotationsByType(NonNull.class);
            if (annotations.length > 0) {
                return true;
            }
        } catch (Exception e) {
            throw new AssertionError("Error processing matcher class ", e);
        }
        return false;
    }

    @Override
    public boolean matches(ExecutionContext executionContext, T actual) {
        executionContext.onEnter(this);
        try {
            executionContext.setLastActual(actual);
            if (actual == null && hasNonNullAnnotation(getClass())) {
                executionContext.mismatch("value is <null>");
            } else {
                doesMatch(executionContext, actual);
            }
            return executionContext.isMatched();
        } finally {
            executionContext.onExit();
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + getExpectation();
    }

    protected abstract void doesMatch(ExecutionContext executionContext, T actual);

    @SuppressWarnings("squid:S1452")
    protected Collection<BaseMatcher<?>> getChilds() {
        return Collections.emptyList();
    }

    protected abstract Message getExpectation();
}
