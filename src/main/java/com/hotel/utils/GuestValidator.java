package com.hotel.utils;

import com.hotel.models.Guest;
import com.hotel.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GuestValidator implements Validator {
    private final GuestService guestService;

    @Autowired
    public GuestValidator(GuestService guestService) {
        this.guestService = guestService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Guest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Guest guest = (Guest) target;
        if (guestService.findByPassportId(guest.getPassportId()) != null) {
            errors.rejectValue("passportId", "", "guest with this passport id already exists");
        }
    }

    public void validateNotPresent(Object target, Errors errors) {
        Guest guest = (Guest) target;
        if (guestService.findByPassportId(guest.getPassportId()) == null) {
            errors.rejectValue("guest.passportId", "", "guest with this passport id not found, " +
                    "enter credentials to register new guest");
        }
    }
}
