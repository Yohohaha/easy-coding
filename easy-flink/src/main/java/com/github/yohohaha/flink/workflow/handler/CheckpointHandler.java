package com.github.yohohaha.flink.workflow.handler;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import static com.github.yohohaha.flink.workflow.configuration.CheckpointOptions.CHECKPOINTING_DISABLE;
import static com.github.yohohaha.flink.workflow.configuration.CheckpointOptions.CHECKPOINTING_INTERVAL;
import static com.github.yohohaha.flink.workflow.configuration.CheckpointOptions.CHECKPOINTING_MODE;

/**
 * created at 2020/01/16 11:46:44
 *
 * @author Yohohaha
 */
public class CheckpointHandler implements FlinkStreamJobEnvHandler {
    public static void main(String[] args) {

    }


    @Override
    public void handleEnv(StreamExecutionEnvironment env, Configuration config) {
        // checkpoint disabled, do nothing
        if (config.contains(CHECKPOINTING_DISABLE)) {
            return;
        }

        env.enableCheckpointing(config.getLong(CHECKPOINTING_INTERVAL));

        CheckpointConfig checkpointConfig = env.getCheckpointConfig();
        checkpointConfig.setCheckpointingMode(config.getEnum(CheckpointingMode.class, CHECKPOINTING_MODE));
    }
}
