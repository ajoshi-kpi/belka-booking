package com.example.repositories;

import com.example.domain.Booking;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {
}
