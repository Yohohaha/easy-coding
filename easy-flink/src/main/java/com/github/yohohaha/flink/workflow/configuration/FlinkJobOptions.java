package com.github.yohohaha.flink.workflow.configuration;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;

/**
 * created at 2020/01/07 10:14:04
 *
 * @author Yohohaha
 */
public class FlinkJobOptions {
    public static final ConfigOption<String> RUN_CONFIGURATION = ConfigOptions
        .key("flink.runConfiguration")
        .noDefaultValue()
        .withDescription("configuration file path");

    public static final ConfigOption<String> FLINK_JOB_NAME = ConfigOptions
        .key("flink.jobName")
        .noDefaultValue()
        .withDescription("flink job name");
}
