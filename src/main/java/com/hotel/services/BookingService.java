package com.hotel.services;

import com.hotel.models.enums.BookingState;
import com.hotel.models.enums.RoomState;
import com.hotel.repistories.BookingsRepository;
import com.hotel.utils.CheckPrinter;
import com.hotel.models.Booking;
import com.hotel.models.Room;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    private final BookingsRepository bookingsRepository;
    private final RoomService roomService;
    private final GuestService guestService;

    @Autowired
    public BookingService(BookingsRepository bookingsRepository, RoomService roomService, GuestService guestService) {
        this.bookingsRepository = bookingsRepository;
        this.roomService = roomService;
        this.guestService = guestService;
    }

    public List<Booking> findAll() {
        return bookingsRepository.findAll(Sort.by("id").descending());
    }

    public Booking findOne(int id) {
        return bookingsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Booking booking) {
        bookingsRepository.save(booking);
    }

    public List<Booking> findByState(String state) {
        return bookingsRepository.findByStateOrderByIdDesc(BookingState.valueOf(state));
    }

    public List<Booking> findByCheckOutDate(LocalDate date) {
        return bookingsRepository.findByCheckOutDate(date);
    }

    public void createBooking(Booking booking) {
        if (guestService.findByPassportId(booking.getGuest().getPassportId()) == null) {
            guestService.save(booking.getGuest());
        }
        booking.setState(BookingState.ONGOING);
        booking.getRoom().setState(RoomState.CHECKED_IN);
        booking.setTotalCost(calculateTotal(booking.getCheckInDate(), booking.getCheckOutDate(),
                booking.getRoom().getPricePerNight()));
        save(booking);
        roomService.save(booking.getRoom());
        CheckPrinter.print(booking);
    }

    public void updateDates(int bookingId, Booking updatedBooking) {
        Booking booking = findOne(bookingId);
        booking.setCheckInDate(updatedBooking.getCheckInDate());
        booking.setCheckOutDate(updatedBooking.getCheckOutDate());
        booking.setTotalCost(calculateTotal(booking.getCheckInDate(), booking.getCheckOutDate(),
                booking.getRoom().getPricePerNight()));
        bookingsRepository.save(booking);
    }

    public void closeByRoomNumber(int roomNumber) {
        Room room = roomService.findOne(roomNumber);
        Booking booking = room.getBookings().stream().filter(b -> b.getState().equals(BookingState.ONGOING))
                .findFirst().get();
        close(booking.getId());
    }

    public void close(int bookingId) {
        Booking booking = findOne(bookingId);
        double pricePerNight = booking.getRoom().getPricePerNight();
        double previousTotal = booking.getTotalCost();
        double newTotal = calculateTotal(booking.getCheckInDate(), LocalDate.now(), pricePerNight);
        booking.setTotalCost(newTotal);
        booking.getRoom().setState(RoomState.EMPTY);
        booking.setState(BookingState.COMPLETED);
        booking.setCheckOutDate(LocalDate.now());
        bookingsRepository.save(booking);
        booking.setTotalCost(0 - (previousTotal - newTotal));
        if (previousTotal - newTotal >= pricePerNight) {
            CheckPrinter.print(booking);
        }
    }

    public double calculateTotal(LocalDate checkInDate, LocalDate checkOutDate, double pricePerNight) {
        int days = checkOutDate.compareTo(checkInDate);
        return pricePerNight * (days == 0 ? 1 : days);
    }
}
