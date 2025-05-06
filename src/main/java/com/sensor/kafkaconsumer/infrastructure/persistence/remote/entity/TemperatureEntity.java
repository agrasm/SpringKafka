package com.sensor.kafkaconsumer.infrastructure.persistence.remote.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "temperature")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureEntity {
    @Id
    private String deviceId;
    private String temperature;
}