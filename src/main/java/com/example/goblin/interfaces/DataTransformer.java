package com.example.goblin.interfaces;

import com.example.goblin.entity.JobConfig;

public interface DataTransformer<I, O> {
    O transform(I input, JobConfig jobConfig) throws Exception;
}
