package com.github.yohohaha.java.util;

/**
 * created at 2020/02/26 15:12:53
 *
 * @author Yohohaha
 */
public class ThreadUtils {
    /**
     * use for methods that would not invoke interrupt
     *
     * @param milli milliseconds
     */
    public static void sleep(long milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            throw new IllegalStateException("error use for this method:sleep");
        }
    }
}
