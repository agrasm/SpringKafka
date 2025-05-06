package com.sensor.kafkaconsumer.infrastructure.persistence.sensor.repository;

import com.sensor.kafkaconsumer.infrastructure.persistence.sensor.entity.OperationalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationalJpaRepository extends JpaRepository<OperationalEntity, String> {
}