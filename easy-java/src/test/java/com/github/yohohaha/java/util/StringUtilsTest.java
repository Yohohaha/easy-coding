package com.github.yohohaha.java.util;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * created by Yohohaha at 2020/02/04 23:01:57
 */
public class StringUtilsTest {

    @Test
    public void interpolateTest() {
        assertEquals("My name is zhangsan", StringUtils.interpolate("My name is ${name}", new HashMap<String, String>() {
            {
                put("name", "zhangsan");
            }
        }));
        assertEquals("My name is zhangsan", StringUtils.interpolate("My name is ${name}", new HashMap<String, String>() {
            {
                put("name", "zhangsan");
            }
        }));
    }
}