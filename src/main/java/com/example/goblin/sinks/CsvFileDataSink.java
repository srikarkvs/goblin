package com.example.goblin.sinks;

import com.example.goblin.entity.JobConfig;
import com.example.goblin.executors.JobExecutorImpl;
import com.example.goblin.interfaces.DataSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

@Component("CSV_SINK")
public class CsvFileDataSink implements DataSink<String[]> {
    private static final Logger log = LoggerFactory.getLogger(CsvFileDataSink.class);
    @Override
    public void write(Stream<String[]> data, JobConfig jobConfig) throws Exception {
        String outputPath = extractOutputPath(jobConfig.getSinkProperties());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            data.forEach(record -> {
                try {
                    writer.write(String.join(",", record));
                    writer.newLine();
                } catch (IOException e) {
                    log.error("Error writing CSV record {}", record, e);
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private String extractOutputPath(String sinkProperties) {
        return sinkProperties.trim().replace("\"", "");
    }
}
