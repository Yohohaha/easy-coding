package com.github.yohohaha.java.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Excepition utils for manipulating exceptions.
 *
 * @author Yohohaha
 */
public class ExceptionUtils {
    /**
     * get exception info, printed by {@code Throwable#printStatckTrace} method
     *
     * @param e Throwable object
     *
     * @return exception info
     */
    public static String getExceptionInfo(Throwable e) {
        Writer w = new StringWriter();
        e.printStackTrace(new PrintWriter(w));
        return w.toString();
    }
}
