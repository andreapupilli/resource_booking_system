package it.unicam.resourcebooking.repo;

import it.unicam.resourcebooking.model.Booking;
import it.unicam.resourcebooking.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
           select b
           from Booking b
           where b.resource.id = :resourceId
             and b.status = :status
             and b.startAt < :endAt
             and b.endAt > :startAt
           """)
    List<Booking> findConflictingBookings(
            @Param("resourceId") Long resourceId,
            @Param("startAt") OffsetDateTime startAt,
            @Param("endAt") OffsetDateTime endAt,
            @Param("status") BookingStatus status
    );
}
