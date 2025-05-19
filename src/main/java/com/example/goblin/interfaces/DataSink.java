package com.example.goblin.interfaces;

import com.example.goblin.entity.JobConfig;

import java.util.stream.Stream;

public interface DataSink<T> {
    void write(Stream<T> data, JobConfig jobConfig) throws Exception;
}
