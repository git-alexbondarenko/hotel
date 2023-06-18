package com.hotel.controllers;

import com.hotel.models.enums.BookingSelector;
import com.hotel.services.BookingService;
import com.hotel.services.GuestService;
import com.hotel.services.RoomService;
import com.hotel.utils.GuestValidator;
import com.hotel.models.Booking;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/bookings")

public class BookingController {
    private final BookingService bookingService;
    private final RoomService roomService;
    private final GuestService guestService;
    private final GuestValidator guestValidator;

    @Autowired
    public BookingController(BookingService bookingService, RoomService roomService,
                             GuestService guestService, GuestValidator guestValidator) {
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.guestService = guestService;
        this.guestValidator = guestValidator;
    }

    @GetMapping()
    public String index(@RequestParam(name = "state", required = false) String state, Model model) {
        switch (state != null ? state : "") {
            case "ONGOING", "COMPLETED" -> model.addAttribute("bookings", bookingService.findByState(state));
            case "ENDING_TODAY" -> model.addAttribute("bookings", bookingService.findByCheckOutDate(LocalDate.now()));
            default -> model.addAttribute("bookings", bookingService.findAll());
        }
        model.addAttribute("dateNow", LocalDate.now());
        model.addAttribute("bookingState", BookingSelector.values());
        return "/bookings/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("booking", bookingService.findOne(id));
        return "/bookings/show";
    }


    @GetMapping("/{bookingId}/edit")
    public String edit(Model model, @PathVariable("bookingId") int bookingId) {
        model.addAttribute("booking", bookingService.findOne(bookingId));
        return "/bookings/edit";
    }

    @PatchMapping("/{bookingId}")
    public String update(@ModelAttribute("booking") Booking updatedBooking, @PathVariable("bookingId") int bookingId) {
        bookingService.updateDates(bookingId, updatedBooking);
        return "redirect:/bookings/{bookingId}";
    }


    @PostMapping("/{bookingId}/close")
    public String close(@PathVariable("bookingId") int bookingId) {
        bookingService.close(bookingId);
        return "redirect:/bookings/{bookingId}";
    }

    @GetMapping("/new")
    public String newBooking(@ModelAttribute("booking") Booking booking,
                             @RequestParam("room") int roomNumber) {
        booking.setRoom(roomService.findOne(roomNumber));
        return "/bookings/new";
    }

    @PostMapping("/new")
    public String newBookingNext(@ModelAttribute("booking") Booking booking, BindingResult bindingResult) {
        guestValidator.validateNotPresent(booking.getGuest(), bindingResult);
        if (bindingResult.hasErrors()) {
            return "/bookings/new";
        }

        if (guestService.findByPassportId(booking.getGuest().getPassportId()) != null) {
            booking.setGuest(guestService.findByPassportId(booking.getGuest().getPassportId()));
        }
        return "/bookings/new";
    }

    @PostMapping("/add")
    public String newBookingCreate(@ModelAttribute("booking") @Valid Booking booking, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/bookings/new";
        }
        bookingService.createBooking(booking);
        return "redirect:/rooms";
    }

    @GetMapping("/checkOut")
    public String checkOut(@RequestParam("room") int roomNumber){
        bookingService.closeByRoomNumber(roomNumber);
        return "redirect:/rooms";
    }
}
