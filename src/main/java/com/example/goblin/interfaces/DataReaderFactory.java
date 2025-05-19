package com.example.goblin.interfaces;

import com.example.goblin.entity.JobConfig;

public interface DataReaderFactory {
    DataReader<?> getReader(JobConfig config);
}
