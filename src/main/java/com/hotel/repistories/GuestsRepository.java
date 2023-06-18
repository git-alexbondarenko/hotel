package com.hotel.repistories;

import com.hotel.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestsRepository extends JpaRepository<Guest, Integer> {
    Guest findByPassportId(String id);
    Guest findByPhone(String phone);
    @Query("SELECT g FROM Guest g WHERE CONCAT(g.firstName, g.lastName) LIKE %:name%")
    List<Guest> findByFirstNameOrLastNameContains(String name);
}
