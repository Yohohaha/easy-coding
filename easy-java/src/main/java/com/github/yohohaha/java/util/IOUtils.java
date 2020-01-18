package com.github.yohohaha.java.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/**
 * IO Utils for processing io.
 *
 * @author Yohohaha
 */
public class IOUtils {
    /**
     * read resource content from file path
     *
     * @param inPath file path
     *
     * @return resource content in string
     *
     * @throws IOException if any io exception occurs
     */
    public static String readResourceContent(String inPath) throws IOException {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(inPath)) {
            if (in == null) {
                return null;
            }
            return readResourceContent(in);
        }
    }

    /**
     * read resource content from inputstream
     *
     * @param in inputstream
     *
     * @return resource content in string
     *
     * @throws IOException if any io exception occurs
     */
    public static String readResourceContent(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bufos = new BufferedOutputStream(baos);
        BufferedInputStream bufis = new BufferedInputStream(in);
        byte[] buf = new byte[8092];
        int len;
        while ((len = bufis.read(buf)) > 0) {
            bufos.write(buf, 0, len);
        }
        bufos.flush();
        return baos.toString(String.valueOf(StandardCharsets.UTF_8));
    }

    /**
     * read properties from file path
     *
     * @param inPath file path
     *
     * @return properties from file path
     *
     * @throws IOException if any io exception occurs
     */
    public static Properties readProperties(String inPath) throws IOException {
        final Properties properties = new Properties();
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(inPath)) {
            properties.load(in);
        }
        return properties;
    }


    /**
     * get absolute path from resource path
     *
     * @param resourcePath maven resource path
     *
     * @return absolte path
     *
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static String getAbsoulutePathFromResourcePath(String resourcePath) {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(resourcePath)).getPath();
    }
}
