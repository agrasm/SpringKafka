package com.sensor.kafkaconsumer.domain.port;

import com.sensor.kafkaconsumer.domain.model.OperationalData;

public interface OperationalDataRepository {
    void save(OperationalData operationalData);
}