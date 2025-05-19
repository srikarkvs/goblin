package com.example.goblin.transformers;

import com.example.goblin.entity.JobConfig;
import com.example.goblin.interfaces.DataTransformer;
import com.example.goblin.interfaces.DataTransformerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DataTransformerFactoryImpl implements DataTransformerFactory {
    private final Map<String, DataTransformer<?, ?>> transformerMap;

    @Autowired
    public DataTransformerFactoryImpl(Map<String, DataTransformer<?, ?>> transformerMap) {
        this.transformerMap = transformerMap;
    }

    @Override
    public DataTransformer<?, ?> getTransformer(JobConfig config) {
        List<String> steps = config.getTransformations(); // assume parsed list
        ChainedTransformer<String> chain = new ChainedTransformer<>();

        for (String step : steps) {
            DataTransformer<?, ?> transformer = transformerMap.get(step);
            if (transformer == null) {
                throw new IllegalArgumentException("No transformer found for step: " + step);
            }
            //noinspection unchecked
            chain.addTransformer((DataTransformer<String, String>) transformer);
        }
        return chain;
    }
}
