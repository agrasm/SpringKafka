package com.sensor.kafkaconsumer.infrastructure.persistence.sensor.repository;

import com.sensor.kafkaconsumer.infrastructure.persistence.sensor.entity.StatisticalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticalJpaRepository extends JpaRepository<StatisticalEntity, String> {

    @Query("SELECT s FROM StatisticalEntity s WHERE s.deviceId = :deviceId AND s.statType = 'location' AND s.timestamp BETWEEN :beginTime AND :endTime ORDER BY s.timestamp")
    List<StatisticalEntity> findLocationHistoryByDeviceIdAndTimeRange(
            @Param("deviceId") String deviceId,
            @Param("beginTime") LocalDateTime beginTime,
            @Param("endTime") LocalDateTime endTime);
}