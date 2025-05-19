package com.example.goblin.sinks;

import com.example.goblin.entity.JobConfig;
import com.example.goblin.enums.SinkType;
import com.example.goblin.interfaces.DataSink;
import com.example.goblin.interfaces.DataSinkFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSinkFactoryImpl implements DataSinkFactory {

    private final Map<String, DataSink<?>> sinkMap;

    @Autowired
    public DataSinkFactoryImpl(Map<String, DataSink<?>> sinkMap) {
        this.sinkMap = sinkMap;
    }

    @Override
    public DataSink<?> getSink(JobConfig config) {
        SinkType sinkType = config.getSinkType();
        DataSink<?> sink = sinkMap.get(sinkType.name());
        if (sink == null) {
            throw new IllegalArgumentException("No sink registered for sink type: " + sinkType);
        }
        return sink;
    }
}
