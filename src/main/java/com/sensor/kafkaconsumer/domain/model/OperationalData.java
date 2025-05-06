package com.sensor.kafkaconsumer.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationalData {
    private String id;
    private String deviceId;
    private String type;
    private String opType;
    private String opValue;
    private LocalDateTime timestamp;
}