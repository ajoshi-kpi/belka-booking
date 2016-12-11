package com.example.controllers;

import com.example.domain.Booking;
import com.example.domain.Room;
import com.example.domain.User;
import com.example.dto.BookingDto;
import com.example.repositories.BookingRepository;
import com.example.repositories.RoomRepository;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    public PersistentEntityResource book(@RequestBody BookingDto bookingDto, PersistentEntityResourceAssembler asm) throws BookingException, NoSuchUserException, NoSuchRoomException {
        LocalDateTime dateTime = bookingDto.getDateTime();
        Duration bookingDuration = Duration.ofMinutes(bookingDto.getMinutes());

        Room room = roomRepository.findByTitle(bookingDto.getRoomTitle());
        if(!canBeBooked(dateTime, bookingDuration, room)) {
            throw new BookingException();
        }

        User user = userRepository.findByUsername(bookingDto.getUsername());
        if(user == null) throw new NoSuchUserException();
        if(room == null) throw new NoSuchRoomException();
        return asm.toFullResource(bookingRepository.save(createBooking(user, room, dateTime, bookingDuration)));
    }

    private boolean canBeBooked(LocalDateTime bookingStart, Duration bookingDuration, Room roomToBook) {
        boolean canBeBooked = true;
        LocalDateTime bookingEnd = bookingStart.plusMinutes(bookingDuration.toMinutes());
        for (Booking currentBooking : bookingRepository.findAll()) {
            if(currentBooking.getRoom().equals(roomToBook)) {
                LocalDateTime currentBookingStart = currentBooking.getDateTime();
                LocalDateTime currentBookingEnd = currentBookingStart.plusMinutes(bookingDuration.toMinutes());
                if ((bookingStart.isAfter(currentBookingStart) && bookingStart.isBefore(currentBookingEnd)) ||
                        (bookingEnd.isAfter(currentBookingStart) && bookingEnd.isBefore(currentBookingEnd))) {
                    canBeBooked = false;
                }
                if (bookingStart.isEqual(currentBookingStart)) {
                    canBeBooked = false;
                }
                if (bookingEnd.isEqual(currentBookingStart)) {
                    canBeBooked = false;
                }
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
