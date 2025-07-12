package com.clamav.backend.vo;

import lombok.Data;

@Data
public class EngineStatus {
    private String clamavVersion;
    private String virusDbVersion;
    private String virusDbUpdateTime;
    private String processStatus;
    private String memoryUsage;
    private String virusSignatures;
}