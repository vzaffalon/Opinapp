package com.opinnapp.opinnapp.models;

import java.util.List;

/**
 * Created by cayke on 29/06/17.
 */

public class OAUtil {
    public static <T> boolean contains(final List<T> array, final T v) {
        if (v == null) {
            for (final T e : array)
                if (e == null)
                    return true;
        } else {
            for (final T e : array)
                if (e == v || v.equals(e))
                    return true;
        }

        return false;
    }

    public static <T> void add(final List<T> array, final T v) {
        if (!contains(array, v))
            array.add(v);
    }

    public static <T> void remove(final List<T> array, final T v) {
        if (v != null && array != null) {
            for (final T e : array)
                if (e == v || v.equals(e))
                    array.remove(e);
        }
    }
}
