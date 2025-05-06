package com.sensor.kafkaconsumer.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensor.kafkaconsumer.domain.model.CalculatedData;
import com.sensor.kafkaconsumer.domain.model.LocationHistory;
import com.sensor.kafkaconsumer.domain.model.OperationalData;
import com.sensor.kafkaconsumer.domain.model.StatisticalData;
import com.sensor.kafkaconsumer.domain.model.TemperatureData;
import com.sensor.kafkaconsumer.domain.port.CalculatedDataRepository;
import com.sensor.kafkaconsumer.domain.port.LocationHistoryService;
import com.sensor.kafkaconsumer.domain.port.OperationalDataRepository;
import com.sensor.kafkaconsumer.domain.port.SensorDataConsumer;
import com.sensor.kafkaconsumer.domain.port.StatisticalDataRepository;
import com.sensor.kafkaconsumer.domain.port.TemperatureDataRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorDataService implements SensorDataConsumer, LocationHistoryService {

    private final OperationalDataRepository operationalDataRepository;
    private final StatisticalDataRepository statisticalDataRepository;
    private final CalculatedDataRepository calculatedDataRepository;
    private final TemperatureDataRepository temperatureDataRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void consume(String message) {
        try {
            JsonNode rootNode = objectMapper.readTree(message);
            String id = rootNode.get("id").asText();
            String deviceId = rootNode.get("device_id").asText();
            String type = rootNode.get("type").asText();

            if (rootNode.has("op_changes")) {
                // Process operational message
                JsonNode opChangesNode = rootNode.get("op_changes").get(0);
                if (opChangesNode.has("temperature")) {
                    String temperature = opChangesNode.get("temperature").asText();

                    // Save to operational table
                    OperationalData operationalData = OperationalData.builder()
                            .id(id)
                            .deviceId(deviceId)
                            .type(type)
                            .opType("temperature")
                            .opValue(temperature)
                            .timestamp(LocalDateTime.now())
                            .build();
                    operationalDataRepository.save(operationalData);

                    // Process calculation with remote temperature data
                    processTemperatureCalculation(deviceId, temperature);
                }
            } else if (rootNode.has("stat_changes")) {
                // Process statistical message
                JsonNode statChangesNode = rootNode.get("stat_changes").get(0);
                if (statChangesNode.has("airport")) {
                    String airport = statChangesNode.get("airport").asText();

                    // Save to statistical table
                    StatisticalData statisticalData = StatisticalData.builder()
                            .id(id)
                            .deviceId(deviceId)
                            .type(type)
                            .statType("airport")
                            .statValue(airport)
                            .build();
                    statisticalDataRepository.save(statisticalData);
                } else if (statChangesNode.has("location")) {
                    String location = statChangesNode.get("location").asText();

                    // Save to statistical table
                    StatisticalData statisticalData = StatisticalData.builder()
                            .id(id)
                            .deviceId(deviceId)
                            .type(type)
                            .statType("location")
                            .statValue(location)
                            .build();
                    statisticalDataRepository.save(statisticalData);
                }
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing message: {}", message, e);
        }
    }

    private void processTemperatureCalculation(String deviceId, String temperature) {
        TemperatureData remoteTemperature = temperatureDataRepository.findByDeviceId(deviceId);
        if (remoteTemperature != null) {
            try {
                double remoteTemp = Double.parseDouble(remoteTemperature.getTemperature());
                double currentTemp = Double.parseDouble(temperature);
                double calculatedValue = remoteTemp - currentTemp;

                CalculatedData calculatedData = CalculatedData.builder()
                        .id(deviceId + "-" + System.currentTimeMillis())
                        .deviceId(deviceId)
                        .type("TemperatureCalculation")
                        .opType("temperature_diff")
                        .opValue(String.valueOf(calculatedValue))
                        .timestamp(LocalDateTime.now())
                        .build();

                calculatedDataRepository.save(calculatedData);
            } catch (NumberFormatException e) {
                log.error("Error parsing temperature values for deviceId: {}", deviceId, e);
            }
        }
    }

    @Override
    public List<LocationHistory> getLocationHistory(String deviceId, LocalDateTime begin, LocalDateTime end) {
        return statisticalDataRepository.findLocationHistoryByDeviceIdAndTimeRange(deviceId, begin, end);
    }
}