package com.github.yohohaha.flink.workflow.configuration;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.FallbackKey;

import com.github.yohohaha.flink.exception.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * created at 2020/01/16 16:34:35
 *
 * @author Yohohaha
 */
public class LazyRequiredConfiguration extends Configuration {

    private static final Logger LOG = LoggerFactory.getLogger(LazyRequiredConfiguration.class);

    public LazyRequiredConfiguration(Configuration other) {
        super(other);
    }

    protected boolean convertToBoolean(Object o) {
        if (o.getClass() == Boolean.class) {
            return (Boolean) o;
        } else {
            return Boolean.parseBoolean(o.toString());
        }
    }

    protected boolean convertToBoolean(Object o, Supplier<Boolean> defaultValueSupplier) {
        if (o.getClass() == Boolean.class) {
            return (Boolean) o;
        } else {
            return defaultValueSupplier.get();
        }
    }

    public String getString(String key, Supplier<String> defaultValueSuppiler) {
        return Optional.ofNullable(getRawValue(key)).map(Object::toString).orElseGet(defaultValueSuppiler);
    }

    protected Object getRawValue(String key) {
        if (key == null) {
            throw new NullPointerException("Key must not be null.");
        }

        synchronized (this.confData) {
            return this.confData.get(key);
        }
    }

    public String getStringRequired(ConfigOption<String> configOption) {
        return Optional.ofNullable(super.getString(configOption)).orElseThrow(configExceptionSupplier(configOption));
    }

    protected Supplier<ConfigException> configExceptionSupplier(ConfigOption<?> configOption) {
        return () -> configException(configOption);
    }

    protected ConfigException configException(ConfigOption<?> configOption) {
        return new ConfigException("`" + configOption.key() + "` is not set");
    }

    public String getString(ConfigOption<String> configOption, Supplier<String> overrideDefaultSupplier) {
        return Optional.ofNullable(super.getString(configOption)).orElseGet(overrideDefaultSupplier);
    }

    public int getInteger(String key, Supplier<Integer> defaultValueSupplier) {
        return Optional.ofNullable(getRawValue(key)).map(o -> convertToInt(o, defaultValueSupplier)).get();
    }

