package com.example.goblin.interfaces;

import com.example.goblin.entity.JobConfig;

public interface DataSinkFactory {
    DataSink<?> getSink(JobConfig config);
}
