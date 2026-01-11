package demoanalyzer.com.dem.api.dto;

import demoanalyzer.com.dem.domain.model.status.DemAnalysisStatus;

import java.time.Instant;

public record DemStatusResponse(
    long demId, Instant createdAt, DemAnalysisStatus status, String description) {}
