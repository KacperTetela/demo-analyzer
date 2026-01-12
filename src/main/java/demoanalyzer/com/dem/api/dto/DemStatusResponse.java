package demoanalyzer.com.dem.api.dto;

import demoanalyzer.com.dem.domain.model.metadata.AnalysisStatus;

import java.time.Instant;

public record DemStatusResponse(
        long demId, Instant createdAt, AnalysisStatus status, String description) {}
