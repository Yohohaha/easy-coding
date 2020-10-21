package com.github.yohohaha.java.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * created by Yohohaha at 2020/10/21 11:55:30
 */
public class DbUtilsTest {

    @Test
    public void testSplitSqls() {
        List<String> sqls = DbUtils.splitSqls("insert into `test`(`name`) values ('jack'); insert into `test`(`name`) values ('david');");
        assertArrayEquals(new String[]{
            "insert into `test`(`name`) values ('jack');",
            "insert into `test`(`name`) values ('david');"
        }, sqls.toArray(new String[2]));
    }
}