package com.example.goblin.interfaces;

import com.example.goblin.entity.JobConfig;

public interface DataTransformerFactory {
    DataTransformer<?, ?> getTransformer(JobConfig config);
}
