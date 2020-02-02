package com.github.yohohaha.flink;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.KeyedStream;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * created by Yohohaha at 2020/01/28 22:27:03
 */
public class FlinkStreamJobTest extends FlinkStreamJob {
    @Test
    public void testRun() throws Exception {
        run(new String[]{"-flink.runConfiguration", "test.properties", "-flink.jobName", "testJob"});
    }

    @Override
    protected void process() {
        env().setParallelism(1);
        KeyedStream<Tuple3<Integer, String, Integer>, String> keyedStream = env().fromElements(
            Tuple3.of(1, "key", 2),
            Tuple3.of(2, "key", 3),
            Tuple3.of(3, "key", 1)
        ).keyBy(value -> value.f1);
        keyedStream
            .sum(0).print("sum");
        keyedStream
            .minBy(0).print("minBy");
        keyedStream
            .min(0).print("min");
        keyedStream
            .maxBy(0).print("maxBy");
        keyedStream
            .max(0).print("max");
    }
}