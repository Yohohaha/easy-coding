package com.github.yohohaha.java.util;

import java.util.function.Supplier;

/**
 * created at 2020/02/07 10:26:32
 *
 * @author Yohohaha
 */
public class CodeUtils {
    public static <T> T get(Supplier<T> supplier) {
        return supplier.get();
    }
}
