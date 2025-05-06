package com.sensor.kafkaconsumer.infrastructure.persistence.adapter;

import com.sensor.kafkaconsumer.domain.model.CalculatedData;
import com.sensor.kafkaconsumer.domain.port.CalculatedDataRepository;
import com.sensor.kafkaconsumer.infrastructure.persistence.sensor.entity.CalculatedEntity;
import com.sensor.kafkaconsumer.infrastructure.persistence.sensor.repository.CalculatedJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculatedDataRepositoryAdapter implements CalculatedDataRepository {

    private final CalculatedJpaRepository calculatedJpaRepository;

    @Override
    public void save(CalculatedData calculatedData) {
        CalculatedEntity entity = CalculatedEntity.builder()
                .id(calculatedData.getId())
                .deviceId(calculatedData.getDeviceId())
                .type(calculatedData.getType())
                .opType(calculatedData.getOpType())
                .opValue(calculatedData.getOpValue())
                .timestamp(calculatedData.getTimestamp())
                .build();

        calculatedJpaRepository.save(entity);
    }
}