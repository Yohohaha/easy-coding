package com.github.yohohaha.flink.workflow.configuration;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;

/**
 * created at 2020/01/16 15:51:03
 *
 * @author Yohohaha
 */
public class KafkaOptions {
    public static final ConfigOption<String> KAFKA_TOPICS = ConfigOptions
        .key("flink.kafka.topics")
        .noDefaultValue()
        .withDescription("flink kafka topics, "
            + "format as `kafka_servers_key1:topic1,topic2;kafka_servers_key2:topic1,topic2`");
}
