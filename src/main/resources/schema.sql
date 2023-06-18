CREATE DATABASE IF NOT EXISTS hotel;

CREATE TABLE IF NOT EXISTS guest(
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    first_name VARCHAR(255) NOT NULL,
                                    last_name VARCHAR(255) NOT NULL,
                                    passport_id VARCHAR(255) NOT NULL UNIQUE,
                                    phone VARCHAR(10) NOT NULL,
                                    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS room(
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   room_number INT NOT NULL UNIQUE,
                                   room_class VARCHAR(32) NOT NULL,
                                   beds_quantity INT NOT NULL,
                                   price_per_night DOUBLE NOT NULL,
                                   state VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS booking(
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      guest_id INT REFERENCES guest(id),
                                      room_id INT REFERENCES room(id),
                                      check_in DATE NOT NULL,
                                      check_out DATE NOT NULL,
                                      total_cost DOUBLE,
                                      state VARCHAR(32)
);



