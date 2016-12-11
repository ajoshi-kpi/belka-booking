package com.example.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    private LocalTime openTime;
    private LocalTime closeTime;

    @OneToMany(mappedBy = "room")
    private Set<Booking> bookings;
}
