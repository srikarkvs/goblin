package com.example.goblin.controller;

import com.example.goblin.entity.JobConfig;
import com.example.goblin.executors.JobExecutorImpl;
import com.example.goblin.interfaces.JobExecutor;
import com.example.goblin.repositories.JobConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/goblin/jobs/")
@CrossOrigin(origins = "*") // Allow all origins for testing
public class JobConfigController {

    @Autowired
    private final JobExecutor jobExecutor;

    @Autowired
    private JobConfigRepository jobConfigRepository;

    @Autowired
    public JobConfigController(JobExecutor jobExecutor) {
        this.jobExecutor = jobExecutor;
    }

    @PostMapping("/createjob/")
    public ResponseEntity<String> createJob(@RequestBody JobConfig config) throws Exception {
        jobExecutor.execute(config);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobConfig> getJob(@PathVariable String jobId) {
        Optional<JobConfig> jobConfig = jobConfigRepository.findByJobId(jobId);
        return jobConfig.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
