package io.github.leoniedermeier.matcher.matchers;

import static io.github.leoniedermeier.matcher.matchers.ArrayMatchersUtils.deepEquals0;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.leoniedermeier.matcher.matchers.ArrayMatchersUtils.IndexEntry;

class ArrayMatchersUtilsTest {

    @Nested
    class ArraysWithData {
        @Test
        void boolean_arry() {
            assertArrays(new boolean[] { true, false }, new boolean[] { true, false }, new boolean[] { true, true }, 1);
        }

        @Test
        void byte_arry() {
            assertArrays(new byte[] { 1, 2 }, new byte[] { 1, 2 }, new byte[] { 1, 3 }, 1);
        }

        @Test
        void char_arry() {
            assertArrays(new char[] { 1, 2, 3 }, new char[] { 1, 2, 3 }, new char[] { 1, 99, 3 }, 1);
        }

        @Test
        void double_arry() {
            assertArrays(new double[] { 1, 2, 3 }, new double[] { 1, 2, 3 }, new double[] { 1, 99, 3 }, 1);
        }

        @Test
        void float_arry() {
            assertArrays(new float[] { 1, 2, 3 }, new float[] { 1, 2, 3 }, new float[] { 1, 99, 3 }, 1);
        }

        @Test
        void int_arry() {
            assertArrays(new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 }, new int[] { 1, 2, 4 }, 2);
        }

        @Test
        void long_arry() {
            assertArrays(new long[] { 1, 2, 3 }, new long[] { 1, 2, 3 }, new long[] { 1, 99, 3 }, 1);
        }

        @Test
        void Object_array() {
            assertArrays(new Object[] { "A", "B" }, new Object[] { "A", "B" }, new Object[] { "A", "X" }, 1);
        }

        @Test
        void short_arry() {
            assertArrays(new short[] { 1, 2 }, new short[] { 1, 2 }, new short[] { 99, 2 }, 0);
        }

    }

    @Nested
    class ArrayTypesMix {

        @Test
        void differnetArrayTypes() {
            Object[] arrays = new Object[] { new Object[1], new byte[1], new short[1], new int[1], new long[1],
                    new char[1], new double[1], new float[1], new boolean[1] };
            for (int i = 0; i < arrays.length; i++) {
                for (int j = 0; j < arrays.length; j++) {
                    if (i != j) {
                        assertFalse(deepEquals0(arrays[i], arrays[j], indices));
                    }
                }
            }
        }

        @Test
        void sameArrayTypes() {
            Object[] arrays = new Object[] { new Object[1], new byte[1], new short[1], new int[1], new long[1],
                    new char[1], new double[1], new float[1], new boolean[1] };
            for (int i = 0; i < arrays.length; i++) {
                assertTrue(deepEquals0(arrays[i], arrays[i], indices));
            }
        }
    }

    @Nested
    class Basic {

        @Test
        void arrays_and_not_array() {
            assertFalse(deepEquals0(new int[1], new Object(), indices));
        }

        @Test
        void arrays_with_differnet_length() {
            assertFalse(deepEquals0(new int[1], new int[2], indices));
        }

        @Test
        void both_objects_null() {
            assertTrue(deepEquals0(null, null, indices));
        }

        @Test
        void equal_objects() {
            assertTrue(deepEquals0(new String("a"), new String("a"), indices));
        }

        @Test
        void first_object_null() {
            Object object = new Object();
            assertFalse(deepEquals0(null, object, indices));
        }

        @Test
        void same_objects() {
            Object object = new Object();
            assertTrue(deepEquals0(object, object, indices));
        }

        @Test
        void secound_object_null() {
            Object object = new Object();
            assertFalse(deepEquals0(object, null, indices));
        }
    }

    @Nested
    class MultiDimensionalArrays {

        @Test
        void array_2d_equal() {
            int[][] array1 = { { 11, 12 }, { 21, 22 } };
            int[][] array2 = { { 11, 12 }, { 21, 22 } };
            assertTrue(deepEquals0(array1, array2, indices));
        }

        @Test
        void array_2d_not_equal() {
            int[][] array1 = { { 11, 12 }, { 21, 22 } };
            int[][] array2 = { { 11, 12 }, { 99, 22 } };
            assertFalse(deepEquals0(array1, array2, indices));
            assertTrue(indices.size() == 2);
            assertTrue(indices.get(0).getIndex() == 1);
            assertTrue(indices.get(1).getIndex() == 0);
        }
    }

    private static void assertArrays(Object array1, Object array2, Object array3, int mismatchPosition) {
        {
            Deque<IndexEntry> indices = new LinkedList<>();
            assertTrue(deepEquals0(array1, array2, indices));
            assertTrue(indices.size() == 0);
        }
        {
            Deque<IndexEntry> indices = new LinkedList<>();
            assertFalse(deepEquals0(array1, array3, indices));
            assertTrue(indices.size() == 1);
            assertTrue(indices.getFirst().getIndex() == mismatchPosition);
        }
    }

    private LinkedList<IndexEntry> indices = new LinkedList<>();
}
