package com.hotel.repistories;

import com.hotel.models.Booking;
import com.hotel.models.enums.BookingState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByStateOrderByIdDesc(BookingState state);
    List<Booking> findByCheckOutDate(LocalDate date);
}
