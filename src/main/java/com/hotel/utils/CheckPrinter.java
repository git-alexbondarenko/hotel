package com.hotel.utils;

import com.hotel.models.Booking;

public class CheckPrinter {
    public static void print(Booking booking) {
        System.out.println(
                """
                ++++++++ %6d ++++++++
                
                 Room: %9d
                 Check in: %12s
                 Check out: %11s
                 Total: %10.2f
                 
                    Have a nice day
                ++++++++++++++++++++++++
                """.formatted(booking.getId(), booking.getRoom().getRoomNumber(),
                        booking.getCheckInDate(), booking.getCheckOutDate(),
                        booking.getTotalCost()));
    }
}
