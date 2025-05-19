package com.example.goblin.executors;

import com.example.goblin.entity.JobConfig;
import com.example.goblin.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobExecutorImpl implements JobExecutor {
    private static final Logger log = LoggerFactory.getLogger(JobExecutorImpl.class);

    private final DataReaderFactory readerFactory;
    private final DataTransformerFactory transformerFactory;
    private final DataSinkFactory sinkFactory;

    @Autowired
    public JobExecutorImpl(DataReaderFactory readerFactory, DataTransformerFactory transformerFactory, DataSinkFactory sinkFactory) {
        this.readerFactory = readerFactory;
        this.transformerFactory = transformerFactory;
        this.sinkFactory = sinkFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobConfig jobConfig) throws Exception {

        var reader = readerFactory.getReader(jobConfig);
        var transformer = transformerFactory.getTransformer(jobConfig);
        var sink = sinkFactory.getSink(jobConfig);

        log.info("Starting job {} of type {}", jobConfig.getJobId(), jobConfig.getJobType());

        try (var input = reader.read(jobConfig)) {
            var output = input.map(item -> {
                try {
                    return ((DataTransformer<Object, Object>) transformer).transform(item, jobConfig);
                } catch (Exception e) {
                    log.error("Error transforming item {}", item, e);
                    throw new RuntimeException(e);
                }
            });
            ((DataSink<Object>) sink).write(output, jobConfig);
        }
        catch (Exception e) {
            log.error("Error executing job {}", jobConfig.getJobId(), e);
            throw new RuntimeException("Error executing job", e);
        }

    }
}
