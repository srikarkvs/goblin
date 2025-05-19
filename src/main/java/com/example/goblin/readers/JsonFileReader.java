package com.example.goblin.readers;

import com.example.goblin.entity.JobConfig;
import com.example.goblin.interfaces.DataReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.stream.Stream;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.stream.StreamSupport;

@Component("JSON")
public class JsonFileReader implements DataReader<JsonNode> {

    private final ObjectMapper mapper;

    @Autowired
    public JsonFileReader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Stream<JsonNode> read(JobConfig jobConfig) throws Exception {
        // sourceProperties is expected to be a path to a .json file
        String path = jobConfig.getSourceProperties().trim().replace("\"", "");
        File file = new File(path);

        // parse the entire JSON document
        JsonNode root = mapper.readTree(file);

        // if it's an array, stream each element; otherwise stream just the single node
        if (root.isArray()) {
            return StreamSupport.stream(root.spliterator(), false);
        } else {
            return Stream.of(root);
        }
    }
}

