package com.example.goblin.interfaces;

import com.example.goblin.entity.JobConfig;

public interface JobExecutor {

    void execute(JobConfig jobConfig) throws Exception;
}
