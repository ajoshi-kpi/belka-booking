package com.example.controllers;

import com.example.domain.Booking;
import com.example.domain.Room;
import com.example.domain.User;
import com.example.dto.BookingDto;
import com.example.repositories.BookingRepository;
import com.example.repositories.RoomRepository;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RepositoryRestController
public class BookingController {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @RequestMapping(value = "/bookings/newBooking", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void book(@RequestBody BookingDto bookingDto) {
        Room room = roomRepository.findByTitle(bookingDto.getRoomTitle());
        User user = userRepository.findByUsername(bookingDto.getUsername());
        LocalDateTime dateTime = bookingDto.getDateTime();
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setDateTime(dateTime);
        bookingRepository.save(booking);
    }
}
