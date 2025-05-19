package com.example.goblin.readers;

import com.example.goblin.entity.JobConfig;
import com.example.goblin.interfaces.DataReader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

@Component(value = "CSV")
public class CsvFileReader implements DataReader<String[]> {
    @Override
    public Stream<String[]> read(JobConfig jobConfig) throws Exception {
        String filePath = extractFilePath(jobConfig.getSourceProperties());

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        return reader.lines()
                .skip(1)
                .map(line -> line.split(",")) // naive CSV split
                .onClose(() -> {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException("Error closing CSV reader", e);
                    }
                });
    }

    private String extractFilePath(String sourceProperties) {
        return sourceProperties.trim().replace("\"", "");
    }
}
