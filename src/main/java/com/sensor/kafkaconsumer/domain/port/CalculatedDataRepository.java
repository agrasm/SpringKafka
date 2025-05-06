package com.sensor.kafkaconsumer.domain.port;

import com.sensor.kafkaconsumer.domain.model.CalculatedData;

public interface CalculatedDataRepository {
    void save(CalculatedData calculatedData);
}