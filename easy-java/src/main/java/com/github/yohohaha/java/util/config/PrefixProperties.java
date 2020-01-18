package com.github.yohohaha.java.util.config;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties with specified prefix.
 *
 * @author Yohohaha
 */
public class PrefixProperties {
    private Properties props;
    private String prefix;

    public PrefixProperties(final Properties props, final String prefix) {
        this.props = props;
        this.prefix = prefix;
    }

    public String getProperty(String key) {
        return this.props.getProperty(this.prefix + "." + key);
    }

    public Properties getAll() {
        Properties prefixProperties = new Properties();
        String prefixDot = this.prefix + ".";
        @SuppressWarnings("unchecked")
        Enumeration<String> keys = (Enumeration<String>) this.props.propertyNames();
        int length = prefixDot.length();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.startsWith(prefixDot)) {
                prefixProperties.setProperty(key.substring(length), this.props.getProperty(key));
            }
        }
        return prefixProperties;
    }

    @SuppressWarnings("unchecked")
    public <T> Map<String, T> getAllMap() {
        Map<String, T> map = new HashMap<>(this.props.size());
        String prefixDot = this.prefix + ".";
        Enumeration<String> keys = (Enumeration<String>) this.props.propertyNames();
        int length = prefixDot.length();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.startsWith(prefixDot)) {
                map.put(key.substring(length), (T) this.props.getProperty(key));
            }
        }
        return Collections.unmodifiableMap(map);
    }
}
