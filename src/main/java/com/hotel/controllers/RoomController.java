package com.hotel.controllers;

import com.hotel.models.Room;
import com.hotel.models.enums.RoomClass;
import com.hotel.services.RoomService;
import com.hotel.utils.RoomValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;
    private final RoomValidator roomValidator;

    @Autowired
    public RoomController(RoomService roomService, RoomValidator roomValidator) {
        this.roomService = roomService;
        this.roomValidator = roomValidator;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "findBy", required = false) String param,
                        @RequestParam(value = "value", required = false) String value) {
        model.addAttribute("roomClasses", RoomClass.values());
        switch (param != null ? param : "") {
            case "roomNumber" -> model.addAttribute("rooms", roomService.findByRoomNumber(value));
            case "roomClass" -> model.addAttribute("rooms", roomService.findRoomsByClass(value));
            case "bedsQuantity" -> model.addAttribute("rooms", roomService.findByBedsQuantity(value));
            default -> model.addAttribute("rooms", roomService.findAll());
        }
        return "/rooms/index";
    }

    @GetMapping("/{roomNumber}")
    public String show(@ModelAttribute("room") Room room, @PathVariable("roomNumber") int roomNumber,
                       Model model) {
        model.addAttribute("room", roomService.findOne(roomNumber));
        return "rooms/show";
    }

    @GetMapping("/new")
    public String newRoom(@ModelAttribute("room") Room room, Model model) {
        model.addAttribute("roomClasses", RoomClass.values());
        return "rooms/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("room") @Valid Room room, BindingResult bindingResult) {
        roomValidator.validate(room, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/rooms/new";
        }
        roomService.save(room);
        return "redirect:/rooms";
    }

    @GetMapping("/{roomNumber}/edit")
    public String edit(Model model, @PathVariable("roomNumber") int roomNumber) {
        model.addAttribute("room", roomService.findOne(roomNumber));
        model.addAttribute("roomClasses", RoomClass.values());

        return "/rooms/edit";
    }

    @PatchMapping("/{roomNumber}")
    public String update(@ModelAttribute("room") @Valid Room room, BindingResult bindingResult,
                         @PathVariable("roomNumber") int roomNumber) {
        roomValidator.validate(room, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/rooms/edit";
        }
        roomService.update(roomNumber, room);
        return "redirect:/rooms";
    }

    @DeleteMapping("/{roomNumber}")
    public String delete(@PathVariable("roomNumber") int roomNumber) {
        roomService.delete(roomNumber);
        return "redirect:/rooms";
    }
}
