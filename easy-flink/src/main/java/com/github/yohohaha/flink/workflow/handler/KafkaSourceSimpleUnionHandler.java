package com.github.yohohaha.flink.workflow.handler;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import com.github.yohohaha.flink.workflow.configuration.LazyRequiredConfiguration;
import com.github.yohohaha.java.util.config.PrefixProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.github.yohohaha.flink.workflow.configuration.KafkaOptions.KAFKA_TOPICS;

/**
 * created at 2020/01/16 15:15:57
 *
 * @author Yohohaha
 */
public class KafkaSourceSimpleUnionHandler implements FlinkDataStreamHandler<Object, DataStream<String>> {
    public static void main(String[] args) {

    }

    @Override
    public DataStream<String> handleStream(StreamExecutionEnvironment env, Object o, Configuration config) {
        // kafka_servers_key1:topic1,topic2;kafka_servers_key2:topic1,topic2
        String kafkaTopicsStr = ((LazyRequiredConfiguration) config).getStringRequired(KAFKA_TOPICS);
        DataStream<String> source = null;
        String[] serversSplits = kafkaTopicsStr.split(";");
        for (String servers : serversSplits) {
            List<String> topics = Arrays.asList(servers.split(","));
            Properties kafkaProperties = new PrefixProperties(((LazyRequiredConfiguration) config).toProperties(), "kafka").getAll();
            kafkaProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
            FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(topics, new SimpleStringSchema(), kafkaProperties);
            consumer.setStartFromTimestamp(1L);
            if (source == null) {
                source = env.addSource(consumer);
            } else {
                source = source.union(env.addSource(consumer));
            }
        }
        return source;
    }
}
