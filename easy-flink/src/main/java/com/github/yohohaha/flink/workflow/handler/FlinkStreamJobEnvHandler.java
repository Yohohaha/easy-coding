package com.github.yohohaha.flink.workflow.handler;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * created at 2020/01/16 11:35:29
 *
 * @author Yohohaha
 */
public interface FlinkStreamJobEnvHandler {
    void handleEnv(StreamExecutionEnvironment env, Configuration config);
}