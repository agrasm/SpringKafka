package com.sensor.kafkaconsumer.infrastructure.persistence.adapter;

import com.sensor.kafkaconsumer.domain.model.TemperatureData;
import com.sensor.kafkaconsumer.domain.port.TemperatureDataRepository;
import com.sensor.kafkaconsumer.infrastructure.persistence.remote.entity.TemperatureEntity;
import com.sensor.kafkaconsumer.infrastructure.persistence.remote.repository.TemperatureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemperatureDataRepositoryAdapter implements TemperatureDataRepository {

    private final TemperatureJpaRepository temperatureJpaRepository;

    @Override
    public TemperatureData findByDeviceId(String deviceId) {
        TemperatureEntity entity = temperatureJpaRepository.findByDeviceId(deviceId);
        if (entity == null) {
            return null;
        }

        return TemperatureData.builder()
                .deviceId(entity.getDeviceId())
                .temperature(entity.getTemperature())
                .build();
    }
}