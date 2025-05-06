package com.sensor.kafkaconsumer.infrastructure.persistence.remote.repository;

import com.sensor.kafkaconsumer.infrastructure.persistence.remote.entity.TemperatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureJpaRepository extends JpaRepository<TemperatureEntity, String> {
    TemperatureEntity findByDeviceId(String deviceId);
}