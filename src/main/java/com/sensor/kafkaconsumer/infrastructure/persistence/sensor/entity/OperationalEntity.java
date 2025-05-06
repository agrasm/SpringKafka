package com.sensor.kafkaconsumer.infrastructure.persistence.sensor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "operational")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationalEntity {
    @Id
    private String id;
    private String deviceId;
    private String type;
    private String opType;
    private String opValue;
    private LocalDateTime timestamp;
}