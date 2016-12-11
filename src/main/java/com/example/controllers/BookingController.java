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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Duration;
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
    public void book(@RequestBody BookingDto bookingDto) throws BookingException {
        LocalDateTime dateTime = bookingDto.getDateTime();
        Duration bookingDuration = Duration.ofMinutes(bookingDto.getMinutes());

        if(!canBeBooked(dateTime, bookingDuration)) {
            throw new BookingException();
        }

        Room room = roomRepository.findByTitle(bookingDto.getRoomTitle());
        User user = userRepository.findByUsername(bookingDto.getUsername());
        bookingRepository.save(createBooking(user, room, dateTime, bookingDuration));
    }

    private boolean canBeBooked(LocalDateTime bookingStart, Duration bookingDuration) {
        boolean canBeBooked = true;
        LocalDateTime bookindEnd = bookingStart.plusMinutes(bookingDuration.toMinutes());
        for (Booking currentBooking : bookingRepository.findAll()) {
            LocalDateTime currentBookingStart = currentBooking.getDateTime();
            LocalDateTime currentBookingEnd = currentBookingStart.plusMinutes(bookingDuration.toMinutes());
            if ((bookingStart.isAfter(currentBookingStart) && bookingStart.isBefore(currentBookingEnd)) ||
                (bookindEnd.isAfter(currentBookingStart) && bookindEnd.isBefore(currentBookingEnd))) {
                canBeBooked = false;
            }
        }
        return canBeBooked;
    }

    private Booking createBooking(User user, Room room, LocalDateTime dateTime, Duration duration) {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setDateTime(dateTime);
        booking.setDuration(duration);

        return booking;
    }
}
