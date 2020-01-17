package com.github.yohohaha.java.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * created by Yohohaha at 2020/01/18 02:43:22
 */
public class IOUtilsTest {

    @Test
    public void readResourceContent() throws IOException {
        Assert.assertEquals("java.version=1.8", IOUtils.readResourceContent("test_ioutils.properties"));
    }
}