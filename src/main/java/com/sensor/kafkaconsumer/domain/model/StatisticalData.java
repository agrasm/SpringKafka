package com.sensor.kafkaconsumer.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticalData {
    private String id;
    private String deviceId;
    private String type;
    private String statType;
    private String statValue;
}