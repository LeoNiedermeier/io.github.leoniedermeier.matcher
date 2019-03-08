package io.github.leoniedermeier.matcher;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

public interface BaseMatcher<T> extends Matcher<T> {

    @Target({ ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface NonNull {
    }

    static boolean hasNonNullAnnotation(Class<?> clazz) {
        try {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if ("doesMatch".equals(method.getName()) && !method.isBridge()) {
                    Annotation[] annotations = method.getParameters()[0].getAnnotationsByType(NonNull.class);
                    if (annotations.length > 0) {
                        return true;
                    }
                }
            }

        } catch (Exception e) {
            throw new AssertionError("Error processing matcher class ", e);
        }
        return false;
    }

    static void updateTreeEntryOnEnter(BaseMatcher<?> matcher, ExecutionContext context) {
        if (context.getTreeEntry() == null) {
            TreeEntry traverse = TreeBuilder.buildFrom(matcher);
            context.setTreeEntry(traverse);
        } else {

            for (TreeEntry child : context.getTreeEntry().getChilds()) {
                if (child.getMatcher() == matcher) {
                    context.setTreeEntry(child);
                    break;
                }
            }
            if (context.getTreeEntry().getMatcher() != matcher) {
                throw new IllegalStateException("No child found with matcher equals tu current matcher");
            }
        }
        context.getTreeEntry().setInvers(context.isInvers());

        // clear children
        context.getTreeEntry().applyRecursice((TreeEntry t) -> {
            t.setExecuted(true);
            t.setMismatch(null);
            t.setMatched(false);
        });
    }

    static void updateTreeEntryOnExit(ExecutionContext context) {
        if (context.getTreeEntry().getParent() != null) {
            context.setTreeEntry(context.getTreeEntry().getParent());
        }
    }

    boolean doesMatch(T actual, ExecutionContext context);

    @SuppressWarnings("squid:S1452") 
    default Collection<BaseMatcher<?>> getChilds() {
        return Collections.emptyList();
    }

    Entry getExpectation();

    @Override
    default boolean matches(T actual, ExecutionContext context) {
        updateTreeEntryOnEnter(this, context);
        context.setActual(actual);
        if (actual == null && hasNonNullAnnotation(getClass())) {
            context.setMismatch("value is <null>");
            context.setMatched(false);
            return false;
        }
        boolean matched = doesMatch(actual, context);
        context.setMatched(matched);
        updateTreeEntryOnExit(context);
        return matched;
    }

}
