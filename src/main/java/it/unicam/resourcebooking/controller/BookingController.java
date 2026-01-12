package it.unicam.resourcebooking.controller;

import it.unicam.resourcebooking.dto.BookingResponse;
import it.unicam.resourcebooking.dto.CreateBookingRequest;
import it.unicam.resourcebooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingResponse> list() {
        return bookingService.list();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookingResponse create(@Valid @RequestBody CreateBookingRequest request) {
        return bookingService.create(request);
    }

    @PutMapping("/{id}/cancel")
    public BookingResponse cancel(@PathVariable Long id) {
        return bookingService.cancel(id);
    }
}
