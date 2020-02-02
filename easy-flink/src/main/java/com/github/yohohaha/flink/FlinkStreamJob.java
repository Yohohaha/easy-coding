package com.github.yohohaha.flink;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.github.yohohaha.flink.exception.EnvHandlerException;
import com.github.yohohaha.flink.workflow.configuration.LazyRequiredConfiguration;
import com.github.yohohaha.flink.workflow.handler.CheckpointHandler;
import com.github.yohohaha.flink.workflow.handler.FlinkStreamJobEnvHandler;
import com.github.yohohaha.java.util.ClassUtils;
import com.github.yohohaha.java.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static com.github.yohohaha.flink.workflow.configuration.FlinkJobOptions.FLINK_JOB_NAME;
import static com.github.yohohaha.flink.workflow.configuration.FlinkJobOptions.RUN_CONFIGURATION;

/**
 * created at 2020/01/07 14:24:37
 *
 * @author Yohohaha
 */
public abstract class FlinkStreamJob {
    private static final Logger log = LoggerFactory.getLogger(FlinkStreamJob.class);
    private Configuration config;
    private String flinkJobName;
    private StreamExecutionEnvironment env;
    private Map<Integer, FlinkStreamJobEnvHandler> order2EnvHandlerMap;

    public static void run(String[] args) throws Exception {
        Class<? extends FlinkStreamJob> c = ClassUtils.getInvocationClass();
        Constructor<? extends FlinkStreamJob> constructor = c.getConstructor();
        FlinkStreamJob flinkStreamJob = constructor.newInstance();
        flinkStreamJob.init(args);
        flinkStreamJob.process();
        flinkStreamJob.finish();
    }

    /**
     * init flink configurations and other workflow handlers
     *
     * @param args
     */
    final protected void init(String[] args) {
        ParameterTool argParas = ParameterTool.fromArgs(args);
        String runConfigurationPath = argParas.getRequired(RUN_CONFIGURATION.key());
        log.info("runConfigurationPath={}", runConfigurationPath);
        Properties runProperties;
        try {
            runProperties = IOUtils.readProperties(runConfigurationPath);
        } catch (IOException e) {
            log.error("exception occured when reading `run.configuration`", e);
            runProperties = new Properties();
        }
        config = new LazyRequiredConfiguration(ParameterTool.fromMap((Map) runProperties).mergeWith(argParas).getConfiguration());
        flinkJobName = ((LazyRequiredConfiguration) config).getStringRequired(FLINK_JOB_NAME);
        env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(config);

        // add env handlers
        addInitialEnvHandlers();

        // add process handlers

    }

    abstract protected void process();

    final protected void finish() throws Exception {
        env.execute(flinkJobName());
    }

    private void addInitialEnvHandlers() {
        order2EnvHandlerMap = new HashMap<>();
        addEnvHandler(0, new CheckpointHandler());
    }

    protected String flinkJobName() {
        return flinkJobName;
    }

    /*
     * workflow handler methods
     */
    protected FlinkStreamJob addEnvHandler(int order, FlinkStreamJobEnvHandler envHandler) {
        if (order2EnvHandlerMap.containsKey(order)) {
            throw new EnvHandlerException("should not give the same order when specifying env handler," +
                " there is one order=" + order + "," +
                " and the handler class is " + order2EnvHandlerMap.get(order).getClass());
        }
        order2EnvHandlerMap.put(order, envHandler);
        return this;
    }

    /*
     * properties getters
     */
    protected Configuration config() {
        return config;
    }

    protected StreamExecutionEnvironment env() {
        return env;
    }
}
