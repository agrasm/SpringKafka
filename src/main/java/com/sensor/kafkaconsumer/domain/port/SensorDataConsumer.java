package com.sensor.kafkaconsumer.domain.port;

public interface SensorDataConsumer {
    void consume(String message);
}