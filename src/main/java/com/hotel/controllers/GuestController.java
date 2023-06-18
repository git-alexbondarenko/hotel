package com.hotel.controllers;

import com.hotel.models.Guest;
import com.hotel.models.Room;
import com.hotel.services.GuestService;
import com.hotel.utils.GuestValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/guests")
public class GuestController {
    private final GuestService guestService;
    private final GuestValidator guestValidator;

    @Autowired
    public GuestController(GuestService guestService, GuestValidator guestValidator) {
        this.guestService = guestService;
        this.guestValidator = guestValidator;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "findBy", required = false) String param,
                        @RequestParam(value = "value", required = false) String value) {
        switch (param != null ? param : "") {
            case "name" -> model.addAttribute("guests", guestService.findByName(value));
            case "phone" -> model.addAttribute("guests", guestService.findByPhone(value));
            case "passportId" -> model.addAttribute("guests", guestService.findByPassportId(value));
            default -> model.addAttribute("guests", guestService.findAll());
        }
        return "/guests/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("room") Room room, Model model) {
        model.addAttribute("guest", guestService.findOne(id));
        return "/guests/show";
    }

    @GetMapping("/new")
    public String newGuest(@ModelAttribute("guest") Guest guest) {
        return "/guests/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("guest") @Valid Guest guest, BindingResult bindingResult) {
        guestValidator.validate(guest, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/guests/new";
        }
        guestService.save(guest);
        return "redirect:/guests";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("guest", guestService.findOne(id));
        return "/guests/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("guest") @Valid Guest guest, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "/guests/edit";
        }
        guestService.update(id, guest);
        return "redirect:/guests";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        guestService.delete(id);
        return "redirect:/guests";
    }
}
