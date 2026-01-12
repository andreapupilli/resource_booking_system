package it.unicam.resourcebooking.service;

import it.unicam.resourcebooking.dto.BookingResponse;
import it.unicam.resourcebooking.dto.CreateBookingRequest;
import it.unicam.resourcebooking.exception.ApiException;
import it.unicam.resourcebooking.model.*;
import it.unicam.resourcebooking.repo.BookingRepository;
import it.unicam.resourcebooking.repo.ResourceRepository;
import it.unicam.resourcebooking.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          ResourceRepository resourceRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> list() {
        return bookingRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional
    public BookingResponse create(CreateBookingRequest request) {
        if (!request.endAt().isAfter(request.startAt())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "endAt deve essere successivo a startAt");
        }

        Resource resource = resourceRepository.findById(request.resourceId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Resource not found: " + request.resourceId()));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found: " + request.userId()));

        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                resource.getId(),
                request.startAt(),
                request.endAt(),
                BookingStatus.CONFIRMED
        );

        if (!conflicts.isEmpty()) {
            throw new ApiException(HttpStatus.CONFLICT, "Resource already booked in this time range");
        }

        Booking booking = new Booking();
        booking.setResource(resource);
        booking.setUser(user);
        booking.setStartAt(request.startAt());
        booking.setEndAt(request.endAt());
        booking.setStatus(BookingStatus.CONFIRMED);

        return toDto(bookingRepository.save(booking));
    }

    @Transactional
    public BookingResponse cancel(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Booking not found: " + bookingId));

        booking.setStatus(BookingStatus.CANCELLED);
        return toDto(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    public boolean isAvailable(Long resourceId, java.time.OffsetDateTime startAt, java.time.OffsetDateTime endAt) {
        if (!resourceRepository.existsById(resourceId)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Resource not found: " + resourceId);
        }
        if (!endAt.isAfter(startAt)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "endAt deve essere successivo a startAt");
        }
        return bookingRepository.findConflictingBookings(resourceId, startAt, endAt, BookingStatus.CONFIRMED).isEmpty();
    }

    private BookingResponse toDto(Booking b) {
        return new BookingResponse(
                b.getId(),
                b.getResource() != null ? b.getResource().getId() : null,
                b.getUser() != null ? b.getUser().getId() : null,
                b.getStartAt(),
                b.getEndAt(),
                b.getStatus()
        );
    }
}
