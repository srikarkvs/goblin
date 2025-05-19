package com.example.goblin.repositories;

import com.example.goblin.entity.JobConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobConfigRepository extends JpaRepository<JobConfig, Long> {
    Optional<JobConfig> findByJobId(String jobId);
    void updateStatus(String jobId, String status);
}
