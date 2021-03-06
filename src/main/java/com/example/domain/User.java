package com.example.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class User {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String username;
    @OneToMany(mappedBy = "user")
    private Set<Booking> bookings;
}
