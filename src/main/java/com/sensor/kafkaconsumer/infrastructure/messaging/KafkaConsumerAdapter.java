package com.sensor.kafkaconsumer.infrastructure.messaging;

import com.sensor.kafkaconsumer.domain.port.SensorDataConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerAdapter {

    private final SensorDataConsumer sensorDataConsumer;

    @KafkaListener(topics = "${kafka.topic.sensor-data}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        log.info("Received message: {}", message);
        sensorDataConsumer.consume(message);
    }
}