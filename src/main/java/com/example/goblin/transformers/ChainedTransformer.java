package com.example.goblin.transformers;

import com.example.goblin.entity.JobConfig;
import com.example.goblin.interfaces.DataTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChainedTransformer<T> implements DataTransformer<T, T> {
    private final List<DataTransformer<T, T>> transformers = new ArrayList<>();

    public void addTransformer(DataTransformer<T, T> transformer) {
        transformers.add(transformer);
    }

    @Override
    public T transform(T input, JobConfig jobConfig) throws Exception {
        T result = input;
        for (DataTransformer<T, T> transformer : transformers) {
            result = transformer.transform(result,jobConfig);
        }
        return result;
    }
}
