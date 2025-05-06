package com.sensor.kafkaconsumer.domain.port;

import com.sensor.kafkaconsumer.domain.model.StatisticalData;
import com.sensor.kafkaconsumer.domain.model.LocationHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticalDataRepository {
    void save(StatisticalData statisticalData);
    List<LocationHistory> findLocationHistoryByDeviceIdAndTimeRange(String deviceId, LocalDateTime begin, LocalDateTime end);
}