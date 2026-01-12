package it.unicam.resourcebooking.dto;

import it.unicam.resourcebooking.model.BookingStatus;
import java.time.OffsetDateTime;

public record BookingResponse(
        Long id,
        Long resourceId,
        Long userId,
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        BookingStatus status
) {}
