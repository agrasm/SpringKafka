package com.sensor.kafkaconsumer.infrastructure.persistence.sensor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "statistical")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticalEntity {
    @Id
    private String id;
    private String deviceId;
    private String type;
    private String statType;
    private String statValue;
    private LocalDateTime timestamp;
}