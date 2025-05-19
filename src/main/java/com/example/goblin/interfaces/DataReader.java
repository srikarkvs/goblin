package com.example.goblin.interfaces;


import com.example.goblin.entity.JobConfig;

import java.util.stream.Stream;

public interface DataReader<T> {
    Stream<T> read(JobConfig jobConfig) throws Exception;
}