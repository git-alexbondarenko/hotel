package com.hotel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table(name = "guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "passport_id")
    @NotEmpty(message = "Passport id should not be empty")
    private String passportId;
    @Column(name = "first_name")
    @NotEmpty(message = "Name should not be empty")
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty(message = "Name should not be empty")
    private String lastName;
    @Column(name = "phone")
    @NotEmpty(message = "phone number should not be empty")
    @Pattern(regexp = "^\\d{10}$", message = "phone number must be in format: 0123456789")
    private String phone;
    @Column(name = "email")
    @Email(message = "Enter valid email")
    @NotEmpty(message = "Email should not be empty")
    private String email;
    @OneToMany(mappedBy = "guest")
    private List<Booking> reservations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Booking> getReservations() {
        return reservations;
    }

    public void setReservations(List<Booking> reservations) {
        this.reservations = reservations;
    }
}
