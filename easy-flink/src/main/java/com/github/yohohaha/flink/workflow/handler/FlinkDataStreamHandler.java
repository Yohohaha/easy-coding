package com.github.yohohaha.flink.workflow.handler;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * created at 2020/01/16 15:08:15
 *
 * @author Yohohaha
 */
public interface FlinkDataStreamHandler<InStream, OutStream> {
    OutStream handleStream(StreamExecutionEnvironment env, InStream stream, Configuration config);
}
