package com.sensor.kafkaconsumer.infrastructure.web;

import com.sensor.kafkaconsumer.domain.model.LocationHistory;
import com.sensor.kafkaconsumer.domain.port.LocationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final LocationHistoryService locationHistoryService;

    @GetMapping("/{deviceId}/location-history")
    public ResponseEntity<List<LocationHistory>> getLocationHistory(
            @PathVariable String deviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime begin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<LocationHistory> locationHistory = locationHistoryService.getLocationHistory(deviceId, begin, end);
        return ResponseEntity.ok(locationHistory);
    }
}