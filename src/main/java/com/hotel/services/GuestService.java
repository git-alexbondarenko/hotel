package com.hotel.services;

import com.hotel.models.Guest;
import com.hotel.repistories.GuestsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    private final GuestsRepository guestsRepository;

    @Autowired
    public GuestService(GuestsRepository guestsRepository) {
        this.guestsRepository = guestsRepository;
    }

    public List<Guest> findAll() {
        return guestsRepository.findAll();
    }

    public Guest findOne(int id) {
        return guestsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Guest guest) {
        guestsRepository.save(guest);
    }

    @Transactional
    public void update(int id, Guest updatedGuest) {
        updatedGuest.setId(id);
        guestsRepository.save(updatedGuest);
    }

    @Transactional
    public void delete(int id) {
        guestsRepository.deleteById(id);
    }

    public Guest findByPassportId(String passportId) {
        return guestsRepository.findByPassportId(passportId);
    }

    public List<Guest> findByName(String name) {
        System.out.println(name);
        return guestsRepository.findByFirstNameOrLastNameContains(name);
    }

    public Guest findByPhone(String phone) {
        return guestsRepository.findByPhone(phone);
    }
}
