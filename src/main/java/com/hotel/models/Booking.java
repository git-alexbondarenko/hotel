package com.hotel.models;

import com.hotel.models.enums.BookingState;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @Valid
    private Room room;

    @ManyToOne
    @JoinColumn(name = "guest_id", referencedColumnName = "id")
    @Valid
    private Guest guest;

    @Column(name = "check_in")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkInDate = LocalDate.now();

    @Column(name = "check_out")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@FutureOrPresent(message = "date")
    @NotNull(message = "Date should not be empty")
    private LocalDate checkOutDate;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private BookingState state;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookingState getState() {
        return state;
    }

    public void setState(BookingState state) {
        this.state = state;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
