package com.sensor.kafkaconsumer.domain.port;

import com.sensor.kafkaconsumer.domain.model.LocationHistory;
import java.time.LocalDateTime;
import java.util.List;

public interface LocationHistoryService {
    List<LocationHistory> getLocationHistory(String deviceId, LocalDateTime begin, LocalDateTime end);
}