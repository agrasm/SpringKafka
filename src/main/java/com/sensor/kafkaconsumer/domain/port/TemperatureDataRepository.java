package com.sensor.kafkaconsumer.domain.port;

import com.sensor.kafkaconsumer.domain.model.TemperatureData;

public interface TemperatureDataRepository {
    TemperatureData findByDeviceId(String deviceId);
}