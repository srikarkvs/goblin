package com.example.goblin.sinks;

import com.example.goblin.entity.JobConfig;
import com.example.goblin.interfaces.DataSink;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.stream.Stream;

@Component("JSON_SINK")
public class JsonFileDataSink<T> implements DataSink<T> {

    private final ObjectMapper mapper;

    @Autowired
    public JsonFileDataSink(ObjectMapper mapper) {
        this.mapper = mapper;
        // configure mapper if needed, e.g. pretty‐print:
        this.mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    }

    @Override
    public void write(Stream<T> data, JobConfig cfg) throws Exception {
        // sinkProperties holds your output path, e.g. "/data/output.json"
        String outputPath = cfg.getSinkProperties().trim().replace("\"", "");
        File out = new File(outputPath);

        // ensure parent dirs exist
        File parent = out.getParentFile();
        if (parent != null) parent.mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(out))) {
            // start array
            writer.write("[");
            writer.newLine();

            // write each element as JSON
            // collect with an index so we can comma‐separate
            Object[] first = new Object[1]; // hack to detect first element
            data.forEach(item -> {
                try {
                    if (first[0] != null) {
                        writer.write(",");
                        writer.newLine();
                    } else {
                        first[0] = Boolean.TRUE;
                    }
                    // serialize `item` (could be JsonNode or your POJO)
                    writer.write(mapper.writeValueAsString(item));
                } catch (Exception e) {
                    throw new RuntimeException("Error writing JSON element", e);
                }
            });

            // close array
            writer.newLine();
            writer.write("]");
        }
    }
}
