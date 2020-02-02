package com.github.yohohaha.flink.workflow.handler;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * created at 2020/01/17 17:45:59
 *
 * @author Yohohaha
 */
public class KafkaSinkHandler<T> implements FlinkDataStreamHandler<DataStream<T>, DataStream<T>> {
    public static void main(String[] args) {

    }

    @Override
    public DataStream<T> handleStream(StreamExecutionEnvironment env, DataStream<T> stream, Configuration config) {
        // stream.addSink();
        return null;
    }
}
