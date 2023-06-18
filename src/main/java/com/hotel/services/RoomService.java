package com.hotel.services;

import com.hotel.models.Room;
import com.hotel.repistories.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {
    private final RoomsRepository roomsRepository;

    @Autowired
    public RoomService(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    public List<Room> findAll() {
        return roomsRepository.findAll(Sort.by("roomNumber"));
    }

    public Room findOne(int roomNumber) {
        return roomsRepository.findRoomByRoomNumber(roomNumber).orElse(null);
    }

    @Transactional
    public void save(Room room) {
        roomsRepository.save(room);
    }

    @Transactional
    public void update(int roomNumber, Room updatedRoom) {
        updatedRoom.setId(roomsRepository.findRoomByRoomNumber(roomNumber).get().getId());
        roomsRepository.save(updatedRoom);
    }

    @Transactional
    public void delete(int id) {
        roomsRepository.deleteByRoomNumber(id);
    }

    public List<Room> findRoomsByClass(String roomClass) {
        return roomsRepository.findRoomsByRoomClass(roomClass);
    }

    public Room findByRoomNumber(String roomNumber) {
        return roomsRepository.findRoomByRoomNumber(Integer.parseInt(roomNumber)).orElse(null);
    }

    public List<Room> findByBedsQuantity(String bedsQuantity) {
        return roomsRepository.findRoomsByBedsQuantity(Integer.parseInt(bedsQuantity));
    }
}
