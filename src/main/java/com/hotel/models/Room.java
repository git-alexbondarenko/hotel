package com.hotel.models;

import com.hotel.models.enums.RoomState;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "room_number")
    private int roomNumber;
    @Column(name = "room_class")
    private String roomClass;
    @Column(name = "beds_quantity")
    @Min(value = 1, message = "value should be >=1")
    @Max(value = 10, message = "value should be <=10")
    private int bedsQuantity;
    @Column(name = "price_per_night")
    private double pricePerNight;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private RoomState state = RoomState.EMPTY;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getRoomClass() {
        return roomClass;
    }
    public void setRoomClass(String roomClass) {
        this.roomClass = roomClass;
    }
    public int getBedsQuantity() {
        return bedsQuantity;
    }
    public void setBedsQuantity(int bedsQuantity) {
        this.bedsQuantity = bedsQuantity;
    }
    public double getPricePerNight() {
        return pricePerNight;
    }
    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
    public RoomState getState() {
        return state;
    }
    public void setState(RoomState state) {
        this.state = state;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
