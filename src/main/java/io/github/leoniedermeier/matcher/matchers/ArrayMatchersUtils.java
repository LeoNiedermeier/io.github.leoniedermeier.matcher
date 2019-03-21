package io.github.leoniedermeier.matcher.matchers;

import java.lang.reflect.Array;
import java.util.Deque;

import io.github.leoniedermeier.matcher.imp.Message;
import io.github.leoniedermeier.matcher.imp.MismatchMessage;

// copied and modified from java.util.Arrays.deepEquals0(Object, Object)
public class ArrayMatchersUtils {

    public static class IndexEntry {

        private final Integer index;
        private Message mismatch;
        private final Object value1;
        private final Object value2;

        public IndexEntry(Integer index, Object value1, Object value2) {
            this.index = index;
            this.value1 = value1;
            this.value2 = value2;
        }

        public Integer getIndex() {
            return index;
        }

        public Message getMismatch() {
            return mismatch;
        }

        public Object getValue1() {
            return value1;
        }

        public Object getValue2() {
            return value2;
        }

        public void setMismatch(Message mismatch) {
            this.mismatch = mismatch;
        }
    }

    @SuppressWarnings({ "squid:MethodCyclomaticComplexity", "squid:S1142" })
    public static boolean deepEquals0(Object e1, Object e2, Deque<IndexEntry> indices) {
        if (e1 == e2) {
            return true;
        }
        if (e1 == null || e2 == null) {
            IndexEntry entry = new IndexEntry(null, e1, e2);
            indices.add(entry);
            return false;
        }
        if (arraysOfDifferentLength(e1, e2)) {
            IndexEntry entry = new IndexEntry(null, e1, e2);
            entry.mismatch = new MismatchMessage("arrays have differnt length <%s> and <%s>", Array.getLength(e1),
                    Array.getLength(e2));
            indices.add(entry);
            return false;
        }

        boolean eq;
        if (e1 instanceof Object[] && e2 instanceof Object[]) {
            eq = deepEquals((Object[]) e1, (Object[]) e2, indices);
        } else if (samePrimitiveComponentType(e1, e2, Byte.TYPE)) {
            eq = equals((byte[]) e1, (byte[]) e2, indices);
        } else if (samePrimitiveComponentType(e1, e2, Short.TYPE)) {
            eq = equals((short[]) e1, (short[]) e2, indices);
        } else if (samePrimitiveComponentType(e1, e2, Integer.TYPE)) {
            eq = equals((int[]) e1, (int[]) e2, indices);
        } else if (samePrimitiveComponentType(e1, e2, Long.TYPE)) {
            eq = equals((long[]) e1, (long[]) e2, indices);
        } else if (samePrimitiveComponentType(e1, e2, Character.TYPE)) {
            eq = equals((char[]) e1, (char[]) e2, indices);
        } else if (samePrimitiveComponentType(e1, e2, Float.TYPE)) {
            eq = equals((float[]) e1, (float[]) e2, indices);
        } else if (samePrimitiveComponentType(e1, e2, Double.TYPE)) {
            eq = equals((double[]) e1, (double[]) e2, indices);
        } else if (samePrimitiveComponentType(e1, e2, Boolean.TYPE)) {
            eq = equals((boolean[]) e1, (boolean[]) e2, indices);
        } else {
            eq = e1.equals(e2);
        }
        return eq;
    }

    private static boolean arraysOfDifferentLength(Object e1, Object e2) {
        return e1.getClass().isArray() && e2.getClass().isArray() && Array.getLength(e1) != Array.getLength(e2);
    }

    private static boolean deepEquals(Object[] a1, Object[] a2, Deque<IndexEntry> indices) {
        for (int i = 0; i < a1.length; i++) {
            Object e1 = a1[i];
            Object e2 = a2[i];

            // Figure out whether the two elements are equal
            boolean eq = deepEquals0(e1, e2, indices);

            if (!eq) {
                indices.addFirst(new IndexEntry(i, e1, e2));
                return false;
            }
        }
        return true;
    }

    private static boolean equals(boolean[] a1, boolean[] a2, Deque<IndexEntry> indices) {

        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                indices.addFirst(new IndexEntry(i, a1[i], a2[i]));
                return false;
            }
        }
        return true;
    }

    private static boolean equals(byte[] a1, byte[] a2, Deque<IndexEntry> indices) {
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                indices.addFirst(new IndexEntry(i, a1[i], a2[i]));
                return false;
            }
        }
        return true;
    }

    private static boolean equals(char[] a1, char[] a2, Deque<IndexEntry> indices) {
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                indices.addFirst(new IndexEntry(i, a1[i], a2[i]));
                return false;
            }
        }
        return true;
    }

    private static boolean equals(double[] a1, double[] a2, Deque<IndexEntry> indices) {
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                indices.addFirst(new IndexEntry(i, a1[i], a2[i]));
                return false;
            }
        }
        return true;
    }

    private static boolean equals(float[] a1, float[] a2, Deque<IndexEntry> indices) {
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                indices.addFirst(new IndexEntry(i, a1[i], a2[i]));
                return false;
            }
        }
        return true;
    }

    private static boolean equals(int[] a1, int[] a2, Deque<IndexEntry> indices) {

        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                indices.addFirst(new IndexEntry(i, a1[i], a2[i]));
                return false;
            }
        }

        return true;
    }

    private static boolean equals(long[] a1, long[] a2, Deque<IndexEntry> indices) {
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                indices.addFirst(new IndexEntry(i, a1[i], a2[i]));
                return false;
            }
        }
        return true;
    }

    private static boolean equals(short[] a1, short[] a2, Deque<IndexEntry> indices) {
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                indices.addFirst(new IndexEntry(i, a1[i], a2[i]));
                return false;
            }
        }
        return true;
    }

    private static boolean samePrimitiveComponentType(Object e1, Object e2, Class<?> componentType) {
        return e1.getClass().getComponentType() == componentType && e2.getClass().getComponentType() == componentType
                && e1.getClass().getComponentType().isPrimitive();
    }

    private ArrayMatchersUtils() {
        throw new AssertionError("No ArrayMatchersUtils instances for you!");
    }
}
