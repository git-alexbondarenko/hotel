package com.hotel.utils;

import com.hotel.models.Room;
import com.hotel.services.RoomService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RoomValidator implements Validator {
    private final RoomService roomService;

    public RoomValidator(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Room.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Room room = (Room) target;
        if (roomService.findOne(room.getRoomNumber()) != null) {
            errors.rejectValue("roomNumber", "", "Room with this number already exists");
        }
    }
}
