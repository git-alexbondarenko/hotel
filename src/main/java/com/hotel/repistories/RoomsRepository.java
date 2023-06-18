package com.hotel.repistories;

import com.hotel.models.Room;
import com.hotel.models.enums.RoomState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomsRepository extends JpaRepository<Room, Integer> {

    List<Room> findRoomsByBedsQuantity(int bedsQuantity);

    Optional<Room> findRoomById(int id);

    Optional<Room> findRoomByRoomNumber(int roomNumber);

    List<Room> findRoomsByRoomClass(String roomClass);

    void deleteByRoomNumber(int roomNumber);

    List<Room> findByState(RoomState state);
}
