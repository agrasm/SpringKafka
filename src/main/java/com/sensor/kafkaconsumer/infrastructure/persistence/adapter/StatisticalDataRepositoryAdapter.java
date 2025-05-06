package com.sensor.kafkaconsumer.infrastructure.persistence.adapter;

import com.sensor.kafkaconsumer.domain.model.LocationHistory;
import com.sensor.kafkaconsumer.domain.model.StatisticalData;
import com.sensor.kafkaconsumer.domain.port.StatisticalDataRepository;
import com.sensor.kafkaconsumer.infrastructure.persistence.sensor.entity.StatisticalEntity;
import com.sensor.kafkaconsumer.infrastructure.persistence.sensor.repository.StatisticalJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StatisticalDataRepositoryAdapter implements StatisticalDataRepository {

    private final StatisticalJpaRepository statisticalJpaRepository;

    @Override
    public void save(StatisticalData statisticalData) {
        StatisticalEntity entity = StatisticalEntity.builder()
                .id(statisticalData.getId())
                .deviceId(statisticalData.getDeviceId())
                .type(statisticalData.getType())
                .statType(statisticalData.getStatType())
                .statValue(statisticalData.getStatValue())
                .timestamp(LocalDateTime.now())
                .build();

        statisticalJpaRepository.save(entity);
    }

    @Override
    public List<LocationHistory> findLocationHistoryByDeviceIdAndTimeRange(String deviceId, LocalDateTime begin, LocalDateTime end) {
        return statisticalJpaRepository.findLocationHistoryByDeviceIdAndTimeRange(deviceId, begin, end)
                .stream()
                .map(entity -> new LocationHistory(entity.getDeviceId(), entity.getStatValue(), entity.getTimestamp().toString()))
                .collect(Collectors.toList());
    }
}