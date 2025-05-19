package com.example.goblin.entity;

import com.example.goblin.enums.JobStatus;
import com.example.goblin.enums.JobType;
import com.example.goblin.enums.SinkType;
import com.example.goblin.enums.SourceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "job_configs")
@Getter
@Setter
public class JobConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String jobId;

    private String jobName;

    private String schemaName;

    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    @Lob
    private String sourceProperties;

    private List<String> transformations;

    @Enumerated(EnumType.STRING)
    private SinkType sinkType;

    @Lob
    private String sinkProperties;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    private JobStatus jobStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
