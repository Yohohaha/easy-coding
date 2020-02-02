package com.github.yohohaha.flink.workflow.configuration;

import org.apache.flink.api.common.time.Time;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.streaming.api.CheckpointingMode;

/**
 * created at 2020/01/16 11:49:58
 *
 * @author Yohohaha
 */
public class CheckpointOptions {
    /**
     * checkpoint will be enabled by default, so use `CHECKPOINT_DISABLE` to disable it
     */
    public static final ConfigOption<String> CHECKPOINTING_DISABLE = ConfigOptions
        .key("flink.checkpointing.disable")
        .noDefaultValue()
        .withDescription("disable flink checkpoint");

    public static final ConfigOption<Long> CHECKPOINTING_INTERVAL = ConfigOptions
        .key("flink.checkpointing.interval")
        .defaultValue(Time.minutes(10).toMilliseconds())
        .withDescription("flink checkpoint interval");

    /**
     * flink's conf.yaml can set these options
     */
    // public static final ConfigOption<String> STATE_BACKEND = ConfigOptions
    //     .key("flink.statebackend")
    //     .defaultValue("rocksdb")
    //     .withDescription("flink statebackend to use");
    //
    // public static final ConfigOption<String> BACKEND_PATH = ConfigOptions
    //     .key("flink.statebackend.path")
    //     .noDefaultValue()
    //     .withDescription("flink statebackend path");

    public static final ConfigOption<String> CHECKPOINTING_MODE = ConfigOptions
        .key("flink.checkpointing.mode")
        .defaultValue(CheckpointingMode.EXACTLY_ONCE.name())
        .withDescription("flink checkpointing mode");
}
