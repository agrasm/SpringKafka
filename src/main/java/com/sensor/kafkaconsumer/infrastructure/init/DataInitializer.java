package com.sensor.kafkaconsumer.infrastructure.init;

import com.sensor.kafkaconsumer.infrastructure.persistence.remote.entity.TemperatureEntity;
import com.sensor.kafkaconsumer.infrastructure.persistence.remote.repository.TemperatureJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final TemperatureJpaRepository temperatureJpaRepository;
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        log.info("Initializing temperature data in remote database...");

        List<TemperatureEntity> temperatures = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            String deviceId = "device_" + i;
            String temperature = String.valueOf(20 + random.nextInt(20)); // Random temperature between 20 and 40

            temperatures.add(TemperatureEntity.builder()
                    .deviceId(deviceId)
                    .temperature(temperature)
                    .build());
        }

        temperatureJpaRepository.saveAll(temperatures);
        log.info("Temperature data initialized successfully with {} records", temperatures.size());
    }
}