package com.sensor.kafkaconsumer.infrastructure.persistence.adapter;

import com.sensor.kafkaconsumer.domain.model.OperationalData;
import com.sensor.kafkaconsumer.domain.port.OperationalDataRepository;
import com.sensor.kafkaconsumer.infrastructure.persistence.sensor.entity.OperationalEntity;
import com.sensor.kafkaconsumer.infrastructure.persistence.sensor.repository.OperationalJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperationalDataRepositoryAdapter implements OperationalDataRepository {

    private final OperationalJpaRepository operationalJpaRepository;

    @Override
    public void save(OperationalData operationalData) {
        OperationalEntity entity = OperationalEntity.builder()
                .id(operationalData.getId())
                .deviceId(operationalData.getDeviceId())
                .type(operationalData.getType())
                .opType(operationalData.getOpType())
                .opValue(operationalData.getOpValue())
                .timestamp(operationalData.getTimestamp())
                .build();

        operationalJpaRepository.save(entity);
    }
}