    protected int convertToInt(Object o, Supplier<Integer> defaultValueSupplier) {
        if (o.getClass() == Integer.class) {
            return (Integer) o;
        } else if (o.getClass() == Long.class) {
            long value = (Long) o;
            if (value <= Integer.MAX_VALUE && value >= Integer.MIN_VALUE) {
                return (int) value;
            } else {
                LOG.warn("Configuration value {} overflows/underflows the integer type.", value);
                return defaultValueSupplier.get();
            }
        } else {
            try {
                return Integer.parseInt(o.toString());
            } catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value {} as an integer number", o);
                return defaultValueSupplier.get();
            }
        }
    }

    public int getIntegerRequired(ConfigOption<Integer> configOption) {
        return Optional.ofNullable(getRawValueFromOption(configOption)).map(o -> convertToInt(o, configOption.defaultValue())).orElseThrow(configExceptionSupplier(configOption));
    }

    protected Object getRawValueFromOption(ConfigOption<?> configOption) {
        // first try the current key
        Object o = getRawValue(configOption.key());

        if (o != null) {
            // found a value for the current proper key
            return o;
        } else if (configOption.hasFallbackKeys()) {
            // try the deprecated keys
            for (FallbackKey fallbackKey : configOption.fallbackKeys()) {
                Object oo = getRawValue(fallbackKey.getKey());
                if (oo != null) {
                    loggingFallback(fallbackKey, configOption);
                    return oo;
                }
            }
        }

        return null;
    }

    protected int convertToInt(Object o, int defaultValue) {
        if (o.getClass() == Integer.class) {
            return (Integer) o;
        } else if (o.getClass() == Long.class) {
            long value = (Long) o;
            if (value <= Integer.MAX_VALUE && value >= Integer.MIN_VALUE) {
                return (int) value;
            } else {
                LOG.warn("Configuration value {} overflows/underflows the integer type.", value);
                return defaultValue;
            }
        } else {
            try {
                return Integer.parseInt(o.toString());
            } catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value {} as an integer number", o);
                return defaultValue;
            }
        }
    }

    protected void loggingFallback(FallbackKey fallbackKey, ConfigOption<?> configOption) {
        if (fallbackKey.isDeprecated()) {
            LOG.warn("Config uses deprecated configuration key '{}' instead of proper key '{}'",
                fallbackKey.getKey(), configOption.key());
        } else {
            LOG.info("Config uses fallback configuration key '{}' instead of key '{}'",
                fallbackKey.getKey(), configOption.key());
        }
    }

    public int getInteger(ConfigOption<Integer> configOption, Supplier<Integer> overrideDefaultSupplier) {
        return Optional.ofNullable(getRawValueFromOption(configOption)).map(o -> convertToInt(o, overrideDefaultSupplier)).get();
    }

    public long getLong(String key, Supplier<Long> defaultValueSupplier) {
        return Optional.ofNullable(getRawValue(key)).map(o -> convertToLong(o, defaultValueSupplier)).get();
    }

    protected long convertToLong(Object o, Supplier<Long> defaultValueSupplier) {
        if (o.getClass() == Long.class) {
            return (Long) o;
        } else if (o.getClass() == Integer.class) {
            return ((Integer) o).longValue();
        } else {
            try {
                return Long.parseLong(o.toString());
            } catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value " + o + " as a long integer number");
                return defaultValueSupplier.get();
            }
        }
    }

    public long getLongRequired(ConfigOption<Long> configOption) {
        return Optional.ofNullable(getRawValueFromOption(configOption)).map(o -> convertToLong(o, configOption.defaultValue())).orElseThrow(configExceptionSupplier(configOption));
    }

    protected long convertToLong(Object o, long defaultValue) {
        if (o.getClass() == Long.class) {
            return (Long) o;
        } else if (o.getClass() == Integer.class) {
            return ((Integer) o).longValue();
        } else {
            try {
                return Long.parseLong(o.toString());
            } catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value " + o + " as a long integer number");
                return defaultValue;
            }
        }
    }

    public long getLong(ConfigOption<Long> configOption, Supplier<Long> overrideDefaultSupplier) {
        return Optional.ofNullable(getRawValueFromOption(configOption)).map(o -> convertToLong(o, overrideDefaultSupplier)).get();
    }

    public float getFloat(String key, Supplier<Float> defaultValueSupplier) {
        return Optional.ofNullable(getRawValue(key)).map(o -> convertToFloat(o, defaultValueSupplier)).get();
    }

    protected float convertToFloat(Object o, Supplier<Float> defaultValueSupplier) {
        if (o.getClass() == Float.class) {
            return (Float) o;
        } else if (o.getClass() == Double.class) {
            double value = ((Double) o);
            if (value == 0.0
                || (value >= Float.MIN_VALUE && value <= Float.MAX_VALUE)
                || (value >= -Float.MAX_VALUE && value <= -Float.MIN_VALUE)) {
                return (float) value;
            } else {
                LOG.warn("Configuration value {} overflows/underflows the float type.", value);
                return defaultValueSupplier.get();
            }
        } else {
            try {
                return Float.parseFloat(o.toString());
            } catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value {} as a float value", o);
                return defaultValueSupplier.get();
            }
        }
    }

    public float getFloatRequired(ConfigOption<Float> configOption) {
        return Optional.ofNullable(getRawValueFromOption(configOption)).map(o -> convertToFloat(o, configOption.defaultValue())).orElseThrow(configExceptionSupplier(configOption));
    }

    protected float convertToFloat(Object o, float defaultValue) {
        if (o.getClass() == Float.class) {
            return (Float) o;
        } else if (o.getClass() == Double.class) {
            double value = ((Double) o);
            if (value == 0.0
                || (value >= Float.MIN_VALUE && value <= Float.MAX_VALUE)
                || (value >= -Float.MAX_VALUE && value <= -Float.MIN_VALUE)) {
                return (float) value;
            } else {
                LOG.warn("Configuration value {} overflows/underflows the float type.", value);
                return defaultValue;
            }
        } else {
            try {
                return Float.parseFloat(o.toString());
            } catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value {} as a float value", o);
                return defaultValue;
            }
        }
    }

    public float getFloat(ConfigOption<Float> configOption, Supplier<Float> overrideDefaultSupplier) {
        return Optional.ofNullable(getRawValueFromOption(configOption)).map(o -> convertToFloat(o, overrideDefaultSupplier)).get();
    }

    public double getDouble(String key, Supplier<Double> defaultValueSupplier) {
        return Optional.ofNullable(getRawValue(key)).map(o -> convertToDouble(o, defaultValueSupplier)).get();
    }

    protected double convertToDouble(Object o, Supplier<Double> defaultValueSupplier) {
        if (o.getClass() == Double.class) {
            return (Double) o;
        } else if (o.getClass() == Float.class) {
            return ((Float) o).doubleValue();
        } else {
            try {
                return Double.parseDouble(o.toString());
            } catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value {} as a double value", o);
                return defaultValueSupplier.get();
            }
        }
    }

    public double getDoubleRequired(ConfigOption<Double> configOption) {
        return Optional.ofNullable(getRawValueFromOption(configOption)).map(o -> convertToDouble(o, configOption.defaultValue())).orElseThrow(configExceptionSupplier(configOption));
    }

    protected double convertToDouble(Object o, double defaultValue) {
        if (o.getClass() == Double.class) {
            return (Double) o;
        } else if (o.getClass() == Float.class) {
            return ((Float) o).doubleValue();
        } else {
            try {
                return Double.parseDouble(o.toString());
            } catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value {} as a double value", o);
                return defaultValue;
            }
        }
    }

    public double getDouble(ConfigOption<Double> configOption, Supplier<Double> overrideDefaultSupplier) {
        return Optional.ofNullable(getRawValueFromOption(configOption)).map(o -> convertToDouble(o, overrideDefaultSupplier)).get();
    }

    public Properties toProperties() {
        synchronized (this.confData) {
            Properties properties = new Properties();
            for (Map.Entry<String, Object> entry : confData.entrySet()) {
                properties.put(entry.getKey(), entry.getValue().toString());
            }
            return properties;
        }
    }

}
