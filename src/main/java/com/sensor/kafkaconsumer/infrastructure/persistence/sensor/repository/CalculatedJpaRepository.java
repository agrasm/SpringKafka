package com.sensor.kafkaconsumer.infrastructure.persistence.sensor.repository;

import com.sensor.kafkaconsumer.infrastructure.persistence.sensor.entity.CalculatedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculatedJpaRepository extends JpaRepository<CalculatedEntity, String> {
}