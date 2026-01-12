package it.unicam.resourcebooking.controller;

import it.unicam.resourcebooking.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/resources")
public class AvailabilityController {

    private final BookingService bookingService;

    public AvailabilityController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}/availability")
    public Map<String, Object> availability(@PathVariable Long id,
                                            @RequestParam OffsetDateTime startAt,
                                            @RequestParam OffsetDateTime endAt) {

        boolean available = bookingService.isAvailable(id, startAt, endAt);

        return Map.of(
                "resourceId", id,
                "startAt", startAt,
                "endAt", endAt,
                "available", available
        );
    }
}
