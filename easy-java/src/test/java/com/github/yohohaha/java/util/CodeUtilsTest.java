package com.github.yohohaha.java.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * created by Yohohaha at 2020/02/07 10:27:47
 */
public class CodeUtilsTest {

    @Test
    public void get() {
        String s = "1";
        assertEquals("1", CodeUtils.get(() -> {
            if (Integer.parseInt(s) == 1) {
                return s;
            } else {
                return "zero";
            }
        }));
    }
}