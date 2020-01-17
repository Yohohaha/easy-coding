package com.github.yohohaha.java.util;

/**
 * created at 2020/01/18 02:24:57
 *
 * @author Yohohaha
 */
public class ClassUtils {
    /**
     * get current class(invocation class)'s name
     *
     * @return current class name
     */
    public static String getCurrentClassName() {
        return Thread.currentThread().getStackTrace()[1].getClassName();
    }

    /**
     * get current invocation class(invocation class of invocation class)'s name
     *
     * @return current invocation class
     */
    public static String getInvocationClassName() {
        return Thread.currentThread().getStackTrace()[3].getClassName();
    }

    /**
     * get current invocation class
     *
     * @return current invocation class
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getInvocationClass() throws ClassNotFoundException {
        return (Class<T>) Class.forName(Thread.currentThread().getStackTrace()[3].getClassName());
    }

}
